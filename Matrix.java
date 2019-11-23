package Package1;

import java.io.*;

import java.lang.Math;

// import java.util.Scanner;

public class Matrix {
	int[][][] matrix = new int[100][100][2];
	int xSize = 0;
	int ySize = 0;
	int direction = 1;
	int alreadyUsed   = 0;
	
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

		if ((matrix[x][y][1] == 0)) { // The third dimension represents whether the number is blank (0), or given (1).
		
			for (int val = curValue; val <= ySize; val++) { // Cycle through values 1-9    
				usedInRow  = checkRow(x,y,val);
				usedInCol  = checkColumn(x,y,val);
				usedInGrid = checkGrid(x,y,val);
				if ((usedInRow == 1) || (usedInCol == 1) || (usedInGrid ==1)) // check to see if the value is illegal for this cell.
					alreadyUsed = 1;
				else { // The value has not been used in val's row, column nor 3x3 grid
					matrix[x][y][0] = val; // use the number (val)
					direction   = 1;       // we found a usable number so let's move right (or down if at the end of a row)
					alreadyUsed = 0;       // set flag that this is a valid number and is not already used.
					if ((x!=xSize-1) || (y!=ySize-1)) { // if both of these tests are False, it means we are at the end of the matrix so stop recursive calls.
					
						if ((y<ySize-1) && (direction == 1)) //  not at the end of a row yet
							retValue = bruteForce(1,x,y+1);	 //  recurse right		
						else if ((y>0) && (direction == -1)) //  go left i.e. back-track
							retValue = bruteForce((matrix[x][y-1][0]+1),x,y-1);						
						else {
							if ((y==ySize-1) && (direction == 1)) // this means we are at the end of a row
								retValue = bruteForce(1,x+1,0);   // go to next row
							else if ((y == 0) && (x>0) && (direction == -1)) { // if at first location of row, and not row 0, go ahead and back-track 1 row.
								retValue = bruteForce((matrix[x-1][ySize-1][0]+1),x-1,ySize-1);
							}		
						} // end else
					} // end if ((x!=xSize-1)...
					else
						val = ySize; // When we're done (last row, last column), force the for loop to exit.
				} // end else	
			} // end for
			if (alreadyUsed == 1) { // if we could not find a solution to a cell, zero it and set direction to go backwards.
				matrix[x][y][0]=0;
				direction = -1;
				return(0);
			}
			direction = 1; // set the direction to go forward now.
		}		
		else { // This is a given number (3rd dimension = 1), so just move forward or backward (direction) to the next cell.
			if (y < ySize-1) {                      // if not at the end of a row...
				if (direction == 1)                 // if moving forward...
				   retValue = bruteForce(1,x,y+1);  // recurse right.
				else {                              // we are back-tracking i.e. no solution
					if (y > 0)                      // if we are not in the first column...
						retValue = bruteForce(matrix[x][y-1][0]+1,x,y-1); // move left.
					else if ((y==0) && (x > 0))     // else if we are in the first column but not the first row, move up a row.
						retValue = bruteForce(matrix[x-1][ySize-1][0]+1,x-1,ySize-1);
				}
			}
		    else                                    // we are in the last location of a row
		    {
			   if (x < xSize-1) {                   // if we are not yet at the bottom row...
				   if (direction == 1)              // direction is forward so move one row down.
					   retValue = bruteForce(1,x+1,0);
				   else                             // we are at the bottom row, so move left.
					   retValue = bruteForce(matrix[x][y-1][0]+1, x, y-1);
			   }	
			}		
		}
		retValue=0;

		if ((x==xSize-1) && (y==ySize-1)) { // Go in here if we are at the end.  It's time to write the solution to a file.
	    try {

	    	BufferedWriter outFile = new BufferedWriter(new FileWriter("MatrixOutputFile.txt"));
	    	
	        for (int x1 = 0; x1 < xSize; x1++) {
	        	for (int y1 =0; y1 < ySize; y1++)
	               outFile.write(String.valueOf(matrix[x1][y1][0] + " "));
	        	outFile.write('\n');
	        }
	        outFile.close();
	      } catch (IOException e) {
	        System.out.println("Error - " + e.toString());
	      }
	    retValue = 1;

	    }
		return (retValue);
	}
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
