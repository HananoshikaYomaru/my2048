package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Arrays;

import javax.swing.JPanel;



public class Board extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8079866739055506044L;

	public Tile[] tiles;
	
	final GUI host ; 
	public static final int[] _0123 = {0,1,2,3} ; 

	public Board(GUI gui) {
		// TODO Auto-generated constructor stub
		host = gui ; 
		setFocusable(true)	;
		initTiles() ; 
	}

	private void initTiles() {
		tiles = new Tile[ 4 * 4 ] ; 
		for(int i = 0; i < tiles.length ; ++i ) 
			tiles[i] = Tile.ZERO ; 
		host.statusBar.setText("");
	}

	
	private static final Color BG_COLOR = new Color(0xbbada0) ;
	private static final Font STR_FONT = new Font(Font.SANS_SERIF, Font.BOLD ,17) ; 

	@Override
	public void paint(Graphics g ) {
		super.paint(g);
		g.setColor(BG_COLOR);
		g.setFont(STR_FONT);
		g.fillRect(0, 0, this.getSize().width, this.getSize().height);
		for(int i :_0123) {
			for(int j : _0123)
				drawTile(g,tiles[i * 4 + j] , i, j ) ; 
		}
	}
	
	/* Side of the tile square */
    private static final int SIDE = 64;

    /* Margin between tiles */
    private static final int MARGIN = 16;

    /**
     * Draw a tile use specific number and color in (x, y) coords, x and y need
     * offset a bit.
     */
    private void drawTile(Graphics g, Tile tile, int x, int y) {
        if(tile == null){
            System.out.println(x+"   "+y);
        }
        Value val = tile.value();
        int xOffset = offsetCoors(x);
        int yOffset = offsetCoors(y);
        g.setColor(val.color());
        g.fillRect(xOffset, yOffset, SIDE, SIDE);
        g.setColor(val.fontColor());
        if (val.score() != 0)
            g.drawString(tile.toString(), xOffset
                    + (SIDE >> 1) - MARGIN, yOffset + (SIDE >> 1));
    }

    private static int offsetCoors(int arg) {
        return arg * (MARGIN + SIDE) + MARGIN;
    }

}
