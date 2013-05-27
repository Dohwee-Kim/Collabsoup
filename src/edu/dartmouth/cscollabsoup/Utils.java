package edu.dartmouth.cscollabsoup;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;


public class Utils {
	
	
	public static void printMatrix(String[][] m){
	    try{
	        int rows = m.length;
	        int columns = m[0].length;
	        String str = "|\t";

	        for(int i=0;i<rows;i++){
	            for(int j=0;j<columns;j++){
	                str += m[i][j] + "\t";
	            }

//	            System.out.println(str + "|");
	            str = "|\t";
	        }

	    }catch(Exception e){System.out.println("Matrix is empty!!");}
	}
	
	public static String hashToString(Hashtable<String, ArrayList<String>> h)
	{
		String s = "";
		Enumeration<String> enumKey = h.keys();
		while(enumKey.hasMoreElements()) {
		    String key = enumKey.nextElement();
//		    s = s + key + ": ";
		    ArrayList<String> val = h.get(key);
		    for (int i = 0; i < val.size(); i++)
		    	if (i < 2)
		    		s = s + val.get(i) + " ";
		    	else if (i == 2)
		    		s = s + ": " + val.get(i);
	    		else 
	    			s = s + ", "+ val.get(i);
		    s = s + "\n\n";
		}
		return s;
		
	}
}
	