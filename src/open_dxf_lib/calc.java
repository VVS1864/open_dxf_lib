package open_dxf_lib;


public class calc{
	
	public static double[] rotate_point(double x0, double y0, double x, double y, double sin, double cos){
		double a = x - x0;
		double b = y - y0;
		double x_new = a*cos + b*sin;
		double y_new = -a*sin + b*cos;
		x_new += x0;
		y_new += y0;
		double[] ret = {x_new, y_new};
		return ret;
	}
		
	public static double[] rotate_point_90d(double x0, double y0, double x, double y, double sin){
		double a = x - x0;
		double b = y - y0;
		double x_new = b*sin;
		double y_new = -a*sin;
		x_new += x0;
		y_new += y0;
		double[] ret = {x_new, y_new};
		return ret;
	}
	
	public static double[] rotate_line_90d(double x0, double y0, double[] line, double sin){
		double[] t1;
		double[] t2;
		t1 = rotate_point_90d(x0, y0, line[0], line[1], sin);
		t2 = rotate_point_90d(x0, y0, line[2], line[3], sin);
		double[] ret = {t1[0], t1[1], t2[0], t2[1]};
		return ret;
	}
	
	public static double[] rotate_line_90d(double x0, double y0, double x1, double y1, double x2, double y2, double sin){
		double[] t1;
		double[] t2;
		t1 = rotate_point_90d(x0, y0, x1, y1, sin);
		t2 = rotate_point_90d(x0, y0, x2, y2, sin);
		double[] ret = {t1[0], t1[1], t2[0], t2[1]};
		return ret;
	}
}