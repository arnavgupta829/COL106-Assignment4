package col106.assignment4.HashMap;

public class WordCounter {

	private HashMap<Integer> hm;

	public WordCounter(){
		// write your code here
		hm = new HashMap<Integer>(200);
	}

	public int count(String str, String word){
		// write your code here
		int output = 0;
		int totalCases = str.length() - word.length() + 1;
		String finder = "";
		for(int i = 0; i<totalCases; i++){
			finder = str.substring(i, i+word.length());
			if(hm.contains(finder))
				hm.put(finder, hm.get(finder)+1);
			else
				hm.put(finder, 1);
		}
		if(hm.contains(word))
			output = hm.get(word);
		hm = new HashMap<Integer>(200);
		return output;
	}
}
