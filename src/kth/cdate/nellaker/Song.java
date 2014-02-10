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
	private static final int NOTE_OFF = 0x80;
	private static final String[] NOTE_NAMES = {"C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"};

	public Song(File midi)
	{
		try {
			tones = parseMidi(midi);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Song()
	{
		int scaleLength = 10;
		tones = new ArrayList<Integer>();
		for(int i = 0 ; i<scaleLength ; i++)
			tones.add(i);
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
		for (Track track :  sequence.getTracks()) {
			for (int i=0; i < track.size(); i++) { 
				MidiEvent event = track.get(i);
				MidiMessage message = event.getMessage();
				if (message instanceof ShortMessage) {
					ShortMessage sm = (ShortMessage) message;
					if (sm.getCommand() == NOTE_ON && sm.getData2()>0) { // Check so it is a hit and that it has velocity

						notes.add(sm.getData1()); // Adds the note to the list
					} 
				} 
			}         
		}
		return notes;
	}

	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		for(int i = 0 ; i < tones.size() ; i++)
		{
			sb.append(Song.getNoteName(tones.get(i))+"  ");
		}
		return sb.toString();
	}
}
