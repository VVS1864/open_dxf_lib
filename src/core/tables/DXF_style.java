package core.tables;

/**
 * text style, include handle, text size and text width factor
 */
public class DXF_style {
	public String name;
	public String handle;
	public double text_size;
	public double width_factor;
	
	public DXF_style(String name, String handle, double text_size, double width_factor){
		this.name = name;
		this.handle = handle;
		this.text_size = text_size;
		this.width_factor = width_factor;
	}

}
