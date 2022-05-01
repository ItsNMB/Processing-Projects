import java.util.ArrayList;

public class Network implements Comparable<Network> {
	public ArrayList<Layer> neuronLayers;
	public Layer outerLayer;
	public ArrayList<Axon> inputAxons;

	public double fitness=0.0;

	public int[] layers;

	public Network(int numLayers) {
		neuronLayers=new ArrayList<Layer>(numLayers);
	}

	//layers MUST be the an array whose length is the number of layers in the network(or greater but that will be ignored)
	public void initLayers(int [] layers) {
		this.layers=layers;
		inputAxons=new ArrayList<Axon>(layers[0]);
		for (int i=0; i<layers.length; i++) {
			boolean bias=true;
			if (i==layers.length-1)
				bias=false;
			Layer curLayer;
			Layer prevLayer=null;
			if (i==0) {
				curLayer=new InputLayer(layers[i], inputAxons);
			}
			else {
				curLayer=new Layer();
				prevLayer=neuronLayers.get(i-1);
				curLayer.initLayer(layers[i], bias);
			}
			neuronLayers.add(curLayer);
			if (prevLayer!=null)
				prevLayer.connect(curLayer, bias);
		}
		outerLayer=neuronLayers.get(neuronLayers.size()-1);
	}

	public void setInput(int index, double input) {
		inputAxons.get(index).setNum(input);
	}

	public void setInputs(double [] inputs) {
		for (int i=0; i<inputAxons.size()-1; i++)
			setInput(i, inputs[i]);
	}

	public void calculateOutputs() {
		for (Layer i : neuronLayers)
			i.update();
	}

	public ArrayList<Neuron> getOutput() {
		return neuronLayers.get(neuronLayers.size()-1).getNeurons();
	}

	public void mutate() {
		for (Layer l : neuronLayers)
			l.mutate();
	}

	public double setFitness(double desired[]) {
		Layer output=neuronLayers.get(neuronLayers.size()-1);
		fitness=0.0;
		for (int i=0; i<output.layerNeurons.size(); i++) {
			fitness+=Math.abs(output.layerNeurons.get(i).firing-desired[i]);
		}
		return fitness;
	}

	public void reproduce(Network n, boolean m) {
		for (int i=0; i<n.neuronLayers.size(); i++)
			neuronLayers.get(i).copy(n.neuronLayers.get(i));
		if (m)
			mutate();
	}

	@Override
		public int compareTo(Network n) {
		String self=Double.toString(fitness);
		String other=Double.toString(n.fitness);

		if (self.equals(other))
			return 0;
		else if (self.equals("NaN"))
			return 1;
		else if (other.equals("NaN"))
			return -1;
		else if (fitness>n.fitness) {
			return 1;
		}
		else {
			return -1;
		}
	}
}
