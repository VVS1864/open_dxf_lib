package core.tables;

import java.util.ArrayList;
import java.util.HashMap;

import core.DXF_Utils;
import core.Section;

public class Section_TABLES extends Section {
	
	public String MY_BLOCK_RECORDS = "";
	public String MY_BLKREFS = "";
	public String MY_ACAD_REACTORS = "";
	
	public HashMap<String, DXF_style> DXF_styles = new HashMap<>();
	
	public Section_TABLES() {
		section_name = "SECTION_TABLES.txt";
		body = super.init();		
	}
	
	public void print_styles(){
		System.out.println("There are " + DXF_styles.size() + " styles in DXF file:");
		for (DXF_style s: DXF_styles.values()){
			System.out.println("Name: " + s.name);
			System.out.println("Handle: " + s.handle);
			System.out.println("Text size: " + s.text_size);
			System.out.println("width factor: " + s.width_factor);
			System.out.println("---------------");
		}
	}
	@Override
	public String to_string() {
		values.put("MY_BLOCK_RECORDS", MY_BLOCK_RECORDS);
		values.put("MY_BLKREFS", MY_BLKREFS);
		values.put("MY_ACAD_REACTORS", MY_ACAD_REACTORS);
		body = DXF_Utils.replace_values(values, body);
		return DXF_Utils.replace_values(values, body);
	}

}
