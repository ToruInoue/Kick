$(document).ready(function() {
    //ここってなんとかなるんかいな。
    var channel = new goog.appengine.Channel(channelToken);
    var socket = channel.open();
    
    var updateView = function(messageContent) {
    	messageContent = decodeURI(messageContent);
    	window.alert(messageContent);
	}
    
    socket.onmessage = function(msg) {
        var data = $.parseJSON(msg.data);
        console.debug("content: " + data.content);
        updateView(data.content);
      };
      
    // Global
    myapp = {
	postMessage : function(){
	$.post(
		"message",
		{content:$('#textarea_value').val()},
		function(data,status){
			console.debug(data);
			$('#textarea_value').val('');
		},	
		'text'
		)
	}
    }

});