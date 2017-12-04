package open_dxf_lib;

public class Color_rgb {
	private int r;
	private int g;
	private int b;
	private String err_str = "rgb color value error, ";
	public Color_rgb(int r, int g, int b) {
		set_r(r);
		set_g(g);
		set_b(b);
	}
	
	public Color_rgb(int[] rgb) {
		this(rgb[0], rgb[1], rgb[2]);
		
	}
	
	public void set_r(int r){
		if(r<=255 && r>=0){this.r = r;}
		else{System.out.println(err_str + "r = " + r);}
	}
	
	public void set_g(int g){
		if(g<=255 && g>=0){this.g = g;}
		else{System.out.println(err_str + "g = " + g);}
	}
	
	public void set_b(int b){
		if(b<=255 && b>=0){this.b = b;}
		else{System.out.println(err_str + "b = " + b);}
	}
	public int get_r(){return r;}
	public int get_g(){return g;}
	public int get_b(){return b;}
	
	public int[] get_rgb(){return new int[]{r,g,b};};
	
	public String get_rgb_string(){
		String rgb = Integer.toString(r) + ", " + Integer.toString(g) + ", " + Integer.toString(b);
		return rgb;
	}

}
