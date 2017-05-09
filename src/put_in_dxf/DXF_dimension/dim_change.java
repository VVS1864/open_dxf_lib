package put_in_dxf.DXF_dimension;
/**
 * Dimension text change (where is the text on dimension):
 * 1 - unchanged, auto
 * 2 - changed, place on dimension line out of dimension
 * 3 - changed, place on dimension line between lines
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