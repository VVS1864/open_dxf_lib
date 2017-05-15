package core;


public class calc{
	
	public static double[] rotate_point(double x0, double y0, double x, double y, double sin, double cos){
		double a = x - x0;
		double b = y - y0;
		double x_new = a*cos + b*sin;
		double y_new = -a*sin + b*cos;
		double[] ret = {x_new, y_new};
		return ret;
	}
	
	public static double[] rotate_point_90d(double x0, double y0, double x, double y, double sin){
		double a = x - x0;
		double b = y - y0;
		double x_new = b*sin;
		double y_new = -a*sin;
		double[] ret = {x_new, y_new};
		return ret;
	}
}