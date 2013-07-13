package SlotGrammarParser.SlotGrammarParser;

import SlotGrammarParser.ConcreteSenseFrame;
import SlotGrammarParser.IndexWord;
import SlotGrammarParser.abstractions.SenseFrame;
import java.util.ArrayList;
import java.util.List;

public class  LexicalEntry implements SlotGrammarParser.abstractions.LexicalEntry {

    // The index word is the root word for the lexical entry. It can be a multiword, or a single word.
    // The indexWord should know which are the possible forms of this word. For example, the index word
    // Run should accept ran, running, and possible will run.
    IndexWord indexWord;

    // The sense frames represent all possible "sense" the index word can take.
    List<SenseFrame> senseFrames;

    public LexicalEntry() {
        senseFrames = new ArrayList<SenseFrame>();
    }
    public LexicalEntry(IndexWord indexWord) {
        this.indexWord = indexWord;
        senseFrames = new ArrayList<SenseFrame>();
    }

    public void addSenseFrame(ConcreteSenseFrame sf) {
        sf.setIndexWord(indexWord);
        sf.setID(senseFrames.size()+1);
        senseFrames.add(sf);
    }

    // Returns a list of phrases the index word covers. This list will be used
    // To determine if the given phrase uses this lexical entry
    public List<String> getAcceptedPhrases() {
         List<String> list = new ArrayList<String>();
        list.add(indexWord.toString());
         return list;
        //return indexWord.getAcceptedPhrases();
    }

    public List<SenseFrame> getSenseFrames() {
        return senseFrames;
    }

    public IndexWord getIndexWord() {
        return indexWord;
    }
    public void setIndexWord(IndexWord word) {
        this.indexWord = word;
    }

    
     @Override
     public String getCitationWord() {
          throw new UnsupportedOperationException("Not supported yet.");
     }



}
