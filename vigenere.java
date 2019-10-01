/**
 * 
 */

/*
File Name: Vig.java
Author: Aadhya Bhatt
Date: 25-Jan-2019 9:30:13 PM
Description:
*/

import java.text.DecimalFormat;
import java.util.LinkedList;
import java.lang.NumberFormatException;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;


public class Vigenere {
  public static void main ( String args[] ) {
  	Vigenere ndxcoinc = new Vigenere();

    

    int mess_length = 0;
    
    //get the length of the ciphertext from input
    String str = "cjnpkgrlilqwawbnuptgkerwxuzviaiiysxckwdntjawhqcutttvp"
    		+"tewtrpgvcwlkkkgczafsihrimixukrwxrfmgfgkfxgukpjvvzmcmj"+
    		"vawbnuptgcicvxvkgczkekgcqbchvnrqhhwiadfrcyxgvzqqtuvbd"+
    		"guvttkccdpvvfphftamzxqwrtgukcelqlrxgvycwtncbjkkeerecj"+
    		"qihvrjzpkkfexqgjtpjfupemswwxcjqxzpjtxkvlyaeaemwhovudk"+
    		"mnfxegfrwxtdyiaecyhlgjfpogymbxyfpzxxvpngkxfitnkfdniyr"+
    		"wxukssxpkqabmvkgcqbciagpadfrcyxgvyyimjvwpkgscwbpurwxq"+
    		"kftkorrwvnrqhxurlslgvjxmvccraceathhtfpmeygczwgutttvtt"+
    		"katmcvgiltwcsmjmvyghitfzaxodkbf";
    for (int i = 0; i < str.length(); i++) {
      if (Character.isLetter(str.charAt(i)))
    	  mess_length ++;
    }
    
    System.out.println(mess_length  + " letters.");

    List<Integer> list = new ArrayList<Integer>(); 
    list.add(2); 
    list.add(3); 
    list.add(4); 
    list.add(5); 
    list.add(6); 

    //create a map of the indecies of coincidence for each given key length
    LinkedHashMap<Integer, Double> ics = new LinkedHashMap<Integer, Double>();
    
    for (Integer key_len : list) {
      ics.put(key_len, ndxcoinc.calc_expected_ic(mess_length, key_len));
    }

    ndxcoinc.printTable(mess_length, ics);
    double finalIc= calculate(str, mess_length);

    DecimalFormat df = new DecimalFormat("#.###");
    df.setRoundingMode(RoundingMode.CEILING);
   String fic= df.format(finalIc);
    System.out.println("The rounded IC is"+ fic);
    double d = Double.parseDouble(fic);
    int keylen = 0;
    for (Map.Entry<Integer, Double> entry : ics.entrySet()){
    	
    	DecimalFormat df1 = new DecimalFormat("#.###");
        df.setRoundingMode(RoundingMode.CEILING);
        String fic1= df.format(entry.getValue());
        double d1 = Double.parseDouble(fic1);
    	if(d1== d)
    	{
    		 keylen= entry.getKey();
        System.out.printf("Key Length is:"+ entry.getKey());
      }
    	}
    
    //String key= estimateKey(str,keylen);
    //System.out.printf("Key is:"+ key);
    
  }//end of main

  
  public Double calc_expected_ic(Integer int_N, Integer int_d){
    double N = int_N *1.0;
    double d = int_d *1.0;
    
    return ((1.0/d)*((N-d)/(N-1.0))*(0.066)) + (((d-1.0)/d)*(N/(N-1))*(0.038));
  }

  
  
  public void printTable(Integer mess_length, 
                         LinkedHashMap<Integer, Double> ics )
{
    System.out.printf("Key Expected is IC  (N=%d)\n", mess_length);
    System.out.println("  ---- ------------------");
    for (Map.Entry<Integer, Double> entry : ics.entrySet()){
      System.out.printf("%4d %-5.4f\n", entry.getKey(), entry.getValue());
    }
  }

 
   
  public static Double calculate(String input, int N){
	  int[] freq = new int[26];
      int i = 0;
      int j =  0;
      int textlength = 0;
      while(i < input.length()) {
          char c  = input.charAt(i);
          j = (int) c - (int) 'a';
          if(j >= 0 && j <= 25) {
              freq[j]++;
              textlength++;
          }
          i++;
          j = 0;
      }
      double ic = 0;
      for(int k = 0; k < 26; k++) 
          ic += freq[k] * (freq[k] - 1);
      ic /= textlength * (textlength - 1);
      System.out.println("The value for index of coincidence  "  + ic + ".");
      return ic;
}


  public static int[] letterFrequency(String text) {
		int[] frequencies = new int[26];

		text = format(text);

		for (int i = 0; i < text.length(); i++) {
			frequencies[text.charAt(i) - 'A']++;
		}

		return frequencies;
	}
  public static String format(String text) {
		return text.toUpperCase().replaceAll("[^\\p{L}]", "");
	}
}//end of class