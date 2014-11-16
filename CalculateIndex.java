

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import org.tartarus.snowball.EnglishSnowballStemmerFactory;


public class CalculateIndex {
	
	public static HashMap<String, HashMap<String,Integer>> indexList;
	public static HashMap<String, HashMap<String,Double>> tfIdf;
	public static HashMap<String, HashMap<String,ArrayList<Integer>>> positions;
	
	public static double totalDocuments = 78619;
	
	public static void computeIndex()
	{
		TreeMap<String, Integer> map = new TreeMap<String, Integer>();
		ArrayList<String> stop_words = new ArrayList<String>();
		indexList = new HashMap<String, HashMap<String,Integer>>();
		positions = new HashMap<String, HashMap<String,ArrayList<Integer>>>();
		int count = 0, pos = 0;
		String url = "", maxURL = "" , line = null;
		
		
		try {
		
			
		BufferedReader brStopFile = new BufferedReader(new FileReader("C:\\Users\\swadwekar\\workspace\\Programs\\src\\AnalysisProgLang\\stopwords2.txt"));	
		while((line=brStopFile.readLine())!=null)
			stop_words.addAll(Arrays.asList(line.split(",|\\s")));
		
		
		while(count<100) {
			
		String filename = "D:\\crawlstorage\\datafile" + Integer.toString(++count)  + ".xml";  
		System.out.println(filename);
		String str = null;
		BufferedReader br = new BufferedReader(new FileReader(filename));
		
		while((str=br.readLine())!=null)
		
		{
			if(str.startsWith("<url>")) 
			{
				url = str.substring(5, str.length()-6); 
				pos = 0;
			}
				
			if(str.startsWith("<data>") && !url.contains("csv") && !url.contains("ironwood"))
			{
				ArrayList<String> list = new ArrayList<String>();
				while(!(str=br.readLine()).contains("</data>"))
				{ 
					str = str.replaceAll("[^A-Za-z0-9 ]", " ").toLowerCase();
					
					String[] wordsInLine = str.split(",|\\n|\\s+");
					for(String word:wordsInLine)
					{
						if(!stop_words.contains(word) && word.length()>2)
						{
							pos++;
							addWord(url, word);
							//addPosition(url, word, pos);
						}
					}
				}
				
			}
		
		}
	  }
		//System.out.println(indexList.size());
	
	 /* Iterator itr = indexList.entrySet().iterator();
	  while(itr.hasNext())
	  {
		  Map.Entry pairs = (Map.Entry)itr.next();
		  System.out.println(pairs.getKey() + " - " + pairs.getValue());
	  }
		
		 Iterator itr = positions.entrySet().iterator();
		  while(itr.hasNext())
		  {
			  Map.Entry pairs = (Map.Entry)itr.next();
			  System.out.println(pairs.getKey() + " - " + pairs.getValue());
		  }
		  
		
		calculateTFIDF(); 
	 

	  Iterator itr2 = tfIdf.entrySet().iterator();
	  while(itr.hasNext())
	  {
		  Map.Entry pairs = (Map.Entry)itr2.next();
		  System.out.println(pairs.getKey() + " - " + pairs.getValue());
	  } */
		
		 calculateTFIDF(); 
		 
		 
	 }catch(Exception e) { e.printStackTrace(); }	
		
	}
	
	public static void addWord(String url, String word) throws Exception
	{
		HashMap<String, Integer> map;
		word = EnglishSnowballStemmerFactory.getInstance().process(word);
		//System.out.println(word);
		if(!indexList.containsKey(word))
		{
			map = new HashMap<String, Integer>();
			map.put(url, 1);
			indexList.put(word, map);
		}
		else
		{
			map = (HashMap<String,Integer>)indexList.get(word);
			if(map.containsKey(url))
				map.put(url, map.get(url) +1);
			else
				map.put(url, 1);
			indexList.put(word, map);
		}
	}
	
	public static void calculateTFIDF()
	{
		  tfIdf = new HashMap<String, HashMap<String,Double>>();
		  Iterator itr = indexList.entrySet().iterator();
		  while(itr.hasNext())
		  {
			  Map.Entry pairs = (Map.Entry)itr.next();
			  String word = (String)pairs.getKey();
			  HashMap<String,Integer> countMap = (HashMap<String,Integer>)pairs.getValue();
			  
			  HashMap<String, Double> wordtfidf = new HashMap<String, Double>();
			  
			  Iterator itr2 = countMap.entrySet().iterator();
			  while(itr2.hasNext())
			  {
				  Map.Entry p = (Map.Entry)itr2.next();
				  String url = (String)p.getKey();
				  int freq = (Integer)p.getValue();
				  
				  double tfidf = (double) (freq * Math.log((double)totalDocuments/countMap.size()));
				  wordtfidf.put(url, tfidf);
			  }
			  
			  tfIdf.put(word, wordtfidf);  
		  }
	}
	
	public static void addPosition(String url, String word, int pos)
	{
		HashMap<String,ArrayList<Integer>> map;
		
		if(!positions.containsKey(word))
		{
			map = new HashMap<String,ArrayList<Integer>>();
			ArrayList<Integer> list = new ArrayList<Integer>();
			list.add(pos);
			map.put(url, list);
		}
		else
		{
			map = (HashMap<String,ArrayList<Integer>>)positions.get(word);
			if(map.containsKey(url))
			{
				ArrayList<Integer> temp = (ArrayList<Integer>)map.get(url);
				temp.add(pos);
				map.put(url, temp);
			}
			else
			{
				ArrayList<Integer> list = new ArrayList<Integer>();
				list.add(pos);
				map.put(url, list);
			}
				
		}
		
		positions.put(word, map);
	}
	
	public static void main(String args[]) throws Exception
	{
		computeIndex();
		FileOutputStream fileOut = new FileOutputStream("index.ser");
		ObjectOutputStream out = new ObjectOutputStream(fileOut);
        out.writeObject(tfIdf);
        out.close();
        fileOut.close();
		System.out.println("Serialized");
	}

}
