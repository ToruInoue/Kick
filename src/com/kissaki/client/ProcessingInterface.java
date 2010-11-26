package com.kissaki.client;

import com.google.gwt.core.client.JavaScriptObject;

public interface ProcessingInterface{
	
	
	
	public void Processing(JavaScriptObject aElement, String aCode);
	
	public void parse(JavaScriptObject aCode, String p);
	
	public void imageModeCorner(JavaScriptObject x, JavaScriptObject y, JavaScriptObject w, JavaScriptObject h, JavaScriptObject whAreSizes);
	
	public void imageModeCorners(JavaScriptObject x, JavaScriptObject y, JavaScriptObject w, JavaScriptObject h, JavaScriptObject whAreSizes);
	
	public void imageModeCenter(JavaScriptObject x, JavaScriptObject y, JavaScriptObject w, JavaScriptObject h, JavaScriptObject whAreSizes);
	
	public void buildProcessing(String curElement);
	
	public void Char(String chr);
	
	public void printMatrixHelper(JavaScriptObject elements);
	
	public void load();
	
	public void load(JavaScriptObject loadOverloaded_0, JavaScriptObject loadOverloaded_1);
	
	public void load(JavaScriptObject loadOverloaded_0);
	
	public void mult(JavaScriptObject matrix);
	
	public void virtHashCode(JavaScriptObject obj);
	
	public void virtEquals(JavaScriptObject obj, JavaScriptObject other);
	
	public void HashMap();
	
	public void HashMap(JavaScriptObject HashMapOverloaded_0, JavaScriptObject HashMapOverloaded_1);
	
	public void HashMap(JavaScriptObject HashMapOverloaded_0);
	
	public void Iterator(String conversion, String removeItem);
	
	public void Set(JavaScriptObject conversion, JavaScriptObject isIn, JavaScriptObject removeItem);
	
	public void Entry(JavaScriptObject pair);
	
	public void color(JavaScriptObject aValue1, JavaScriptObject aValue2, JavaScriptObject aValue3, JavaScriptObject aValue4);
	
	public void verifyChannel(JavaScriptObject aColor);
	
	public void lerpColor(JavaScriptObject c1, JavaScriptObject c2, JavaScriptObject amt);
	
	public void colorMode(String mode, String range1, String range2, String range3, String range4);
	
	public void colorMode(JavaScriptObject colorModeOverloaded_0, JavaScriptObject colorModeOverloaded_1, JavaScriptObject colorModeOverloaded_2, JavaScriptObject colorModeOverloaded_3, JavaScriptObject colorModeOverloaded_4, JavaScriptObject colorModeOverloaded_5);
	
	public void colorMode(JavaScriptObject colorModeOverloaded_0, JavaScriptObject colorModeOverloaded_1, JavaScriptObject colorModeOverloaded_2);
	
	public void translate(JavaScriptObject x, JavaScriptObject y, JavaScriptObject z);
	
	public void scale(JavaScriptObject x, JavaScriptObject y, JavaScriptObject z);
	
	public void applyMatrix();
	
	public void applyMatrix(JavaScriptObject applyMatrixOverloaded_0);
	
	public void rotate(JavaScriptObject angleInRadians);
	
	public void loop();
	
	public void frameRate(JavaScriptObject aRate);
	
	public void cursor();
	
	public void cursor(JavaScriptObject cursorOverloaded_0, JavaScriptObject cursorOverloaded_1);
	
	public void cursor(JavaScriptObject cursorOverloaded_0);
	
	public void cursor(JavaScriptObject cursorOverloaded_0, JavaScriptObject cursorOverloaded_1, JavaScriptObject cursorOverloaded_2, JavaScriptObject cursorOverloaded_3);
	
	public void cursor(JavaScriptObject cursorOverloaded_0, JavaScriptObject cursorOverloaded_1, JavaScriptObject cursorOverloaded_2);
	
	public void Import(JavaScriptObject lib);
	
	public void decToBin(JavaScriptObject value, JavaScriptObject numBitsInValue);
	
	public void unbinary(String binaryString);
	
	public void unbinary(JavaScriptObject unbinaryOverloaded_0, JavaScriptObject unbinaryOverloaded_1);
	
	public void decimalToHex(JavaScriptObject d, JavaScriptObject padding);
	
	public void hex(JavaScriptObject value, JavaScriptObject len);
	
	public void loadStrings(JavaScriptObject url);
	
