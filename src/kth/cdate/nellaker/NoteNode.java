package kth.cdate.nellaker;

import java.util.ArrayList;
import java.util.LinkedList;


public class NoteNode {

	private int songIndex;
	private Song song;

	private int finger;

	private ArrayList<NoteNode> children;
	private NoteNode bestChild;
	private NoteNode parent;

	private int localScore; // The score of the current node with respect to rules

	public NoteNode(int songIndex, int finger, NoteNode parent, Song song) {
		this.songIndex = songIndex;
		this.finger = finger;
		this.parent = parent;
		this.song = song;
		children = new ArrayList<NoteNode>();
	}

	public NoteNode getBestChild()
	{
		return bestChild;
	}

	public void generateChildren(LinkedList<NoteNode> queue)
	{
		if(songIndex+1 < song.getLength())
		{
			for(int i = 1 ; i<=5 ; i++)
			{
				NoteNode c = new NoteNode(songIndex+1, i, this, song);
				queue.add(c);
				children.add(c);
			}
		}
	}

	public void generateValue()
	{
		localScore = IntervalEvalutation.getScore(this, song);
	}

	public int getFinger()
	{
		return finger;
	}

	public int getIndex()
	{
		return songIndex;
	}

	public NoteNode getParent()
	{
		return parent;
	}
}
