import java.util.Scanner;

public class WeightedNineTailsProblem {
	
	public static void main(String[] args) {
		
		NineTailsWeightedGraph graph = new NineTailsWeightedGraph();
		
		while (true){
			//Prompt the user to enter the nine coin's Heads and Tails
			System.out.print("Enter an initial nine coins' Hs and Ts:");
			Scanner input = new Scanner(System.in);
			String s = input.nextLine();
			char[] initialConfiguration = s.toUpperCase().toCharArray();
		
			//generate the index of the input configuration
			int indexConfiguration = graph.configurationToIndex(initialConfiguration);
		
			//print shortest path (i.e. minimum number of moves) 
			//from the input configuration to the target configuration, with the total number of flips
			graph.printShortestPath(indexConfiguration);

			System.out.println("Finished[Y/N]?");
			String decision = input.nextLine();
			if (decision.toUpperCase().equals("Y")) {
				break;
			}
		}
	}
}
