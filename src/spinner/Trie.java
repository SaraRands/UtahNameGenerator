import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

// This trie differs from a classic trie
// in that instead of storing strings of 26 letters,
// it stores strings that contain 3 start characters
// and 3 end characters.

// The names are stored as lower-case strings
// because the Social Security data's strings 
// all contain an initial capital letter and 
// all lowercase letters following 
// (no punctuation, no other capital letters).
// Preprocessing removes the initial capital letter
// because it doesn't yield any extra information.

// The start and end characters were chosen by 
// the characters immediately before and after the
// lowercase ASCII characters:
// First start character = start1 = '^'
// Second start character = start2 = '_'
// Third start character = start3 = '`'
// First end character = end1 = '{'
// Second end character = end2 = '|'
// Third end character = end3 = '}'
// Characters are converted to indexes by subtracting
// the int value of the first start character '^'.

// This trie stores 4grams created from each name.
// The name "Bob" is entered as ^_`b, _`bo, `bob, bob{, ob{|, b{|}.
// Counts are needed at the 3rd and 4th character of each 4gram
// (but I'll keep counts at 1st and 2nd char just in case it's needed).

// Not sure if the trie needs a nodeCount still but 
// again I'll keep it just in case.

// Don't think we need the find function

// Spinner function:
// Given start1 symbol, return a fourgram
// The last character of the fourgram is the next character of the name.
// The second character of the fourgram is the given character of the next fourgram.
// There's a spinner corresponding with each initial node 
// (except for the end symbols, because no fourgram begins with an end symbol).


public class Trie {

	static final int NUM_LETTERS = 32;
	static final char start1 = '^';
	static final char start2 = '_';
	static final char start3 = '`';
	static final char end1 = '{';
	static final char end2 = '|';
	static final char end3 = '}';
	
	Node root;
	int wordCount; // number fourGrams
//	double cumProbSoFar = Double.NEGATIVE_INFINITY;
	
	public Trie() {
		root = new Node();
		wordCount = 0;
	}
	
	public void add(String word) {
		if (word.length() == 0 || word == null) {
			return;
		}
//		System.out.println("Trie add method: word = " + word);
//		String w = word.toLowerCase();
		int index = word.charAt(0) - (int) start1;
		if (root.children[index] == null) {
			root.children[index] = new Node();
		}
//		// Change to keep count at each node
//		else {
//			root.children[index].count++;
//		}
		root.children[index].addR(word.substring(1));
	}

	// Return a map each threegram mapped to each associated fourgram
	// Call this method only after training is done and counts are set
	public Map<String, List<FourGram>> getFourGrams()
	{
		Map<String, List<FourGram>> fourgrams = new TreeMap<String, List<FourGram>>();
		String[] strings = this.toString().split("\n");
		String threegram = null;
		List<FourGram> list = null;
		double cumProbSoFar = 0;
		for (String s : strings)
		{
			String[] fourg = s.split(" ");
			String string = fourg[0];
			String newThreegram = string.substring(0, 3);
			int num = Integer.parseInt(fourg[1]);
			int denom = Integer.parseInt(fourg[2]);

			double logprob = Math.log((double) num / (double) denom);
			// if it's a new threegram, start a new list and cumprob
			if (!newThreegram.equals(threegram))
			{
				threegram = newThreegram;
				cumProbSoFar = Double.NEGATIVE_INFINITY;
				cumProbSoFar = logAdd(cumProbSoFar, logprob);
				FourGram fourgram = new FourGram(string, logprob, cumProbSoFar);
				list = new ArrayList<FourGram>();
				list.add(fourgram);
				fourgrams.put(threegram, list);
			}
			else 
			{
				cumProbSoFar = logAdd(cumProbSoFar, logprob);
				FourGram fourgram = new FourGram(string, logprob, cumProbSoFar);
				list = fourgrams.get(threegram);
				list.add(fourgram);
				fourgrams.put(threegram, list);
			}
		}
		return fourgrams;
	}

	public int getWordCount() {
		return wordCount;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < NUM_LETTERS; i++) {
			if (root.children[i] != null) {
				String s = Character.toString((char) (i + start1));
				sb.append(root.children[i].toStringR(s));
			}
		}
		return sb.toString();
	}
	
	// Returns all strings that begin with the specified character
	public String toString(char c) {
		StringBuilder sb = new StringBuilder();
		int i = c - start1;
		if (root.children[i] != null) {
			String s = Character.toString((char) (i + start1));
			sb.append(root.children[i].toStringR(s));
		}
		return sb.toString();
	}
	
	private double logAdd(double logX, double logY) {
		// make x the max
		if (logY > logX)
		{
			double temp = logX;
			logX = logY;
			logY = temp;
		}
		// now x is bigger
		if (logX == Double.NEGATIVE_INFINITY)
		{
			return logX;
		}
		double negDiff = logY - logX;
		if (negDiff < -20) {
			return logX;
		}
		return logX + Math.log(1.0 + Math.exp(negDiff));
	}

	public class Node {
		static final int NUM_LETTERS = 32;
		Node[] children;
		int count;
		
		public Node() {
			children = new Node[NUM_LETTERS];
			count = 0;
		}

		public FourGram[] getFourGrams(StringBuilder sb) {
			List<FourGram> fourgrams = new ArrayList<FourGram>();
			for (int i = 0; i < NUM_LETTERS; i++)
			{
				if (this.children[i] != null) {
					String prefix = sb.toString() + ((char) (i + start1));
					sb.append(this.children[i].toStringR(prefix));
				}
			}
			return null;
		}

		public String toStringR(String s) {
			StringBuilder sb = new StringBuilder();
			// If the word is complete,
			// append the count of the current node and the previous node
//			if (count > 0) {
//				sb.append(s + " " + count + "\n");
//			}//			if (count > 0) {
//			sb.append(s + " " + count + "\n");
//		}
			
			// case: the incoming string has 1 or 2 characters
			if (s.length() < 3)
			{
				for (int i = 0; i < NUM_LETTERS; i++) {
					if (this.children[i] != null) {
						String prefix = s + ((char) (i + start1));
						sb.append(this.children[i].toStringR(prefix));
					}
				}
				return sb.toString();
			}
			
			// case: the incoming string has 3 characters
			if (s.length() == 3)
			{
				for (int i = 0; i < NUM_LETTERS; i++)
				{
					if (this.children[i] != null)
					{
						String prefix = s + ((char) (i + start1));
						sb.append(prefix + " " + this.children[i].count + " "
								+ this.count + "\n");
											}
				}
			}
			return sb.toString();
		}

		public void addR(String word) {
			// Change because word count is kept at each char:
			count++;
			if (word.length() == 0) {
				wordCount++;
				return;
			}
			System.out.println(word);
//			System.out.println()
			int index = word.charAt(0) - (int) start1;
			if (this.children[index] == null) {
				this.children[index] = new Node();
			}
			this.children[index].addR(word.substring(1));
		}

		public int getValue() {
			return count;
		}
	}
}
