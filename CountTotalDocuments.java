

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;


public class CountTotalDocuments {
	
	public static void main(String args[])
	{
		TreeMap<String, Integer> map = new TreeMap<String, Integer>();
		HashSet<String> set = new HashSet<String>();
		int count = 0, totalURLs = 0;
		while(count++<99) {
		try {	
		String filename = "D:\\crawlstorage\\datafile" + Integer.toString(count+1)  + ".xml";  
		String str = null, url = null;;
		BufferedReader br = new BufferedReader(new FileReader(filename));
		
		while((str=br.readLine())!=null)
		
		{
			if(str.startsWith("<url>")) 
			{
				url = str.substring(5, str.length()-6); 
				set.add(url);
			}
		
		}
		
	}
	catch(Exception e) 
	{ e.printStackTrace(); }	
		
	}
		
	
	System.out.println(set.size());
	}

}
