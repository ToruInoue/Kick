package com.kissaki.client;

import com.google.gwt.core.client.JavaScriptObject;

public interface ProcessingInterface{
	
	
	
	public void Processing(String aElement, String aCode);
	
	public void parse(String aCode, String p);
	
	public void imageModeCorner(String x, String y, String w, String h, String whAreSizes);
	
	public void imageModeCorners(String x, String y, String w, String h, String whAreSizes);
	
	public void imageModeCenter(String x, String y, String w, String h, String whAreSizes);
	
	public void buildProcessing(String curElement);
	
	public void Char(String chr);
	
	public void printMatrixHelper(String elements);
	
	public void load();
	
	public void load(String loadOverloaded_0, String loadOverloaded_1);
	
	public void load(String loadOverloaded_0);
	
	public void mult(String matrix);
	
	public void virtHashCode(String obj);
	
	public void virtEquals(String obj, String other);
	
	public void HashMap();
	
	public void HashMap(String HashMapOverloaded_0, String HashMapOverloaded_1);
	
	public void HashMap(String HashMapOverloaded_0);
	
	public void Iterator(String conversion, String removeItem);
	
	public void Set(String conversion, String isIn, String removeItem);
	
	public void Entry(String pair);
	
	public void color(String aValue1, String aValue2, String aValue3, String aValue4);
	
	public void verifyChannel(String aColor);
	
	public void lerpColor(String c1, String c2, String amt);
	
	public void colorMode(String mode, String range1, String range2, String range3, String range4);
	
	public void colorMode(String colorModeOverloaded_0, String colorModeOverloaded_1, String colorModeOverloaded_2, String colorModeOverloaded_3, String colorModeOverloaded_4, String colorModeOverloaded_5);
	
	public void colorMode(String colorModeOverloaded_0, String colorModeOverloaded_1, String colorModeOverloaded_2);
	
	public void translate(String x, String y, String z);
	
	public void scale(String x, String y, String z);
	
	public void applyMatrix();
	
	public void applyMatrix(String applyMatrixOverloaded_0);
	
	public void rotate(String angleInRadians);
	
	public void loop();
	
	public void frameRate(String aRate);
	
	public void cursor();
	
	public void cursor(String cursorOverloaded_0, String cursorOverloaded_1);
	
	public void cursor(String cursorOverloaded_0);
	
	public void cursor(String cursorOverloaded_0, String cursorOverloaded_1, String cursorOverloaded_2, String cursorOverloaded_3);
	
	public void cursor(String cursorOverloaded_0, String cursorOverloaded_1, String cursorOverloaded_2);
	
	public void Import(String lib);
	
	public void decToBin(String value, String numBitsInValue);
	
	public void unbinary(String binaryString);
	
	public void unbinary(String unbinaryOverloaded_0, String unbinaryOverloaded_1);
	
	public void decimalToHex(String d, String padding);
	
	public void hex(String value, String len);
	
	public void loadStrings(String url);
	
	public void loadBytes(String url);
	
	public void matchAll(String aString, String aRegExp);
	
	public void equals(String str);
	
	public void println(String message);
	
	public void print(String message);
	
	public void str(String val);
	
	public void str(String strOverloaded_0, String strOverloaded_1);
	
	public void random(String aMin, String aMax);
	
	public void random(String randomOverloaded_0, String randomOverloaded_1, String randomOverloaded_2);
	
	public void noiseGen(String x, String y);
	
	public void smoothedNoise(String x, String y);
	
	public void interpolate(String a, String b, String x);
	
	public void interpolatedNoise(String x, String y);
	
	public void perlinNoise_2D(String x, String y);
	
	public void size(String aWidth, String aHeight, String aMode);
	
	public void uniformf(String programObj, String varName, String varValue);
	
	public void uniformi(String programObj, String varName, String varValue);
	
	public void vertexAttribPointer(String programObj, String varName, String size, String VBO);
	
	public void uniformMatrix(String programObj, String varName, String transpose, String matrix);
	
	public void lightFalloff(String constant, String linear, String quadratic);
	
	public void lightSpecular(String r, String g, String b);
	
