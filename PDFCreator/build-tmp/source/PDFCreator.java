import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import processing.pdf.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class PDFCreator extends PApplet {


PVector tl, tr, bl, br;

public void setup() {
  
  tl = new PVector(width*0.05f, height*0.05f);
  tr = new PVector(width-(width*0.05f), height*0.05f);
  bl = new PVector(width*0.05f, height-(height*0.05f));
  br = new PVector(width-(width*0.05f), height-(height*0.05f));
}

public void draw() {
  // Draw something good here
  background(255);
  line(tl, tr);
  line(tr, br);
  line(br, bl);
  line(tl, bl);

  // Exit the program
  exit();
}

public void line(PVector p1, PVector p2) {
	line(p1.x, p1.y, p2.x, p2.y);
}
  public void settings() {  size(2480, 3508, PDF, "test.pdf"); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "PDFCreator" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
