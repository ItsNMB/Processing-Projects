import java.util.ArrayList;

public class Graph {
	public final static double minPointSize=0.0078125;

	public PApplet gc;

	public int topX, topY, width, height;

	public double pointSize;
	public ArrayList<Double> points=new ArrayList<Double>();
	public boolean fixedPointSize=false;

	public Color[] colors={new Color(Color.RED), new Color(Color.BLACK), new Color(Color.GREEN), new Color(Color.CYAN), new Color(Color.BLUE)};

	public void setGC(PApplet p) {
		gc=p;
	}

	public Graph(int x, int y, int w, int h) {
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

	public void setFixedPointSize(double pointsize) {
		pointSize=pointSize;
		fixedPointSize=true;
	}

	public void addPoint(double point) {
		points.add(point);
		if (fixedPointSize) {
			if (width/pointSize>points.size())
				points.remove(0);
		}
	}

	public double getRelCoord(double val, double min, double max) {
		return (val-min)/(max-min);
	}

	public void avgPoints() {
		for (int i=0; i<points.size()/2; i++) {
			points.set(i, (points.get(i*2)+points.get(i*2+1))/2);
		}

		int j=points.size();
		for (int i=j/2; i<j; i++)
			points.remove(points.size()-1);
	}

	public double logAvg(double num, int digits) {
		double tmp=Math.pow(10.0, Math.round(Math.log(num)/Math.log(10.0))-digits+1);
		num=num/tmp;
		num=Math.round(num);
		num=num*tmp;
		return num;
	}

	public void draw() {
		double pSize=((double)width)/((double)points.size());
		if (pSize<minPointSize)
			avgPoints();
		pSize=((double)width)/((double)points.size());

		double maxSize=Double.NEGATIVE_INFINITY;
		double minSize=Double.POSITIVE_INFINITY;
		for (double i : points) {
			if (i>maxSize)
				maxSize=i;
			if (i<minSize) {
				minSize=i;
			}
		}

		if (maxSize<0.0)
			maxSize=0.0;
		if (minSize>0.0)
			minSize=0.0;

		maxSize+=0.5;
		minSize-=0.5;

		maxSize+=Math.abs(minSize);

		colors[2].fill(gc);
		colors[3].stroke(gc);
		gc.rect(topX, topY, width, height);

		colors[0].fill(gc);
		gc.noStroke();
		for (int i=0; i<points.size(); i++) {
			float y=(float)(topY+height-getRelCoord(points.get(i), minSize, maxSize)*height);
			float h=(float)(getRelCoord(points.get(i), minSize, maxSize)*height);
			if (points.get(i)>0.0)
				y-=(float)(getRelCoord(0.0, minSize, maxSize)*height);
			else if (points.get(i)<=0.0) {
				y=(float)(topY+height-getRelCoord(0.0, minSize, maxSize)*height);
				h=(float)(getRelCoord(0.0, minSize, maxSize)*height)-h;
			}

			gc.rect((float)(pSize*i)+topX, y, (float)(pSize), h);
		}

		colors[1].stroke(gc);
		gc.line(topX, (float)(topY+height-getRelCoord(0.0, minSize, maxSize)*height), topX+width, (float)(topY+height-getRelCoord(0.0, minSize, maxSize)*height));

		colors[1].stroke(gc);
		colors[4].fill(gc);

		int len=8;
		for (int i=0; i<=len; i++) {
			double tmp=((maxSize-minSize)*i/len+minSize)*0.9;
			gc.line(topX, (float)(topY+height-getRelCoord(tmp, minSize, maxSize)*height), topX+10, (float)(topY+height-getRelCoord(tmp, minSize, maxSize)*height));
			gc.textAlign(gc.LEFT, gc.TOP);
			gc.text(""+Math.floor(logAvg(tmp, 3)*1000000000.0)/1000000000.0, topX, (float)(topY+height-getRelCoord(tmp, minSize, maxSize)*height)-20);
		}
	}
}