	public void loadBytes(JavaScriptObject url);
	
	public void matchAll(JavaScriptObject aString, JavaScriptObject aRegExp);
	
	public void equals(JavaScriptObject str);
	
	public void println(JavaScriptObject message);
	
	public void print(JavaScriptObject message);
	
	public void str(String val);
	
	public void str(JavaScriptObject strOverloaded_0, JavaScriptObject strOverloaded_1);
	
	public void random(JavaScriptObject aMin, JavaScriptObject aMax);
	
	public void random(JavaScriptObject randomOverloaded_0, JavaScriptObject randomOverloaded_1, JavaScriptObject randomOverloaded_2);
	
	public void noiseGen(JavaScriptObject x, JavaScriptObject y);
	
	public void smoothedNoise(JavaScriptObject x, JavaScriptObject y);
	
	public void interpolate(JavaScriptObject a, JavaScriptObject b, JavaScriptObject x);
	
	public void interpolatedNoise(JavaScriptObject x, JavaScriptObject y);
	
	public void perlinNoise_2D(JavaScriptObject x, JavaScriptObject y);
	
	public void size(String aWidth, String aHeight, String aMode);
	
	public void uniformf(JavaScriptObject programObj, JavaScriptObject varName, JavaScriptObject varValue);
	
	public void uniformi(JavaScriptObject programObj, JavaScriptObject varName, JavaScriptObject varValue);
	
	public void vertexAttribPointer(JavaScriptObject programObj, JavaScriptObject varName, JavaScriptObject size, JavaScriptObject VBO);
	
	public void uniformMatrix(JavaScriptObject programObj, JavaScriptObject varName, JavaScriptObject transpose, JavaScriptObject matrix);
	
	public void lightFalloff(JavaScriptObject constant, JavaScriptObject linear, JavaScriptObject quadratic);
	
	public void lightSpecular(JavaScriptObject r, JavaScriptObject g, JavaScriptObject b);
	
	public void spotLight(JavaScriptObject r, JavaScriptObject g, JavaScriptObject b, JavaScriptObject x, JavaScriptObject y, JavaScriptObject z, JavaScriptObject nx, JavaScriptObject ny, JavaScriptObject nz, JavaScriptObject angle, JavaScriptObject concentration);
	
	public void camera(String eyeX, String eyeY, String eyeZ, String centerX, String centerY, String centerZ, String upX, String upY, String upZ);
	
	public void camera(JavaScriptObject cameraOverloaded_0);
	
	public void perspective(String fov, String aspect, String near, String far);
	
	public void perspective(JavaScriptObject perspectiveOverloaded_0);
	
	public void perspective(JavaScriptObject perspectiveOverloaded_0, JavaScriptObject perspectiveOverloaded_1, JavaScriptObject perspectiveOverloaded_2);
	
	public void frustum(JavaScriptObject left, JavaScriptObject right, String bottom, String top, JavaScriptObject near, JavaScriptObject far);
	
	public void ortho(JavaScriptObject left, JavaScriptObject right, JavaScriptObject bottom, JavaScriptObject top, JavaScriptObject near, JavaScriptObject far);
	
	public void ortho(JavaScriptObject orthoOverloaded_0);
	
	public void sphereDetail(String ures, String vres);
	
	public void sphereDetail(JavaScriptObject sphereDetailOverloaded_0);
	
	public void modelX(JavaScriptObject x, JavaScriptObject y, JavaScriptObject z);
	
	public void modelY(JavaScriptObject x, JavaScriptObject y, JavaScriptObject z);
	
	public void modelZ(JavaScriptObject x, JavaScriptObject y, JavaScriptObject z);
	
	public void ambient();
	
	public void ambient(JavaScriptObject ambientOverloaded_0, JavaScriptObject ambientOverloaded_1);
	
	public void emissive();
	
	public void emissive(JavaScriptObject emissiveOverloaded_0, JavaScriptObject emissiveOverloaded_1);
	
	public void shininess(JavaScriptObject shine);
	
	public void specular();
	
	public void specular(JavaScriptObject specularOverloaded_0);
	
	public void fill();
	
	public void fill(JavaScriptObject fillOverloaded_0);
	
	public void fill(JavaScriptObject fillOverloaded_0, JavaScriptObject fillOverloaded_1);
	
	public void fill(JavaScriptObject fillOverloaded_0, JavaScriptObject fillOverloaded_1, JavaScriptObject fillOverloaded_2);
	
