package put_in_dxf.DXF_dimension;

import core.Color_dxf;
import core.Color_rgb;
import core.DXF_Utils;
import core.Section;
import core.Width;

public class DXF_dimension extends Section {

	/**
	 * Multiple line string of circle entity for add to SECTION_ENTITIES
	 */
	public String dxf_entity;

	public DXF_dimension(
			double x1,
			double y1, 
			double R, 
			Color_rgb color_rgb, 
			int width) {
		section_name = "entity_circle.txt";
		body = super.init();
		Color_dxf color_dxf = new Color_dxf(color_rgb);
		Width width_dxf = new Width(width);
		dim_change DXF_dim_text_change = new dim_change(dim_text_change);
		//Handles:
		values.put("handle", core.DXF_file.hex_handle);
		core.DXF_file.next_handle();
		values.put("handle_block_records_dim", core.DXF_file.hex_handle);
		core.DXF_file.next_handle();
		values.put("handle_block_dim", core.DXF_file.hex_handle);
		core.DXF_file.next_handle();
		values.put("handle_endblock_dim", core.DXF_file.hex_handle);
		core.DXF_file.next_handle();
		values.put("handle_block_dim_line_1", core.DXF_file.hex_handle);
		core.DXF_file.next_handle();
		values.put("handle_block_dim_line_2", core.DXF_file.hex_handle);
		core.DXF_file.next_handle();
		values.put("handle_block_dim_line_3", core.DXF_file.hex_handle);
		core.DXF_file.next_handle();
		values.put("handle_block_dim_insert_1", core.DXF_file.hex_handle);
		core.DXF_file.next_handle();
		values.put("handle_block_dim_blkrefs_insert_1", core.DXF_file.hex_handle);
		core.DXF_file.next_handle();
		values.put("handle_block_dim_insert_2", core.DXF_file.hex_handle);
		core.DXF_file.next_handle();
		values.put("handle_block_dim_blkrefs_insert_2", core.DXF_file.hex_handle);
		core.DXF_file.next_handle();
		values.put("handle_block_dim_mtext", core.DXF_file.hex_handle);
		core.DXF_file.next_handle();
		values.put("handle_block_dim_point_1", core.DXF_file.hex_handle);
		core.DXF_file.next_handle();
		values.put("handle_block_dim_point_2", core.DXF_file.hex_handle);
		core.DXF_file.next_handle();
		values.put("handle_block_dim_point_3", core.DXF_file.hex_handle);
		core.DXF_file.next_handle();
		
		
		
		values.put("color", color_dxf.get_dxf_color_string());
		values.put("x1", Double.toString(x1));
		values.put("y1", Double.toString(y1));
		values.put("x2", Double.toString(x2));
		values.put("y2", Double.toString(y2));
		values.put("x3", Double.toString(x3));
		values.put("y3", Double.toString(y3));
		values.put("ext_dim_lines", Double.toString(ext_dim_lines));
		values.put("ext_ticks", Double.toString(ext_ticks));
		values.put("dim_text_size", Double.toString(dim_text_size));
		values.put("text", text);
		values.put("DXF_dim_text_change", Integer.toString(DXF_dim_text_change.DXF_dim_text_change));
		values.put("DXF_dim_text_change2", Integer.toString(DXF_dim_text_change.DXF_dim_text_change2));
		
		dxf_entity = DXF_Utils.replace_values(values, body);
	}

	@Override
	public String to_string() {
		return dxf_entity;
	}
}