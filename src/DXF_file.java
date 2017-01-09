

import java.io.BufferedWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DXF_file {

	Section SECTION_HEADER = new Section_HEADER();
	Section SECTION_CLASSES = new Section_CLASSES();
	Section SECTION_TABLES = new Section_TABLES();
	Section SECTION_BLOCKS = new Section_BLOCKS();
	Section SECTION_ENTITIES = new Section_ENTITIES();
	Section SECTION_OBJECTS = new Section_OBJECTS();
	public final HashMap<Integer, Color_rgb> dxf_rgb_color_map = new HashMap<>();
	
	public String file;
	public Mode mode;

	public DXF_file(Mode key, String file) {
		load_color_dxf();
		this.file = file;
		this.mode = key;
	}

	void load_color_dxf(){
		String textLocation = "/color_acad_rgb.txt";
		URL path = Object.class.getResource(textLocation);
		String path2 = path.getPath();
		Path path3 = Paths.get(path2);
		//String dxf_colors = DXF_Utils.readFile(path2, StandardCharsets.UTF_8);
		
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
	void save_file() {
		String DXF = SECTION_HEADER.to_string();
		DXF += SECTION_CLASSES.to_string();
		DXF += SECTION_TABLES.to_string();
		DXF += SECTION_BLOCKS.to_string();
		DXF += SECTION_ENTITIES.to_string();
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
	
	public static void main(String[] args){
		String path = "/home/vlad/cad111.dxf";
		DXF_file f = new DXF_file(Mode.New_file, path);
		System.out.println(f.dxf_rgb_color_map.get(160).get_rgb_string());
		//f.save_file();
	}
}
