import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * <p>
 * This is the File Reading and Character matching and word substitution
 * assignment.<br> This program works like mad libs, in that a the user fills in parts of speech in a story to create a new story
 * </p>
 * 
 * @author rileyp
 *
 */
public class FileReader {

	/**
	 * This is the filename of the output file
	 */
	private static String outputFile = "output.txt";

	public static void main(String[] args) {

		// checks to make sure the program has enough files to use
		if (args.length < 3) {
			System.out.println("Not enough files my dude");
			System.exit(1);
		}

		PrintWriter out = fileWrite(outputFile);

		// This is part 1
		// Reads in the first file and checks if the braces within it are
		// balanced properly (syntactically)
		Scanner in1 = fileRead(args[0], 1);
		if (in1 != null && checkBraces(in1))
			out.println("Braces Balanced\n");
		else
			out.println("Braces Not Balanced\n");

		// This is part 2
		// Takes in two files and compares them to see if they are identical
		in1 = fileRead(args[0], 1);
		Scanner in2 = fileRead(args[1], 2);

		if (in2 != null && compareFile(in1, in2))
			out.println("Files Identical");
		else
			out.println("Files Not Identical");
		
		out.println();

		// This is part 3
		// this takes in a file with words missing (like mad libs) and will
		// replace them with words
		// supplied either by the user or a file
		Scanner madLibs = fileRead(args[2], 3);
		if(madLibs != null) {
		if (args.length < 4)
			setWords(madLibs, args[2], false, "",out);
		else
			setWords(madLibs, args[2], true, args[3],out);
		}
		out.close();
	}

	/**
	 * 
	 * @return Scanner a scanner of the txt file
	 * 
	 * @param fname
	 *            is a file name
	 * @param index saves which part of the program the error is referencing
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
			//System.exit(1);???
			return null;

		}

		return input;

	}

	/**
	 * @param fname
	 *            a file name
	 * 
	 * @return
	 *
	 * 		PrintWriter Writes to the supplied file
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

	/**
	 * <p>This checks if the file has balanced braces</p>
	 * 
	 * @param in
	 *            Scanner file
	 * @return
	 *
	 * 		boolean if the braces are balanced
	 */
	public static boolean checkBraces(Scanner in) {
		int brace = 0;

		while (in.hasNextLine()) {
			char[] s = in.nextLine().toCharArray();
			for (char c : s) {
				if (c == '{')
					brace++;
				else if (c == '}')
					brace--;
			}
		}
		return brace == 0;
	}

	/**
	 * <p>
	 * Compares to files line by line to see if they are equal
	 * </p>
	 * 
	 * @param f1
	 *            first file
	 * @param f2
	 *            second file
	 * @return
	 *
	 * 		boolean if they are equal
	 */
	public static boolean compareFile(Scanner f1, Scanner f2) {

		while (f1.hasNextLine()) {
			if (!f2.hasNextLine())
				return false;
			String file1 = f1.nextLine();
			String file2 = f2.nextLine();
			if (!file1.equals(file2))
				return false;
		}
		return true;

	}
	
	
	/**
	 * 
	 * @param wordList
	 *            list of the parts of speech
	 * @return ArrayList&lt;String&gt; of the new words to put into the story
	 */
	public static ArrayList<String> newWords(ArrayList<String> wordList) {

		JFrame frame = new JFrame("Gimme some words!!!");
		// Scanner kybd = new Scanner(System.in);

		ArrayList<String> newWords = new ArrayList<String>();
		int i = 0;
		
		//prompts the user for the new words
		for (String s : wordList) {
			newWords.add(JOptionPane.showInputDialog(frame, "Please input a " + wordList.get(i)));
			i++;
		}


		return newWords;
	}

	
	/**
	 * <p> Reads in words from a file to be used to replace the placeholders
	 * @param fname
	 *            filename of the file to get the replacement words from
	 * @return ArrayList&lt;String&gt; the words read in from the file
	 */
	public static ArrayList<String> fileWords(String fname) {
		Scanner wordsForRep = fileRead(fname, 3);
		ArrayList<String> ret = new ArrayList<String>();

		while (wordsForRep.hasNextLine())
			ret.add(wordsForRep.nextLine());
		return ret;

	}


	/**
	 * <p>
	 * finds the placeholders designated within the file that will be
	 * replaced
	 * </p>
	 * 
	 * @param mdLib
	 *            the file with the missing words
	 * @return
	 *
	 * 		ArrayList&lt;String&gt; parts of speech to be replaced by actual words
	 */
	public static ArrayList<String> findWords(Scanner mdLib) {
		ArrayList<String> missingWords = new ArrayList<String>();

		while (mdLib.hasNextLine()) {
			String line = mdLib.nextLine();
			int pos = line.indexOf("<");
			while (pos != -1) {
				missingWords.add(line.substring(line.indexOf("<", pos), line.indexOf(">", pos) + 1));
				pos = line.indexOf("<", pos + 1);
			}
		}
		return missingWords;
	}

	
	
	 
	 

	/**
	 * <p>
	 * this takes the words to be replaced and add them in the correct places in
	 * the file, then outputs the changed file
	 </p>
	 * @param mdLib this is the scanner of the unfilled poem
	 * @param fname	the file name mdLib is refrencing
	 * @param fromFile if the words are going to be from a file or not
	 * @param repFName if the new words are coming from a file, this is the file name of it
	 * @param out the printWriter used to print to the output
	 *
	 *void
	 */
	public static void setWords(Scanner mdLib, String fname, boolean fromFile, String repFName, PrintWriter out) {
		// this gets the needed parts
		ArrayList<String> neededParts = findWords(mdLib);
		// this asks for the new words
		ArrayList<String> replace = new ArrayList<String>();
		
		//checks if the words will come from a file or from user prompt
		if (!fromFile)
			replace = newWords(neededParts);
		else {
			replace = fileWords(repFName);
			if (replace.size() < neededParts.size())
				//makes sure there is something to print
				for (int i = 0; i <= neededParts.size() - replace.size(); i++)
					replace.add("[Insert Word Here]");
		}

		// this is a copy of mdLib
		Scanner copy = fileRead(fname, 3);
		
		//goes through each line of the file and changes each part of speech to the new word with from the user or from a file
		int word = 0;
		while (copy.hasNextLine()) {
			String line = copy.nextLine();

			int pos = line.indexOf("<");
			if (pos != -1) {
				while (pos != -1 && word < replace.size()) {

					String s = line.substring(0, pos) + replace.get(word) + line.substring(line.indexOf(">", pos) + 1);
					line = s;
					word++;
					pos = line.indexOf("<", pos + 1);

				}
				out.println(line);
				
			} else 
				out.println(line);
		}
		copy.close();

	}


}
