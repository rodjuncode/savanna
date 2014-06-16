package br.com.rodrigojunqueira.savanna;

public class RunSavanna {

	public static void main(String[] args) {
		
		MinusXSquared.setGoal(-0.005);
		Pride pride = new Pride(new MinusXSquaredFactory());
		
		pride.populate(10);
		while (!pride.getKing().isGoodEnough()) {
			pride.nextGeneration();
		}
		
		System.out.println("Finished");
		
	}

}
