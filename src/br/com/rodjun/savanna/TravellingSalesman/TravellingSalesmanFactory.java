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
		cities.push("H");
		cities.push("I");
		cities.push("J");
		cities.push("K");
		cities.push("L");
		cities.push("M");
		cities.push("N");
		cities.push("O");
		cities.push("P");
		cities.push("Q");
		cities.push("R");
		cities.push("S");
		cities.push("T");
		Collections.shuffle(cities);
		String[] newRoute = new String[21];
		newRoute[0] = "A";
		newRoute[1] = cities.pop();
		newRoute[2] = cities.pop();
		newRoute[3] = cities.pop();
		newRoute[4] = cities.pop();
		newRoute[5] = cities.pop();
		newRoute[6] = cities.pop();		
		newRoute[7] = cities.pop();		
		newRoute[8] = cities.pop();		
		newRoute[9] = cities.pop();		
		newRoute[10] = cities.pop();		
		newRoute[11] = cities.pop();		
		newRoute[12] = cities.pop();		
		newRoute[13] = cities.pop();		
		newRoute[14] = cities.pop();		
		newRoute[15] = cities.pop();		
		newRoute[16] = cities.pop();		
		newRoute[17] = cities.pop();		
		newRoute[18] = cities.pop();		
		newRoute[19] = cities.pop();		
		newRoute[20] = "A";
		newDna.setRoute(newRoute);
		return newDna;
		
	}

	public void setMap(TravellingSalesmanMap newMap) {
		if (newMap.validate()) this.map = newMap;
	}
	
}