	public void spotLight(String r, String g, String b, String x, String y, String z, String nx, String ny, String nz, String angle, String concentration);
	
	public void camera(String eyeX, String eyeY, String eyeZ, String centerX, String centerY, String centerZ, String upX, String upY, String upZ);
	
	public void camera(String cameraOverloaded_0);
	
	public void perspective(String fov, String aspect, String near, String far);
	
	public void perspective(String perspectiveOverloaded_0);
	
	public void perspective(String perspectiveOverloaded_0, String perspectiveOverloaded_1, String perspectiveOverloaded_2);
	
	public void frustum(String left, String right, String bottom, String top, String near, String far);
	
	public void ortho(String left, String right, String bottom, String top, String near, String far);
	
	public void ortho(String orthoOverloaded_0);
	
	public void sphereDetail(String ures, String vres);
	
	public void sphereDetail(String sphereDetailOverloaded_0);
	
	public void modelX(String x, String y, String z);
	
	public void modelY(String x, String y, String z);
	
	public void modelZ(String x, String y, String z);
	
	public void ambient();
	
	public void ambient(String ambientOverloaded_0, String ambientOverloaded_1);
	
	public void emissive();
	
	public void emissive(String emissiveOverloaded_0, String emissiveOverloaded_1);
	
	public void shininess(String shine);
	
	public void specular();
	
	public void specular(String specularOverloaded_0);
	
	public void fill();
	
	public void fill(String fillOverloaded_0);
	
	public void fill(String fillOverloaded_0, String fillOverloaded_1);
	
	public void fill(String fillOverloaded_0, String fillOverloaded_1, String fillOverloaded_2);
	
	public void fill(String fillOverloaded_0, String fillOverloaded_1, String fillOverloaded_2, String fillOverloaded_3);
	
	public void stroke();
	
	public void stroke(String strokeOverloaded_0);
	
	public void stroke(String strokeOverloaded_0, String strokeOverloaded_1);
	
	public void stroke(String strokeOverloaded_0, String strokeOverloaded_1, String strokeOverloaded_2);
	
	public void stroke(String strokeOverloaded_0, String strokeOverloaded_1, String strokeOverloaded_2, String strokeOverloaded_3);
	
	public void strokeWeight(String w);
	
	public void strokeCap(String value);
	
	public void strokeJoin(String value);
	
	public void Point(String x, String y);
	
	public void point(String x, String y, String z);
	
	public void beginShape(String type);
	
	public void endShape(String close);
	
	public void vertex(String x, String y, String x2, String y2, String x3, String y3);
	
	public void vertex(String vertexOverloaded_0, String vertexOverloaded_1, String vertexOverloaded_2);
	
	public void vertex(String vertexOverloaded_0, String vertexOverloaded_1, String vertexOverloaded_2, String vertexOverloaded_3);
	
	public void vertex(String vertexOverloaded_0);
	
	public void vertex(String vertexOverloaded_0, String vertexOverloaded_1);
	
	public void vertex(String vertexOverloaded_0, String vertexOverloaded_1, String vertexOverloaded_2, String vertexOverloaded_3, String vertexOverloaded_4);
	
	public void vertex(String vertexOverloaded_0, String vertexOverloaded_1, String vertexOverloaded_2, String vertexOverloaded_3, String vertexOverloaded_4, String vertexOverloaded_5, String vertexOverloaded_6);
	
	public void curve();
	
	public void curve(String curveOverloaded_0, String curveOverloaded_1, String curveOverloaded_2, String curveOverloaded_3, String curveOverloaded_4, String curveOverloaded_5, String curveOverloaded_6, String curveOverloaded_7, String curveOverloaded_8);
	
	public void curve(String curveOverloaded_0);
	
	public void curve(String curveOverloaded_0, String curveOverloaded_1);
	
	public void curve(String curveOverloaded_0, String curveOverloaded_1, String curveOverloaded_2);
	
	public void curve(String curveOverloaded_0, String curveOverloaded_1, String curveOverloaded_2, String curveOverloaded_3);
	
	public void curve(String curveOverloaded_0, String curveOverloaded_1, String curveOverloaded_2, String curveOverloaded_3, String curveOverloaded_4);
	
