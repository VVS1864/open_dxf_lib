package core;


import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import put_in_dxf.DXF_line;
import put_in_dxf.DXF_arc;
import put_in_dxf.DXF_circle;
import put_in_dxf.DXF_text;
import core.dash_type;

public class DXF_file {

	Section_HEADER  SECTION_HEADER = new Section_HEADER();
	Section_CLASSES  SECTION_CLASSES  = new Section_CLASSES();
	Section_TABLES SECTION_TABLES = new Section_TABLES();
	Section_BLOCKS SECTION_BLOCKS = new Section_BLOCKS();
	Section_ENTITIES SECTION_ENTITIES = new Section_ENTITIES();
	Section_OBJECTS SECTION_OBJECTS = new Section_OBJECTS();
		
	public static HashMap<Integer, Color_rgb> dxf_rgb_color_map = new HashMap<>();
	
	public String file;
	public Mode mode;
	
	public static String hex_handle = "BB";
	public static int int_handle = Integer.parseInt(hex_handle, 16);

	public DXF_file(Mode key, String file) {
		load_dxf_colors();
		this.file = file;
		this.mode = key;
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
	
	public void put_text(double x1, double y1, double text_size, double angle, double text_ss, Color_rgb color, String text) {

		String entity = new DXF_text(x1, y1, text_size, angle, text_ss, color, text).dxf_entity;
		put_base(entity);
	}
	
	void save_file() {
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

		try (BufferedWriter writer = Files.newBufferedWriter(
				newFile, StandardCharsets.UTF_8)) {
			writer.append(DXF);
			writer.flush();

		} catch (IOException exception) {
			System.out.println("Error writing to file");
		}
	}
	
	public static void next_handle(){
		int_handle += 1;
		hex_handle = Integer.toHexString(int_handle).toUpperCase();		
		if (hex_handle.equals("BD") || hex_handle.equals("105")){
			int_handle += 1;
			hex_handle = Integer.toHexString(int_handle).toUpperCase();
		}
	}
	
	/**
	 * Method for load data of colors dxf-rgb from file to HashMap
	 */
	void load_dxf_colors(){
		String textLocation = "src/color_acad_rgb.txt";
		//URL path = Object.class.getResource(textLocation);
		//String path2 = path.getPath();
		File file = new File(textLocation);
		String path2 = file.getAbsolutePath();
		Path path3 = Paths.get(path2);
		
		List<String> stringList;
		try {
			stringList = Files.readAllLines(path3, StandardCharsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		String regex = "(\\d*):\\((\\d*),(\\d*),(\\d*)\\),";
		Pattern pattern = Pattern.compile(regex);
		for (String s: stringList){
			Matcher matcher = pattern.matcher(s);
			if (matcher.matches()) {	
				Color_rgb rgb = new Color_rgb(
						Integer.parseInt(matcher.group(2)), 
						Integer.parseInt(matcher.group(3)), 
						Integer.parseInt(matcher.group(4))
						);
				dxf_rgb_color_map.put(Integer.parseInt(matcher.group(1)), rgb);
			}
		}	
	}
	
	public static void main(String[] args){
		String path = "/home/vlad/cad111.dxf";
		DXF_file f = new DXF_file(Mode.New_file, path);
		Color_dxf c = new Color_dxf(76,0,0);
		f.put_text(100, 50, 450, 0, 0.5, c, "Samocad - v0.0.9.0");
		f.put_arc(250, 300, 50, 120, 360, c, 4);
		//f.put_circle(250, 300, 50, c, 4);
		f.put_line(0, 320, 100, 50, core.dash_type.Continuous, 20, c, 1);
		f.save_file();
	}
}
