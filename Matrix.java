package Package1;

import java.io.*;

import java.lang.Math;

// import java.util.Scanner;

public class Matrix {
	int[][][] matrix = new int[9][9][2];
	int xSize = 0;
	int ySize = 0;
	int direction = 1;
	int alreadyUsed   = 0;
	int solved = 0;
	long endTimeMSInMatrix;
	int	previousCell = 0;
	
	int build(String suppliedFile) throws IOException {
		
		// If a file is passed in, use that file to build the matrix. If
		// a file is not passed in, throw an error (for now). It would be
		// good to add a future capability to "create" a matrix for the user.
		//
		BufferedReader csvReader = new BufferedReader(new FileReader("MatrixInputFile.txt"));

		String row = "1234";
		int lineLength = 0;
		int i = 0;
		
		while ((row = csvReader.readLine()) != null) {
			String[] data = row.split(",");
			lineLength = (row.length() + 1) / 2; // accounting for commas

			for (int jindex = 0; jindex < lineLength; jindex++) {
				matrix[i][jindex][0] = Integer.parseInt(data[jindex]);
				if (Integer.parseInt(data[jindex]) != 0)
					matrix[i][jindex][1] = 1;
				else
					matrix[i][jindex][1] = 0;
			}
			i++;
		}
		ySize = lineLength;
		xSize = i;
 
		csvReader.close();

		return (0);
		}
		
	int bruteForce(int curValue, int x, int y) {
		int usedInRow     = 0;
		int usedInCol     = 0;
		int usedInGrid    = 0;
		int retValue      = 0;

		
		for (int val = curValue; val <= ySize; val++) { // Cycle through values 1-9    
			if (solved == 0) {
				
				alreadyUsed=0;
				
				if ((matrix[x][y][1] == 0)) { // The third dimension represents whether the number is blank (0), or given (1).
					usedInRow  = checkRow(x,y,val);
					usedInCol  = checkColumn(x,y,val);
					usedInGrid = checkGrid(x,y,val);
					if ((usedInRow == 1) || (usedInCol == 1) || (usedInGrid ==1))  // check to see if the value is illegal for this cell.
						alreadyUsed = 1;
					else
							alreadyUsed = 0;
					
				}	// end if (matrix...
				if ((matrix[x][y][1] == 0  ) && (alreadyUsed == 0)) { // The third dimension represents whether the number is blank (0), or given (1).                  
					matrix[x][y][0] = val; // use the number (val)
					direction = 1;
				}
                if ((alreadyUsed == 0)) {
					if ((x!=xSize-1) || (y!=ySize-1)) { // if both of these tests are False, it means we are at the end of the matrix so stop recursive calls.
					
						if ((y<ySize-1) && (direction == 1)) { //  not at the end of a row yet
							retValue = bruteForce(1,x,y+1);	 //  recurse right		
							alreadyUsed = 1; // Need to set this to 1 in case 'val' is already '9', it won't go into the for loop
						}
					//	else if ((y>0) && (direction == -1)) //  go left i.e. back-track
						//	retValue = bruteForce((matrix[x][y-1][0]+1),x,y-1);						
						else {
							if ((y==ySize-1) && (direction == 1)) {// this means we are at the end of a row
								retValue = bruteForce(1,x+1,0);   // go to next row		
								alreadyUsed = 1; // Need to set this to 1 in case 'val' is already '9', it won't go into the for loop
							}
						} // end else
					} // end if ((x!=xSize-1)...
					else {
						val = ySize;
						solved = 1;
					}// When we're done (last row, last column), force the for loop to exit.
                } // end if (alreadyUsed...
			} // end if solved
			previousCell = matrix[x][y][1];
		}// end for

		if ((alreadyUsed == 1) && (solved == 0) & (matrix[x][y][1]==0)) { // if we could not find a solution to a cell, zero it and set direction to go backwards.
			matrix[x][y][0]=0;
			direction = -1;
			return(0);
		}

			
		if ((x==xSize-1) && (y==ySize-1)) { // Go in here if we are at the end.  It's time to write the solution to a file.
			endTimeMSInMatrix = System.nanoTime();
			for (int x1 = 0; x1 < xSize; x1++) {
				for (int y1 = 0; y1 < ySize; y1++)
				   System.out.print(matrix[x1][y1][0]);
			System.out.println("");  
			} // end for
			retValue = 1;
		} // end if (x==...
		return (retValue);
	} // end bruteForce()
	
	int checkRow(int xLocation, int yLocation, int tryValue) {
		// march along the row starting at y value '0'.
		// check to see if the current y value is the number we are trying (yLocation).  If it is skip over it.
		int retValue=0;
		for (int i=0; i<ySize; i++) {
			if ((matrix[xLocation][i][0]==tryValue) && !((i==yLocation)))
					retValue=1;
		}
		return(retValue);
	}
	
	int checkColumn(int xLocation, int yLocation, int tryValue) {
		// march along the row starting at y value '0'.
		// check to see if the current y value is the number we are trying (yLocation).  If it is skip over it.
		int retValue=0;
		for (int i=0; i<xSize; i++) {
				if ((matrix[i][yLocation][0]==tryValue) && !((i==xLocation)))
					retValue=1;	
		}
		return(retValue);
	}
	
	int checkGrid(int xLocation, int yLocation, int tryValue) {
		// march along the row starting at y value '0'.
		// check to see if the current y value is the number we are trying (yLocation).  If it is skip over it.
		int retValue= 0;
		int xStart  = 0;
		int yStart  = 0;
		xStart = (xLocation / 3) * 3;
		yStart = (yLocation / 3) * 3;
		for (int x=xStart; x<(xStart +3); x++) {
			for (int y=yStart; y<(yStart+3); y++) {
				if ((matrix[x][y][0]==tryValue) && !((x==xLocation) && (y==yLocation)))
					retValue=1;	
			}	
		}
		return(retValue);
	}

}