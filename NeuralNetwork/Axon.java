public class Axon {
	public double num;
	public double weight;

	public Neuron in, out;

	public Axon() {
		weight=Math.random()*2.0-1.0;
		num=0.0;
	}

	public Axon(Neuron out) {
		this();
		out.addInputAxon(this);
	}

	//in is the neuron fed INTO this axon(so, from the neuron's standpoint, it is outputting)
	public Axon(Neuron in, Neuron out) {
		this();
		this.in=in;
		this.out=out;
		in.addOutputAxon(this);
		out.addInputAxon(this);
	}

	public void setNum(double input) {
		num=input*weight;
	}

	public void copy(Axon a) {
		num=a.num;
		weight=a.weight;
		if (Double.toString(weight)=="NaN")
			System.out.println("WTF IS GOING ON!!");
		if (Double.toString(a.weight)=="NaN") {
			System.out.println("Another axon is null for some dumb reason in copy after");
			System.exit(0);
		}
	}
}
