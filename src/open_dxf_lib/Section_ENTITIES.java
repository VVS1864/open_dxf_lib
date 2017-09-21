package open_dxf_lib;


public class Section_ENTITIES extends Section {
	
	public String MY_ENTITIES = "\n";
	
	public Section_ENTITIES() {
		section_name = "SECTION_ENTITIES.txt";
		body = super.init();
		
	}
	
	@Override
	public String to_string() {
		values.put("MY_ENTITIES", MY_ENTITIES);
		return DXF_Utils.replace_values(values, body);
	}

}
