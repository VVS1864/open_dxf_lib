package open_dxf_lib;


import java.awt.Color;
import java.util.HashMap;
import java.util.Map;


public class Color_dxf extends Color_rgb{
	
	private int dxf_color;
	
	public Color_dxf(int r, int g, int b) {
		this(get_dxf_color(r, g, b));
	}
	
	public Color_dxf(Color_rgb c) {
		this(c.get_r(), c.get_g(), c.get_b());
	}
	
	public Color_dxf(int dxf_color) {
		super(0, 0, 0);
		if(DXF_file.color_map.dxf_rgb_color_map.containsKey(dxf_color)){
			this.dxf_color = dxf_color;
			Color_rgb normal_color = DXF_file.color_map.dxf_rgb_color_map.get(dxf_color);
			set_r(normal_color.get_r());
			set_g(normal_color.get_g());
			set_b(normal_color.get_b());
		}
		else{
			System.out.println("DXF color value error: color '" + dxf_color + "' is not found");
		}
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
		for (Map.Entry<Integer, Color_rgb> entry: DXF_file.color_map.dxf_rgb_color_map.entrySet()){
			int r2 = entry.getValue().get_r();
			int g2 = entry.getValue().get_g();
			int b2 = entry.getValue().get_b();
			double color_dist = Math.sqrt(Math.pow((r-r2), 2) + Math.pow((g-g2), 2) + Math.pow((b-b2), 2));
			
			if (color_dist < min_dist){
				min_dist = color_dist;
				dxf_color = entry.getKey();
			}
		}
		
		return dxf_color;
	}
	public int get_dxf_color(){
		return dxf_color;
	}
	
	public String get_dxf_color_string(){
		return Integer.toString(dxf_color);
	}
	
	public static void main(String[] args) {
		Color_map m = new Color_map();
		Color c = new Color(255, 30, 56);
	      if (c != null) {
	    	  System.out.println(c.getRed() + " " + c.getGreen() + " " + c.getBlue());
	    	  Color_dxf c_dxf = new Color_dxf(c.getRed(), c.getGreen(), c.getBlue());
	    	  System.out.println(c_dxf.get_r() + " " + c_dxf.get_g() + " " + c_dxf.get_b());
	      }
	}
	
}
