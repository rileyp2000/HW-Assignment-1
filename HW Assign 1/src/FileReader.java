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
 * assignment<\p>
 * 
 * @author rileyp
 *
 */
public class FileReader {

	private static String outputFile = "output.txt";

	public static void main(String[] args) {
		
		if (args.length < 3) {
			System.out.println("Not enough files my dude");
			System.exit(1);
		}
		
		PrintWriter out = fileWrite(outputFile);

		//This is part 1
		Scanner in1 = fileRead(args[0], 1);
		if (checkBraces(in1))
			out.println("Braces Balanced\n");
		else
			out.println("Braces Not Balanced\n");
	
		
		//This is part 2
		in1 = fileRead(args[0],1);
		Scanner in2 = fileRead(args[1],2);

		if(compareFile(in1, in2))
			out.println("Files Identical");
		else
			out.println("Files Not Identical");

		
		//This is part 3
		Scanner madLibs = fileRead(args[2], 3);
		ArrayList<String> neededParts = findWords(madLibs);
		//if(args[3] == null){
			ArrayList<String> words = newWords(neededParts);
			//SETWORDS DOESNT WORK
			madLibs = setWords(madLibs, words);
			while(madLibs.hasNextLine())
				out.println(madLibs.nextLine());
		//}
		
		
		
		
		out.close();
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
	
	
	
	/**
	 * <p> This checks if the file has balanced braces<\p>
	 * @param in Scanner file
	 * @return
	 *
	 *boolean if the braces are balanced
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
	 * 
	 * @param f1 first file
	 * @param f2 second file
	 * @return
	 *
	 *boolean if they are equal
	 */
	public static boolean compareFile(Scanner f1, Scanner f2) {
	//NEED TO TEST
		while(f1.hasNextLine()){
			if(!f2.hasNextLine())
				return false;
			String file1 = f1.nextLine();
			String file2 = f2.nextLine();
			if(file1 != file2)
				return false;
		}
		 return true;

	}
	
	/**
	 * 
	 * @param mdLib
	 * @return
	 *
	 *ArrayList<String>
	 */
	public static ArrayList<String> findWords(Scanner mdLib){
		ArrayList<String> missingWords = new ArrayList<String>();
		
		while(mdLib.hasNextLine()) {
			int pos = 0;
			String line = mdLib.nextLine();
			while(line.indexOf("<",pos) != -1) {
				missingWords.add(line.substring(line.indexOf("<",pos), line.indexOf(">", pos)+1 ));
				pos = line.indexOf('<', pos +2);
			}
		}
		return missingWords; 
	}
	
	/**
	 * 
	 * @param mdLib the poem with the missing words
	 * @param replace the words to replace the string with
	 */
	public static Scanner setWords(Scanner mdLib, ArrayList<String> replace) {
		Scanner copy = mdLib.reset();
		
		int word = 0;
		//THIS DOES NOT WORK YOU IDIOT WHAT ARE YOU THINKING THERE IS NO WAY THIS COULD EVER WORK YOU STUPID
		while(copy.hasNextLine()) {
			String line = copy.nextLine();
			int pos = 0;
			//CHANGE THIS ENTIRELY
			while(line.indexOf("<",pos) != -1) {
				line = line.substring(0, line.indexOf("<",pos)) + replace.get(word)
						+ line.substring(line.indexOf(word),pos);
				pos = line.indexOf("<",pos+1);
			}			
		}
		return copy;
		
	}
	
	/**
	 * 
	 * @param wordList list of the parts of speech
	 * @return ArrayList<String> of the new words to put into the story
	 */
	public static ArrayList<String> newWords(ArrayList<String> wordList){

		//JFrame frame = new JFrame("Gimme some words!!!");
		Scanner kybd = new Scanner(System.in);
		
		ArrayList<String> newWords = new ArrayList<String>();
		int i = 0;
		
		for(String s : wordList) {
			//newWords.add(JOptionPane.showInputDialog(frame, "Please input a " + wordList.get(i) ));
			System.out.println("Please input a " + wordList.get(i) );
			newWords.add(kybd.nextLine());
		}
		
		kybd.close();
		
		return newWords;
	}

}
