package spelling;

import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;

/** 
 * A trie data structure that implements the Dictionary and the AutoComplete ADT
 * @author Rajmohan Asokan
 *
 */
public class AutoCompleteDictionaryTrie implements  Dictionary, AutoComplete {

    private TrieNode root;
    private int size;
    

    public AutoCompleteDictionaryTrie()
	{
		root = new TrieNode();
	}
	
	
	/** Insert a word into the trie.
	 * For the basic part of the assignment (part 2), you should convert the 
	 * string to all lower case before you insert it. 
	 * 
	 * This method adds a word by creating and linking the necessary trie nodes 
	 * into the trie, as described outlined in the videos for this week. It 
	 * should appropriately use existing nodes in the trie, only creating new 
	 * nodes when necessary. E.g. If the word "no" is already in the trie, 
	 * then adding the word "now" would add only one additional node 
	 * (for the 'w').
	 * 
	 * @return true if the word was successfully added or false if it already exists
	 * in the dictionary.
	 */
	public boolean addWord(String word)
	{
	    //TODO: Implement this method.
		boolean isAdded = false;
		TrieNode newNode = null;
		if(!word.isEmpty()) {
			int length = word.length();
			int iter = 0;			
			char[] charArr = word.toLowerCase().toCharArray();
			if(root.getChild(charArr[0]) == null) {
				newNode = root.insert(charArr[0]);
				iter++;
			}
			else {
				newNode = root.getChild(charArr[0]);
				iter++;
			}						
			while(iter < length && newNode.getChild(charArr[iter]) != null) {
				newNode = newNode.getChild(charArr[iter]);
				iter++;
			}
			while(iter < length) {
				newNode = newNode.insert(charArr[iter]);
				iter++;
			}
			if(!newNode.endsWord()) {
				isAdded = true;				
			}
		}
		if(isAdded) {
			newNode.setEndsWord(true);
			size++;
		}
	    return isAdded;
	}
	
	/** 
	 * Return the number of words in the dictionary.  This is NOT necessarily the same
	 * as the number of TrieNodes in the trie.
	 */
	public int size()
	{
	    //TODO: Implement this method
	    return this.size;
	}
	
	
	/** Returns whether the string is a word in the trie, using the algorithm
	 * described in the videos for this week. */
	@Override
	public boolean isWord(String s) 
	{
	    // TODO: Implement this method
		int iter = 0;
		boolean wordFlag = false;
		char[] charArr = s.toLowerCase().toCharArray();
		int length = s.length();
		TrieNode newNode = root;
		while(iter < length && newNode.getChild(charArr[iter]) != null) {
			if(iter == length - 1) {
				wordFlag = newNode.getChild(charArr[iter]).endsWord();
				break;
			}
			newNode = newNode.getChild(charArr[iter]);
			iter++;
		}				
		return wordFlag;
	}

	/** 
     * Return a list, in order of increasing (non-decreasing) word length,
     * containing the numCompletions shortest legal completions 
     * of the prefix string. All legal completions must be valid words in the 
     * dictionary. If the prefix itself is a valid word, it is included 
     * in the list of returned words. 
     * 
     * The list of completions must contain 
     * all of the shortest completions, but when there are ties, it may break 
     * them in any order. For example, if there the prefix string is "ste" and 
     * only the words "step", "stem", "stew", "steer" and "steep" are in the 
     * dictionary, when the user asks for 4 completions, the list must include 
     * "step", "stem" and "stew", but may include either the word 
     * "steer" or "steep".
     * 
     * If this string prefix is not in the trie, it returns an empty list.
     * 
     * @param prefix The text to use at the word stem
     * @param numCompletions The maximum number of predictions desired.
     * @return A list containing the up to numCompletions best predictions
     */@Override
     public List<String> predictCompletions(String prefix, int numCompletions) 
     {
    	 // TODO: Implement this method
    	 // This method should implement the following algorithm:
    	 // 1. Find the stem in the trie.  If the stem does not appear in the trie, return an
    	 //    empty list
    	 // 2. Once the stem is found, perform a breadth first search to generate completions
    	 //    using the following algorithm:
    	 //    Create a queue (LinkedList) and add the node that completes the stem to the back
    	 //       of the list.
    	 //    Create a list of completions to return (initially empty)
    	 //    While the queue is not empty and you don't have enough completions:
    	 //       remove the first Node from the queue
    	 //       If it is a word, add it to the completions list
    	 //       Add all of its child nodes to the back of the queue
    	 // Return the list of completions
    	 Queue<TrieNode> queueNode = new LinkedList<TrieNode>();
    	 List<String> suggestions = new LinkedList<String>();
    	 TrieNode tempNode = null;
    	 int numWords = 0;
    	 if(prefix.length() > 0 && root.getChild(prefix.charAt(0)) != null) {
    		 tempNode = root.getChild(prefix.charAt(0));
    	 }
    	 else if(prefix.isEmpty() && numCompletions > 0){
    		 tempNode = root;
    	 }
    	 else {
    		 return suggestions;
    	 }
    	 
    	 for(int i = 1; i < prefix.length(); i++) {
    		 if(tempNode.getChild(prefix.charAt(i)) != null) {
    			 tempNode = tempNode.getChild(prefix.charAt(i));    			 
    		 }
    		 else {
    			 return suggestions;
    		 }    		 
    	 }
    	 if(tempNode.endsWord()) {
    		 suggestions.add(tempNode.getText());
    		 numWords++;
    	 }
    	 
		 for (Character c : tempNode.getValidNextCharacters()) {
	 			queueNode.add(tempNode.getChild(c));	 			
	 	 }
		 while(!queueNode.isEmpty() && numWords < numCompletions) {
			 TrieNode front = queueNode.poll();
			 if(front.endsWord()) {
				 suggestions.add(front.getText());
				 numWords++;
			 }
			 for (Character c : front.getValidNextCharacters()) {
		 			queueNode.add(front.getChild(c));	 			
		 	 }
		 }
    	 
         return suggestions;
     }

 	// For debugging
 	public void printTree()
 	{
 		printNode(root);
 	}
 	
 	/** Do a pre-order traversal from this node down */
 	public void printNode(TrieNode curr)
 	{
 		if (curr == null) 
 			return;
 		
 		System.out.println(curr.getText());
 		
 		TrieNode next = null;
 		for (Character c : curr.getValidNextCharacters()) {
 			next = curr.getChild(c);
 			printNode(next);
 		}
 	}
 	

	
}