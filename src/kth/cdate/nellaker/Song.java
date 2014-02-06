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
		int scaleLength = 20;
		tones = new ArrayList<Integer>();
		for(int i = 0 ; i<20 ; i++)
		{
			tones.add(i);
		}
		
	}
	
	public int getTone(int i)
	{
		return tones.get(i);
	}
	
	public int getLength()
	{
		return tones.size();
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

	public static String getNoteName(int i)
	{
		int note = i % 12;
		 int octave = (i / 12)-1;
        return NOTE_NAMES[note]+octave;
	}
	public ArrayList<Integer> parseMidi(File midi) throws Exception {
        Sequence sequence = MidiSystem.getSequence(midi);
        ArrayList<Integer> notes = new ArrayList<Integer>();
        int trackNumber = 0;
        for (Track track :  sequence.getTracks()) {
            trackNumber++;
            System.out.println("Track " + trackNumber + ": size = " + track.size());
            System.out.println();
            for (int i=0; i < track.size(); i++) { 
                MidiEvent event = track.get(i);
                System.out.print("@" + event.getTick() + " ");
                MidiMessage message = event.getMessage();
                if (message instanceof ShortMessage) {
                    ShortMessage sm = (ShortMessage) message;
                    System.out.print("Channel: " + sm.getChannel() + " ");
                    if (sm.getCommand() == NOTE_ON && sm.getData2()>0) {
                        int key = sm.getData1();
                        int octave = (key / 12)-1;
                        int note = key % 12;
                        String noteName = NOTE_NAMES[note];
                        int velocity = sm.getData2();
                        System.out.println("Note on, " + noteName + octave + " key=" + key + " velocity: " + velocity);
                        notes.add(key);
                    } else if (sm.getCommand() == NOTE_OFF) {
                        int key = sm.getData1();
                        int octave = (key / 12)-1;
                        int note = key % 12;
                        String noteName = NOTE_NAMES[note];
                        int velocity = sm.getData2();
                        System.out.println("Note off, " + noteName + octave + " key=" + key + " velocity: " + velocity);
                    } else {
                        System.out.println("Command:" + sm.getCommand());
                    }
                } else {
                    System.out.println("Other message: " + message.getClass());
                }
            }

            System.out.println();
            
            
        }
        return notes;
	}

}
