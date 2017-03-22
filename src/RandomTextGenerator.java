// Name: Hongzhao Zhu
// USC loginid: hongzhaz
// CS 455 PA4
// Fall 2016

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class RandomTextGenerator {
   private ArrayList<String> outputWords; // contains all words that is generated;
   private ArrayList<String> currentPrefix; // store the current prefix chosen;
   private ArrayList<ArrayList<String>> prefixList;
   private HashMap<ArrayList<String>, ArrayList<String>> prefixMap;
   private boolean debug;
   private int numWords;
   private Random randomNum; // Random object to generate random integer;

   /**
    * Construct a RandomTextGenerator object;
    * @param prefixList contains all prefix;
    * @param prefixMap contains the HashMap from prefix to successor;
    * @param debug debug flag is true when command-line args has the [-d];
    * @param numWords the number of words to generate;
    */
   public RandomTextGenerator(ArrayList<ArrayList<String>> prefixList,
                              HashMap<ArrayList<String>, ArrayList<String>> prefixMap,
                              boolean debug, int numWords){
      this.prefixList = prefixList;
      this.prefixMap = prefixMap;
      this.debug = debug;
      this.numWords = numWords;

      if (debug){
         this.randomNum = new Random(1);
      }
      else{
         this.randomNum = new Random();
      }

      this.currentPrefix = choosePrefix();
      this.outputWords = genOutputWords();
   }

   /**
    * Choose a new prefix randomly from the prefixList;
    * @return a new prefix;
    */
   private ArrayList<String> choosePrefix (){
      ArrayList<String> newPrefix = new ArrayList<>(
              prefixList.get(randomNum.nextInt(prefixList.size())));
      if (debug){
         System.out.println("DEBUG: chose a new initial prefix: " + newPrefix.toString());
      }
      return newPrefix;
   }

   /**
    * Generate a new word based on currentPrefix;
    * @param currentPrefix the basic prefix that a new generated word depends on;
    * @return a new generated word;
    */
   private String genWord(ArrayList<String> currentPrefix){
      String newWord = "";
      ArrayList<String> successor = prefixMap.get(currentPrefix);
      newWord = successor.get(randomNum.nextInt(successor.size()));
      if (debug){
         System.out.println("DEBUG: successors: " + successor.toString());
         System.out.println("DEBUG: word generated: " + newWord);
      }

      return newWord;
   }

   /**
    * Generate an ArrayList<String> outputWords that contains all the words that are about
    * to print into outFile.
    * @return an ArrayList<String> outputWords
    */
   private ArrayList<String> genOutputWords(){
      ArrayList<String> outputWords = new ArrayList<>();
      String newWord;
      while (outputWords.size() < numWords){
         if (debug){
            System.out.println("DEBUG: prefix: " + currentPrefix.toString());
         }
         if (prefixMap.containsKey(currentPrefix)){
            newWord = genWord(currentPrefix);
            outputWords.add(newWord);
            //create a clone of currentPrefix to avoid the mutation of original prefix;
            currentPrefix = new ArrayList<String> (currentPrefix);
            //shift to the next prefix;
            currentPrefix.remove(0);
            currentPrefix.add(newWord);
         }
         else{
            if (debug){
               System.out.println("DEBUG: successors: <END OF FILE>");
            }
            currentPrefix = choosePrefix();
         }
      }
      return outputWords;
   }

   /**
    * Return the outputWords
    * @return an ArrayList<String> that contains all output words;
    */
   public ArrayList<String> getOutputWords(){
      return new ArrayList<>(outputWords);
   }

}
