package open_dxf_lib;

import java.io.BufferedReader;
import java.io.BufferedWriter;

import java.io.IOException;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import put_in_dxf.DXF_line;
import put_in_dxf.DXF_arc;
import put_in_dxf.DXF_circle;
import put_in_dxf.DXF_text;
import put_in_dxf.DXF_dimension.DXF_dimension;
import core.tables.Section_TABLES;
import open_dxf_lib.dash_type;
import open_file.parse_dxf;

public class DXF_file {
	public String dxf_large_string = "";
	
	public Section_HEADER SECTION_HEADER = new Section_HEADER();
	public Section_CLASSES SECTION_CLASSES = new Section_CLASSES();
	public Section_TABLES SECTION_TABLES = new Section_TABLES();
	public Section_BLOCKS SECTION_BLOCKS = new Section_BLOCKS();
	public Section_ENTITIES SECTION_ENTITIES = new Section_ENTITIES();
	public Section_OBJECTS SECTION_OBJECTS = new Section_OBJECTS();

	//public HashMap<Integer, Color_rgb> dxf_rgb_color_map = new HashMap<>();
	public static Color_map color_map = new Color_map();
	public String file;
	public Mode mode;

	public static String hex_handle = "BB";
	public static int int_handle = Integer.parseInt(hex_handle, 16);
	public static int dimension_index = 0;
	// Key - True if block_oblique was created.
	public static boolean key_oblique = false;
	public static String handle_block_record_dim_oblique;

	public DXF_file(Mode key, String file) {
		this.file = file;
		this.mode = key;
		if(mode == Mode.Open_file){
			read_file(file);
		}
	}

	public void put_base(String entity) {
		next_handle();
		SECTION_ENTITIES.MY_ENTITIES += (entity + "\n");
	}

	public void put_line(double x1, double y1, double x2, double y2, dash_type dash, double factor, Color_rgb color,
			int width) {

		String entity = new DXF_line(x1, y1, x2, y2, dash, factor, color, width).dxf_entity;
		put_base(entity);
	}

	public void put_circle(double x1, double y1, double R, Color_rgb color, int width) {

		String entity = new DXF_circle(x1, y1, R, color, width).dxf_entity;
		put_base(entity);
	}

	public void put_arc(double x1, double y1, double R, double start, double extent, Color_rgb color, int width) {

		String entity = new DXF_arc(x1, y1, R, start, extent, color, width).dxf_entity;
		put_base(entity);
	}

	public void put_text(double x1, double y1, double text_size, double angle, double text_ss, Color_rgb color,
			String text) {

		String entity = new DXF_text(x1, y1, text_size, angle, text_ss, color, text).dxf_entity;
		put_base(entity);
	}

	public void put_dimension(double x1, double y1, double x2, double y2, double x3, double y3, double ext_dim_lines,
			double ext_ticks, double dim_text_size, double dim_text_width_factor, double s, int dim_text_change, double text_x,
			double text_y, double arrow_size, double angle, String arrow_type, String text, Color_rgb color_rgb) {

		DXF_dimension dim = new DXF_dimension(x1, y1, x2, y2, x3, y3, ext_dim_lines, ext_ticks, dim_text_size,
				dim_text_width_factor, s, dim_text_change, text_x, text_y, arrow_size, angle, arrow_type, text, color_rgb,
				key_oblique, handle_block_record_dim_oblique);

		// Write all dxf dimensions` parts to dxf sections
		put_base(dim.dxf_entity);
		SECTION_BLOCKS.MY_BLOCKS += (dim.dxf_blocks);
		SECTION_TABLES.MY_ACAD_REACTORS += (dim.dxf_tables_ACAD_REACTORS);
		SECTION_TABLES.MY_BLOCK_RECORDS += (dim.dxf_tables_BLOCK_RECORDS);
		SECTION_TABLES.MY_BLKREFS += (dim.dxf_tables_BLKREFS);
		handle_block_record_dim_oblique = dim.handle_block_record_dim_oblique;
		key_oblique = dim.key_oblique;
		
	}

	public void save_file() {
		String DXF = (SECTION_HEADER.to_string() + "\n");
		DXF += (SECTION_CLASSES.to_string() + "\n");
		DXF += (SECTION_TABLES.to_string() + "\n");
		DXF += (SECTION_BLOCKS.to_string() + "\n");
		DXF += (SECTION_ENTITIES.to_string() + "\n");
		DXF += SECTION_OBJECTS.to_string();

		Path newFile = Paths.get(file);
		try {
			Files.deleteIfExists(newFile);
			newFile = Files.createFile(newFile);
		} catch (IOException ex) {
			System.out.println("Error creating file");
		}

		try (BufferedWriter writer = Files.newBufferedWriter(newFile, StandardCharsets.UTF_8)) {
			writer.append(DXF);
			writer.flush();

		} catch (IOException exception) {
			System.out.println("Error writing to file");
		}
	}
	
	public void read_file(String Abs_path) {
		//read DXF file as very large string
		dxf_large_string = DXF_Utils.readFile(Abs_path, StandardCharsets.UTF_8);
		
		if (dxf_large_string != null) {
			parse_dxf tt = new parse_dxf(this);
		}
		else{
			System.out.println("Error reading file:");
			System.out.println("file " + Abs_path + " is not exists");
		}
	}

	public static void next_handle() {
		int_handle += 1;
		hex_handle = Integer.toHexString(int_handle).toUpperCase();
		if (hex_handle.equals("BD") || hex_handle.equals("105")) {
			int_handle += 1;
			hex_handle = Integer.toHexString(int_handle).toUpperCase();
		}
	}

	public static void main(String[] args) {
		String path = "/home/vlad/cad111.dxf";
		//DXF_file f = new DXF_file(Mode.Open_file, path);
		//f.SECTION_TABLES.print_styles();
		
		DXF_file f = new DXF_file(Mode.New_file, path);
		Color_dxf c = new Color_dxf(255, 0, 0);
		f.put_text(100, 50, 450, 0, 0.5, c, "Samocad - v0.0.9.0");
		f.put_arc(250, 300, 50, 120, 360, c, 4);
		f.put_circle(400, 300, 50, c, 4);
		f.put_line(0, 320, 100, 50, dash_type.Continuous, 20, c, 1);
		
		f.put_dimension(300, 300, 1950, 300, 300, 800, 
				200, //ext dim lines
				100, //ext ticks
				350, //text_size
				0.67, //text width factor
				50, //s
				2, //text change
				2633.140,  //text_x
				1025, //text_y 
				100, //arrow size
				0, "Open 30", "", c);
				
		f.put_dimension(300, 300, 300, 1950, 1500, 300, 
				200, //ext dim lines
				100, //ext ticks
				350, //text_size
				0.67, //text width factor
				50, //s
				2, //text change
				1600,  //text_x
				2000, //text_y 
				100, //arrow size
				90, //angle
				"Arch", //arrow type
				"tret", //text
				c //color
				);
				
		f.save_file();
		
	}
}
