package kth.cdate.nellaker;

import java.util.ArrayList;
import java.util.LinkedList;

public class FingerTree {
	
	private ArrayList<NoteNode> root;
	private NoteNode best;
	public void generateTree(Song s)
	{
		LinkedList<NoteNode> queue = new LinkedList<NoteNode>();
		root = new ArrayList<NoteNode>();
		for(int i = 0 ; i<5 ; i++)
		{
			root.add(new NoteNode(0, s.getTone(0)-i, null));
			queue.add(root.get(i));
		}
		
		while(!queue.isEmpty())
		{
			NoteNode current = queue.removeFirst();
			current.searchValue(queue, s);
			if(best != null)
				queue = new LinkedList<NoteNode>();
				
		}
		
		best = root.get(0);
		
		for(int i = 1 ; i<root.size() ; i++)
		{
			if(best.getSwitches() > root.get(i).getSwitches())
				best = root.get(i);
		}
	}
	
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
		
		sb.append("Switches: "+ best.getSwitches());
		sb.append("\nSequence: ");
		NoteNode c = best;
		while(c.getBestChild() != null)
		{
			sb.append("\nThumb placement: "+Song.getNoteName(c.getRightHand())+"\t"+c.getIndex());
			c = c.getBestChild();
		}
		sb.append("\nRightHand: "+c.getRightHand());
		
		return sb.toString();
	}
	
	

}
