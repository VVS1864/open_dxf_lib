package put_in_dxf.DXF_dimension;

import java.util.ArrayList;
import java.util.HashMap;
import core.calc;

/**
 * Parameterized dim --> dim with HashMaps of lines, arrows and text coords for add to DXF.
 * This is algorithm for make dimension
 * @author vlad
 *
 */
public class Dim_parts{
	
	public HashMap<String, Double> lines;
	public HashMap<String, Double> arrows;
	public HashMap<String, Double> text;
	
	//All visible lines of dim (without text)
	public ArrayList<double[]> lines_data = new ArrayList<double[]>(); 
	//lines for snap
	public ArrayList<double[]> snap_lines_data = new ArrayList<double[]>(); 
	
	public Dim_parts(DXF_dimension dim) {
		// If angle == 90, rotate all coords of dim to 90 degrees clockwise.
		// After building its need to rotate all parts of dim to 
		// 90 degrees counterclockwise.
		if (dim.angle == 90) {
			double[] t;
			double sin = 1;
			t = calc.rotate_point_90d(dim.x1, dim.y1, dim.x2, dim.y2, sin);
			dim.x2 = t[0];
			dim.y2 = t[1];
			t = calc.rotate_point_90d(dim.x1, dim.y1, dim.x3, dim.y3, sin);
			dim.x3 = t[0];
			dim.y3 = t[1];
			t = calc.rotate_point_90d(dim.x1, dim.y1, dim.text_x, dim.text_y, sin);
			dim.text_x = t[0];
			dim.text_y = t[1];
		}
		// Find min and max x
		double x_min;
		double x_max;
		double y_min;
		double y_max;
		if (dim.x1 < dim.x2){
			x_min = dim.x1;
			y_min = dim.y1;
			x_max = dim.x2;
			y_max = dim.y2;
		}
		else{
			x_min = dim.x2;
			y_min = dim.y2;
			x_max = dim.x1;
			y_max = dim.y1;
		}
		
		//Dim measure
		double dim_measure = x_max-x_min;
		
		//Determinate text string
		String text;
		if (dim.text.equals("")){
			text = Double.toString(dim_measure);
			text = String.format("%.0f", text);
		}
		else{
			text = dim.text;
		}
		
		//Length of text string
		double text_length = 0.5*dim.dim_text_ss*dim.dim_text_size * text.length();
		
		// Dim lines with ext_dim_lines
		int ext_dim_lines_sign = 1;
		if (dim.y3 < y_max){
			ext_dim_lines_sign = -1;
		}
		
		//line 1
		double line1_x1 = x_min;
		double line1_y1 = y_min;
		double line1_x2 = x_min;
		double line1_y2 = dim.y3 + dim.ext_dim_lines*ext_dim_lines_sign;
		
		lines.put("line_1_x1", line1_x1);
		lines.put("line_1_x2", line1_x2);
		lines.put("line_1_y1", line1_y1);
		lines.put("line_1_y2", line1_y2);
		double[] d1 = { line1_x1, line1_y1, line1_x2, line1_y2 };
		lines_data.add(d1);

		// line 2
		double line2_x1 = x_max;
		double line2_y1 = y_max;
		double line2_x2 = x_max;
		double line2_y2 = dim.y3 + dim.ext_dim_lines * ext_dim_lines_sign;

		lines.put("line_2_x1", line2_x1);
		lines.put("line_2_x2", line2_x2);
		lines.put("line_2_y1", line2_y1);
		lines.put("line_2_y2", line2_y2);
		double[] d2 = { line2_x1, line2_y1, line2_x2, line2_y2 };
		lines_data.add(d2);
		
		// line 3 and text coords
		double line3_x1 = x_min - dim.ext_ticks; //by default
		double line3_y1 = dim.y3;
		double line3_x2 = x_max + dim.ext_ticks; //by default
		double line3_y2 = dim.y3;
		
		double text_y = dim.y3 + dim.dim_text_size/2.0 + dim.s;
		double text_x = x_max + dim_measure/2.0; //by default
		
		int arrow_sign = 1; //arrows inside(1) or outside(-1) dimension 
		
		//line3 x1 and x2 depend on dim_text_change
		if (dim.dim_text_change == 3){
			double text_dy = Math.abs(dim.y3 - (dim.text_y - dim.dim_text_size/2.0));
			if (text_dy < dim.s){
				dim.dim_text_change = 2;
			}
			else{
				text_x = dim.text_x;
				text_y = dim.text_y;
			}
		}
		if (dim.dim_text_change == 1){
			
			//if text not fit between dim lines - move it outside dim lines
			//else text between dim lines on middle of line 3
			if (text_length > (dim_measure-dim.arrow_size*2)){
				line3_x1 = x_min - dim.ext_ticks;
				line3_x2 = x_max + dim.arrow_size*3 + text_length;
				
				text_x = x_max + dim.arrow_size*3 + text_length/2.0;
				
				arrow_sign = -1;
			}
		}
		else if(dim.dim_text_change == 2){
			double x1 = x_min - dim.ext_ticks;
			double x2 = x_max + dim.ext_ticks;
			double x3 = dim.text_x - text_length;
			double x4 = dim.text_x + text_length;
			line3_x1 = Math.min(x1, x3);
			line3_x2 = Math.max(x2, x4);
		}
	}
	
	public double text_length(String text, double text_s_s, double text_size){
		double text_length = 0.5*text_s_s*text_size * text.length();
		return text_length;
	}

}


	


