package myProject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class TwoGrams {

	public static void main(String args[])
	{
		String stopWordFile = "";
		int count = 0;
		StringBuilder str = new StringBuilder("");
		try {
			
			
		while(count<100) {
			
		String filename = "datafile" + Integer.toBinaryString(count+1);
		File file = new File(filename);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(file);
				
		doc.getDocumentElement().normalize();
		NodeList nList = doc.getElementsByTagName("86842705");
		
		for (int temp = 0; temp < nList.getLength(); temp++) {
			 
			Node nNode = nList.item(temp);
	 
			//System.out.println("\nCurrent Element :" + nNode.getNodeName());
	 
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
	 
				Element eElement = (Element) nNode;
				str.append(eElement.getAttribute("data"));
			}
		}
	 
	
		}
		
		ArrayList<String> stop_words = new ArrayList<String>();
		ArrayList<String> listOfAllWords = new ArrayList<String>();
		ArrayList<String> listOfWords = new ArrayList<String>();
		TreeMap<String, Integer> map = new TreeMap<String, Integer>();
		
		BufferedReader brStopWords = new BufferedReader(new FileReader(stopWordFile));
		 String line = null;
		    while((line=brStopWords.readLine())!=null)
		    	stop_words.addAll(Arrays.asList(line.split(",|\\s")));
		    
		  String string = str.toString();
		  string = string.replaceAll("[\\W]|_", " ").toLowerCase().trim();
		  
		  listOfAllWords.addAll(Arrays.asList(string.split("\\s+")));
		   
		  for(int i=0;i<listOfAllWords.size();i++)
			{  if (!stop_words.contains(listOfAllWords.get(i)) && listOfAllWords.get(i).length()>1)
					{
							listOfWords.add(listOfAllWords.get(i));
					}
				
			}
		  
		  Iterator itr = listOfWords.iterator();
			
		  for(int i=0;i<listOfWords.size()-1;i++)
			{
				String twoGram = (String)listOfWords.get(i)+ " " + (String)listOfWords.get(i+1);
				if(map.containsKey(twoGram))
					map.put(twoGram, map.get(twoGram)+1);
				else
					map.put(twoGram, 1);
			}
		  
		    int size = map.size(), wordCount = 0;
			for(int i=0;i<size && wordCount++<20;i++)
			{
				int max = Collections.max(map.values());
				itr = map.entrySet().iterator();
				String key = null;
				while(itr.hasNext())
				{
					Map.Entry pair = (Map.Entry)itr.next();
					if((Integer)pair.getValue()==max)
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


