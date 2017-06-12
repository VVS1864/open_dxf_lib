package open_file;

import core.DXF_file;
import core.Mode;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import core.DXF_Utils;
import core.Section;

public class parse_dxf {
	DXF_file f;
	public static String br = "\r?\n[ ]*";
	//public String comment = "";

	public parse_dxf(DXF_file f) {
		this.f = f;
		//boolean break_err = false;
		while (true) {
			// HEADER
			f.SECTION_HEADER.body = parse_section("HEADER");
			f.SECTION_CLASSES.body = parse_section("CLASSES");
			f.SECTION_TABLES.body = parse_section("TABLES");
			if (f.SECTION_TABLES.body == null){
				print_err("Section TABLES not found");
				break;
			}
			f.SECTION_BLOCKS.body = parse_section("BLOCKS");
			if (f.SECTION_BLOCKS.body == null){
				print_err("Section BLOCKS not found");
				break;
			}
			f.SECTION_ENTITIES.body = parse_section("ENTITIES");
			if (f.SECTION_ENTITIES.body == null){
				print_err("Section ENTITIES not found");
				break;
			}
			f.SECTION_OBJECTS.body = parse_section("OBJECTS");
			
			break;
		}

	}

	
	public void print_err(String comment){
		System.out.println("DXF fatal error:");
		System.out.println(comment);
		System.out.println("Invalid or incomplete DXF input -- drawing discarded.");
	}
	
	public String parse_section(String section_name){
		String section_body = null; 
		//replace "section_name" to real name of section in regular expression
		String regex = "0%br%SECTION%br%2%br%%section_name%([\\w\\W]*?0%br%ENDSEC)";
		HashMap<String, String> values = new HashMap<String, String>();
		values.put("section_name", section_name);
		values.put("br", br);
		regex = DXF_Utils.replace_values(values, regex);
		
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(f.dxf_large_string);
		if (matcher.find()) {
			section_body = matcher.group();
		}
		
		return section_body;
	}
}