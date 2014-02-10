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

		// Initialize the queue for each finger
		for(int i = 1 ; i<=5 ; i++)
		{
			// Add node to root list
			root.add(new NoteNode(0, i, null,s));
			// Add node to queue.
			queue.add(root.get(i-1));
			// Evaluate node value
		}
		// Sort the queue

		while(!queue.isEmpty())
		{
			// Pop the next node from the queue
			NoteNode current = queue.removeFirst();

			// Evaluate score of the current node
			current.generateValue();
			// Generate children and add to queue
			current.generateChildren(queue);



		}
	}
}
