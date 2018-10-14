/*
 * Victor Ruiz
 * CS 311 - Formal Language and Automata
 * Professor Daisy Sang
 * 
 * Project 2
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

	static Scanner reader = null;
	static File f;
	static int nfsaStart = 0;
	static String[] language;
	static String[] testStrings;

	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		String fileName;
		
		//getting input file and initializing a scanner to read from it	
		System.out.print("Please provide the input file name: ");
		fileName = input.next();
		
		//NFSA nfsa = new NFSA();
		NFSA[] nfsa = readInput(fileName);
		
		nfsa[0].buildNFSA();
		nfsa[1].buildNFSA();
		nfsa[2].buildNFSA();
		
		DFSA dfsa = new DFSA();
		
		//dfsa.convertToDFSA(nfsa[0]);
	}

	
	public static NFSA[] readInput(String fileName) {
		NFSA[] nfsa = new NFSA[3];
		
		for(int n = 0; n < nfsa.length; n++) {
			f = new File(fileName);
			
			try {
				reader = new Scanner(f);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			int languageCount = 0;
			int testStrCount = 0;
			boolean flip = false;
			String cur;
			
			//to move the pointer to the start of the next NFSA
			for(int i = 0; i < nfsaStart; i++) {
				reader.nextLine();
			}
			
			//counting the number of words in the language and test strings provided
			while(reader.hasNext()) {
				cur = reader.nextLine();
				//System.out.println(cur);
				if(flip == false) {
					if (cur.equals(" ")) {
						flip = true;
						continue;
					}
					languageCount++;
				} else {
					if (cur.equals(" ")) {
						break;
					}
					testStrCount++;
				}
			}
			
			language = new String[languageCount];
			testStrings = new String[testStrCount];
			
			//System.out.println(n + ": " + languageCount + "  " + testStrCount);
			
			//closing and opening the file so the pointer is at the beginning
			reader.close();
			
			try {
				reader = new Scanner(f);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			for(int i = 0; i < nfsaStart; i++) {
				reader.nextLine();
			}
			
			for(int i = 0; i < language.length; i++) {
				language[i] = reader.nextLine();
				//System.out.println(language[i]);
			}
			
			reader.nextLine(); //get rid of the blank line
			for(int i = 0; i < testStrings.length; i++) {
				testStrings[i] = reader.nextLine();
				//System.out.println(testStrings[i]);
			}
			
			nfsaStart = nfsaStart + languageCount + testStrCount + 2;
			
			nfsa[n] = new NFSA(language, testStrings);		
		}
		
		return nfsa;
	}
}
