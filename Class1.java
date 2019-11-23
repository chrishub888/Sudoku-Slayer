package Package1;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Class1 {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		Matrix myMatrix = null;
		myMatrix = new Matrix();
		myMatrix.build("filename");
		myMatrix.bruteForce(1, 0, 0);

		}
		
		//int x = 3;
		//TestClass tstClass = new TestClass(x);
		//System.out.println(tstClass.testNum);
		
	

}
