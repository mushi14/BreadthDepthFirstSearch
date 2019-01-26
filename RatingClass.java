public class RatingClass {
	
	private int key;
	private int target;
	private int rating;
	
	public RatingClass(int k) {
		key = k;
	}
	
	public void setTarget(int t) {
		target = t;
	}
	
	public int getTarget() {
		return target;
	}
	
	public void setRating(int r) {
		rating = r;
	}
	
	public int getRating() {
		return rating;
	}
	
	public String toString() {
		return target + " " + rating;
	}
}