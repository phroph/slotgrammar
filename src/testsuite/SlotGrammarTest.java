package testsuite;/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import SlotGrammarParser.abstractions.Feature;
import SlotGrammarParser.parser.PhraseParserChart;
import SlotGrammarParser.parser.SlotGrammarParser;
import java.util.List;

import SlotGrammarParser.abstractions.Phrase;
import SlotGrammarParser.parser.SlotGrammarPhraseChart;

/**
 *
 * @author Anthony
 */
public class SlotGrammarTest {
     
     public SlotGrammarTest() {
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
     // TODO add test methods here.
     // The methods must be annotated with annotation @Test. For example:
     //
     @Test
     public void test() throws Exception {
          
          SlotGrammarParser parser = new SlotGrammarParser();
          String input = "Mary gave John a ball.";
          
          // The first phrase of the parser is to generate the candidate
          // string. For this example they are the following:
          List<String> candidateStrings = SlotGrammarParser.listPhraseStringCandidates(input);
          //  Mary gave John a ball
          assert(candidateStrings.size()==15);
          assert(candidateStrings.get(0).equals("Mary gave John a ball"));
          assert(candidateStrings.get(1).equals("Mary gave John a"));
          assert(candidateStrings.get(2).equals("Mary gave John"));
          assert(candidateStrings.get(3).equals("Mary gave"));
          assert(candidateStrings.get(4).equals("Mary"));
          assert(candidateStrings.get(5).equals("gave John a ball"));
          assert(candidateStrings.get(6).equals("gave John a"));
          assert(candidateStrings.get(7).equals("gave John"));
          assert(candidateStrings.get(8).equals("gave"));
          assert(candidateStrings.get(9).equals("John a ball"));
          assert(candidateStrings.get(10).equals("John a"));
          assert(candidateStrings.get(11).equals("John"));
          assert(candidateStrings.get(12).equals("a ball"));
          assert(candidateStrings.get(13).equals("a"));
          assert(candidateStrings.get(14).equals("ball"));      
          
          // The reason we want to generate the candidates in this way
          // is because we do not know which group of words form the 
          // optimal starter phrases, so we must consider each.
          // Consider "The Cat in the Hat slipped on the mat". We 
          // want to consider the pronoun "The Cat in the Hat" as well
          // as "The","Cat","in".. etc.
         
          // We now go on to generate the starter phrases
          List<Phrase> starterPhrases = parser.extractSentenceStarterPhrases(input);
          assert(starterPhrases.size()==5);
          
          // The following starter phrases should be found
          //        Phrase               Text     Left Bound     Right Bound    Part Of Speech    
          testPhrase(starterPhrases.get(0), "Mary",               0,                1,           "Pronoun");
          testPhrase(starterPhrases.get(1), "gave",               1,                2,           "Verb");
          testPhrase(starterPhrases.get(2), "John",               2,                3,           "Pronoun");
          testPhrase(starterPhrases.get(3), "a"   ,               3,                4,           "Noun Determiner");
          testPhrase(starterPhrases.get(4), "ball",               4,                5,           "Noun");
   
          // We now know we have the correct starter phrases. We can 
          // now begin building our phrase chart          
          SlotGrammarPhraseChart phraseChart = new SlotGrammarPhraseChart();
          
          // Add the first starter phrase, Mary
          phraseChart.addPhrase(starterPhrases.get(0));
          
          // The chart should contain extactly one phrase now
          assert(phraseChart.size()==1);
          
          // Now we want to add the second phrase, gave, to the chart
          phraseChart.addPhrase(starterPhrases.get(1));
         phraseChart.addPhrase(starterPhrases.get(2));
         phraseChart.addPhrase(starterPhrases.get(3));
         phraseChart.addPhrase(starterPhrases.get(4));
          
          // At this point we should have three phrases in total in the
          // chart. The first phrase is Mary, the second gave, and the
          // third is gave with Mary in its subject slot.
          
          //                     Phrase    Text     Left Bound      Right Bound   Filled Slots
          testPhraseChart(phraseChart.get(0), "Mary",                0,                1,               0);
          testPhraseChart(phraseChart.get(2), "gave",                1,                2,               0);
          testPhraseChart(phraseChart.get(1), "gave",                0,                2,               1);
          for(Phrase p : phraseChart.getPhrases())
          {
              System.out.println(p.toString());
          }

          for(Phrase p : phraseChart.getPhrases())
          {
              if(p.getParseScore() == 4)
              {
                  System.out.println(p.toString());
              }
          }
     
     }

     private void testPhrase(Phrase phrase, String originalText, int leftBound, int rightBound, String partOfSpeech) {
          
          String originalTxt = phrase.getOriginalText();
          int lBound = phrase.getLeftBoundary();
          int rBound = phrase.getRightBoundary();
          //System.out.println(originalTxt + " " + lBound + " " + rBound);
          Feature fPOS = phrase.getPartOfSpeech();
          
          assertEquals(originalText,originalTxt);
          assertEquals(leftBound, lBound);
          assertEquals(rightBound, rBound);
          //assertEquals(partOfSpeech, fPOS);
          
    
     }

     private void testPhraseChart(Phrase phrase, String originalText, int leftBound, int rightBound, int filledSlots) {
          assertEquals(originalText, phrase.getOriginalText());
          //assertEquals(leftBound, phrase.getLeftBoundary());
          //assertEquals(rightBound, phrase.getRightBoundary());
          //assertEquals(filledSlots, phrase.getFilledSlots());
     }
   
}
