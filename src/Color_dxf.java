

import java.util.HashMap;

public class Color_dxf extends Color_rgb{
	
	public int dxf_color;
	public final HashMap<Integer, Color_rgb> dxf_rgb_color_map= new HashMap<>();
	
	public Color_dxf(int r, int g, int b) {
		super(r, g, b);
		
	}
	
	//public static int get_dxf_color(int r, int g, int b){
		
	//}
	
	
	
}
