package kth.cdate.nellaker;

public class DebugMessage {
	
	public static void msg(String msg, boolean debugging_on)
	{
		if(debugging_on)
			System.err.println("DEBUG ("+System.currentTimeMillis()+"): "+msg);
	}

}
