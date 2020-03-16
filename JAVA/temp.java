
public class temp {

	public static void main(String args[])
	{
		
		String word = "AAa";
		int hash = 0;
		
		for(int i=0;i<word.length();i++)
		{
			hash = hash + word.charAt(i);
		}
		
		System.out.println(hash);
	}
}
