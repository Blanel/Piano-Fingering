package kth.cdate.nellaker;

import java.io.File;
import java.util.ArrayList;

import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;

public class Song {

	private static ArrayList<Integer> tones;
	private static final int NOTE_ON = 0x90;
	private static final int NOTE_OFF = 0x80;//  0     1    2    3     4    5    6     7    8     9    10   11
	private static final String[] NOTE_NAMES = {"C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"};
	private static final int[] song = {0,2,4,5,7,9,11};
	private static final int[] song2 = {69,60,69,76};
	private static final boolean debug = false;
	private File midi;
	public Song(File midi)
	{
		this.midi = midi;
		try {
			tones = parseMidi(midi);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Song()
	{
		int scaleLength = 35;
		tones = new ArrayList<Integer>();
		for(int i = 0 ; i<scaleLength ; i++)
			tones.add(i);
		//for(int i = 0; i<song2.length ; i++)
		//	tones.add(song2[i]);
	}

	public int getTone(int i)
	{
		return tones.get(i);
	}

	public int getLength()
	{
		return tones.size();
	}

	public static String getNoteName(int i)
	{
		if(i<0)
			return "Paus";
		return NOTE_NAMES[i % 12];
	}

	public static boolean isBlack(int i)
	{
		int num = i % 12;
		if(num == 1 || num == 3 || num == 6 || num == 8 || num == 10)
			return true;
		return false;
	}

	public static int getOctave(int i)
	{
		return (i / 12)-1;
	}

	public ArrayList<Integer> parseMidi(File midi) throws Exception {
		Sequence sequence = MidiSystem.getSequence(midi);
		ArrayList<Integer> notes = new ArrayList<Integer>();
		long noteOffTime = Long.MAX_VALUE;
		boolean firstNote = true;
		for (Track track :  sequence.getTracks()) {
			for (int i=0; i < track.size(); i++) { 
				MidiEvent event = track.get(i);
				MidiMessage message = event.getMessage();
				if (message instanceof ShortMessage) {
					ShortMessage sm = (ShortMessage) message;
					if (sm.getCommand() == NOTE_ON && sm.getData2()>0) { // Check so it is a hit and that it has velocity
						if(!firstNote && noteOffTime+2 < event.getTick())
						{
							/*
							 * Paus lengths:
							 * whole-note: 			1921 ticks
							 * half-note: 			961 ticks
							 * quarter-note: 		481 ticks
							 * eighth-note: 		241 ticks
							 * sixteenth-note: 		121 ticks
							 * thirty-second-note: 	61 ticks
							 * sixty-fourth-note: 	31 ticks 
							 */
							notes.add((int)(noteOffTime-event.getTick()));
							DebugMessage.msg("Paus: "+(event.getTick()-noteOffTime),debug);
						}
						//System.out.println("Note added");
						notes.add(sm.getData1()); // Adds the note to the list
						if(firstNote)
							firstNote = false;
					} 
					//System.out.println(sm.getCommand());
					if (sm.getData2()==0)
					{
						noteOffTime = event.getTick();
						
					}
				} 
			}         
		}
		return notes;
	}

	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("Midi File: "+midi.getName()+"\n");
		for(int i = FingerTree.start_index ; i <= FingerTree.end_index ; i++)
		{
			sb.append(Song.getNoteName(tones.get(i))+Song.getOctave(tones.get(i))+"  ");
		}
		return sb.toString();
	}
}
