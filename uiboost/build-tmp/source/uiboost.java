import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import uibooster.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class uiboost extends PApplet {

/* Example for creating a slider selection dialog.
 * This kind of dialog is blocking and waits for confirmation.
 *  
 * Author: Nick 'Milchreis' Müller
 */
 


UiBooster booster;
Integer numberOfHotDogs;

public void setup() {
   
  background(10);

  booster = new UiBooster();
  numberOfHotDogs = booster.showSlider(
    "How many HotDogs do you want?",    // question
    "Your order",                       // title
    0,                                  // range: start value
    10,                                 // range: end value
    2,                                  // initial value
    5,                                  // steps for ticks with value
    1);                                 // steps for ticks
}

public void draw() {
    textAlign(CENTER, CENTER);
    textSize(32);
    text("You want " + numberOfHotDogs + " HotDogs", width/2, height/2);
}
  public void settings() {  size(800, 400); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "uiboost" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
