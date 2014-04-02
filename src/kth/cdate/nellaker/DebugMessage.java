package kth.cdate.nellaker;

public class DebugMessage {
	private static boolean DEBUG_ON= false;
	public static void msg(String msg)
	{
		if(DEBUG_ON)
			System.err.println("DEBUG ("+System.currentTimeMillis()+"): "+msg);
	}

}
