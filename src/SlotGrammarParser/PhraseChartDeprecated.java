package SlotGrammarParser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Anthony
 * Date: 8/15/12
 * Time: 12:42 AM
 * To change this template use File | Settings | File Templates.
 */
public class PhraseChartDeprecated {

    /*private List<parser.Phrase> phraseList;

    public PhraseChart() {

         phraseList = new ArrayList<parser.Phrase>();


    }


    public void add(parser.Phrase newPhrase) {

        List<parser.Phrase> adjacentPhrases = parser.Phrase.getAdjacentPhrases(phraseList, newPhrase);

        // for each adjacent phrase
        for(parser.Phrase adjacentListPhrase : adjacentPhrases) {

            // If the existing phrase in the list accepts the new phrase, we add the new phrase into
            // the adjacentListPhrases slot, and we can return, leaving the list in tact.
            if(adjacentListPhrase.accepts(newPhrase)) {
               adjacentListPhrase.fill(newPhrase);

               return;
            }

            // If the new phrase accepts one of the phrases in the list, then we must remove the phrase from the list,
            // put it itno the new phrase, and put the new phrase back in the list (recursivly)
            if(newPhrase.accepts(adjacentListPhrase)) {
                phraseList.remove(adjacentListPhrase);
                newPhrase.fill(adjacentListPhrase);
                add(newPhrase);
            }

            // Remove adjacent phrase from list
            phraseList.remove(adjacentPhrases);



        }
        // if we could not fill any of the existing slots, simply add the new slot to the list.
        phraseList.add(newPhrase);


    }    */







}
