import java.util.HashMap;
import java.util.Map;


public class Word {

   String word;
   Map<String, Integer> documentFrequency;
   
   public Word(String word)
   {
	   this.word = word;
	   documentFrequency = new HashMap<String, Integer>();
   }
   
   public boolean equals(Object o)
   {
	   Word w = (Word)o;
	   return w.word.equals(this.word);
   }
   
   public String toString()
   {
	   return(word + " - " + documentFrequency);
   }
   
   public Map<String, Integer> getMap()
   {
	   return documentFrequency;
   }
   
   public void setMap(Map<String, Integer> map)
   {
	    documentFrequency = map;
   }
   
   @Override
   public int hashCode()
   {
	  int hash = 0;
	   for (int i=0; i < word.length(); i++) {
		    hash = hash*31+ word.charAt(i);
		}
	   return hash;
   }
}
