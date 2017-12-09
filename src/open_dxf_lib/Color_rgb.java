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
	public Color_rgb(float r, float g, float b) {
		this(new float[] {r, g, b});
	}
	public Color_rgb(int[] rgb) {
		this(rgb[0], rgb[1], rgb[2]);
		
	}
	
	public Color_rgb(float[] rgb) {
		float_set_r(rgb[0]);
		float_set_g(rgb[1]);
		float_set_b(rgb[2]);
	}
	
	public void float_set_r(float r){
		if(r<=1 && r>=0) this.r = float_to_int(r);
		else{
			System.out.println(err_str + "float color out of [0,1] range "+"r = " + r);
		}
	}
	
	public void float_set_g(float g){
		if(g<=1 && g>=0)this.g = float_to_int(g);
		else{
			System.out.println(err_str + "float color out of [0,1] range "+"g = " + g);
		}
	}
	
	public void float_set_b(float b){
		if(b<=1 && b>=0) this.b = float_to_int(b);
		else{
			System.out.println(err_str + "float color out of [0,1] range "+"b = " + b);
		}
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
	
	public float get_float_r(){return int_to_float(r);}
	public float get_float_g(){return int_to_float(g);}
	public float get_float_b(){return int_to_float(b);}
	public float[] get_float_rgb(){
		return new float[]{
			int_to_float(r),
			int_to_float(g),
			int_to_float(b)
			};
		}
	
	public String get_rgb_string(){
		String rgb = Integer.toString(r) + ", " + Integer.toString(g) + ", " + Integer.toString(b);
		return rgb;
	}
	
	public int float_to_int(float f) {
		return Math.round(255*f);
	}
	public float int_to_float(int i) {
		return (float)i / 255.0f;
	}

}
