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
		map.setAllDistancesFor("A", new int[]{0, 1, 2, 4, 5, 8, 1});
		map.setAllDistancesFor("B", new int[]{1, 0, 1, 3, 8, 2, 5});		
		map.setAllDistancesFor("C", new int[]{2, 1, 0, 1, 2, 2, 1});
		map.setAllDistancesFor("D", new int[]{4, 3, 1, 0, 1, 4, 4});
		map.setAllDistancesFor("E", new int[]{5, 8, 2, 1, 0, 1, 2});
		map.setAllDistancesFor("F", new int[]{8, 2, 2, 4, 1, 0, 1});
		map.setAllDistancesFor("G", new int[]{1, 5, 1, 4, 2, 1, 0});		
		TravellingSalesman.setGoal(7);
		TravellingSalesmanFactory travellingSalesmanFactory = new TravellingSalesmanFactory();
		travellingSalesmanFactory.setMap(map);
		
		Pride pride = new Pride(travellingSalesmanFactory);
		
		pride.populate(10);
		while (!pride.getKing().isGoodEnough()) {
			pride.nextGeneration();
			pride.getKing().show();
		}
		
	
		System.out.println("Finished on generation " + pride.getGeneration());
		
		
	}

	
}


