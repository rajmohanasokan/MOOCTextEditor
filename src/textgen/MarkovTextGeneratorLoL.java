package textgen;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Random;

/** 
 * An implementation of the MTG interface that uses a list of lists.
 * @author UC San Diego Intermediate Programming MOOC team 
 */
public class MarkovTextGeneratorLoL implements MarkovTextGenerator {

	// The list of words with their next words
	private List<ListNode> wordList; 
	
	private Map<String, ListNode> wordMatch;
	
	// The starting "word"
	private String starter;
	
	// The random number generator
	private Random rnGenerator;
	
	public MarkovTextGeneratorLoL(Random generator)
	{
		wordList = new LinkedList<ListNode>();
		starter = "";
		rnGenerator = generator;
	}
	
	
	/** Train the generator by adding the sourceText */
	@Override
	public void train(String sourceText)
	{
		// TODO: Implement this method
		if(wordList.isEmpty()){
			String[] wordsInText = sourceText.split("\\s+");
			/*for(String s : wordsInText){
				System.out.println(s);
			}*/
			wordMatch = new HashMap<String, ListNode>();
			starter = wordsInText[0];
			String prevWord = wordsInText[0];
			String currWord;		
			for(int i = 0; i < wordsInText.length; i++){
				if(i == 0){
					ListNode node = new ListNode(starter);
					wordMatch.put(starter, node);				
				}else{
					currWord = wordsInText[i];
					/*if(wordMatch.containsKey(prevWord)){
						ListNode node = wordMatch.get(prevWord);
						node.addNextWord(currWord);
						wordMatch.put(prevWord, node);					
					}else{
						ListNode node = new ListNode(prevWord);
						node.addNextWord(currWord);
						wordMatch.put(prevWord, node);
					}*/
					addWordToList(wordMatch, prevWord, currWord);
					prevWord = currWord;
				}
			}
			/*if(wordMatch.containsKey(prevWord)){
				ListNode node = wordMatch.get(prevWord);
				node.addNextWord(starter);
				wordMatch.put(prevWord, node);					
			}else{
				ListNode node = new ListNode(prevWord);
				node.addNextWord(starter);
				wordMatch.put(prevWord, node);
			}*/	
			addWordToList(wordMatch, prevWord, starter);
			
			for(Map.Entry<String, ListNode> entry : wordMatch.entrySet()){
				wordList.add(entry.getValue());
			}
		}
		
	}
	
	private void addWordToList(Map<String, ListNode> tempWordMatch, String prevWord, String currWord){
		if(tempWordMatch.containsKey(prevWord)){
			ListNode node = tempWordMatch.get(prevWord);
			node.addNextWord(currWord);
			tempWordMatch.put(prevWord, node);					
		}else{
			ListNode node = new ListNode(prevWord);
			node.addNextWord(currWord);
			tempWordMatch.put(prevWord, node);
		}
	}
	
	/** 
	 * Generate the number of words requested.
	 */
	@Override
	public String generateText(int numWords) {
	    // TODO: Implement this method
		if(wordList.isEmpty() || numWords == 0){
			return "";
		}
		rnGenerator = new Random();
		String currWord = starter;
		StringBuilder output = new StringBuilder();
		output.append(currWord);
		numWords--;
		while(numWords > 0){
			ListNode node = wordMatch.get(currWord);
			String newWord = node.getRandomNextWord(rnGenerator);
			currWord = newWord;
			output.append(" ");
			output.append(newWord);
			numWords--;
		}		
		return output.toString();
	}
	
	
	// Can be helpful for debugging
	@Override
	public String toString()
	{
		String toReturn = "";
		for (ListNode n : wordList)
		{
			toReturn += n.toString();
		}
		return toReturn;
	}
	
	/** Retrain the generator from scratch on the source text */
	@Override
	public void retrain(String sourceText)
	{
		// TODO: Implement this method.
		wordList = new LinkedList<ListNode>();
		train(sourceText);		
	}
	
	// TODO: Add any private helper methods you need here.
	
	
	/**
	 * This is a minimal set of tests.  Note that it can be difficult
	 * to test methods/classes with randomized behavior.   
	 * @param args
	 */
	public static void main(String[] args)
	{
		// feed the generator a fixed random value for repeatable behavior
		MarkovTextGeneratorLoL gen = new MarkovTextGeneratorLoL(new Random(42));
		String textString = "Hello.  Hello there.  This is a test.  Hello there.  Hello Bob.  Test again.";
		System.out.println(textString);
		gen.train(textString);
		System.out.println(gen.toString());
		System.out.println(gen.generateText(20));
		String textString2 = "You say yes, I say no, "+
				"You say stop, and I say go, go, go, "+
				"Oh no. You say goodbye and I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello. "+
				"I say high, you say low, "+
				"You say why, and I say I don't know. "+
				"Oh no. "+
				"You say goodbye and I say hello, hello, hello. "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello. "+
				"Why, why, why, why, why, why, "+
				"Do you say goodbye. "+
				"Oh no. "+
				"You say goodbye and I say hello, hello, hello. "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello. "+
				"You say yes, I say no, "+
				"You say stop and I say go, go, go. "+
				"Oh, oh no. "+
				"You say goodbye and I say hello, hello, hello. "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello, hello, hello,";
		System.out.println(textString2);
		gen.retrain(textString2);
		System.out.println(gen);
		System.out.println(gen.generateText(20));
	}

}

/** Links a word to the next words in the list 
 * You should use this class in your implementation. */
class ListNode
{
    // The word that is linking to the next words
	private String word;
	
	// The next words that could follow it
	private List<String> nextWords;
	
	ListNode(String word)
	{
		this.word = word;
		nextWords = new LinkedList<String>();
	}
	
	public String getWord()
	{
		return word;
	}

	public void addNextWord(String nextWord)
	{
		nextWords.add(nextWord);
	}
	
	public String getRandomNextWord(Random generator)
	{
		// TODO: Implement this method
	    // The random number generator should be passed from 
	    // the MarkovTextGeneratorLoL class
		int i = generator.nextInt(nextWords.size()); 
	    return nextWords.get(i);
	}

	public String toString()
	{
		String toReturn = word + ": ";
		for (String s : nextWords) {
			toReturn += s + "->";
		}
		toReturn += "\n";
		return toReturn;
	}
	
}


