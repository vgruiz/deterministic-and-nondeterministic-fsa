import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Arrays;

public class NFSA {
	Scanner reader;
	File f;
	int nfsaStart = 0;		//for navigating the input file
	String[] language;
	String[] testStrings;
	char[] uniqueCharacters;
	int[][][] NFSA;
	int stateCount;	//total number of states, which is the total number of characters in the language
	
	private final int STARTING_STATE = 0;
	
	public NFSA(String[] lang, String[] testStr) {
		language = lang;
		testStrings = testStr;
	}

	public void buildNFSA() {
		uniqueCharacters = getUniqueCharacters(language);
		
		NFSA = new int[uniqueCharacters.length][stateCount + 1][stateCount];
		/*		int [ 1 ][ 2 ][ 3 ]
		 * 		1 - rows, number of possible inputs
		 * 		2 - columns, possible states for this NFSA
		 * 		3 - this is where we list the possible states when you are in a state and take an input
		 */
		
		for(int i = 0; i < NFSA.length; i++) {
			for(int j = 0; j < NFSA[i].length; j++) {
				Arrays.fill(NFSA[i][j], 0, 10, -1);
			}
		}
		
		int curState = 0;
		int storedState = -1;
		int nextState = -1;
		int curInput;
		char[] curWord;
		
		for(int i = 0; i < language.length; i++) {		//go through each word in the language
			for(int j = 0; j < language[i].toCharArray().length; j++) {		//for every character in this word
				if(j == 0) {								//if we are at the beginning of a new word, we are at the starting state
					storedState = curState;
					nextState = curState + 1;
					curState = STARTING_STATE;
				} else {
					storedState = curState;
					nextState = curState + 1;
				}
				
				curWord = language[i].toCharArray();
				curInput = getCharNum(uniqueCharacters, curWord[j]);

				for(int k = 0; k < NFSA[curInput][curState].length; k++) {
						
					if(NFSA[curInput][curState][k] == -1) {
						
						NFSA[curInput][curState][k] = nextState;
//						System.out.println("at state " + curState + " and for input " + uniqueCharacters[curInput] 
//								+ ", we assign state " + nextState);
						
						curState = nextState;
						
						break;
					}
					
				}
			}
		}
		
		printNFSAInfo();
		
	}
	
	private char[] getUniqueCharacters(String[] array) {
		char[] unique = new char[26];
		char[] unique2;
		int uniqueCount = 0;
		char cur;
		char[] curChars;
		boolean flag = false;
		stateCount = 0;
		
		
		for(int i = 0; i < array.length; i++) {
			curChars = array[i].toCharArray();
			
			for(int x = 0; x < curChars.length; x++) {
				flag = false;
				cur = curChars[x];
				stateCount++;
				
				for(int j = 0; j < unique.length; j++) {
					if(curChars[x] == unique[j]) {
						flag = true;
					}
				}
			
				if(!flag) {
					unique[uniqueCount] = curChars[x];
					//System.out.println(unique[uniqueCount]);
					uniqueCount++;
				}
			}
		}
		
		unique2 = new char[uniqueCount];
		
		for(int i = 0; i < uniqueCount; i++) {
			unique2[i] = unique[i];
		}
		
		return unique2;
	}

	private int getCharNum(char[] array, char c) {
		for(int i = 0; i < array.length; i++) {
			if(array[i] == c) {
				return i;
			}
		}
		
		return -1;
	}
	
	private void printNFSAInfo() {
		System.out.println("number of states: " + NFSA[0].length);
		
		System.out.print("final states: ");
		boolean[] empty = new boolean[this.stateCount + 1];
		Arrays.fill(empty, false);
		for(int i = 0; i < NFSA.length; i++) {
			for(int j = 0; j < NFSA[i].length; j++) {
				//System.out.println("NFSA["+ i +"]["+ j + "][0]: " + NFSA[i][j][0] );
				if(NFSA[i][j][0] != -1) {
					if(empty[j] == false) {
						empty[j] = true;
					}
				}
			}
		}
		
		for(int i = 0; i < empty.length; i++) {
			if (!empty[i]) {
				System.out.print(i + ", ");
			}
		}
		System.out.println();
		
		System.out.print("alphabet: ");
		for(int i = 0; i < uniqueCharacters.length; i++) {
			System.out.print(uniqueCharacters[i] + ", ");
		}
		System.out.println();
		
		System.out.println("transitions: ");
		for(int i = 0; i < NFSA.length; i++) {
			for(int j = 0; j < NFSA[i].length; j++) {
				if(NFSA[i][j][0] != -1) {
					System.out.print("   " + j + " " + uniqueCharacters[i] + " ");
					for(int k = 0; k < NFSA[i][j].length; k++) {
						if(NFSA[i][j][k] != -1 && NFSA[i][j][k] != 0) {
							System.out.print(NFSA[i][j][k] + " ");
						}
					}
					System.out.println();
				}
			}
		}
		System.out.println();
	}
}