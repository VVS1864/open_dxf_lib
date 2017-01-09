

public class Section_OBJECTS extends Section {
	
	String MY_LAST_HANDLE = "";
	
	public Section_OBJECTS() {
		section_name = "SECTION_OBJECTS.txt";
		super.init();
		
	}
	
	@Override
	String to_string() {
		values.put("MY_LAST_HANDLE", MY_LAST_HANDLE);
		return DXF_Utils.replace_values(values, body);
	}

}
