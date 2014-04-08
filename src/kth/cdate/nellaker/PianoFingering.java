package kth.cdate.nellaker;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class PianoFingering {

	
	private static final String usage = "Usage: java PianoFingering <midi_file> <options>\n"
			+ "Valid options:\n"
			+ "-s N\n"
			+ "  Run the program from starting index N (inclusive). Can be from 0 up to song length. (Default: 0)\n"
			+ "-e N\n"
			+ "  Run the program to ending index N (non-inclusive). Can be from 1 up to song length. (Default: Song_Length)\n"
			+ "-m N\n"
			+ "  Set a max score allowed for nodes. Is useful if running out of heap is an issue. (Default: 1500)\n"; 
	public static void main(String[] args)
	{
		/*
		 * Run startup and check for arguments
		 */
		File midiFile = null;
		int startIndex = 0;
		int endIndex = Integer.MAX_VALUE;
		int maxScore = 1500;
		try
		{
			if(args.length == 0)
				throw new Exception("No arguments Exception");
			else
			{
				midiFile = new File(args[0]);
				for(int i = 1; i<args.length ; )
				{						
					switch(args[i]){
					case "-s":
						startIndex = Integer.parseInt(args[i+1]);
						i+=2;
						break;
					case "-e":
						endIndex = Integer.parseInt(args[i+1]);
						i+=2;
						break;
					case "-m":
						maxScore = Integer.parseInt(args[i+1]);
						i+=2;
					default:
						
						break;
					
					}
				}
			}
		}
		catch(NumberFormatException e)
		{
			System.out.println("Incorrect use of arguments Exception");
			System.out.println(usage);
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			System.out.println(usage);
			return;
		}
		
		/*
		 * Initialize all variables that have not been set already
		 */
		//Song song = new Song(); // Create a default scale
		Song song = new Song(midiFile);
		FingerTree.end_index = endIndex;
		FingerTree.start_index = startIndex;
		FingerTree.max_score = maxScore;
		if(FingerTree.end_index==Integer.MAX_VALUE)
			FingerTree.end_index = song.getLength()-1;
		
		System.out.println("Song: "+song.toString());
		FingerTree ft = new FingerTree();
		long start = System.currentTimeMillis();
		ft.generateTree(song, 0);
		long delta = System.currentTimeMillis()-start;
		System.err.println("\n\nTime to run: "+delta);
		
		
		if(ft.getBest() != null)
			System.err.println("Final Score: "+ft.getBest().getCurrentScore());
		else
			System.err.println("No path found, try lowering MAX_SCORE in FingerTree");
		String out = ft.getAllBest();
		System.out.println(out);
		
	}

}
