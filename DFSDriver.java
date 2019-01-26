import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class DFSDriver {

	// Global variable for default source user enters
	static int default_source;
	
	public static void main(String[] args) {

		// Scanner for getting user input
		Scanner scan = new Scanner(System.in);

		// HashMap of type Integer and RatingClass (custom made, contains a target and min_rating)
		Map<Integer, ArrayList<RatingClass>> hashMap = new HashMap<Integer, ArrayList<RatingClass>>();

		// Array List for total ratings
		ArrayList<Float> total = new ArrayList<Float>();

		// Set for keeping up with paths
		Set<Integer> set = new HashSet<Integer>();

		// Visited boolean array
		boolean[] visited = new boolean[10000];

		// Declaring source and min_rating
		int source;
		int target;
		
		// Getting user input for source
		System.out.print("Please enter a source: ");
		source = scan.nextInt();
		default_source = source;

		// Getting user input for min_rating
		System.out.print("Please enter a target: ");
		target = scan.nextInt();
		
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
					RatingClass info = new RatingClass(Integer.parseInt(line[0]));
					info.setTarget(Integer.parseInt(line[1]));
					info.setRating(Integer.parseInt(line[2]));
					value.add(info);
					hashMap.put(Integer.parseInt(line[0]), value);
					
				} else {
					// Creates new values for a new key
					ArrayList<RatingClass> value = new ArrayList<RatingClass>();
					// Only puts element in the hashMap if min_rating is above the one user wants
					RatingClass info = new RatingClass(Integer.parseInt(line[0]));
					info.setTarget(Integer.parseInt(line[1]));
					info.setRating(Integer.parseInt(line[2]));
					value.add(info);
					hashMap.put(Integer.parseInt(line[0]), value);
				}	
			}
			
//			System.out.println(hashMap);
			DFS(source, target, hashMap, visited, 0, 0, set, total);
			OverallAverage(total);
			
			// Catch statement
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	// Method for DFS 
	public static void DFS(int source, int target, Map<Integer, ArrayList<RatingClass>> G, boolean[] visited, 
			int sum, int count, Set<Integer> set, ArrayList<Float> total) {
		
		// Marks visited of source as true to avoids any circles
		visited [source] = true;
		
		// Base Case
		if (source == target) {
			return;
		}
		
		// Checking to see if source has any nodes
		if (G.get(source) != null) {
			// If it does, going through the nodes
			for (RatingClass key: G.get(source)) {
				// Setting neighbor as the current node and adding the current rating of the node to sum
				int neighbor = key.getTarget();
				sum += key.getRating();
				count++;
				// Adding to the set for visibility of the path
				set.add(key.getTarget());
				// Checking to see if neighbor has already been visited or not
				if (!visited[neighbor] && neighbor!= target) {
					if (G.get(neighbor) != null) {
						// Recursive call
						DFS(neighbor, target, G, visited, sum, count, set, total);				
						visited[neighbor] = false;
					}
				}
				// If neighbor is the target then calling PathTracker method to get average for the path
				if (neighbor == target) {
					visited[neighbor] = true;
					PathTracker(target, sum, count, total, set);
				}
				// Removing the nodes that don't contribute to the path
				set.remove(neighbor);
				sum = sum - key.getRating();
				count--;
			}
		} else {
			return;
		}
	}
	
	// Tracking the path by 
	public static void PathTracker(int target, int sum, int count, ArrayList<Float> total, Set<Integer> set) {
		// Adds the initial source that user entered for printing reasons
		set.add(default_source);
		// Not in order
		System.out.println(set);
        System.out.println("This is a path!!! P.S. Not in order.");
        
        // Finding the average of the particular path
        System.out.println("Average: " + ((float) sum / count));
        System.out.println();
        
        // Adding to total ratings array
        total.add((float) sum / count);
	}

	// Method for finding the total average of all the averages 
	public static void OverallAverage(ArrayList<Float> total) {
		float total_avg = 0;
		// Goes through the total array (which has all the ratings) and increments total_avg variable
		for (int i = 0; i < total.size(); i++) {
			total_avg += total.get(i);
		}
		// Printing the total overall average
		System.out.println("Overall Average: " + total_avg / total.size());
	}
}