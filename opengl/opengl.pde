float moveSpeed = 0.4;
float moveScale = 400;

int[] colors = {
	#77c39c, 
	#2f5e36, 
	#55aba5, 
	#2d7063, 
	#3f6829, 
	#44a872, 
	#215964, 
	#cdedae
};

ArrayList<Particle> pos = new ArrayList<Particle>();
OpenSimplexNoise op;

void setup() {
	size(1920, 1080, P2D);
	frameRate(1000);
	smooth(8);
	noStroke();

	for (int i = 0; i < 500; i++) {
		pos.add(new Particle(random(width), random(height), colors[floor(random(colors.length))]));
	}

	op = new OpenSimplexNoise(hour()+minute()+second());

	background(#162a25);
}


void draw() {
	for (int i = 0; i < pos.size(); i++) {
		Particle p = pos.get(i);
		float angle = openNoise(p.x / moveScale, p.y / moveScale) * TWO_PI * moveScale;
		
		p.x += cos(angle) * moveSpeed;
		p.y += sin(angle) * moveSpeed;
		fill(p.c);
		ellipse(p.x, p.y, 2, 2);
		if (p.x > width || p.x < 0 || p.y > height || p.y < 0) {
			p.x = random(width);
			p.y = random(height);
		}
	}

	surface.setTitle(String.format("Perlin Noise | FPS: %d | Count: %d", (int)frameRate, pos.size()));
}

float openNoise(float x_, float y_) {
	return map((float) op.eval(x_, y_), -1, 1, 0, 1);
}

void mousePressed() {
	for (int i = 0; i < 10; i++) {
		pos.add(new Particle(mouseX+random(-30, 30), mouseY+random(-30, 30), colors[floor(random(colors.length))]));
	}
}

void mouseDragged() {
	mousePressed();
}

class Particle {
	float x, y;
	int c;

	public Particle (float x_, float y_, int c_) {
		this.x = x_;
		this.y = y_;
		this.c = c_;
	}
}
