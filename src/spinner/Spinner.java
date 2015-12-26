package spinner;

import java.util.List;
import java.util.Map;
import java.util.Random;

public class Spinner {
	
	static final int NUM_LETTERS = 32;
	static final char start1 = '^';
	static final char start2 = '_';
	static final char start3 = '`';
	static final char end1 = '{';
//	static final char end2 = '|';
//	static final char end3 = '}';
	
	Map<String, List<FourGram>> spinner;
		
	public Spinner(Map<String, List<FourGram>> s)
	{
		spinner = s;
	}
	
	// From the results of the spin method
	// The second character is the given for the next fourgram
	// The fourth character is the next character in the generated name
	// If the fourth character is the end character, the name is done
	public String generate()
	{
		StringBuilder generatedName = new StringBuilder();
		String threegram = start1 + "" + start2 + "" + start3;
		Character finalChar = start3;
		while (!finalChar.equals(end1))
		{
			String fourgram = spin(threegram);
			threegram = fourgram.substring(1);
			finalChar = fourgram.charAt(3);
			if (finalChar.equals(end1))
			{
				break;
			}
			// Make the character capital if it is the first letter
			if (generatedName.length() == 0)
			{
				generatedName.append(Character.toUpperCase(finalChar));
			}
			else {
				generatedName.append(finalChar);
			}
		}	
		return generatedName.toString();
	}
	
	// Given the threegram
	// Returns the FourGram string
	public String spin(String threegram)
	{
		// Generate a random double between 0 and 1
		// Take the log
		Random r = new Random();
		double number = Math.log(r.nextDouble());
		// Compare to the spinner[]
		// Return the associated string
		for (FourGram f : spinner.get(threegram))
		{
			if (number < f.getCumprob())
			{
				return f.getFourgram();
			}
		}
		return "probabilities don't add to 1";
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (Map.Entry<String, List<FourGram>> e : spinner.entrySet())
		{	
			for (FourGram f : e.getValue())
			{
				sb.append(e.getKey() + "," + f.getFourgram() + "," + 
						f.getLogprob() + "," + f.getCumprob() + "\n");
			}
			sb.append("\n");
		}
		return sb.toString();
	}
}
