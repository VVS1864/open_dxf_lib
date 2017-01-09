

public class Section_ENTITIES extends Section {
	
	String MY_ENTITIES = "";
	
	public Section_ENTITIES() {
		section_name = "SECTION_ENTITIES.txt";
		super.init();
		
	}
	
	@Override
	String to_string() {
		values.put("MY_ENTITIES", MY_ENTITIES);
		return DXF_Utils.replace_values(values, body);
	}

}
