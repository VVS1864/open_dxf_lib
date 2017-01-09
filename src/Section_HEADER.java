


public class Section_HEADER extends Section {
	
	String MY_HANDSEED;
	
	public Section_HEADER(){
		section_name = "SECTION_HEADER.txt";
		super.init();
		
		MY_HANDSEED = Integer.toHexString(110000);
		
	}
	
	@Override
	public String to_string() {
		values.put("MY_HANDSEED", MY_HANDSEED);
		return DXF_Utils.replace_values(values, body);
	}
	
	

}
