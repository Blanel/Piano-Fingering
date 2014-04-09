package kth.cdate.nellaker;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

import com.scottlogic.util.SortedList;


public class NoteNode {

	private int songIndex;
	private Song song;

	private int finger;


	private NoteNode parent;
	private int nextFinger;
	//private ArrayList<NoteNode> children;

	private int localScore; // The score of the current node with respect to rules
	private int currentScore = Integer.MAX_VALUE; // Aggregated score of best children
	private boolean[] rulesTriggered;

	public NoteNode(int songIndex, int finger, int nextFinger, NoteNode parent, Song song) {
		this.songIndex = songIndex;
		//children = new ArrayList<NoteNode>();
		this.finger = finger;
		this.parent = parent;
		this.song = song;
		this.nextFinger = nextFinger;
		rulesTriggered = new boolean[12];
		generateValue();
	}


	public void generateChildren(SortedList<NoteNode> queue)
	{
		if(songIndex+1 < FingerTree.end_index)
		{
			if(songIndex+2 < FingerTree.end_index && song.getTone(songIndex+2)<0)
			{
				NoteNode temp = new NoteNode(songIndex+1, nextFinger, -1, this, song);
				temp.generateValue();
				if(temp.getCurrentScore()<FingerTree.max_score)
					queue.add(temp);
			}
			else
			{
				for(int i = 1 ; i<=5 ; i++)
				{
					NoteNode temp = new NoteNode(songIndex+1, nextFinger, i, this, song);
					temp.generateValue();
					if(temp.getCurrentScore()<FingerTree.max_score)
						queue.add(temp);
				}
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

	public int getNextFinger()
	{
		return nextFinger;
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
		int lastScore = this.getCurrentScore();
		LinkedList<FStringNode> fingering = new LinkedList<FStringNode>();
		for(int i = 0 ; i<=FingerTree.end_index-FingerTree.start_index ; i++)
		{
			fingering.addFirst(new FStringNode(current));
			current = current.parent;
		}
		Iterator<FStringNode> it = fingering.iterator();
		while(it.hasNext())
		{
			sb.append(it.next().toString()+"\n");
		}
		sb.append("Final Score: "+lastScore);
		return sb.toString();
	}

	public void setRules(boolean[] rulesTriggered)
	{
		this.rulesTriggered = rulesTriggered;
	}

	private class FStringNode
	{
		int finger;
		String note;
		int score;
		boolean[] rulesTriggered;

		public FStringNode(NoteNode nn)
		{
			this.finger = nn.finger;
			this.score = nn.localScore;
			this.rulesTriggered = nn.rulesTriggered;
			note = Song.getNoteName(song.getTone(nn.getIndex()))+Song.getOctave(song.getTone(nn.getIndex()));
		}

		public String toString()
		{
			StringBuilder sb = new StringBuilder();
			sb.append(finger+"\t"+note+"\t"+score+"\t[");

			for(int i = 0 ; i<11 ; i++)
			{
				if(rulesTriggered[i])
					sb.append((i+1)+" ");
			}
			if(rulesTriggered[11])
				sb.append(12);
			sb.append("]");
			return sb.toString();
		}


	}

}