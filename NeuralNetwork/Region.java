import java.util.ArrayList;
import java.util.Arrays;

public class Region {
	Creature allCreatures[];
	public int genNum;

	public Region(int numCreatures) {
		allCreatures=new Creature[numCreatures];
		genNum=0;
	}

	public void initCreatures(int netSize) {
		for (int i=0; i<allCreatures.length; i++)
			allCreatures[i]=new Creature(netSize);
	}

	public void templateNetwork(int[] layers) {
		for (int i=0; i<allCreatures.length; i++)
			allCreatures[i].initLayers(layers);
	}

	public void update() {//num of creatures must be even
		Creature.lowestFitness=-1.0;
		//sortByFitness();
		ArrayList<Integer> tmp=new ArrayList<Integer>();
		ArrayList<Integer> otherTmp=new ArrayList<Integer>();

		for (int i=0; i<allCreatures.length; i++)
			tmp.add(i);
		for (int i=0; i<allCreatures.length/2; i++) {
			int t=(int) (Math.random()*tmp.size()*0.9999999999);
			otherTmp.add(tmp.get(t));
			tmp.remove(t);
		}

		for (int i=0; i<allCreatures.length/2; i++) {
			Creature c1=allCreatures[tmp.get(i)];
			Creature c2=allCreatures[otherTmp.get(i)];
			Creature.simulate(c1, c2);
		}
		sortByFitness();
	}

	void sortByFitness() {
		Arrays.sort(allCreatures);
	}

	public void reproduce() {
		for (int i=0; i<allCreatures.length/2; i++) {
			allCreatures[i+allCreatures.length/2].reproduce(allCreatures[i]);
		}
		genNum++;
	}

	public Creature best() {
		return allCreatures[0];
	}

	public double averageFitness() {
		double avg=0.0;
		for (Creature c : allCreatures) {
			avg+=c.fitness/c.numTimesRun;
		}
		avg/=allCreatures.length;
		return avg;
	}

	public double averageLife() {
		double avg=0.0;
		for (Creature c : allCreatures) {
			avg+=c.numTimesRun;
		}
		avg/=allCreatures.length;
		return avg;
	}
}
