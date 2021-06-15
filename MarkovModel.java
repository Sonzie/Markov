import java.io.File;
import java.util.HashMap;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

/**
 * MarkovModel.java Creates an order K Markov model of the supplied source
 * text. The value of K determines the size of the "kgrams" used to generate
 * the model. A kgram is a sequence of k consecutive characters in the source
 * text.
 *
 * @author     Your Name (you@auburn.edu)
 * @author     Dean Hendrix (dh@auburn.edu)
 * @version    2018-04-17
 *
 */
public class MarkovModel {

   // Map of <kgram, chars following> pairs that stores the Markov model.
   private HashMap<String, String> model;
   private String firstGram;

   // add other fields as you need them ...


   /**
    * Reads the contents of the file sourceText into a string, then calls
    * buildModel to construct the order K model.
    *
    * DO NOT CHANGE THIS CONSTRUCTOR.
    *
    */
   public MarkovModel(int K, File sourceText) {
      model = new HashMap<>();
      try {
         String text = new Scanner(sourceText).useDelimiter("\\Z").next();
         buildModel(K, text);
      }
      catch (IOException e) {
         System.out.println("Error loading source text: " + e);
      }
   }


   /**
    * Calls buildModel to construct the order K model of the string sourceText.
    *
    * DO NOT CHANGE THIS CONSTRUCTOR.
    *
    */
   public MarkovModel(int K, String sourceText) {
      model = new HashMap<>();
      buildModel(K, sourceText);
   }


   /**
    * Builds an order K Markov model of the string sourceText.
    */
   private void buildModel(int K, String sourceText) {
      
      model = new HashMap<String, String>();
      firstGram = "";
      String kGram;
      String value;
      char nextChar = ' ';
      
      for (int i = 0; i <= sourceText.length() - K; i++)
      {
         //build kgram
         kGram = "";
         for (int j = i; j < i + K; j++)
         {
            kGram += sourceText.charAt(j);
            if (j + 1 < sourceText.length())
            {
               nextChar = sourceText.charAt(j + 1);
            }
            else
            {
               nextChar = '\u0000';
            }
         }
         // add first kgram
         if (firstGram == "")
         {
            firstGram = kGram;
         }
         
         value = model.get(kGram);
         if (value == null)
         {
            value = "" + nextChar;
            model.put(kGram, value);
         }
         else
         {
            value += nextChar;
            model.replace(kGram, value);
         }
        
      }
      
   
   }


   /** Returns the first kgram found in the source text. */
   public String getFirstKgram() {
      return firstGram;
   }


   /** Returns a kgram chosen at random from the source text. */
   public String getRandomKgram() {
      Set<String> keysSet = model.keySet();
      Object[] keys = keysSet.toArray();
      int rand = getRand(0, keysSet.size() - 1);
      String kGram = keys[rand].toString();
      return kGram;
   }
   
   /** returns random in in range. */
   private static int getRand(int min, int max)
   {
      if (min > max || (max - min + 1 > Integer.MAX_VALUE)) 
      {
         throw new IllegalArgumentException("Invalid range");
      }
   
      return new Random().nextInt(max - min + 1) + min;
   }


   /**
    * Returns the set of kgrams in the source text.
    *
    * DO NOT CHANGE THIS METHOD.
    *
    */
   public Set<String> getAllKgrams() {
      return model.keySet();
   }


   /**
    * Returns a single character that follows the given kgram in the source
    * text. This method selects the character according to the probability
    * distribution of all characters that follow the given kgram in the source
    * text.
    */
   public char getNextChar(String kgram) {
      String allNext = model.get(kgram);
      
      if(allNext == null || allNext.length() < 1)
      {
         return getRandomKgram().charAt(0);
      }
      
      int rand = getRand(0, allNext.length() - 1);
     
      return allNext.charAt(rand);
   }


   /**
    * Returns a string representation of the model.
    * This is not part of the provided shell for the assignment.
    *
    * DO NOT CHANGE THIS METHOD.
    *
    */
   @Override
    public String toString() {
      return model.toString();
   }

}
