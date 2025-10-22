import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;


public class Trainer {

	static final char start1 = '^';
	static final char start2 = '_';
	static final char start3 = '`';
	static final char end1 = '{';
	static final char end2 = '|';
	static final char end3 = '}';
	static final int gram = 4;
	
	Trie males;
	Trie females;
	
	public Trainer(Trie m, Trie f)
	{
		males = m;
		females = f;
	}
	
	// Training file is in the form of comma separated values
	// Each line contains two values
	// First: sex (M or F)
	// Second: Name, a string beginning with an uppercase letter
	// followed by a sequence of lowercase letters

	public Set<String> train(String trainingFile) throws FileNotFoundException {
		FileInputStream fis = new FileInputStream(trainingFile);
		BufferedInputStream bis = new BufferedInputStream(fis);
		Scanner scanner = new Scanner(bis);
		
		Set<String> names = new TreeSet<String>();

		while (scanner.hasNextLine()) {
			String[] nextLine = scanner.nextLine().split(",");
			names.add(nextLine[1]);
//			System.out.println(nextLine[0] + ", " + nextLine[1]);
			String name = nextLine[1].toLowerCase();
			if (nextLine[0].equals("M"))
			{
				addFourGrams(males, name);
			}
			else if (nextLine[0].equals("F"))
			{
				addFourGrams(females, name);
			}
			else
			{
				System.err.println("Make sure each first value in file is M or F");
			}
		}
		scanner.close();
		return names;
	}

	private void addFourGrams(Trie trie, String name) {
		StringBuilder sb = new StringBuilder();
		sb.append(start1 + "" + start2 + ""+ start3+ "" +name + end1 + end2 + end3);
//		System.out.println(sb.toString());
		String str = sb.toString();
		for (int i = 0; i < str.length() - (gram - 1); i++)
		{
//			System.out.println(str.substring(i, i + gram));
			trie.add(str.substring(i, i + gram));
		}
		
	}
	
	
}
