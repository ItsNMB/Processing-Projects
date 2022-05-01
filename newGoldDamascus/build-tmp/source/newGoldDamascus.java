import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import controlP5.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class newGoldDamascus extends PApplet {



ControlP5 cp5;

int cell_n = 4;        // 4
int line_n = 1000;      // 1000
float step = 0.01f;     // 0.01
float amplitude = 2.0f; // 2.0

PVector[][] cells;
PVector[] points;
int cell_size;

int[] colors;


public void setup() {
	
	frameRate(170);
	background(0);
	// blendMode(SCREEN);


	cp5 = new ControlP5(this);
	cp5.addSlider("CELL-N").setPosition(5, 5).setSize(200, 20).setRange(1, 10).setValue(1);
	cp5.addSlider("LINE-N").setPosition(5, 25).setSize(200, 20).setRange(10, 3000).setValue(1000);
	cp5.addSlider("STEP").setPosition(5, 45).setSize(200, 20).setRange(0.001f, 1).setValue(0.01f);
	cp5.addSlider("AMPLITUDE").setPosition(5, 65).setSize(200, 20).setRange(1, 20).setValue(2.0f);
	cp5.addButton("CLEAR").setValue(0).setPosition(5, 85).setSize(200, 20);

	cells = new PVector[cell_n * width / height][cell_n];
	points = new PVector[line_n];
	colors = new int[5];

	for (int i = 0; i < cell_n * width / height; i++)
		for (int j = 0; j < cell_n; j++)
			cells[i][j] = new PVector(random(1), random(1));

	for (int i = 0; i < line_n; i++)
		points[i] = new PVector(random(width), random(height));

	colors[0] = color(0xffB8860B);
	colors[1] = color(0xffCA9C08);
	colors[2] = color(0xffDCB306);
	colors[3] = color(0xffEDC903);
	colors[4] = color(0xffFFDF00);
}

public void draw() {
	cell_n = (int) cp5.getController("CELL-N").getValue();
	line_n = (int) cp5.getController("LINE-N").getValue();
	step = cp5.getController("STEP").getValue();
	amplitude = cp5.getController("AMPLITUDE").getValue();

	cell_size = height / cell_n;

	for (int i = 0; i < line_n; i++) {
		int hue = PApplet.parseInt(calculate_d(points[i].x, points[i].y) / cell_size * 400) % 360;

		PVector v = curl(points[i].x, points[i].y);

		// stroke(color(`hsb(${hue}, 70%, 50%)`));
		int index = PApplet.parseInt(map(hue, 0, 360, 0, 4));
		stroke(colors[index]);
		strokeWeight(2);
		line(points[i].x, points[i].y, points[i].x + v.x, points[i].y + v.y);

		points[i].x += v.x;
		points[i].y += v.y;
	}

	// noStroke();
	// fill(0);
	// rect(0, 0, 210, 100);

	surface.setTitle("" + (int) frameRate);
}

public void CLEAR(int theValue) {
  	background(0);
}

public float calculate_d(float x, float y) {
	int xn = PApplet.parseInt(x / cell_size);
	int yn = PApplet.parseInt(y / cell_size);
	float min_d = 1e3f;
	for (int xx = max(-1, -xn); xx <= min(1, cells.length - xn - 1); xx++) {
		for (int yy = max(-1, -yn); yy <= min(1, cell_n - yn - 1); yy++) {
			float d = dist((xn + xx + cells[xn + xx][yn + yy].x) * cell_size,
			               (yn + yy + cells[xn + xx][yn + yy].y) * cell_size, x, y);
			min_d = min(min_d, d);
		}
	}

	return min_d;
}

public PVector curl(float x, float y) {
	float x1 = calculate_d(x + 1, y) / cell_size + noise((x + 1) * step, y * step) * amplitude;
	float x2 = calculate_d(x - 1, y) / cell_size + noise((x - 1) * step, y * step) * amplitude;
	float y1 = calculate_d(x, y + 1) / cell_size + noise(x * step, (y + 1) * step) * amplitude;
	float y2 = calculate_d(x, y - 1) / cell_size + noise(x * step, (y - 1) * step) * amplitude;

	return new PVector((y1 - y2) / 2 * cell_size, (x2 - x1) / 2 * cell_size);
}
  public void settings() { 	size(1920, 1080, P2D); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "newGoldDamascus" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
