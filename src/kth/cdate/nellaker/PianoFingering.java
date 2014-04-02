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
		/*File midiFile = null;
		int startIndex = 0;
		int endIndex = Integer.MAX_VALUE;
		try
		{
			if(args.length == 0)
				throw new Exception("No arguments Exception");
			else
			{
				midiFile = new File(args[0]);
				for(int i = 1; i<args.length ; i+=2)
				{
					if(i+1 == args.length)
						throw new Exception("Incorrect use of arguments Exception");
					switch(args[i]){
					case "-s":
						startIndex = Integer.parseInt(args[i+1]);
						break;
					case "-e":
						endIndex = Integer.parseInt(args[i+1]);
						break;
					default:
						
						break;
					
					}
				}
			}
		}
		catch(Exception e)
		{
			System.out.println(usage);
			return;
		}*/
		
		Song song = new Song(new File("Divenire.mid")); 
		//Song song = new Song(); // Create a default scale
		System.out.println("Song: "+song.toString());
		FingerTree ft = new FingerTree();
		long start = System.currentTimeMillis();
		ft.generateTree(song, 0);
		long delta = System.currentTimeMillis()-start;
		DebugMessage.msg("Time to run: "+delta);
		DebugMessage.msg("Final Score: "+ft.getBest().getCurrentScore());
		//String out = ft.getBestSequence();
		String out = ft.getAllBest();
		System.out.println(out);
		
	}

}
