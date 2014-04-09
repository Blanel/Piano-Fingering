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
	private boolean debug=true;

	public int worstScore = 0;
	public static int max_score = 1500;
	public static int start_index = 0;
	public static int end_index = Integer.MAX_VALUE;
	public static int PRUNED = 0;

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
			if(s.getTone(start_index+1)<0)
			{
				NoteNode temp = new NoteNode(start_index, i, -1, null, s);
				temp.generateValue();
				root.add(temp);
			}
			else if(s.getTone(start_index)<0)
			{
				NoteNode temp = new NoteNode(start_index, -1, i, null, s);
				temp.generateValue();
				root.add(temp);
			}
			else
			{
				// Add node to root list
				for(int j = 1 ; j<=5 ; j++)
				{
					NoteNode temp = new NoteNode(start_index, i, j, null, s);
					temp.generateValue();
					root.add(temp);

				}
			}
		}

		// Queue all nodes in root
		for(int i = 0 ; i<root.size() ; i++)
		{
			queue.add(root.get(i));
		}
		// Sort the queue
		int iterations_done = 0;
		float loadingBar = 0;
		int loadingBarLength = 50;
		System.out.println();
		for(int i = 0 ; i<loadingBarLength ; i++)
		{
			System.out.print("^");
		}
		System.out.println();
		while(!queue.isEmpty() && queue.get(0).getCurrentScore()<bestPath+offset)
		{
			iterations_done++;

			//System.err.println(queue.get(0).getIndex()+" "+highestIndex+" "+queue.get(0).getCurrentScore() + " "+iterations_done++);
			if((float)highestIndex/(float)end_index>loadingBar-(float)1/(float)loadingBarLength)
			{
				loadingBar += (float)1/(float)loadingBarLength; 
				System.out.print("#");
			}
			//DebugMessage.msg(bestPath + " " +queue.get(0).getIndex()+" "+highestIndex+" "+queue.get(0).getCurrentScore() + " "+iterations--);
			NoteNode current = queue.remove(0);
			if(current.getIndex()== end_index-1  && current.getCurrentScore()<=bestPath+offset)
			{
				NoteNode leaf = new NoteNode(current.getIndex()+1, current.getNextFinger(), -1, current, s);
				leaf.generateValue();
				if(leaf.getCurrentScore()<=bestPath)
				{
					best = leaf;
					allBest.add(leaf);
					bestPath = leaf.getCurrentScore();
				}
				
			}
			else
			{
				// Generate children and add to queue
				current.generateChildren(queue);
			}
			if(current.getIndex()>highestIndex)
				highestIndex = current.getIndex();
		}
		System.out.println();
		//worstScore = queue.get(queue.size()-1).getCurrentScore();
		DebugMessage.msg("Generation done!",debug);
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
			sb.append(allBest.get(i).toString()+"\n\n");
		}
		return sb.toString();
	}
	public NoteNode getBest()
	{
		return best;
	}
}