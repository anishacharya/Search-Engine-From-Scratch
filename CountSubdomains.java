package myProject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;


public class CountSubdomains {
	
	public static void main(String args[])
	{
		TreeMap<String, Integer> map = new TreeMap<String, Integer>();
		int count = 0, totalURLs = 0;
		while(count++<99) {
		try {	
		String filename = "D:\\crawlstorage\\datafile" + Integer.toString(count+1)  + ".xml";  
		String str = null;
		BufferedReader br = new BufferedReader(new FileReader(filename));
		
		while((str=br.readLine())!=null)
		
		{
			if(str.startsWith("<subDomain>"))
			{
				String subdomain = str.substring(11, str.length()-12);
				
				subdomain = subdomain.trim();
				if(subdomain.length()>2) 
				{
				if(map.containsKey(subdomain))
					map.put(subdomain, map.get(subdomain) + 1);
				else
					map.put(subdomain, 1);
				}
				
			}
		
		}
		
	}
	catch(Exception e) 
	{ e.printStackTrace(); }	
		
	}
		
	Iterator itr = map.entrySet().iterator();
		
	while(itr.hasNext())
		{
			Map.Entry pair = (Map.Entry)itr.next();
			totalURLs  =  totalURLs + (Integer)pair.getValue();
			System.out.println(pair.getKey() +"  " +pair.getValue());
	    }
		System.out.println("Total URLs - " + totalURLs);
	}

}
