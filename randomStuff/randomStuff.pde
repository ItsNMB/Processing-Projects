String[] sentences = {"I drink coffee. ", "I drink tea."};

void setup() {
	size(800, 800);
	textSize(30);
}

void draw() {
	background(92);
	drawText(sentences[0], 0xffff0000, sentences[1], 0xff00ff00, mouseX, mouseY);
}

void drawText(String txt1, int col1, String txt2, int col2, int x, int y) {
	fill(col1);
	text(txt1, x, y);
	fill(col2);
	text(txt2, x + textWidth(txt1), y);
}