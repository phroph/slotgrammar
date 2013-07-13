/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package SlotGrammarParser.abstractions;


import SlotGrammarParser.Dictionary.WordHasNoSenseFramesException;
import SlotGrammarParser.Dictionary.WordNotFoundException;

/**
 *
 * @author Anthony
 */
public interface SentenceTokenizer {

    public TokenizedSentence tokenize(String input);

}
