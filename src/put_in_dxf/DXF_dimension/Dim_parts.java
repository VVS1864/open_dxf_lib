package put_in_dxf.DXF_dimension;

import java.util.ArrayList;
import java.util.HashMap;

import open_dxf_lib.calc;

/**
 * Parameterized dim --> dim with HashMaps of lines, arrows and text coords for
 * add to DXF. This is algorithm for make dimension
 * 
 * @author vlad
 *
 */
public class Dim_parts {
	//Map for add values to DXF
	public HashMap<String, String> lines = new HashMap<String, String>();
	// All visible lines of dim (without text)
	public ArrayList<double[]> lines_data = new ArrayList<double[]>();
	// lines for snap
	public ArrayList<double[]> snap_lines_data = new ArrayList<double[]>();
	
	//Coords for center of rotation (if angle 90)
	public double x0;
	public double y0;
	public boolean rotate;
	
	public double dim_measure;
	public String mtext;
	public int dim_text_change;
	
	public Dim_parts(DXF_dimension dim) {
		// If angle == 90, rotate all coords of dim to 90 degrees clockwise.
		// After building its need to rotate all parts of dim to
		// 90 degrees counterclockwise.
		x0 = dim.x1;
		y0 = dim.y1;
		if (dim.angle == 90.0) {
			rotate = true;
			double[] t;
			double sin = 1;
			t = calc.rotate_point_90d(x0, y0, dim.x2, dim.y2, sin);
			dim.x2 = t[0];
			dim.y2 = t[1];
			t = calc.rotate_point_90d(x0, y0, dim.x3, dim.y3, sin);
			dim.x3 = t[0];
			dim.y3 = t[1];
			t = calc.rotate_point_90d(x0, y0, dim.text_x, dim.text_y, sin);
			dim.text_x = t[0];
			dim.text_y = t[1];
			lines.put("angle_arrow_1", Double.toString(270.0));
			lines.put("angle_arrow_2",  Double.toString(90.0));
		} else {
			rotate = false;
			lines.put("angle_arrow_1",  Double.toString(0.0));
			lines.put("angle_arrow_2",  Double.toString(180.0));
		}
		// Find min and max x
		double x_min;
		double x_max;
		double y_min;
		double y_max;
		if (dim.x1 < dim.x2) {
			x_min = dim.x1;
			y_min = dim.y1;
			x_max = dim.x2;
			y_max = dim.y2;
		} else {
			x_min = dim.x2;
			y_min = dim.y2;
			x_max = dim.x1;
			y_max = dim.y1;
		}

		// Dim measure
		dim_measure = x_max - x_min;
		
		// Determinate text string
		//String text;
		if (dim.text.equals("")) {
			mtext = String.format("%.0f", dim_measure);
			System.out.println(mtext);
		} else {
			mtext = dim.text;
		}

		// Length of text string
		double text_length = dim.dim_text_width_factor * dim.dim_text_size * mtext.length();

		// Dim lines with ext_dim_lines
		int ext_dim_lines_sign = 1;
		if (dim.y3 < y_max) {
			ext_dim_lines_sign = -1;
		}

		// line 1
		double line1_x1 = x_min;
		double line1_y1 = y_min;
		double line1_x2 = x_min;
		double line1_y2 = dim.y3 + (dim.ext_dim_lines * ext_dim_lines_sign);

		put_line("line_1", line1_x1, line1_y1, line1_x2, line1_y2);

		// line 2
		double line2_x1 = x_max;
		double line2_y1 = y_max;
		double line2_x2 = x_max;
		double line2_y2 = dim.y3 + (dim.ext_dim_lines * ext_dim_lines_sign);

		put_line("line_2", line2_x1, line2_y1, line2_x2, line2_y2);

		// line 3 and text coords
		double line3_x1 = x_min - dim.ext_ticks; // by default
		double line3_y1 = dim.y3;
		double line3_x2 = x_max + dim.ext_ticks; // by default
		double line3_y2 = dim.y3;

		double text_y = dim.y3 + (dim.dim_text_size / 2.0) + dim.s;
		double text_x = x_min + dim_measure / 2.0; // by default

		// line3 x1 and x2 depend on dim_text_change
		// if text y is close to line 3 y - make text 
		//on line 3 (text_change = 2)
		dim_text_change = dim.dim_text_change;
		if (dim_text_change == 3) {
			double text_dy = Math.abs(dim.y3 - (dim.text_y - dim.dim_text_size / 2.0));
			if (text_dy < dim.s) {
				dim_text_change = 2;
			} 
			else {
				text_x = dim.text_x;
				text_y = dim.text_y;
			}
			System.out.println("3text change "+dim_text_change);
		}
		if (dim_text_change == 2) {
			if (dim.text_x == (x_max + x_min) / 2.0) {
				dim_text_change = 1;
				
			}
			else{
				double x1 = x_min - dim.ext_ticks;
				double x2 = x_max + dim.ext_ticks;
				double x3 = dim.text_x - (text_length / 2.0);
				double x4 = dim.text_x + (text_length / 2.0);
				line3_x1 = Math.min(x1, x3);
				line3_x2 = Math.max(x2, x4);
				text_x = dim.text_x;
				
			}
			System.out.println("2text change "+dim_text_change);
		}
		if (dim_text_change == 1) {
			// if text not fit between dim lines - move it outside dim lines
			// else text between dim lines on middle of line 3
			if (text_length > (dim_measure - dim.arrow_size * 2)) {
				line3_x1 = x_min - dim.ext_ticks;
				line3_x2 = x_max + dim.arrow_size * 3 + text_length;

				text_x = x_max + dim.arrow_size * 3 + text_length / 2.0;
			}
			System.out.println("1text change "+dim_text_change);
		}
		//Text coords
		put_point("text", text_x, text_y);
		put_line("line_3", line3_x1, line3_y1, line3_x2, line3_y2);
		// 3 Points
		put_point("point_1", x_min, y_min);
		put_point("point_2", x_max, y_max);
		put_point("point_3", x_max, dim.y3);
		// Arrows
		double s = dim.arrow_size;
		put_point("arrow_point_1", x_min, dim.y3);
		put_point("arrow_point_2", x_max, dim.y3);

		if (dim.arrow_type.equals("Arch")) {
			put_line("arrow_1", x_min - s / 2.0, dim.y3 - s / 2.0, x_min + s / 2.0, dim.y3 + s / 2.0);
			put_line("arrow_2", x_max - s / 2.0, dim.y3 - s / 2.0, x_max + s / 2.0, dim.y3 + s / 2.0);
		} 
		else if (dim.arrow_type.equals("Closed filled") || dim.arrow_type.equals("Open 30")) {
			int arrow_sign = 1; // arrows inside(1) or outside(-1) dimension
			if (dim_measure < s * 3.0)
				arrow_sign = -1;
			put_line("arrow_1", x_min, dim.y3, x_min + s * arrow_sign, dim.y3 + s / 5.0);
			put_line("arrow_2", x_min, dim.y3, x_min + s * arrow_sign, dim.y3 - s / 5.0);
			put_line("arrow_3", x_max, dim.y3, x_max - s * arrow_sign, dim.y3 + s / 5.0);
			put_line("arrow_4", x_max, dim.y3, x_max - s * arrow_sign, dim.y3 - s / 5.0);
		}

		// Snap lines
		put_snap_line(x_min, y_min, x_min, dim.y3); // line 1
		put_snap_line(x_max, y_max, x_max, dim.y3); // line 2
		put_snap_line(line3_x1, line3_y1, line3_x2, line3_y2); // line 3
		// text mid line
		put_snap_line(text_x - text_length / 2.0, text_y, text_x + text_length / 2.0, text_y);
			
		
	}

