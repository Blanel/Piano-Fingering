package kth.cdate.nellaker;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class FingeringEvaluation {
	
	public static void evaluate(File fingerFile, Song song) throws FileNotFoundException
	{
		FileReader fr = new FileReader(fingerFile);
		
		BufferedReader br = new BufferedReader(fr);
		// Loop through the fingerfile
		String content = new Scanner(fingerFile).useDelimiter("\\Z").next();
		//System.out.println(content);
		String[] fingers = content.split("\\n");
		FingerTree.start_index=0;
		FingerTree.end_index=fingers.length-1;
		NoteNode root = new NoteNode(0, Integer.parseInt(fingers[0]), Integer.parseInt(fingers[1]), null, song);
		//root.generateValue();
		NoteNode last = root;
		for(int i = 1 ; i < fingers.length-1 ; i++)
		{
			NoteNode current = new NoteNode(i, Integer.parseInt(fingers[i]), Integer.parseInt(fingers[i+1]), last, song);
			//current.generateValue();
			last = current;
		}
		root = new NoteNode(last.getIndex()+1, Integer.parseInt(fingers[last.getIndex()+1]), -1, last, song);
		//root.generateValue();
		// Print out chain
		System.out.println(root.toString());
		// Print out final score
		//System.out.println(root.getCurrentScore());
	}

	public static void main(String[] args)
	{
		Song song = new Song(new File("Eine_Kleine_Nachtmusik.mid"));
		try {
			evaluate(new File("test.ff"), song);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
