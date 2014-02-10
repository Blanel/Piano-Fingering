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
	private int currentScore = Integer.MAX_VALUE; // Aggregated score of best children

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

	public ArrayList<NoteNode> generateChildren(LinkedList<NoteNode> queue)
	{
		if(songIndex+1 < song.getLength())
		{
			for(int i = 1 ; i<=5 ; i++)
			{
				NoteNode c = new NoteNode(songIndex+1, i, this, song);
				queue.add(c);
				children.add(c);
			}
			if(songIndex+2 == song.getLength())
			{
				return children;
			}
		}
		return null;
	}

	public void generateValue()
	{
		//currentScore = -1;
		localScore = IntervalEvalutation.getScore(this, song);
		
	}
	public void zeroCurrentValue()
	{
		currentScore = 0;
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
	
	public void updateParents()
	{
		NoteNode current = this;
		while(current.parent != null)
		{
			if(current.parent.bestChild == null)
			{
				current.parent.bestChild = current;
				current.parent.currentScore = current.currentScore+current.localScore;
			}
			else
			{
				if(current.parent.currentScore>current.localScore+current.currentScore)
				{
					current.parent.bestChild = current;
					current.parent.currentScore = current.currentScore+current.localScore;
					
				}
			}
			current = current.parent;
		}
	}
	
	public int getCurrentScore()
	{
		return currentScore;
	}
	
	public int getLocalScore()
	{
		return localScore;
	}
	
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		NoteNode current = this;
		while(current.bestChild != null)
		{
			sb.append(current.getFinger());
			current = current.bestChild;
		}
		sb.append(current.getFinger());
		return sb.toString();
	}
}