	//Methods for put coordinates, if dim was rotated, coords will be rotate back.
	public void put_point(String name, double x1, double y1) {
		if (rotate){
			double[] t;
			t = calc.rotate_point_90d(x0, y0, x1, y1, -1);
			x1 = t[0];
			y1 = t[1];
				
		}
		lines.put(name + "_x",  Double.toString(x1));
		lines.put(name + "_y",  Double.toString(y1));
	}

	public void put_line(String name, double x1, double y1, double x2, double y2) {
		if (rotate){
			double[] t;
			t = calc.rotate_line_90d(x0, y0, x1, y1, x2, y2, -1);
			x1 = t[0];
			y1 = t[1];
			x2 = t[2];
			y2 = t[3];
		}
		lines.put(name + "_x1",  Double.toString(x1));
		lines.put(name + "_x2",  Double.toString(x2));
		lines.put(name + "_y1",  Double.toString(y1));
		lines.put(name + "_y2",  Double.toString(y2));
		double[] d = { x1, y1, x2, y2 };
		lines_data.add(d);
	}

	public void put_snap_line(double x1, double y1, double x2, double y2) {
		if (rotate){
			double[] t;
			t = calc.rotate_line_90d(x0, y0, x1, y1, x2, y2, -1);
			x1 = t[0];
			y1 = t[1];
			x2 = t[2];
			y2 = t[3];
		}
		double[] d = { x1, y1, x2, y2 };
		snap_lines_data.add(d);
	}

	public double text_length(String text, double text_s_s, double text_size) {
		double text_length = 0.5 * text_s_s * text_size * text.length();
		return text_length;
	}

}
