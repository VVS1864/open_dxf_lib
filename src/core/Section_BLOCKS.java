package core;


public class Section_BLOCKS extends Section {
	
	public String MY_BLOCKS = "";
	
	public Section_BLOCKS() {
		section_name = "SECTION_BLOCKS.txt";
		body = super.init();
		
	}
	
	@Override
	public String to_string() {
		values.put("MY_BLOCKS", MY_BLOCKS);
		return DXF_Utils.replace_values(values, body);
	}

}
