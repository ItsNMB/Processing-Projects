int DIM = 32;

void setup() {
	size(1920, 1080, P3D);
}

void draw() {
	background(0);
	translate(width / 2, height / 2);

	for (int x = 0; x < DIM; x++) {
		for (int y = 0; y < DIM; y++) {
			for (int z = 0; z < DIM; z++) {
				point(x, y, z);
			}
		}
	}
}