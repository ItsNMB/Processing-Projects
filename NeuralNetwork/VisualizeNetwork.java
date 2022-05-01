public class VisualizeNetwork {
	public static PApplet gc;

	public int topX, topY, width, height;
	public boolean showHidden=true;

	//Colors
	//0-background
	//1-color if value closer to 0 for neuron. Color if smaller(negative) for axon
	//2-color if value further from 0 for neuron. Color if larger(negative) for axon
	public Color[] colors={new Color(Color.GREEN), new Color(Color.BLACK), new Color(Color.WHITE), new Color(Color.CYAN)};

	public static void setGC(PApplet p) {
		gc=p;
	}

	public VisualizeNetwork(int x, int y, int w, int h) {
		topX=x;
		topY=y;
		width=w;
		height=h;
	}

	public void setOrigin(int x, int y) {
		topX=x;
		topY=y;
	}

	public void setSize(int w, int h) {
		width=w;
		height=h;
	}



	public Color setTempColor(double val, double minVal, double maxVal) {
		Color c;
		if (val==0) {
			c=colors[1].average(colors[2]);
		}
		else if (val>0) {
			c=colors[1].gradAverage(colors[2], 0.5-val/maxVal/2.0);
		}
		else {
			c=colors[1].gradAverage(colors[2], 0.5+val/minVal/2.0);
		}
		return c;
	}

	public PVector getNeuronCoords(int x, int y, int centerX, int centerY, int neuronDia, int maxX, int maxY) {
		PVector out=new PVector(centerX, centerY);
		PVector tmp=new PVector(x-(maxX-1)/2.0f, y-(maxY-1)/2.0f);
		tmp.mult(neuronDia*2);
		out.add(tmp);
		return out;
	}

	public void draw(Network n) {
		gc.fill(colors[0].r, colors[0].g, colors[0].b);
		gc.rect(topX, topY, width, height);

		double maxAxon=0.0;
		double minAxon=0.0;

		int numLayers=n.neuronLayers.size();
		int maxLayerSize=0;

		for (Layer l : n.neuronLayers) {
			if (l.layerNeurons.size()>maxLayerSize)
				maxLayerSize=l.layerNeurons.size();
			for (Neuron nu : l.layerNeurons) {
				for (Axon a : nu.outputs) {
					if (a.weight>maxAxon)
						maxAxon=a.weight;
					else if (a.weight<minAxon)
						minAxon=a.weight;
				}
			}
		}

		int neuronDia=width/numLayers/2;
		if (neuronDia>height/maxLayerSize/2)
			neuronDia=height/maxLayerSize/2;

		int centerX=topX+width/2;
		int centerY=topY+height/2;

		gc.textAlign(gc.CENTER, gc.CENTER);

		for (int i=0; i<n.neuronLayers.size(); i++) {
			Layer l=n.neuronLayers.get(i);
			for (int j=0; j<l.layerNeurons.size(); j++) {
				Neuron nu=l.layerNeurons.get(j);
				PVector nuCoords=getNeuronCoords(i, j, centerX, centerY, neuronDia, n.neuronLayers.size(), l.layerNeurons.size());

				gc.strokeWeight(2);
				for (Axon a : nu.outputs) {
					PVector nextCoords=getNeuronCoords(i+1, a.out.id, centerX, centerY, neuronDia, n.neuronLayers.size(), n.neuronLayers.get(i+1).layerNeurons.size());
					setTempColor(a.num, minAxon, maxAxon).stroke(gc);
					gc.line(nuCoords.x, nuCoords.y, nextCoords.x, nextCoords.y);
				}

				gc.noStroke();

				setTempColor(nu.firing-0.5, -0.5, 0.5).fill(gc);
				gc.ellipse(nuCoords.x, nuCoords.y, neuronDia, neuronDia);

				colors[3].fill(gc);
				gc.text((float)nu.firing, nuCoords.x, nuCoords.y);
			}
		}
	}
}
