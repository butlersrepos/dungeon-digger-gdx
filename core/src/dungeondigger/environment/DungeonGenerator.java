package dungeondigger.environment;

import java.util.ArrayList;
import java.util.Random;

import com.google.gson.JsonObject;

public class DungeonGenerator {
	public static int				ROOM_COUNTER	= 0;
	public static ArrayList<String>	ROOMS			= new ArrayList<String>();

	public static JsonObject generateDungeon() {
		DungeonGenerator dg = new DungeonGenerator();
		Random r = new Random( DungeonParameters.seed );
		// Create blank dungeon
		plot[][] dungeon = new plot[DungeonParameters.height][DungeonParameters.width];
		for( int row = 0; row < DungeonParameters.height; row++ ) {
			for( int col = 0; col < DungeonParameters.width; col++ ) {
				dungeon[row][col] = dg.new plot();
			}
		}
		// Room placement
		while( checkDensity( dungeon ) < DungeonParameters.density ) {
			int roomSize = 3 + r.nextInt( 5 );
			int randomRow = r.nextInt( DungeonParameters.height - roomSize );
			int randomCol = r.nextInt( DungeonParameters.width - roomSize );
			// place room
			for( int i = 0; i < 50; i++ ) {
				if( placeRoom( dungeon, roomSize, randomRow, randomCol ) ) {
					break;
				}
			}
		}

		StringBuffer line = new StringBuffer();
		for( int row = 0; row < DungeonParameters.height; row++ ) {
			line.setLength( 0 );
			for( int col = 0; col < DungeonParameters.width; col++ ) {
				line.append( dungeon[row][col].type == "filler" ? "X" : " " );
			}
			System.out.println( line );
		}
		// transcribe dungeon to JSON
		JsonObject result = new JsonObject();
		return result;
	}

	/** Attempts to place a room of {roomSize} width by height at the location row, column - being it's topleft corner. */
	public static boolean placeRoom( plot[][] dungeon, int roomSize, int row, int column ) {
		// Make sure the area is clear for the room, including a buffer of 1 extra around the edges
		// to prevent hallways running next to rooms, effectively enlarging them.
		for( int i = -1; i <= roomSize; i++ ) {
			for( int n = -1; n <= roomSize; n++ ) {
				if( !isCellFiller( dungeon, row + n, column + i ) ) { return false; }
			}
		}
		String roomName = "room" + ROOM_COUNTER;
		ROOMS.add( roomName );
		// Actually place the room
		for( int i = 0; i < roomSize; i++ ) {
			for( int n = 0; n < roomSize; n++ ) {
				dungeon[row + n][column + i].type = "open";
				dungeon[row + n][column + i].owner = roomName;
			}
		}
		return true;
	}

	/** Check if the cell is closed (is an X)
	 * 
	 * @return */
	private static boolean isCellFiller( plot[][] dungeon, int row, int col ) {
		if( row < 0 || row >= DungeonParameters.height || col < 0 || col >= DungeonParameters.width ) { return false; }
		return dungeon[row][col].type == "filler";
	}

	private static Double checkDensity( plot[][] dungeon ) {
		Double builtPlots = 0d;
		for( int row = 0; row < DungeonParameters.height; row++ ) {
			for( int col = 0; col < DungeonParameters.width; col++ ) {
				builtPlots += dungeon[row][col].type.equalsIgnoreCase( "filler" ) ? 0 : 1;
			}
		}
		return builtPlots / ( DungeonParameters.height * DungeonParameters.width );
	}

	// Ease of use internal class
	private class plot {
		public String	type	= "filler";
		public String	owner	= "none";
	}

	public static class DungeonParameters {
		public static long		seed				= ( long ) ( Math.random() * 1000000000000000l );
		/** The vertical number of game units (squares, tiles, etc) */
		public static Integer	height				= 100;
		/** The horizontal number of game units (squares, tiles, etc) */
		public static Integer	width				= 100;
		/** The density of non-filler (useable) area to filler (unuseable) area across the entire map */
		public static Double	density				= 0.3d;
		/** A list of percentages, expressed as Doubles from 0 - 1.0, each Double you add
		 * signifies a pass of the hallway algorithm using your number to determine what
		 * percentage of Rooms in the dungeon it attempts to connect up via new hallways. <br>
		 * <br>
		 * For example; <br>
		 * <ul>
		 * <li>Setting a Double[0.50] means that the DungeonGenerator would connect roughly 50% of the rooms to other rooms via hallways, leaving lots of unconnected rooms.</li>
		 * <li>Setting a Double[1.0, 0.95] means that the DungeonGenerator would connect every room to at least one other room and THEN attempt to connect them again to another
		 * room with 95% success.</li>
		 * <li>Setting a Double[1.0, 1.0, 1.0] would connect every room to three other rooms, although sometimes they may get connected to the same room as a previous attempt.</lu>
		 * </ul> */
		public static Double[]	hallwayDensities	= new Double[] {
															1.0d, 0.75d
													};
	}
}