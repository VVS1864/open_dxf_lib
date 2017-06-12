package open_file;

import core.DXF_file;
import core.Mode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import core.DXF_Utils;
import core.Section;
import core.tables.DXF_style;

public class parse_dxf {
	DXF_file f;
	public static String br = "\r?\n[ ]*";
	public static String value_regex = "(.*)";
	//public String comment = "";

	public parse_dxf(DXF_file f) {
		this.f = f;
		//boolean break_err = false;
		//Break if there is error in DXF
		 
		while (true) {
			// HEADER
			f.SECTION_HEADER.body = parse_section("HEADER");
			//CLASSES
			f.SECTION_CLASSES.body = parse_section("CLASSES");
			//TABLES
			f.SECTION_TABLES.body = parse_section("TABLES");
			if (f.SECTION_TABLES.body == null){
				print_err("Section TABLES not found");
				break;
			}
			else{
				System.out.println("Read TABLES");
			}
			//BLOCKS
			f.SECTION_BLOCKS.body = parse_section("BLOCKS");
			if (f.SECTION_BLOCKS.body == null){
				print_err("Section BLOCKS not found");
				break;
			}
			else{
				System.out.println("Read BLOCKS");
			}
			//ENTITIES
			f.SECTION_ENTITIES.body = parse_section("ENTITIES");
			if (f.SECTION_ENTITIES.body == null){
				print_err("Section ENTITIES not found");
				break;
			}
			else{
				System.out.println("Read ENTITIES");
			}
			//OBJECTS
			f.SECTION_OBJECTS.body = parse_section("OBJECTS");
			//styles
			DXF_styles();
			break;
		}
	}

	public void DXF_styles(){
		String DXF_TABLE_styles = find_in_text(f.SECTION_TABLES.body, 
				"0%br%TABLE%br%2%br%STYLE([\\w\\W]*?%br%ENDTAB)", "table styles not found");
		Matcher m = parse_text(DXF_TABLE_styles,
				"(?<=0\r?\n[ ]?)STYLE%br%([\\w\\W]*?)%br%0%br%(?=STYLE|ENDTAB)");
		//ArrayList<String> styles = new ArrayList<String>();
		int i = 0;
		while (m.find() == true) {
			i++;
			String s = m.group(1);
			
			String[] parts = s.split(br);
			String name = property_finder(parts, "2");
			String handle = property_finder(parts, "5");
			String text_size = property_finder(parts, "42");
			String width_factor = property_finder(parts, "41");
			f.SECTION_TABLES.DXF_styles.put(name,
					new DXF_style(name, handle, Double.parseDouble(text_size), Double.parseDouble(width_factor)));

		}
		System.out.println("Read " + f.SECTION_TABLES.DXF_styles.size() + " Styles");
	}

	public void print_err(String comment){
		System.out.println("DXF fatal error:");
		System.out.println(comment);
		System.out.println("Invalid or incomplete DXF input -- drawing discarded.");
	}
	
	public String find_in_text(String text, String regex, String comment){
		String excavated = null;
		Matcher matcher = parse_text(text, regex);
		if (matcher.find()){
			excavated = matcher.group();
		}
		else{
			System.out.println("DXF error:");
			System.out.println(comment);
		}
		return excavated;
	}
	
	/**
	 * General method for find values it any text with regular exp.
	 * @param text
	 * @param regex
	 * @return matcher 
	 */
	public Matcher parse_text(String text, String regex){
		HashMap<String, String> values = new HashMap<String, String>();
		values.put("br", br);
		regex = DXF_Utils.replace_values(values, regex);
		return parse_string(text, regex);
	}
	public Matcher parse_string(String text, String regex){
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(text);
		return matcher;
	}
	
	public String parse_section(String section_name){
		String section_body = null; 
		//replace "section_name" to real name of section in regular expression
		String regex = "0%br%SECTION%br%2%br%%section_name%([\\w\\W]*?0%br%ENDSEC)";
		HashMap<String, String> values = new HashMap<String, String>();
		values.put("section_name", section_name);
		regex = DXF_Utils.replace_values(values, regex);
		Matcher matcher = parse_text(f.dxf_large_string, regex);
		
		if (matcher.find()) {
			section_body = matcher.group();
		}
		
		return section_body;
	}
	//Method for find property in split strings
	public String property_finder(String[] strings, String property_regex){
		String value = null;
		property_regex = "\\b" + property_regex + "\\b";
		for (int i = 0; i < strings.length; i+=2){
			Matcher m_property = parse_string(strings[i], property_regex);
			if(m_property.find()){
				Matcher m_value = parse_string(strings[i+1], value_regex);
				if(m_value.find()){
					value = m_value.group();
				}
				break;
			}
		}
		
		return value;
	}
}