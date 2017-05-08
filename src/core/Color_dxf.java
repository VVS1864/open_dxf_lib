package core;


import java.util.HashMap;
import java.util.Map;

public class Color_dxf extends Color_rgb{
	
	public int dxf_color;
	public final HashMap<Integer, Color_rgb> dxf_rgb_color_map = new HashMap<>();
	
	public Color_dxf(int r, int g, int b) {
		super(r, g, b);
		this.dxf_color = get_dxf_color(r, g, b);
		Color_rgb normal_color = DXF_file.dxf_rgb_color_map.get(dxf_color);
		this.r = normal_color.r;
		this.g = normal_color.g;
		this.b = normal_color.b;
	}
	
	public Color_dxf(Color_rgb c) {
		this(c.r, c.g, c.b);
	}
	
	/**
	 * This method for find nearest dxf color (int value) in dxf-rgb table for any rgb color (0-255, 0-255, 0-255)
	 * @param r
	 * @param g
	 * @param b
	 * @return int number of dxf color in dxf-rgb table.
	 */
	public static int get_dxf_color(int r, int g, int b){
		double min_dist = 2500;
		int dxf_color = 7;
		for (Map.Entry<Integer, Color_rgb> entry: DXF_file.dxf_rgb_color_map.entrySet()){
			int r2 = entry.getValue().r;
			int g2 = entry.getValue().g;
			int b2 = entry.getValue().b;
			double color_dist = Math.sqrt(Math.pow((r-r2), 2) + Math.pow((g-g2), 2) + Math.pow((b-b2), 2));
			
			if (color_dist < min_dist){
				min_dist = color_dist;
				dxf_color = entry.getKey();
			}
		}
		
		return dxf_color;
	}
	
	public String get_dxf_color_string(){
		return Integer.toString(dxf_color);
	}
	
	
	
}
