package spinner;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;


public class BigSpinner {
	
	private Spinner maleNames;
	private Spinner femaleNames;
	private Set<String> names;
	
	public BigSpinner(Spinner male, Spinner female, Set<String> n)
	{
		maleNames = male;
		femaleNames = female;
		names = n;
	}

	public BigSpinner(String file) {
		parse(file);
	}

	private void parse(String file) {
		Map<String, List<FourGram>> males = new TreeMap<String, List<FourGram>>();
		Map<String, List<FourGram>> females = new TreeMap<String, List<FourGram>>();
		names = new TreeSet<String>();
		
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		BufferedInputStream bis = new BufferedInputStream(fis);
		Scanner scanner = new Scanner(bis);
		
		// First line is "Males"
		String trash = scanner.nextLine();
		String threegram = null;
		// Next is a series of list of fourgrams
		String next = scanner.nextLine();
		while (!next.equals(""))
		{
			List<FourGram> list = new ArrayList<FourGram>();
			while (!next.equals(""))
			{
				
				String[] nextLine = next.split(",");
				threegram = nextLine[0];
				String four = nextLine[1];
				double logprob = Double.parseDouble(nextLine[2]);
				double cumprob = Double.parseDouble(nextLine[3]);
				FourGram fourgram = new FourGram(four, logprob, cumprob);
				list.add(fourgram);
				next = scanner.nextLine();
			}
			males.put(threegram, list);
			next = scanner.nextLine();
		}

		trash = scanner.nextLine();
		// Series of lists of fourgrams
		next = scanner.nextLine();
		while (!next.equals(""))
		{
			List<FourGram> list = new ArrayList<FourGram>();
			while (!next.equals(""))
			{
				String[] nextLine = next.split(",");
				threegram = nextLine[0];
				String four = nextLine[1];
				double logprob = Double.parseDouble(nextLine[2]);
				double cumprob = Double.parseDouble(nextLine[3]);
				FourGram fourgram = new FourGram(four, logprob, cumprob);
				list.add(fourgram);
				next = scanner.nextLine();
			}
			females.put(threegram, list);
			next = scanner.nextLine();
		}
		// "Names"
		trash = scanner.nextLine();
		// Series of names
		while (scanner.hasNextLine())
		{
			names.add(scanner.nextLine());
		}
		
		scanner.close();
		maleNames = new Spinner(males);
		femaleNames = new Spinner(females);
	}
		

	public Spinner getMaleNames() {
		return maleNames;
	}

	public void setMaleNames(Spinner maleNames) {
		this.maleNames = maleNames;
	}

	public Spinner getFemaleNames() {
		return femaleNames;
	}

	public void setFemaleNames(Spinner femaleNames) {
		this.femaleNames = femaleNames;
	}

	public Set<String> getNames() {
		return names;
	}

	public void setNames(Set<String> names) {
		this.names = names;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Males\n" + maleNames.toString() + "\n");
		sb.append("Females\n" + femaleNames.toString() + "\n");
		sb.append("Names");
		for (String n : names)
		{
			sb.append(n + "\n");
		}
		return sb.toString();
	}
}
