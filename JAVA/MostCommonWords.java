package myProject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class MostCommonWords {
	
	public static void main(String args[])
	{
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		ArrayList<String> stop_words = new ArrayList<String>();
		Iterator itr = null;
		int count = -1;
		String line="";
		try {
			
		BufferedReader brStopWords = new BufferedReader(new FileReader("C:\\Users\\swadwekar\\workspace\\Programs\\src\\AnalysisProgLang\\stopwords.txt"));	
		while((line=brStopWords.readLine())!=null)
			stop_words.addAll(Arrays.asList(line.split(",|\\s")));
			
			
		while(count++<99) 
		{
		String filename = "D:\\crawlstorage\\datafile" + Integer.toString(count+1)  + ".xml";  //Integer.toBinaryString(count+1)
		System.out.println(filename);
		String str = null;
		BufferedReader br = new BufferedReader(new FileReader(filename));
		
		while((str=br.readLine())!=null)
		
		{
			if(str.startsWith("<data>"))
			{
				String data = str;
				while(!(str=br.readLine()).contains("</data>"))
						data = data + str; 
				
				data = data.replaceAll("[^A-Za-z]", " ");
				//System.out.println(data);
				//System.out.println("----------------------------------------------------------------------------------------");
				
				String[] list = data.split(",|\\n|\\s+");
				
			    for(int i=0;i<list.length;i++)
				{
					String word = (String)list[i].toLowerCase();
					if(!stop_words.contains(word) && word.length()>1) {
					if(map.containsKey(word))
						map.put(word, map.get(word) + 1);
					else
						map.put(word, 1);
					}
				}
					
			 }
		   }
		
		}
		
		
		for(int i=0;i<500;i++)
		{
			int maxValue = Collections.max(map.values());
			itr = map.entrySet().iterator();
			String key = null;
			while(itr.hasNext())
			{
				Map.Entry pair = (Map.Entry)itr.next();
				if((Integer)pair.getValue()==maxValue)
				{
					key = (String) pair.getKey();
					System.out.println(pair.getKey() +"  " +pair.getValue());
					break;
				}
			}
			map.remove(key);
		}
		
	 }
	 catch(Exception e) { e.printStackTrace(); }	
		
	
	}

}
