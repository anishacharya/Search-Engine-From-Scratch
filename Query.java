import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PriorityQueue;

import org.tartarus.snowball.EnglishSnowballStemmerFactory;


public class Query {

	static HashMap<String, HashMap<String,Double>> tfIdf;
	static HashMap<String,Double> totalMap;
	static HashMap<String, ArrayList<String>> headerIndexList;
	
	public static void main(String args[]) throws Exception
	{
		totalMap = new HashMap<String,Double>();
		ArrayList<String> queryList = new ArrayList<String>();
		queryList.add("mondego");
		queryList.add("machine learning");
		queryList.add("software engineering");
		queryList.add("security");
		queryList.add("student affairs");
		queryList.add("graduate courses");
		queryList.add("crista lopes");
		queryList.add("rest");
		queryList.add("computer games");
		queryList.add("information retrieval");
	

        loadIndices();
      	
		
		for(int i=0;i<queryList.size();i++)
		{
			totalMap.clear();
			String query = queryList.get(i);
			String[] terms = query.split("\\s");
			for(int j=0;j<terms.length;j++)
			{
				String word = EnglishSnowballStemmerFactory.getInstance().process(terms[j]);
				addToMap(tfIdf.get(word));
			}
			boosturl(query);
			boosturlHeader(query);
			print(query);
		}
		
	}	
	
	static void addToMap(HashMap<String,Double> map)
	{
		if(map==null)
			return ;
		Iterator itr = map.entrySet().iterator();
		while(itr.hasNext())
		{
			Map.Entry pair = (Map.Entry)itr.next();
			String key = (String)pair.getKey(); Double value = (Double)pair.getValue();
			if(totalMap.containsKey(key))
				totalMap.put(key, totalMap.get(key) + value);
			else
				totalMap.put(key, value);
		}
	}
	
	static void boosturl(String query)
	{
		String[] terms = query.split("\\s");
		for(String s : terms)
		{
			for(Entry<String,Double> entry : totalMap.entrySet())
			{
				if(entry.getKey().contains(s))
				{
					totalMap.put(entry.getKey(), totalMap.get(entry.getKey()) + 400);
				}
			}
		}
	}
	
	static void boosturlHeader(String query) throws Exception
	{
		String[] terms = query.split("\\s");
		for(String s : terms)
		{
			s = EnglishSnowballStemmerFactory.getInstance().process(s);
			ArrayList<String> list = (ArrayList<String>)headerIndexList.get(s);
			if(list!=null)
			{
				for(int i=0;i<list.size();i++)
				{
					String url = list.get(i);
					//System.out.println(url);
					
					if(totalMap.containsKey(url))
						totalMap.put(url, totalMap.get(url) + 500);
					else
						totalMap.put(url, 500.0);
				}	
			}
		}
	}
	
	static void print(String query)
		{
		
		System.out.println("\n\n\n\n\n" + query + "\n");
		PriorityQueue<Entry<String, Double>> pq = new PriorityQueue<Map.Entry<String,Double>>(totalMap.size(), new Comparator<Entry<String, Double>>() {
		    @Override
		    public int compare(Entry<String, Double> arg0,
		            Entry<String, Double> arg1) {
		        return arg1.getValue().compareTo(arg0.getValue());
		    }
		});
		pq.addAll(totalMap.entrySet());
		
		
		for(int i=0;i<10;i++)
		{   
		  if(!pq.isEmpty()) {	
			Map.Entry<String, Double> temp = pq.poll();
			System.out.println(temp.getKey()  /*+ "--- " + temp.getValue()*/);
		  }
		}
	}
	
	static void loadIndices() throws Exception
	{
		    System.out.println("Loading index..");
			FileInputStream fileIn = new FileInputStream("index.ser");
	        ObjectInputStream in = new ObjectInputStream(fileIn);
	        tfIdf = (HashMap<String, HashMap<String,Double>>) in.readObject();
	        System.out.println("Index loaded.");
	        
	        System.out.println("Loading header index..");
			FileInputStream fileIn2 = new FileInputStream("headerIndex.ser");
	        ObjectInputStream in2 = new ObjectInputStream(fileIn2);
	        headerIndexList = (HashMap<String, ArrayList<String>>) in2.readObject();
	        System.out.println("Header Index loaded.");
	        
	        
	}
}
