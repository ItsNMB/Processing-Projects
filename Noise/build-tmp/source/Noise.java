import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class Noise extends PApplet {

float noiseVal;
float noiseScale=0.02f;

public void setup() {
	
}

public void draw() {
	for (int y = 0; y < height; y++) {
		for (int x = 0; x < width/2; x++) {
			noiseDetail(3, 0.5f);
			noiseVal = noise((mouseX+x) * noiseScale, (mouseY+y) * noiseScale);
			set(x, y, color(noiseVal*255));
			noiseDetail(8, 0.65f);
			noiseVal = noise((mouseX + x + width/2) * noiseScale,
				(mouseY + y) * noiseScale);
			set(x + width/2, y, color(noiseVal*255));
		}
	}
}
  public void settings() { 	size(400, 400, P2D); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "Noise" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
