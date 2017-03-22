import java.util.ArrayList;

/**
 * Created by Hunting on 11/13/2016.
 */
public class PrefixTestor {
   public static void main(String[] args){
      int prefixLength = 2;
      ArrayList<String> allWords = new ArrayList<>();
      String word = "";

      for (int i = 0; i < 5; i++){
         word = word + "a";
         allWords.add(word);
      }

      //word = "";
      //for (int i = 0; i < 5; i++){
      //   word = word + "a";
      //   allWords.add(word);
      //}

      Prefix prefixTestor = new Prefix(allWords, prefixLength);

      System.out.println(prefixTestor.getPrefixList().toString());
      System.out.println();
      System.out.println(prefixTestor.getPrefixMap().toString());

   }
}
