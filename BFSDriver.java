import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Stack;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class BFSDriver {
	
	public static void main(String[] args) {
		
		// Scanner for getting user input
		Scanner scan = new Scanner(System.in);

		// HashMap of type Integer and RatingClass (custom made, contains a target and min_rating)
		Map<Integer, ArrayList<RatingClass>> hashMap = new HashMap<Integer, ArrayList<RatingClass>>();
		
		// Declaring source and min_rating
		int source;
		int min_rating;
		
		// Getting user input for source
		System.out.print("Please enter a source: ");
		source = scan.nextInt();
		
		// Getting user input for min_rating
		System.out.print("Please enter the minimum rating to search by: ");
		min_rating = scan.nextInt();
		
		// Going through the file
		try {
			File file = new File(args[0]);
			scan = new Scanner(file);
			
			while (scan.hasNext()) {
				// Creating string array to keep track of source, target, and min rating
				String[] line = scan.nextLine().split(",");
				
				// Add to the values of the key if hashMap already has the key
				if (hashMap.containsKey(Integer.parseInt(line[0]))) {
					ArrayList<RatingClass> value = hashMap.get(Integer.parseInt(line[0]));
					// Only puts element in the hashMap if min_rating is above the one user wants
					if (Integer.parseInt(line[2]) >= min_rating) {
						RatingClass info = new RatingClass(Integer.parseInt(line[0]));
						info.setTarget(Integer.parseInt(line[1]));
						info.setRating(Integer.parseInt(line[2]));
						value.add(info);
						hashMap.put(Integer.parseInt(line[0]), value);
					}
				} else {
					// Creates new values for a new key
					ArrayList<RatingClass> value = new ArrayList<RatingClass>();
					// Only puts element in the hashMap if min_rating is above the one user wants
					if (Integer.parseInt(line[2]) >= min_rating) {
						RatingClass info = new RatingClass(Integer.parseInt(line[0]));
						info.setTarget(Integer.parseInt(line[1]));
						info.setRating(Integer.parseInt(line[2]));
						value.add(info);
						hashMap.put(Integer.parseInt(line[0]), value);
					}
				}	
			}
					
//			System.out.println(hashMap);
			BFS(source, hashMap, min_rating);			
			
			// Catch statement
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}	
	}
	
	// BFS
	public static void BFS(int source, Map<Integer, ArrayList<RatingClass>> G, int min_rating) {
		// Counter for future use
		int count = 0;
		// Setting default source for printing reasons
		int default_source = source;
		// array list to display sorted target
		ArrayList<Integer> sortedTargets = new ArrayList<Integer>();
		
		// Boolean Visited to avoid any possible loops
		boolean Visited[] = new boolean[10000];
		Visited[source] = true;
	
		// Creating queue
		Queue<Integer> Q = new LinkedList<Integer>();
		Q.add(source);
		
		// Algorithm for finding all possible nodes from the default source
		while (!Q.isEmpty()) {
			source = Q.poll();
			if (G.get(source) != null) {
				for (RatingClass target : G.get(source)) {
					if (!Visited[target.getTarget()]) {
						Visited[target.getTarget()] = true;
						sortedTargets.add(target.getTarget());
						Q.add(target.getTarget());
						count++;
					}
				}
			}
		}
		// Printing 
		System.out.println("Source node: " +  default_source + ", minimum acceptable rating: " + min_rating + ", (total nodes: " + count + ")");
		// Performing insertion sort and printing 
		InsertionSort.sort(sortedTargets);
	}
}