package col106.assignment4.HashMap;
import java.util.Vector;
import java.util.ArrayList;

public class HashMap<V> implements HashMapInterface<V> {

	private String[] keys;
	private Object[] table;

	private int hashed(String key){
		int insertAt = 0;
		for(int i = key.length()-1; i >= 0; i--){
			insertAt = (key.charAt(i)+(41*insertAt))%table.length;
		}
		return insertAt;
	}

	public HashMap(int size) {
		// write your code here
		table = new Object[size];
		keys = new String[size];
	}

	public V put(String key, V value){
		// write your code here
		V oldValue = null;
		int insertAt = hashed(key);
		while(true){
			if(table[insertAt%table.length] == null){
				table[insertAt%table.length] = value;
				keys[insertAt%table.length] = key;
				break;
			}
			else if(table[insertAt%table.length] != null && keys[insertAt%table.length].equals(key)){
				oldValue = (V)table[insertAt%table.length];
				table[insertAt%table.length] = value;
				break;
			}
			else
				insertAt += 1;
		}
		return oldValue;
	}

	public V get(String key){
		// write your code here
		V output = null;
		int searchIndex = hashed(key);
		int counter = 0;
		while(table[searchIndex%table.length] != null && counter < table.length){
			if(keys[searchIndex%keys.length].equals(key)) {
				output = (V)table[searchIndex % table.length];
				break;
			}
			searchIndex += 1;
			counter++;
		}
		return output;
	}

	public boolean existsNullBetween(int index1, int index2){
		if(index1 == index2)
			return false;
		if(index1 < index2) {
			for (int i = index1; i < index2; i++) {
				if (keys[i % keys.length] == null)
					return true;
			}
		}
		else{
			for(int i = index1; i<keys.length; i++){
				if(keys[i%keys.length] == null)
					return true;
			}
			for(int i = 0; i<index2; i++){
				if(keys[i%keys.length] == null)
					return true;
			}
		}
		return false;
	}

	public boolean remove(String key){
		// write your code here
		boolean output = false;
		int searchIndex = hashed(key);
		if(contains(key)) {
			while (table[searchIndex % table.length] != null) {
				if (keys[searchIndex % keys.length].equals(key)) {
					keys[searchIndex % keys.length] = null;
					table[searchIndex % table.length] = null;
					output = true;
					break;
				}
				searchIndex += 1;
			}
		}
		searchIndex += 1;
		if(output == true){
			int i = 0;
			while(table[searchIndex%table.length] != null){
				i = (hashed(keys[searchIndex%keys.length]))%keys.length;
				if(existsNullBetween(i, searchIndex)) {
					String extractKey = keys[searchIndex % keys.length];
					V extractValue = (V)table[searchIndex%table.length];
					keys[searchIndex % keys.length] = null;
					table[searchIndex%table.length] = null;
					put(extractKey, extractValue);
				}
				searchIndex += 1;
			}
		}
		return output;
	}

	public boolean contains(String key){
		// write your code here
		boolean output = false;
		int searchIndex = hashed(key);
		int counter = 0;
		while(table[searchIndex%table.length] != null && counter < table.length){
			if(keys[searchIndex%keys.length].equals(key)) {
				output = true;
				break;
			}
			searchIndex += 1;
			counter++;
		}
		return output;
	}

	public Vector<String> getKeysInOrder(){
		// write your code here
		Vector<String> output = new Vector<>();
		for(int i = 0; i<keys.length; i++){
			if(keys[i] != null)
				output.add(keys[i]);
		}
		return output;
	}
}
