package put_in_dxf;

import core.Color_dxf;
import core.Color_rgb;
import core.DXF_Utils;
import core.Section;
import core.Width;

public class DXF_circle extends Section {

	/**
	 * Multiple line string of circle entity for add to SECTION_ENTITIES
	 */
	public String dxf_entity;

	public DXF_circle(double x1, double y1, double R, Color_rgb color_rgb, int width) {
		section_name = "entity_circle.txt";
		body = super.init();
		Color_dxf color_dxf = new Color_dxf(color_rgb);
		Width width_dxf = new Width(width);
		values.put("handle", core.DXF_file.hex_handle);
		values.put("color", color_dxf.get_dxf_color_string());
		values.put("width", width_dxf.get_dxf_with_string());
		values.put("x1", Double.toString(x1));
		values.put("y1", Double.toString(y1));
		values.put("R", Double.toString(R));
		dxf_entity = DXF_Utils.replace_values(values, body);
	}

	@Override
	public String to_string() {
		return dxf_entity;
	}
}