	public void fill(JavaScriptObject fillOverloaded_0, JavaScriptObject fillOverloaded_1, JavaScriptObject fillOverloaded_2, JavaScriptObject fillOverloaded_3);
	
	public void stroke();
	
	public void stroke(JavaScriptObject strokeOverloaded_0);
	
	public void stroke(JavaScriptObject strokeOverloaded_0, JavaScriptObject strokeOverloaded_1);
	
	public void stroke(JavaScriptObject strokeOverloaded_0, JavaScriptObject strokeOverloaded_1, JavaScriptObject strokeOverloaded_2);
	
	public void stroke(JavaScriptObject strokeOverloaded_0, JavaScriptObject strokeOverloaded_1, JavaScriptObject strokeOverloaded_2, JavaScriptObject strokeOverloaded_3);
	
	public void strokeWeight(JavaScriptObject w);
	
	public void strokeCap(JavaScriptObject value);
	
	public void strokeJoin(JavaScriptObject value);
	
	public void Point(JavaScriptObject x, JavaScriptObject y);
	
	public void point(JavaScriptObject x, JavaScriptObject y, JavaScriptObject z);
	
	public void beginShape(JavaScriptObject type);
	
	public void endShape(JavaScriptObject close);
	
	public void vertex(JavaScriptObject x, JavaScriptObject y, String x2, String y2, String x3, String y3);
	
	public void vertex(JavaScriptObject vertexOverloaded_0, JavaScriptObject vertexOverloaded_1, JavaScriptObject vertexOverloaded_2);
	
	public void vertex(JavaScriptObject vertexOverloaded_0, JavaScriptObject vertexOverloaded_1, JavaScriptObject vertexOverloaded_2, JavaScriptObject vertexOverloaded_3);
	
	public void vertex(JavaScriptObject vertexOverloaded_0);
	
	public void vertex(JavaScriptObject vertexOverloaded_0, JavaScriptObject vertexOverloaded_1);
	
	public void vertex(JavaScriptObject vertexOverloaded_0, JavaScriptObject vertexOverloaded_1, JavaScriptObject vertexOverloaded_2, JavaScriptObject vertexOverloaded_3, JavaScriptObject vertexOverloaded_4);
	
	public void vertex(JavaScriptObject vertexOverloaded_0, JavaScriptObject vertexOverloaded_1, JavaScriptObject vertexOverloaded_2, JavaScriptObject vertexOverloaded_3, JavaScriptObject vertexOverloaded_4, JavaScriptObject vertexOverloaded_5, JavaScriptObject vertexOverloaded_6);
	
	public void curve();
	
	public void curve(JavaScriptObject curveOverloaded_0, JavaScriptObject curveOverloaded_1, JavaScriptObject curveOverloaded_2, JavaScriptObject curveOverloaded_3, JavaScriptObject curveOverloaded_4, JavaScriptObject curveOverloaded_5, JavaScriptObject curveOverloaded_6, JavaScriptObject curveOverloaded_7, JavaScriptObject curveOverloaded_8);
	
	public void curve(JavaScriptObject curveOverloaded_0);
	
	public void curve(JavaScriptObject curveOverloaded_0, JavaScriptObject curveOverloaded_1);
	
	public void curve(JavaScriptObject curveOverloaded_0, JavaScriptObject curveOverloaded_1, JavaScriptObject curveOverloaded_2);
	
	public void curve(JavaScriptObject curveOverloaded_0, JavaScriptObject curveOverloaded_1, JavaScriptObject curveOverloaded_2, JavaScriptObject curveOverloaded_3);
	
	public void curve(JavaScriptObject curveOverloaded_0, JavaScriptObject curveOverloaded_1, JavaScriptObject curveOverloaded_2, JavaScriptObject curveOverloaded_3, JavaScriptObject curveOverloaded_4);
	
	public void curve(JavaScriptObject curveOverloaded_0, JavaScriptObject curveOverloaded_1, JavaScriptObject curveOverloaded_2, JavaScriptObject curveOverloaded_3, JavaScriptObject curveOverloaded_4, JavaScriptObject curveOverloaded_5);
	
	public void curve(JavaScriptObject curveOverloaded_0, JavaScriptObject curveOverloaded_1, JavaScriptObject curveOverloaded_2, JavaScriptObject curveOverloaded_3, JavaScriptObject curveOverloaded_4, JavaScriptObject curveOverloaded_5, JavaScriptObject curveOverloaded_6);
	
