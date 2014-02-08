package kth.cdate.nellaker;

public class IntervalEvalutation {
	
	private static int M1_2 = 0;
	private static int M1_3 = 1;
	private static int M1_4 = 2;
	private static int M1_5 = 3;
	private static int M2_3 = 4;
	private static int M2_4 = 5;
	private static int M2_5 = 6;
	private static int M3_4 = 7;
	private static int M3_5 = 8;
	private static int M4_5 = 9;
	
	private static int[] minPrac = {-5,-4,-3,-1,1,1, 2,1,1,1};
	private static int[] minComf = {-3,-2,-1, 1,1,1, 2,1,1,1};
	private static int[] minRel  = { 1, 3, 5, 7,1,3, 5,1,3,1};
	private static int[] maxRel  = { 5, 7, 9,10,2,4, 6,2,4,2};
	private static int[] maxComf = { 8,10,12,13,3,5, 8,2,5,3};
	private static int[] maxPrac = {10,12,14,15,5,7,10,4,7,5};
	
	public static int getScore(int finger1, int finger2, int songIndex, Song song) throws Exception
	{
		int fIndex = -1;
		if(finger1 > finger2 )
		{
			int temp = finger1;
			finger1 = finger2;
			finger2 = temp;
		}
		
		if(finger1==1)
		{
			if(finger2==2)
				fIndex = M1_2;
			else if(finger2==3)
				fIndex = M1_3;
			else if(finger2==4)
				fIndex = M1_4;
			else if(finger2==5)
				fIndex = M1_5;
		}
		else if(finger1==2)
		{
			if(finger2==3)
				fIndex = M2_3;
			else if(finger2==4)
				fIndex = M2_4;
			else if(finger2==5)
				fIndex = M2_5;
		}
		else if(finger1==3)
		{
			if(finger2==4)
				fIndex = M3_4;
			else if(finger2==5)
				fIndex = M3_5;
		}
		else if(finger1==4)
		{
			if(finger2==5)
				fIndex = M4_5;
		}
		
		/*
		 * 1. Strech Rule: Assign 2 points for each semitone
		 * that an interval exceeds MaxComf or is less
		 * than MinComf
		 */
		// TODO
		
		/*
		 * 2. Small-Span Rule: For finger pairs including
		 * the thumb, assign 1 point for each semitone
		 * that an interval is less than MinRel. For
		 * finger pairs not including the thumb, assign 
		 * 2 points per semitone
		 */
		// TODO
		
		/*
		 * 3. Large-Span Rule: For finger pairs including
		 * the thumb, assign 1 point for each semitone
		 * that an interval exceeds MaxRel. For finger
		 * pairs not including the thumb, assign 2 points
		 * per semitone.
		 */
		// TODO
		
		/*
		 * 4. Position-Change-Count Rule: Assign 2 points 
		 * for every full change of hand position and 1
		 * point for every half change. A change of hand
		 * position occurs whenever the first and third
		 * notes in a consecutive group of three span an
		 * interval that is greater than MaxComf or less
		 * than MinComf for the corresponding fingers.
		 * In a full change, three conditions are 
		 * satisfied simultaneously: The finger on the
		 * second of the three notes in the thumb; the
		 * second pitch lies between the first and third
		 * pitches; and the interval between the first and
		 * third pitches is greater than MaxPrac or less 
		 * than MinPrac. All other changes are half changes.
		 */
		// TODO
		
		/*
		 * 5. Position-Change-Size Rule: If the interval
		 * spanned by the first and third notes in a group
		 * of three is less than MinComf, assign the
		 * difference between the interval and MinComf
		 * (expressed in semitones). Conversely, if the 
		 * interval is greater than MaxComf, assign the
		 * difference between the interval and MaxComf
		 */
		// TODO
		
		/*
		 * 6. Weak-Finger Rule: Assign 1 point every time
		 * finger 4 or finger 5 is used.
		 */
		// TODO
		
		/*
		 * 7. Three-Four-Five Rule: Assign 1 point every
		 * time fingers 3,4, and 5 occur consecutively in
		 * any order, even when groups overlap.
		 */
		// TODO
		
		/*
		 * 8. Three-to-Four Rule: Assign 1 point each time
		 * finger 3 is immediately followed by finger 4.
		 */
		// TODO
		
		/*
		 * 9. Four-on-Black Rule: Assign 1 point each time
		 * fingers 3 and 4 occur consecutively in any order
		 * with 3 on white and 4 on black
		 */
		// TODO
		
		/*
		 * 10. Thumb-on-Black Rule: Assign 1 point whenever
		 * the thumb plays a black key. If the immediately
		 * preceding note is white, assign a further 2
		 * points. If the immediately following note is
		 * white, assign a further 2 points.
		 */
		// TODO
		
		/*
		 * 11. Five-on-Black Rule: If the fifth finger plays
		 * a black key and the immediately preceding and 
		 * following notes are also black, assign 0 points. If
		 * the immediately preceding note is white, assign 2
		 * points. If the immediately following key is white,
		 * assign 2 further points.
		 */
		// TODO
		
		/*
		 * 12. Thumb-Passing Rule: Assign 1 points for each
		 * thumb- or finger-pass on the same level (from white
		 * to white or black to black). Assign 3 points if the
		 * lower note is white, played by a finger other than
		 * the thumb, and the upper is black, played by the
		 * thumb.
		 */
		// TODO
		
		
		return -1;
	}
	

}
