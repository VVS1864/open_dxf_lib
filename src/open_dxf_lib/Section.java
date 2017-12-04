package open_dxf_lib;


import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

import open_dxf_lib.DXF_Utils;

public abstract class Section {
	public String section_name;
	public String body;
	public HashMap<String, String> values = new HashMap<String, String>();
	
	/**
	 * Method for read text of section in folder "src/empty_sections/"
	 * @param text_section_name
	 * @return text of section as String
	 */
	public String init(String text_section_name) {
		String textLocation = "/empty_sections/" + text_section_name;
		BufferedReader br = DXF_Utils.read_section(textLocation);
		StringBuilder builder = new StringBuilder();
		try {
			String s = br.readLine();
			while(true){
				builder.append(s);
				s = br.readLine();
				if(s == null){break;}
				else{
					builder.append("\n");
				}
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String section_text = builder.toString();
		
		// String path = DXF_Utils.get_absolute_path(textLocation).toString();
		// String section_text = DXF_Utils.readFile(path,
		// StandardCharsets.UTF_8);

		return section_text;
	}
	public String init(){
		return init(this.section_name);
	}
	
	public abstract String to_string();
}
