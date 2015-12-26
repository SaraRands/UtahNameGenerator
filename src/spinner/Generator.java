package spinner;
import java.util.Set;

// The driver class

public class Generator {
	
	static final char start1 = '^';

	public static void main(String[] args) {
		
		BigSpinner bigSpinner = new BigSpinner(args[0]);
		
		Spinner maleSpinner = bigSpinner.getMaleNames();
		Spinner femaleSpinner = bigSpinner.getFemaleNames();
		Set<String> names = bigSpinner.getNames();
		
		String boyName = maleSpinner.generate();
		String girlName = femaleSpinner.generate();

		while (names.contains(boyName))
		{
			boyName = maleSpinner.generate();
		}
		while (names.contains(girlName))
		{
			girlName = femaleSpinner.generate();
		}
		System.out.println("New male name:   " + boyName);
		System.out.println("New female name: " + girlName);
	}
}
