package edu.cuny.csi.csc330.newradio;

import java.util.*;
import edu.cuny.csi.csc330.lab1.*;


//Marshall Amp definition
public class Marshall 
{
	public static class Levels
	{
		public static int [] generateInitialSettings(int size, int from, int to) 
		{
			Randomizer randomizer = new Randomizer(); 
			int [] presets = new int[size];
			
			for(int i = 0 ; i  < presets.length ; i++ ) 
			{
				presets[i] = randomizer.generateInt(from, to);
			}
			
			return presets;
		}
	
	
			static public class Clean
			{
				public final static String name = "Clean";
				public final static int MAX_SELECTIONS = 4;
				public final static int LOW_SETTING	= 0;
				public final static int HIGH_SETTING = 10;
				
				public static int [] generateInitialSettings()
				{
					return Levels.generateInitialSettings(MAX_SELECTIONS, LOW_SETTING, HIGH_SETTING);
					
				}
			}
			
			static public class Distortion
			{
				public final static String name  = "Distortion";
				public final static int MAX_SELECTIONS = 7;
				public final static int LOW_SETTING = 0;
				public final static int HIGH_SETTING = 10;
				
				public static int [] generateInitialSettings()
				{
					return Levels.generateInitialSettings(MAX_SELECTIONS, LOW_SETTING, HIGH_SETTING);
				}
			}
		}
	
		//Misc constants 
		protected static final int MIN_VOLUME = 0;
		protected static final int MAX_VOLUME = 10;
		protected static final int DEFAULT_VOLUME = 0;
		protected static final String DEFAULT_TONE = Levels.Clean.name;
		protected static final int DEFAULT_MIDDLE = 0;
		protected static final int DEFAULT_BASS = 0;
		protected static final int DEFAULT_TREBLE = 0;
		protected static final int DEFAULT_GAIN = 0;
		protected static final int DEFAULT_REVERB = 0;
		protected static final int DEFAULT_FXMIX = 0;
		protected static final int DEFAULT_CONTOUR = 0;
		
		//object instances
		private boolean power;
		private boolean instrument;
		private int setVolume;
		private int selectedVolume;
		private int selectedLevels;
		private int[] clean;
		private int[] distortion;
		private Date firstTimeOn;
		private Date lastTimeOn;
		private String selectedTone;
		private String serialNumber;
		private int middle;
		private int bass;
		private int treble;
		private int gain;
		private int reverb;
		private int fxmix;
		private int contour;
		
		public Marshall()
		{
			init();
		}
		
		private void init()
		{
			// serial number 
			Randomizer randomizer = new Randomizer(); 
			Integer irand = randomizer.generateInt(11111111, 99999999); 
			this.serialNumber = new Date().getTime() + ":" + irand.toString();
		}
		
		public void on() throws RadioException
		{
			if(this.isOn() )  
				throw new RadioException("on() method",  RadioException.INVALID_STATE_CHANGE);  
			
			Date now = new Date(); 
		
			// first instance
			if(firstTimeOn == null) 
			{
				// create pre-sets arrays - have the inner classes do it for us 
				clean = Levels.Clean.generateInitialSettings();  
				distortion = Levels.Distortion.generateInitialSettings();  
				firstTimeOn = now; 
				// selectedVolume & station 
				setVolume = DEFAULT_VOLUME; 
				selectedVolume = DEFAULT_VOLUME;
				//selectedStation = DEFAULT_STATION; 
				// tone  
				middle = DEFAULT_MIDDLE; 
				treble = DEFAULT_TREBLE; 
				bass = DEFAULT_BASS;
				gain = DEFAULT_GAIN;
				reverb = DEFAULT_REVERB;
				fxmix = DEFAULT_FXMIX;
				contour = DEFAULT_CONTOUR;
			}
			
			power = true;
			instrument = true;
			lastTimeOn = now;
				
		}
		
		public void off() throws RadioException  
		{
			
			if(this.isOn() == false ) 
				throw new RadioException("off() method",  RadioException.INVALID_STATE_CHANGE);  
			
			
			power = false; 
			
			if(this.isIn() ==false)
				throw new RadioException("off() method", RadioException.INVALID_STATE_CHANGE);
			
			instrument = false;
			
		}
		
		//power check
		public boolean isOn()
		{
			return power == true;
		}
		
