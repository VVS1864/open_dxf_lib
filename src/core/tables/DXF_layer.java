package core.tables;

import core.Color_dxf;

/**
 * dxf layer, include color, linetype, linewidth.
 * @author vlad
 *
 */
public class DXF_layer {
	public String name;
	public String linetype;
	public Color_dxf color_dxf; //??? or rgb?
	public double width;
	
	public DXF_layer(String name, String linetype, Color_dxf color_dxf, double width) {
		this.name = name;
		this.linetype = linetype;
		this.color_dxf = color_dxf;
		this.width = width;
	}
}
