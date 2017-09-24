package put_in_dxf.DXF_dimension;

import java.util.HashMap;

import open_dxf_lib.Color_dxf;
import open_dxf_lib.Color_rgb;
import open_dxf_lib.DXF_Utils;
import open_dxf_lib.DXF_file;
import open_dxf_lib.Section;
import open_dxf_lib.Width;
import put_in_dxf.DXF_dimension.Dim_parts;

public class DXF_dimension extends Section {

	/**
	 * 
	 */
	//Parts of another sections for add to dxf file
	public String dxf_entity = "";
	public String dxf_tables_BLOCK_RECORDS = "";
	public String dxf_tables_BLKREFS = "";
	public String dxf_tables_ACAD_REACTORS = "";
	/**
	 * In BLOCKS:
	 * block dim
	 * {
	 * 3 lines
	 * 2 arrows
	 * mtext
	 * 3 points
	 * endblock
	 * }
	 * Block_oblique
	 * {
	 * endblock
	 * }
	 */
	public String dxf_blocks = "";
	
	public String handle_block_record_dim_oblique;
	
	public double x1;
	public double y1; 
	public double x2;
	public double y2; 
	public double x3;
	public double y3;
	public double ext_dim_lines;
	public double ext_ticks;
	public double dim_text_size;
	public double dim_text_width_factor; 
	public double s;
	public int dim_text_change;
	public double text_x;
	public double text_y;
	public double arrow_size;
	public double angle;
	public String arrow_type;
	public String text;
	public boolean key_oblique;
	/**
	 * 
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 * @param x3
	 * @param y3
	 * @param ext_dim_lines size of Extend beyond dimension lines
	 * @param ext_ticks size of Extend beyond ticks
	 * @param dim_text_size
	 * @param dim_text_width_factor distanse between letters of text
	 * @param s text offset from dim line
	 * @param dim_text_change 
	 * 1 - text position unchanged, auto placing text;
	 * 2 - text position changed, text on dim line not on middle dim line3;
	 * 3 - text position changed, text is free;
	 * See figs/Dim text change.png
	 * @param text_x x coord of CENTER point of dim text
	 * @param text_y
	 * @param arrow_size Size of arrowhead 
	 * @param arrow_type
	 * @param angle 0 - horizontal, 90 - vertical
	 * @param text if it is "" then text is dimension distance
	 * @param color_rgb
	 * @param key_oblique true if block oblique was created, else false,
	 */
	public DXF_dimension(
			double x1,
			double y1, 
			double x2,
			double y2, 
			double x3,
			double y3, 
			double ext_dim_lines,
			double ext_ticks,
			double dim_text_size,
			double dim_text_width_factor, 
			double s, 
			int dim_text_change,
			double text_x,
			double text_y,
			double arrow_size,
			double angle, 
			String arrow_type,
			String text,
			Color_rgb color_rgb,
			boolean key_oblique,
			String handle_block_record_dim_oblique) {
		
		this.x1 = x1;
		this.y1 = y1; 
		this.x2 = x2;
		this.y2 = y2; 
		this.x3 = x3;
		this.y3 = y3;
		this.ext_dim_lines = ext_dim_lines;
		this.ext_ticks = ext_ticks;
		this.dim_text_size = dim_text_size;
		this.dim_text_width_factor = dim_text_width_factor;
		this.s = s;
		this.dim_text_change = dim_text_change;
		this.text_x = text_x;
		this.text_y = text_y;
		this.arrow_size = arrow_size;
		this.angle = angle;
		this.arrow_type = arrow_type;
		this.text = text;
		
		section_name = "entity_dimension/dimension_entity.txt";
		body = super.init();
		
		Color_dxf color_dxf = new Color_dxf(color_rgb);
		
		
		//Get coords of dim parts (lines, arrows, text...)
		//HashMap<String, Integer> dim_parts_coords = new HashMap<String, Integer>();
		
		Dim_parts maked_dim = new Dim_parts(this);
		dim_text_change = maked_dim.dim_text_change;
		dim_change DXF_dim_text_change = new dim_change(dim_text_change);
		
		values.putAll(maked_dim.lines);
		
		
		//dimension_index
		open_dxf_lib.DXF_file.dimension_index += 1;
		
		String dim_handle = open_dxf_lib.DXF_file.hex_handle; //???String
		//Handles:
		values.put("handle", dim_handle);
		open_dxf_lib.DXF_file.next_handle();
		values.put("handle_block_records_dim", open_dxf_lib.DXF_file.hex_handle);
		open_dxf_lib.DXF_file.next_handle();
		values.put("handle_block_dim", open_dxf_lib.DXF_file.hex_handle);
		open_dxf_lib.DXF_file.next_handle();
		values.put("handle_endblock_dim", open_dxf_lib.DXF_file.hex_handle);
		open_dxf_lib.DXF_file.next_handle();
		values.put("handle_block_dim_line_1", open_dxf_lib.DXF_file.hex_handle);
		open_dxf_lib.DXF_file.next_handle();
		values.put("handle_block_dim_line_2", open_dxf_lib.DXF_file.hex_handle);
		open_dxf_lib.DXF_file.next_handle();
		values.put("handle_block_dim_line_3", open_dxf_lib.DXF_file.hex_handle);
		open_dxf_lib.DXF_file.next_handle();
		values.put("handle_block_dim_insert_1", open_dxf_lib.DXF_file.hex_handle);
		open_dxf_lib.DXF_file.next_handle();
		values.put("handle_block_dim_blkrefs_insert_1", open_dxf_lib.DXF_file.hex_handle);
		open_dxf_lib.DXF_file.next_handle();
		values.put("handle_block_dim_insert_2", open_dxf_lib.DXF_file.hex_handle);
		open_dxf_lib.DXF_file.next_handle();
		values.put("handle_block_dim_blkrefs_insert_2", open_dxf_lib.DXF_file.hex_handle);
		open_dxf_lib.DXF_file.next_handle();
		values.put("handle_block_dim_mtext", open_dxf_lib.DXF_file.hex_handle);
		open_dxf_lib.DXF_file.next_handle();
		values.put("handle_block_dim_point_1", open_dxf_lib.DXF_file.hex_handle);
		open_dxf_lib.DXF_file.next_handle();
		values.put("handle_block_dim_point_2", open_dxf_lib.DXF_file.hex_handle);
		open_dxf_lib.DXF_file.next_handle();
		values.put("handle_block_dim_point_3", open_dxf_lib.DXF_file.hex_handle);
		open_dxf_lib.DXF_file.next_handle();
		
		values.put("color", color_dxf.get_dxf_color_string());
		values.put("x1", Double.toString(x1));
		values.put("y1", Double.toString(y1));
		values.put("x2", Double.toString(x2));
		values.put("y2", Double.toString(y2));
		values.put("x3", Double.toString(x3));
		values.put("y3", Double.toString(y3));
		values.put("ext_dim_lines", Double.toString(ext_dim_lines));
		values.put("ext_ticks", Double.toString(ext_ticks));
		values.put("arrow_size", Double.toString(arrow_size));
		values.put("dim_text_size", Double.toString(dim_text_size));
		values.put("dim_text_ss", Double.toString(dim_text_width_factor));
		values.put("s", Double.toString(s));
		values.put("dim_measure", Double.toString(maked_dim.dim_measure));
		values.put("text", text);
		values.put("mtext", maked_dim.mtext);
		values.put("angle", Double.toString(angle));
		values.put("DXF_dim_text_change", Integer.toString(DXF_dim_text_change.DXF_dim_text_change));
		values.put("DXF_dim_text_change_2", Integer.toString(DXF_dim_text_change.DXF_dim_text_change2));
		values.put("dim_ind", "D"+Integer.toString(open_dxf_lib.DXF_file.dimension_index));
		
		//Add dimension handle to ACAD_REACTORS in SECTION_TABLES
		dxf_tables_ACAD_REACTORS += ("330\n" + dim_handle + "\n");
		
		//Add block_records_handle and dim index to BLOCK_RECORDS in SECTION_TABLES
		dxf_tables_BLOCK_RECORDS = put_to_section("entity_dimension/entity_block_record.txt", dxf_tables_BLOCK_RECORDS);
		
		
		//Add  handle of block, block_records and dim index to BLOCKS in SECTION_BLOCKS
		dxf_blocks = put_to_section("entity_dimension/entity_block.txt", dxf_blocks);
		
		//3 Lines of dimension:
		//Add line 1 to BLOCKS
		dxf_blocks = put_to_section("entity_dimension/entity_dim_line_1.txt", dxf_blocks);
		//Add line 2 to BLOCKS
		dxf_blocks = put_to_section("entity_dimension/entity_dim_line_2.txt", dxf_blocks);
		//Add line 3 to BLOCKS
		dxf_blocks = put_to_section("entity_dimension/entity_dim_line_3.txt", dxf_blocks);
		
		//2 Arrows:
		boolean new_oblique = false;
		String MY_ARROW_BLOCK_REF = "";
		if(arrow_type.equals("Arch")){
			//Add 2 INSERTs to BLOCKS (this is lines)
			dxf_blocks = put_to_section("entity_dimension/entity_insert_1.txt", dxf_blocks);
			dxf_blocks = put_to_section("entity_dimension/entity_insert_2.txt", dxf_blocks);
			
			//Prepare Block Oblique if it necessary
			//if block oblique not exist - create it
			if(key_oblique == false){
				//Handles
				open_dxf_lib.DXF_file.next_handle();
				handle_block_record_dim_oblique = open_dxf_lib.DXF_file.hex_handle;
				values.put("handle_block_record_dim_oblique", open_dxf_lib.DXF_file.hex_handle);
				open_dxf_lib.DXF_file.next_handle();
				values.put("handle_block_dim_oblique", open_dxf_lib.DXF_file.hex_handle);
				open_dxf_lib.DXF_file.next_handle();
				values.put("handle_endblock_dim_oblique", open_dxf_lib.DXF_file.hex_handle);
				open_dxf_lib.DXF_file.next_handle();
				values.put("handle_oblique_line", open_dxf_lib.DXF_file.hex_handle);
				
				dxf_tables_BLOCK_RECORDS = put_to_section("entity_dimension/block_oblique/block_record.txt", dxf_tables_BLOCK_RECORDS);
				
				key_oblique = true;
				new_oblique = true;
			}
			else{
				values.put("handle_block_record_dim_oblique", handle_block_record_dim_oblique);
			}
			
			//Read ARROW_BLOCK_REF.txt, replace values in it
			MY_ARROW_BLOCK_REF = "\n" + super.init("entity_dimension/ARROW_BLOCK_REF.txt");
			MY_ARROW_BLOCK_REF = DXF_Utils.replace_values(values, MY_ARROW_BLOCK_REF);
			
		}
		else if (arrow_type.equals("Closed filled") || arrow_type.equals("Open 30")){
			//Add 2 SOLIDs to BLOCKS (it is not correct, it TODO)
			dxf_blocks = put_to_section("entity_dimension/entity_solid_1.txt", dxf_blocks);
			dxf_blocks = put_to_section("entity_dimension/entity_solid_2.txt", dxf_blocks);
			
		}
		//add MY_ARROW_BLOCK_REF to Map values as new field for use in SECTION_ENTITIES
		values.put("MY_ARROW_BLOCK_REF", MY_ARROW_BLOCK_REF);
		//BLKREFS for SECTION_TABLES
		dxf_tables_BLKREFS = put_to_section("entity_dimension/BLKREFS.txt", dxf_tables_BLKREFS);
		
		//MTEXT
		String MY_TEXT_ROTATE = "";
		if(angle == 90){
			MY_TEXT_ROTATE = "\n" + super.init("entity_dimension/TEXT_ROTATE.txt");
		}
		values.put("MY_TEXT_ROTATE", MY_TEXT_ROTATE);
		
		//Add mtext to BLOCKS
		dxf_blocks = put_to_section("entity_dimension/entity_dim_mtext.txt", dxf_blocks);
		
		//Add 3 POINTS to BLOCKS
		dxf_blocks = put_to_section("entity_dimension/entity_dim_point_1.txt", dxf_blocks);
		dxf_blocks = put_to_section("entity_dimension/entity_dim_point_2.txt", dxf_blocks);
		dxf_blocks = put_to_section("entity_dimension/entity_dim_point_3.txt", dxf_blocks);
		
		//Dimension ENDBLK
		dxf_blocks = put_to_section("entity_dimension/entity_endblk.txt", dxf_blocks);
		dxf_entity = DXF_Utils.replace_values(values, body);
		
		//Add Block OBLIQUE to BLOCKS
		if (new_oblique == true){
			dxf_blocks = put_to_section("entity_dimension/block_oblique/block.txt", dxf_blocks);
		}
		
	}
	
	public String put_to_section(String text_name, String section_name){
		String text = super.init(text_name);
		section_name += "\n" + DXF_Utils.replace_values(values, text);
		return section_name;
	}
	
	public int dim_direction(){
		int direction;
		if (angle == 0){
			if (y3 < y2){
				direction = -1;
			}
			else{
				direction = 1;
			}
		}
		else{
			if (x3 < x2){
				direction = -1;
			}
			else{
				direction = 1;
			}
		}
		return direction;
	}

	@Override
	public String to_string() {
		return dxf_entity;
	}
}