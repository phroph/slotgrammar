/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package SlotGrammarParser.abstractions;

import SlotGrammarParser.SlotOption;

import java.util.List;

/**
 *
 * @author Anthony
 */
public interface Slot  {

     public abstract Phrase getParent();

     public abstract String getSlotType();
     
     public abstract boolean isComplementSlot();
     
     public abstract boolean isAdjunctSlot();
     
     public abstract List<SlotGrammarParser.abstractions.SlotOption> getSlotOptions();

     public boolean accepts(Phrase child);

     // Puts the child into the slot, and returns the score resulting
     // in doing so
     public int put(Phrase child);

    public Slot clone();

    int getFillCount();

    void setParent(Phrase newPhrase);

    Phrase getContents();
}