	public void curve(JavaScriptObject curveOverloaded_0, JavaScriptObject curveOverloaded_1, JavaScriptObject curveOverloaded_2, JavaScriptObject curveOverloaded_3, JavaScriptObject curveOverloaded_4, JavaScriptObject curveOverloaded_5, JavaScriptObject curveOverloaded_6, JavaScriptObject curveOverloaded_7);
	
	public void curve(JavaScriptObject curveOverloaded_0, JavaScriptObject curveOverloaded_1, JavaScriptObject curveOverloaded_2, JavaScriptObject curveOverloaded_3, JavaScriptObject curveOverloaded_4, JavaScriptObject curveOverloaded_5, JavaScriptObject curveOverloaded_6, JavaScriptObject curveOverloaded_7, JavaScriptObject curveOverloaded_8, JavaScriptObject curveOverloaded_9);
	
	public void curve(JavaScriptObject curveOverloaded_0, JavaScriptObject curveOverloaded_1, JavaScriptObject curveOverloaded_2, JavaScriptObject curveOverloaded_3, JavaScriptObject curveOverloaded_4, JavaScriptObject curveOverloaded_5, JavaScriptObject curveOverloaded_6, JavaScriptObject curveOverloaded_7, JavaScriptObject curveOverloaded_8, JavaScriptObject curveOverloaded_9, JavaScriptObject curveOverloaded_10);
	
	public void curve(JavaScriptObject curveOverloaded_0, JavaScriptObject curveOverloaded_1, JavaScriptObject curveOverloaded_2, JavaScriptObject curveOverloaded_3, JavaScriptObject curveOverloaded_4, JavaScriptObject curveOverloaded_5, JavaScriptObject curveOverloaded_6, JavaScriptObject curveOverloaded_7, JavaScriptObject curveOverloaded_8, JavaScriptObject curveOverloaded_9, JavaScriptObject curveOverloaded_10, JavaScriptObject curveOverloaded_11);
	
	public void curveDetail();
	
	public void curveDetail(JavaScriptObject curveDetailOverloaded_0);
	
	public void rectMode(JavaScriptObject aRectMode);
	
	public void ellipseMode(JavaScriptObject aEllipseMode);
	
	public void arc(JavaScriptObject x, JavaScriptObject y, JavaScriptObject width, JavaScriptObject height, JavaScriptObject start, JavaScriptObject stop);
	
	public void line();
	
	public void line(JavaScriptObject lineOverloaded_0, JavaScriptObject lineOverloaded_1, JavaScriptObject lineOverloaded_2, JavaScriptObject lineOverloaded_3, JavaScriptObject lineOverloaded_4, JavaScriptObject lineOverloaded_5, JavaScriptObject lineOverloaded_6);
	
	public void line(JavaScriptObject lineOverloaded_0);
	
	public void line(JavaScriptObject lineOverloaded_0, JavaScriptObject lineOverloaded_1);
	
	public void line(JavaScriptObject lineOverloaded_0, JavaScriptObject lineOverloaded_1, JavaScriptObject lineOverloaded_2);
	
	public void line(JavaScriptObject lineOverloaded_0, JavaScriptObject lineOverloaded_1, JavaScriptObject lineOverloaded_2, JavaScriptObject lineOverloaded_3);
	
	public void line(JavaScriptObject lineOverloaded_0, JavaScriptObject lineOverloaded_1, JavaScriptObject lineOverloaded_2, JavaScriptObject lineOverloaded_3, JavaScriptObject lineOverloaded_4);
	
	public void line(JavaScriptObject lineOverloaded_0, JavaScriptObject lineOverloaded_1, JavaScriptObject lineOverloaded_2, JavaScriptObject lineOverloaded_3, JavaScriptObject lineOverloaded_4, JavaScriptObject lineOverloaded_5);
	
	public void bezier(JavaScriptObject x1, JavaScriptObject y1, JavaScriptObject x2, JavaScriptObject y2, JavaScriptObject x3, JavaScriptObject y3, JavaScriptObject x4, JavaScriptObject y4);
	
	public void bezierPoint(JavaScriptObject a, JavaScriptObject b, JavaScriptObject c, JavaScriptObject d, JavaScriptObject t);
	
