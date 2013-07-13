/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package SlotGrammarParser.abstractions;

import SlotGrammarParser.ConcreteSenseFrame;

import java.util.List;

/**
 *
 * @author Anthony
 */
public interface LexicalEntry {
     
     public abstract List<SenseFrame> getSenseFrames();
     
     public abstract String getCitationWord();
     
     public abstract List<String> getAcceptedPhrases();
 }
