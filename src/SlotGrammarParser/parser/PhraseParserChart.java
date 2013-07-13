package SlotGrammarParser.parser;

import SlotGrammarParser.abstractions.Phrase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Anthony
 * Date: 8/18/12
 * Time: 7:46 PM
 * To change this template use File | Settings | File Templates.
 */
public class PhraseParserChart {

    private List<Phrase> phrases;
    private int chartLeftBoundary;
    private int chartRigthBoundary;

    public PhraseParserChart() {

        chartLeftBoundary = 0;
        chartRigthBoundary = 0;
        phrases = new ArrayList<Phrase>();

    }

    public List<Phrase> getPhrases() {
        return phrases;
    }

    public String toString() {
         StringBuilder stringBuilder = new StringBuilder();
        //stringBuilder.append("Chart: \n");
         for(Phrase phrase : phrases) {
             stringBuilder.append("\t" + phrase + "\n");
         }

        return stringBuilder.toString();
    }

    /**
     *  Get all phrases in the resulting parse of phrases which extend the entire chart
     * @return all phrases in phrases which cover all added phrases
     */
   /* public List<Phrase> getCompletePhrases() {

       // System.out.println("List<Phrase> getCompletePhrases()");
        List<Phrase> completePhrases = new ArrayList<Phrase>();
        for(Phrase phrase : phrases) {
            if( (chartLeftBoundary == phrase.getLeftBoundary()) && (chartRigthBoundary== phrase.getRightBoundary())) {
                completePhrases.add(phrase);
            }
        }

       // System.out.println(Arrays.toString(completePhrases.toArray()));
        return completePhrases;


    }   */

    public void add(Phrase phrase) {
        System.out.printf("\t\tChart Adding: %s\n", phrase);

        System.out.println("\t\tChart: ");
        for(int i = 0;i<phrases.size();i++) {
            System.out.printf("\t\t\t[%d] %s\n", i,phrases.get(i));
        }

        phrases.add(phrase);


        // System.out.println("\n\nPhraseParserChart.naddPhrase()");

      //  System.out.println("-----------------------------------------------------------------------------------------------------------");
       // System.out.println("Incomming Phrase: " + phrase);

      //  System.out.println("Chart:");
       // for(Phrase chartPhrase : phrases) {
        //    System.out.println(chartPhrase);
       // }

        // Adjust the Charts left and right boundaries
        if(chartLeftBoundary<phrase.getLeftBoundary()) {
            chartLeftBoundary = phrase.getLeftBoundary();
        }

        if(chartRigthBoundary> phrase.getRightBoundary()) {
            chartRigthBoundary = phrase.getRightBoundary();
        }

         List<Phrase> adjacentLeftPhrases = getPhrasesAdjacentLeft(phrase);

         List<Phrase> newPhrases;
        // Now that we have a list of phrases adjacent to the left, check each to see if we can combine with the
        // current incomming phrase, if we can create a new phrase with it, and call recursivly
        for(Phrase adjacentPhrase : adjacentLeftPhrases) {

          /*  System.out.println("\t\tChart: Adjacent Phrase Found: "+ adjacentLeftPhrases);

            List<Phrase> combinedPhrases = adjacentPhrase.combine(adjacentPhrase, phrase);

            if(combinedPhrases.size()>0) {
                System.out.println("\t\tChart: Add New Combined Phrases Recursively: " + combinedPhrases);
                phrases.addAll(combinedPhrases);
                for(Phrase newPhrase : combinedPhrases) {
                    add(newPhrase);
                }

            } else {
                System.out.println("\t\tChart: Could not combine with existing Phrases");
            }  */


        }
    }

            /*if(Phrase.canCombine(adjacentPhrase, phrase)) {
                System.out.println("Chart: Phraes can combine.");

                 newPhrases = Phrase.combine(adjacentPhrase, phrase);

           //     System.out.printf("\n\t%s -->  <-- %s\n", adjacentPhrase, phrase);
            //    System.out.println("New Phrases Created: " + newPhrases );
                // Add all new phrases to phrase list, and add phrase recursivly
                for(Phrase newPhrase : newPhrases) {
                    phrases.add(newPhrase);
                    add(newPhrase);
                }
            } else {
           //     System.out.printf("\n\t%s --> X <--  %s\n", adjacentPhrase, phrase);  */
      //      }
      //  }

      //  phrases.add(phrase);

      ////  System.out.println("\n\n");
      //  System.out.println("-----------------------------------------------------------------------------------------------------------");
  //  }

    /**
     * Returns phrases that are adjacent on the left. This Means that the incomming phrase's
     * leftBoundary ==  the chart phrases right boundary
     *
     * @param phrase
     * @return
     */
    private List<Phrase> getPhrasesAdjacentLeft(Phrase phrase) {

        List<Phrase> adjacentLeftPhrases = new ArrayList<Phrase>();
        for(Phrase chartPhrase : phrases) {

            if(chartPhrase.getRightBoundary() == phrase.getLeftBoundary()) {
           //     System.out.printf("\t%s %d ]   [ %d %s\n",chartPhrase,chartPhrase.getRightBoundary(),phrase.getLeftBoundary(),phrase);
                adjacentLeftPhrases.add(chartPhrase);
            } else {
           //     System.out.printf("\t%d ] X [ %d\n",chartPhrase.getRightBoundary(),phrase.getLeftBoundary());
            }
        }

        return adjacentLeftPhrases;
    }


}
