package view.GUI;

import static view.GUI.Value.*;

/* all AIGUI code is taken from https://github.com/Alwayswithme/2048.java/blob/master/src/phx/Board.java
 * 10/4 2020
 * */

import java.util.HashMap; 

public class Tile {
	private final Value val ; 
	private final static HashMap<Integer, Tile> cache = new HashMap<>();
	
	/**
	 * frequently used tiles, reuse when possible 
	 */
	public final static Tile ZERO = new Tile(_0); 
	public final static Tile TWO = new Tile(_2) ; 
	public final static Tile FOUR = new Tile(_4);
	public final static Tile EIGHT = new Tile(_8); 
	
	static {
		for(Value v : Value.values()) {
			switch(v) {
			case _0:
				cache.put(v.score(), ZERO) ; 
				break ; 
			case _2 :
				cache.put(v.score(), TWO ); 
				break ; 
			case _4 : 
				cache.put(v.score(),FOUR); 
				break ; 
			case _8 : 
				cache.put(v.score(),EIGHT ) ; 
				break ;
			default : 
				cache.put(v.score(), new Tile(v)) ; 
				break ; 
			}
		}
	}
		
	public Tile(Value v) {
		val = v ;
	}
	
	public static Tile valueOf(int num) {
		return cache.get(num ); 
	}
	
	Value value() {
		return val ; 
	}
	
	/**
     * Use for merge, double the score
     * @return a new Tile which's val multiply 2
     */
	public Tile getDouble() {
		return valueOf(val.score() << 1 ) ; 
		//faster than *2 
	}
	
	/**
	 * test the tile is empty or not. empty mean it's val is 0 
	 */
	boolean empty() {
		return val == _0 ; 
	}
	
	
	/**
	 * performance comparison 
	 * https://stackoverflow.com/questions/513600/should-i-use-javas-string-format-if-performance-is-important
	 */
	@Override 
	public String toString() {
		//return String.format("%1$4d", val) ; 
		return "" + val.score() ; 
	}
	
	@Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((val == null) ? 0 : val.hashCode());
        return result;
    }
	
	@Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof Tile))
            return false;
        Tile other = (Tile) obj;
        if (val != other.val)
            return false;
        return true;
    }
	
	/**
     * Generate a Tile which's val is 2 or 4, bigger chances return 2
     */
    static Tile randomTile() {
        return Math.random() < 0.15 ? FOUR : TWO;
    }
	
}
