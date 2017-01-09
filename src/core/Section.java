package core;


import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

public abstract class Section {
	public String section_name;
	public String body;
	public HashMap<String, String> values = new HashMap<String, String>();
	
	public String init(String text_section_name){
		String textLocation = "/empty_sections/"+text_section_name;
		URL path = Section.class.getResource(textLocation);
		String path2 = path.getPath();
		String section_text = DXF_Utils.readFile(path2, StandardCharsets.UTF_8);
		
		return section_text; 
	}
	public String init(){
		return init(this.section_name);
	}
	
	public abstract String to_string();
}
