package br.com.rodrigojunqueira.savanna.TravellingSalesman;

import java.util.Collections;
import java.util.Stack;

import br.com.rodrigojunqueira.savanna.core.Dna;
import br.com.rodrigojunqueira.savanna.core.DnaFactory;

public class TravellingSalesmanFactory implements DnaFactory {

	private TravellingSalesmanMap map;
	
	public Dna generate() {
		// This needs to be refactored to support different sizes of maps
		TravellingSalesman newDna = new TravellingSalesman(this.map);
		Stack<String> cities = new Stack<String>();
		cities.push("B");
		cities.push("C");
		cities.push("D");
		cities.push("E");
		cities.push("F");
		cities.push("G");		
		Collections.shuffle(cities);
		String[] newRoute = new String[8];
		newRoute[0] = "A";
		newRoute[1] = cities.pop();
		newRoute[2] = cities.pop();
		newRoute[3] = cities.pop();
		newRoute[4] = cities.pop();
		newRoute[5] = cities.pop();
		newRoute[6] = cities.pop();		
		newRoute[7] = "A";
		newDna.setRoute(newRoute);
		return newDna;
	}

	public void setMap(TravellingSalesmanMap newMap) {
		if (newMap.validate()) this.map = newMap;
	}
	
}
