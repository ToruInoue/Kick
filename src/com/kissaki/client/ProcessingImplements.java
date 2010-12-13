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
	
	
	public void Processing(String aElement, String aCode){
		_Processing(aElement, aCode);
	}
	private native String _Processing(String aElement, String aCode)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().Processing(aElement, aCode);
	}-*/;
	
	public void parse(String aCode, String p){
		_parse(aCode, p);
	}
	private native String _parse(String aCode, String p)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().parse(aCode, p);
	}-*/;
	
	public void imageModeCorner(String x, String y, String w, String h, String whAreSizes){
		_imageModeCorner(x, y, w, h, whAreSizes);
	}
	private native String _imageModeCorner(String x, String y, String w, String h, String whAreSizes)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().imageModeCorner(x, y, w, h, whAreSizes);
	}-*/;
	
	public void imageModeCorners(String x, String y, String w, String h, String whAreSizes){
		_imageModeCorners(x, y, w, h, whAreSizes);
	}
	private native String _imageModeCorners(String x, String y, String w, String h, String whAreSizes)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().imageModeCorners(x, y, w, h, whAreSizes);
	}-*/;
	
	public void imageModeCenter(String x, String y, String w, String h, String whAreSizes){
		_imageModeCenter(x, y, w, h, whAreSizes);
	}
	private native String _imageModeCenter(String x, String y, String w, String h, String whAreSizes)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().imageModeCenter(x, y, w, h, whAreSizes);
	}-*/;
	
	public void buildProcessing(String curElement){
		_buildProcessing(curElement);
	}
	private native String _buildProcessing(String curElement)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().buildProcessing(curElement);
	}-*/;
	
	public void Char(String chr){
		_Char(chr);
	}
	private native String _Char(String chr)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().Char(chr);
	}-*/;
	
	public void printMatrixHelper(String elements){
		_printMatrixHelper(elements);
	}
	private native String _printMatrixHelper(String elements)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().printMatrixHelper(elements);
	}-*/;
	
	public void load(){
		_load();
	}
	private native String _load()/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().load();
	}-*/;
	
	public void load(String loadOverloaded_0, String loadOverloaded_1){
		_load(loadOverloaded_0, loadOverloaded_1);
	}
	private native String _load(String loadOverloaded_0, String loadOverloaded_1)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().load(loadOverloaded_0, loadOverloaded_1);
	}-*/;
	
	public void load(String loadOverloaded_0){
		_load(loadOverloaded_0);
	}
	private native String _load(String loadOverloaded_0)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().load(loadOverloaded_0);
	}-*/;
	
	public void mult(String matrix){
		_mult(matrix);
	}
	private native String _mult(String matrix)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().mult(matrix);
	}-*/;
	
	public void virtHashCode(String obj){
		_virtHashCode(obj);
	}
	private native String _virtHashCode(String obj)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().virtHashCode(obj);
	}-*/;
	
	public void virtEquals(String obj, String other){
		_virtEquals(obj, other);
	}
	private native String _virtEquals(String obj, String other)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().virtEquals(obj, other);
	}-*/;
	
	public void HashMap(){
		_HashMap();
	}
	private native String _HashMap()/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().HashMap();
	}-*/;
	
	public void HashMap(String HashMapOverloaded_0, String HashMapOverloaded_1){
		_HashMap(HashMapOverloaded_0, HashMapOverloaded_1);
	}
	private native String _HashMap(String HashMapOverloaded_0, String HashMapOverloaded_1)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().HashMap(HashMapOverloaded_0, HashMapOverloaded_1);
	}-*/;
	
	public void HashMap(String HashMapOverloaded_0){
		_HashMap(HashMapOverloaded_0);
	}
	private native String _HashMap(String HashMapOverloaded_0)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().HashMap(HashMapOverloaded_0);
	}-*/;
	
	public void Iterator(String conversion, String removeItem){
		_Iterator(conversion, removeItem);
	}
	private native String _Iterator(String conversion, String removeItem)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().Iterator(conversion, removeItem);
	}-*/;
	
	public void Set(String conversion, String isIn, String removeItem){
		_Set(conversion, isIn, removeItem);
	}
	private native String _Set(String conversion, String isIn, String removeItem)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().Set(conversion, isIn, removeItem);
	}-*/;
	
	public void Entry(String pair){
		_Entry(pair);
	}
	private native String _Entry(String pair)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().Entry(pair);
	}-*/;
	
	public void color(String aValue1, String aValue2, String aValue3, String aValue4){
		_color(aValue1, aValue2, aValue3, aValue4);
	}
	private native String _color(String aValue1, String aValue2, String aValue3, String aValue4)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().color(aValue1, aValue2, aValue3, aValue4);
	}-*/;
	
	public void verifyChannel(String aColor){
		_verifyChannel(aColor);
	}
	private native String _verifyChannel(String aColor)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().verifyChannel(aColor);
	}-*/;
	
	public void lerpColor(String c1, String c2, String amt){
		_lerpColor(c1, c2, amt);
	}
	private native String _lerpColor(String c1, String c2, String amt)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().lerpColor(c1, c2, amt);
	}-*/;
	
	public void colorMode(String mode, String range1, String range2, String range3, String range4){
		_colorMode(mode, range1, range2, range3, range4);
	}
	private native String _colorMode(String mode, String range1, String range2, String range3, String range4)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().colorMode(mode, range1, range2, range3, range4);
	}-*/;
	
	public void colorMode(String colorModeOverloaded_0, String colorModeOverloaded_1, String colorModeOverloaded_2, String colorModeOverloaded_3, String colorModeOverloaded_4, String colorModeOverloaded_5){
		_colorMode(colorModeOverloaded_0, colorModeOverloaded_1, colorModeOverloaded_2, colorModeOverloaded_3, colorModeOverloaded_4, colorModeOverloaded_5);
	}
	private native String _colorMode(String colorModeOverloaded_0, String colorModeOverloaded_1, String colorModeOverloaded_2, String colorModeOverloaded_3, String colorModeOverloaded_4, String colorModeOverloaded_5)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().colorMode(colorModeOverloaded_0, colorModeOverloaded_1, colorModeOverloaded_2, colorModeOverloaded_3, colorModeOverloaded_4, colorModeOverloaded_5);
	}-*/;
	
	public void colorMode(String colorModeOverloaded_0, String colorModeOverloaded_1, String colorModeOverloaded_2){
		_colorMode(colorModeOverloaded_0, colorModeOverloaded_1, colorModeOverloaded_2);
	}
	private native String _colorMode(String colorModeOverloaded_0, String colorModeOverloaded_1, String colorModeOverloaded_2)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().colorMode(colorModeOverloaded_0, colorModeOverloaded_1, colorModeOverloaded_2);
	}-*/;
	
	public void translate(String x, String y, String z){
		_translate(x, y, z);
	}
	private native String _translate(String x, String y, String z)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().translate(x, y, z);
	}-*/;
	
	public void scale(String x, String y, String z){
		_scale(x, y, z);
	}
	private native String _scale(String x, String y, String z)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().scale(x, y, z);
	}-*/;
	
	public void applyMatrix(){
		_applyMatrix();
	}
	private native String _applyMatrix()/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().applyMatrix();
	}-*/;
	
	public void applyMatrix(String applyMatrixOverloaded_0){
		_applyMatrix(applyMatrixOverloaded_0);
	}
	private native String _applyMatrix(String applyMatrixOverloaded_0)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().applyMatrix(applyMatrixOverloaded_0);
	}-*/;
	
	public void rotate(String angleInRadians){
		_rotate(angleInRadians);
	}
	private native String _rotate(String angleInRadians)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().rotate(angleInRadians);
	}-*/;
	
	public void loop(){
		_loop();
	}
	private native String _loop()/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().loop();
	}-*/;
	
	public void frameRate(String aRate){
		_frameRate(aRate);
	}
	private native String _frameRate(String aRate)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().frameRate(aRate);
	}-*/;
	
	public void cursor(){
		_cursor();
	}
	private native String _cursor()/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().cursor();
	}-*/;
	
	public void cursor(String cursorOverloaded_0, String cursorOverloaded_1){
		_cursor(cursorOverloaded_0, cursorOverloaded_1);
	}
	private native String _cursor(String cursorOverloaded_0, String cursorOverloaded_1)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().cursor(cursorOverloaded_0, cursorOverloaded_1);
	}-*/;
	
	public void cursor(String cursorOverloaded_0){
		_cursor(cursorOverloaded_0);
	}
	private native String _cursor(String cursorOverloaded_0)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().cursor(cursorOverloaded_0);
	}-*/;
	
	public void cursor(String cursorOverloaded_0, String cursorOverloaded_1, String cursorOverloaded_2, String cursorOverloaded_3){
		_cursor(cursorOverloaded_0, cursorOverloaded_1, cursorOverloaded_2, cursorOverloaded_3);
	}
	private native String _cursor(String cursorOverloaded_0, String cursorOverloaded_1, String cursorOverloaded_2, String cursorOverloaded_3)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().cursor(cursorOverloaded_0, cursorOverloaded_1, cursorOverloaded_2, cursorOverloaded_3);
	}-*/;
	
	public void cursor(String cursorOverloaded_0, String cursorOverloaded_1, String cursorOverloaded_2){
		_cursor(cursorOverloaded_0, cursorOverloaded_1, cursorOverloaded_2);
	}
	private native String _cursor(String cursorOverloaded_0, String cursorOverloaded_1, String cursorOverloaded_2)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().cursor(cursorOverloaded_0, cursorOverloaded_1, cursorOverloaded_2);
	}-*/;
	
	public void Import(String lib){
		_Import(lib);
	}
	private native String _Import(String lib)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().Import(lib);
	}-*/;
	
	public void decToBin(String value, String numBitsInValue){
		_decToBin(value, numBitsInValue);
	}
	private native String _decToBin(String value, String numBitsInValue)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().decToBin(value, numBitsInValue);
	}-*/;
	
	public void unbinary(String binaryString){
		_unbinary(binaryString);
	}
	private native String _unbinary(String binaryString)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().unbinary(binaryString);
	}-*/;
	
	public void unbinary(String unbinaryOverloaded_0, String unbinaryOverloaded_1){
		_unbinary(unbinaryOverloaded_0, unbinaryOverloaded_1);
	}
	private native String _unbinary(String unbinaryOverloaded_0, String unbinaryOverloaded_1)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().unbinary(unbinaryOverloaded_0, unbinaryOverloaded_1);
	}-*/;
	
	public void decimalToHex(String d, String padding){
		_decimalToHex(d, padding);
	}
	private native String _decimalToHex(String d, String padding)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().decimalToHex(d, padding);
	}-*/;
	
	public void hex(String value, String len){
		_hex(value, len);
	}
	private native String _hex(String value, String len)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().hex(value, len);
	}-*/;
	
	public void loadStrings(String url){
		_loadStrings(url);
	}
	private native String _loadStrings(String url)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().loadStrings(url);
	}-*/;
	
	public void loadBytes(String url){
		_loadBytes(url);
	}
	private native String _loadBytes(String url)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().loadBytes(url);
	}-*/;
	
	public void matchAll(String aString, String aRegExp){
		_matchAll(aString, aRegExp);
	}
	private native String _matchAll(String aString, String aRegExp)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().matchAll(aString, aRegExp);
	}-*/;
	
	public void equals(String str){
		_equals(str);
	}
	private native String _equals(String str)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().equals(str);
	}-*/;
	
	public void println(String message){
		_println(message);
	}
	private native String _println(String message)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().println(message);
	}-*/;
	
	public void print(String message){
		_print(message);
	}
	private native String _print(String message)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().print(message);
	}-*/;
	
	public void str(String val){
		_str(val);
	}
	private native String _str(String val)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().str(val);
	}-*/;
	
	public void str(String strOverloaded_0, String strOverloaded_1){
		_str(strOverloaded_0, strOverloaded_1);
	}
	private native String _str(String strOverloaded_0, String strOverloaded_1)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().str(strOverloaded_0, strOverloaded_1);
	}-*/;
	
	public void random(String aMin, String aMax){
		_random(aMin, aMax);
	}
	private native String _random(String aMin, String aMax)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().random(aMin, aMax);
	}-*/;
	
	public void random(String randomOverloaded_0, String randomOverloaded_1, String randomOverloaded_2){
		_random(randomOverloaded_0, randomOverloaded_1, randomOverloaded_2);
	}
	private native String _random(String randomOverloaded_0, String randomOverloaded_1, String randomOverloaded_2)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().random(randomOverloaded_0, randomOverloaded_1, randomOverloaded_2);
	}-*/;
	
	public void noiseGen(String x, String y){
		_noiseGen(x, y);
	}
	private native String _noiseGen(String x, String y)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().noiseGen(x, y);
	}-*/;
	
	public void smoothedNoise(String x, String y){
		_smoothedNoise(x, y);
	}
	private native String _smoothedNoise(String x, String y)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().smoothedNoise(x, y);
	}-*/;
	
	public void interpolate(String a, String b, String x){
		_interpolate(a, b, x);
	}
	private native String _interpolate(String a, String b, String x)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().interpolate(a, b, x);
	}-*/;
	
	public void interpolatedNoise(String x, String y){
		_interpolatedNoise(x, y);
	}
	private native String _interpolatedNoise(String x, String y)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().interpolatedNoise(x, y);
	}-*/;
	
	public void perlinNoise_2D(String x, String y){
		_perlinNoise_2D(x, y);
	}
	private native String _perlinNoise_2D(String x, String y)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().perlinNoise_2D(x, y);
	}-*/;
	
	public void size(String aWidth, String aHeight, String aMode){
		_size(aWidth, aHeight, aMode);
	}
	private native String _size(String aWidth, String aHeight, String aMode)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().size(aWidth, aHeight, aMode);
	}-*/;
	
	public void uniformf(String programObj, String varName, String varValue){
		_uniformf(programObj, varName, varValue);
	}
	private native String _uniformf(String programObj, String varName, String varValue)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().uniformf(programObj, varName, varValue);
	}-*/;
	
	public void uniformi(String programObj, String varName, String varValue){
		_uniformi(programObj, varName, varValue);
	}
	private native String _uniformi(String programObj, String varName, String varValue)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().uniformi(programObj, varName, varValue);
	}-*/;
	
	public void vertexAttribPointer(String programObj, String varName, String size, String VBO){
		_vertexAttribPointer(programObj, varName, size, VBO);
	}
	private native String _vertexAttribPointer(String programObj, String varName, String size, String VBO)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().vertexAttribPointer(programObj, varName, size, VBO);
	}-*/;
	
	public void uniformMatrix(String programObj, String varName, String transpose, String matrix){
		_uniformMatrix(programObj, varName, transpose, matrix);
	}
	private native String _uniformMatrix(String programObj, String varName, String transpose, String matrix)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().uniformMatrix(programObj, varName, transpose, matrix);
	}-*/;
	
	public void lightFalloff(String constant, String linear, String quadratic){
		_lightFalloff(constant, linear, quadratic);
	}
	private native String _lightFalloff(String constant, String linear, String quadratic)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().lightFalloff(constant, linear, quadratic);
	}-*/;
	
	public void lightSpecular(String r, String g, String b){
		_lightSpecular(r, g, b);
	}
	private native String _lightSpecular(String r, String g, String b)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().lightSpecular(r, g, b);
	}-*/;
	
	public void spotLight(String r, String g, String b, String x, String y, String z, String nx, String ny, String nz, String angle, String concentration){
		_spotLight(r, g, b, x, y, z, nx, ny, nz, angle, concentration);
	}
	private native String _spotLight(String r, String g, String b, String x, String y, String z, String nx, String ny, String nz, String angle, String concentration)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().spotLight(r, g, b, x, y, z, nx, ny, nz, angle, concentration);
	}-*/;
	
	public void camera(String eyeX, String eyeY, String eyeZ, String centerX, String centerY, String centerZ, String upX, String upY, String upZ){
		_camera(eyeX, eyeY, eyeZ, centerX, centerY, centerZ, upX, upY, upZ);
	}
	private native String _camera(String eyeX, String eyeY, String eyeZ, String centerX, String centerY, String centerZ, String upX, String upY, String upZ)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().camera(eyeX, eyeY, eyeZ, centerX, centerY, centerZ, upX, upY, upZ);
	}-*/;
	
	public void camera(String cameraOverloaded_0){
		_camera(cameraOverloaded_0);
	}
	private native String _camera(String cameraOverloaded_0)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().camera(cameraOverloaded_0);
	}-*/;
	
	public void perspective(String fov, String aspect, String near, String far){
		_perspective(fov, aspect, near, far);
	}
	private native String _perspective(String fov, String aspect, String near, String far)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().perspective(fov, aspect, near, far);
	}-*/;
	
	public void perspective(String perspectiveOverloaded_0){
		_perspective(perspectiveOverloaded_0);
	}
	private native String _perspective(String perspectiveOverloaded_0)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().perspective(perspectiveOverloaded_0);
	}-*/;
	
	public void perspective(String perspectiveOverloaded_0, String perspectiveOverloaded_1, String perspectiveOverloaded_2){
		_perspective(perspectiveOverloaded_0, perspectiveOverloaded_1, perspectiveOverloaded_2);
	}
	private native String _perspective(String perspectiveOverloaded_0, String perspectiveOverloaded_1, String perspectiveOverloaded_2)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().perspective(perspectiveOverloaded_0, perspectiveOverloaded_1, perspectiveOverloaded_2);
	}-*/;
	
	public void frustum(String left, String right, String bottom, String top, String near, String far){
		_frustum(left, right, bottom, top, near, far);
	}
	private native String _frustum(String left, String right, String bottom, String top, String near, String far)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().frustum(left, right, bottom, top, near, far);
	}-*/;
	
	public void ortho(String left, String right, String bottom, String top, String near, String far){
		_ortho(left, right, bottom, top, near, far);
	}
	private native String _ortho(String left, String right, String bottom, String top, String near, String far)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().ortho(left, right, bottom, top, near, far);
	}-*/;
	
	public void ortho(String orthoOverloaded_0){
		_ortho(orthoOverloaded_0);
	}
	private native String _ortho(String orthoOverloaded_0)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().ortho(orthoOverloaded_0);
	}-*/;
	
	public void sphereDetail(String ures, String vres){
		_sphereDetail(ures, vres);
	}
	private native String _sphereDetail(String ures, String vres)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().sphereDetail(ures, vres);
	}-*/;
	
	public void sphereDetail(String sphereDetailOverloaded_0){
		_sphereDetail(sphereDetailOverloaded_0);
	}
	private native String _sphereDetail(String sphereDetailOverloaded_0)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().sphereDetail(sphereDetailOverloaded_0);
	}-*/;
	
	public void modelX(String x, String y, String z){
		_modelX(x, y, z);
	}
	private native String _modelX(String x, String y, String z)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().modelX(x, y, z);
	}-*/;
	
	public void modelY(String x, String y, String z){
		_modelY(x, y, z);
	}
	private native String _modelY(String x, String y, String z)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().modelY(x, y, z);
	}-*/;
	
	public void modelZ(String x, String y, String z){
		_modelZ(x, y, z);
	}
	private native String _modelZ(String x, String y, String z)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().modelZ(x, y, z);
	}-*/;
	
	public void ambient(){
		_ambient();
	}
	private native String _ambient()/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().ambient();
	}-*/;
	
	public void ambient(String ambientOverloaded_0, String ambientOverloaded_1){
		_ambient(ambientOverloaded_0, ambientOverloaded_1);
	}
	private native String _ambient(String ambientOverloaded_0, String ambientOverloaded_1)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().ambient(ambientOverloaded_0, ambientOverloaded_1);
	}-*/;
	
	public void emissive(){
		_emissive();
	}
	private native String _emissive()/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().emissive();
	}-*/;
	
	public void emissive(String emissiveOverloaded_0, String emissiveOverloaded_1){
		_emissive(emissiveOverloaded_0, emissiveOverloaded_1);
	}
	private native String _emissive(String emissiveOverloaded_0, String emissiveOverloaded_1)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().emissive(emissiveOverloaded_0, emissiveOverloaded_1);
	}-*/;
	
	public void shininess(String shine){
		_shininess(shine);
	}
	private native String _shininess(String shine)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().shininess(shine);
	}-*/;
	
	public void specular(){
		_specular();
	}
	private native String _specular()/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().specular();
	}-*/;
	
	public void specular(String specularOverloaded_0){
		_specular(specularOverloaded_0);
	}
	private native String _specular(String specularOverloaded_0)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().specular(specularOverloaded_0);
	}-*/;
	
	public void fill(){
		_fill();
	}
	private native String _fill()/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().fill();
	}-*/;
	
	public void fill(String fillOverloaded_0){
		_fill(fillOverloaded_0);
	}
	private native String _fill(String fillOverloaded_0)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().fill(fillOverloaded_0);
	}-*/;
	
	public void fill(String fillOverloaded_0, String fillOverloaded_1){
		_fill(fillOverloaded_0, fillOverloaded_1);
	}
	private native String _fill(String fillOverloaded_0, String fillOverloaded_1)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().fill(fillOverloaded_0, fillOverloaded_1);
	}-*/;
	
	public void fill(String fillOverloaded_0, String fillOverloaded_1, String fillOverloaded_2){
		_fill(fillOverloaded_0, fillOverloaded_1, fillOverloaded_2);
	}
	private native String _fill(String fillOverloaded_0, String fillOverloaded_1, String fillOverloaded_2)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().fill(fillOverloaded_0, fillOverloaded_1, fillOverloaded_2);
	}-*/;
	
	public void fill(String fillOverloaded_0, String fillOverloaded_1, String fillOverloaded_2, String fillOverloaded_3){
		_fill(fillOverloaded_0, fillOverloaded_1, fillOverloaded_2, fillOverloaded_3);
	}
	private native String _fill(String fillOverloaded_0, String fillOverloaded_1, String fillOverloaded_2, String fillOverloaded_3)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().fill(fillOverloaded_0, fillOverloaded_1, fillOverloaded_2, fillOverloaded_3);
	}-*/;
	
	public void stroke(){
		_stroke();
	}
	private native String _stroke()/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().stroke();
	}-*/;
	
	public void stroke(String strokeOverloaded_0){
		_stroke(strokeOverloaded_0);
	}
	private native String _stroke(String strokeOverloaded_0)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().stroke(strokeOverloaded_0);
	}-*/;
	
	public void stroke(String strokeOverloaded_0, String strokeOverloaded_1){
		_stroke(strokeOverloaded_0, strokeOverloaded_1);
	}
	private native String _stroke(String strokeOverloaded_0, String strokeOverloaded_1)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().stroke(strokeOverloaded_0, strokeOverloaded_1);
	}-*/;
	
	public void stroke(String strokeOverloaded_0, String strokeOverloaded_1, String strokeOverloaded_2){
		_stroke(strokeOverloaded_0, strokeOverloaded_1, strokeOverloaded_2);
	}
	private native String _stroke(String strokeOverloaded_0, String strokeOverloaded_1, String strokeOverloaded_2)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().stroke(strokeOverloaded_0, strokeOverloaded_1, strokeOverloaded_2);
	}-*/;
	
	public void stroke(String strokeOverloaded_0, String strokeOverloaded_1, String strokeOverloaded_2, String strokeOverloaded_3){
		_stroke(strokeOverloaded_0, strokeOverloaded_1, strokeOverloaded_2, strokeOverloaded_3);
	}
	private native String _stroke(String strokeOverloaded_0, String strokeOverloaded_1, String strokeOverloaded_2, String strokeOverloaded_3)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().stroke(strokeOverloaded_0, strokeOverloaded_1, strokeOverloaded_2, strokeOverloaded_3);
	}-*/;
	
	public void strokeWeight(String w){
		_strokeWeight(w);
	}
	private native String _strokeWeight(String w)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().strokeWeight(w);
	}-*/;
	
	public void strokeCap(String value){
		_strokeCap(value);
	}
	private native String _strokeCap(String value)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().strokeCap(value);
	}-*/;
	
	public void strokeJoin(String value){
		_strokeJoin(value);
	}
	private native String _strokeJoin(String value)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().strokeJoin(value);
	}-*/;
	
	public void Point(String x, String y){
		_Point(x, y);
	}
	private native String _Point(String x, String y)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().Point(x, y);
	}-*/;
	
	public void point(String x, String y, String z){
		_point(x, y, z);
	}
	private native String _point(String x, String y, String z)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().point(x, y, z);
	}-*/;
	
	public void beginShape(String type){
		_beginShape(type);
	}
	private native String _beginShape(String type)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().beginShape(type);
	}-*/;
	
	public void endShape(String close){
		_endShape(close);
	}
	private native String _endShape(String close)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().endShape(close);
	}-*/;
	
	public void vertex(String x, String y, String x2, String y2, String x3, String y3){
		_vertex(x, y, x2, y2, x3, y3);
	}
	private native String _vertex(String x, String y, String x2, String y2, String x3, String y3)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().vertex(x, y, x2, y2, x3, y3);
	}-*/;
	
	public void vertex(String vertexOverloaded_0, String vertexOverloaded_1, String vertexOverloaded_2){
		_vertex(vertexOverloaded_0, vertexOverloaded_1, vertexOverloaded_2);
	}
	private native String _vertex(String vertexOverloaded_0, String vertexOverloaded_1, String vertexOverloaded_2)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().vertex(vertexOverloaded_0, vertexOverloaded_1, vertexOverloaded_2);
	}-*/;
	
	public void vertex(String vertexOverloaded_0, String vertexOverloaded_1, String vertexOverloaded_2, String vertexOverloaded_3){
		_vertex(vertexOverloaded_0, vertexOverloaded_1, vertexOverloaded_2, vertexOverloaded_3);
	}
	private native String _vertex(String vertexOverloaded_0, String vertexOverloaded_1, String vertexOverloaded_2, String vertexOverloaded_3)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().vertex(vertexOverloaded_0, vertexOverloaded_1, vertexOverloaded_2, vertexOverloaded_3);
	}-*/;
	
	public void vertex(String vertexOverloaded_0){
		_vertex(vertexOverloaded_0);
	}
	private native String _vertex(String vertexOverloaded_0)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().vertex(vertexOverloaded_0);
	}-*/;
	
	public void vertex(String vertexOverloaded_0, String vertexOverloaded_1){
		_vertex(vertexOverloaded_0, vertexOverloaded_1);
	}
	private native String _vertex(String vertexOverloaded_0, String vertexOverloaded_1)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().vertex(vertexOverloaded_0, vertexOverloaded_1);
	}-*/;
	
	public void vertex(String vertexOverloaded_0, String vertexOverloaded_1, String vertexOverloaded_2, String vertexOverloaded_3, String vertexOverloaded_4){
		_vertex(vertexOverloaded_0, vertexOverloaded_1, vertexOverloaded_2, vertexOverloaded_3, vertexOverloaded_4);
	}
	private native String _vertex(String vertexOverloaded_0, String vertexOverloaded_1, String vertexOverloaded_2, String vertexOverloaded_3, String vertexOverloaded_4)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().vertex(vertexOverloaded_0, vertexOverloaded_1, vertexOverloaded_2, vertexOverloaded_3, vertexOverloaded_4);
	}-*/;
	
	public void vertex(String vertexOverloaded_0, String vertexOverloaded_1, String vertexOverloaded_2, String vertexOverloaded_3, String vertexOverloaded_4, String vertexOverloaded_5, String vertexOverloaded_6){
		_vertex(vertexOverloaded_0, vertexOverloaded_1, vertexOverloaded_2, vertexOverloaded_3, vertexOverloaded_4, vertexOverloaded_5, vertexOverloaded_6);
	}
	private native String _vertex(String vertexOverloaded_0, String vertexOverloaded_1, String vertexOverloaded_2, String vertexOverloaded_3, String vertexOverloaded_4, String vertexOverloaded_5, String vertexOverloaded_6)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().vertex(vertexOverloaded_0, vertexOverloaded_1, vertexOverloaded_2, vertexOverloaded_3, vertexOverloaded_4, vertexOverloaded_5, vertexOverloaded_6);
	}-*/;
	
	public void curve(){
		_curve();
	}
	private native String _curve()/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().curve();
	}-*/;
	
	public void curve(String curveOverloaded_0, String curveOverloaded_1, String curveOverloaded_2, String curveOverloaded_3, String curveOverloaded_4, String curveOverloaded_5, String curveOverloaded_6, String curveOverloaded_7, String curveOverloaded_8){
		_curve(curveOverloaded_0, curveOverloaded_1, curveOverloaded_2, curveOverloaded_3, curveOverloaded_4, curveOverloaded_5, curveOverloaded_6, curveOverloaded_7, curveOverloaded_8);
	}
	private native String _curve(String curveOverloaded_0, String curveOverloaded_1, String curveOverloaded_2, String curveOverloaded_3, String curveOverloaded_4, String curveOverloaded_5, String curveOverloaded_6, String curveOverloaded_7, String curveOverloaded_8)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().curve(curveOverloaded_0, curveOverloaded_1, curveOverloaded_2, curveOverloaded_3, curveOverloaded_4, curveOverloaded_5, curveOverloaded_6, curveOverloaded_7, curveOverloaded_8);
	}-*/;
	
	public void curve(String curveOverloaded_0){
		_curve(curveOverloaded_0);
	}
	private native String _curve(String curveOverloaded_0)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().curve(curveOverloaded_0);
	}-*/;
	
	public void curve(String curveOverloaded_0, String curveOverloaded_1){
		_curve(curveOverloaded_0, curveOverloaded_1);
	}
	private native String _curve(String curveOverloaded_0, String curveOverloaded_1)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().curve(curveOverloaded_0, curveOverloaded_1);
	}-*/;
	
	public void curve(String curveOverloaded_0, String curveOverloaded_1, String curveOverloaded_2){
		_curve(curveOverloaded_0, curveOverloaded_1, curveOverloaded_2);
	}
	private native String _curve(String curveOverloaded_0, String curveOverloaded_1, String curveOverloaded_2)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().curve(curveOverloaded_0, curveOverloaded_1, curveOverloaded_2);
	}-*/;
	
	public void curve(String curveOverloaded_0, String curveOverloaded_1, String curveOverloaded_2, String curveOverloaded_3){
		_curve(curveOverloaded_0, curveOverloaded_1, curveOverloaded_2, curveOverloaded_3);
	}
	private native String _curve(String curveOverloaded_0, String curveOverloaded_1, String curveOverloaded_2, String curveOverloaded_3)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().curve(curveOverloaded_0, curveOverloaded_1, curveOverloaded_2, curveOverloaded_3);
	}-*/;
	
	public void curve(String curveOverloaded_0, String curveOverloaded_1, String curveOverloaded_2, String curveOverloaded_3, String curveOverloaded_4){
		_curve(curveOverloaded_0, curveOverloaded_1, curveOverloaded_2, curveOverloaded_3, curveOverloaded_4);
	}
	private native String _curve(String curveOverloaded_0, String curveOverloaded_1, String curveOverloaded_2, String curveOverloaded_3, String curveOverloaded_4)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().curve(curveOverloaded_0, curveOverloaded_1, curveOverloaded_2, curveOverloaded_3, curveOverloaded_4);
	}-*/;
	
	public void curve(String curveOverloaded_0, String curveOverloaded_1, String curveOverloaded_2, String curveOverloaded_3, String curveOverloaded_4, String curveOverloaded_5){
		_curve(curveOverloaded_0, curveOverloaded_1, curveOverloaded_2, curveOverloaded_3, curveOverloaded_4, curveOverloaded_5);
	}
	private native String _curve(String curveOverloaded_0, String curveOverloaded_1, String curveOverloaded_2, String curveOverloaded_3, String curveOverloaded_4, String curveOverloaded_5)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().curve(curveOverloaded_0, curveOverloaded_1, curveOverloaded_2, curveOverloaded_3, curveOverloaded_4, curveOverloaded_5);
	}-*/;
	
	public void curve(String curveOverloaded_0, String curveOverloaded_1, String curveOverloaded_2, String curveOverloaded_3, String curveOverloaded_4, String curveOverloaded_5, String curveOverloaded_6){
		_curve(curveOverloaded_0, curveOverloaded_1, curveOverloaded_2, curveOverloaded_3, curveOverloaded_4, curveOverloaded_5, curveOverloaded_6);
	}
	private native String _curve(String curveOverloaded_0, String curveOverloaded_1, String curveOverloaded_2, String curveOverloaded_3, String curveOverloaded_4, String curveOverloaded_5, String curveOverloaded_6)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().curve(curveOverloaded_0, curveOverloaded_1, curveOverloaded_2, curveOverloaded_3, curveOverloaded_4, curveOverloaded_5, curveOverloaded_6);
	}-*/;
	
	public void curve(String curveOverloaded_0, String curveOverloaded_1, String curveOverloaded_2, String curveOverloaded_3, String curveOverloaded_4, String curveOverloaded_5, String curveOverloaded_6, String curveOverloaded_7){
		_curve(curveOverloaded_0, curveOverloaded_1, curveOverloaded_2, curveOverloaded_3, curveOverloaded_4, curveOverloaded_5, curveOverloaded_6, curveOverloaded_7);
	}
	private native String _curve(String curveOverloaded_0, String curveOverloaded_1, String curveOverloaded_2, String curveOverloaded_3, String curveOverloaded_4, String curveOverloaded_5, String curveOverloaded_6, String curveOverloaded_7)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().curve(curveOverloaded_0, curveOverloaded_1, curveOverloaded_2, curveOverloaded_3, curveOverloaded_4, curveOverloaded_5, curveOverloaded_6, curveOverloaded_7);
	}-*/;
	
	public void curve(String curveOverloaded_0, String curveOverloaded_1, String curveOverloaded_2, String curveOverloaded_3, String curveOverloaded_4, String curveOverloaded_5, String curveOverloaded_6, String curveOverloaded_7, String curveOverloaded_8, String curveOverloaded_9){
		_curve(curveOverloaded_0, curveOverloaded_1, curveOverloaded_2, curveOverloaded_3, curveOverloaded_4, curveOverloaded_5, curveOverloaded_6, curveOverloaded_7, curveOverloaded_8, curveOverloaded_9);
	}
	private native String _curve(String curveOverloaded_0, String curveOverloaded_1, String curveOverloaded_2, String curveOverloaded_3, String curveOverloaded_4, String curveOverloaded_5, String curveOverloaded_6, String curveOverloaded_7, String curveOverloaded_8, String curveOverloaded_9)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().curve(curveOverloaded_0, curveOverloaded_1, curveOverloaded_2, curveOverloaded_3, curveOverloaded_4, curveOverloaded_5, curveOverloaded_6, curveOverloaded_7, curveOverloaded_8, curveOverloaded_9);
	}-*/;
	
	public void curve(String curveOverloaded_0, String curveOverloaded_1, String curveOverloaded_2, String curveOverloaded_3, String curveOverloaded_4, String curveOverloaded_5, String curveOverloaded_6, String curveOverloaded_7, String curveOverloaded_8, String curveOverloaded_9, String curveOverloaded_10){
		_curve(curveOverloaded_0, curveOverloaded_1, curveOverloaded_2, curveOverloaded_3, curveOverloaded_4, curveOverloaded_5, curveOverloaded_6, curveOverloaded_7, curveOverloaded_8, curveOverloaded_9, curveOverloaded_10);
	}
	private native String _curve(String curveOverloaded_0, String curveOverloaded_1, String curveOverloaded_2, String curveOverloaded_3, String curveOverloaded_4, String curveOverloaded_5, String curveOverloaded_6, String curveOverloaded_7, String curveOverloaded_8, String curveOverloaded_9, String curveOverloaded_10)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().curve(curveOverloaded_0, curveOverloaded_1, curveOverloaded_2, curveOverloaded_3, curveOverloaded_4, curveOverloaded_5, curveOverloaded_6, curveOverloaded_7, curveOverloaded_8, curveOverloaded_9, curveOverloaded_10);
	}-*/;
	
	public void curve(String curveOverloaded_0, String curveOverloaded_1, String curveOverloaded_2, String curveOverloaded_3, String curveOverloaded_4, String curveOverloaded_5, String curveOverloaded_6, String curveOverloaded_7, String curveOverloaded_8, String curveOverloaded_9, String curveOverloaded_10, String curveOverloaded_11){
		_curve(curveOverloaded_0, curveOverloaded_1, curveOverloaded_2, curveOverloaded_3, curveOverloaded_4, curveOverloaded_5, curveOverloaded_6, curveOverloaded_7, curveOverloaded_8, curveOverloaded_9, curveOverloaded_10, curveOverloaded_11);
	}
	private native String _curve(String curveOverloaded_0, String curveOverloaded_1, String curveOverloaded_2, String curveOverloaded_3, String curveOverloaded_4, String curveOverloaded_5, String curveOverloaded_6, String curveOverloaded_7, String curveOverloaded_8, String curveOverloaded_9, String curveOverloaded_10, String curveOverloaded_11)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().curve(curveOverloaded_0, curveOverloaded_1, curveOverloaded_2, curveOverloaded_3, curveOverloaded_4, curveOverloaded_5, curveOverloaded_6, curveOverloaded_7, curveOverloaded_8, curveOverloaded_9, curveOverloaded_10, curveOverloaded_11);
	}-*/;
	
	public void curveDetail(){
		_curveDetail();
	}
	private native String _curveDetail()/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().curveDetail();
	}-*/;
	
	public void curveDetail(String curveDetailOverloaded_0){
		_curveDetail(curveDetailOverloaded_0);
	}
	private native String _curveDetail(String curveDetailOverloaded_0)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().curveDetail(curveDetailOverloaded_0);
	}-*/;
	
	public void rectMode(String aRectMode){
		_rectMode(aRectMode);
	}
	private native String _rectMode(String aRectMode)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().rectMode(aRectMode);
	}-*/;
	
	public void ellipseMode(String aEllipseMode){
		_ellipseMode(aEllipseMode);
	}
	private native String _ellipseMode(String aEllipseMode)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().ellipseMode(aEllipseMode);
	}-*/;
	
	public void arc(String x, String y, String width, String height, String start, String stop){
		_arc(x, y, width, height, start, stop);
	}
	private native String _arc(String x, String y, String width, String height, String start, String stop)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().arc(x, y, width, height, start, stop);
	}-*/;
	
	public void line(){
		_line();
	}
	private native String _line()/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().line();
	}-*/;
	
	public void line(String lineOverloaded_0, String lineOverloaded_1, String lineOverloaded_2, String lineOverloaded_3, String lineOverloaded_4, String lineOverloaded_5, String lineOverloaded_6){
		_line(lineOverloaded_0, lineOverloaded_1, lineOverloaded_2, lineOverloaded_3, lineOverloaded_4, lineOverloaded_5, lineOverloaded_6);
	}
	private native String _line(String lineOverloaded_0, String lineOverloaded_1, String lineOverloaded_2, String lineOverloaded_3, String lineOverloaded_4, String lineOverloaded_5, String lineOverloaded_6)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().line(lineOverloaded_0, lineOverloaded_1, lineOverloaded_2, lineOverloaded_3, lineOverloaded_4, lineOverloaded_5, lineOverloaded_6);
	}-*/;
	
	public void line(String lineOverloaded_0){
		_line(lineOverloaded_0);
	}
	private native String _line(String lineOverloaded_0)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().line(lineOverloaded_0);
	}-*/;
	
	public void line(String lineOverloaded_0, String lineOverloaded_1){
		_line(lineOverloaded_0, lineOverloaded_1);
	}
	private native String _line(String lineOverloaded_0, String lineOverloaded_1)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().line(lineOverloaded_0, lineOverloaded_1);
	}-*/;
	
	public void line(String lineOverloaded_0, String lineOverloaded_1, String lineOverloaded_2){
		_line(lineOverloaded_0, lineOverloaded_1, lineOverloaded_2);
	}
	private native String _line(String lineOverloaded_0, String lineOverloaded_1, String lineOverloaded_2)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().line(lineOverloaded_0, lineOverloaded_1, lineOverloaded_2);
	}-*/;
	
	public void line(String lineOverloaded_0, String lineOverloaded_1, String lineOverloaded_2, String lineOverloaded_3){
		_line(lineOverloaded_0, lineOverloaded_1, lineOverloaded_2, lineOverloaded_3);
	}
	private native String _line(String lineOverloaded_0, String lineOverloaded_1, String lineOverloaded_2, String lineOverloaded_3)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().line(lineOverloaded_0, lineOverloaded_1, lineOverloaded_2, lineOverloaded_3);
	}-*/;
	
	public void line(String lineOverloaded_0, String lineOverloaded_1, String lineOverloaded_2, String lineOverloaded_3, String lineOverloaded_4){
		_line(lineOverloaded_0, lineOverloaded_1, lineOverloaded_2, lineOverloaded_3, lineOverloaded_4);
	}
	private native String _line(String lineOverloaded_0, String lineOverloaded_1, String lineOverloaded_2, String lineOverloaded_3, String lineOverloaded_4)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().line(lineOverloaded_0, lineOverloaded_1, lineOverloaded_2, lineOverloaded_3, lineOverloaded_4);
	}-*/;
	
	public void line(String lineOverloaded_0, String lineOverloaded_1, String lineOverloaded_2, String lineOverloaded_3, String lineOverloaded_4, String lineOverloaded_5){
		_line(lineOverloaded_0, lineOverloaded_1, lineOverloaded_2, lineOverloaded_3, lineOverloaded_4, lineOverloaded_5);
	}
	private native String _line(String lineOverloaded_0, String lineOverloaded_1, String lineOverloaded_2, String lineOverloaded_3, String lineOverloaded_4, String lineOverloaded_5)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().line(lineOverloaded_0, lineOverloaded_1, lineOverloaded_2, lineOverloaded_3, lineOverloaded_4, lineOverloaded_5);
	}-*/;
	
	public void bezier(String x1, String y1, String x2, String y2, String x3, String y3, String x4, String y4){
		_bezier(x1, y1, x2, y2, x3, y3, x4, y4);
	}
	private native String _bezier(String x1, String y1, String x2, String y2, String x3, String y3, String x4, String y4)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().bezier(x1, y1, x2, y2, x3, y3, x4, y4);
	}-*/;
	
	public void bezierPoint(String a, String b, String c, String d, String t){
		_bezierPoint(a, b, c, d, t);
	}
	private native String _bezierPoint(String a, String b, String c, String d, String t)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().bezierPoint(a, b, c, d, t);
	}-*/;
	
	public void bezierTangent(String a, String b, String c, String d, String t){
		_bezierTangent(a, b, c, d, t);
	}
	private native String _bezierTangent(String a, String b, String c, String d, String t)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().bezierTangent(a, b, c, d, t);
	}-*/;
	
	public void curvePoint(String a, String b, String c, String d, String t){
		_curvePoint(a, b, c, d, t);
	}
	private native String _curvePoint(String a, String b, String c, String d, String t)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().curvePoint(a, b, c, d, t);
	}-*/;
	
	public void curveTangent(String a, String b, String c, String d, String t){
		_curveTangent(a, b, c, d, t);
	}
	private native String _curveTangent(String a, String b, String c, String d, String t)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().curveTangent(a, b, c, d, t);
	}-*/;
	
	public void triangle(String x1, String y1, String x2, String y2, String x3, String y3){
		_triangle(x1, y1, x2, y2, x3, y3);
	}
	private native String _triangle(String x1, String y1, String x2, String y2, String x3, String y3)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().triangle(x1, y1, x2, y2, x3, y3);
	}-*/;
	
	public void quad(String x1, String y1, String x2, String y2, String x3, String y3, String x4, String y4){
		_quad(x1, y1, x2, y2, x3, y3, x4, y4);
	}
	private native String _quad(String x1, String y1, String x2, String y2, String x3, String y3, String x4, String y4)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().quad(x1, y1, x2, y2, x3, y3, x4, y4);
	}-*/;
	
	public void rect(String x, String y, String width, String height){
		_rect(x, y, width, height);
	}
	private native String _rect(String x, String y, String width, String height)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().rect(x, y, width, height);
	}-*/;
	
	public void ellipse(String x, String y, String width, String height){
		_ellipse(x, y, width, height);
	}
	private native String _ellipse(String x, String y, String width, String height)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().ellipse(x, y, width, height);
	}-*/;
	
	public void normal(String nx, String ny, String nz){
		_normal(nx, ny, nz);
	}
	private native String _normal(String nx, String ny, String nz)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().normal(nx, ny, nz);
	}-*/;
	
	public void normal(String normalOverloaded_0, String normalOverloaded_1, String normalOverloaded_2, String normalOverloaded_3){
		_normal(normalOverloaded_0, normalOverloaded_1, normalOverloaded_2, normalOverloaded_3);
	}
	private native String _normal(String normalOverloaded_0, String normalOverloaded_1, String normalOverloaded_2, String normalOverloaded_3)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().normal(normalOverloaded_0, normalOverloaded_1, normalOverloaded_2, normalOverloaded_3);
	}-*/;
	
	public void save(String file){
		_save(file);
	}
	private native String _save(String file)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().save(file);
	}-*/;
	
	public void PImage(String aWidth, String aHeight, String aFormat){
		_PImage(aWidth, aHeight, aFormat);
	}
	private native String _PImage(String aWidth, String aHeight, String aFormat)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().PImage(aWidth, aHeight, aFormat);
	}-*/;
	
	public void PImage(String PImageOverloaded_0, String PImageOverloaded_1){
		_PImage(PImageOverloaded_0, PImageOverloaded_1);
	}
	private native String _PImage(String PImageOverloaded_0, String PImageOverloaded_1)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().PImage(PImageOverloaded_0, PImageOverloaded_1);
	}-*/;
	
	public void PImage(String PImageOverloaded_0){
		_PImage(PImageOverloaded_0);
	}
	private native String _PImage(String PImageOverloaded_0)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().PImage(PImageOverloaded_0);
	}-*/;
	
	public void PImage(String PImageOverloaded_0, String PImageOverloaded_1, String PImageOverloaded_2, String PImageOverloaded_3){
		_PImage(PImageOverloaded_0, PImageOverloaded_1, PImageOverloaded_2, PImageOverloaded_3);
	}
	private native String _PImage(String PImageOverloaded_0, String PImageOverloaded_1, String PImageOverloaded_2, String PImageOverloaded_3)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().PImage(PImageOverloaded_0, PImageOverloaded_1, PImageOverloaded_2, PImageOverloaded_3);
	}-*/;
	
	public void createImage(String w, String h, String mode){
		_createImage(w, h, mode);
	}
	private native String _createImage(String w, String h, String mode)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().createImage(w, h, mode);
	}-*/;
	
	public void loadImage(String file, String type, String callback){
		_loadImage(file, type, callback);
	}
	private native String _loadImage(String file, String type, String callback)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().loadImage(file, type, callback);
	}-*/;
	
	public void get(String x, String y, String w, String h, String img){
		_get(x, y, w, h, img);
	}
	private native String _get(String x, String y, String w, String h, String img)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().get(x, y, w, h, img);
	}-*/;
	
	public void get(String getOverloaded_0){
		_get(getOverloaded_0);
	}
	private native String _get(String getOverloaded_0)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().get(getOverloaded_0);
	}-*/;
	
	public void get(String getOverloaded_0, String getOverloaded_1, String getOverloaded_2, String getOverloaded_3, String getOverloaded_4, String getOverloaded_5){
		_get(getOverloaded_0, getOverloaded_1, getOverloaded_2, getOverloaded_3, getOverloaded_4, getOverloaded_5);
	}
	private native String _get(String getOverloaded_0, String getOverloaded_1, String getOverloaded_2, String getOverloaded_3, String getOverloaded_4, String getOverloaded_5)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().get(getOverloaded_0, getOverloaded_1, getOverloaded_2, getOverloaded_3, getOverloaded_4, getOverloaded_5);
	}-*/;
	
	public void get(String getOverloaded_0, String getOverloaded_1, String getOverloaded_2, String getOverloaded_3){
		_get(getOverloaded_0, getOverloaded_1, getOverloaded_2, getOverloaded_3);
	}
	private native String _get(String getOverloaded_0, String getOverloaded_1, String getOverloaded_2, String getOverloaded_3)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().get(getOverloaded_0, getOverloaded_1, getOverloaded_2, getOverloaded_3);
	}-*/;
	
	public void get(String getOverloaded_0, String getOverloaded_1, String getOverloaded_2){
		_get(getOverloaded_0, getOverloaded_1, getOverloaded_2);
	}
	private native String _get(String getOverloaded_0, String getOverloaded_1, String getOverloaded_2)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().get(getOverloaded_0, getOverloaded_1, getOverloaded_2);
	}-*/;
	
	public void get(String getOverloaded_0, String getOverloaded_1){
		_get(getOverloaded_0, getOverloaded_1);
	}
	private native String _get(String getOverloaded_0, String getOverloaded_1)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().get(getOverloaded_0, getOverloaded_1);
	}-*/;
	
	public void createGraphics(String w, String h){
		_createGraphics(w, h);
	}
	private native String _createGraphics(String w, String h)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().createGraphics(w, h);
	}-*/;
	
	public void set(String x, String y, String obj, String img){
		_set(x, y, obj, img);
	}
	private native String _set(String x, String y, String obj, String img)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().set(x, y, obj, img);
	}-*/;
	
	public void set(String setOverloaded_0, String setOverloaded_1, String setOverloaded_2, String setOverloaded_3, String setOverloaded_4){
		_set(setOverloaded_0, setOverloaded_1, setOverloaded_2, setOverloaded_3, setOverloaded_4);
	}
	private native String _set(String setOverloaded_0, String setOverloaded_1, String setOverloaded_2, String setOverloaded_3, String setOverloaded_4)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().set(setOverloaded_0, setOverloaded_1, setOverloaded_2, setOverloaded_3, setOverloaded_4);
	}-*/;
	
	public void background(){
		_background();
	}
	private native String _background()/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().background();
	}-*/;
	
	public void background(String backgroundOverloaded_0){
		_background(Integer.parseInt(backgroundOverloaded_0));//こんなかんじで、JavaScriptに変形できればいいんだよな、、
	}
	private native String _background(int backgroundOverloaded_0)/*-{//この部分の検出はガチでやる必要がある
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().background(backgroundOverloaded_0);
	}-*/;
	
	public void background(String backgroundOverloaded_0, String backgroundOverloaded_1){
		_background(backgroundOverloaded_0, backgroundOverloaded_1);
	}
	private native String _background(String backgroundOverloaded_0, String backgroundOverloaded_1)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().background(backgroundOverloaded_0, backgroundOverloaded_1);
	}-*/;
	
	public void getImage(String img){
		_getImage(img);
	}
	private native String _getImage(String img)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().getImage(img);
	}-*/;
	
	public void image(String img, String x, String y, String w, String h){
		_image(img, x, y, w, h);
	}
	private native String _image(String img, String x, String y, String w, String h)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().image(img, x, y, w, h);
	}-*/;
	
	public void clear(String x, String y, String width, String height){
		_clear(x, y, width, height);
	}
	private native String _clear(String x, String y, String width, String height)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().clear(x, y, width, height);
	}-*/;
	
	public void clear(String clearOverloaded_0){
		_clear(clearOverloaded_0);
	}
	private native String _clear(String clearOverloaded_0)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().clear(clearOverloaded_0);
	}-*/;
	
	public void tint(){
		_tint();
	}
	private native String _tint()/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().tint();
	}-*/;
	
	public void noTint(){
		_noTint();
	}
	private native String _noTint()/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().noTint();
	}-*/;
	
	public void copy(String src, String sx, String sy, String sw, String sh, String dx, String dy, String dw, String dh){
		_copy(src, sx, sy, sw, sh, dx, dy, dw, dh);
	}
	private native String _copy(String src, String sx, String sy, String sw, String sh, String dx, String dy, String dw, String dh)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().copy(src, sx, sy, sw, sh, dx, dy, dw, dh);
	}-*/;
	
	public void blend(String src, String sx, String sy, String sw, String sh, String dx, String dy, String dw, String dh, String mode, String pimgdest){
		_blend(src, sx, sy, sw, sh, dx, dy, dw, dh, mode, pimgdest);
	}
	private native String _blend(String src, String sx, String sy, String sw, String sh, String dx, String dy, String dw, String dh, String mode, String pimgdest)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().blend(src, sx, sy, sw, sh, dx, dy, dw, dh, mode, pimgdest);
	}-*/;
	
	public void blend(String blendOverloaded_0, String blendOverloaded_1, String blendOverloaded_2, String blendOverloaded_3, String blendOverloaded_4, String blendOverloaded_5, String blendOverloaded_6, String blendOverloaded_7, String blendOverloaded_8, String blendOverloaded_9){
		_blend(blendOverloaded_0, blendOverloaded_1, blendOverloaded_2, blendOverloaded_3, blendOverloaded_4, blendOverloaded_5, blendOverloaded_6, blendOverloaded_7, blendOverloaded_8, blendOverloaded_9);
	}
	private native String _blend(String blendOverloaded_0, String blendOverloaded_1, String blendOverloaded_2, String blendOverloaded_3, String blendOverloaded_4, String blendOverloaded_5, String blendOverloaded_6, String blendOverloaded_7, String blendOverloaded_8, String blendOverloaded_9)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().blend(blendOverloaded_0, blendOverloaded_1, blendOverloaded_2, blendOverloaded_3, blendOverloaded_4, blendOverloaded_5, blendOverloaded_6, blendOverloaded_7, blendOverloaded_8, blendOverloaded_9);
	}-*/;
	
	public void blend(String blendOverloaded_0, String blendOverloaded_1, String blendOverloaded_2, String blendOverloaded_3, String blendOverloaded_4, String blendOverloaded_5, String blendOverloaded_6, String blendOverloaded_7, String blendOverloaded_8, String blendOverloaded_9, String blendOverloaded_10, String blendOverloaded_11){
		_blend(blendOverloaded_0, blendOverloaded_1, blendOverloaded_2, blendOverloaded_3, blendOverloaded_4, blendOverloaded_5, blendOverloaded_6, blendOverloaded_7, blendOverloaded_8, blendOverloaded_9, blendOverloaded_10, blendOverloaded_11);
	}
	private native String _blend(String blendOverloaded_0, String blendOverloaded_1, String blendOverloaded_2, String blendOverloaded_3, String blendOverloaded_4, String blendOverloaded_5, String blendOverloaded_6, String blendOverloaded_7, String blendOverloaded_8, String blendOverloaded_9, String blendOverloaded_10, String blendOverloaded_11)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().blend(blendOverloaded_0, blendOverloaded_1, blendOverloaded_2, blendOverloaded_3, blendOverloaded_4, blendOverloaded_5, blendOverloaded_6, blendOverloaded_7, blendOverloaded_8, blendOverloaded_9, blendOverloaded_10, blendOverloaded_11);
	}-*/;
	
	public void intersect(String sx1, String sy1, String sx2, String sy2, String dx1, String dy1, String dx2, String dy2){
		_intersect(sx1, sy1, sx2, sy2, dx1, dy1, dx2, dy2);
	}
	private native String _intersect(String sx1, String sy1, String sx2, String sy2, String dx1, String dy1, String dx2, String dy2)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().intersect(sx1, sy1, sx2, sy2, dx1, dy1, dx2, dy2);
	}-*/;
	
	public void blit_resize(String img, String srcX1, String srcY1, String srcX2, String srcY2, String destPixels, String screenW, String screenH, String destX1, String destY1, String destX2, String destY2, String mode){
		_blit_resize(img, srcX1, srcY1, srcX2, srcY2, destPixels, screenW, screenH, destX1, destY1, destX2, destY2, mode);
	}
	private native String _blit_resize(String img, String srcX1, String srcY1, String srcX2, String srcY2, String destPixels, String screenW, String screenH, String destX1, String destY1, String destX2, String destY2, String mode)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().blit_resize(img, srcX1, srcY1, srcX2, srcY2, destPixels, screenW, screenH, destX1, destY1, destX2, destY2, mode);
	}-*/;
	
	public void loadFont(String name){
		_loadFont(name);
	}
	private native String _loadFont(String name)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().loadFont(name);
	}-*/;
	
	public void textFont(String name, String size){
		_textFont(name, size);
	}
	private native String _textFont(String name, String size)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().textFont(name, size);
	}-*/;
	
	public void textSize(String size){
		_textSize(size);
	}
	private native String _textSize(String size)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().textSize(size);
	}-*/;
	
	public void glyphLook(String font, String chr){
		_glyphLook(font, chr);
	}
	private native String _glyphLook(String font, String chr)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().glyphLook(font, chr);
	}-*/;
	
	public void text(){
		_text();
	}
	private native String _text()/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().text();
	}-*/;
	
	public void text(String textOverloaded_0){
		_text(textOverloaded_0);
	}
	private native String _text(String textOverloaded_0)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().text(textOverloaded_0);
	}-*/;
	
	public void text(String textOverloaded_0, String textOverloaded_1){
		_text(textOverloaded_0, textOverloaded_1);
	}
	private native String _text(String textOverloaded_0, String textOverloaded_1)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().text(textOverloaded_0, textOverloaded_1);
	}-*/;
	
	public void text(String textOverloaded_0, String textOverloaded_1, String textOverloaded_2, String textOverloaded_3){
		_text(textOverloaded_0, textOverloaded_1, textOverloaded_2, textOverloaded_3);
	}
	private native String _text(String textOverloaded_0, String textOverloaded_1, String textOverloaded_2, String textOverloaded_3)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().text(textOverloaded_0, textOverloaded_1, textOverloaded_2, textOverloaded_3);
	}-*/;
	
	public void text(String textOverloaded_0, String textOverloaded_1, String textOverloaded_2){
		_text(textOverloaded_0, textOverloaded_1, textOverloaded_2);
	}
	private native String _text(String textOverloaded_0, String textOverloaded_1, String textOverloaded_2)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().text(textOverloaded_0, textOverloaded_1, textOverloaded_2);
	}-*/;
	
	public void text(String textOverloaded_0, String textOverloaded_1, String textOverloaded_2, String textOverloaded_3, String textOverloaded_4){
		_text(textOverloaded_0, textOverloaded_1, textOverloaded_2, textOverloaded_3, textOverloaded_4);
	}
	private native String _text(String textOverloaded_0, String textOverloaded_1, String textOverloaded_2, String textOverloaded_3, String textOverloaded_4)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().text(textOverloaded_0, textOverloaded_1, textOverloaded_2, textOverloaded_3, textOverloaded_4);
	}-*/;
	
	public void text(String textOverloaded_0, String textOverloaded_1, String textOverloaded_2, String textOverloaded_3, String textOverloaded_4, String textOverloaded_5){
		_text(textOverloaded_0, textOverloaded_1, textOverloaded_2, textOverloaded_3, textOverloaded_4, textOverloaded_5);
	}
	private native String _text(String textOverloaded_0, String textOverloaded_1, String textOverloaded_2, String textOverloaded_3, String textOverloaded_4, String textOverloaded_5)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().text(textOverloaded_0, textOverloaded_1, textOverloaded_2, textOverloaded_3, textOverloaded_4, textOverloaded_5);
	}-*/;
	
	public void text(String textOverloaded_0, String textOverloaded_1, String textOverloaded_2, String textOverloaded_3, String textOverloaded_4, String textOverloaded_5, String textOverloaded_6){
		_text(textOverloaded_0, textOverloaded_1, textOverloaded_2, textOverloaded_3, textOverloaded_4, textOverloaded_5, textOverloaded_6);
	}
	private native String _text(String textOverloaded_0, String textOverloaded_1, String textOverloaded_2, String textOverloaded_3, String textOverloaded_4, String textOverloaded_5, String textOverloaded_6)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().text(textOverloaded_0, textOverloaded_1, textOverloaded_2, textOverloaded_3, textOverloaded_4, textOverloaded_5, textOverloaded_6);
	}-*/;
	
	public void loadGlyph(String url){
		_loadGlyph(url);
	}
	private native String _loadGlyph(String url)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().loadGlyph(url);
	}-*/;
	
	public void regex(String needle, String hay){
		_regex(needle, hay);
	}
	private native String _regex(String needle, String hay)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().regex(needle, hay);
	}-*/;
	
	public void buildPath(String d){
		_buildPath(d);
	}
	private native String _buildPath(String d)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().buildPath(d);
	}-*/;
	
	public void parseSVGFontse(String svg){
		_parseSVGFontse(svg);
	}
	private native String _parseSVGFontse(String svg)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().parseSVGFontse(svg);
	}-*/;
	
	public void loadXML(){
		_loadXML();
	}
	private native String _loadXML()/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().loadXML();
	}-*/;
	
	public void extendClass(String obj, String args, String fn){
		_extendClass(obj, args, fn);
	}
	private native String _extendClass(String obj, String args, String fn)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().extendClass(obj, args, fn);
	}-*/;
	
	public void extendClass(String extendClassOverloaded_0, String extendClassOverloaded_1, String extendClassOverloaded_2, String extendClassOverloaded_3){
		_extendClass(extendClassOverloaded_0, extendClassOverloaded_1, extendClassOverloaded_2, extendClassOverloaded_3);
	}
	private native String _extendClass(String extendClassOverloaded_0, String extendClassOverloaded_1, String extendClassOverloaded_2, String extendClassOverloaded_3)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().extendClass(extendClassOverloaded_0, extendClassOverloaded_1, extendClassOverloaded_2, extendClassOverloaded_3);
	}-*/;
	
	public void addMethod(String object, String name, String fn){
		_addMethod(object, name, fn);
	}
	private native String _addMethod(String object, String name, String fn)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().addMethod(object, name, fn);
	}-*/;
	
	public void init(String code){
		_init(code);
	}
	private native String _init(String code)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().init(code);
	}-*/;
	
	public void attach(String elem, String type, String fn){
		_attach(elem, type, fn);
	}
	private native String _attach(String elem, String type, String fn)/*-{
		this.@com.kissaki.client.ProcessingImplements::getInstanceOfJSObject()().attach(elem, type, fn);
	}-*/;
	
}
