package Package1;

import java.io.*;
import java.lang.*;




import java.io.FileNotFoundException;
import java.io.IOException;


public class Class1 {

	public static void main(String[] args) throws IOException {
		long startTimeMS;
		long endTimeMS;
		long milisecondTime;

		Matrix myMatrix = null;
		myMatrix = new Matrix();
		myMatrix.build("filename");
		startTimeMS = System.nanoTime();
		myMatrix.bruteForce(1, 0, 0);
		endTimeMS = myMatrix.endTimeMSInMatrix;
		milisecondTime = (endTimeMS - startTimeMS) / 1000;
		System.out.println(" ");
		System.out.print("Time to slay Soduko:  " + Long.toString(milisecondTime) + " Miliseconds");

		}
}
