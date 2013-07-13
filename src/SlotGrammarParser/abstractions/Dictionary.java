/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package SlotGrammarParser.abstractions;

import SlotGrammarParser.Dictionary.WordHasNoSenseFramesException;
import SlotGrammarParser.Dictionary.WordNotFoundException;

import java.util.List;

/**
 *
 * @author Anthony
 */
public interface Dictionary {
     
    public abstract LexicalEntry lookup(String input) throws WordNotFoundException, WordHasNoSenseFramesException;

    public abstract List<LexicalEntry> getLexicalEntryList();
}
