import java.util.ArrayList;

public class InputLayer extends Layer {

	public InputLayer(int numNeurons, ArrayList<Axon> inputAxons) {
		initLayer(numNeurons, true);
		for (Neuron i : layerNeurons) {
			inputAxons.add(new Axon(i));
		}
	}
}
