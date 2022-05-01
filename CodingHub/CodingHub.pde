

int surfacePosX, surfacePosY, surfaceSizeX = 1000, surfaceSizeY = 600;

void setup() {
	fullScreen(P2D);
	frameRate(170);
	surfacePosX = (2560 / 2) - (surfaceSizeX / 2);
	surfacePosY = (1440 / 2) - (surfaceSizeY / 2);
	surface.setSize(surfaceSizeX, surfaceSizeY);
	surface.setResizable(true);
	surface.setLocation(surfacePosX, surfacePosY);

}

void draw() {
	background(hsb(float(frameCount % 1000) / 1000, 1, 1));
}
