/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package SlotGrammarParser.test;

import SlotGrammarParser.Dictionary.ConcreteDictionary;
import SlotGrammarParser.Dictionary.WordHasNoSenseFramesException;
import SlotGrammarParser.Dictionary.WordNotFoundException;
import SlotGrammarParser.abstractions.Dictionary;
import SlotGrammarParser.abstractions.LexicalEntry;

import java.io.File;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Anthony
 */
public class DictionaryTest {
     
     public DictionaryTest() {
     }

     @BeforeClass
     public static void setUpClass() throws Exception {
     }

     @AfterClass
     public static void tearDownClass() throws Exception {
     }
     
     @After
     public void tearDown() {
     }
  
     @Test
     // Test the dictionary to ensure it can lookup different tenses
     // of a word
     public void tenseTest() {

         try
         {
          Dictionary dictionary = new ConcreteDictionary(new File("test.txt"));

          LexicalEntry tense1;
          LexicalEntry tense2;
          LexicalEntry tense3;

              tense1 = dictionary.lookup("given");
              tense2 = dictionary.lookup("given");
              tense3 = dictionary.lookup("giving");
              
              // Assert the tenses return the same citation (give)
              assertEquals(tense1.getCitationWord(), "give");
              assertEquals(tense2.getCitationWord(), "give");
              assertEquals(tense3.getCitationWord(), "give");
              
              
          } catch (WordNotFoundException ex) {
               assert(false);
              // Logger.getLogger(DictionaryTest.class.getName()).log(Level.SEVERE, null, ex);
          } catch (WordHasNoSenseFramesException ex) {
               assert(false);
               //Logger.getLogger(DictionaryTest.class.getName()).log(Level.SEVERE, null, ex);
          }
         catch (Exception e)
         {
             assert(false);
         }
          
          
     }
    /* 
     @Test
     public void Mary_gave_John_a_ball_lookup() {
          IDictionary dictionary = new Dictionary(new File("test.txt"));
          try {
               ILexicalEntry Mary = dictionary.lookup("Mary");
               ILexicalEntry gave = dictionary.lookup("Mary");
               ILexicalEntry John = dictionary.lookup("Mary");
               ILexicalEntry a = dictionary.lookup("Mary");
               ILexicalEntry ball = dictionary.lookup("Mary");
               
               assert(Mary!=null);
               assert(gave!=null);
               assert(John!=null);
               assert(a!=null);
               assert(ball!=null);
               
               
               // Test sense frames
               assertEquals(1, Mary.getSenseFrames().size());
               assertEquals(1, gave.getSenseFrames().size());
               assertEquals(1, John.getSenseFrames().size());
               assertEquals(1, a.getSenseFrames().size());
               assertEquals(1, ball.getSenseFrames().size());
               
               // Test citation forms
               assertEquals("Mary1", Mary.getSenseFrames().get(0).getSenseName());
               assertEquals("gave1", gave.getSenseFrames().get(0).getSenseName());
               assertEquals("John1", John.getSenseFrames().get(0).getSenseName());
               assertEquals("a1", a.getSenseFrames().get(0).getSenseName());
               assertEquals("ball1", ball.getSenseFrames().get(0).getSenseName());
               
               
               
          } catch (WordNotFoundException ex) {
               assert(false);               
              // Logger.getLogger(DictionaryTest.class.getName()).log(Level.SEVERE, null, ex);
          } catch (WordHasNoSenseFramesException ex) {
               assert(false);
              // Logger.getLogger(DictionaryTest.class.getName()).log(Level.SEVERE, null, ex);
          }
        
          
          
     
     }*/
}
