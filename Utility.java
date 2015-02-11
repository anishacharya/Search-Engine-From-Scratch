package myProject;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Utility {

	static ArrayList<PrintWriter> dataPrintWriters = new ArrayList<PrintWriter>();
	static ArrayList<PrintWriter> htmlPrintWriters = new ArrayList<PrintWriter>();
	static double count = 0;
	
	static void initializePrintWriters()
	{
		try {
		for(int i=0;i<100;i++)
		{
			dataPrintWriters.add(new PrintWriter(new BufferedWriter(new FileWriter("D:\\crawlstorage\\datafile"+Integer.toString(i+1)+".xml"))));
		}
		
		for(int i=0;i<100;i++)
		{
			htmlPrintWriters.add(new PrintWriter(new BufferedWriter(new FileWriter("D:\\crawlstorage\\htmlfile"+Integer.toString(i+1)+".xml"))));
		}
		
		} catch(Exception e) { e.printStackTrace(); }
	}
	
	
	static void writeToFileData(String text, int crawlerId, String url, String domain, String subDomain)
	{
	
		    dataPrintWriters.get(crawlerId-1).println("<86842705>");
			dataPrintWriters.get(crawlerId-1).println("<url>" + url + "</url>");
			dataPrintWriters.get(crawlerId-1).println("<domain>" + domain + "</domain>");
			dataPrintWriters.get(crawlerId-1).println("<subDomain>" + subDomain + "</subDomain>");
			dataPrintWriters.get(crawlerId-1).println("<data>" + text + "</data>");
			dataPrintWriters.get(crawlerId-1).println("</86842705>");
			dataPrintWriters.get(crawlerId-1).flush();
			//temp.close();
		
	}
	
	static void writeToFileHTML(String text, int crawlerId)
	{

		htmlPrintWriters.get(crawlerId-1).println("<86842705>");
		htmlPrintWriters.get(crawlerId-1).println(text);
		htmlPrintWriters.get(crawlerId-1).println("</86842705>");
		htmlPrintWriters.get(crawlerId-1).flush();
//		/htmlPrintWriters.get(crawlerId).close();
	
	}
}
