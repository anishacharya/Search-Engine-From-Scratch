

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


public class CalculateHeaderIndex {
	
	public static HashMap<String, ArrayList<String>> headerIndexList;
		
	public static void computeIndex()
	{
		TreeMap<String, Integer> map = new TreeMap<String, Integer>();
		ArrayList<String> stop_words = new ArrayList<String>();
		headerIndexList = new HashMap<String, ArrayList<String>>();
		
		int count = 0, pos = 0,flag;
		String url = "", maxURL = "" , line = null, header = null;
		
		try {
		
			
		BufferedReader brStopFile = new BufferedReader(new FileReader("C:\\Users\\swadwekar\\workspace\\Programs\\src\\AnalysisProgLang\\stopwords2.txt"));	
		while((line=brStopFile.readLine())!=null)
			stop_words.addAll(Arrays.asList(line.split(",|\\s")));
		
		
		while(count<20) {
			
		String filename = "D:\\crawlstorage\\results_html\\Html_crawledContent_" + Integer.toString(++count)  + ".txt";  
		System.out.println(filename);
		String str = null;
		BufferedReader br = new BufferedReader(new FileReader(filename));
		
		while((str=br.readLine())!=null /*&& headerIndexList.size()<30*/)
		
		{
			if(str.startsWith("<URL>")) 
			{
				url = str.substring(5, str.length()-6); 
				pos = 0;
				//System.out.println(url);
			}
				
			if(str.contains("<title>") && !url.contains("csv") && !url.contains("xml"))
			{
				ArrayList<String> list = new ArrayList<String>();
				outerloop:
				while(true)
				{ 
					str = str.replaceAll("[^A-Za-z ]", " ").toLowerCase();
					//System.out.println("-----------" + str + "__--------_______");
					flag=1;
					String[] wordsInLine = str.split(",|\\n|\\s+");
					for(String word:wordsInLine)
					{
						//System.out.println("-----------" + word + "__--------_______");
						if(word.contains("title") && flag++==2)
							break outerloop;
						if(flag!=2)
							continue;
						if(!stop_words.contains(word) && word.length()>2)
						{
							//System.out.println(word + "-----" + url);
							addWord(url, word);
						}
					}
				}
				
			}
		
		}
	  }
		
	}catch(Exception e) { e.printStackTrace(); }	
		
	}
	
	public static void addWord(String url, String word) throws Exception
	{
			
		if(word.equals("lopes"))
			System.out.println(url);
		
		ArrayList<String> list;
		word = EnglishSnowballStemmerFactory.getInstance().process(word);
		
		if(!headerIndexList.containsKey(word))
		{
			list = new ArrayList<String>();
			list.add(url);
			headerIndexList.put(word, list);
		}
		else
		{
			list = (ArrayList<String>)headerIndexList.get(word);
			if(!list.contains(url))
			{ 
				list.add(url);
				headerIndexList.put(word, list);
			}
		}
	}
	

	
	
	
	public static void main(String args[]) throws Exception
	{
		System.out.println("Calculating..");
		computeIndex();
		//System.out.println(headerIndexList);
		System.out.println("Writing..");
		FileOutputStream fileOut = new FileOutputStream("headerIndex.ser");
		ObjectOutputStream out = new ObjectOutputStream(fileOut);
        out.writeObject(headerIndexList);
        out.close();
        fileOut.close();
		System.out.println("Serialized");
	}

}
