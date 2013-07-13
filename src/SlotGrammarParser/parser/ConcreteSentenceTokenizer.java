package SlotGrammarParser.parser;

import SlotGrammarParser.Dictionary.WordHasNoSenseFramesException;
import SlotGrammarParser.Dictionary.WordNotFoundException;
import SlotGrammarParser.abstractions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Anthony
 * Date: 8/17/12
 * Time: 7:22 PM
 * To change this template use File | Settings | File Templates.
 */
public class ConcreteSentenceTokenizer implements SentenceTokenizer {

   // String input;
    Dictionary dictionary;
   // List<SentenceToken> tokens;
   //private  final String strippedText;

    // Mapping of sentences interlaces to string position
    Map<Integer, Integer> interlaceMap;

    final public static Character[] punctuation = {'.', '!', '\"',};

    public ConcreteSentenceTokenizer(Dictionary dictionary) {

      //  System.out.println(input);
             //The Attorney General checked the "data base", John
        this.dictionary = dictionary;

      //  strippedText = removePunctuation(input);
       // System.out.println(strippedText);
     //   interlaceMap  = createInterlaceMap(strippedText);
     //   tokens = createTokens(input);

    }

   public List<SentenceToken> createTokens(String input) {
        // Create a list of punctuation at the interlace positions
      //  Map<Integer, String> punctuationMap = createPunctuationMap(input);

        List<SentenceToken> tokens = new ArrayList<SentenceToken>();
        String[] splitStrippedInput = this.removePunctuation(input).split(" "); //strippedText.split(" ");

        // For each possible token
        for(int l = 0;l< splitStrippedInput.length;l++) {

            for(int r = splitStrippedInput.length-1;r>=l;r--) {

                String newWord = createWordFromStringArr(splitStrippedInput, l, r);


                LexicalEntry entry = null;
                try {
                    entry = dictionary.lookup(newWord);
                    tokens.add(new ConcreteSentenceToken(newWord, l, entry, false, l, r+1)) ;
                } catch (WordNotFoundException e) {
                   // e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                } catch (WordHasNoSenseFramesException e) {
                   // e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }




            }
        }

       return tokens;
   }

    private String createWordFromStringArr(String[] splitStrippedInput, int l, int r) {
        StringBuilder builder = new StringBuilder();
        for(int cur = l;cur<=r;cur++) {
            if(cur==r) {
                builder.append(splitStrippedInput[cur]);
            } else {
                builder.append(splitStrippedInput[cur] + " ");
            }
        }

        return builder.toString();
    }

    //   System.out.println("\nTest Word: " + newWord);
            /*    try {
                    LexicalEntry entry = dictionary.lookup(newWord);
                    tokens.add(new SentenceToken( newWord, entry, false, l, r+1)) ;
                    System.out.println("Added: " + newWord);
                   // System.out.printf("%s %s [%d %d]\n",newWord,entry,l,r);
                } catch (WordNotFoundException e) {
                    System.out.println("Failed: " + newWord);
                 //   e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                } catch (WordHasNoSenseFramesException e) {
                    System.out.println("Failed: " + newWord);
                //    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
                //  System.out.println(newWord);
                try {
                    LexicalEntry entry = dictionary.lookup(dictionary.getLemmaForm(newWord.trim()));
                    System.out.printf("%s %s [%d %d]",newWord.trim(),entry,l,r);

                    tokens.add(new SentenceToken( newWord.trim(), entry, false, l, r+1)) ;
                  //  System.out.println("added");
                } catch (WordNotFoundException e) {

                    try {
                        LexicalEntry  entry = dictionary.lookup(dictionary.getLemmaForm(newWord.trim().toLowerCase()));
                        System.out.printf("%s %s [%d %d]",newWord.trim(),entry,l,r);

                        tokens.add(new SentenceToken( newWord.trim(), entry, false, l, r+1)) ;
                      // System.out.println("added");

                    } catch (WordNotFoundException e1) {
                   //     e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                    } catch (WordHasNoSenseFramesException e1) {
                   //     e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                    }  */



              /* } catch (WordHasNoSenseFramesException e) {


                    LexicalEntry entry = null;
                    try {
                        entry = dictionary.lookup(newWord.trim().toLowerCase());
                    } catch (WordNotFoundException e1) {
                       // e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                    } catch (WordHasNoSenseFramesException e1) {
                     //   e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                    }
                    System.out.printf("%s %s [%d %d]",newWord.trim(),entry,l,r);

                    tokens.add(new SentenceToken( newWord, entry, false, l, r)) ;
                    System.out.println("added");
                }
                //if()     *



            }    */

      //  }

      //  System.out.println(tokens.size());
      //  return tokens;
        // for each word in the split text



       // System.out.println(punctuationMap);
       // System.out.println(strippedText);
      //  System.out.println(interlaceMap);

        /*List<SentenceToken> sentenceTokens = new ArrayList<SentenceToken>();

        Set<Integer> punctuationPositions = punctuationMap.keySet();
        int prevInterlace = 0;
        int leftPosition, rightPosition;
        String sentencePart;
        for(Integer punctuationInterlace: punctuationPositions) {
            leftPosition = interlaceMap.get(prevInterlace);
            rightPosition = interlaceMap.get(punctuationInterlace);

            sentencePart = strippedText.substring(leftPosition,rightPosition);

            //  sentenceTokens.addAll(createSentenceTokens(sentencePart, prevInterlace, punctuationInterlace));
            sentenceTokens.addAll(createSentenceTokens(sentencePart, prevInterlace, punctuationInterlace));
            prevInterlace = punctuationInterlace;
        }





       // return sentenceTokens;

    }   */
  /*

    public List<SentenceToken> getTokens() {
        return tokens;

    }

    public List<SentenceToken> getTokens(String input) {

        return tokens;

    }

    private Collection<? extends SentenceToken> createSentenceTokens(String sentencePart, Integer leftInterlace, Integer rightInterlace) {
        List<SentenceToken> tokens = new ArrayList<SentenceToken>();
       List<String> splitSentence = Arrays.asList(sentencePart.split(" "));
        int findPosition;
        int entryLeft;
        int entryRight;
        int precedingSpaces;
        int indexOfSubstring;
     //  System.out.println("Split sentence: " + splitSentence + " Left Interlace: " + leftInterlace + " Right Interlace: " + rightInterlace);
       HashMap<String, LexicalEntry> sentencePartLexicalEntries = dictionary.lookup(splitSentence);

        // For each original string combination
       for(String resultEntryString : sentencePartLexicalEntries.keySet()) {

       //    System.out.println("\n Result Entry:" + resultEntryString);
           // Find position and create token with
//           precedingSpaces = sentencePart.substring(0, sentencePart.indexOf(resultEntryString)).split(" ").length;
           indexOfSubstring =   sentencePart.toUpperCase().indexOf(resultEntryString.toUpperCase());

           if(indexOfSubstring == 0) {
               findPosition = 0;
              // findPosition =  sentencePart.substring(0, sentencePart.toUpperCase().indexOf(resultEntryString.toUpperCase())).split(" ").length;

           } else {
               findPosition =  sentencePart.substring(0, sentencePart.toUpperCase().indexOf(resultEntryString.toUpperCase())).split(" ").length;

           }

           // The left boundary of the result entry
           entryLeft =  findPosition + leftInterlace;
           entryRight = entryLeft + sentencePartLexicalEntries.get(resultEntryString).getIndexWord().getLength();

           // The rigt boundary
         //  entryRight = //findPosition + rightInterlace;// entryLeft + resultEntryString.trim().split(" ").length;
           //System.out.printf("Entry: %s FindPos: %d EntryLeft: %d EntryRight: %d Index Of Substring: %d\n", resultEntryString, findPosition, entryLeft,entryRight,
            //       indexOfSubstring);

           tokens.add(new SentenceToken(resultEntryString, sentencePartLexicalEntries.get(resultEntryString), false, entryLeft, entryRight));

          // System.out.println(tok);
       }
       //System.out.println(sentencePartLexicalEntries.size());
       //System.out.println(sentencePartLexicalEntries);
       return tokens;

    }


    // Maps interlace position with string position
    private Map<Integer, Integer> createInterlaceMap(String input) {

        Map<Integer, Integer> interlaceMap = new HashMap<Integer, Integer>();
        interlaceMap.put(0,0);
        interlaceMap.put(input.split(" ").length, input.length());
        int interLacePos = 1;
        Character ch;
        for(int i = 0;i<input.toCharArray().length;i++) {
            ch = input.toCharArray()[i];
            if(ch.equals(' ')) {

                interlaceMap.put(interLacePos++, i);

            }
        }
        return interlaceMap;

    }
              */
    // Removes punctuation from given input
    private String removePunctuation(String input) {
        StringBuilder result = new StringBuilder();
        Character ch;
        for(int i = 0;i<input.toCharArray().length;i++) {
             ch = input.toCharArray()[i];
             if(!Arrays.asList(punctuation).contains(ch)) {
                 result.append(ch);
             }
        }
        return result.toString();
    }

    /*
    // Create a map of the grammar, where the intereger is the position of the space, and the string is the punctuation
    private Map<Integer, String> createPunctuationMap(String input) {

        int position = 0;
        Map<Integer, String> punctuationMap = new HashMap<Integer, String>();
        String words[] = input.split(" ");
        String leftPunctuation;
        String rightPunctuation;
        String word;
        for(int i = 0;i<words.length;i++) {

            word = words[i];

            leftPunctuation = extractLeftPunctuation(word);
            if(leftPunctuation.length()>0) {
                punctuationMap.put(i, leftPunctuation);
            }


            rightPunctuation = extractRightPunctuation(word);
            if(rightPunctuation.length()>0) {
                punctuationMap.put(i+1, rightPunctuation);
            }


        }

        return punctuationMap;
    }

    private String extractRightPunctuation(String word) {

        StringBuilder rightPunctuation = new StringBuilder();
        Character ch;
        for(int i =word.toCharArray().length-1;i>=0;i--) {
            ch = word.toCharArray()[i];
            if(punctuation.contains(ch)) {
                rightPunctuation.append(ch);
            } else {
                return rightPunctuation.toString();
            }
        }

        return null;
    }

    private String extractLeftPunctuation(String word) {
        StringBuilder leftPunctuation = new StringBuilder();
        Character ch;
        for(int i =0;i<word.toCharArray().length;i++) {
            ch = word.toCharArray()[i];
            if(punctuation.contains(ch)) {
                leftPunctuation.append(ch);
            } else {
                return leftPunctuation.toString();
            }
        }

        return null;
    }

    private static final List<Character> punctuation = Arrays.asList('.', ',', ':', '\"', '!', '?', '(', ')', '\'');
    private boolean isPunctuation(Character ch) {
        return punctuation.contains(ch);
    }


    public String getStrippedText() {
        return strippedText;
    }  */

    @Override
    public TokenizedSentence tokenize(String input) {

        List<SentenceToken> tokens = createTokens(input);
        assert(tokens!=null);
        assert(tokens.size()>0);

        return new ConcreteTokenizedSentence(createTokens(input));
    }
}
