import java.io.*;
import java.net.*;
import java.nio.charset.*;
PFont myFont;

// TODO: fonts = consolas, yeager, madetommy, ocr a extended, relexiarg-regular,
// https://api.mcsrvstat.us/2/potential-bird.craft.playit.gg:25565

int COLOR_BACKGROUND, COLOR_WHITE, COLOR_GREEN, COLOR_BLUE, COLOR_RED, COLOR_YELLOW;

String urlPrefix = "https://api.mcsrvstat.us/2/";
String url, ip = "potential-bird.craft.playit.gg", port = "22565";

JSONObject json;
int players = 0;
int maxPlayers = 0;
ArrayList<String> playerNames;
boolean online = false;
boolean loading = true;
float val = 0, max = 60;
int dots = 0;
long lastM = 0;

void setup() {
	COLOR_BACKGROUND = color(#272822);
	COLOR_WHITE = color(#f8f8f2);
	COLOR_GREEN = color(#a7e32e);
	COLOR_BLUE = color(#66d9ef);
	COLOR_RED = color(#f92672);
	COLOR_YELLOW = color(#e6da74);

	size(1080, 1300);
	surface.setResizable(true);
	myFont = createFont("OCRA.TTF", 32);
	textFont(myFont);
	textAlign(CENTER);
	textSize(130);
	fill(COLOR_WHITE);
	background(COLOR_BACKGROUND);
	text("LOADING", width / 2, height / 2);

	thread("loadJson");
}

void draw() {
	background(COLOR_BACKGROUND);
	fill(COLOR_WHITE);
	textSize(50);
	drawText("IP: ", COLOR_WHITE, ip + ":" + port, COLOR_YELLOW, width / 2, height / 10);
	if (!loading) {
		fill(online ? COLOR_GREEN : COLOR_RED);
		textSize(150);
		text(online ? "ONLINE" : "OFFLINE", width / 2, height / 2);
		fill(COLOR_BLUE);
		textSize(80);
		if (online) {
			text("Players: (" + players + " / " + maxPlayers + ")", width / 2, height * 0.6);
			if (players > 0) {
				for (int p = 0; p < players; p++) {
					text("• " + playerNames.get(p), width / 2, height / 2 + (height / 10) + ((height / 10) * p));
				}
			}
		}
		if (mousePressed) val++;
		else if (val > 0) val -= 3;

		if (val > max) {
			loading = true;
			val = 0;
			thread("loadJson");
		}

		stroke(COLOR_WHITE);
		strokeWeight(30);
		strokeCap(ROUND);
		if (val > 5)
			line(width / 2 - map(val, 0, max, 0, width / 2), height, width / 2 + map(val, 0, max, 0, width / 2), height);
	} else {
		fill(COLOR_WHITE);
		textSize(100);
		text("LOADING" + loadingDots(), width / 2, height / 2);
	}
}

public void loadJson() {
	playerNames = new ArrayList<String>(0);
	try {
		String[] pasteData = loadStrings("https://pastebin.com/raw/bVmCvibB");
		printArray(pasteData);
		// ip = pasteData[0];
		// port = pasteData[1];
		url = urlPrefix + ip + ":" + port;
		json = loadJSONObject(url);
		JSONObject playerObj = json.getJSONObject("players");
		players = playerObj.getInt("online");
		maxPlayers = playerObj.getInt("max");
		if (players > 0) {
			JSONArray arr = playerObj.getJSONArray("list");
			for (int i = 0; i < arr.size(); i++) {
				playerNames.add(arr.getString(0));
			}
		}

		online = json.getBoolean("online");
	} catch (Exception e) {
		e.printStackTrace();
	}
	loading = false;
}


public String loadingDots() {
	if (millis() - lastM > 500) {
		dots++;
		if (dots > 3) dots -= 3;
		lastM = millis();
	}

	String result = "";
	for (int i = 0; i < dots; i++) {
		result += ".";
	}
	return result;
}

public void drawText(String txt1, int col1, String txt2, int col2, int x, int y) {
	fill(col1);
	text(txt1, x - (textWidth(txt2) / 2), y);
	fill(col2);
	text(txt2, x + (textWidth(txt1) / 2), y);
}