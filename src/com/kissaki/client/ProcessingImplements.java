package com.kissaki.client;

import com.google.gwt.core.client.JavaScriptObject;

public class  ProcessingImplements implements ProcessingInterface {
	
	private JavaScriptObject ProcessingJSObject = null; //JavaScriptObject of Processing
	
	private JavaScriptObject getInstanceOfJSObject(){
		return ProcessingJSObject;
	}
	
	
	private native JavaScriptObject setupProcessingJSObject (JavaScriptObject aElement, String aCode) /*-{
		return $wnd.Processing(aElement, aCode);
	}-*/;
	
	
	/*コンストラクタ*/
	public ProcessingImplements(JavaScriptObject aElement, String aCode){
		ProcessingJSObject = setupProcessingJSObject(aElement, aCode);
	}
	
	
	public void Processing(JavaScriptObject aElement, String aCode){
		_Processing(aElement, aCode);
	}
	private native JavaScriptObject _Processing(JavaScriptObject aElement, String aCode)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().Processing(aElement, aCode);
	}-*/;
	
	public void parse(JavaScriptObject aCode, String p){
		_parse(aCode, p);
	}
	private native JavaScriptObject _parse(JavaScriptObject aCode, String p)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().parse(aCode, p);
	}-*/;
	
	public void imageModeCorner(JavaScriptObject x, JavaScriptObject y, JavaScriptObject w, JavaScriptObject h, JavaScriptObject whAreSizes){
		_imageModeCorner(x, y, w, h, whAreSizes);
	}
	private native JavaScriptObject _imageModeCorner(JavaScriptObject x, JavaScriptObject y, JavaScriptObject w, JavaScriptObject h, JavaScriptObject whAreSizes)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().imageModeCorner(x, y, w, h, whAreSizes);
	}-*/;
	
	public void imageModeCorners(JavaScriptObject x, JavaScriptObject y, JavaScriptObject w, JavaScriptObject h, JavaScriptObject whAreSizes){
		_imageModeCorners(x, y, w, h, whAreSizes);
	}
	private native JavaScriptObject _imageModeCorners(JavaScriptObject x, JavaScriptObject y, JavaScriptObject w, JavaScriptObject h, JavaScriptObject whAreSizes)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().imageModeCorners(x, y, w, h, whAreSizes);
	}-*/;
	
	public void imageModeCenter(JavaScriptObject x, JavaScriptObject y, JavaScriptObject w, JavaScriptObject h, JavaScriptObject whAreSizes){
		_imageModeCenter(x, y, w, h, whAreSizes);
	}
	private native JavaScriptObject _imageModeCenter(JavaScriptObject x, JavaScriptObject y, JavaScriptObject w, JavaScriptObject h, JavaScriptObject whAreSizes)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().imageModeCenter(x, y, w, h, whAreSizes);
	}-*/;
	
	public void buildProcessing(String curElement){
		_buildProcessing(curElement);
	}
	private native JavaScriptObject _buildProcessing(String curElement)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().buildProcessing(curElement);
	}-*/;
	
	public void Char(String chr){
		_Char(chr);
	}
	private native JavaScriptObject _Char(String chr)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().Char(chr);
	}-*/;
	
	public void printMatrixHelper(JavaScriptObject elements){
		_printMatrixHelper(elements);
	}
	private native JavaScriptObject _printMatrixHelper(JavaScriptObject elements)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().printMatrixHelper(elements);
	}-*/;
	
	public void load(){
		_load();
	}
	private native JavaScriptObject _load()/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().load();
	}-*/;
	
	public void load(JavaScriptObject loadOverloaded_0, JavaScriptObject loadOverloaded_1){
		_load(loadOverloaded_0, loadOverloaded_1);
	}
	private native JavaScriptObject _load(JavaScriptObject loadOverloaded_0, JavaScriptObject loadOverloaded_1)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().load(loadOverloaded_0, loadOverloaded_1);
	}-*/;
	
	public void load(JavaScriptObject loadOverloaded_0){
		_load(loadOverloaded_0);
	}
	private native JavaScriptObject _load(JavaScriptObject loadOverloaded_0)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().load(loadOverloaded_0);
	}-*/;
	
	public void mult(JavaScriptObject matrix){
		_mult(matrix);
	}
	private native JavaScriptObject _mult(JavaScriptObject matrix)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().mult(matrix);
	}-*/;
	
	public void virtHashCode(JavaScriptObject obj){
		_virtHashCode(obj);
	}
	private native JavaScriptObject _virtHashCode(JavaScriptObject obj)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().virtHashCode(obj);
	}-*/;
	
	public void virtEquals(JavaScriptObject obj, JavaScriptObject other){
		_virtEquals(obj, other);
	}
	private native JavaScriptObject _virtEquals(JavaScriptObject obj, JavaScriptObject other)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().virtEquals(obj, other);
	}-*/;
	
	public void HashMap(){
		_HashMap();
	}
	private native JavaScriptObject _HashMap()/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().HashMap();
	}-*/;
	
	public void HashMap(JavaScriptObject HashMapOverloaded_0, JavaScriptObject HashMapOverloaded_1){
		_HashMap(HashMapOverloaded_0, HashMapOverloaded_1);
	}
	private native JavaScriptObject _HashMap(JavaScriptObject HashMapOverloaded_0, JavaScriptObject HashMapOverloaded_1)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().HashMap(HashMapOverloaded_0, HashMapOverloaded_1);
	}-*/;
	
	public void HashMap(JavaScriptObject HashMapOverloaded_0){
		_HashMap(HashMapOverloaded_0);
	}
	private native JavaScriptObject _HashMap(JavaScriptObject HashMapOverloaded_0)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().HashMap(HashMapOverloaded_0);
	}-*/;
	
	public void Iterator(String conversion, String removeItem){
		_Iterator(conversion, removeItem);
	}
	private native JavaScriptObject _Iterator(String conversion, String removeItem)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().Iterator(conversion, removeItem);
	}-*/;
	
	public void Set(JavaScriptObject conversion, JavaScriptObject isIn, JavaScriptObject removeItem){
		_Set(conversion, isIn, removeItem);
	}
	private native JavaScriptObject _Set(JavaScriptObject conversion, JavaScriptObject isIn, JavaScriptObject removeItem)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().Set(conversion, isIn, removeItem);
	}-*/;
	
	public void Entry(JavaScriptObject pair){
		_Entry(pair);
	}
	private native JavaScriptObject _Entry(JavaScriptObject pair)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().Entry(pair);
	}-*/;
	
	public void color(JavaScriptObject aValue1, JavaScriptObject aValue2, JavaScriptObject aValue3, JavaScriptObject aValue4){
		_color(aValue1, aValue2, aValue3, aValue4);
	}
	private native JavaScriptObject _color(JavaScriptObject aValue1, JavaScriptObject aValue2, JavaScriptObject aValue3, JavaScriptObject aValue4)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().color(aValue1, aValue2, aValue3, aValue4);
	}-*/;
	
	public void verifyChannel(JavaScriptObject aColor){
		_verifyChannel(aColor);
	}
	private native JavaScriptObject _verifyChannel(JavaScriptObject aColor)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().verifyChannel(aColor);
	}-*/;
	
	public void lerpColor(JavaScriptObject c1, JavaScriptObject c2, JavaScriptObject amt){
		_lerpColor(c1, c2, amt);
	}
	private native JavaScriptObject _lerpColor(JavaScriptObject c1, JavaScriptObject c2, JavaScriptObject amt)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().lerpColor(c1, c2, amt);
	}-*/;
	
	public void colorMode(String mode, String range1, String range2, String range3, String range4){
		_colorMode(mode, range1, range2, range3, range4);
	}
	private native JavaScriptObject _colorMode(String mode, String range1, String range2, String range3, String range4)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().colorMode(mode, range1, range2, range3, range4);
	}-*/;
	
	public void colorMode(JavaScriptObject colorModeOverloaded_0, JavaScriptObject colorModeOverloaded_1, JavaScriptObject colorModeOverloaded_2, JavaScriptObject colorModeOverloaded_3, JavaScriptObject colorModeOverloaded_4, JavaScriptObject colorModeOverloaded_5){
		_colorMode(colorModeOverloaded_0, colorModeOverloaded_1, colorModeOverloaded_2, colorModeOverloaded_3, colorModeOverloaded_4, colorModeOverloaded_5);
	}
	private native JavaScriptObject _colorMode(JavaScriptObject colorModeOverloaded_0, JavaScriptObject colorModeOverloaded_1, JavaScriptObject colorModeOverloaded_2, JavaScriptObject colorModeOverloaded_3, JavaScriptObject colorModeOverloaded_4, JavaScriptObject colorModeOverloaded_5)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().colorMode(colorModeOverloaded_0, colorModeOverloaded_1, colorModeOverloaded_2, colorModeOverloaded_3, colorModeOverloaded_4, colorModeOverloaded_5);
	}-*/;
	
	public void colorMode(JavaScriptObject colorModeOverloaded_0, JavaScriptObject colorModeOverloaded_1, JavaScriptObject colorModeOverloaded_2){
		_colorMode(colorModeOverloaded_0, colorModeOverloaded_1, colorModeOverloaded_2);
	}
	private native JavaScriptObject _colorMode(JavaScriptObject colorModeOverloaded_0, JavaScriptObject colorModeOverloaded_1, JavaScriptObject colorModeOverloaded_2)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().colorMode(colorModeOverloaded_0, colorModeOverloaded_1, colorModeOverloaded_2);
	}-*/;
	
	public void translate(JavaScriptObject x, JavaScriptObject y, JavaScriptObject z){
		_translate(x, y, z);
	}
	private native JavaScriptObject _translate(JavaScriptObject x, JavaScriptObject y, JavaScriptObject z)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().translate(x, y, z);
	}-*/;
	
	public void scale(JavaScriptObject x, JavaScriptObject y, JavaScriptObject z){
		_scale(x, y, z);
	}
	private native JavaScriptObject _scale(JavaScriptObject x, JavaScriptObject y, JavaScriptObject z)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().scale(x, y, z);
	}-*/;
	
	public void applyMatrix(){
		_applyMatrix();
	}
	private native JavaScriptObject _applyMatrix()/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().applyMatrix();
	}-*/;
	
	public void applyMatrix(JavaScriptObject applyMatrixOverloaded_0){
		_applyMatrix(applyMatrixOverloaded_0);
	}
	private native JavaScriptObject _applyMatrix(JavaScriptObject applyMatrixOverloaded_0)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().applyMatrix(applyMatrixOverloaded_0);
	}-*/;
	
	public void rotate(JavaScriptObject angleInRadians){
		_rotate(angleInRadians);
	}
	private native JavaScriptObject _rotate(JavaScriptObject angleInRadians)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().rotate(angleInRadians);
	}-*/;
	
	public void loop(){
		_loop();
	}
	private native JavaScriptObject _loop()/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().loop();
	}-*/;
	
	public void frameRate(String aRate){
		_frameRate(aRate);
	}
	private native JavaScriptObject _frameRate(String aRate)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().frameRate(aRate);
	}-*/;
	
	public void cursor(){
		_cursor();
	}
	private native JavaScriptObject _cursor()/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().cursor();
	}-*/;
	
	public void cursor(JavaScriptObject cursorOverloaded_0, JavaScriptObject cursorOverloaded_1){
		_cursor(cursorOverloaded_0, cursorOverloaded_1);
	}
	private native JavaScriptObject _cursor(JavaScriptObject cursorOverloaded_0, JavaScriptObject cursorOverloaded_1)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().cursor(cursorOverloaded_0, cursorOverloaded_1);
	}-*/;
	
	public void cursor(JavaScriptObject cursorOverloaded_0){
		_cursor(cursorOverloaded_0);
	}
	private native JavaScriptObject _cursor(JavaScriptObject cursorOverloaded_0)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().cursor(cursorOverloaded_0);
	}-*/;
	
	public void cursor(JavaScriptObject cursorOverloaded_0, JavaScriptObject cursorOverloaded_1, JavaScriptObject cursorOverloaded_2, JavaScriptObject cursorOverloaded_3){
		_cursor(cursorOverloaded_0, cursorOverloaded_1, cursorOverloaded_2, cursorOverloaded_3);
	}
	private native JavaScriptObject _cursor(JavaScriptObject cursorOverloaded_0, JavaScriptObject cursorOverloaded_1, JavaScriptObject cursorOverloaded_2, JavaScriptObject cursorOverloaded_3)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().cursor(cursorOverloaded_0, cursorOverloaded_1, cursorOverloaded_2, cursorOverloaded_3);
	}-*/;
	
	public void cursor(JavaScriptObject cursorOverloaded_0, JavaScriptObject cursorOverloaded_1, JavaScriptObject cursorOverloaded_2){
		_cursor(cursorOverloaded_0, cursorOverloaded_1, cursorOverloaded_2);
	}
	private native JavaScriptObject _cursor(JavaScriptObject cursorOverloaded_0, JavaScriptObject cursorOverloaded_1, JavaScriptObject cursorOverloaded_2)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().cursor(cursorOverloaded_0, cursorOverloaded_1, cursorOverloaded_2);
	}-*/;
	
	public void Import(JavaScriptObject lib){
		_Import(lib);
	}
	private native JavaScriptObject _Import(JavaScriptObject lib)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().Import(lib);
	}-*/;
	
	public void decToBin(JavaScriptObject value, JavaScriptObject numBitsInValue){
		_decToBin(value, numBitsInValue);
	}
	private native JavaScriptObject _decToBin(JavaScriptObject value, JavaScriptObject numBitsInValue)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().decToBin(value, numBitsInValue);
	}-*/;
	
	public void unbinary(String binaryString){
		_unbinary(binaryString);
	}
	private native JavaScriptObject _unbinary(String binaryString)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().unbinary(binaryString);
	}-*/;
	
	public void unbinary(JavaScriptObject unbinaryOverloaded_0, JavaScriptObject unbinaryOverloaded_1){
		_unbinary(unbinaryOverloaded_0, unbinaryOverloaded_1);
	}
	private native JavaScriptObject _unbinary(JavaScriptObject unbinaryOverloaded_0, JavaScriptObject unbinaryOverloaded_1)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().unbinary(unbinaryOverloaded_0, unbinaryOverloaded_1);
	}-*/;
	
	public void decimalToHex(JavaScriptObject d, JavaScriptObject padding){
		_decimalToHex(d, padding);
	}
	private native JavaScriptObject _decimalToHex(JavaScriptObject d, JavaScriptObject padding)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().decimalToHex(d, padding);
	}-*/;
	
	public void hex(JavaScriptObject value, JavaScriptObject len){
		_hex(value, len);
	}
	private native JavaScriptObject _hex(JavaScriptObject value, JavaScriptObject len)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().hex(value, len);
	}-*/;
	
	public void loadStrings(JavaScriptObject url){
		_loadStrings(url);
	}
	private native JavaScriptObject _loadStrings(JavaScriptObject url)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().loadStrings(url);
	}-*/;
	
	public void loadBytes(JavaScriptObject url){
		_loadBytes(url);
	}
	private native JavaScriptObject _loadBytes(JavaScriptObject url)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().loadBytes(url);
	}-*/;
	
	public void matchAll(JavaScriptObject aString, JavaScriptObject aRegExp){
		_matchAll(aString, aRegExp);
	}
	private native JavaScriptObject _matchAll(JavaScriptObject aString, JavaScriptObject aRegExp)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().matchAll(aString, aRegExp);
	}-*/;
	
	public void equals(JavaScriptObject str){
		_equals(str);
	}
	private native JavaScriptObject _equals(JavaScriptObject str)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().equals(str);
	}-*/;
	
	public void println(JavaScriptObject message){
		_println(message);
	}
	private native JavaScriptObject _println(JavaScriptObject message)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().println(message);
	}-*/;
	
	public void print(JavaScriptObject message){
		_print(message);
	}
	private native JavaScriptObject _print(JavaScriptObject message)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().print(message);
	}-*/;
	
	public void str(String val){
		_str(val);
	}
	private native JavaScriptObject _str(String val)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().str(val);
	}-*/;
	
	public void str(JavaScriptObject strOverloaded_0, JavaScriptObject strOverloaded_1){
		_str(strOverloaded_0, strOverloaded_1);
	}
	private native JavaScriptObject _str(JavaScriptObject strOverloaded_0, JavaScriptObject strOverloaded_1)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().str(strOverloaded_0, strOverloaded_1);
	}-*/;
	
	public void random(JavaScriptObject aMin, JavaScriptObject aMax){
		_random(aMin, aMax);
	}
	private native JavaScriptObject _random(JavaScriptObject aMin, JavaScriptObject aMax)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().random(aMin, aMax);
	}-*/;
	
	public void random(JavaScriptObject randomOverloaded_0, JavaScriptObject randomOverloaded_1, JavaScriptObject randomOverloaded_2){
		_random(randomOverloaded_0, randomOverloaded_1, randomOverloaded_2);
	}
	private native JavaScriptObject _random(JavaScriptObject randomOverloaded_0, JavaScriptObject randomOverloaded_1, JavaScriptObject randomOverloaded_2)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().random(randomOverloaded_0, randomOverloaded_1, randomOverloaded_2);
	}-*/;
	
	public void noiseGen(JavaScriptObject x, JavaScriptObject y){
		_noiseGen(x, y);
	}
	private native JavaScriptObject _noiseGen(JavaScriptObject x, JavaScriptObject y)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().noiseGen(x, y);
	}-*/;
	
	public void smoothedNoise(JavaScriptObject x, JavaScriptObject y){
		_smoothedNoise(x, y);
	}
	private native JavaScriptObject _smoothedNoise(JavaScriptObject x, JavaScriptObject y)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().smoothedNoise(x, y);
	}-*/;
	
	public void interpolate(JavaScriptObject a, JavaScriptObject b, JavaScriptObject x){
		_interpolate(a, b, x);
	}
	private native JavaScriptObject _interpolate(JavaScriptObject a, JavaScriptObject b, JavaScriptObject x)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().interpolate(a, b, x);
	}-*/;
	
	public void interpolatedNoise(JavaScriptObject x, JavaScriptObject y){
		_interpolatedNoise(x, y);
	}
	private native JavaScriptObject _interpolatedNoise(JavaScriptObject x, JavaScriptObject y)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().interpolatedNoise(x, y);
	}-*/;
	
	public void perlinNoise_2D(JavaScriptObject x, JavaScriptObject y){
		_perlinNoise_2D(x, y);
	}
	private native JavaScriptObject _perlinNoise_2D(JavaScriptObject x, JavaScriptObject y)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().perlinNoise_2D(x, y);
	}-*/;
	
	public void size(String aWidth, String aHeight, String aMode){
		_size(aWidth, aHeight, aMode);
	}
	private native JavaScriptObject _size(String aWidth, String aHeight, String aMode)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().size(aWidth, aHeight, aMode);
	}-*/;
	
	public void uniformf(JavaScriptObject programObj, JavaScriptObject varName, JavaScriptObject varValue){
		_uniformf(programObj, varName, varValue);
	}
	private native JavaScriptObject _uniformf(JavaScriptObject programObj, JavaScriptObject varName, JavaScriptObject varValue)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().uniformf(programObj, varName, varValue);
	}-*/;
	
	public void uniformi(JavaScriptObject programObj, JavaScriptObject varName, JavaScriptObject varValue){
		_uniformi(programObj, varName, varValue);
	}
	private native JavaScriptObject _uniformi(JavaScriptObject programObj, JavaScriptObject varName, JavaScriptObject varValue)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().uniformi(programObj, varName, varValue);
	}-*/;
	
	public void vertexAttribPointer(JavaScriptObject programObj, JavaScriptObject varName, JavaScriptObject size, JavaScriptObject VBO){
		_vertexAttribPointer(programObj, varName, size, VBO);
	}
	private native JavaScriptObject _vertexAttribPointer(JavaScriptObject programObj, JavaScriptObject varName, JavaScriptObject size, JavaScriptObject VBO)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().vertexAttribPointer(programObj, varName, size, VBO);
	}-*/;
	
	public void uniformMatrix(JavaScriptObject programObj, JavaScriptObject varName, JavaScriptObject transpose, JavaScriptObject matrix){
		_uniformMatrix(programObj, varName, transpose, matrix);
	}
	private native JavaScriptObject _uniformMatrix(JavaScriptObject programObj, JavaScriptObject varName, JavaScriptObject transpose, JavaScriptObject matrix)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().uniformMatrix(programObj, varName, transpose, matrix);
	}-*/;
	
	public void lightFalloff(JavaScriptObject constant, JavaScriptObject linear, JavaScriptObject quadratic){
		_lightFalloff(constant, linear, quadratic);
	}
	private native JavaScriptObject _lightFalloff(JavaScriptObject constant, JavaScriptObject linear, JavaScriptObject quadratic)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().lightFalloff(constant, linear, quadratic);
	}-*/;
	
	public void lightSpecular(JavaScriptObject r, JavaScriptObject g, JavaScriptObject b){
		_lightSpecular(r, g, b);
	}
	private native JavaScriptObject _lightSpecular(JavaScriptObject r, JavaScriptObject g, JavaScriptObject b)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().lightSpecular(r, g, b);
	}-*/;
	
	public void spotLight(JavaScriptObject r, JavaScriptObject g, JavaScriptObject b, JavaScriptObject x, JavaScriptObject y, JavaScriptObject z, JavaScriptObject nx, JavaScriptObject ny, JavaScriptObject nz, JavaScriptObject angle, JavaScriptObject concentration){
		_spotLight(r, g, b, x, y, z, nx, ny, nz, angle, concentration);
	}
	private native JavaScriptObject _spotLight(JavaScriptObject r, JavaScriptObject g, JavaScriptObject b, JavaScriptObject x, JavaScriptObject y, JavaScriptObject z, JavaScriptObject nx, JavaScriptObject ny, JavaScriptObject nz, JavaScriptObject angle, JavaScriptObject concentration)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().spotLight(r, g, b, x, y, z, nx, ny, nz, angle, concentration);
	}-*/;
	
	public void camera(String eyeX, String eyeY, String eyeZ, String centerX, String centerY, String centerZ, String upX, String upY, String upZ){
		_camera(eyeX, eyeY, eyeZ, centerX, centerY, centerZ, upX, upY, upZ);
	}
	private native JavaScriptObject _camera(String eyeX, String eyeY, String eyeZ, String centerX, String centerY, String centerZ, String upX, String upY, String upZ)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().camera(eyeX, eyeY, eyeZ, centerX, centerY, centerZ, upX, upY, upZ);
	}-*/;
	
	public void camera(JavaScriptObject cameraOverloaded_0){
		_camera(cameraOverloaded_0);
	}
	private native JavaScriptObject _camera(JavaScriptObject cameraOverloaded_0)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().camera(cameraOverloaded_0);
	}-*/;
	
	public void perspective(String fov, String aspect, String near, String far){
		_perspective(fov, aspect, near, far);
	}
	private native JavaScriptObject _perspective(String fov, String aspect, String near, String far)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().perspective(fov, aspect, near, far);
	}-*/;
	
	public void perspective(JavaScriptObject perspectiveOverloaded_0){
		_perspective(perspectiveOverloaded_0);
	}
	private native JavaScriptObject _perspective(JavaScriptObject perspectiveOverloaded_0)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().perspective(perspectiveOverloaded_0);
	}-*/;
	
	public void perspective(JavaScriptObject perspectiveOverloaded_0, JavaScriptObject perspectiveOverloaded_1, JavaScriptObject perspectiveOverloaded_2){
		_perspective(perspectiveOverloaded_0, perspectiveOverloaded_1, perspectiveOverloaded_2);
	}
	private native JavaScriptObject _perspective(JavaScriptObject perspectiveOverloaded_0, JavaScriptObject perspectiveOverloaded_1, JavaScriptObject perspectiveOverloaded_2)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().perspective(perspectiveOverloaded_0, perspectiveOverloaded_1, perspectiveOverloaded_2);
	}-*/;
	
	public void frustum(JavaScriptObject left, JavaScriptObject right, String bottom, String top, JavaScriptObject near, JavaScriptObject far){
		_frustum(left, right, bottom, top, near, far);
	}
	private native JavaScriptObject _frustum(JavaScriptObject left, JavaScriptObject right, String bottom, String top, JavaScriptObject near, JavaScriptObject far)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().frustum(left, right, bottom, top, near, far);
	}-*/;
	
	public void ortho(JavaScriptObject left, JavaScriptObject right, JavaScriptObject bottom, JavaScriptObject top, JavaScriptObject near, JavaScriptObject far){
		_ortho(left, right, bottom, top, near, far);
	}
	private native JavaScriptObject _ortho(JavaScriptObject left, JavaScriptObject right, JavaScriptObject bottom, JavaScriptObject top, JavaScriptObject near, JavaScriptObject far)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().ortho(left, right, bottom, top, near, far);
	}-*/;
	
	public void ortho(JavaScriptObject orthoOverloaded_0){
		_ortho(orthoOverloaded_0);
	}
	private native JavaScriptObject _ortho(JavaScriptObject orthoOverloaded_0)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().ortho(orthoOverloaded_0);
	}-*/;
	
	public void sphereDetail(String ures, String vres){
		_sphereDetail(ures, vres);
	}
	private native JavaScriptObject _sphereDetail(String ures, String vres)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().sphereDetail(ures, vres);
	}-*/;
	
	public void sphereDetail(JavaScriptObject sphereDetailOverloaded_0){
		_sphereDetail(sphereDetailOverloaded_0);
	}
	private native JavaScriptObject _sphereDetail(JavaScriptObject sphereDetailOverloaded_0)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().sphereDetail(sphereDetailOverloaded_0);
	}-*/;
	
	public void modelX(JavaScriptObject x, JavaScriptObject y, JavaScriptObject z){
		_modelX(x, y, z);
	}
	private native JavaScriptObject _modelX(JavaScriptObject x, JavaScriptObject y, JavaScriptObject z)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().modelX(x, y, z);
	}-*/;
	
	public void modelY(JavaScriptObject x, JavaScriptObject y, JavaScriptObject z){
		_modelY(x, y, z);
	}
	private native JavaScriptObject _modelY(JavaScriptObject x, JavaScriptObject y, JavaScriptObject z)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().modelY(x, y, z);
	}-*/;
	
	public void modelZ(JavaScriptObject x, JavaScriptObject y, JavaScriptObject z){
		_modelZ(x, y, z);
	}
	private native JavaScriptObject _modelZ(JavaScriptObject x, JavaScriptObject y, JavaScriptObject z)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().modelZ(x, y, z);
	}-*/;
	
	public void ambient(){
		_ambient();
	}
	private native JavaScriptObject _ambient()/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().ambient();
	}-*/;
	
	public void ambient(JavaScriptObject ambientOverloaded_0, JavaScriptObject ambientOverloaded_1){
		_ambient(ambientOverloaded_0, ambientOverloaded_1);
	}
	private native JavaScriptObject _ambient(JavaScriptObject ambientOverloaded_0, JavaScriptObject ambientOverloaded_1)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().ambient(ambientOverloaded_0, ambientOverloaded_1);
	}-*/;
	
	public void emissive(){
		_emissive();
	}
	private native JavaScriptObject _emissive()/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().emissive();
	}-*/;
	
	public void emissive(JavaScriptObject emissiveOverloaded_0, JavaScriptObject emissiveOverloaded_1){
		_emissive(emissiveOverloaded_0, emissiveOverloaded_1);
	}
	private native JavaScriptObject _emissive(JavaScriptObject emissiveOverloaded_0, JavaScriptObject emissiveOverloaded_1)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().emissive(emissiveOverloaded_0, emissiveOverloaded_1);
	}-*/;
	
	public void shininess(JavaScriptObject shine){
		_shininess(shine);
	}
	private native JavaScriptObject _shininess(JavaScriptObject shine)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().shininess(shine);
	}-*/;
	
	public void specular(){
		_specular();
	}
	private native JavaScriptObject _specular()/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().specular();
	}-*/;
	
	public void specular(JavaScriptObject specularOverloaded_0){
		_specular(specularOverloaded_0);
	}
	private native JavaScriptObject _specular(JavaScriptObject specularOverloaded_0)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().specular(specularOverloaded_0);
	}-*/;
	
	public void fill(){
		_fill();
	}
	private native JavaScriptObject _fill()/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().fill();
	}-*/;
	
	public void fill(JavaScriptObject fillOverloaded_0){
		_fill(fillOverloaded_0);
	}
	private native JavaScriptObject _fill(JavaScriptObject fillOverloaded_0)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().fill(fillOverloaded_0);
	}-*/;
	
	public void fill(JavaScriptObject fillOverloaded_0, JavaScriptObject fillOverloaded_1){
		_fill(fillOverloaded_0, fillOverloaded_1);
	}
	private native JavaScriptObject _fill(JavaScriptObject fillOverloaded_0, JavaScriptObject fillOverloaded_1)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().fill(fillOverloaded_0, fillOverloaded_1);
	}-*/;
	
	public void fill(JavaScriptObject fillOverloaded_0, JavaScriptObject fillOverloaded_1, JavaScriptObject fillOverloaded_2){
		_fill(fillOverloaded_0, fillOverloaded_1, fillOverloaded_2);
	}
	private native JavaScriptObject _fill(JavaScriptObject fillOverloaded_0, JavaScriptObject fillOverloaded_1, JavaScriptObject fillOverloaded_2)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().fill(fillOverloaded_0, fillOverloaded_1, fillOverloaded_2);
	}-*/;
	
	public void fill(JavaScriptObject fillOverloaded_0, JavaScriptObject fillOverloaded_1, JavaScriptObject fillOverloaded_2, JavaScriptObject fillOverloaded_3){
		_fill(fillOverloaded_0, fillOverloaded_1, fillOverloaded_2, fillOverloaded_3);
	}
	private native JavaScriptObject _fill(JavaScriptObject fillOverloaded_0, JavaScriptObject fillOverloaded_1, JavaScriptObject fillOverloaded_2, JavaScriptObject fillOverloaded_3)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().fill(fillOverloaded_0, fillOverloaded_1, fillOverloaded_2, fillOverloaded_3);
	}-*/;
	
	public void stroke(){
		_stroke();
	}
	private native JavaScriptObject _stroke()/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().stroke();
	}-*/;
	
	public void stroke(JavaScriptObject strokeOverloaded_0){
		_stroke(strokeOverloaded_0);
	}
	private native JavaScriptObject _stroke(JavaScriptObject strokeOverloaded_0)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().stroke(strokeOverloaded_0);
	}-*/;
	
	public void stroke(JavaScriptObject strokeOverloaded_0, JavaScriptObject strokeOverloaded_1){
		_stroke(strokeOverloaded_0, strokeOverloaded_1);
	}
	private native JavaScriptObject _stroke(JavaScriptObject strokeOverloaded_0, JavaScriptObject strokeOverloaded_1)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().stroke(strokeOverloaded_0, strokeOverloaded_1);
	}-*/;
	
	public void stroke(JavaScriptObject strokeOverloaded_0, JavaScriptObject strokeOverloaded_1, JavaScriptObject strokeOverloaded_2){
		_stroke(strokeOverloaded_0, strokeOverloaded_1, strokeOverloaded_2);
	}
	private native JavaScriptObject _stroke(JavaScriptObject strokeOverloaded_0, JavaScriptObject strokeOverloaded_1, JavaScriptObject strokeOverloaded_2)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().stroke(strokeOverloaded_0, strokeOverloaded_1, strokeOverloaded_2);
	}-*/;
	
	public void stroke(JavaScriptObject strokeOverloaded_0, JavaScriptObject strokeOverloaded_1, JavaScriptObject strokeOverloaded_2, JavaScriptObject strokeOverloaded_3){
		_stroke(strokeOverloaded_0, strokeOverloaded_1, strokeOverloaded_2, strokeOverloaded_3);
	}
	private native JavaScriptObject _stroke(JavaScriptObject strokeOverloaded_0, JavaScriptObject strokeOverloaded_1, JavaScriptObject strokeOverloaded_2, JavaScriptObject strokeOverloaded_3)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().stroke(strokeOverloaded_0, strokeOverloaded_1, strokeOverloaded_2, strokeOverloaded_3);
	}-*/;
	
	public void strokeWeight(JavaScriptObject w){
		_strokeWeight(w);
	}
	private native JavaScriptObject _strokeWeight(JavaScriptObject w)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().strokeWeight(w);
	}-*/;
	
	public void strokeCap(JavaScriptObject value){
		_strokeCap(value);
	}
	private native JavaScriptObject _strokeCap(JavaScriptObject value)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().strokeCap(value);
	}-*/;
	
	public void strokeJoin(JavaScriptObject value){
		_strokeJoin(value);
	}
	private native JavaScriptObject _strokeJoin(JavaScriptObject value)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().strokeJoin(value);
	}-*/;
	
	public void Point(JavaScriptObject x, JavaScriptObject y){
		_Point(x, y);
	}
	private native JavaScriptObject _Point(JavaScriptObject x, JavaScriptObject y)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().Point(x, y);
	}-*/;
	
	public void point(JavaScriptObject x, JavaScriptObject y, JavaScriptObject z){
		_point(x, y, z);
	}
	private native JavaScriptObject _point(JavaScriptObject x, JavaScriptObject y, JavaScriptObject z)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().point(x, y, z);
	}-*/;
	
	public void beginShape(JavaScriptObject type){
		_beginShape(type);
	}
	private native JavaScriptObject _beginShape(JavaScriptObject type)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().beginShape(type);
	}-*/;
	
	public void endShape(JavaScriptObject close){
		_endShape(close);
	}
	private native JavaScriptObject _endShape(JavaScriptObject close)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().endShape(close);
	}-*/;
	
	public void vertex(JavaScriptObject x, JavaScriptObject y, String x2, String y2, String x3, String y3){
		_vertex(x, y, x2, y2, x3, y3);
	}
	private native JavaScriptObject _vertex(JavaScriptObject x, JavaScriptObject y, String x2, String y2, String x3, String y3)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().vertex(x, y, x2, y2, x3, y3);
	}-*/;
	
	public void vertex(JavaScriptObject vertexOverloaded_0, JavaScriptObject vertexOverloaded_1, JavaScriptObject vertexOverloaded_2){
		_vertex(vertexOverloaded_0, vertexOverloaded_1, vertexOverloaded_2);
	}
	private native JavaScriptObject _vertex(JavaScriptObject vertexOverloaded_0, JavaScriptObject vertexOverloaded_1, JavaScriptObject vertexOverloaded_2)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().vertex(vertexOverloaded_0, vertexOverloaded_1, vertexOverloaded_2);
	}-*/;
	
	public void vertex(JavaScriptObject vertexOverloaded_0, JavaScriptObject vertexOverloaded_1, JavaScriptObject vertexOverloaded_2, JavaScriptObject vertexOverloaded_3){
		_vertex(vertexOverloaded_0, vertexOverloaded_1, vertexOverloaded_2, vertexOverloaded_3);
	}
	private native JavaScriptObject _vertex(JavaScriptObject vertexOverloaded_0, JavaScriptObject vertexOverloaded_1, JavaScriptObject vertexOverloaded_2, JavaScriptObject vertexOverloaded_3)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().vertex(vertexOverloaded_0, vertexOverloaded_1, vertexOverloaded_2, vertexOverloaded_3);
	}-*/;
	
	public void vertex(JavaScriptObject vertexOverloaded_0){
		_vertex(vertexOverloaded_0);
	}
	private native JavaScriptObject _vertex(JavaScriptObject vertexOverloaded_0)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().vertex(vertexOverloaded_0);
	}-*/;
	
	public void vertex(JavaScriptObject vertexOverloaded_0, JavaScriptObject vertexOverloaded_1){
		_vertex(vertexOverloaded_0, vertexOverloaded_1);
	}
	private native JavaScriptObject _vertex(JavaScriptObject vertexOverloaded_0, JavaScriptObject vertexOverloaded_1)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().vertex(vertexOverloaded_0, vertexOverloaded_1);
	}-*/;
	
	public void vertex(JavaScriptObject vertexOverloaded_0, JavaScriptObject vertexOverloaded_1, JavaScriptObject vertexOverloaded_2, JavaScriptObject vertexOverloaded_3, JavaScriptObject vertexOverloaded_4){
		_vertex(vertexOverloaded_0, vertexOverloaded_1, vertexOverloaded_2, vertexOverloaded_3, vertexOverloaded_4);
	}
	private native JavaScriptObject _vertex(JavaScriptObject vertexOverloaded_0, JavaScriptObject vertexOverloaded_1, JavaScriptObject vertexOverloaded_2, JavaScriptObject vertexOverloaded_3, JavaScriptObject vertexOverloaded_4)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().vertex(vertexOverloaded_0, vertexOverloaded_1, vertexOverloaded_2, vertexOverloaded_3, vertexOverloaded_4);
	}-*/;
	
	public void vertex(JavaScriptObject vertexOverloaded_0, JavaScriptObject vertexOverloaded_1, JavaScriptObject vertexOverloaded_2, JavaScriptObject vertexOverloaded_3, JavaScriptObject vertexOverloaded_4, JavaScriptObject vertexOverloaded_5, JavaScriptObject vertexOverloaded_6){
		_vertex(vertexOverloaded_0, vertexOverloaded_1, vertexOverloaded_2, vertexOverloaded_3, vertexOverloaded_4, vertexOverloaded_5, vertexOverloaded_6);
	}
	private native JavaScriptObject _vertex(JavaScriptObject vertexOverloaded_0, JavaScriptObject vertexOverloaded_1, JavaScriptObject vertexOverloaded_2, JavaScriptObject vertexOverloaded_3, JavaScriptObject vertexOverloaded_4, JavaScriptObject vertexOverloaded_5, JavaScriptObject vertexOverloaded_6)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().vertex(vertexOverloaded_0, vertexOverloaded_1, vertexOverloaded_2, vertexOverloaded_3, vertexOverloaded_4, vertexOverloaded_5, vertexOverloaded_6);
	}-*/;
	
	public void curve(){
		_curve();
	}
	private native JavaScriptObject _curve()/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().curve();
	}-*/;
	
	public void curve(JavaScriptObject curveOverloaded_0, JavaScriptObject curveOverloaded_1, JavaScriptObject curveOverloaded_2, JavaScriptObject curveOverloaded_3, JavaScriptObject curveOverloaded_4, JavaScriptObject curveOverloaded_5, JavaScriptObject curveOverloaded_6, JavaScriptObject curveOverloaded_7, JavaScriptObject curveOverloaded_8){
		_curve(curveOverloaded_0, curveOverloaded_1, curveOverloaded_2, curveOverloaded_3, curveOverloaded_4, curveOverloaded_5, curveOverloaded_6, curveOverloaded_7, curveOverloaded_8);
	}
	private native JavaScriptObject _curve(JavaScriptObject curveOverloaded_0, JavaScriptObject curveOverloaded_1, JavaScriptObject curveOverloaded_2, JavaScriptObject curveOverloaded_3, JavaScriptObject curveOverloaded_4, JavaScriptObject curveOverloaded_5, JavaScriptObject curveOverloaded_6, JavaScriptObject curveOverloaded_7, JavaScriptObject curveOverloaded_8)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().curve(curveOverloaded_0, curveOverloaded_1, curveOverloaded_2, curveOverloaded_3, curveOverloaded_4, curveOverloaded_5, curveOverloaded_6, curveOverloaded_7, curveOverloaded_8);
	}-*/;
	
	public void curve(JavaScriptObject curveOverloaded_0){
		_curve(curveOverloaded_0);
	}
	private native JavaScriptObject _curve(JavaScriptObject curveOverloaded_0)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().curve(curveOverloaded_0);
	}-*/;
	
	public void curve(JavaScriptObject curveOverloaded_0, JavaScriptObject curveOverloaded_1){
		_curve(curveOverloaded_0, curveOverloaded_1);
	}
	private native JavaScriptObject _curve(JavaScriptObject curveOverloaded_0, JavaScriptObject curveOverloaded_1)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().curve(curveOverloaded_0, curveOverloaded_1);
	}-*/;
	
	public void curve(JavaScriptObject curveOverloaded_0, JavaScriptObject curveOverloaded_1, JavaScriptObject curveOverloaded_2){
		_curve(curveOverloaded_0, curveOverloaded_1, curveOverloaded_2);
	}
	private native JavaScriptObject _curve(JavaScriptObject curveOverloaded_0, JavaScriptObject curveOverloaded_1, JavaScriptObject curveOverloaded_2)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().curve(curveOverloaded_0, curveOverloaded_1, curveOverloaded_2);
	}-*/;
	
	public void curve(JavaScriptObject curveOverloaded_0, JavaScriptObject curveOverloaded_1, JavaScriptObject curveOverloaded_2, JavaScriptObject curveOverloaded_3){
		_curve(curveOverloaded_0, curveOverloaded_1, curveOverloaded_2, curveOverloaded_3);
	}
	private native JavaScriptObject _curve(JavaScriptObject curveOverloaded_0, JavaScriptObject curveOverloaded_1, JavaScriptObject curveOverloaded_2, JavaScriptObject curveOverloaded_3)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().curve(curveOverloaded_0, curveOverloaded_1, curveOverloaded_2, curveOverloaded_3);
	}-*/;
	
	public void curve(JavaScriptObject curveOverloaded_0, JavaScriptObject curveOverloaded_1, JavaScriptObject curveOverloaded_2, JavaScriptObject curveOverloaded_3, JavaScriptObject curveOverloaded_4){
		_curve(curveOverloaded_0, curveOverloaded_1, curveOverloaded_2, curveOverloaded_3, curveOverloaded_4);
	}
	private native JavaScriptObject _curve(JavaScriptObject curveOverloaded_0, JavaScriptObject curveOverloaded_1, JavaScriptObject curveOverloaded_2, JavaScriptObject curveOverloaded_3, JavaScriptObject curveOverloaded_4)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().curve(curveOverloaded_0, curveOverloaded_1, curveOverloaded_2, curveOverloaded_3, curveOverloaded_4);
	}-*/;
	
	public void curve(JavaScriptObject curveOverloaded_0, JavaScriptObject curveOverloaded_1, JavaScriptObject curveOverloaded_2, JavaScriptObject curveOverloaded_3, JavaScriptObject curveOverloaded_4, JavaScriptObject curveOverloaded_5){
		_curve(curveOverloaded_0, curveOverloaded_1, curveOverloaded_2, curveOverloaded_3, curveOverloaded_4, curveOverloaded_5);
	}
	private native JavaScriptObject _curve(JavaScriptObject curveOverloaded_0, JavaScriptObject curveOverloaded_1, JavaScriptObject curveOverloaded_2, JavaScriptObject curveOverloaded_3, JavaScriptObject curveOverloaded_4, JavaScriptObject curveOverloaded_5)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().curve(curveOverloaded_0, curveOverloaded_1, curveOverloaded_2, curveOverloaded_3, curveOverloaded_4, curveOverloaded_5);
	}-*/;
	
	public void curve(JavaScriptObject curveOverloaded_0, JavaScriptObject curveOverloaded_1, JavaScriptObject curveOverloaded_2, JavaScriptObject curveOverloaded_3, JavaScriptObject curveOverloaded_4, JavaScriptObject curveOverloaded_5, JavaScriptObject curveOverloaded_6){
		_curve(curveOverloaded_0, curveOverloaded_1, curveOverloaded_2, curveOverloaded_3, curveOverloaded_4, curveOverloaded_5, curveOverloaded_6);
	}
	private native JavaScriptObject _curve(JavaScriptObject curveOverloaded_0, JavaScriptObject curveOverloaded_1, JavaScriptObject curveOverloaded_2, JavaScriptObject curveOverloaded_3, JavaScriptObject curveOverloaded_4, JavaScriptObject curveOverloaded_5, JavaScriptObject curveOverloaded_6)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().curve(curveOverloaded_0, curveOverloaded_1, curveOverloaded_2, curveOverloaded_3, curveOverloaded_4, curveOverloaded_5, curveOverloaded_6);
	}-*/;
	
	public void curve(JavaScriptObject curveOverloaded_0, JavaScriptObject curveOverloaded_1, JavaScriptObject curveOverloaded_2, JavaScriptObject curveOverloaded_3, JavaScriptObject curveOverloaded_4, JavaScriptObject curveOverloaded_5, JavaScriptObject curveOverloaded_6, JavaScriptObject curveOverloaded_7){
		_curve(curveOverloaded_0, curveOverloaded_1, curveOverloaded_2, curveOverloaded_3, curveOverloaded_4, curveOverloaded_5, curveOverloaded_6, curveOverloaded_7);
	}
	private native JavaScriptObject _curve(JavaScriptObject curveOverloaded_0, JavaScriptObject curveOverloaded_1, JavaScriptObject curveOverloaded_2, JavaScriptObject curveOverloaded_3, JavaScriptObject curveOverloaded_4, JavaScriptObject curveOverloaded_5, JavaScriptObject curveOverloaded_6, JavaScriptObject curveOverloaded_7)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().curve(curveOverloaded_0, curveOverloaded_1, curveOverloaded_2, curveOverloaded_3, curveOverloaded_4, curveOverloaded_5, curveOverloaded_6, curveOverloaded_7);
	}-*/;
	
	public void curve(JavaScriptObject curveOverloaded_0, JavaScriptObject curveOverloaded_1, JavaScriptObject curveOverloaded_2, JavaScriptObject curveOverloaded_3, JavaScriptObject curveOverloaded_4, JavaScriptObject curveOverloaded_5, JavaScriptObject curveOverloaded_6, JavaScriptObject curveOverloaded_7, JavaScriptObject curveOverloaded_8, JavaScriptObject curveOverloaded_9){
		_curve(curveOverloaded_0, curveOverloaded_1, curveOverloaded_2, curveOverloaded_3, curveOverloaded_4, curveOverloaded_5, curveOverloaded_6, curveOverloaded_7, curveOverloaded_8, curveOverloaded_9);
	}
	private native JavaScriptObject _curve(JavaScriptObject curveOverloaded_0, JavaScriptObject curveOverloaded_1, JavaScriptObject curveOverloaded_2, JavaScriptObject curveOverloaded_3, JavaScriptObject curveOverloaded_4, JavaScriptObject curveOverloaded_5, JavaScriptObject curveOverloaded_6, JavaScriptObject curveOverloaded_7, JavaScriptObject curveOverloaded_8, JavaScriptObject curveOverloaded_9)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().curve(curveOverloaded_0, curveOverloaded_1, curveOverloaded_2, curveOverloaded_3, curveOverloaded_4, curveOverloaded_5, curveOverloaded_6, curveOverloaded_7, curveOverloaded_8, curveOverloaded_9);
	}-*/;
	
	public void curve(JavaScriptObject curveOverloaded_0, JavaScriptObject curveOverloaded_1, JavaScriptObject curveOverloaded_2, JavaScriptObject curveOverloaded_3, JavaScriptObject curveOverloaded_4, JavaScriptObject curveOverloaded_5, JavaScriptObject curveOverloaded_6, JavaScriptObject curveOverloaded_7, JavaScriptObject curveOverloaded_8, JavaScriptObject curveOverloaded_9, JavaScriptObject curveOverloaded_10){
		_curve(curveOverloaded_0, curveOverloaded_1, curveOverloaded_2, curveOverloaded_3, curveOverloaded_4, curveOverloaded_5, curveOverloaded_6, curveOverloaded_7, curveOverloaded_8, curveOverloaded_9, curveOverloaded_10);
	}
	private native JavaScriptObject _curve(JavaScriptObject curveOverloaded_0, JavaScriptObject curveOverloaded_1, JavaScriptObject curveOverloaded_2, JavaScriptObject curveOverloaded_3, JavaScriptObject curveOverloaded_4, JavaScriptObject curveOverloaded_5, JavaScriptObject curveOverloaded_6, JavaScriptObject curveOverloaded_7, JavaScriptObject curveOverloaded_8, JavaScriptObject curveOverloaded_9, JavaScriptObject curveOverloaded_10)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().curve(curveOverloaded_0, curveOverloaded_1, curveOverloaded_2, curveOverloaded_3, curveOverloaded_4, curveOverloaded_5, curveOverloaded_6, curveOverloaded_7, curveOverloaded_8, curveOverloaded_9, curveOverloaded_10);
	}-*/;
	
	public void curve(JavaScriptObject curveOverloaded_0, JavaScriptObject curveOverloaded_1, JavaScriptObject curveOverloaded_2, JavaScriptObject curveOverloaded_3, JavaScriptObject curveOverloaded_4, JavaScriptObject curveOverloaded_5, JavaScriptObject curveOverloaded_6, JavaScriptObject curveOverloaded_7, JavaScriptObject curveOverloaded_8, JavaScriptObject curveOverloaded_9, JavaScriptObject curveOverloaded_10, JavaScriptObject curveOverloaded_11){
		_curve(curveOverloaded_0, curveOverloaded_1, curveOverloaded_2, curveOverloaded_3, curveOverloaded_4, curveOverloaded_5, curveOverloaded_6, curveOverloaded_7, curveOverloaded_8, curveOverloaded_9, curveOverloaded_10, curveOverloaded_11);
	}
	private native JavaScriptObject _curve(JavaScriptObject curveOverloaded_0, JavaScriptObject curveOverloaded_1, JavaScriptObject curveOverloaded_2, JavaScriptObject curveOverloaded_3, JavaScriptObject curveOverloaded_4, JavaScriptObject curveOverloaded_5, JavaScriptObject curveOverloaded_6, JavaScriptObject curveOverloaded_7, JavaScriptObject curveOverloaded_8, JavaScriptObject curveOverloaded_9, JavaScriptObject curveOverloaded_10, JavaScriptObject curveOverloaded_11)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().curve(curveOverloaded_0, curveOverloaded_1, curveOverloaded_2, curveOverloaded_3, curveOverloaded_4, curveOverloaded_5, curveOverloaded_6, curveOverloaded_7, curveOverloaded_8, curveOverloaded_9, curveOverloaded_10, curveOverloaded_11);
	}-*/;
	
	public void curveDetail(){
		_curveDetail();
	}
	private native JavaScriptObject _curveDetail()/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().curveDetail();
	}-*/;
	
	public void curveDetail(JavaScriptObject curveDetailOverloaded_0){
		_curveDetail(curveDetailOverloaded_0);
	}
	private native JavaScriptObject _curveDetail(JavaScriptObject curveDetailOverloaded_0)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().curveDetail(curveDetailOverloaded_0);
	}-*/;
	
	public void rectMode(JavaScriptObject aRectMode){
		_rectMode(aRectMode);
	}
	private native JavaScriptObject _rectMode(JavaScriptObject aRectMode)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().rectMode(aRectMode);
	}-*/;
	
	public void ellipseMode(JavaScriptObject aEllipseMode){
		_ellipseMode(aEllipseMode);
	}
	private native JavaScriptObject _ellipseMode(JavaScriptObject aEllipseMode)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().ellipseMode(aEllipseMode);
	}-*/;
	
	public void arc(JavaScriptObject x, JavaScriptObject y, JavaScriptObject width, JavaScriptObject height, JavaScriptObject start, JavaScriptObject stop){
		_arc(x, y, width, height, start, stop);
	}
	private native JavaScriptObject _arc(JavaScriptObject x, JavaScriptObject y, JavaScriptObject width, JavaScriptObject height, JavaScriptObject start, JavaScriptObject stop)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().arc(x, y, width, height, start, stop);
	}-*/;
	
	public void line(){
		_line();
	}
	private native JavaScriptObject _line()/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().line();
	}-*/;
	
	public void line(JavaScriptObject lineOverloaded_0, JavaScriptObject lineOverloaded_1, JavaScriptObject lineOverloaded_2, JavaScriptObject lineOverloaded_3, JavaScriptObject lineOverloaded_4, JavaScriptObject lineOverloaded_5, JavaScriptObject lineOverloaded_6){
		_line(lineOverloaded_0, lineOverloaded_1, lineOverloaded_2, lineOverloaded_3, lineOverloaded_4, lineOverloaded_5, lineOverloaded_6);
	}
	private native JavaScriptObject _line(JavaScriptObject lineOverloaded_0, JavaScriptObject lineOverloaded_1, JavaScriptObject lineOverloaded_2, JavaScriptObject lineOverloaded_3, JavaScriptObject lineOverloaded_4, JavaScriptObject lineOverloaded_5, JavaScriptObject lineOverloaded_6)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().line(lineOverloaded_0, lineOverloaded_1, lineOverloaded_2, lineOverloaded_3, lineOverloaded_4, lineOverloaded_5, lineOverloaded_6);
	}-*/;
	
	public void line(JavaScriptObject lineOverloaded_0){
		_line(lineOverloaded_0);
	}
	private native JavaScriptObject _line(JavaScriptObject lineOverloaded_0)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().line(lineOverloaded_0);
	}-*/;
	
	public void line(JavaScriptObject lineOverloaded_0, JavaScriptObject lineOverloaded_1){
		_line(lineOverloaded_0, lineOverloaded_1);
	}
	private native JavaScriptObject _line(JavaScriptObject lineOverloaded_0, JavaScriptObject lineOverloaded_1)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().line(lineOverloaded_0, lineOverloaded_1);
	}-*/;
	
	public void line(JavaScriptObject lineOverloaded_0, JavaScriptObject lineOverloaded_1, JavaScriptObject lineOverloaded_2){
		_line(lineOverloaded_0, lineOverloaded_1, lineOverloaded_2);
	}
	private native JavaScriptObject _line(JavaScriptObject lineOverloaded_0, JavaScriptObject lineOverloaded_1, JavaScriptObject lineOverloaded_2)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().line(lineOverloaded_0, lineOverloaded_1, lineOverloaded_2);
	}-*/;
	
	public void line(JavaScriptObject lineOverloaded_0, JavaScriptObject lineOverloaded_1, JavaScriptObject lineOverloaded_2, JavaScriptObject lineOverloaded_3){
		_line(lineOverloaded_0, lineOverloaded_1, lineOverloaded_2, lineOverloaded_3);
	}
	private native JavaScriptObject _line(JavaScriptObject lineOverloaded_0, JavaScriptObject lineOverloaded_1, JavaScriptObject lineOverloaded_2, JavaScriptObject lineOverloaded_3)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().line(lineOverloaded_0, lineOverloaded_1, lineOverloaded_2, lineOverloaded_3);
	}-*/;
	
	public void line(JavaScriptObject lineOverloaded_0, JavaScriptObject lineOverloaded_1, JavaScriptObject lineOverloaded_2, JavaScriptObject lineOverloaded_3, JavaScriptObject lineOverloaded_4){
		_line(lineOverloaded_0, lineOverloaded_1, lineOverloaded_2, lineOverloaded_3, lineOverloaded_4);
	}
	private native JavaScriptObject _line(JavaScriptObject lineOverloaded_0, JavaScriptObject lineOverloaded_1, JavaScriptObject lineOverloaded_2, JavaScriptObject lineOverloaded_3, JavaScriptObject lineOverloaded_4)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().line(lineOverloaded_0, lineOverloaded_1, lineOverloaded_2, lineOverloaded_3, lineOverloaded_4);
	}-*/;
	
	public void line(JavaScriptObject lineOverloaded_0, JavaScriptObject lineOverloaded_1, JavaScriptObject lineOverloaded_2, JavaScriptObject lineOverloaded_3, JavaScriptObject lineOverloaded_4, JavaScriptObject lineOverloaded_5){
		_line(lineOverloaded_0, lineOverloaded_1, lineOverloaded_2, lineOverloaded_3, lineOverloaded_4, lineOverloaded_5);
	}
	private native JavaScriptObject _line(JavaScriptObject lineOverloaded_0, JavaScriptObject lineOverloaded_1, JavaScriptObject lineOverloaded_2, JavaScriptObject lineOverloaded_3, JavaScriptObject lineOverloaded_4, JavaScriptObject lineOverloaded_5)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().line(lineOverloaded_0, lineOverloaded_1, lineOverloaded_2, lineOverloaded_3, lineOverloaded_4, lineOverloaded_5);
	}-*/;
	
	public void bezier(JavaScriptObject x1, JavaScriptObject y1, JavaScriptObject x2, JavaScriptObject y2, JavaScriptObject x3, JavaScriptObject y3, JavaScriptObject x4, JavaScriptObject y4){
		_bezier(x1, y1, x2, y2, x3, y3, x4, y4);
	}
	private native JavaScriptObject _bezier(JavaScriptObject x1, JavaScriptObject y1, JavaScriptObject x2, JavaScriptObject y2, JavaScriptObject x3, JavaScriptObject y3, JavaScriptObject x4, JavaScriptObject y4)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().bezier(x1, y1, x2, y2, x3, y3, x4, y4);
	}-*/;
	
	public void bezierPoint(JavaScriptObject a, JavaScriptObject b, JavaScriptObject c, JavaScriptObject d, JavaScriptObject t){
		_bezierPoint(a, b, c, d, t);
	}
	private native JavaScriptObject _bezierPoint(JavaScriptObject a, JavaScriptObject b, JavaScriptObject c, JavaScriptObject d, JavaScriptObject t)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().bezierPoint(a, b, c, d, t);
	}-*/;
	
	public void bezierTangent(JavaScriptObject a, JavaScriptObject b, JavaScriptObject c, JavaScriptObject d, JavaScriptObject t){
		_bezierTangent(a, b, c, d, t);
	}
	private native JavaScriptObject _bezierTangent(JavaScriptObject a, JavaScriptObject b, JavaScriptObject c, JavaScriptObject d, JavaScriptObject t)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().bezierTangent(a, b, c, d, t);
	}-*/;
	
	public void curvePoint(JavaScriptObject a, JavaScriptObject b, JavaScriptObject c, JavaScriptObject d, JavaScriptObject t){
		_curvePoint(a, b, c, d, t);
	}
	private native JavaScriptObject _curvePoint(JavaScriptObject a, JavaScriptObject b, JavaScriptObject c, JavaScriptObject d, JavaScriptObject t)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().curvePoint(a, b, c, d, t);
	}-*/;
	
	public void curveTangent(JavaScriptObject a, JavaScriptObject b, JavaScriptObject c, JavaScriptObject d, JavaScriptObject t){
		_curveTangent(a, b, c, d, t);
	}
	private native JavaScriptObject _curveTangent(JavaScriptObject a, JavaScriptObject b, JavaScriptObject c, JavaScriptObject d, JavaScriptObject t)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().curveTangent(a, b, c, d, t);
	}-*/;
	
	public void triangle(JavaScriptObject x1, JavaScriptObject y1, JavaScriptObject x2, JavaScriptObject y2, JavaScriptObject x3, JavaScriptObject y3){
		_triangle(x1, y1, x2, y2, x3, y3);
	}
	private native JavaScriptObject _triangle(JavaScriptObject x1, JavaScriptObject y1, JavaScriptObject x2, JavaScriptObject y2, JavaScriptObject x3, JavaScriptObject y3)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().triangle(x1, y1, x2, y2, x3, y3);
	}-*/;
	
	public void quad(JavaScriptObject x1, JavaScriptObject y1, JavaScriptObject x2, JavaScriptObject y2, JavaScriptObject x3, JavaScriptObject y3, JavaScriptObject x4, JavaScriptObject y4){
		_quad(x1, y1, x2, y2, x3, y3, x4, y4);
	}
	private native JavaScriptObject _quad(JavaScriptObject x1, JavaScriptObject y1, JavaScriptObject x2, JavaScriptObject y2, JavaScriptObject x3, JavaScriptObject y3, JavaScriptObject x4, JavaScriptObject y4)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().quad(x1, y1, x2, y2, x3, y3, x4, y4);
	}-*/;
	
	public void rect(JavaScriptObject x, JavaScriptObject y, JavaScriptObject width, JavaScriptObject height){
		_rect(x, y, width, height);
	}
	private native JavaScriptObject _rect(JavaScriptObject x, JavaScriptObject y, JavaScriptObject width, JavaScriptObject height)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().rect(x, y, width, height);
	}-*/;
	
	public void ellipse(String x, String y, String width, String height){
		_ellipse(x, y, width, height);
	}
	private native JavaScriptObject _ellipse(String x, String y, String width, String height)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().ellipse(x, y, width, height);
	}-*/;
	
	public void normal(JavaScriptObject nx, JavaScriptObject ny, JavaScriptObject nz){
		_normal(nx, ny, nz);
	}
	private native JavaScriptObject _normal(JavaScriptObject nx, JavaScriptObject ny, JavaScriptObject nz)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().normal(nx, ny, nz);
	}-*/;
	
	public void normal(JavaScriptObject normalOverloaded_0, JavaScriptObject normalOverloaded_1, JavaScriptObject normalOverloaded_2, JavaScriptObject normalOverloaded_3){
		_normal(normalOverloaded_0, normalOverloaded_1, normalOverloaded_2, normalOverloaded_3);
	}
	private native JavaScriptObject _normal(JavaScriptObject normalOverloaded_0, JavaScriptObject normalOverloaded_1, JavaScriptObject normalOverloaded_2, JavaScriptObject normalOverloaded_3)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().normal(normalOverloaded_0, normalOverloaded_1, normalOverloaded_2, normalOverloaded_3);
	}-*/;
	
	public void save(String file){
		_save(file);
	}
	private native JavaScriptObject _save(String file)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().save(file);
	}-*/;
	
	public void PImage(JavaScriptObject aWidth, JavaScriptObject aHeight, String aFormat){
		_PImage(aWidth, aHeight, aFormat);
	}
	private native JavaScriptObject _PImage(JavaScriptObject aWidth, JavaScriptObject aHeight, String aFormat)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().PImage(aWidth, aHeight, aFormat);
	}-*/;
	
	public void PImage(JavaScriptObject PImageOverloaded_0, JavaScriptObject PImageOverloaded_1){
		_PImage(PImageOverloaded_0, PImageOverloaded_1);
	}
	private native JavaScriptObject _PImage(JavaScriptObject PImageOverloaded_0, JavaScriptObject PImageOverloaded_1)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().PImage(PImageOverloaded_0, PImageOverloaded_1);
	}-*/;
	
	public void PImage(JavaScriptObject PImageOverloaded_0){
		_PImage(PImageOverloaded_0);
	}
	private native JavaScriptObject _PImage(JavaScriptObject PImageOverloaded_0)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().PImage(PImageOverloaded_0);
	}-*/;
	
	public void PImage(JavaScriptObject PImageOverloaded_0, JavaScriptObject PImageOverloaded_1, JavaScriptObject PImageOverloaded_2, JavaScriptObject PImageOverloaded_3){
		_PImage(PImageOverloaded_0, PImageOverloaded_1, PImageOverloaded_2, PImageOverloaded_3);
	}
	private native JavaScriptObject _PImage(JavaScriptObject PImageOverloaded_0, JavaScriptObject PImageOverloaded_1, JavaScriptObject PImageOverloaded_2, JavaScriptObject PImageOverloaded_3)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().PImage(PImageOverloaded_0, PImageOverloaded_1, PImageOverloaded_2, PImageOverloaded_3);
	}-*/;
	
	public void createImage(JavaScriptObject w, JavaScriptObject h, JavaScriptObject mode){
		_createImage(w, h, mode);
	}
	private native JavaScriptObject _createImage(JavaScriptObject w, JavaScriptObject h, JavaScriptObject mode)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().createImage(w, h, mode);
	}-*/;
	
	public void loadImage(JavaScriptObject file, JavaScriptObject type, String callback){
		_loadImage(file, type, callback);
	}
	private native JavaScriptObject _loadImage(JavaScriptObject file, JavaScriptObject type, String callback)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().loadImage(file, type, callback);
	}-*/;
	
	public void get(JavaScriptObject x, JavaScriptObject y, JavaScriptObject w, JavaScriptObject h, JavaScriptObject img){
		_get(x, y, w, h, img);
	}
	private native JavaScriptObject _get(JavaScriptObject x, JavaScriptObject y, JavaScriptObject w, JavaScriptObject h, JavaScriptObject img)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().get(x, y, w, h, img);
	}-*/;
	
	public void get(JavaScriptObject getOverloaded_0){
		_get(getOverloaded_0);
	}
	private native JavaScriptObject _get(JavaScriptObject getOverloaded_0)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().get(getOverloaded_0);
	}-*/;
	
	public void get(JavaScriptObject getOverloaded_0, JavaScriptObject getOverloaded_1, JavaScriptObject getOverloaded_2, JavaScriptObject getOverloaded_3, JavaScriptObject getOverloaded_4, JavaScriptObject getOverloaded_5){
		_get(getOverloaded_0, getOverloaded_1, getOverloaded_2, getOverloaded_3, getOverloaded_4, getOverloaded_5);
	}
	private native JavaScriptObject _get(JavaScriptObject getOverloaded_0, JavaScriptObject getOverloaded_1, JavaScriptObject getOverloaded_2, JavaScriptObject getOverloaded_3, JavaScriptObject getOverloaded_4, JavaScriptObject getOverloaded_5)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().get(getOverloaded_0, getOverloaded_1, getOverloaded_2, getOverloaded_3, getOverloaded_4, getOverloaded_5);
	}-*/;
	
	public void get(JavaScriptObject getOverloaded_0, JavaScriptObject getOverloaded_1, JavaScriptObject getOverloaded_2, JavaScriptObject getOverloaded_3){
		_get(getOverloaded_0, getOverloaded_1, getOverloaded_2, getOverloaded_3);
	}
	private native JavaScriptObject _get(JavaScriptObject getOverloaded_0, JavaScriptObject getOverloaded_1, JavaScriptObject getOverloaded_2, JavaScriptObject getOverloaded_3)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().get(getOverloaded_0, getOverloaded_1, getOverloaded_2, getOverloaded_3);
	}-*/;
	
	public void get(JavaScriptObject getOverloaded_0, JavaScriptObject getOverloaded_1, JavaScriptObject getOverloaded_2){
		_get(getOverloaded_0, getOverloaded_1, getOverloaded_2);
	}
	private native JavaScriptObject _get(JavaScriptObject getOverloaded_0, JavaScriptObject getOverloaded_1, JavaScriptObject getOverloaded_2)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().get(getOverloaded_0, getOverloaded_1, getOverloaded_2);
	}-*/;
	
	public void get(JavaScriptObject getOverloaded_0, JavaScriptObject getOverloaded_1){
		_get(getOverloaded_0, getOverloaded_1);
	}
	private native JavaScriptObject _get(JavaScriptObject getOverloaded_0, JavaScriptObject getOverloaded_1)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().get(getOverloaded_0, getOverloaded_1);
	}-*/;
	
	public void createGraphics(JavaScriptObject w, JavaScriptObject h){
		_createGraphics(w, h);
	}
	private native JavaScriptObject _createGraphics(JavaScriptObject w, JavaScriptObject h)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().createGraphics(w, h);
	}-*/;
	
	public void set(JavaScriptObject x, JavaScriptObject y, JavaScriptObject obj, JavaScriptObject img){
		_set(x, y, obj, img);
	}
	private native JavaScriptObject _set(JavaScriptObject x, JavaScriptObject y, JavaScriptObject obj, JavaScriptObject img)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().set(x, y, obj, img);
	}-*/;
	
	public void set(JavaScriptObject setOverloaded_0, JavaScriptObject setOverloaded_1, JavaScriptObject setOverloaded_2, JavaScriptObject setOverloaded_3, JavaScriptObject setOverloaded_4){
		_set(setOverloaded_0, setOverloaded_1, setOverloaded_2, setOverloaded_3, setOverloaded_4);
	}
	private native JavaScriptObject _set(JavaScriptObject setOverloaded_0, JavaScriptObject setOverloaded_1, JavaScriptObject setOverloaded_2, JavaScriptObject setOverloaded_3, JavaScriptObject setOverloaded_4)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().set(setOverloaded_0, setOverloaded_1, setOverloaded_2, setOverloaded_3, setOverloaded_4);
	}-*/;
	
	public void background(){
		_background();
	}
	private native JavaScriptObject _background()/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().background();
	}-*/;
	
	public void background(int backgroundOverloaded_0){
		_background(backgroundOverloaded_0);
	}
	private native JavaScriptObject _background(int backgroundOverloaded_0)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().background(backgroundOverloaded_0);
	}-*/;
	
	public void background(JavaScriptObject backgroundOverloaded_0, JavaScriptObject backgroundOverloaded_1){
		_background(backgroundOverloaded_0, backgroundOverloaded_1);
	}
	private native JavaScriptObject _background(JavaScriptObject backgroundOverloaded_0, JavaScriptObject backgroundOverloaded_1)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().background(backgroundOverloaded_0, backgroundOverloaded_1);
	}-*/;
	
	public void getImage(JavaScriptObject img){
		_getImage(img);
	}
	private native JavaScriptObject _getImage(JavaScriptObject img)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().getImage(img);
	}-*/;
	
	public void image(JavaScriptObject img, JavaScriptObject x, JavaScriptObject y, JavaScriptObject w, JavaScriptObject h){
		_image(img, x, y, w, h);
	}
	private native JavaScriptObject _image(JavaScriptObject img, JavaScriptObject x, JavaScriptObject y, JavaScriptObject w, JavaScriptObject h)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().image(img, x, y, w, h);
	}-*/;
	
	public void clear(JavaScriptObject x, JavaScriptObject y, JavaScriptObject width, JavaScriptObject height){
		_clear(x, y, width, height);
	}
	private native JavaScriptObject _clear(JavaScriptObject x, JavaScriptObject y, JavaScriptObject width, JavaScriptObject height)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().clear(x, y, width, height);
	}-*/;
	
	public void clear(JavaScriptObject clearOverloaded_0){
		_clear(clearOverloaded_0);
	}
	private native JavaScriptObject _clear(JavaScriptObject clearOverloaded_0)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().clear(clearOverloaded_0);
	}-*/;
	
	public void tint(){
		_tint();
	}
	private native JavaScriptObject _tint()/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().tint();
	}-*/;
	
	public void noTint(){
		_noTint();
	}
	private native JavaScriptObject _noTint()/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().noTint();
	}-*/;
	
	public void copy(JavaScriptObject src, JavaScriptObject sx, JavaScriptObject sy, JavaScriptObject sw, JavaScriptObject sh, JavaScriptObject dx, JavaScriptObject dy, String dw, String dh){
		_copy(src, sx, sy, sw, sh, dx, dy, dw, dh);
	}
	private native JavaScriptObject _copy(JavaScriptObject src, JavaScriptObject sx, JavaScriptObject sy, JavaScriptObject sw, JavaScriptObject sh, JavaScriptObject dx, JavaScriptObject dy, String dw, String dh)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().copy(src, sx, sy, sw, sh, dx, dy, dw, dh);
	}-*/;
	
	public void blend(JavaScriptObject src, JavaScriptObject sx, JavaScriptObject sy, JavaScriptObject sw, JavaScriptObject sh, JavaScriptObject dx, JavaScriptObject dy, JavaScriptObject dw, JavaScriptObject dh, JavaScriptObject mode, String pimgdest){
		_blend(src, sx, sy, sw, sh, dx, dy, dw, dh, mode, pimgdest);
	}
	private native JavaScriptObject _blend(JavaScriptObject src, JavaScriptObject sx, JavaScriptObject sy, JavaScriptObject sw, JavaScriptObject sh, JavaScriptObject dx, JavaScriptObject dy, JavaScriptObject dw, JavaScriptObject dh, JavaScriptObject mode, String pimgdest)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().blend(src, sx, sy, sw, sh, dx, dy, dw, dh, mode, pimgdest);
	}-*/;
	
	public void blend(JavaScriptObject blendOverloaded_0, JavaScriptObject blendOverloaded_1, JavaScriptObject blendOverloaded_2, JavaScriptObject blendOverloaded_3, JavaScriptObject blendOverloaded_4, JavaScriptObject blendOverloaded_5, JavaScriptObject blendOverloaded_6, JavaScriptObject blendOverloaded_7, JavaScriptObject blendOverloaded_8, JavaScriptObject blendOverloaded_9){
		_blend(blendOverloaded_0, blendOverloaded_1, blendOverloaded_2, blendOverloaded_3, blendOverloaded_4, blendOverloaded_5, blendOverloaded_6, blendOverloaded_7, blendOverloaded_8, blendOverloaded_9);
	}
	private native JavaScriptObject _blend(JavaScriptObject blendOverloaded_0, JavaScriptObject blendOverloaded_1, JavaScriptObject blendOverloaded_2, JavaScriptObject blendOverloaded_3, JavaScriptObject blendOverloaded_4, JavaScriptObject blendOverloaded_5, JavaScriptObject blendOverloaded_6, JavaScriptObject blendOverloaded_7, JavaScriptObject blendOverloaded_8, JavaScriptObject blendOverloaded_9)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().blend(blendOverloaded_0, blendOverloaded_1, blendOverloaded_2, blendOverloaded_3, blendOverloaded_4, blendOverloaded_5, blendOverloaded_6, blendOverloaded_7, blendOverloaded_8, blendOverloaded_9);
	}-*/;
	
	public void blend(JavaScriptObject blendOverloaded_0, JavaScriptObject blendOverloaded_1, JavaScriptObject blendOverloaded_2, JavaScriptObject blendOverloaded_3, JavaScriptObject blendOverloaded_4, JavaScriptObject blendOverloaded_5, JavaScriptObject blendOverloaded_6, JavaScriptObject blendOverloaded_7, JavaScriptObject blendOverloaded_8, JavaScriptObject blendOverloaded_9, JavaScriptObject blendOverloaded_10, JavaScriptObject blendOverloaded_11){
		_blend(blendOverloaded_0, blendOverloaded_1, blendOverloaded_2, blendOverloaded_3, blendOverloaded_4, blendOverloaded_5, blendOverloaded_6, blendOverloaded_7, blendOverloaded_8, blendOverloaded_9, blendOverloaded_10, blendOverloaded_11);
	}
	private native JavaScriptObject _blend(JavaScriptObject blendOverloaded_0, JavaScriptObject blendOverloaded_1, JavaScriptObject blendOverloaded_2, JavaScriptObject blendOverloaded_3, JavaScriptObject blendOverloaded_4, JavaScriptObject blendOverloaded_5, JavaScriptObject blendOverloaded_6, JavaScriptObject blendOverloaded_7, JavaScriptObject blendOverloaded_8, JavaScriptObject blendOverloaded_9, JavaScriptObject blendOverloaded_10, JavaScriptObject blendOverloaded_11)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().blend(blendOverloaded_0, blendOverloaded_1, blendOverloaded_2, blendOverloaded_3, blendOverloaded_4, blendOverloaded_5, blendOverloaded_6, blendOverloaded_7, blendOverloaded_8, blendOverloaded_9, blendOverloaded_10, blendOverloaded_11);
	}-*/;
	
	public void intersect(JavaScriptObject sx1, JavaScriptObject sy1, JavaScriptObject sx2, JavaScriptObject sy2, JavaScriptObject dx1, JavaScriptObject dy1, JavaScriptObject dx2, JavaScriptObject dy2){
		_intersect(sx1, sy1, sx2, sy2, dx1, dy1, dx2, dy2);
	}
	private native JavaScriptObject _intersect(JavaScriptObject sx1, JavaScriptObject sy1, JavaScriptObject sx2, JavaScriptObject sy2, JavaScriptObject dx1, JavaScriptObject dy1, JavaScriptObject dx2, JavaScriptObject dy2)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().intersect(sx1, sy1, sx2, sy2, dx1, dy1, dx2, dy2);
	}-*/;
	
	public void blit_resize(JavaScriptObject img, JavaScriptObject srcX1, JavaScriptObject srcY1, JavaScriptObject srcX2, JavaScriptObject srcY2, JavaScriptObject destPixels, JavaScriptObject screenW, JavaScriptObject screenH, JavaScriptObject destX1, JavaScriptObject destY1, JavaScriptObject destX2, JavaScriptObject destY2, JavaScriptObject mode){
		_blit_resize(img, srcX1, srcY1, srcX2, srcY2, destPixels, screenW, screenH, destX1, destY1, destX2, destY2, mode);
	}
	private native JavaScriptObject _blit_resize(JavaScriptObject img, JavaScriptObject srcX1, JavaScriptObject srcY1, JavaScriptObject srcX2, JavaScriptObject srcY2, JavaScriptObject destPixels, JavaScriptObject screenW, JavaScriptObject screenH, JavaScriptObject destX1, JavaScriptObject destY1, JavaScriptObject destX2, JavaScriptObject destY2, JavaScriptObject mode)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().blit_resize(img, srcX1, srcY1, srcX2, srcY2, destPixels, screenW, screenH, destX1, destY1, destX2, destY2, mode);
	}-*/;
	
	public void loadFont(JavaScriptObject name){
		_loadFont(name);
	}
	private native JavaScriptObject _loadFont(JavaScriptObject name)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().loadFont(name);
	}-*/;
	
	public void textFont(JavaScriptObject name, JavaScriptObject size){
		_textFont(name, size);
	}
	private native JavaScriptObject _textFont(JavaScriptObject name, JavaScriptObject size)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().textFont(name, size);
	}-*/;
	
	public void textSize(JavaScriptObject size){
		_textSize(size);
	}
	private native JavaScriptObject _textSize(JavaScriptObject size)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().textSize(size);
	}-*/;
	
	public void glyphLook(JavaScriptObject font, JavaScriptObject chr){
		_glyphLook(font, chr);
	}
	private native JavaScriptObject _glyphLook(JavaScriptObject font, JavaScriptObject chr)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().glyphLook(font, chr);
	}-*/;
	
	public void text(){
		_text();
	}
	private native JavaScriptObject _text()/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().text();
	}-*/;
	
	public void text(JavaScriptObject textOverloaded_0){
		_text(textOverloaded_0);
	}
	private native JavaScriptObject _text(JavaScriptObject textOverloaded_0)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().text(textOverloaded_0);
	}-*/;
	
	public void text(JavaScriptObject textOverloaded_0, JavaScriptObject textOverloaded_1){
		_text(textOverloaded_0, textOverloaded_1);
	}
	private native JavaScriptObject _text(JavaScriptObject textOverloaded_0, JavaScriptObject textOverloaded_1)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().text(textOverloaded_0, textOverloaded_1);
	}-*/;
	
	public void text(JavaScriptObject textOverloaded_0, JavaScriptObject textOverloaded_1, JavaScriptObject textOverloaded_2, JavaScriptObject textOverloaded_3){
		_text(textOverloaded_0, textOverloaded_1, textOverloaded_2, textOverloaded_3);
	}
	private native JavaScriptObject _text(JavaScriptObject textOverloaded_0, JavaScriptObject textOverloaded_1, JavaScriptObject textOverloaded_2, JavaScriptObject textOverloaded_3)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().text(textOverloaded_0, textOverloaded_1, textOverloaded_2, textOverloaded_3);
	}-*/;
	
	public void text(JavaScriptObject textOverloaded_0, JavaScriptObject textOverloaded_1, JavaScriptObject textOverloaded_2){
		_text(textOverloaded_0, textOverloaded_1, textOverloaded_2);
	}
	private native JavaScriptObject _text(JavaScriptObject textOverloaded_0, JavaScriptObject textOverloaded_1, JavaScriptObject textOverloaded_2)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().text(textOverloaded_0, textOverloaded_1, textOverloaded_2);
	}-*/;
	
	public void text(JavaScriptObject textOverloaded_0, JavaScriptObject textOverloaded_1, JavaScriptObject textOverloaded_2, JavaScriptObject textOverloaded_3, JavaScriptObject textOverloaded_4){
		_text(textOverloaded_0, textOverloaded_1, textOverloaded_2, textOverloaded_3, textOverloaded_4);
	}
	private native JavaScriptObject _text(JavaScriptObject textOverloaded_0, JavaScriptObject textOverloaded_1, JavaScriptObject textOverloaded_2, JavaScriptObject textOverloaded_3, JavaScriptObject textOverloaded_4)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().text(textOverloaded_0, textOverloaded_1, textOverloaded_2, textOverloaded_3, textOverloaded_4);
	}-*/;
	
	public void text(JavaScriptObject textOverloaded_0, JavaScriptObject textOverloaded_1, JavaScriptObject textOverloaded_2, JavaScriptObject textOverloaded_3, JavaScriptObject textOverloaded_4, JavaScriptObject textOverloaded_5){
		_text(textOverloaded_0, textOverloaded_1, textOverloaded_2, textOverloaded_3, textOverloaded_4, textOverloaded_5);
	}
	private native JavaScriptObject _text(JavaScriptObject textOverloaded_0, JavaScriptObject textOverloaded_1, JavaScriptObject textOverloaded_2, JavaScriptObject textOverloaded_3, JavaScriptObject textOverloaded_4, JavaScriptObject textOverloaded_5)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().text(textOverloaded_0, textOverloaded_1, textOverloaded_2, textOverloaded_3, textOverloaded_4, textOverloaded_5);
	}-*/;
	
	public void text(JavaScriptObject textOverloaded_0, JavaScriptObject textOverloaded_1, JavaScriptObject textOverloaded_2, JavaScriptObject textOverloaded_3, JavaScriptObject textOverloaded_4, JavaScriptObject textOverloaded_5, JavaScriptObject textOverloaded_6){
		_text(textOverloaded_0, textOverloaded_1, textOverloaded_2, textOverloaded_3, textOverloaded_4, textOverloaded_5, textOverloaded_6);
	}
	private native JavaScriptObject _text(JavaScriptObject textOverloaded_0, JavaScriptObject textOverloaded_1, JavaScriptObject textOverloaded_2, JavaScriptObject textOverloaded_3, JavaScriptObject textOverloaded_4, JavaScriptObject textOverloaded_5, JavaScriptObject textOverloaded_6)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().text(textOverloaded_0, textOverloaded_1, textOverloaded_2, textOverloaded_3, textOverloaded_4, textOverloaded_5, textOverloaded_6);
	}-*/;
	
	public void loadGlyph(JavaScriptObject url){
		_loadGlyph(url);
	}
	private native JavaScriptObject _loadGlyph(JavaScriptObject url)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().loadGlyph(url);
	}-*/;
	
	public void regex(JavaScriptObject needle, JavaScriptObject hay){
		_regex(needle, hay);
	}
	private native JavaScriptObject _regex(JavaScriptObject needle, JavaScriptObject hay)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().regex(needle, hay);
	}-*/;
	
	public void buildPath(JavaScriptObject d){
		_buildPath(d);
	}
	private native JavaScriptObject _buildPath(JavaScriptObject d)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().buildPath(d);
	}-*/;
	
	public void parseSVGFontse(String svg){
		_parseSVGFontse(svg);
	}
	private native JavaScriptObject _parseSVGFontse(String svg)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().parseSVGFontse(svg);
	}-*/;
	
	public void loadXML(){
		_loadXML();
	}
	private native JavaScriptObject _loadXML()/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().loadXML();
	}-*/;
	
	public void extendClass(JavaScriptObject obj, JavaScriptObject args, String fn){
		_extendClass(obj, args, fn);
	}
	private native JavaScriptObject _extendClass(JavaScriptObject obj, JavaScriptObject args, String fn)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().extendClass(obj, args, fn);
	}-*/;
	
	public void extendClass(JavaScriptObject extendClassOverloaded_0, JavaScriptObject extendClassOverloaded_1, JavaScriptObject extendClassOverloaded_2, JavaScriptObject extendClassOverloaded_3){
		_extendClass(extendClassOverloaded_0, extendClassOverloaded_1, extendClassOverloaded_2, extendClassOverloaded_3);
	}
	private native JavaScriptObject _extendClass(JavaScriptObject extendClassOverloaded_0, JavaScriptObject extendClassOverloaded_1, JavaScriptObject extendClassOverloaded_2, JavaScriptObject extendClassOverloaded_3)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().extendClass(extendClassOverloaded_0, extendClassOverloaded_1, extendClassOverloaded_2, extendClassOverloaded_3);
	}-*/;
	
	public void addMethod(String object, JavaScriptObject name, JavaScriptObject fn){
		_addMethod(object, name, fn);
	}
	private native JavaScriptObject _addMethod(String object, JavaScriptObject name, JavaScriptObject fn)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().addMethod(object, name, fn);
	}-*/;
	
	public void init(String code){
		_init(code);
	}
	private native JavaScriptObject _init(String code)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().init(code);
	}-*/;
	
	public void attach(JavaScriptObject elem, JavaScriptObject type, JavaScriptObject fn){
		_attach(elem, type, fn);
	}
	private native JavaScriptObject _attach(JavaScriptObject elem, JavaScriptObject type, JavaScriptObject fn)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().attach(elem, type, fn);
	}-*/;
	
}
