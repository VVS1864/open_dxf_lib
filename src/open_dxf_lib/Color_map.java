package open_dxf_lib;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Color_map {
	public HashMap<Integer, Color_rgb> dxf_rgb_color_map = new HashMap<>();
	
	/**
	 * Method for load data of colors dxf-rgb from file to HashMap
	 */
	public Color_map() {
		String textLocation = "/color_acad_rgb.txt";
		//Path path = DXF_Utils.get_absolute_path(textLocation);
		BufferedReader br = DXF_Utils.read_section(textLocation);
		String regex = "(\\d*):\\((\\d*),(\\d*),(\\d*)\\),";
		Pattern pattern = Pattern.compile(regex);
		String s = "";
		
		try {
			while ((s = br.readLine()) != null) {
				Matcher matcher = pattern.matcher(s);
				if (matcher.matches()) {
					Color_rgb rgb = new Color_rgb(Integer.parseInt(matcher.group(2)), Integer.parseInt(matcher.group(3)),
							Integer.parseInt(matcher.group(4)));
					dxf_rgb_color_map.put(Integer.parseInt(matcher.group(1)), rgb);
					
				}
			
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
}
