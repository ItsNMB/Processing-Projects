public class Creature implements Comparable<Creature> {
	public final static double[] fitnessSettings={-0.01, 10.0, 3.5};
	public final static int simLength=10;

	public static Network bestSims[]=new Network[simLength];
	public static double lowestFitness;

	private static Network tempSims[][]=new Network[simLength][2];

	public Network brain;
	public double fitness;
	public int numTimesRun;

	public static void initSims(int numLayers, int [] layers) {
		for (int i=0; i<bestSims.length; i++) {
			bestSims[i]=new Network(numLayers);
			bestSims[i].initLayers(layers);
			tempSims[i][0]=new Network(numLayers);
			tempSims[i][0].initLayers(layers);
			tempSims[i][1]=new Network(numLayers);
			tempSims[i][1].initLayers(layers);
		}
	}

	public Creature(int netSize) {
		brain=new Network(netSize);
		fitness=0.0;
		numTimesRun=0;
	}

	public void initLayers(int[] layers) {
		brain.initLayers(layers);
	}

	public void setInputs(double inputs[]) {
		brain.setInputs(inputs);
	}

	public static void simulate(Creature c1, Creature c2) {
		double inputsC1[]={0.5, 0.5};
		double inputsC2[]={0.5, 0.5};
		for (int i=0; i<simLength; i++) {
			c1.setInputs(inputsC1);
			c2.setInputs(inputsC2);
			c1.brain.calculateOutputs();
			c2.brain.calculateOutputs();

			inputsC1[1]=c1.brain.outerLayer.layerNeurons.get(0).firing;
			inputsC2[1]=c2.brain.outerLayer.layerNeurons.get(0).firing;

			double ceilC1=Math.round(inputsC1[1]);
			double ceilC2=Math.round(inputsC2[1]);

			if (ceilC1==ceilC2) {
				c1.fitness+=fitnessSettings[(int) ((1-ceilC1)*2)];
				c2.fitness+=fitnessSettings[(int) ((1-ceilC2)*2)];
			}
			else if (ceilC1==1.0) {
				c1.fitness+=fitnessSettings[1];
			}
			else {
				c2.fitness+=fitnessSettings[1];
			}

			inputsC1[0]=ceilC1;
			inputsC2[0]=ceilC2;

			tempSims[i][0].reproduce(c1.brain, false);
			tempSims[i][1].reproduce(c2.brain, false);
		}

		c1.numTimesRun++;
		c2.numTimesRun++;

		if (c1.fitness/c1.numTimesRun<lowestFitness || lowestFitness<0.0) {
			lowestFitness=c1.fitness/c1.numTimesRun;
			for (int i=0; i<bestSims.length; i++)
				bestSims[i].reproduce(tempSims[i][0], false);
		}
		if (c2.fitness/c2.numTimesRun<lowestFitness) {
			lowestFitness=c2.fitness/c2.numTimesRun;
			for (int i=0; i<bestSims.length; i++)
				bestSims[i].reproduce(tempSims[i][1], false);
		}
	}

	public void reproduce(Creature other) {
		brain.reproduce(other.brain, true);
		fitness=other.fitness/other.numTimesRun;
		numTimesRun=1;
	}

	@Override
		public int compareTo(Creature n) {
		String self=Double.toString(fitness);
		String other=Double.toString(n.fitness);

		if (fitness/numTimesRun==n.fitness/n.numTimesRun)
			return 0;
		else if (fitness/numTimesRun>n.fitness/n.numTimesRun) {
			return 1;
		}
		else {
			return -1;
		}
	}
}
