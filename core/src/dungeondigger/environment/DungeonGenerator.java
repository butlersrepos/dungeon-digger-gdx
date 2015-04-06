package dungeondigger.environment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.badlogic.gdx.math.Vector2;
import com.google.gson.JsonObject;

public class DungeonGenerator {
	public static int					ROOM_COUNTER	= 0;
	public static HashMap<String, Room>	ROOMS			= new HashMap<String, Room>();

	public static JsonObject generateDungeon() {
		DungeonGenerator dg = new DungeonGenerator();
		Random r = new Random( DungeonParameters.seed );
		// Create blank dungeon
		Plot[][] dungeon = new Plot[DungeonParameters.height][DungeonParameters.width];

		initializeDungeon( dg, dungeon );
		generateRooms( dg, r, dungeon );
		generateHallways( dungeon, r );

		logDungeon( dungeon );
		// transcribe dungeon to JSON
		JsonObject result = new JsonObject();
		return result;
	}

	private static void logDungeon( Plot[][] dungeon ) {
		StringBuffer line = new StringBuffer();
		for( int row = 0; row < DungeonParameters.height; row++ ) {
			line.setLength( 0 );
			for( int col = 0; col < DungeonParameters.width; col++ ) {
				line.append( dungeon[row][col].type == "filler" ? "X" : " " );
			}
			System.out.println( line );
		}
	}

	private static void initializeDungeon( DungeonGenerator dg, Plot[][] dungeon ) {
		for( int row = 0; row < DungeonParameters.height; row++ ) {
			for( int col = 0; col < DungeonParameters.width; col++ ) {
				dungeon[row][col] = dg.new Plot();
			}
		}
	}

	private static void generateRooms( DungeonGenerator dg, Random r, Plot[][] dungeon ) {
		// Room placement
		while( checkDensity( dungeon ) < DungeonParameters.density ) {
			int roomSize = 3 + r.nextInt( 5 );
			int randomRow = r.nextInt( DungeonParameters.height - roomSize );
			int randomCol = r.nextInt( DungeonParameters.width - roomSize );
			// place room
			for( int i = 0; i < 50; i++ ) {
				if( placeRoom( dg, dungeon, roomSize, randomRow, randomCol ) ) {
					break;
				}
			}
		}
	}

	public static void generateHallways( Plot[][] dungeon, Random r ) {
		for( Double chance : DungeonParameters.hallwayDensities ) {
			generateRandomRoomWalk( dungeon, chance, r );
		}
	}

	public static void generateRandomRoomWalk( Plot[][] dungeon, Double chance, Random r ) {
		int diffX, diffY, startX, startY, destX, destY, i = 0;
		// Starting vertically (0) or horizontally (1)
		int startDirection = 0;
		int hallwayCounter = 0;

		// Iterate over every room
		Room[] rooms = new Room[ROOMS.keySet().size()];
		int it = 0;
		for( Map.Entry<String, Room> m : ROOMS.entrySet() ) {
			rooms[it++] = m.getValue();
		}
		for( Room startRoom : rooms ) {
			System.out.println( "starting hallway for room: " + startRoom.name );
			if( r.nextDouble() >= chance ) {
				System.out.println( "rolling chance" );
				continue;
			}
			// Grab a random room that's not this one
			do {
				System.out.println( "grabbing random target room" );
				i = r.nextInt( rooms.length );
			} while( rooms[i] == startRoom );

			System.out.println( "setting up variables" );
			// Find the distance vectors and directionality
			Room destRoom = rooms[i];
			// Add some variance to where on the border of the room we start from and goto
			startX = startRoom.column + r.nextInt( startRoom.width );
			startY = startRoom.row + r.nextInt( startRoom.height );
			destX = destRoom.column + r.nextInt( destRoom.width );
			destY = destRoom.row + r.nextInt( destRoom.height );
			diffX = destX - startX;
			diffY = destY - startY;
			// Pick a random axis to begin from 0-vertical, 1-horizontal
			startDirection = r.nextInt( 2 );

			// Loop to draw the hallway using the Marked flag of subsequent squares as it "digs"
			// That way we can roll it back if we need to, otherwise process them
			int currentRow, currentColumn;
			String currentHallway = "hallway" + ( ++hallwayCounter );

			// Loop label for the digging of the current hallway
			digHallway: for( int n = 1; n < Math.abs( diffX ) + Math.abs( diffY ); n++ ) {
				System.out.println( "digging" );
				// This IF block makes our "cursor" follow a 90degree path from start to dest
				if( startDirection == 0 ) {
					// Going vertically first
					currentRow = ( int ) ( startY + Math.min( Math.abs( diffY ), n ) * Math.signum( ( double ) diffY ) );
					currentColumn = ( int ) ( startX + Math.max( n - Math.abs( diffY ), 0 ) * Math.signum( ( double ) diffX ) );
				} else {
					// Going horizontally first
					currentColumn = ( int ) ( startX + Math.min( Math.abs( diffX ), n ) * Math.signum( ( double ) diffX ) );
					currentRow = ( int ) ( startY + Math.max( n - Math.abs( diffX ), 0 ) * Math.signum( ( double ) diffY ) );
				}
				Plot currentSquare = dungeon[currentRow][currentColumn];
				// If we're still in the startRoom, move on
				if( currentSquare.owner.equalsIgnoreCase( startRoom.name ) ) {
					continue;
				} else {
					// Check our orthogonal neighbors
					for( int y = -1; y < 2; y++ ) {
						for( int x = -1; x < 2; x++ ) {
							// Guarantees orthogonal checks only, no diagonals
							if( y != 0 && x != 0 ) {
								continue;
							}
							// Keeps us in bounds
							if( currentRow + x < 0 || currentRow + x >= DungeonParameters.height || currentColumn + y < 0 || currentColumn + y >= DungeonParameters.width ) {
								continue;
							}
							Plot inspectee = dungeon[currentRow + y][currentColumn + x];
							// Checks owners to determine our actions, stop if its not startRoom or our hallway
							if( ( inspectee.type.equals( "path" ) || inspectee.type.equals( "open" ) ) && inspectee.owner != startRoom.name && inspectee.owner != currentHallway ) {
								// If we're still next to the start room, only claim if the triggering square is opposite from the startRoom
								// otherwise the triggering hall is probably connecting to the startRoom
								if( checkBordersOwners( dungeon, currentRow, currentColumn, BorderCheck.ORTHOGONAL, startRoom ).size() > 0 ) {
									if( dungeon[currentRow + y * -1][currentColumn + x * -1].owner == startRoom.name ) {
										currentSquare.type = "path";
										currentSquare.owner = currentHallway;
									}
								} else {
									currentSquare.type = "path";
									currentSquare.owner = currentHallway;
								}
								break digHallway;
							}
						}
					}
					currentSquare.type = "path";
					currentSquare.owner = currentHallway;
				}
			}
		}
	}