	public void curve(String curveOverloaded_0, String curveOverloaded_1, String curveOverloaded_2, String curveOverloaded_3, String curveOverloaded_4, String curveOverloaded_5);
	
	public void curve(String curveOverloaded_0, String curveOverloaded_1, String curveOverloaded_2, String curveOverloaded_3, String curveOverloaded_4, String curveOverloaded_5, String curveOverloaded_6);
	
	public void curve(String curveOverloaded_0, String curveOverloaded_1, String curveOverloaded_2, String curveOverloaded_3, String curveOverloaded_4, String curveOverloaded_5, String curveOverloaded_6, String curveOverloaded_7);
	
	public void curve(String curveOverloaded_0, String curveOverloaded_1, String curveOverloaded_2, String curveOverloaded_3, String curveOverloaded_4, String curveOverloaded_5, String curveOverloaded_6, String curveOverloaded_7, String curveOverloaded_8, String curveOverloaded_9);
	
	public void curve(String curveOverloaded_0, String curveOverloaded_1, String curveOverloaded_2, String curveOverloaded_3, String curveOverloaded_4, String curveOverloaded_5, String curveOverloaded_6, String curveOverloaded_7, String curveOverloaded_8, String curveOverloaded_9, String curveOverloaded_10);
	
	public void curve(String curveOverloaded_0, String curveOverloaded_1, String curveOverloaded_2, String curveOverloaded_3, String curveOverloaded_4, String curveOverloaded_5, String curveOverloaded_6, String curveOverloaded_7, String curveOverloaded_8, String curveOverloaded_9, String curveOverloaded_10, String curveOverloaded_11);
	
	public void curveDetail();
	
	public void curveDetail(String curveDetailOverloaded_0);
	
	public void rectMode(String aRectMode);
	
	public void ellipseMode(String aEllipseMode);
	
	public void arc(String x, String y, String width, String height, String start, String stop);
	
	public void line();
	
	public void line(String lineOverloaded_0, String lineOverloaded_1, String lineOverloaded_2, String lineOverloaded_3, String lineOverloaded_4, String lineOverloaded_5, String lineOverloaded_6);
	
	public void line(String lineOverloaded_0);
	
	public void line(String lineOverloaded_0, String lineOverloaded_1);
	
	public void line(String lineOverloaded_0, String lineOverloaded_1, String lineOverloaded_2);
	
	public void line(String lineOverloaded_0, String lineOverloaded_1, String lineOverloaded_2, String lineOverloaded_3);
	
	public void line(String lineOverloaded_0, String lineOverloaded_1, String lineOverloaded_2, String lineOverloaded_3, String lineOverloaded_4);
	
	public void line(String lineOverloaded_0, String lineOverloaded_1, String lineOverloaded_2, String lineOverloaded_3, String lineOverloaded_4, String lineOverloaded_5);
	
	public void bezier(String x1, String y1, String x2, String y2, String x3, String y3, String x4, String y4);
	
	public void bezierPoint(String a, String b, String c, String d, String t);
	
	public void bezierTangent(String a, String b, String c, String d, String t);
	
	public void curvePoint(String a, String b, String c, String d, String t);
	
	public void curveTangent(String a, String b, String c, String d, String t);
	
	public void triangle(String x1, String y1, String x2, String y2, String x3, String y3);
	
	public void quad(String x1, String y1, String x2, String y2, String x3, String y3, String x4, String y4);
	
	public void rect(String x, String y, String width, String height);
	
	public void ellipse(String x, String y, String width, String height);
	
	public void normal(String nx, String ny, String nz);
	
	public void normal(String normalOverloaded_0, String normalOverloaded_1, String normalOverloaded_2, String normalOverloaded_3);
	
	public void save(String file);
	
	public void PImage(String aWidth, String aHeight, String aFormat);
	
	public void PImage(String PImageOverloaded_0, String PImageOverloaded_1);
	
	public void PImage(String PImageOverloaded_0);
	
	public void PImage(String PImageOverloaded_0, String PImageOverloaded_1, String PImageOverloaded_2, String PImageOverloaded_3);
	
