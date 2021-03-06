package kth.cdate.nellaker;

public class IntervalEvalutation {

	private static boolean debug = false;

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

	// Reach arrays TODO: Decide these values from user input to facilitate for different hand sizes.
	private static int[] minPrac = {-5,-4,-3,-1,1,1, 2,1,1,1};
	private static int[] minComf = {-3,-2,-1, 1,1,1, 2,1,1,1};
	private static int[] minRel  = { 1, 3, 5, 7,1,3, 5,1,3,1};
	private static int[] maxRel  = { 5, 7, 9,10,2,4, 6,2,4,2};
	private static int[] maxComf = { 8,10,12,13,3,5, 8,2,5,3};
	private static int[] maxPrac = {10,12,14,15,5,7,10,4,7,5};

	/**
	 * Goes through rules specified in "An Ergonomic Model of Fingering for Melodic Fragments"
	 * by [INSERT NAMES] in the order of how many past nodes must be generated.
	 */
	public static int getScore(NoteNode nn, Song song)
	{

		boolean[] rulesTriggered = new boolean[13];
		DebugMessage.msg(("NEW NODE"), debug);
		int score = 0;



		/*
		 * 6. Weak-Finger Rule: Assign 1 point every time
		 * finger 4 or finger 5 is used.
		 * 
		 * Needs 0 past node
		 */

		if(song.getTone(nn.getIndex())>-1 && nn.getFinger()>=4 && song.getTone(nn.getIndex())>=0)
		{
			score += 1;
			DebugMessage.msg(("\tRule 6 triggered"), debug);
			rulesTriggered[5]=true;
		}



		// Run rules that are dependant on 1 past node in history
		if(nn.getIndex()>FingerTree.start_index && song.getTone(nn.getIndex())>-1 && song.getTone(nn.getIndex()-1)>-1)
		{
			int fIndex = -1;
			int finger1 = nn.getParent().getFinger();
			int finger2 = nn.getFinger();

			// Set the index for the Reach Arrays.
			fIndex = getFIndex(finger1, finger2);

			int interval = (song.getTone(nn.getIndex())-song.getTone(nn.getParent().getIndex()));

			if(nn.getFinger() < nn.getParent().getFinger())
				interval *= -1;

			// Check if the same finger is used twice
			if(fIndex == -1 && song.getTone(nn.getParent().getIndex())==song.getTone(nn.getIndex()))
			{
				DebugMessage.msg(("\tSame tone, same finger"), debug);
				return 0;
			}
			else if(fIndex == -1)
			{
				DebugMessage.msg(("\tDiffernent tone, same finger"), debug);
				return 30000;
			}
			if(interval > maxPrac[fIndex] || interval<minPrac[fIndex])
			{
				DebugMessage.msg(("\tOutside practical"), debug);
				return 30000;
			}
			/*
			 * 1. Strech Rule: Assign 2 points for each semitone
			 * that an interval exceeds MaxComf or is less
			 * than MinComf checked
			 * 
			 * Needs 1 past node.
			 */
			if(interval > maxComf[fIndex])
			{
				DebugMessage.msg(("\tRule 1 triggered"), debug);
				rulesTriggered[0]=true;
				score += (interval - maxComf[fIndex])*2;
			}
			else if(interval < minComf[fIndex])
			{
				DebugMessage.msg(("\tRule 1 triggered"), debug);
				rulesTriggered[0]=true;
				score += (minComf[fIndex]-interval)*2;
			}

			/*
			 * 2. Small-Span Rule: For finger pairs including
			 * the thumb, assign 1 point for each semitone
			 * that an interval is less than MinRel. For
			 * finger pairs not including the thumb, assign 
			 * 2 points per semitone checked
			 * 
			 * Needs 1 past node
			 */
			if(interval<minRel[fIndex])
			{
				if(fIndex < 4)
				{
					DebugMessage.msg(("\tRule 2 triggered"), debug);
					rulesTriggered[1]=true;
					score += (minRel[fIndex]-interval);
				}
				else
				{
					DebugMessage.msg(("\tRule 2 triggered"), debug);
					rulesTriggered[1]=true;
					score += (minRel[fIndex]-interval)*2;
				}
			}

			/*
			 * 3. Large-Span Rule: For finger pairs including
			 * the thumb, assign 1 point for each semitone
			 * that an interval exceeds MaxRel. For finger
			 * pairs not including the thumb, assign 2 points
			 * per semitone. checked
			 * 
			 * Needs 1 past node
			 */
			if(interval>maxRel[fIndex])
			{
				if(fIndex < 4)
				{
					DebugMessage.msg(("\tRule 3 triggered"), debug);
					rulesTriggered[2]=true;
					score += (interval-maxRel[fIndex]);
				}
				else
				{
					DebugMessage.msg(("\tRule 3 triggered"), debug);
					rulesTriggered[2]=true;
					score += (interval-maxRel[fIndex])*2;
				}
			}

			/*
			 * 8. Three-to-Four Rule: Assign 1 point each time
			 * finger 3 is immediately followed by finger 4. checked
			 * 
			 * Needs 1 past node
			 */
			if(nn.getParent().getFinger()==3 && nn.getFinger()==4)
			{
				DebugMessage.msg(("\tRule 8 triggered"), debug);
				rulesTriggered[7]=true;
				score+=1;
			}

			/*
			 * 9. Four-on-Black Rule: Assign 1 point each time
			 * fingers 3 and 4 occur consecutively in any order
			 * with 3 on white and 4 on black checked
			 * 
			 * Needs 1 past node
			 */
			if	(
					nn.getParent().getFinger()==3 && nn.getFinger() == 4 && !Song.isBlack(song.getTone(nn.getIndex()-1)) && Song.isBlack(song.getTone(nn.getIndex()))
					||
					nn.getParent().getFinger()==4 && nn.getFinger() == 3 && Song.isBlack(song.getTone(nn.getIndex()-1)) && !Song.isBlack(song.getTone(nn.getIndex()))
					) {
				DebugMessage.msg(("\tRule 9 triggered"), debug);
				rulesTriggered[8]=true;
				score += 1;
			}

			/*
			 * 12. Thumb-Passing Rule: Assign 1 points for each
			 * thumb- or finger-pass on the same level (from white
			 * to white or black to black). Assign 3 points if the
			 * lower note is white, played by a finger other than
			 * the thumb, and the upper is black, played by the
			 * thumb. checked
			 * 
			 * Needs 1 past node
			 */
			int oldScore = score;
			if(song.getTone(nn.getIndex()-1)<song.getTone(nn.getIndex()) && nn.getParent().getFinger()>nn.getFinger())
			{
				if(!Song.isBlack(song.getTone(nn.getIndex()-1)) && nn.getParent().getFinger() != 1 && Song.isBlack(song.getTone(nn.getIndex())) && nn.getFinger() ==1)
					score+=3;
				if (Song.isBlack(song.getTone(nn.getIndex())) == Song.isBlack(song.getTone(nn.getIndex()-1)))
					score++;
			}
			if(song.getTone(nn.getIndex()-1)>song.getTone(nn.getIndex()) && nn.getParent().getFinger()<nn.getFinger())
			{
				if(!Song.isBlack(song.getTone(nn.getIndex())) && nn.getFinger() != 1 && Song.isBlack(song.getTone(nn.getIndex()-1)) && nn.getParent().getFinger() ==1)
					score+=3;
				if(Song.isBlack(song.getTone(nn.getIndex())) == Song.isBlack(song.getTone(nn.getIndex()-1)))
					score++;
			}
			if(oldScore != score)
			{
				DebugMessage.msg(("\tRule 12 triggered"), debug);
				rulesTriggered[11]=true;
			}



			// Run rules dependant on future node in history
			if(nn.getIndex()+1<FingerTree.end_index && song.getTone(nn.getIndex())>-1 && song.getTone(nn.getIndex()-1)>-1 && song.getTone(nn.getIndex()+1)>-1)
			{

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
				 * second of the three notes is the thumb; the
				 * second pitch lies between the first and third
				 * pitches; and the interval between the first and
				 * third pitches is greater than MaxPrac or less 
				 * than MinPrac. All other changes are half changes.
				 * 
				 * Needs 2 past node
				 */

				int interval2 = song.getTone(nn.getIndex()+1) - song.getTone(nn.getIndex()-1);
				if(nn.getNextFinger() < nn.getParent().getFinger())
					interval2 *= -1;
				int fIndex2 = (getFIndex(nn.getNextFinger(), nn.getParent().getFinger()));

				if(fIndex2 == -1)
				{
					score+=interval;
				}
				if(fIndex2 != -1) // TODO Check if this is correct, feels fishy!
				{


					if((interval2 > maxComf[fIndex2])||(interval2 < minComf[fIndex2]))
					{
						boolean fullChange = false;
						if(nn.getFinger() == 1)
						{
							if((song.getTone(nn.getIndex())<song.getTone(nn.getIndex()-1) && song.getTone(nn.getIndex())>song.getTone(nn.getIndex()+1)) || (song.getTone(nn.getIndex())<song.getTone(nn.getIndex()+1) && song.getTone(nn.getIndex())>song.getTone(nn.getIndex()-1)))
							{
								if(interval2 > maxPrac[fIndex2] || interval2 < minComf[fIndex2])
								{
									fullChange = true;
									DebugMessage.msg(("\tRule 4 triggered (Full change)"), debug);
									rulesTriggered[3]=true;
									score +=2;
								}
							}

						}
						if(!fullChange)
						{
							DebugMessage.msg(("\tRule 4 triggered"), debug);
							rulesTriggered[3]=true;
							score +=1;
						}
					}


					/*
					 * 5. Position-Change-Size Rule: If the interval
					 * spanned by the first and third notes in a group
					 * of three is less than MinComf, assign the
					 * difference between the interval and MinComf
					 * (expressed in semitones). Conversely, if the 
					 * interval is greater than MaxComf, assign the
					 * difference between the interval and MaxComf
					 * 
					 * Needs 2 past node
					 */
					if(interval2<minComf[fIndex2])
					{
						DebugMessage.msg(("\tRule 5_ triggered"), debug);
						rulesTriggered[4]=true;
						score += minComf[fIndex2]-interval2;
					}
					else if(interval2>maxComf[fIndex2])
					{
						DebugMessage.msg(("\tRule 5 triggered"), debug);
						rulesTriggered[4]=true;
						score += interval2-maxComf[fIndex2];
					}



					/*
					 * 7. Three-Four-Five Rule: Assign 1 point every
					 * time fingers 3,4, and 5 occur consecutively in
					 * any order, even when groups overlap.
					 * 
					 * Needs 2 past node
					 */
					if(nn.getNextFinger() != nn.getFinger() && nn.getParent().getFinger() != nn.getFinger() && nn.getParent().getFinger() != nn.getNextFinger() && nn.getNextFinger()>=3 && nn.getFinger()>=3 && nn.getParent().getFinger()>=3)
					{
						DebugMessage.msg(("\tRule 7 triggered"), debug);
						rulesTriggered[6]=true;
						score+=1;
					}
				}
			}


		}
		// Special rules
		/*
		 * 10. Thumb-on-Black Rule: Assign 1 point whenever
		 * the thumb plays a black key. If the immediately
		 * preceding note is white, assign a further 2
		 * points. If the immediately following note is
		 * white, assign a further 2 points.
		 * 
		 * Needs 1 past node or 2 past nodes
		 */
		if(song.getTone(nn.getIndex())>-1 && nn.getFinger()== 1 && Song.isBlack(song.getTone(nn.getIndex())))
		{
			DebugMessage.msg(("\tRule 10 triggered"), debug);
			rulesTriggered[9]=true;
			score += 1;
			if(song.getTone(nn.getIndex()-1)>-1 && nn.getIndex() > FingerTree.start_index && !Song.isBlack(nn.getIndex()-1))
				score+=2;
			if(song.getTone(nn.getIndex()+1)>01 && nn.getIndex() < FingerTree.end_index && !Song.isBlack(song.getTone(nn.getIndex()+1)))
				score+=2;
		}

		/*
		 * 11. Five-on-Black Rule: If the fifth finger plays
		 * a black key and the immediately preceding and 
		 * following notes are also black, assign 0 points. If
		 * the immediately preceding note is white, assign 2
		 * points. If the immediately following key is white,
		 * assign 2 further points.
		 * 
		 * Needs 1 past node or 2 past nodes
		 */
		if(song.getTone(nn.getIndex())>-1 && nn.getFinger()== 5 && Song.isBlack(song.getTone(nn.getIndex())))
		{
			if(nn.getIndex() > FingerTree.start_index && song.getTone(nn.getIndex()-1)>-1 && !Song.isBlack(song.getTone(nn.getIndex()-1)))
			{
				DebugMessage.msg(("\tRule 11 triggered"), debug);
				rulesTriggered[10]=true;
				score +=2;
			}
			if(nn.getIndex() < FingerTree.end_index && song.getTone(nn.getIndex()+1)>-1 && !Song.isBlack(song.getTone(nn.getIndex()+1)))
			{
				DebugMessage.msg(("\tRule 11 triggered"), debug);
				rulesTriggered[10]=true;
				score +=2;
			}

		}
		// Special rules
		/*
		 * 10. Thumb-on-Black Rule: Assign 1 point whenever
		 * the thumb plays a black key. If the immediately
		 * preceding note is white, assign a further 2
		 * points. If the immediately following note is
		 * white, assign a further 2 points.
		 * 
		 * Needs 1 past node or 2 past nodes
		 */
		if(song.getTone(nn.getIndex())>-1 && nn.getFinger()== 1 && Song.isBlack(song.getTone(nn.getIndex())))
		{
			DebugMessage.msg(("\tRule 10 triggered"), debug);
			rulesTriggered[9]=true;
			score += 1;
			if(song.getTone(nn.getIndex()-1)>-1 && nn.getIndex() > FingerTree.start_index && !Song.isBlack(nn.getIndex()-1))
				score+=2;
			if(song.getTone(nn.getIndex()+1)>01 && nn.getIndex() < FingerTree.end_index && !Song.isBlack(song.getTone(nn.getIndex()+1)))
				score+=2;
		}

		/*
		 * 11. Five-on-Black Rule: If the fifth finger plays
		 * a black key and the immediately preceding and 
		 * following notes are also black, assign 0 points. If
		 * the immediately preceding note is white, assign 2
		 * points. If the immediately following key is white,
		 * assign 2 further points.
		 * 
		 * Needs 1 past node or 2 past nodes
		 */
		if(song.getTone(nn.getIndex())>-1 && nn.getFinger()== 5 && Song.isBlack(song.getTone(nn.getIndex())))
		{
			if(nn.getIndex() > FingerTree.start_index && song.getTone(nn.getIndex()-1)>-1 && !Song.isBlack(song.getTone(nn.getIndex()-1)))
			{
				DebugMessage.msg(("\tRule 11 triggered"), debug);
				rulesTriggered[10]=true;
				score +=2;
			}
			if(nn.getIndex() < FingerTree.end_index && song.getTone(nn.getIndex()+1)>-1 && !Song.isBlack(song.getTone(nn.getIndex()+1)))
			{
				DebugMessage.msg(("\tRule 11 triggered"), debug);
				rulesTriggered[10]=true;
				score +=2;
			}

		}
		/*
		 * XX. The pause rule.
		 * This rule is under development
		 */
		if(nn.getIndex()-1>=FingerTree.start_index && nn.getIndex()+1<=FingerTree.end_index)
		{
			int interval2 = song.getTone(nn.getIndex()+1) - song.getTone(nn.getIndex()-1);
			if(nn.getNextFinger() < nn.getParent().getFinger())
				interval2 *= -1;
			int fIndex2 = (getFIndex(nn.getNextFinger(), nn.getParent().getFinger()));
			if(song.getTone(nn.getIndex())<0)
			{
				if(fIndex2 == -1)
				{
					if(interval2!=0)
					{
						rulesTriggered[12]=true;
						score++;
					}
				}
				else
				{
					if(interval2>maxRel[fIndex2])
					{
						rulesTriggered[12]=true;
						score++;
					}
						
					if(interval2<minRel[fIndex2])
					{
						rulesTriggered[12]=true;
						score++;
					}
				}
			}
		}

		nn.setRules(rulesTriggered);


		return score;
	}

	public static int getFIndex(int finger1, int finger2)
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
		return fIndex;
	}

}
