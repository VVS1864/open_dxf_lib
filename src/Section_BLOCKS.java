

public class Section_BLOCKS extends Section {
	
	String MY_BLOCKS = "";
	
	public Section_BLOCKS() {
		section_name = "SECTION_BLOCKS.txt";
		super.init();
		
	}
	
	@Override
	String to_string() {
		values.put("MY_BLOCK_RECORDS\n", MY_BLOCKS);
		return DXF_Utils.replace_values(values, body);
	}

}
