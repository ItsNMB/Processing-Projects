import processing.pdf.*;
PVector tl, tr, bl, br;

void setup() {
  size(2480, 3508, PDF, "test.pdf");
  smooth(8);
  createFont("sans.ttf", 32, true)

  tl = new PVector(width*0.05, height*0.05);
  tr = new PVector(width-(width*0.05), height*0.05);
  bl = new PVector(width*0.05, height-(height*0.05));
  br = new PVector(width-(width*0.05), height-(height*0.05));
}

void draw() {
  // Draw something good here
  background(255);
  line(tl, tr);
  line(tr, br);
  line(br, bl);
  line(tl, bl);

  // Exit the program
  exit();
}

void line(PVector p1, PVector p2) {
	line(p1.x, p1.y, p2.x, p2.y);
}