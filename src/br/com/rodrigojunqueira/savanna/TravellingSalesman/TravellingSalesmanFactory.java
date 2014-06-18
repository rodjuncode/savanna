package br.com.rodrigojunqueira.savanna.TravellingSalesman;

import br.com.rodrigojunqueira.savanna.core.Dna;
import br.com.rodrigojunqueira.savanna.core.DnaFactory;

public class TravellingSalesmanFactory implements DnaFactory {

	public Dna generate() {
		String[] cities = new String[]{"B", "C", "D"};
		String[] newRoute = new String[5];
		int randomIndex;
		newRoute[0] = "A";
		randomIndex = Math.round((float) Math.random() * 2);
		newRoute[1] = cities[randomIndex];
		//cities = Arrays.remove(cities, randomIndex);
		randomIndex = Math.round((float) Math.random() * 1);
		newRoute[2] = cities[randomIndex];
		//cities = Arrays.remove(cities, randomIndex);
		newRoute[3] = "A";
		return null;
	}

}
