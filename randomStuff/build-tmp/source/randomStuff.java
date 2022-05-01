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

public class randomStuff extends PApplet {



public void setup() {
	String[] data = loadStrings("http://127.0.0.1:4040/status");
	for (int i = 0; i < data.length; i++)
    println(data[i]);
}
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "randomStuff" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
