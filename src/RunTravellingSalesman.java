import br.com.rodrigojunqueira.savanna.TravellingSalesman.TravellingSalesman;
import br.com.rodrigojunqueira.savanna.TravellingSalesman.TravellingSalesmanFactory;
import br.com.rodrigojunqueira.savanna.TravellingSalesman.TravellingSalesmanMap;
import br.com.rodrigojunqueira.savanna.core.Pride;


public class RunTravellingSalesman {

	public static void main(String[] args) {
		
		TravellingSalesmanMap map = new TravellingSalesmanMap();
		map.add("A");
		map.add("B");
		map.add("C");
		map.add("D");
		map.add("E");
		map.add("F");	
		map.add("G");
		map.add("H");
		map.add("I");
		map.add("J");
		map.add("K");
		map.add("L");
		map.add("M");	
		map.add("N");
		map.add("O");
		map.add("P");
		map.add("Q");
		map.add("R");
		map.add("S");
		map.add("T");	
		map.setAllDistancesFor("A", new int[]{0, 1, 2, 4, 5, 8, 1, 2, 1, 7, 9, 2, 4, 2, 7, 9, 2, 3, 4, 1});
		map.setAllDistancesFor("B", new int[]{1, 0, 1, 3, 8, 2, 5, 3, 2, 5, 7, 1, 3, 2, 9, 4, 2, 3, 4, 1});		
		map.setAllDistancesFor("C", new int[]{2, 1, 0, 1, 2, 2, 1, 8, 4, 3, 6, 7, 2, 1, 8, 6, 7, 2, 3, 5});
		map.setAllDistancesFor("D", new int[]{4, 3, 1, 0, 1, 4, 4, 3, 2, 5, 6, 2, 8, 6, 3, 2, 5, 4, 7, 2});
		map.setAllDistancesFor("E", new int[]{5, 8, 2, 1, 0, 1, 2, 4, 7, 3, 2, 1, 7, 6, 3, 2, 7, 2, 2, 4});
		map.setAllDistancesFor("F", new int[]{8, 2, 2, 4, 1, 0, 1, 8, 3, 2, 1, 4, 6, 8, 2, 3, 5, 3, 2, 9});
		map.setAllDistancesFor("G", new int[]{1, 5, 1, 4, 2, 1, 0, 1, 2, 7, 2, 1, 5, 3, 2, 9, 2, 1, 4, 5});		
		map.setAllDistancesFor("H", new int[]{2, 3, 8, 3, 4, 8, 1, 0, 1, 2, 8, 9, 1, 2, 3, 2, 8, 4, 3, 2});
		map.setAllDistancesFor("I", new int[]{1, 2, 4, 2, 7, 3, 2, 1, 0, 1, 2, 3, 2, 4, 6, 8, 2, 2, 1, 6});		
		map.setAllDistancesFor("J", new int[]{7, 5, 3, 5, 3, 2, 7, 2, 1, 0, 1, 4, 8, 3, 2, 3, 6, 2, 6, 7});
		map.setAllDistancesFor("K", new int[]{9, 7, 6, 6, 2, 1, 2, 8, 2, 1, 0, 1, 4, 5, 6, 2, 9, 3, 2, 8});
		map.setAllDistancesFor("L", new int[]{2, 1, 7, 2, 1, 4, 1, 9, 3, 4, 1, 0, 1, 2, 1, 6, 5, 3, 8, 2});
		map.setAllDistancesFor("M", new int[]{4, 3, 2, 8, 7, 6, 5, 1, 2, 8, 4, 1, 0, 1, 2, 5, 3, 4, 2, 8});
		map.setAllDistancesFor("N", new int[]{2, 2, 1, 6, 6, 8, 3, 2, 4, 3, 5, 2, 1, 0, 1, 3, 2, 6, 7, 9});		
		map.setAllDistancesFor("O", new int[]{7, 9, 8, 3, 3, 2, 2, 3, 6, 2, 6, 1, 2, 1, 0, 1, 9, 7, 4, 3});
		map.setAllDistancesFor("P", new int[]{9, 4, 6, 2, 2, 3, 9, 2, 8, 3, 2, 6, 5, 3, 1, 0, 1, 4, 2, 7});		
		map.setAllDistancesFor("Q", new int[]{2, 2, 7, 5, 7, 5, 2, 8, 2, 6, 9, 5, 3, 2, 9, 1, 0, 1, 3, 6});
		map.setAllDistancesFor("R", new int[]{3, 3, 2, 4, 2, 3, 1, 4, 2, 2, 3, 3, 4, 6, 7, 4, 1, 0, 1, 8});
		map.setAllDistancesFor("S", new int[]{4, 4, 3, 7, 2, 2, 4, 3, 1, 6, 2, 8, 2, 7, 4, 2, 3, 1, 0, 1});
		map.setAllDistancesFor("T", new int[]{1, 1, 5, 2, 4, 9, 5, 2, 6, 7, 8, 2, 8, 9, 3, 7, 6, 8, 1, 0});

		TravellingSalesman.setGoal(20);
		TravellingSalesmanFactory travellingSalesmanFactory = new TravellingSalesmanFactory();
		travellingSalesmanFactory.setMap(map);
				
		int goalCount = 0;
		int totalCount = 0;
		Pride pride = new Pride(travellingSalesmanFactory);
		//while(true) {
			long startTime = System.currentTimeMillis();
			pride.populate(50);
			while (!pride.getKing().isGoodEnough() /*&& pride.getGeneration() < 200000*/) {
				pride.nextGeneration();
				System.out.print(pride.getGeneration() + " - ");
				pride.getKing().show();				
			}
			if (pride.getKing().isGoodEnough()) {
				goalCount++;
			}
			totalCount++;
			long endTime   = System.currentTimeMillis();
			long totalTime = endTime - startTime;
			System.out.println(goalCount + "/" + totalCount + " - Finished on generation " + pride.getGeneration() + " under " + totalTime/1000/60 + " minute(s).");
			pride.getKing().show();

		//}
		
		
		
	}

	
}


