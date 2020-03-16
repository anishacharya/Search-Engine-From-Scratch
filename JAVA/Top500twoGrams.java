package myProject;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;


public class top500twograms {

	public static void main(String[] args) throws IOException {
		
		//read stopwords
		ArrayList<String> stopwords = new ArrayList<String>(); 
		BufferedReader reader = new BufferedReader(new FileReader("G:\\stopwords.txt"));
		String line=null;                                 
		while((line=reader.readLine())!= null)
		{
			stopwords.add(line);
		}
		reader.close();
		
		Map<String,Integer> map = new HashMap<String,Integer>();
		
		int counter = 1;
		
		while(counter<101)
		{
			System.out.print(counter);
			//load file
			String path = "G:\\crawlstorage\\datafile"+Integer.toString(counter)+".xml";
			BufferedReader Read = new BufferedReader(new FileReader(path));
			//read line by line
			while((line=Read.readLine())!=null)
			{
				String[] words = line.replaceAll("[^A-Za-z]"," ").toLowerCase().trim().split("\\s+"); 
				
				for(int i =0;i<words.length-1;i++)
				{
					if(!stopwords.contains(words[i]) && !stopwords.contains(words[i+1]) && words[i].length()>2 && words[i+1].length()>2)
					{
						String twogram = words[i] + " " + words[i+1];
						if(!map.containsKey(twogram))
							{
								map.put(twogram, new Integer("1"));
							}
						else 
							{
								map.put(twogram, map.get(twogram) + new Integer(1));
							}
			
						
					}
				}
			}
			//increase counter
			counter++;
		}
		//print the top500 two grams
		int count = 0;
		String del = null;
		while(!map.isEmpty() && count<500)
		{
			int maxvalue = Collections.max(map.values());
			for(Entry<String,Integer> entry : map.entrySet())
			{
				if(entry.getValue() == maxvalue)
				{
					System.out.println(entry.getKey()+":"+entry.getValue());
					del = entry.getKey();
					break;
				}
			}
			map.remove(del);
			count++;
			
		}
		
	
	}

}
