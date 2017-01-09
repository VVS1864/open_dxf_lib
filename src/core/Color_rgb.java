package core;


public class Color_rgb {
	public int r;
	public int g;
	public int b;
	
	Color_rgb(int r, int g, int b) {
		this.r = r;
		this.g = g;
		this.b = b;
	}
	
	public String get_rgb_string(){
		String rgb = Integer.toString(r) + ", " + Integer.toString(g) + ", " + Integer.toString(b);
		return rgb;
	}
}
