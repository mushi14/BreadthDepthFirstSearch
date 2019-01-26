import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Set;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

public class proj3 {

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
			
			// Calling recursion static method
			Recursion(hashMap, source, min_rating);

			// Catch statement
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}	
	}
	
	// Recursion method finding the tree
	public static void Recursion(Map<Integer, ArrayList<RatingClass>> hashMap, int source, int min_Rating) {
		// Base Case
		if (hashMap.get(source) == null) {
			return;
		}
		
		// tempMap for storing key/values temporarily
		Map<Integer, ArrayList<RatingClass>> tempMap = new HashMap<Integer, ArrayList<RatingClass>>();
		tempMap.put(source, hashMap.get(source));
		System.out.println(tempMap);
		System.out.println();

		// This doesn't work properly, but hopefully you can see the logic 
		ArrayList<RatingClass> s = hashMap.get(source);
		for (int i = 0; i < s.size(); i++) {
			if (hashMap.get(s.get(i)) != null) {
				tempMap.put(s.get(i).getTarget(), hashMap.get(s.get(i)));
				s.add(s.get(i));
			}
		}
		
		// This is what I was attempting to do
		
//		ArrayList<RatingClass> s = hashMap.get(source);
//		for (int i = 0; i < s.size(); i++) {
//			if (hashMap.get(s.get(i).getTarget()) != null) {
//			ArrayList<RatingClass> list = hashMap.get(source);
//			for (int j = 0; j < list.size(); j++) {
//				tempMap.put(list.get(j).getTarget(), hashMap.get(list.get(j)));
//				Recursion(hashMap, list.get(j).getTarget(), min_Rating);
//			}
//			}
//		}
		
		// Setting tempMap to hashMap
		hashMap.clear();
		tempMap.keySet().removeAll(hashMap.keySet());
		hashMap.putAll(tempMap);
		System.out.println(hashMap);
	}
}

// Not completely done with this part. I am going to try and come to office hours and see if I can figure this out. 
// I know I'm close, I just need a little more time and I'm sure I'll get it. Thanks for understanding.
