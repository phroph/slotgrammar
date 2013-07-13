package testsuite;

import SlotGrammarParser.Dictionary.ConcreteDictionaryParser;
import SlotGrammarParser.SlotGrammarParser.LexicalEntry;
import org.junit.Test;

import java.io.File;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Phillip
 * Date: 8/16/12
 * Time: 1:09 AM
 * To change this template use File | Settings | File Templates.
 */
public class DictParserTest {
    @Test
    public void TestParser()
    {
        try
        {
            List<LexicalEntry> results = ConcreteDictionaryParser.parseFile(new File("C:\\Users\\Phillip\\IdeaProjects\\Slot Grammar Parser\\Code\\src\\test.txt"));
        }
        catch(Exception e)
        {
            assert(false);
        }
    }
}
