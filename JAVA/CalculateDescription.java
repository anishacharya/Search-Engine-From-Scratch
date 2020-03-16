

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;


public class CalculateDescription {
	
	public static HashMap<String, String> description;
		
	public static void computeIndex()
	{
		description = new  HashMap<String, String>();
		int count = 0;
		String url = "";
		
		try {
		
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
			}
				
			if(str.startsWith("<data>") && !url.contains("csv"))
			{
				int flag = 0;
				ArrayList<String> list = new ArrayList<String>();
				String desc = "";
				str = str.replaceAll("[^A-Za-z0-9 ]", " ");
				while(!(str=br.readLine()).contains("</data>") && flag++<7)
				{ 
					String temp = str.replaceAll("<data>", "");
					desc = desc + " " + temp.trim();
					if(str.replaceAll("<data>", "").length()==0)
					 flag--;
				}
				desc = desc.trim().replaceAll("\\s+", " ");
				description.put(url,desc.length()>417 ? desc.substring(0,417): desc);
				
			}
		
		}
	  }
	
		 
		 
	 }catch(Exception e) { e.printStackTrace(); }	
		
	}
	
	
	public static void main(String args[]) throws Exception
	{
		computeIndex();
		FileOutputStream fileOut = new FileOutputStream("description.ser");
		ObjectOutputStream out = new ObjectOutputStream(fileOut);
        out.writeObject(description);
        out.close();
        fileOut.close();
		System.out.println("Serialized");
		
		System.out.println(description.get("http://mondego.ics.uci.edu/"));
		System.out.println(description.get("http://sdcl.ics.uci.edu/mondego-group/"));
		System.out.println(description.get("http://www.ics.uci.edu/prospective/en/degrees/software-engineering/"));
	}

}