	private static ArrayList<Vector2> checkBordersOwners( Plot[][] dungeon, int row, int col, BorderCheck method, Room owner ) {
		ArrayList<Vector2> result = new ArrayList<Vector2>();
		for( int y = -1; y < 2; y++ ) {
			for( int x = -1; x < 2; x++ ) {
				if( method == BorderCheck.ORTHOGONAL && x != 0 && y != 0 ) {
					continue;
				}
				if( method == BorderCheck.DIAGONAL && x == 0 && y == 0 ) {
					continue;
				}
				if( dungeon[row + x][col + y].owner.equals( owner.name ) ) {
					result.add( new Vector2( row + x, col + y ) );
				}
			}
		}
		return result;
	}

	/** Attempts to place a room of {roomSize} width by height at the location row, column - being it's topleft corner. */
	public static boolean placeRoom( DungeonGenerator dg, Plot[][] dungeon, int roomSize, int row, int column ) {
		// Make sure the area is clear for the room, including a buffer of 1 extra around the edges
		// to prevent hallways running next to rooms, effectively enlarging them.
		for( int i = -1; i <= roomSize; i++ ) {
			for( int n = -1; n <= roomSize; n++ ) {
				if( !isCellFiller( dungeon, row + n, column + i ) ) { return false; }
			}
		}
		String roomName = "room" + ( ++ROOM_COUNTER );
		ROOMS.put( roomName, dg.new Room( roomName, roomSize, roomSize, row, column ) );
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
	private static boolean isCellFiller( Plot[][] dungeon, int row, int col ) {
		if( row < 0 || row >= DungeonParameters.height || col < 0 || col >= DungeonParameters.width ) { return false; }
		return dungeon[row][col].type == "filler";
	}

	private static Double checkDensity( Plot[][] dungeon ) {
		Double builtPlots = 0d;
		for( int row = 0; row < DungeonParameters.height; row++ ) {
			for( int col = 0; col < DungeonParameters.width; col++ ) {
				builtPlots += dungeon[row][col].type.equalsIgnoreCase( "filler" ) ? 0 : 1;
			}
		}
		return builtPlots / ( DungeonParameters.height * DungeonParameters.width );
	}

	// Ease of use internal class
	private class Plot {
		public String	type	= "filler";
		public String	owner	= "none";
	}

	private class Room {
		public String	name;
		public int		width	= 0;
		public int		height	= 0;
		public int		row		= 0;
		public int		column	= 0;

		public Room( String n, int w, int h, int row, int col ) {
			name = n;
			width = w;
			height = h;
			this.row = row;
			column = col;
		}
	}

	public static class DungeonParameters {
		public static long		seed				= ( long ) ( Math.random() * 1000000000000000l );
		/** The vertical number of game units (squares, tiles, etc) */
		public static Integer	height				= 100;
		/** The horizontal number of game units (squares, tiles, etc) */
		public static Integer	width				= 100;
		/** The density of non-filler (useable) area to filler (unuseable) area across the entire map */
		public static Double	density				= 0.2d;
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

	private enum BorderCheck {
		ORTHOGONAL, DIAGONAL, ALL
	}
}