package dungeondigger.tools;

import java.util.HashMap;

import com.badlogic.gdx.graphics.Texture;

public class References {
	public static HashMap<String, Texture>	IMAGES					= new HashMap<String, Texture>();
	public static HashMap<Integer, String>	KEY_BINDINGS			= new HashMap<Integer, String>();
	public static HashMap<String, String>	SLOT_BINDINGS			= new HashMap<String, String>();

	public static final String				GAME_TITLE				= "Vessels of Strife";
	public static final String				GAME_SUBTITLE			= "Wandering without Gods";
	public static final String				BUILD_NUMBER			= "Pre Alpha Build 0.0.1";
	public static final String				START_BUTTON_TEXT		= "Begin Journey";
	public static final String				FOREGROUND_LAYER_NAME	= "foreground";
	public static final String				BACKGROUND_LAYER_NAME	= "ground";
	public static final int					LPC_CHAR_FRAME_WIDTH	= 64;
	public static final int					LPC_CHAR_FRAME_HEIGHT	= 64;
	public static final int					LPC_WALK_LEFT_ROW		= 11;
	public static final int					LPC_NUM_WALK_FRAMES		= 9;

}
