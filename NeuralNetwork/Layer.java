import java.util.ArrayList;

public class Layer {
	public ArrayList<Neuron> layerNeurons;
	public Layer nextLayer;

	public Layer() {
	}

	public void initLayer(int numNeurons, boolean bias) {
		layerNeurons=new ArrayList<Neuron>();
		for (int i=0; i<numNeurons; i++) {
			layerNeurons.add(new Neuron(i));
		}
		if (bias) {
			Neuron b=new Neuron(numNeurons+1);
			Axon b1=new Axon(b);
			b1.num=1.0;
			layerNeurons.add(b);
		}
	}

	public void connect(Layer nextLayer, boolean bias) {
		this.nextLayer=nextLayer;
		for (Neuron i : layerNeurons) {
			int max=nextLayer.layerNeurons.size();
			if (bias)
				max-=1;
			for (int j=0; j<max; j++) {
				Neuron n=nextLayer.layerNeurons.get(j);
				new Axon(i, n);
			}
		}
	}

	public void update() {
		for (Neuron i : layerNeurons)
			i.calculateOutput();
	}

	public void addNeuron() {
		//add a Neuron before the bias just to keep it looking nice if displayed;
		//!!!!!!!FIX!!!!!!!!!!! ID system is not integrated here yet. And reproduction for this isnt supported
		Neuron n=new Neuron();
		layerNeurons.add(layerNeurons.size()-2, n);
		if (nextLayer!=null) {
			for (Neuron j : nextLayer.layerNeurons) {
				new Axon(n, j);
			}
		}
	}

	public ArrayList<Neuron> getNeurons() {
		return layerNeurons;
	}

	public void mutate() {
		for (Neuron n : layerNeurons) {
			n.mutate();
		}
	}

	public void copy(Layer l) {
		for (int i=0; i<l.layerNeurons.size(); i++)
			layerNeurons.get(i).copy(l.layerNeurons.get(i));
	}
}
