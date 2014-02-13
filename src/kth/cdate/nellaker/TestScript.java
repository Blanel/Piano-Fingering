package kth.cdate.nellaker;

import java.io.File;

public class TestScript {

	public static void main(String[] args)
	{
		//Song song = new Song(new File("test.mid")); 
		Song song = new Song(); // Create a default scale
		System.out.println("Song: "+song.toString());
		FingerTree ft = new FingerTree();
		long start = System.currentTimeMillis();
		ft.generateTree(song, 0);
		long delta = System.currentTimeMillis()-start;
		DebugMessage.msg("Time to run: "+delta);
		DebugMessage.msg("Final Score: "+ft.getBest().getCurrentScore());
		String out = ft.getBestSequence();
		System.out.println(out);
		
	}

}
