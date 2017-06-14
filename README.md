# open_dxf_lib
Open library for read and write .dxf files (autocad-compatible). 

It is fine and easy like this:


<code>	
	...
	DXF_file f = new DXF_file(Mode.Open_file, "/home/mydxf.dxf); 
	Color_dxf c = new Color_dxf(255, 255, 255);
	f.put_text(100, 50, 450, 0, 0.5, c, "text in dxf file");
	f.put_arc(250, 300, 50, 120, 360, c, 4);
	f.put_circle(400, 300, 50, c, 4);
	f.put_line(0, 320, 100, 50, core.dash_type.Continuous, 20, c, 1);
	f.put_dimension(
				300, 300, 1950, 300, 300, 800, //coordinates of 3 points
				200, //ext dim lines
				100, //ext ticks
				350, //text_size
				0.67, //text width factor
				50, //s
				2, //text change
				2633.140,  //text_x
				1025, //text_y 
				100, //arrow size
				0, //angle
				"Open 30", //type arrow
				"", //text
				c //color
				);
	f.save_file();
	...</code>