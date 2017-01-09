

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

abstract class Section {
	public String section_name;
	public String body;
	HashMap<String, String> values = new HashMap<String, String>();
	
	protected void init(){
		String textLocation = "/empty_sections/"+section_name;
		URL path = Section.class.getResource(textLocation);
		String path2 = path.getPath();
		this.body = DXF_Utils.readFile(path2, StandardCharsets.UTF_8);
		
	}
	
	abstract String to_string();
}
