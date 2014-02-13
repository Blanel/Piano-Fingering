package kth.cdate.nellaker;

import java.util.ArrayList;
import java.util.LinkedList;

import com.scottlogic.util.SortedList;


public class NoteNode {

	private int songIndex;
	private Song song;

	private int finger;

	
	private NoteNode parent;

	private int localScore; // The score of the current node with respect to rules
	private int currentScore = Integer.MAX_VALUE; // Aggregated score of best children

	public NoteNode(int songIndex, int finger, NoteNode parent, Song song) {
		this.songIndex = songIndex;
		this.finger = finger;
		this.parent = parent;
		this.song = song;
		generateValue();
	}


	public void generateChildren(SortedList<NoteNode> queue)
	{
		if(songIndex+1 < song.getLength())
		{
			for(int i = 1 ; i<=5 ; i++)
			{
				NoteNode c = new NoteNode(songIndex+1, i, this, song);
				queue.add(c);
			}
		}
	}

	public void generateValue()
	{
		if(parent == null)
			currentScore = 0;
		else
			currentScore = parent.getCurrentScore();
		localScore = IntervalEvalutation.getScore(this, song);
		currentScore += localScore;
		
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
		int[] fingering = new int[song.getLength()];
		for(int i = 0 ; i<song.getLength() ; i++)
		{
			fingering[song.getLength()-i-1] = current.getFinger();
			current = current.parent;
		}
		for(int i = 0 ; i<song.getLength() ; i++)
		{
			sb.append(fingering[i] + " ");
		}
		return sb.toString();
	}
	
}