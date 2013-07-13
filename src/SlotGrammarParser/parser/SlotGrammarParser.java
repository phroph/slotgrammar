package SlotGrammarParser.parser;



/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import SlotGrammarParser.Dictionary.ConcreteDictionary;
import SlotGrammarParser.Dictionary.WordHasNoSenseFramesException;
import SlotGrammarParser.Dictionary.WordNotFoundException;
import SlotGrammarParser.abstractions.*;
import SlotGrammarParser.parser.ConcretePhrase;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Test;
/**
 *
 * @author Anthony
 */
public class SlotGrammarParser {
     
     public SlotGrammarParser() throws Exception {
          parserDictionary = new ConcreteDictionary(new File("C:\\test.txt"));
     }
     
     private final Dictionary parserDictionary;
     private static final Character[] PUNCTUATION = {'.'};
     
   
     // Find all possible starter phrases that can be extracted 
     // from the input sentence. A single word might be in more than
     // one phrase. For example, "The Cat in the Hat slipped on the 
     // mat". The Cat in the Hat might return a single pronoun, 
     // additionally "The", "Cat", "in", "the", and "Hat" would also
     // each be a starter phrase.
     @Test
     public void catInTheHatExtractionTest() {
          try {
               Dictionary d = new ConcreteDictionary(new File("test.txt"));
               List<Phrase> starterPhrases = extractSentenceStarterPhrases("The Cat in the Hat slipped on the mat."); 
               
               List<String> resultPhraseStrings = new ArrayList<String>();
               resultPhraseStrings.add("The cat in the Hat");
               
          } catch (Exception ex) {               
               Logger.getLogger(SlotGrammarParser.class.getName()).log(Level.SEVERE, null, ex);
          }
         
         
         
     }     
     @Test
     public void maryGaveJohnABookTest() {
          List<Phrase> starterPhrases = extractSentenceStarterPhrases("Mary gave John a ball");
          assert(starterPhrases.size()==5);
     }
     public List<Phrase> extractSentenceStarterPhrases(String input) {
          List<String> sentenceCandidates = listPhraseStringCandidates(input);
          
          List<Phrase> resultPhrases = new ArrayList<Phrase>();
          for(String candidate : sentenceCandidates) {
               try {
                    LexicalEntry entry = parserDictionary.lookup(candidate);
                    List<SenseFrame> frames = entry.getSenseFrames();
                    int candidateIndex = input.indexOf(" " + candidate);
                    if(candidateIndex==-1) { candidateIndex=0;};
                    int leftBoundary = input.substring(0, candidateIndex).split(" ").length;
                         
                    if(candidateIndex==0) { leftBoundary--;}
                    for(SenseFrame currentFrame : frames) {
                     
                     //   int phraseIndex = input.indexOf(candidate);
                         //                  ConcretePhrase(String originalString, String senseNAme, List<Feature> features, int wordNumber, int leftBoundary,                  int rightBoundary, List<Slot> complementSlots, List<Slot> adjunctSlots)  
                         Phrase newPhrase = new ConcretePhrase(candidate, "",  currentFrame.getFeatures(),     leftBoundary,         leftBoundary,leftBoundary+candidate.split(" ").length, currentFrame.getComplementSlots(), currentFrame.getAdjunctSlots(), currentFrame.getPartOfSpeech());
                        for(Slot s : newPhrase.getSlots())
                        {
                            s.setParent(newPhrase);
                        }
                        resultPhrases.add(newPhrase);
                    }
               } catch (WordNotFoundException ex) {
                    Logger.getLogger(SlotGrammarParser.class.getName()).log(Level.SEVERE, null, ex);
               } catch (WordHasNoSenseFramesException ex) {
                    Logger.getLogger(SlotGrammarParser.class.getName()).log(Level.SEVERE, null, ex);
               }
          }
          
          return resultPhrases;
     }

     
     // We want to list all possible phrases for a given input. 
     // For now we are handling punctuation by simply removing it. 
     // All possible phrases are generated as in the following example
     // Lets say the input string is "A B C.". Candidates are generated
     // in this order
     // A B C
     // A B
     // A
     // B C
     // B
     // C
     @Test 
     public void abcTest() {
          String input = "A B C.";
          List<String> splitInput = listPhraseStringCandidates(input);
          assert(splitInput.size()==6);
          assert("A B C".equals(splitInput.get(0)));
          assert("A B".equals(splitInput.get(1)));
          assert("A".equals(splitInput.get(2)));
          assert("B C".equals(splitInput.get(3)));
          assert("B".equals(splitInput.get(4)));
          assert("C".equals(splitInput.get(5)));
     }
     public static List<String> listPhraseStringCandidates(String input) {
      
          // Strip punctuation
          String strippedInput = null;
          for(Character c : PUNCTUATION) {               
              strippedInput =  input.replace(c.toString(), "");
          }
          
          List<String> candidates = new ArrayList<String>();
          String[] words = strippedInput.trim().split(" ");
          StringBuilder newWord;
          // Generate Candidates
          // Left bound
          for(int l = 0;l<words.length;l++) {
              
               for(int r = words.length-1;r>=l;r--) {
                     newWord = new StringBuilder();
                     for(int i = l;i<=r;i++) {
                         newWord.append(words[i] + " "); 
                     }
                     candidates.add(newWord.toString().trim());
               }
               
              
                   
          }
          
     
          return candidates;
     }
}
