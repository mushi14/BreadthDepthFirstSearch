import java.util.ArrayList;

public class InsertionSort {
	
	public static void sort(ArrayList<Integer> sortedTargets) {
		
		for (int i = 0; i < sortedTargets.size(); i++) {
			int insertElem = sortedTargets.get(i);
			int position = i;
			while (position > 0 && insertElem < sortedTargets.get(position-1)) {
				sortedTargets.set(position, sortedTargets.get(position-1));
				position--;
			}
			sortedTargets.set(position, insertElem);
		}
		
		for (int i = 0; i < sortedTargets.size(); i++) {
			System.out.println(sortedTargets.get(i));
		}
	}
}

