import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;

import org.tartarus.snowball.EnglishSnowballStemmerFactory;


public class QueryHeaderIndex {

	static HashMap<String, ArrayList<String>> headerIndexList;
	public static void main(String args[]) throws Exception
	{
		loadHeaderIndex();
	}
	
	public static void loadHeaderIndex() throws Exception
	{
		    System.out.println("Loading header index..");
			FileInputStream fileIn = new FileInputStream("headerIndex.ser");
	        ObjectInputStream in = new ObjectInputStream(fileIn);
	        headerIndexList  = (HashMap<String, ArrayList<String>>) in.readObject();
	        System.out.println("Header Index loaded.");
	        
	        String word = EnglishSnowballStemmerFactory.getInstance().process("information");
	        System.out.println(headerIndexList.get(word));
	       // System.out.println(headerIndexList.get("lope"));
	}
}
