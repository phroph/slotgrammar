package SlotGrammarParser;
import SlotGrammarParser.Dictionary.WordHasNoSenseFramesException;
import SlotGrammarParser.Dictionary.WordNotFoundException;
import SlotGrammarParser.abstractions.Dictionary;
//import SlotGrammarTree.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Phillip
 * Date: 8/12/12
 * Time: 2:13 AM
 * To change this template use File | Settings | File Templates.
 */


public class Parser {

    private Dictionary dictionary;
    public Parser(Dictionary dictionary)
    {
        this.dictionary = dictionary;
    }


   /* public List<SlotGrammarTreeNode> getUnseenWords(String text) throws Exception {


        ArrayList<SlotGrammarTreeNode> unseenWords = new ArrayList<SlotGrammarTreeNode>();
        String[] temp = text.split(" ");
        int it = 1;
        for(String str : temp)
        {
            // Look up the lexical entry for the given word
            LexicalEntry le = null;
            try {
                le = dictionary.lookup(str);

            } catch (WordNotFoundException e) {

                throw e;
            }

            unseenWords.add(new SlotGrammarTreeNode(le.senseFrames.get(0), it));

            it++;
        }

        return unseenWords;
    }      */


   /* public ParsedSentence parseTree(String text) throws Exception {


        System.out.println("Input text: " + text);

        // A Formal System for Slot Grammar pg 7
        // "The first step of parsing is morpholexical analysis. This is done in an initial pass
        // through the word tokens of the sentence. Each such token is given all its morpholexical 
        // analyses, expressed as one-word starter phrases, using the phrase data structure described
        // in the preceeding section. The "one-word" heads of those phrases may be multiwords
        List<SlotGrammarPhrase> starterPhrases = createStarterPhrases(text);


        for(int i = 0;i<starterPhrases.size();i++) {
            System.out.println("Starter Phrase: " + starterPhrases.get(i));
        }


        // Then, for the synatic analysis proper, the parser makes a second pass, going through the starter phrases
        // left to right, and combining them in teh matter of a bottom-up chart parser. At each step, the parser builds
        // up all possible phrasal analyses of substring of the words that it has looked at so far. The chart the
        // collection of these build up phrases. As a new starter phrase Q is encountered, the parser attempts to
        // combine Q with an existing phrase P in the chart such that the right boundary of P is the left boundary
        // of Q. This combination is done by either letting P fill a slot in Q or visa versa.
        SlotGrammarChart chart = new SlotGrammarChart();

        for(int i = 0;i<starterPhrases.size();i++) {
            chart.addStarterPhrase(starterPhrases.get(i));
        }

        return new ParsedSentence(chart.getPhrases().get(0));


    }     */

    // Creates starter phrases with the input text, we can use the dictionary to convert from string words
    // to lexical entries.
  /* private List<SlotGrammarPhrase> createStarterPhrases(String text) throws WordHasNoSenseFramesException, WordNotFoundException {

        List<SlotGrammarPhrase> entries = new ArrayList<SlotGrammarPhrase>();
        String[] words = text.split(" ");
        for(int i = 0;i<words.length;i++)  {
            // entries.add(new SlotGrammarPhrase(dictionary.lookup(words[i]), words[i], i+1));
        }

        return entries;
    }     */


    /*
   public SlotGrammarTree parseTree(String text) throws Exception
   {
       List<SlotGrammarTreeNode> unseenWords = getUnseenWords(text);
       int i = 1;

       ArrayList<SlotGrammarTreeNode> seenUnslottedWords = new ArrayList<SlotGrammarTreeNode>();
       ArrayList<SlotGrammarTreeNode> unseenSlottedWords = new ArrayList<SlotGrammarTreeNode>();
       do {

           SlotGrammarTreeNode test = unseenWords.get(0);
           unseenWords.remove(0);

           // Determinate found
           if(test.getNode().pos.equals(PartOfSpeech.DET))
           {
               unseenWords.get(0).getNode().addDet(test);
           }


           else if(test.getNode().slots.length > 0)
           {
               int iterator = 0;
               for(SlotGrammarTreeNode sgtn : seenUnslottedWords)
               {
                   Boolean breakme = false;
                   iterator++;
                   for(Slot slot : test.getNode().slots)
                   {
                       try{
                           slot.accept(sgtn);
                           seenUnslottedWords.remove(sgtn);
                           breakme = true;
                           break;
                       }
                       catch(Exception e)
                       {}
                   }
                   if(breakme)
                       break;
               }
               iterator = i;
               for(SlotGrammarTreeNode sgtn : unseenWords)
               {
                   iterator++;
                   for(Slot slot : test.getNode().slots)
                   {
                       try
                       {
                           if(!sgtn.getNode().pos.equals(PartOfSpeech.DET))
                           {
                               slot.accept(sgtn);
                               unseenSlottedWords.add(sgtn);
                               break;
                           }
                       }
                       catch(Exception e)
                       {}
                   }
               }


           }
           if(!test.getNode().pos.equals(PartOfSpeech.DET))
           {
               Boolean add = true;
               for(SlotGrammarTreeNode sgtn : unseenSlottedWords)
               {
                   if(sgtn.equals(test))
                       add = false;
               }
               if(add)
                   seenUnslottedWords.add(test);
           }
           i++;
       }
       while(unseenWords.size() > 0);

       SlotGrammarTree result = new SlotGrammarTree(seenUnslottedWords.get(0));

       return result;
   } */

}
