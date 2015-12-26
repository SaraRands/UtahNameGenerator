package spinner;

import java.util.Scanner;
import java.util.Set;


// The driver class
public class Generator {

	public static void main(String[] args) {
		String spinner = "Utah2000-2014Spinner.txt";
		if (args.length > 0) {
			spinner = args[0];
		}
		
		BigSpinner bigSpinner = new BigSpinner(spinner);
		
		Spinner maleSpinner = bigSpinner.getMaleNames();
		Spinner femaleSpinner = bigSpinner.getFemaleNames();
		Set<String> names = bigSpinner.getNames();

		System.out.println("Generate your very own Utah name!");
		Scanner input = new Scanner(System.in);
		boolean unique = true; 
		boolean again = true;
		while (again) {
			String boyName = maleSpinner.generate();
			String girlName = femaleSpinner.generate();
			
			if (unique) {
				while (names.contains(boyName))
				{
					boyName = maleSpinner.generate();
				}
				while (names.contains(girlName))
				{
					girlName = femaleSpinner.generate();
				}
			}
			System.out.println("\t\tGirl:\t" + girlName);
			System.out.println("\t\tBoy:\t" + boyName);
			System.out.print("More? ");
			again = getInput(input);
		}
		input.close();
	}

	private static boolean getInput(Scanner input) {
		boolean ret = false;
		boolean badInput = true;
		while (badInput) {
			System.out.print("Yes or No:  ");
			String yn = input.next().toLowerCase();
			if (yn.startsWith("y")) {
				ret = true;
				badInput = false;
			}
			else if (yn.startsWith("n")) {
				badInput = false;
			}
			else {
				System.out.print("Bad input. ");
			}
		}
		return ret;
	}
}