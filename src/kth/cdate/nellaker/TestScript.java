package kth.cdate.nellaker;

import java.io.File;

public class TestScript {
	
	public static void main(String[] args)
	{
		Song song = new Song(new File("test.mid")); // Create a default scale
		System.out.println("Song: "+song.toString());
		FingerTree ft = new FingerTree();
		
		ft.generateTree(song);
		
		System.out.println(ft.toString());
	}

}
