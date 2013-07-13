package SlotGrammarParser.Dictionary;

/**
 * Created with IntelliJ IDEA.
 * User: adeluca
 * Date: 8/13/12
 * Time: 12:24 PM
 * To change this template use File | Settings | File Templates.
 */
public class WordHasNoSenseFramesException extends DictionaryException{

    public WordHasNoSenseFramesException(String word) {
        super("Word: " + word + " has no sense frames");
    }
}
