
public class DFSA {
	int[][][] NFSAt; //to store the transposed matrix
	int[][][] DFSA;
	
	public void convertToDFSA (NFSA nfsa) {
		int[][][] NFSA = nfsa.NFSA;
		
		NFSAt = new int[NFSA[0].length][NFSA.length][NFSA[0][0].length];
		DFSA = new int[NFSAt.length][NFSAt[0].length + 1][NFSAt[0][0].length];
		/*
		 * structure is the same as the NFSA, but with an added column. the added
		 * column is to store 
		 */
		
		//transpose the matrix
		for(int i = 0; i < NFSA.length; i++) {
			for(int j = 0; j < NFSA[i].length; j++) {
				for(int k = 0; k < NFSA[i][j].length; k++) {
					if(NFSA[i][j][k] != 0 && NFSA[i][j][k] != -1) {
						NFSAt[j][i][k] = NFSA[i][j][k];
					}
				}
			}
		}
		System.out.println("done");

		//copy matrix to DFSA but have everything one column over
//		for(int i = 0; i < NFSAt.length; i++) {
//			for(int j = 0; j < NFSAt[i].length; j++) {
//				for(int k = 0; k < NFSAt[i][j].length; k++) {
//					if(NFSAt[i][j][k] != 0 && NFSAt[i][j][k] != -1 && (j+1) <= NFSAt[i].length) {
//						DFSA[i][j + 1][k] = NFSAt[i][j][k];
//					}
//				}
//			}
//		}
		
//		System.out.println(DFSA.length + " " + DFSA[0].length + " " + DFSA[0][0].length);
		
		//printing for verification
//		for(int i = 0; i < DFSA.length; i++) {
//			for(int j = 0; j < DFSA[i].length; j++) {
//				for(int k = 0; k < DFSA[i][j].length; k++) {
//					if(DFSA[i][j][k] != -1 && DFSA[i][j][k] != 0) {
//						System.out.println("(" + i + ", " + j + "): " + DFSA[i][j][k] + " ");
//						
//					}
//				}
//				System.out.println("");
//			}
//		}

		//building the DFSA
		int cur = 0;
		DFSA[0][0][0] = 0; //this will be true for all machines, so why not hardcode it
		int row = 0;
		int z = 0;
		for(int i = 0; i < DFSA.length; i++) {
			for(int j = 0; j < DFSA[i].length; j++) {
				for(int k = 0; k < DFSA[i][j].length; k++) {
					row++;
					z = 0;
					if(NFSAt[i][j][k] != 0 && NFSAt[i][j][k] != -1) {
						DFSA[row][0] = NFSAt[i][j];
						
						//DFSA[row][column][as needed] = NFSA[DFSA[row][0][x]][/* go through all columns */][/*increment as needed */];
						
						for(int x = 0; x < DFSA[row][0].length; x++) {
							for(int column = 1; column < DFSA[row].length; column++) {
								for(int d = 0; d < DFSA[row][0].length; d++) {
									if (NFSA[DFSA[row][0][x]][column][d] != 0 && NFSA[DFSA[row][0][x]][column][d] != -1) {
										DFSA[row][column][z] = NFSA[DFSA[row][0][x]][column][d];
										z++;
									}
								}
								
								
							}
						}
					}
				}
			}
		}
	
	}
}
