// Name: Hongzhao Zhu
// USC loginid: hongzhaz
// CS 455 PA4
// Fall 2016

import java.util.ArrayList;
import java.util.HashMap;

public class Prefix {
   // prefixList contains all prefixes, which is a string list;
   // this prefixList is used to choose a prefix randomly based on its probability.
   // the size of the prefixList is allWords.size() - prefixLength + 1;
   private ArrayList<ArrayList<String>> prefixList;
   // prefixMap-->HashMap<ArrayList<String> prefix, ArrayList<String> successor>;
   private HashMap<ArrayList<String>, ArrayList<String>> prefixMap;

   /**
    * Construct a Prefix object.
    * @param allWords an ArrayList<String> that contains all words;
    * @param prefixLength an integer that is the length of the prefix;
    */
   public Prefix (ArrayList<String> allWords, int prefixLength){
      prefixList = new ArrayList<>();
      prefixMap = new HashMap<>();
      createPrefix(allWords, prefixLength);
   }

   /**
    * Create an Prefix object, and initialize prefixList and prefixMap;
    * @param allWords an ArrayList<String> that contains all words;
    * @param prefixLength an integer that is the length of the prefix;
    */
   private void createPrefix (ArrayList<String> allWords,
                                                       int prefixLength){
      // the size of the prefixList is allWords.size() - prefixLength;
      for (int i = 0; i < allWords.size() - prefixLength; i++){
         ArrayList<String> prefix = new ArrayList<>();
         ArrayList<String> successor = new ArrayList<>();
         for (int j = 0; j < prefixLength; j++){
            prefix.add(allWords.get(i + j));
         }
         prefixList.add(prefix);
         // add the successor word;
         successor.add(allWords.get(i + prefixLength));
         // if prefixMap contains the prefix, add successor-word to ArrayList successor;
         // otherwise put a new element to prefixMap;
         if (prefixMap.containsKey(prefix)){
            ArrayList<String> newSuccessors = new ArrayList<>(prefixMap.get(prefix));
            newSuccessors.addAll(successor);
            prefixMap.put(prefix,newSuccessors);
         }
         else{
            prefixMap.put(prefix, successor);
         }
      }
   }

   /**
    * Return a clone of the prefixList;
    * @return ArrayList<ArrayList<String>> prefixList;
    */
   public ArrayList<ArrayList<String>> getPrefixList(){
      return new ArrayList<ArrayList<String>> (prefixList);
   }

   /**
    * Return a clone of the prefixMap;
    * @return HashMap<ArrayList<String>, ArrayList<String>> prefixMap;
    */
   public HashMap<ArrayList<String>, ArrayList<String>> getPrefixMap(){
      return new HashMap<ArrayList<String>, ArrayList<String>> (prefixMap);
   }


}
