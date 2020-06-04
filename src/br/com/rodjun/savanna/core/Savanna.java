package br.com.rodrigojunqueira.savanna.core;

import br.com.rodrigojunqueira.savanna.TravellingSalesman.TravellingSalesman;

public class Savanna {

	public Pride[] prides = new Pride[4];
	private Lion king;
	public Lion[] nomads = new Lion[4];
	
	public Savanna(int population, DnaFactory dnaFactory) {
		this.prides[0] = new Pride(dnaFactory);
		this.prides[0].populate(population);
		this.prides[1] = new Pride(dnaFactory);
		this.prides[1].populate(population);
		this.prides[2] = new Pride(dnaFactory);
		this.prides[2].populate(population);
		this.prides[3] = new Pride(dnaFactory);
		this.prides[3].populate(population);
		this.king = prides[0].getKing();
		
	}
	
	public void nextGeneration() {
		this.prides[0].nextGeneration();	// next generation for pride 0
		this.prides[1].nextGeneration();	// next generation for pride 1
		this.prides[2].nextGeneration();	// next generation for pride 1
		this.prides[3].nextGeneration();	// next generation for pride 1
		
		this.nomads[0] = this.prides[0].getNomad();
		this.nomads[1] = this.prides[1].getNomad();
		this.nomads[2] = this.prides[2].getNomad();
		this.nomads[3] = this.prides[2].getNomad();

		
		int randomPride;
		// the Nomads challenge the Kings
		if (Math.random() > 0.2) {
			randomPride = (int)(Math.random() * 4);
			if (this.nomads[0].compareTo(this.prides[randomPride].getKing()) >= 0) {
				this.prides[randomPride].setKing(this.nomads[0]);
			}
		} else { this.nomads[0].mutate(); } 
		if (Math.random() > 0.2) {
			randomPride = (int)(Math.random() * 4);
			if (this.nomads[1].compareTo(this.prides[randomPride].getKing()) >= 0) {
				this.prides[randomPride].setKing(this.nomads[1]);
			}
		} else { this.nomads[1].mutate(); } 
		if (Math.random() > 0.2) {
			randomPride = (int)(Math.random() * 4);
			if (this.nomads[2].compareTo(this.prides[randomPride].getKing()) >= 0) {
				this.prides[randomPride].setKing(this.nomads[2]);
			}
		} else { this.nomads[2].mutate(); } 
		if (Math.random() > 0.2) {
			randomPride = (int)(Math.random() * 4);
			if (this.nomads[3].compareTo(this.prides[randomPride].getKing()) >= 0) {
				this.prides[randomPride].setKing(this.nomads[3]);
			}
		} else { this.nomads[3].mutate(); } 
		
	
		
		// finds the king of the kings
		if (this.king.compareTo(this.prides[0].getKing()) == -1) {
			this.king = this.prides[0].getKing();
		}
		if (this.king.compareTo(this.prides[1].getKing()) == -1) {
			this.king = this.prides[1].getKing();
		}
		if (this.king.compareTo(this.prides[2].getKing()) == -1) {
			this.king = this.prides[2].getKing();
		}
		if (this.king.compareTo(this.prides[3].getKing()) == -1) {
			this.king = this.prides[3].getKing();
		}

		
	}
	
	public Lion getKing() {
		return this.king;
	}
	
	public long getGeneration() {
		return this.prides[0].getGeneration();
	}
	
}
