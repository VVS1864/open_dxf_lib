

public class Section_TABLES extends Section {
	
	String MY_BLOCK_RECORDS = "";
	String MY_BLKREFS = "";
	String MY_ACAD_REACTORS = "";
	
	public Section_TABLES() {
		section_name = "SECTION_TABLES.txt";
		super.init();		
	}
	@Override
	String to_string() {
		values.put("MY_BLOCK_RECORDS\n", MY_BLOCK_RECORDS);
		values.put("MY_BLKREFS\n", MY_BLKREFS);
		values.put("MY_ACAD_REACTORS", MY_ACAD_REACTORS);
		
		return DXF_Utils.replace_values(values, body);
	}

}
