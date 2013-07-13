/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package SlotGrammarParser.abstractions;


import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Anthony
 */
public interface SenseFrame {
     
     public abstract String getSenseName();
     
     public abstract List<Feature> getFeatures();
     
     public abstract Feature getPartOfSpeech();
     
     public abstract List<Slot> getComplementSlots();
     
     public abstract List<Slot> getAdjunctSlots();
     
     public abstract int getID();

    public List<Slot> slots = new ArrayList<Slot>();
}
