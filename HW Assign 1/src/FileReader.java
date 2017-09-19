import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * <p><\p>
 * @author rileyp
 *
 */
public class FileReader {
	/**
	 * 
	 * @return Scanner a scanner of the txt file
	 * @param fname is a file name
	 */
	public static Scanner fileRead(String fname) {

		File file = new File(fname);
		Scanner input = null;

		try {

			input = new Scanner(file);

		} catch (FileNotFoundException ex) {

			System.out.println("Part 1: unable to Open File");
			return null;

		}
		
		

		return input;

	}

	/**
	 * @param fname a file name
	 * @return
	 *
	 * 		PrintWriter of the file
	 */
	public static PrintWriter fileWrite(String fname) {
		File file = new File(fname);
		PrintWriter output = null;

		try {

			output = new PrintWriter(file);

		} catch (FileNotFoundException ex) {

			System.out.println("Cant open file: " + ex.getMessage() + " " + fname);
			return null;

		}

		return output;

	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public static void main(String[] args) {
		
	}
}
