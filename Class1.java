package Package1;

import java.io.*;
import java.lang.*;



import java.io.FileNotFoundException;
import java.io.IOException;


public class Class1 {

	public static void main(String[] args) throws IOException {
		long startTimeMS;
		long endTimeMS;
		// TODO Auto-generated method stub
		Matrix myMatrix = null;
		myMatrix = new Matrix();
		myMatrix.build("filename");
		startTimeMS = System.currentTimeMillis();
		myMatrix.bruteForce(1, 0, 0);
		endTimeMS = myMatrix.endTimeMSInMatrix;
		System.out.println(endTimeMS-startTimeMS);

		}
}
