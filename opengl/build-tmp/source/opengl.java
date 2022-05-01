import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import processing.opengl.*; 
import javax.media.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class opengl extends PApplet {




PGraphicsOpenGL pgl;
GL gl;

public void setup() {
	
	colorMode( RGB, 1.0f );
	hint( ENABLE_OPENGL_4X_SMOOTH );
	pgl = (PGraphicsOpenGL) g;
	gl = pgl.gl;
	gl.setSwapInterval(1);
	initGL();
	
}

public void draw() {
	pgl.beginGL();
  	
	pgl.endGL();
	getOpenGLErrors();
}

public void initGL() {
	
}

public void getOpenGLErrors() {
  int error = gl.glGetError();
  switch (error) {
    case 1280 :
      println("GL_INVALID_ENUM - An invalid enumerant was passed to an OpenGL command.");
    break;
    case 1282 :
      println("GL_INVALID_OPERATION - An OpenGL command was issued that was invalid or inappropriate for the current state.");
    break;
    case 1281 :
      println("GL_INVALID_VALUE - A value was passed to OpenGL that was outside the allowed range.");
    break;
    case 1285 :
      println("GL_OUT_OF_MEMORY - OpenGL was unable to allocate enough memory to process a command.");
    break;
    case 1283 :
      println("GL_STACK_OVERFLOW - A command caused an OpenGL stack to overflow.");
    break;
    case 1284 :
      println("GL_STACK_UNDERFLOW - A command caused an OpenGL stack to underflow.");
    break;
    case 32817 :
      println("GL_TABLE_TOO_LARGE");
    break;
  }
}
  public void settings() { 	size( 1920, 1080, OPENGL ); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "opengl" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
