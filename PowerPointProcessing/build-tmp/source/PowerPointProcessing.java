import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.awt.BorderLayout; 
import java.awt.Color; 
import java.awt.Container; 
import javax.swing.JFrame; 
import javax.swing.JScrollPane; 
import javax.swing.JTextPane; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class PowerPointProcessing extends PApplet {









ImageController imgc;
Logger logger;

public void setup() {
	
	imgc = new ImageController();
	imgc.addImage("img1.jpg");
	imgc.addImage("img2.jpg");
	imgc.addImage("img3.jpg");
	logger = new Logger();
	logf("test");
}

public void draw() {
	imgc.draw();
}

public void keyPressed() {
	if (keyCode == RIGHT) imgc.next();
	if (keyCode == LEFT) imgc.prev();
}


public class ImageController {
	SlideImage[] images;
	int current, target, total;
	float x, w;

	public ImageController() {
		this.images = new SlideImage[0];
		this.target = 0;
		this.current = 0;
		this.total = 0;
		this.w = width;
	}

	public void addImage(String fileName) {
		this.images = (SlideImage[]) append(this.images, new SlideImage(fileName, this.images.length));
		this.total = this.images.length - 1;
	}

	public void draw() {
		for (SlideImage img : this.images) {
			img.move(0.2f);
			img.show();
		}
		this.images[leftIndex()].show();
		this.images[rightIndex()].show();
		this.images[target].show();
	}

	public int leftIndex() {
		return constrain(target - 1, 0, total);
	}

	public int rightIndex() {
		return constrain(target + 1, 0, total);
	}

	public void next() {
		int tmp = this.target;
		this.target++;
		this.target = constrain(this.target, 0, total);
		if (tmp != this.target) this.update();
		println("Set target to " + this.target);
	}

	public void prev() {
		int tmp = this.target;
		this.target--;
		this.target = constrain(this.target, 0, total);
		if (tmp != this.target) {
			this.update();
			println("Set target to " + this.target);
		}
	}

	public void update() {
		for (SlideImage img : this.images) {
			if (img.index < this.target) img.dx += this.w;
			if (img.index > this.target) img.dx -= this.w;

			println("X: " + img.x + ", DX: " + img.dx);
		}
	}

}

public class SlideImage {
	PImage img;
	float x, dx;
	int index;

	public SlideImage(String fileName, int index_) {
		this.img = loadImage(fileName);
		this.x = this.img.width * index_;
		this.dx = this.x;
		this.index = index_;
		println("X: " + this.x + ", DX: " + this.dx);
	}

	public void move(float amt) {
		this.x = lerp(this.x, this.dx, amt);
	}

	public void show() {
		image(this.img, this.x, 0);
	}

}

public void logf(String data, Object... args) {
	logger.logf(data, args);
}

public class Logger {
	private JTextPane pane;

	public Logger() {
		JFrame frame = new JFrame("JTextPane Example");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container cp = frame.getContentPane();
		pane = new JTextPane();
		pane.setEditable(false);
		pane.setText("Welcome");
		pane.setDisabledTextColor(Color.BLACK);

		JScrollPane scrollPane = new JScrollPane(pane);
		cp.add(scrollPane, BorderLayout.CENTER);

		frame.setSize(400, 300);
		frame.setVisible(true);
	}

	public void log(String data) {
		pane.setText(pane.getText() + "\n" + data);
	}

	public void logf(String data, Object... args) {
		pane.setText(String.format(pane.getText() + "\n" + data, args));
	}

	public void clear() {
		pane.setText("");
	}
}
  public void settings() { 	size(1920, 1080, P2D); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "PowerPointProcessing" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
