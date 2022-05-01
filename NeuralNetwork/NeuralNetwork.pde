Network n = new Network(6);
Region r = new Region(200);
VisualizeNetwork visuals[];
VisualizeNetwork v;
Graph bestFitness, averageLife, averageFitness, bestLife;

double lastAccuracy=0.00001;

boolean paused = false;
boolean display = true;

public void settings() {
	size(1500, 1000);
	smooth(8);
}

public void setup() {
	surface.setResizable(true);
	frameRate(1000);

	visuals = new VisualizeNetwork[Creature.simLength];
	bestFitness = new Graph(0, height*2/3, width, height/3);
	averageLife = new Graph(width*7/8, 0, width/8, height*2/9);
	averageFitness = new Graph(width*7/8, height*2/9, width/8, height*2/9);
	bestLife = new Graph(width*7/8, height*4/9, width/8, height*2/9);
	v = new VisualizeNetwork(0, 0, width, height*2/3);
	v.setGC(this);
	bestFitness.setGC(this);
	averageLife.setGC(this);
	averageFitness.setGC(this);
	bestLife.setGC(this);
	for (int i = 0; i < visuals.length; i++) {
		visuals[i] = new VisualizeNetwork(0, 0, 0, 0);
		visuals[i].setGC(this);
	}

	int layerNums[] = {2, 3, 1};
	r.initCreatures(layerNums.length);
	r.templateNetwork(layerNums);
	Creature.initSims(layerNums.length, layerNums);
}

double j = 0.0;
int old = 0;
public void draw() {

	if (!paused) {

		r.update();
		bestFitness.setOrigin(0, height*2/3);
		bestFitness.setSize(width, height/3);

		averageLife.setOrigin(width*7/8, 0);
		averageLife.setSize(width/8, height*2/9);

		averageFitness.setOrigin(width*7/8, height*2/9);
		averageFitness.setSize(width/8, height*2/9);

		bestLife.setOrigin(width*7/8, height*4/9);
		bestLife.setSize(width/8, height*2/9);
		//if(g.genNum%1000 = = 0){
		for (int i = 0; i < visuals.length; i++) {
			VisualizeNetwork v = visuals[i];
			v.setOrigin(width*(i%(visuals.length/2))/(visuals.length/2)*7/8, (int) (height*(i/(visuals.length/2))/3));
			v.setSize(width/visuals.length*2*7/8+1, height/3+1);
			if (display)
				v.draw(Creature.bestSims[i]);
			//System.out.println(Creature.bestSims[i].neuronLayers.get(0).layerNeurons.get(0).firing+" "+Creature.bestSims[i].outerLayer.layerNeurons.get(0).firing);
		}
		//v.draw(g.best().brain);
		if (display) {
			textAlign(LEFT);
			Color.BLUE.average(Color.RED).fill(this);
			text(""+Creature.lowestFitness, 0, 10);
			text(""+r.best().fitness, 0, 30);
			text(""+r.best().numTimesRun, 0, 50);
			text(("generation "+r.genNum), textWidth(""+Creature.lowestFitness)+50, 10);
		}
		System.out.println(("generation "+r.genNum));
		bestFitness.addPoint(r.best().fitness/r.best().numTimesRun);

		averageLife.addPoint(r.averageLife());

		averageFitness.addPoint(r.averageFitness());

		bestLife.addPoint(r.best().numTimesRun);
		//}
		r.reproduce();
		if (old+j<frameCount) {
			if (display) {
				bestFitness.draw();
				averageLife.draw();
				averageFitness.draw();
				bestLife.draw();
			}
			old = frameCount;
		}

		j += 0.01;
	}
}

public void keyPressed() {
	if (key == ' ')
		paused = !paused;
	if (key == 'd')
		display = !display;
}
