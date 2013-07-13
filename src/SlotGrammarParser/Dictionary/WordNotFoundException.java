package SlotGrammarParser.Dictionary;

/**
 * Created with IntelliJ IDEA.
 * User: adeluca
 * Date: 8/13/12
 * Time: 11:47 AM
 * To change this template use File | Settings | File Templates.
 */
public class WordNotFoundException extends DictionaryException {

    public WordNotFoundException(String inputWord) {
        super("Word not found: " + inputWord);
    }


}
