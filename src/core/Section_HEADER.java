package core;



public class Section_HEADER extends Section {
	
	public String MY_HANDSEED;
	
	public Section_HEADER(){
		section_name = "SECTION_HEADER.txt";
		body = super.init();
		
		MY_HANDSEED = Integer.toHexString(110000).toUpperCase();
		
	}
	
	@Override
	public String to_string() {
		values.put("MY_HANDSEED", MY_HANDSEED);
		return DXF_Utils.replace_values(values, body);
	}
	
	

}
