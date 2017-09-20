import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * <p>
 * This is the File Reading and Character matching and word substitution
 * assignment<\p>
 * 
 * @author rileyp
 *
 */
public class FileReader {

	private static String outputFile = "output.txt";

	public static void main(String[] args) {
		
		/*if (args.length < 3) {
			System.out.println("Not enough files my dude");
			System.exit(1);
		}*/
		
		PrintWriter out = fileWrite(outputFile);

		Scanner in1 = fileRead(args[0], 1);
		if (checkBraces(in1))
			out.println("Braces Balanced\n");
		else
			out.println("Braces Not Balanced\n");
	}

	/**
	 * 
	 * @return Scanner a scanner of the txt file
	 * @param fname
	 *            is a file name
	 */
	public static Scanner fileRead(String fname, int index) {

		File file = new File(fname);
		Scanner input = null;

		try {

			input = new Scanner(file);

		} catch (FileNotFoundException ex) {

			PrintWriter write = fileWrite(outputFile);
			write.println("Part " + index + ": Unable to Open File");
			write.close();
			return null;

		}

		return input;

	}

	public static boolean checkBraces(Scanner in) {
		int openBrace = 0;
		int closeBrace = 0;

		while (in.hasNextLine()) {
			char[] s = in.nextLine().toCharArray();
			for (char c : s) {
				if (c == '{')
					openBrace++;
				else if (c == '}')
					closeBrace++;
			}
		}
		return openBrace == closeBrace;
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
}

/*
 * public static void main(String[] args) { if (args.length < 2) {
 * System.out.println("Not enough files my dude"); System.exit(1); }
 * 
 * Scanner file1 = fileRead(args[0]); if (file1 == null) System.exit(1);
 * 
 * Scanner file2 = fileRead(args[1]); if (file2 == null) System.exit(1);
 * 
 * //PrintWriter out = fileWrite(args[1]);
 * 
 * 
 * in.close(); out.close(); }
 */