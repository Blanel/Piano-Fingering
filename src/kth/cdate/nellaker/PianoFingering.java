package kth.cdate.nellaker;

import java.io.File;

public class PianoFingering {

	
	private static final String usage = "Usage: java PianoFingering <midi_file> <options>\n"
			+ "Valid options:\n"
			+ "-s N\n"
			+ "\t Run the program from starting index N. Can be from 0 up to song length\n"
			+ "-e N\n"
			+ "\t Run the program to ending index N. Can be from 0 up to song length"; 
	public static void main(String[] args)
	{
		System.out.println(usage);
		Song song = new Song(new File("test.mid")); 
		//Song song = new Song(); // Create a default scale
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
