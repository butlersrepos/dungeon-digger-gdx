package dungeondigger.tools;

public class DDMathUtils {
	public static float maxWithinBound( float a, float b, float bound ) {
		return Math.min( Math.max( a, b ), bound );
	}

	public static float minWithinBound( float a, float b, float bound ) {
		return Math.max( Math.min( a, b ), bound );
	}
}
