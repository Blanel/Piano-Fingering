package kth.cdate.nellaker;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.SortedSet;
import java.util.LinkedList;

import com.scottlogic.util.SortedList;

public class FingerTree {

	private ArrayList<NoteNode> root;
	private NoteNode best;
	private ArrayList<NoteNode> allBest;
	public static final int MAX_SCORE = 1000;
	
	Comparator<NoteNode> comp = new Comparator<NoteNode>(){
		public int compare(NoteNode one, NoteNode two){
			return one.getCurrentScore() - two.getCurrentScore();
		}
	}; 
	
	public void generateTree(Song s, int offset)
	{
		long iterations = Math.round(Math.pow(5, s.getLength()));
		SortedList<NoteNode> queue = new SortedList<NoteNode>(comp);
		root = new ArrayList<NoteNode>();
		allBest = new ArrayList<NoteNode>();
		int bestPath = Integer.MAX_VALUE-offset;
		int highestIndex = 0;

		// Initialize the queue for each finger
		for(int i = 1 ; i<=5 ; i++)
		{
			// Add node to root list
			for(int j = 1 ; j<=5 ; j++)
			{
				NoteNode temp = new NoteNode(0, i, j, null, s);
				temp.generateValue();
				root.add(temp);
				
			}
		}
		
		// Queue all nodes in root
		for(int i = 0 ; i<root.size() ; i++)
		{
			queue.add(root.get(i));
		}
		// Sort the queue
		int iterations_done = 0;
		while(!queue.isEmpty() && queue.get(0).getCurrentScore()<bestPath+offset)
		{
			
			//System.err.println(bestPath + " " +queue.get(0).getIndex()+" "+highestIndex+" "+queue.get(0).getCurrentScore() + " "+iterations--+" - "+iterations_done++);
			//DebugMessage.msg(bestPath + " " +queue.get(0).getIndex()+" "+highestIndex+" "+queue.get(0).getCurrentScore() + " "+iterations--);
			NoteNode current = queue.remove(0);
			if(current.getIndex()== s.getLength()-1  && current.getCurrentScore()<=bestPath+offset)
			{
				best = current;
				allBest.add(current);
				bestPath = current.getCurrentScore();
			}
			else
			{
			// Generate children and add to queue
				current.generateChildren(queue);
			}
			if(current.getIndex()>highestIndex)
				highestIndex = current.getIndex();
		}
		DebugMessage.msg("Generation done!");
	}
	
	public String getBestSequence()
	{
		return best.toString();
	}
	public String getAllBest()
	{
		StringBuilder sb = new StringBuilder();
		for(int i = 0 ; i<allBest.size() ; i++)
		{
			sb.append(allBest.get(i).toString()+"\n");
		}
		return sb.toString();
	}
	public NoteNode getBest()
	{
		return best;
	}
}