	public void bezierTangent(JavaScriptObject a, JavaScriptObject b, JavaScriptObject c, JavaScriptObject d, JavaScriptObject t);
	
	public void curvePoint(JavaScriptObject a, JavaScriptObject b, JavaScriptObject c, JavaScriptObject d, JavaScriptObject t);
	
	public void curveTangent(JavaScriptObject a, JavaScriptObject b, JavaScriptObject c, JavaScriptObject d, JavaScriptObject t);
	
	public void triangle(JavaScriptObject x1, JavaScriptObject y1, JavaScriptObject x2, JavaScriptObject y2, JavaScriptObject x3, JavaScriptObject y3);
	
	public void quad(JavaScriptObject x1, JavaScriptObject y1, JavaScriptObject x2, JavaScriptObject y2, JavaScriptObject x3, JavaScriptObject y3, JavaScriptObject x4, JavaScriptObject y4);
	
	public void rect(JavaScriptObject x, JavaScriptObject y, JavaScriptObject width, JavaScriptObject height);
	
	public void ellipse(String x, String y, String width, String height);
	
	public void normal(JavaScriptObject nx, JavaScriptObject ny, JavaScriptObject nz);
	
	public void normal(JavaScriptObject normalOverloaded_0, JavaScriptObject normalOverloaded_1, JavaScriptObject normalOverloaded_2, JavaScriptObject normalOverloaded_3);
	
	public void save(String file);
	
	public void PImage(JavaScriptObject aWidth, JavaScriptObject aHeight, String aFormat);
	
	public void PImage(JavaScriptObject PImageOverloaded_0, JavaScriptObject PImageOverloaded_1);
	
	public void PImage(JavaScriptObject PImageOverloaded_0);
	
	public void PImage(JavaScriptObject PImageOverloaded_0, JavaScriptObject PImageOverloaded_1, JavaScriptObject PImageOverloaded_2, JavaScriptObject PImageOverloaded_3);
	
	public void createImage(JavaScriptObject w, JavaScriptObject h, JavaScriptObject mode);
	
	public void loadImage(JavaScriptObject file, JavaScriptObject type, String callback);
	
	public void get(JavaScriptObject x, JavaScriptObject y, JavaScriptObject w, JavaScriptObject h, JavaScriptObject img);
	
	public void get(JavaScriptObject getOverloaded_0);
	
	public void get(JavaScriptObject getOverloaded_0, JavaScriptObject getOverloaded_1, JavaScriptObject getOverloaded_2, JavaScriptObject getOverloaded_3, JavaScriptObject getOverloaded_4, JavaScriptObject getOverloaded_5);
	
	public void get(JavaScriptObject getOverloaded_0, JavaScriptObject getOverloaded_1, JavaScriptObject getOverloaded_2, JavaScriptObject getOverloaded_3);
	
	public void get(JavaScriptObject getOverloaded_0, JavaScriptObject getOverloaded_1, JavaScriptObject getOverloaded_2);
	
	public void get(JavaScriptObject getOverloaded_0, JavaScriptObject getOverloaded_1);
	
	public void createGraphics(JavaScriptObject w, JavaScriptObject h);
	
	public void set(JavaScriptObject x, JavaScriptObject y, JavaScriptObject obj, JavaScriptObject img);
	
	public void set(JavaScriptObject setOverloaded_0, JavaScriptObject setOverloaded_1, JavaScriptObject setOverloaded_2, JavaScriptObject setOverloaded_3, JavaScriptObject setOverloaded_4);
	
	public void background();
	
	public void background(int backgroundOverloaded_0);
	
	public void background(JavaScriptObject backgroundOverloaded_0, JavaScriptObject backgroundOverloaded_1);
	
	public void getImage(JavaScriptObject img);
	
	public void image(JavaScriptObject img, JavaScriptObject x, JavaScriptObject y, JavaScriptObject w, JavaScriptObject h);
	
	public void clear(JavaScriptObject x, JavaScriptObject y, JavaScriptObject width, JavaScriptObject height);
	
	public void clear(JavaScriptObject clearOverloaded_0);
	
	public void tint();
	
	public void noTint();
	
	public void copy(JavaScriptObject src, JavaScriptObject sx, JavaScriptObject sy, JavaScriptObject sw, JavaScriptObject sh, JavaScriptObject dx, JavaScriptObject dy, String dw, String dh);
	
