package core;


public class Width {
	public int width;
	public int DXF_width;
	
	public Width(int w){
		System.out.println(w);
		this.width = w;
		switch(w){
		case 1:
			this.DXF_width = 20;
			break;
		case 2:
			this.DXF_width = 30;
			break;
		case 3:
			this.DXF_width = 80;
			break;
		case 4:
			this.DXF_width = 158;
			break;
		}
		System.out.println(DXF_width);
	}
	
	public String get_dxf_with_string(){
		return Integer.toString(DXF_width);
	}
}