import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.io.File; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class normalizePixel extends PApplet {



File imgFile;
PImage img, render;
int state;

float brightness;

public void setup() {
	
	frameRate(170);
	surface.setResizable(true);
	surface.setLocation(200, 200);
	state = 0;
	selectInput("Select a file to process:", "fileSelected");
}

public void draw() {
	background(0);
	if (state == 2) {
		if (mousePressed && mouseButton == LEFT && pmouseX != mouseX) {
			brightness = map(mouseX, 0, width, 0, 255);
			render();
		}
		image(mousePressed&&mouseButton==RIGHT ? img : render, 0, 0, width, height);
	}
	surface.setTitle("PixelProcessing | FPS: " + (int)frameRate);
}

public void fileSelected(File selection) {
  if (selection == null) {
    println("Window was closed or the user hit cancel.");
  } else {
    println("User selected " + selection.getAbsolutePath());
    imgFile = new File(selection.getAbsolutePath());
    img = loadImage(imgFile.getAbsolutePath());
    render = img.copy();
    // surface.setSize(img.width, img.height);
    state = 1;
    render();
  }
}


public void render() {
	int hit = (int)random(img.pixels.length);
	for (int i = 0; i < img.pixels.length; i++) {
		int c = img.get(i % img.width, i / img.width);
		float r = red(c), g = green(c), b = blue(c);
		float nr = 0, ng = 0, nb = 0;
		int highest = 0;
		if (r > g && r > b) {
			nr = brightness;
			ng = brightness * (g/r);
			nb = brightness * (b/r);
			highest = 1;
		} else if (g > r && g > b) {
			nr = brightness * (r/g);
			ng = brightness;
			nb = brightness * (b/g);
			highest = 2;
		} else if (b > g && b > r) {
			nr = brightness * (r/b);
			ng = brightness * (g/b);
			nb = brightness;
			highest = 3;
		}

		if (false) {
			printlnf("ORIGINAL: [ %d, %d, %d ]", (int)r, (int)g, (int)b);
			if (highest == 1) printlnf("HIGH: %d   [ %f, %f, %f ]", highest, 1.0f, g/r, b/r);
			else if (highest == 2) printlnf("HIGH: %d   [ %f, %f, %f ]", highest, r/g, 1.0f, b/g);
			else if (highest == 3) printlnf("HIGH: %d   [ %f, %f, %f ]", highest, r/b, g/b, 1.0f);
			printlnf("RENDERED: [ %d, %d, %d ]\n", (int)nr, (int)ng, (int)nb);
		}

		c = color(nr, ng, nb);
		render.set(i % img.width, i / img.width, c);
	}

	state = 2;
}

public void printlnf(String format, Object... args) {
    System.out.println(String.format(format, args));
}
  public void settings() { 	size(1920, 1080, P2D); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "normalizePixel" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
