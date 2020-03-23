package col106.assignment4.Map;
import col106.assignment4.WeakAVLMap.WeakAVLMap;
import col106.assignment4.HashMap.HashMap;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.time.Instant;

public class Map<V> {

	static PrintStream out;
	private WeakAVLMap<String, String> wavl;
	private HashMap<String> hm;

	public Map() {
		// write your code here
		wavl = new WeakAVLMap<String, String>();
		hm = new HashMap<String>(1000000);
	}

	public void eval(String inputFileName, String outputFileName)throws IOException{
		// write your code here
		long timeWAVLI = 0;
		long timeHashMapI = 0;
		long timeWAVLD = 0;
		long timeHashMapD = 0;

		File file = new File(inputFileName);
		out = new PrintStream(new FileOutputStream(outputFileName, false), true);
		System.setOut(out);

		BufferedReader br = null;
		try{
			br = new BufferedReader(new FileReader(file));
			String input = "";
			br.readLine();
			while((input = br.readLine()) != null){
				if(String.valueOf(input.charAt(0)).equals("I")) {
					input = input.replaceAll("\\s","");
					String[] split = new String[3];
					split[0] = String.valueOf(input.charAt(0));
					split[1] = input.substring(2, input.indexOf(","));
					split[2] = input.substring(input.indexOf(",") + 1);
					long temp1w = Instant.now().toEpochMilli();
					wavl.put(split[1], (split[2]));
					long temp2w = Instant.now().toEpochMilli()-temp1w;

					long temp1h = Instant.now().toEpochMilli();
					hm.put(split[1], (split[2]));
					long temp2h = Instant.now().toEpochMilli() - temp1h;

					timeWAVLI += temp2w;
					timeHashMapI += temp2h;
				}
				else{
					String[] split = new String[2];
					split[0] = String.valueOf(input.charAt(0));
					split[1] = input.substring(2);
					long temp1w = Instant.now().toEpochMilli();
					wavl.remove(split[1]);
					long temp2w = Instant.now().toEpochMilli()-temp1w;

					long temp1h = Instant.now().toEpochMilli();
					hm.remove(split[1]);
					long temp2h = Instant.now().toEpochMilli() - temp1h;

					timeWAVLD += temp2w;
					timeHashMapD += temp2h;
				}
			}
			System.out.println("Operations WAVL HashMap");
			System.out.println("Insertions "+timeWAVLI+" "+timeHashMapI);
			System.out.println("Deletions "+timeWAVLD+" "+timeHashMapD);
		}
		catch(FileNotFoundException e){
			System.err.println("Input file Not found. " + file.getAbsolutePath());
		}
		catch(NullPointerException ne){
			ne.printStackTrace();
		}
	}

//	public static void main(String args[])throws IOException{
//		String inputFileName = "src/col106/assignment4/Map/map_input";
//		String outputFileName = "src/col106/assignment4/Map/map_output";
//		Map map = new Map();
//		map.eval(inputFileName, outputFileName);
//	}

}