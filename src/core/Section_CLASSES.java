package core;


public class Section_CLASSES extends Section {
	
	public Section_CLASSES() {
		section_name = "SECTION_CLASSES.txt";
		body = super.init();
	}
	
	@Override
	public String to_string() {
		
		return body;
	}

}