	public void blend(JavaScriptObject src, JavaScriptObject sx, JavaScriptObject sy, JavaScriptObject sw, JavaScriptObject sh, JavaScriptObject dx, JavaScriptObject dy, JavaScriptObject dw, JavaScriptObject dh, JavaScriptObject mode, String pimgdest);
	
	public void blend(JavaScriptObject blendOverloaded_0, JavaScriptObject blendOverloaded_1, JavaScriptObject blendOverloaded_2, JavaScriptObject blendOverloaded_3, JavaScriptObject blendOverloaded_4, JavaScriptObject blendOverloaded_5, JavaScriptObject blendOverloaded_6, JavaScriptObject blendOverloaded_7, JavaScriptObject blendOverloaded_8, JavaScriptObject blendOverloaded_9);
	
	public void blend(JavaScriptObject blendOverloaded_0, JavaScriptObject blendOverloaded_1, JavaScriptObject blendOverloaded_2, JavaScriptObject blendOverloaded_3, JavaScriptObject blendOverloaded_4, JavaScriptObject blendOverloaded_5, JavaScriptObject blendOverloaded_6, JavaScriptObject blendOverloaded_7, JavaScriptObject blendOverloaded_8, JavaScriptObject blendOverloaded_9, JavaScriptObject blendOverloaded_10, JavaScriptObject blendOverloaded_11);
	
	public void intersect(JavaScriptObject sx1, JavaScriptObject sy1, JavaScriptObject sx2, JavaScriptObject sy2, JavaScriptObject dx1, JavaScriptObject dy1, JavaScriptObject dx2, JavaScriptObject dy2);
	
	public void blit_resize(JavaScriptObject img, JavaScriptObject srcX1, JavaScriptObject srcY1, JavaScriptObject srcX2, JavaScriptObject srcY2, JavaScriptObject destPixels, JavaScriptObject screenW, JavaScriptObject screenH, JavaScriptObject destX1, JavaScriptObject destY1, JavaScriptObject destX2, JavaScriptObject destY2, JavaScriptObject mode);
	
	public void loadFont(JavaScriptObject name);
	
	public void textFont(JavaScriptObject name, JavaScriptObject size);
	
	public void textSize(JavaScriptObject size);
	
	public void glyphLook(JavaScriptObject font, JavaScriptObject chr);
	
	public void text();
	
	public void text(JavaScriptObject textOverloaded_0);
	
	public void text(JavaScriptObject textOverloaded_0, JavaScriptObject textOverloaded_1);
	
	public void text(JavaScriptObject textOverloaded_0, JavaScriptObject textOverloaded_1, JavaScriptObject textOverloaded_2, JavaScriptObject textOverloaded_3);
	
	public void text(JavaScriptObject textOverloaded_0, JavaScriptObject textOverloaded_1, JavaScriptObject textOverloaded_2);
	
	public void text(JavaScriptObject textOverloaded_0, JavaScriptObject textOverloaded_1, JavaScriptObject textOverloaded_2, JavaScriptObject textOverloaded_3, JavaScriptObject textOverloaded_4);
	
	public void text(JavaScriptObject textOverloaded_0, JavaScriptObject textOverloaded_1, JavaScriptObject textOverloaded_2, JavaScriptObject textOverloaded_3, JavaScriptObject textOverloaded_4, JavaScriptObject textOverloaded_5);
	
	public void text(JavaScriptObject textOverloaded_0, JavaScriptObject textOverloaded_1, JavaScriptObject textOverloaded_2, JavaScriptObject textOverloaded_3, JavaScriptObject textOverloaded_4, JavaScriptObject textOverloaded_5, JavaScriptObject textOverloaded_6);
	
	public void loadGlyph(JavaScriptObject url);
	
	public void regex(JavaScriptObject needle, JavaScriptObject hay);
	
	public void buildPath(JavaScriptObject d);
	
	public void parseSVGFontse(String svg);
	
	public void loadXML();
	
	public void extendClass(JavaScriptObject obj, JavaScriptObject args, String fn);
	
	public void extendClass(JavaScriptObject extendClassOverloaded_0, JavaScriptObject extendClassOverloaded_1, JavaScriptObject extendClassOverloaded_2, JavaScriptObject extendClassOverloaded_3);
	
	public void addMethod(String object, JavaScriptObject name, JavaScriptObject fn);
	
	public void init(String code);
	
	public void attach(JavaScriptObject elem, JavaScriptObject type, JavaScriptObject fn);
	
}
