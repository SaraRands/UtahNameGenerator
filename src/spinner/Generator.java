package spinner;

import java.util.Scanner;
import java.util.Set;
import java.util.*;
import java.util.Map.Entry;
import java.io.FileNotFoundException;
import java.io.PrintWriter;


// The driver class
public class Generator {

	private static final int DRAWS_PER_GENDER = 500_000; // adjust as you like


	public static void main(String[] args) {
		String spinner = "Utah2000-2024Spinner.txt";
		if (args.length > 0) {
			spinner = args[0];
		}
		
		BigSpinner bigSpinner = new BigSpinner(spinner);
		
		Spinner maleSpinner = bigSpinner.getMaleNames();
		Spinner femaleSpinner = bigSpinner.getFemaleNames();
		Set<String> disallowed = bigSpinner.getNames();

        // --- New: sample to get empirical weights and write names.json ---
        Map<String,Integer> girlCounts = sampleCounts(femaleSpinner, disallowed, DRAWS_PER_GENDER);
        Map<String,Integer> boyCounts  = sampleCounts(maleSpinner, disallowed, DRAWS_PER_GENDER);

        writeCombinedJSON("names.json", girlCounts, boyCounts);

        System.out.printf("names.json: %d unique girls, %d unique boys%n",
                girlCounts.size(), boyCounts.size());
 
		// System.out.println("Generate your very own Utah name!");
		// Scanner input = new Scanner(System.in);
		// boolean unique = true; 
		// boolean again = true;
		// while (again) {
		// 	String boyName = maleSpinner.generate();
		// 	String girlName = femaleSpinner.generate();
			
		// 	if (unique) {
		// 		while (names.contains(boyName))
		// 		{
		// 			boyName = maleSpinner.generate();
		// 		}
		// 		while (names.contains(girlName))
		// 		{
		// 			girlName = femaleSpinner.generate();
		// 		}
		// 	}
		// 	System.out.println("\t\tGirl:\t" + girlName);
		// 	System.out.println("\t\tBoy:\t" + boyName);
		// 	System.out.print("More? ");
		// 	again = getInput(input);
		// }
		// input.close();
	}

    private static Map<String,Integer> sampleCounts(Spinner spinner, Set<String> disallowed, int draws) {
        Map<String,Integer> counts = new HashMap<>(64_000);
        for (int i = 0; i < draws; i++) {
            String name = generateUnique(spinner, disallowed, draws);        // no args; already a final name
            if (name == null) continue;
            // name = name.trim();
            if (name.isEmpty()) continue;
            counts.merge(name, 1, Integer::sum);
        }
        return counts;
    }

	private static String generateUnique(Spinner spinner, Set<String> disallowed, int maxTries) {
	    for (int i = 0; i < maxTries; i++) {
	        String name = spinner.generate();          // returns final display-ready name
	        if (name == null) continue;
	        name = name.trim();
	        if (name.isEmpty()) continue;
	        if (!disallowed.contains(name)) {
	            return name;                           // unique vs original SSA list
	        }
	    }
	    return null; // gave up after maxTries
	}

    private static void writeCombinedJSON(String filename,
                                          Map<String,Integer> girlCounts,
                                          Map<String,Integer> boyCounts) {
        try (PrintWriter out = new PrintWriter(filename)) {
            out.println("{");
            out.println("  \"names\": [");

            boolean first = true;

            for (Entry<String,Integer> e : sortByWeight(girlCounts)) {
                if (!first) out.println(",");
                out.print("    { \"name\": \"" + escape(e.getKey())
                          + "\", \"sex\": \"F\", \"w\": " + e.getValue() + " }");
                first = false;
            }
            for (Entry<String,Integer> e : sortByWeight(boyCounts)) {
                if (!first) out.println(",");
                out.print("    { \"name\": \"" + escape(e.getKey())
                          + "\", \"sex\": \"M\", \"w\": " + e.getValue() + " }");
                first = false;
            }

            out.println();
            out.println("  ]");
            out.println("}");
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Could not write " + filename, e);
        }
    }

    private static List<Entry<String,Integer>> sortByWeight(Map<String,Integer> counts) {
        List<Entry<String,Integer>> list = new ArrayList<>(counts.entrySet());
        list.sort((a, b) -> {
            int c = Integer.compare(b.getValue(), a.getValue()); // weight desc
            return (c != 0) ? c : a.getKey().compareTo(b.getKey()); // tie-break by name
        });
        return list;
    }

    private static String escape(String s) {
        return s.replace("\\", "\\\\").replace("\"", "\\\"");
    }


	// private static boolean getInput(Scanner input) {
	// 	boolean ret = false;
	// 	boolean badInput = true;
	// 	while (badInput) {
	// 		System.out.print("Yes or No:  ");
	// 		String yn = input.next().toLowerCase();
	// 		if (yn.startsWith("y")) {
	// 			ret = true;
	// 			badInput = false;
	// 		}
	// 		else if (yn.startsWith("n")) {
	// 			badInput = false;
	// 		}
	// 		else {
	// 			System.out.print("Bad input. ");
	// 		}
	// 	}
	// 	return ret;
	// }
}

