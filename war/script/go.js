$(document).ready(function() {
  // COLOR
  var BOARD_COLOR = "#fff0c6";
  //
  var socket;
  // 0:Space, 1:Black, 2:White, 3:OutSide
  var gameId = 0;
  var player = 0;
  var playerId = '';
  var onMyTurn = false;
  var token = null;
  var gridSize = 9;
  var black_before = [];
  var white_before = [];
  // interface
  var unit = 50;
  var margineTop = 25;
  var margineSide = 25;
  var posX = -1;
  var posY = -1;
  var grid;
  var width = 0;
  var height = 0;
  // canvas
  var canvas = document.getElementById('board');
  var ctx = canvas.getContext('2d');
  var init = function() {
    grid = new Array(gridSize + 2);
    for (i = 0; i < gridSize + 2; i++) {
      grid[i] = new Array(gridSize + 2);
      for (j = 0; j < gridSize + 2; j++) {
        grid[i][j] = 3;
      }
    }
    $(window).bind('beforeunload', function() {
      if (gameId != 0) {
        resign();
        alert("You lose.");
      }
    });
    $('#join').click(function(e) {
      var user_name = $('#user_name').val();
      console.debug('user_name: ' + user_name);
      if (!(user_name)) {
        alert('Please input your name.');
        return;
      }
      join(user_name);
    });
    $('#resign').click(function(e) {
      if (window.confirm('Resign?')) {
        resign();
      }
    });
    width = margineSide * 2 + unit * (gridSize - 1);
    height = margineTop * 2 + unit * (gridSize - 1);
    canvas.onmousemove = function(e) {
      if (onMyTurn == false || player == 0) return;
      var b = adjust(e);
      if (!b) return;
      drawBackground();
      moveStone();
    }
    canvas.onmousedown = function(e) {
      if (onMyTurn == false || posX == -1 || posY == -1 || player == 0) return;
      onMyTurn = false;
      play();
      drawBackground();
    }
    drawBackground();
  };
  // join a game
  var join = function(user_name) {
    console.debug("join: " + user_name);
    $.post('/join', {
      user_name : user_name
    }, function(t) {
      token = t;
      $('#msg').text('Connect to server...');
      console.debug("join OK, token: " + token);
      var channel = new goog.appengine.Channel(token);
      socket = channel.open();
      socket.onopen = function() {
        setTimeout(function() {
          $.post('/entry', {
            token : token,
            rank : 1
          }, function(pid) {
            playerId = pid;
            $('#msg').text('Please wait for matching...');
          });
        }, 100);
      };
      socket.onmessage = function(msg) {
        var data = $.parseJSON(msg.data);
        console.debug("data: " + data.black);
        handle(data);
      };
    });
  };
  // action handler
  var handle = function(data) {
    if (data.command == 'start') {
      start(data);
    }
    update(data);
    if (data.command == 'finish') {
      finish(data);
    }
  };
  // when game is started
  var start = function(data) {
    console.debug("start: gameId=" + data.gameId + ", black=" + data.black + ", white=" + data.white);
    gameId = data.gameId;
    if (playerId == data.black) {
      player = 1;
      $('#msg').text('You are Black, have a good game!');
    } else {
      player = 2;
      $('#msg').text('You are White, have a good game!');
    }
    $('#resign').removeAttr('disabled');
  };
  // when game is finished
  var finish = function(data) {
    console.debug("finish: winner=" + data.winner);
    if (playerId == data.winner) {
      $('#msg').text('You win.');
    } else {
      $('#msg').text('You lose.');
    }
    gameId = 0;
    onMyTurn = false;
    $('#resign').attr('disabled', 'disabled');
    // socket.close();
  };
  // update game board
  var update = function(data) {
    onMyTurn = (playerId == data.player);
    console.debug("onMyTurn:" + onMyTurn);
    var len = gridSize + 2;
    for (i = 0; i < len; i++) {
      grid[i] = data.grid.slice(i * len, (i + 1) * len);
      console.debug(grid[i]);
    }
    // before stone
    black_before = data.black_before;
    white_before = data.white_before;
    $('#turn').text('Turn: ' + (data.turn + 1));
    if (player == 1) { // Black
      $('#player').text('You: Black');
      $('#player_hama').text('Hama: ' + data.black_hama);
      $('#opponent').text('Opponent: White:');
      $('#opponent_hama').text('Hama: ' + data.white_hama);
    } else { // White
      $('#player').text('You: White');
      $('#player_hama').text('Hama: ' + data.black_hama);
      $('#opponent').text('Opponent: Black:');
      $('#opponent_hama').text('Hama: ' + data.white_hama);
    }
    if (onMyTurn) {
      $('#stat').text('Your turn');
    } else {
      $('#stat').text('Opponent turn');
    }
    drawBackground();
  };
  // play
  var play = function() {
    console.debug("[" + posX + "," + posY + "]");
    grid[posY][posX] = player;
    $.post('/play', {
      gameId : gameId,
      token : token,
      x : posX,
      y : posY
    });
  };
  // resign
  var resign = function() {
    console.debug("resign");
    $.ajax({
      type : 'POST',
      url : '/resign',
      data : ({
        gameId : gameId,
        token : token
      }),
      async : false
    });
  };
  // Game board rendering
  var drawBackground = function() {
    ctx.clearRect(0, 0, canvas.width, canvas.height);
    ctx.strokeStyle = "#000000";
    ctx.fillStyle = BOARD_COLOR;
    ctx.fillRect(0, 0, width, height);
    ctx.rect(0, 0, width, height);
    ctx.beginPath();
    for (i = 0; i < gridSize; i++) {
      ctx.moveTo(margineSide, margineTop + 50 * i);
      ctx.lineTo(width - margineSide, margineTop + 50 * i);
      ctx.moveTo(margineSide + 50 * i, margineTop);
      ctx.lineTo(margineSide + 50 * i, height - margineTop);
    }
    ctx.stroke();
    ctx.strokeStyle = "#000000";
    ctx.fillStyle = "#000000";
    mark(2, 2);
    mark(2, 6);
    mark(6, 2);
    mark(6, 6);
    mark(4, 4);
    ctx.closePath();
    layoutStones();
  };
  var mark = function(x, y) {
    ctx.beginPath();
    ctx.arc(margineSide + x * unit, margineTop + y * unit, 4, 0, 2 * Math.PI, true);
    ctx.fill();
  };
  var layoutStones = function() {
    for (y = 1; y <= gridSize; y++) {
      for (x = 1; x <= gridSize; x++) {
        layoutStone(grid[y][x], x, y);
      }
    }
    ctx.font = '20px bold';
    ctx.textAlign = 'center';
    ctx.textBaseline = 'middle';
    if (black_before.length != 0) {
      ctx.strokeStyle = "#fff";
      ctx.fillStyle = "#fff";
      var x = black_before[1];
      var y = black_before[2];
      ctx.fillText(black_before[0] , margineSide + (x - 1) * unit, margineTop + (y - 1) * unit, 45);
    }
    if (white_before.length != 0) {
      ctx.strokeStyle = "#000";
      ctx.fillStyle = "#000";
      var x = white_before[1];
      var y = white_before[2];
      ctx.fillText(white_before[0] , margineSide + (x - 1) * unit, margineTop + (y - 1) * unit, 45);
    }
  };
  var layoutStone = function(color, x, y) {
    if (color == 0 || color == 3) return;
    if (color == 4) { // Eye or Ko
      if (!onMyTurn) return;
      ctx.strokeStyle = "#000000";
      ctx.fillStyle = "#ff2a00";
      mark(x - 1, y - 1);
      return;
    }
    if (color == 1) {
      ctx.fillStyle = "#000000";
      ctx.strokeStyle = "#000000";
    } else { // 2
      ctx.fillStyle = "#F3F3F3";
      ctx.strokeStyle = "#333333";
    }
    ctx.beginPath();
    ctx.arc(margineSide + (x - 1) * unit, margineTop + (y - 1) * unit, unit / 3, 0, Math.PI * 2, false);
    ctx.fill();
    ctx.stroke();
  };
  var moveStone = function() {
    if (posX == -1 || posY == -1) return;
    layoutStone(player, posX, posY);
  };
  var adjust = function(e) {
    var rect = e.target.getBoundingClientRect();
    var x = Math.floor((e.clientX - rect.left - margineSide + unit / 2) / unit) + 1;
    var y = Math.floor((e.clientY - rect.top - margineTop + unit / 2) / unit) + 1;
    if (x < 1 || gridSize < x || y < 1 || gridSize < y) {
      x = y = -1;
    }
    var oldX = posX;
    var oldY = posY;
    posX = x;
    posY = y;
    if (x != -1 && y != -1 && grid[y][x] != 0) return false;
    return (x != oldX || y != oldY);
  };
  init();
});
