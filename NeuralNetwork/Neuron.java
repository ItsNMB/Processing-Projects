import java.util.ArrayList;

public class Neuron {
	public ArrayList<Axon> outputs;
	public ArrayList<Axon> inputs;

	int id;

	public double firing;

	public double mutateFactor=0.1;
	public double mutateBias=0.1;

	public static final double maxMutateFactor=100.0;
	public static final double maxMutateBias=100.0;

	public Neuron() {
		outputs=new ArrayList<Axon>();
		inputs=new ArrayList<Axon>();
	}

	public Neuron(int id) {
		this();
		this.id=id;
	}

	public static double activate(double num) {
		return (1.0)/(1.0+Math.pow(Math.E, -num));
	}

	public void setOutputs() {
		for (Axon i : outputs) {
			i.setNum(firing);
		}
	}

	public void calculateOutput() {
		firing=0.0;
		for (Axon i : inputs) {
			firing+=i.num;
		}
		firing=activate(firing);
		setOutputs();
	}

	public void addInputAxon(Axon i) {
		inputs.add(i);
	}

	public void addOutputAxon(Axon o) {
		outputs.add(o);
	}

	public double randMutate() {
		return Math.random()*mutateFactor-mutateFactor/2.0;
	}

	public void mutate() {
		for (Axon a : inputs) {
			a.weight+=a.weight*randMutate()+mutateBias*randMutate();
		}
		mutateBias+=mutateBias*randMutate();
		if (Math.abs(mutateBias)>maxMutateBias)
			mutateBias=maxMutateBias*Math.signum(mutateBias);
		mutateFactor+=mutateFactor*randMutate();
		if (Math.abs(mutateFactor)>maxMutateFactor)
			mutateFactor=maxMutateFactor*Math.signum(mutateFactor);
	}

	public void copy(Neuron n) {
		firing=n.firing;
		for (int i=0; i<n.inputs.size(); i++) {
			inputs.get(i).copy(n.inputs.get(i));
		}
	}
}
