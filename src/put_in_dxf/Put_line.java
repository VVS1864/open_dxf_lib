package put_in_dxf;

import core.Color_dxf;
import core.Color_rgb;
import core.DXF_Utils;
import core.Section;
import core.dash_type;

public class Put_line extends Section{
	
	/**
	 * Multiple line string of line entity for add to SECTION_ENTITIES
	 */
	public String dxf_line;
	
	public Put_line(double x1, double y1, double x2, double y2, dash_type dash, double factor, Color_rgb color_rgb) {
		section_name = "entity_line.txt";
		body = super.init();
		Color_dxf color_dxf = new Color_dxf(color_rgb);
		values.put("x1", Double.toString(x1));
		values.put("y1", Double.toString(y1));
		values.put("x2", Double.toString(x2));
		values.put("y2", Double.toString(y2));
		values.put("dash", dash.toString());
		values.put("factor", Double.toString(factor));
		dxf_line = DXF_Utils.replace_values(values, body);
	}	
	
	@Override
	public String to_string() {
		return dxf_line;
}
}