public class Gene {
	public static final int length = getLength();
	public static final double possiblityOfGeneMutation = 0.01;
	private int[] value;
	public double score;

	private static int getLength() {
		int length = 1;
		for(int i = 0; i < 5; i++) {
			length *= CellStatus.values().length;
		}
		return length;
	}

	private Gene() {
		this.value = new int[length];
	}

	static public Gene createRandom() {
		Gene gene = new Gene();
		for(int i = 0; i < gene.value.length; i++) {
			gene.value[i] = (int) (Math.random() * Action.values().length);
		}
		return gene;
	}

	static public Gene create(String s) {
		Gene gene = new Gene();
		for(int i = 0; i < length; i++) {
			gene.value[i] = s.charAt(i) - '0';
		}
		return gene;
	}

	public Gene clone() {
		Gene gene = new Gene();
		for(int i = 0; i < length; i++) {
			gene.value[i] = this.value[i];
		}
		return gene;
	}

	public Action getAction(RobbySensor sensor) {
		return getAction(
				sensor.north,
				sensor.south,
				sensor.east,
				sensor.west,
				sensor.currentSite);
	}

	private Action getAction(
			CellStatus north,
			CellStatus south,
			CellStatus east,
			CellStatus west,
			CellStatus currentSite) {
		int index = 0;
		index += north.ordinal();
		index *= CellStatus.values().length;
		index += south.ordinal();
		index *= CellStatus.values().length;
		index += east.ordinal();
		index *= CellStatus.values().length;
		index += west.ordinal();
		index *= CellStatus.values().length;
		index += currentSite.ordinal();
		return Action.values()[this.value[index]];
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		for(int e : value) {
			builder.append(e);
		}
		return builder.toString();
	}

	static public Gene crossover(Gene father1, Gene father2) {
		int truncation = (int) (Math.random() * length);
		Gene child = new Gene();
		{
			int i = 0;
			for(; i < truncation; i++) {
				child.value[i] = father1.value[i];
			}
			for(; i < length; i++) {
				child.value[i] = father2.value[i];
			}
		}
		for(int i = 0; i < child.value.length; i++) {
			if(Math.random() < possiblityOfGeneMutation) {
				child.value[i] = (int) (Action.values().length * Math.random());
			}
		}
		return child;
	}
}
