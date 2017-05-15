package put_in_dxf.DXF_dimension;
/**
 * Dimension text change (where is the text on dimension):
 * 1 - text position unchanged, auto placing text;
 * 2 - text position changed, text on dim line not on middle dim line3;
 * 3 - text position changed, text is free;
 * See figs/Dim text change.png
 */

public class dim_change {
	
	public int DXF_dim_text_change;
	public int DXF_dim_text_change2;
	
	public dim_change(int text_change){
		if(text_change == 1){
			DXF_dim_text_change = 0;
			DXF_dim_text_change2 = 32;
		}
		else if(text_change == 2){
			DXF_dim_text_change = 0;
			DXF_dim_text_change2 = 160;
		}
		else if(text_change == 3){
			DXF_dim_text_change = 2;
			DXF_dim_text_change2 = 160;
		}
	}
}