	public void createImage(String w, String h, String mode);
	
	public void loadImage(String file, String type, String callback);
	
	public void get(String x, String y, String w, String h, String img);
	
	public void get(String getOverloaded_0);
	
	public void get(String getOverloaded_0, String getOverloaded_1, String getOverloaded_2, String getOverloaded_3, String getOverloaded_4, String getOverloaded_5);
	
	public void get(String getOverloaded_0, String getOverloaded_1, String getOverloaded_2, String getOverloaded_3);
	
	public void get(String getOverloaded_0, String getOverloaded_1, String getOverloaded_2);
	
	public void get(String getOverloaded_0, String getOverloaded_1);
	
	public void createGraphics(String w, String h);
	
	public void set(String x, String y, String obj, String img);
	
	public void set(String setOverloaded_0, String setOverloaded_1, String setOverloaded_2, String setOverloaded_3, String setOverloaded_4);
	
	public void background();
	
	public void background(String backgroundOverloaded_0);
	
	public void background(String backgroundOverloaded_0, String backgroundOverloaded_1);
	
	public void getImage(String img);
	
	public void image(String img, String x, String y, String w, String h);
	
	public void clear(String x, String y, String width, String height);
	
	public void clear(String clearOverloaded_0);
	
	public void tint();
	
	public void noTint();
	
	public void copy(String src, String sx, String sy, String sw, String sh, String dx, String dy, String dw, String dh);
	
	public void blend(String src, String sx, String sy, String sw, String sh, String dx, String dy, String dw, String dh, String mode, String pimgdest);
	
	public void blend(String blendOverloaded_0, String blendOverloaded_1, String blendOverloaded_2, String blendOverloaded_3, String blendOverloaded_4, String blendOverloaded_5, String blendOverloaded_6, String blendOverloaded_7, String blendOverloaded_8, String blendOverloaded_9);
	
	public void blend(String blendOverloaded_0, String blendOverloaded_1, String blendOverloaded_2, String blendOverloaded_3, String blendOverloaded_4, String blendOverloaded_5, String blendOverloaded_6, String blendOverloaded_7, String blendOverloaded_8, String blendOverloaded_9, String blendOverloaded_10, String blendOverloaded_11);
	
	public void intersect(String sx1, String sy1, String sx2, String sy2, String dx1, String dy1, String dx2, String dy2);
	
	public void blit_resize(String img, String srcX1, String srcY1, String srcX2, String srcY2, String destPixels, String screenW, String screenH, String destX1, String destY1, String destX2, String destY2, String mode);
	
	public void loadFont(String name);
	
	public void textFont(String name, String size);
	
	public void textSize(String size);
	
	public void glyphLook(String font, String chr);
	
	public void text();
	
	public void text(String textOverloaded_0);
	
	public void text(String textOverloaded_0, String textOverloaded_1);
	
	public void text(String textOverloaded_0, String textOverloaded_1, String textOverloaded_2, String textOverloaded_3);
	
	public void text(String textOverloaded_0, String textOverloaded_1, String textOverloaded_2);
	
	public void text(String textOverloaded_0, String textOverloaded_1, String textOverloaded_2, String textOverloaded_3, String textOverloaded_4);
	
	public void text(String textOverloaded_0, String textOverloaded_1, String textOverloaded_2, String textOverloaded_3, String textOverloaded_4, String textOverloaded_5);
	
	public void text(String textOverloaded_0, String textOverloaded_1, String textOverloaded_2, String textOverloaded_3, String textOverloaded_4, String textOverloaded_5, String textOverloaded_6);
	
	public void loadGlyph(String url);
	
	public void regex(String needle, String hay);
	
	public void buildPath(String d);
	
	public void parseSVGFontse(String svg);
	
	public void loadXML();
	
	public void extendClass(String obj, String args, String fn);
	
	public void extendClass(String extendClassOverloaded_0, String extendClassOverloaded_1, String extendClassOverloaded_2, String extendClassOverloaded_3);
	
	public void addMethod(String object, String name, String fn);
	
	public void init(String code);
	
	public void attach(String elem, String type, String fn);
	
}
