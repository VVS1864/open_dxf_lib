package put_in_dxf;

import core.Color_dxf;
import core.Color_rgb;
import core.DXF_Utils;
import core.Section;
import core.dash_type;
import core.Width;

public class DXF_text extends Section {

	/**
	 * Multiple line string of text entity for add to SECTION_ENTITIES
	 */
	public String dxf_entity;
	
	/**
	 * 
	 * @param x1 x coords of left bottom corner of text line
	 * @param y1 y coords of left bottom corner of text line
	 * @param text_size
	 * @param angle angle of anticlockwise rotation text line
	 * @param text_ss distanse between letters of text
	 * @param color_rgb
	 * @param text content of text string (encode in cp1251 ???)
	 */
	public DXF_text(double x1, double y1, double text_size, double angle, double text_ss, Color_rgb color_rgb, String text) {
		section_name = "entity_text.txt";
		body = super.init();
		Color_dxf color_dxf = new Color_dxf(color_rgb);
		values.put("handle", core.DXF_file.hex_handle);
		values.put("color", color_dxf.get_dxf_color_string());
		values.put("x1", Double.toString(x1));
		values.put("y1", Double.toString(y1));
		values.put("text_size", Double.toString(text_size));
		values.put("angle", Double.toString(angle));
		values.put("text_ss", Double.toString(text_ss));
		values.put("text", text);
		dxf_entity = DXF_Utils.replace_values(values, body);
	}

	@Override
	public String to_string() {
		return dxf_entity;
	}
}