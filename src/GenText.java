// Name: Hongzhao Zhu
// USC loginid: hongzhaz
// CS 455 PA4
// Fall 2016

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class GenText {
   public static final int FIRST_CMD_ARG = 0; // the first commonld-line-argument index;
   public static final int SECOND_CMD_ARG = 1; // the second commonld-line-argument index;
   public static final int THIRD_CMD_ARG = 2; // the third commonld-line-argument index;
   public static final int FOURTH_CMD_ARG = 3; // the fourth commonld-line-argument index;
   public static final int FIFTH_CMD_ARG = 4; // the fifth commonld-line-argument index;
   public static final int DEBUG_ARG_NUM = 5; // the number of command-line arguments in debug mode;
   public static final int NORMAL_ARG_NUM = 4; // the number of command-line arguments in normal mode;
   public static final int LENGTH_LINE = 80; // the length of each line in outFile;

   public static void main(String[] args){
      try {
         if (args.length < NORMAL_ARG_NUM) {
            System.out.println("Command line format: java GenText [-d] prefixLength numWords sourceFile outFile");
            throw new IOException("ERROR: Missing command-line arguments.");
         }
         else if (args.length > DEBUG_ARG_NUM){
            throw new IOException("ERROR: Too many command-line arguments.");
         }
         // Debug mode;
         else if (args.length == DEBUG_ARG_NUM){
            if (!args[FIRST_CMD_ARG].equals("-d")){
               throw new IOException("ERROR: Wrong command line arguments: " + args[FIRST_CMD_ARG]);
            }
            else if (!isInteger(args[SECOND_CMD_ARG]) || !isInteger(args[THIRD_CMD_ARG])){
               System.out.println("Command line format: java GenText [-d] prefixLength numWords sourceFile outFile");
               throw new IOException("ERROR: prefixLength or numWords arguments are not integers: "
                       + args[SECOND_CMD_ARG] + " or " + args[THIRD_CMD_ARG]);
            }
            else{doDebugMode(args);}//Enter Debug mode;
         }
         // Normal mode;
         else if (args.length == NORMAL_ARG_NUM){
            if (!isInteger(args[FIRST_CMD_ARG]) || !isInteger(args[SECOND_CMD_ARG])){
               System.out.println("Command line format: java GenText [-d] prefixLength numWords sourceFile outFile");
               throw new IOException("ERROR: prefixLength or numWords arguments are not integers: "
                       + args[FIRST_CMD_ARG] + " or " + args[SECOND_CMD_ARG]);
            }
            else{doNormalMode(args);} //Enter Normal mode;
         }
      }
      catch (FileNotFoundException exc) {System.out.println("ERROR: Input file does not exist.");
      }
      catch (IOException exc) {exc.printStackTrace();}
   }

   /**
    * To check if the string is an Integer;
    * @param s this is the input string;
    * @return return true if the string is an Integer, otherwise return false;
    */
   public static boolean isInteger(String s){
      if (s.isEmpty()) return false;
      for (int i = 0; i < s.length(); i++){
         if (i == 0 && s.charAt(0) == '-');
         else if (!Character.isDigit(s.charAt(i))) return false;
      }
      return true;
   }

   /**
    * Enter the Debug mode
    * @param args command-line arguments;
    * @throws IOException
    */
   public static void doDebugMode(String[] args) throws IOException{
      int prefixLength = Integer.valueOf(args[SECOND_CMD_ARG]);
      int numWords = Integer.valueOf(args[THIRD_CMD_ARG]);
      String inFile = args[FOURTH_CMD_ARG];
      String outFile = args[FIFTH_CMD_ARG];
      boolean debug = true; // if it has command-line arguments "-d", set debug = true
                            // otherwise set debug = false;
      ArrayList<String> allWords; // store all words in an ArrayList;
      Prefix prefix; // create a Prefix object to store prefixes and successors;
      RandomTextGenerator textGenerator; // create a RandomTextGenerator object to get outputWords;
      ArrayList<String> outputWords;  // store output words into an ArrayList;

      if (prefixLength < 1) {
         System.out.println("Command line format: java GenText [-d] prefixLength numWords sourceFile outFile");
         throw new IOException("ERROR: prefixLength < 1.");
      }
      if (numWords < 0) {
         System.out.println("Command line format: java GenText [-d] prefixLength numWords sourceFile outFile");
         throw new IOException("ERROR: numWords < 0.");
      }
      // read data file, and convert data to ArrayList<String> format;
      allWords = readFile(inFile);
      if (allWords.size() <= prefixLength){
         throw new IOException("ERROR: prefixLength >= number of words in sourceFile.");
      }
      // create a Prefix object, and get all prefixes and successors;
      prefix = new Prefix(allWords,prefixLength);
      // get output words, and store them into an ArrayList;
      textGenerator = new RandomTextGenerator(prefix.getPrefixList(), prefix.getPrefixMap(),
              debug,numWords);
      outputWords = textGenerator.getOutputWords();
      // create a outFile and write generated words into it;
      writeFile(outFile, outputWords);
   }

   /**
    * Enter the Normal mode
    * @param args command-line arguments;
    * @throws IOException
    */
   public static void doNormalMode(String[] args) throws IOException{
      int prefixLength = Integer.valueOf(args[FIRST_CMD_ARG]);
      int numWords = Integer.valueOf(args[SECOND_CMD_ARG]);
      String inFile = args[THIRD_CMD_ARG];
      String outFile = args[FOURTH_CMD_ARG];
      boolean debug = false; // if it has command-line arguments "-d", set debug = true
                             // otherwise set debug = false;
      ArrayList<String> allWords; // store all words in an ArrayList;
      Prefix prefix; // create a Prefix object to store prefixes and successors;
      RandomTextGenerator textGenerator; // create a RandomTextGenerator object to get outputWords;
      ArrayList<String> outputWords;  // store output words into an ArrayList;

      if (prefixLength < 1) {
         System.out.println("Command line format: java GenText [-d] prefixLength numWords sourceFile outFile");
         throw new IOException("ERROR: prefixLength < 1.");
      }
      if (numWords < 0) {
         System.out.println("Command line format: java GenText [-d] prefixLength numWords sourceFile outFile");
         throw new IOException("ERROR: numWords < 0.");
      }
      // read file;
      allWords = readFile(inFile);
      if (allWords.size() <= prefixLength){
         throw new IOException("ERROR: prefixLength >= number of words in sourceFile.");
      }
      // create a Prefix object, and get all prefixes and successors;
      prefix = new Prefix(allWords,prefixLength);
      // get output words, and store them into an ArrayList;
      textGenerator = new RandomTextGenerator(prefix.getPrefixList(), prefix.getPrefixMap(),
              debug,numWords);
      outputWords = textGenerator.getOutputWords();
      // create a outFile and write generated words into it;
      writeFile(outFile, outputWords);
   }

   /**
    * Read words in a file;
    * @param inFileName this is the input filename;
    * @return an ArrayList that contains all words;
    * @throws IOException;
    */
   public static ArrayList<String> readFile(String inFileName) throws IOException {
      Scanner in = new Scanner(new File(inFileName));
      ArrayList<String> allWords= new ArrayList<>();
      try{
         String currentWord;
         while(in.hasNext()){
            currentWord = in.next();
            allWords.add(currentWord);
         }
      }
      finally {in.close();}
      return allWords;
   }

   /**
    * Write generated words into a file;
    * @param outFileName output file name;
    * @param outputWords the list of generated words.
    * @throws IOException
    */
   public static void writeFile(String outFileName, ArrayList<String> outputWords) throws IOException{
      PrintWriter out = new PrintWriter(outFileName);
      try{
         int currentLenOfLine = 0;
         //print 80 characters in order to count;
         for (int i = 0; i < 8; i++){
            for (int j = 0; j <= 9; j++){
               out.print(j);
            }
         }
         out.print('\n');

         for(int i = 0; i < outputWords.size(); i++){
            String currentWord = outputWords.get(i);
            // if the length of current line exceeds 80 characters after adding current word and a withespace;
            // we print a carriage return '\n', and make currentLenOfLine =0;
            if (currentLenOfLine + currentWord.length() + 1 > LENGTH_LINE) {
               out.print('\n');
               currentLenOfLine = 0;
            }
            else if (currentLenOfLine != 0){
               out.print(' ');
               currentLenOfLine++;
            }
            out.print(currentWord); // print current word;
            currentLenOfLine += currentWord.length();
         }
      }
      finally {out.close(); }
   }
}
