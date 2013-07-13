package SlotGrammarParser;

import java.util.List;

public class IndexWord {

    String[] acceptedPhrases;
    public IndexWord(String... acceptedPhrases)
    {
        this.acceptedPhrases = acceptedPhrases;
    }
    // Returns a list of phrases covered by the index word
    public String[] getAcceptedPhrases() {
        // TODO Auto-generated method stub
        return acceptedPhrases;
    }

    public int getLength() {
        return toString().split(" ").length;
    }
    /**
     * This will probably not work
     * @return
     */
    public String toString() {

        return acceptedPhrases[0];
    }



}
