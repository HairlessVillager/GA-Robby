public class GeneticAlgorithm {
	public static final int numberOfAction = 200;
	public static final int numberOfGenes = 200;
	public static final int numberOfRemain = 20;
	public static final int numberOfTasks = 100;
	public static final int numberOfEpoch = 1000;
	public static final double probabilityOfCan = 0.5;

	public void run() {
		Gene[] genes = createRandomGenes();
		//Gene[] genes = createGenes("055352156255322254350001250151353155151355255354264501252655250252055253054066300356351152253354562356236341151152564152602220353640023050334545050602006055620605353355154255201254354100510155150151153636154214210214261433221624643410256422133");
		for(int i = 0; i < numberOfEpoch; i++) {
			System.out.printf("INFO : epoch : %d\n", i);
			genes = evoluteGenes(genes);
			//testGene(genes[0]);
		}
		System.out.println(genes[0].toString());
	}

	private Gene[] createRandomGenes() {
		Gene[] genes = new Gene[numberOfGenes];
		for(int i = 0; i < numberOfGenes; i++) {
			genes[i] = Gene.createRandom();
		}
		return genes;
	}

	private Gene[] createGenes(String s) {
		Gene[] genes = new Gene[numberOfGenes];
		for(int i = 0; i < numberOfGenes; i++) {
			genes[i] = Gene.create(s);
		}
		return genes;
	}

	private Gene[] evoluteGenes(Gene[] old) {
		calcAverageScores(old);
		Gene[] eliminated = eliminateGenes(old);
		return expendGenes(eliminated);
	}

	private void calcAverageScores(Gene[] genes) {
		for(int i = 0; i < numberOfGenes; i++) {
			genes[i].score = calcAverageScore(genes[i]);
		}
	}

	private double calcAverageScore(Gene gene) {
		Robby robby = new Robby();
		double sum = 0.0;
		for(int j = 0; j < numberOfTasks; j++) {
			robby.init(gene, Board.createRandom(0.5));
			for(int k = 0; k < numberOfAction; k++) {
				robby.step();
			}
			sum += (double) robby.getScore();
		}
		return sum / numberOfTasks;
	}

	private Gene[] eliminateGenes(Gene[] genes) {
		for(int i = 0; i < numberOfRemain; i++) {
			for(int j = i + 1; j < numberOfGenes; j++) {
				if(genes[i].score < genes[j].score) {
					Gene t = genes[i];
					genes[i] = genes[j];
					genes[j] = t;
				}
			}
		}
		System.out.println(genes[0].score);
		return genes;
	}

	private Gene[] expendGenes(Gene[] eliminated) {
		for(int i = numberOfRemain; i < numberOfGenes; i++) {
			eliminated[i] = Gene.crossover(choice(eliminated), choice(eliminated));
		}
		return eliminated;
	}

	private Gene choice(Gene[] eliminated) {
		return eliminated[(int) (Math.random() * numberOfRemain)];
	}

	private void testGene(Gene gene) {
		System.out.println(calcAverageScore(gene));
	}

	public static void main(String[] args) {
		GeneticAlgorithm ga = new GeneticAlgorithm();
		ga.run();
	}
}
