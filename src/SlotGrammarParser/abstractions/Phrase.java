/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package SlotGrammarParser.abstractions;


import SlotGrammarParser.abstractions.Feature;
import java.util.List;

/**
 *
 * @author Anthony
 */
public interface Phrase {


     public abstract int getWordNumber();
     
     public abstract List<Slot> getComplementSlots();
     
     public abstract List<Slot> getAvailableComplementSlots();

    public abstract List<Slot> getSlots();
     
     public abstract List<Slot> getAdjunctSlots();
     
     
     /**
      * Returns a list of available slots, that is the list of 
      * available complement slots and all adjunct slots
      * @return 
      */
     public abstract List<Slot> getAvailableSlots();
     
     /**
      * Returns all slots that can be created by filling the current
      * phrase with the fillerPhrase
      * @param fillerPhrase
      * @return 
      */
     public abstract List<Phrase> fillWith(Phrase fillerPhrase);
     
     
     public abstract List<Feature> getFeatures();
     
     public abstract Feature getPartOfSpeech();

    String getSenseName();

    int getRightBoundary();

    int getLeftBoundary();

    public String getOriginalText();

     public Phrase clone();

     public void addScore(int score);


    int getFilledSlots();

    void setLeftBoundary(int leftBoundary);

    void setRightBoundary(int rightBoundary);


    int getParseScore();

    public abstract List<Phrase> getFilledPhrases();
}
