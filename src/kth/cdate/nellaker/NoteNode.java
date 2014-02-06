package kth.cdate.nellaker;

import java.util.ArrayList;
import java.util.LinkedList;


public class NoteNode {

	private int songIndex;
	private int rightHand;

	private int switches = Integer.MAX_VALUE;
	private ArrayList<NoteNode> children;
	private NoteNode bestChild;
	private NoteNode parent;

	public NoteNode(int songIndex, int rightHand, NoteNode parent) {
		this.songIndex = songIndex;
		this.rightHand = rightHand;
		this.parent = parent;
	}

	public void searchValue(LinkedList<NoteNode> found, Song s)
	{
		int tempIndex = songIndex+1;
		for(tempIndex = songIndex+1 ; tempIndex< s.getLength() && isInRange(s.getTone(tempIndex))  ; tempIndex++)
		{
			// Search for how long song can be played without replace of hands.
		}

		for(int i = 0 ; i<5 ; i++)
		{
			if(tempIndex<s.getLength())
			{
				//if(handsValid(s.getTone(tempIndex)-i,rightHand))
				//	found.add(new NoteNode(tempIndex, s.getTone(tempIndex)-i, rightHand, this));
				//if(handsValid(leftHand, s.getTone(tempIndex)-i))
					found.add(new NoteNode(tempIndex, s.getTone(tempIndex)-i, this));
			}
			else
			{
				switches = 0;
				updateParents();
				i = 5;
			}
		}
	}

	private boolean isInRange(int tone)
	{
		if(/*(tone >= leftHand && tone <= leftHand+4) ||*/ (tone >= rightHand && tone <= rightHand+9))
			return true;
		return false;
	}

	/*private boolean handsValid(int leftHand, int rightHand)
	{
		if(leftHand > rightHand)
			return false;
		if(leftHand+4>= rightHand)
			return false;
		return true;
	}*/

	private void updateParents()
	{
		if(parent == null)
			return;
		if(switches<parent.getSwitches())
		{
			parent.setBestChild(this);
			parent.updateParents();
		}

	}

	public int getSwitches()
	{
		return switches;
	}

	public void setBestChild(NoteNode n)
	{
		bestChild = n;
		switches = n.getSwitches()+1;
	}
	public NoteNode getBestChild()
	{
		return bestChild;
	}


	public int getRightHand()
	{
		return rightHand;
	}
	
	public int getIndex()
	{
		return songIndex;
	}

}