		public boolean isIn()
		{
			return instrument == true;
		}
		public int getSelectedVolume() 
		{
			return selectedVolume;
		}
		
		public void setSelectedVolume(int selectedVolume)
		{	
			this.selectedVolume = selectedVolume;
		}

		public int getSelectedLevels() 
		{
			return selectedLevels;
		}

		public void setSelectedLevels(int selectedLevels) 
		{
			this.selectedLevels = selectedLevels;
		}

		public String getSelectedTone() 
		{
			return selectedTone;
		}

		public void setSelectedTone(String selectedTone) 
		{
			this.selectedTone = selectedTone;
		}

		public int[] getDistortion() 
		{
			return distortion;
		}


		public Date getFirstTimeOn() 
		{
			return firstTimeOn;
		}
		

		public int getMiddle() 
		{
			return middle;
		}

		public void setMiddle(int middle) 
		{
			this.middle = middle;
		}

		public int getBass() 
		{
			return bass;
		}

		public void setBass(int bass) 
		{
			this.bass = bass;
		}

		public int getTreble() 
		{
			return treble;
		}

		public void setTreble(int treble) 
		{
			this.treble = treble;
		}
		
		public int getGain() 
		{
			return gain;
		}

		public void setGain(int gain) 
		{
			this.gain = gain;
		}
		
		public int getReverb() 
		{
			return reverb;
		}

		public void setReverb(int reverb) 
		{
			this.reverb = reverb;
		}
		
		public int getFXmix() 
		{
			return fxmix;
		}

		public void setFxmix(int fxmix) 
		{
			this.fxmix = fxmix;
		}
		
		public int getContour() 
		{
			return contour;
		}

		public void setContour(int contour) 
		{
			this.contour = contour;
		}
		
		// Assign Band Presets - to current band 
		public void assignToPreset( int position, int station) {
			if(Levels.Clean.name.equals(this.selectedLevels)) {
				this.clean[position - 1] = station; 
			}
			else if(Levels.Distortion.name.equals(this.selectedLevels)) {
				this.distortion[position - 1] = station; 
			}
		}
		
		// Select from Preset - from current band 
		public void selectFromPreset(int position) {
			if(Levels.Clean.name.equals(this.selectedLevels)) {
				this.selectedLevels = this.clean[position - 1]; 
			}
			else if(Levels.Distortion.name.equals(this.selectedLevels)) {
				this.selectedLevels = this.distortion[position - 1]; 
			}
		}
		
		public String toString() 
		{
			return "Marshall Amp Instance: " + "[SerialNumber=" + serialNumber + 
					", power=" + power + ", Instrument=" + instrument + ", Master Volume="
					+ selectedVolume + ", Levels=" + selectedLevels  
					+ ", Tone=" + selectedTone + ",\n Clean Tone="
					+ Arrays.toString(clean) + " [Volume, Treble, Middle, Bass]" + "\n Distortion Tone="
					+ Arrays.toString(distortion) + "[Gain, Bass, Contour, Treble, Volume, FX Mix, Reverb] "+ "\n firstTimeOn=" + firstTimeOn
					+ ", lastTimeOn=" + lastTimeOn + //", Reverb=" + reverb
					//+ ", Bass Level=" + bass
					//+ ", Treble Level=" + treble + 
					"]\n";
			
		}
	public static void main(String[] args) 
	{
		Marshall marshall = new Marshall();  
		System.out.println("New Instance\n" + marshall + "\n");
		
		try 
		{
			marshall.on();
			System.out.println("Powered up\n" + marshall + "\n");
			
			// select FM Band & a station 
			marshall.setSelectedTone(Marshall.Levels.Clean.name); 
			marshall.setSelectedVolume(4);
			marshall.setSelectedLevels(8);
			System.out.println("Changed Channel\n" + marshall + "\n");
			marshall.setSelectedTone(Marshall.Levels.Distortion.name);
			marshall.setSelectedVolume(6);
			marshall.setSelectedLevels(8);
			
			// Turn off radio 
			marshall.off(); 
			System.out.println("Turned Off\n" + marshall + "\n");
						
			
		}

		catch(RadioException re ) 
		{
			System.err.println(re); 
		}
		
		finally 
		{
			System.out.println(marshall.getClass().getName() + " Completed Marshall Amp Test"); 
		}
	}

}
