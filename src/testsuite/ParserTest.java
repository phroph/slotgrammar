package testsuite;

import SlotGrammarParser.Dictionary.ConcreteDictionary;
import SlotGrammarParser.Dictionary.WordHasNoSenseFramesException;
import SlotGrammarParser.Dictionary.WordNotFoundException;
import SlotGrammarParser.abstractions.Dictionary;
import SlotGrammarParser.abstractions.Phrase;
import SlotGrammarParser.parser.PhraseParser;
import org.junit.Test;

import java.io.File;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Anthony
 * Date: 8/19/12
 * Time: 1:09 AM
 * To change this template use File | Settings | File Templates.
 */
public class ParserTest {

    @Test
    public void MaryGaveABookToJohn() throws WordHasNoSenseFramesException, WordNotFoundException {

        String input = "Mary gave a book to John.";

        System.out.println("----------------------------------------------------------------------------------------");
        System.out.println("Input: " + input);

                      try
                      {
        PhraseParser parser = new PhraseParser(new ConcreteDictionary(new File("test.txt")));

        // Get the results from the parser
        List<Phrase> resultPhrases = parser.parseText(input);
        assert resultPhrases!=null;

        System.out.println("\n[--- [Results] ------------------------------------------");
        for(int i = 0;i<resultPhrases.size();i++) {

            System.out.printf(" %d. %s\n", i, resultPhrases.get(i));
        }
                      }
                      catch(Exception e)
                      {
                          assert(false);
                      }






    }
}
