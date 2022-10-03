import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class HashMarkov implements MarkovInterface {
    protected String[] myWords;		// Training text split into array of words 
	protected Random myRandom;		// Random number generator
	protected int myOrder;			// Length of WordGrams used
    protected HashMap<WordGram, List<String>> myMap; // Map WordGrams with lists of words that follow
    
	/**
	 * Default constructor creates order 2 model
	 */
	public HashMarkov() {
		this(2);
	}


	/**
	 * Initializes a model of given order and random number generator.
	 * @param order Number of words used to generate next 
	 * random word / size of WordGrams used.
	 */
	public HashMarkov(int order){
		myOrder = order;
		myRandom = new Random();
		myMap = new HashMap<WordGram, List<String>>();
	}
	

	/**
	 * Initializes training text. Should always be called prior to
	 * random text generation.
	 */
	@Override
	public void setTraining(String text){
		myWords = text.split("\\s+");
		//myMap = new HashMap<>();
		//if(myMap != null) 
		myMap.clear();
		for(int i=0;i<myWords.length-myOrder;i++) {
			WordGram wg=new WordGram(myWords, i, myOrder);
			//HashMarkov.addMap(myMap, wg, myWords[i+myOrder]);
			ArrayList<String> temp = new ArrayList<>();
			myMap.putIfAbsent(wg, temp);
			myMap.get(wg).add(myWords[i+myOrder]);
			}
		System.out.println(myMap);
		}
	/*
	public static void addMap(HashMap<WordGram, List<String>> map, WordGram wg, String text){
		ArrayList<String> temp = new ArrayList<>();
		if(!map.containsKey(wg))map.put(wg, temp);
		map.get(wg).add(text);

		
	}
	*/
	
	@Override
	public List<String> getFollows(WordGram wgram) {
		//System.out.println(wgram);
		if(myMap.containsKey(wgram)){
			//System.out.println(myMap.get(wgram));
			return myMap.get(wgram);
		}
		else{
			List<String> empty = new ArrayList<>();
			//System.out.println(empty);
			return empty;
		}
	}

	private String getNext(WordGram wgram) {
		List<String> follows = getFollows(wgram);
		//System.out.println(follows.size());
		if (follows.size() != 0) {
			int randomIndex = myRandom.nextInt(follows.size());
			return follows.get(randomIndex);
		}
		int randomIndex = myRandom.nextInt(myWords.length);
		System.out.println(randomIndex);
		System.out.println(myWords[randomIndex]);
		return myWords[randomIndex];
		}
		

	@Override

	public String getRandomText(int length) {
		
		//ArrayList<String> randomWords = new ArrayList<>(length);

		int index = myRandom.nextInt(myWords.length - myOrder + 1);
		WordGram current = new WordGram(myWords,index,myOrder);
		List<String> randomWords = new ArrayList<>();
		randomWords.add(current.toString());
		for(int k=0; k < length-myOrder; k ++) {
			String nextWord = getNext(current);
			randomWords.add(nextWord);
			current = current.shiftAdd(nextWord);
		}
		return String.join(" ", randomWords);
	}
	
		
		 

	/* 
	public String getRandomText(int length) {
		int index = myRandom.nextInt(myWords.length - myOrder + 1);
		WordGram current = new WordGram(myWords,index,myOrder);
		ArrayList<String> randomWords = new ArrayList<>(length);
		//randomWords.add(current.toString());

		for(int k=0; k < length-myOrder; k += 1) {
			int ind = myRandom.nextInt(myWords.length);
			List<String> temp = myMap.get(current);
			if(temp != null){
			String nextWord = temp.get(ind);
			randomWords.add(nextWord);
			current = current.shiftAdd(nextWord);
			}else{
			String nextWord = myWords[ind];
			randomWords.add(nextWord);
			current = current.shiftAdd(nextWord);
			}
		}
		return String.join(" ", randomWords);
		}
		*/
			
		/* 
		StringBuilder sb = new StringBuilder();
		int index = myRandom.nextInt(myText.length() - myOrder + 1);

		String current = myText.substring(index, index + myOrder);
		sb.append(current);
		
		for(int k=0; k < length-myOrder; k += 1){
			ArrayList<String> follows = getFollows(current);
			if (follows.size() == 0){
				break;
			}
			index = myRandom.nextInt(follows.size());
			
			String nextItem = follows.get(index);

			sb.append(nextItem);
			current = current.substring(1)+ nextItem;
		}		
		return sb.toString();
		*/

	


	@Override
	public int getOrder() {
		// TODO Auto-generated method stub
		return myOrder;
	}


	@Override
	public void setSeed(long seed) {
		// TODO Auto-generated method stub
		myRandom.setSeed(seed);
	}
}



