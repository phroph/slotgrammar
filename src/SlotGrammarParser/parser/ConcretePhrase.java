package SlotGrammarParser.parser;

import SlotGrammarParser.*;

import SlotGrammarParser.abstractions.Feature;
import SlotGrammarParser.abstractions.Phrase;
import SlotGrammarParser.abstractions.Slot;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Anthony
 * Date: 8/15/12
 * Time: 12:33 AM
 * To change this template use File | Settings | File Templates.
 */
public class ConcretePhrase implements Phrase {

    // Surface Structure
    // The headword in its text form (which may include capitalization). Such
    // "words" can be multiwords, as determined by the lexicon or other parts
    // of the system.
    final String originalString;


    // The word number for the headword in the segment.
    final int wordNumber;

    Feature partOfSpeech;

    // The citation form (or lemma) of the headword.
    // This is likely how the word appears in the dictionary.
    ///final String headwordLemma;

    //Feature pos;

    // The list of features of the node. These are identifiers that include
    // part of speech, morphosyntactic features, and semantic features. Most of
    // the features come from the headword and its morpholexical analysis –
    // from the features attached to its chosen sense in the lexicon, and from
    // its inflectional features. These are just viewed as features shared by
    // the head and the phrase as a whole, in the spirit of dependency grammar.
    // But some features provide information about the phrase as a whole.
   private final List<Feature> features;

    // The predicate argument list for the word sense. This is a list of pairs
    // arising from the lexical slot frame of the word sense, each pair
    // consisting of a complement slot from the frame and its filler, or nil if
    // there is no filler in the parse tree. The slots are listed in the same
    // order as in the lexical slot frame. Slot-filler arguments should be
    // viewed as deep or logical arguments. Logical subjects and objects are
    // shown for passive past participle verbs, and remote fillers of slots are
    // shown in the case of extraposition and coordination.

    private final List<Slot> complementSlots;
    private final List<Slot> adjunctSlots;


    // The lists of left modifiers and right modifiers of the head, given in
    // left-to-right surface order. Each modifier is recursively a phrase itself.
    List<Phrase> leftModifier;
    List<Phrase> rightModifiers;

    // The mother node of the current node – which is nil in case the current
    // node is the top node, and otherwise is a phrase.
    Phrase mother;

    // The surface slot (name) filled by the current node, or top if the current
    // node is the top node.
    String surfaceSlot;

    // The left and right boundary numbers of the current phrase.
    private Integer leftBoundary;
    private Integer rightBoundary;
    public final int fixedLeftBoundary;
    public final int fixedRightBoundary;

    // A parse score (a real number) for the current node, representing roughly
    // how likely it is that this phrase is a good analysis of the text string
    // it spans.
    int parseScore;

    //A list of tests to be done on the node – its phrase tests – when it is
    // used as a filler, or is taken as the top node. In most cases, this list
    // will be empty. We describe this in more detail below.
    List<PhraseTest> tests;


    // Deep (logical) structure
    // The sense name of the headword. This is by default the citation form
    // followed by a number, but the SG lexicon can specify some arbitrary
    // sense name.
    // It seems this is used to identify the sense frame.
    final private String senseName;

    // The slot option for the slot-filling just named, or nop if the current
    // node is the top node. See [10, 9]for the description of slot options.
    // They are closely associated with the POS of the filler phrase.
    SlotOption slotOption;


    public ConcretePhrase(String originalString, String senseNAme, List<Feature> features, int wordNumber, int leftBoundary, int rightBoundary, List<Slot> complementSlots, List<Slot> adjunctSlots, Feature partOfSpeech) {
        this.originalString = originalString;
        this.wordNumber = wordNumber;
        this.leftBoundary = leftBoundary;
        this.rightBoundary = rightBoundary;
        this.complementSlots = complementSlots;
        this.adjunctSlots = adjunctSlots;
        this.senseName = senseNAme;
        this.features = features;
        this.partOfSpeech = partOfSpeech;
        this.fixedLeftBoundary = leftBoundary;
        this.fixedRightBoundary = rightBoundary;
    }


    public String toString() {
        return toStringHelper(0, false, null);
    }
    public String toStringHelper(int depth, boolean top, Slot parent)
    {
        String retVal = "";
        for(Slot s : getSlots())
        {
            if(s.getFillCount() > 0)
            {
                Phrase p = s.getContents();
                if(fixedLeftBoundary > p.getLeftBoundary())
                {
                    retVal += ((ConcretePhrase)p).toStringHelper(depth+1, true, s);
                }
            }
        }
        String sub = depth == 0 ? "\u2502" : top ? "\u250c-" : "\u2514-";
        String slot = parent == null ? "top" : parent.isComplementSlot() ? String.format("%s(%s)",parent.getSlotType(), getPartOfSpeech()) : parent.getSlotType();
        String graph = String.format("%"+(depth+1)+"s",sub);
        String arguments = ""+fixedRightBoundary;
        for(Slot s : getSlots())
        {
            if(s.isComplementSlot() && s.getFillCount() > 0)
            {
                arguments += ","+s.getContents().getRightBoundary();
            }
        }
        String features = "";
        for(Feature f : getFeatures())
        {
            features += f.toString() + " ";
        }

        retVal += String.format("%-5s%-15s%-20s%5s\n",graph, slot, getOriginalText() + "("+arguments+")", features);
        for(Slot s : getSlots())
        {
            if(s.getFillCount() > 0)
            {
                Phrase p = s.getContents();
                if(fixedRightBoundary < p.getRightBoundary())
                {
                    retVal += ((ConcretePhrase)p).toStringHelper(depth+1, false, s);
                }
            }
        }
        return retVal;
    }

    /*public ConcretePhrase(Phrase toBeFilled, Phrase adjacentPhrase, Slot openSlotInPhraseTobeFilled, boolean filledSlotIsComplement) {

        senseName = toBeFilled.getSenseName();
        this.rightBoundary = (toBeFilled.getRightBoundary()>adjacentPhrase.getLeftBoundary())? toBeFilled.getRightBoundary() : adjacentPhrase.getRightBoundary();
        this.leftBoundary = (toBeFilled.getLeftBoundary()<adjacentPhrase.getLeftBoundary())? toBeFilled.getLeftBoundary() : adjacentPhrase.getLeftBoundary();

        features = combineFeaatures(toBeFilled, adjacentPhrase, openSlotInPhraseTobeFilled, filledSlotIsComplement);
        adjunctSlots = combineAdjunctSlots(toBeFilled, adjacentPhrase, openSlotInPhraseTobeFilled, filledSlotIsComplement) ;
        slots = combineComplementSlots(toBeFilled, adjacentPhrase, openSlotInPhraseTobeFilled, filledSlotIsComplement);
        wordNumber = toBeFilled.getWordNumber();
        pos = toBeFilled.getPartOfSpeech();
        originalString = toBeFilled.getOriginalText();

    }   */

    private List<Feature> combineFeaatures(Phrase toBeFilled, Phrase adjacentPhrase, Slot openSlotInPhraseTobeFilled, boolean filledSlotIsComplement) {
        return toBeFilled.getFeatures();
    }


    private static Map<Slot, List<Phrase>> combineAdjunctSlots(Phrase phraseToBeFilled, Phrase fillerPhras, Slot openSlotInPhraseTobeFilled, boolean filledSlotIsComplement){

        // TODO: Imeplement
        return null;

    }

    private static Map<Slot, Phrase> combineComplementSlots(Phrase phraseToBeFilled, Phrase fillerPhras, Slot openSlotInPhraseTobeFilled, boolean filledSlotIsComplement){
        // TODO: Implement
        return null;
    }

   /* public ConcretePhrase(SentenceToken token, ConcreteSenseFrame frame) {
        assert(frame!=null);

        parseScore = 0;

        features = frame.getFeatures();
        originalString = token.getOriginalText();
        wordNumber = token.getLeftBoundary()+1;
        assert wordNumber>0;

        leftBoundary = token.getLeftBoundary();
        rightBoundary = token.getRightBoundary();
        senseName = frame.getSenseName();
        assert senseName.length()>0;
       // System.out.println("SenseName: " + senseName);
        pos = frame.getPartOfSpeech();


        // Initialize slots
        slots = new HashMap<Slot, Phrase>();
        for(Slot s : frame.getComplementSlots())  {
            slots.put(s,null);
        }

        // Iniitialize adjunct slots
        adjunctSlots = new HashMap<Slot, List<Phrase>>();
        for(Slot adjunctSlot : frame.getAdjunctSlots()) {
            adjunctSlots.put(adjunctSlot, null);
        }


        // System.out.println(frame.getSlots().length);

        // this.leftBoundary = leftBoundary;
       // this.rightBoundary = rightBoundary;
       // this.headwordTextForm = originalText;
    }    */


    public int getWordNumber() {
        return wordNumber;
    }

    /*public Set<Slot> getSlotList() {
        return slots.keySet();
    }   */

  /*  public PartOfSpeech getPOS() {
        return pos;
    }*/
    
    public String getSenseName() {
        return senseName;
    }

    public int getLeftBoundary() {
        return leftBoundary;
    }

    public int getRightBoundary() {
        return rightBoundary;
    }
    public String getOriginalText() {
        return originalString;
    }



    public List<Feature> getFeatures() {
        List<Feature> retVal = new ArrayList<Feature>();
        if(getPartOfSpeech()!=null)
        {
            retVal.add(getPartOfSpeech());
        }
        retVal.addAll(features);
        return retVal;
    }

   /* public String toString() {
        StringBuilder result = new StringBuilder();
        result.append(String.format("\"%s\" %s [%d, %d]", originalString, senseName, leftBoundary,rightBoundary));

        if(slots.size()>0) {

            result.append(String.format("(%d, ",wordNumber));

            for(int i = 0;i<slots.size();i++)  {
                result.append(slots.get(i));
                if(i==slots.size()-1) {
                    result.append(")") ;
                } else {
                    result.append(", ");
                }

            }


            for(int i = 0;i<adjunctSlots.keySet().size();i++)  {
                result.append(adjunctSlots);
                if(i==adjunctSlots.keySet().size()-1) {
                    result.append(")") ;
                } else {
                    result.append(", ");
                }

            }



        } else {

            // No Slots
            result.append(String.format("(%d) [%d, %d]", wordNumber,leftBoundary,rightBoundary));
        }

        return result.toString();


    }       */

     /*
    public List<Slot> getComplementSlots() {
        List<Slot> complementSlots = new ArrayList<Slot>();
        for(Slot slot : slots.keySet()) {
            complementSlots.add(slot);
        }

        return complementSlots;
        //return Arrays.asList(this.slots.keySet().toArray());
    }

    public List<Slot> getAvailableComplementSlots() {
        List<Slot> availableSlots = new ArrayList<Slot>();
        List<Slot> complementSlotList = getComplementSlots();
        for(Slot s: complementSlotList) {
            if(slots.get(s)==null) {
                availableSlots.add(s);
            }
        }

        return availableSlots;
    }

    public List<Slot> getAdjuctSlots() {

        List<Slot> adjunctSlotList = new ArrayList<Slot>();
        for(Slot slot : adjunctSlots.keySet()) {
          adjunctSlotList.add(slot);
        }

        return adjunctSlotList;
    }
         */
    // Functions used to combine phrases
   /* public boolean canCombine(Phrase adjacentPhrase) {
     //   System.out.printf("Can %s combine with %s?\n", this, adjacentPhrase);

        assert(getLeftBoundary() == adjacentPhrase.getRightBoundary() || getRightBoundary() == adjacentPhrase.getLeftBoundary());

        // Check complement slots
        List<Slot> availableComplementSlots = getAvailableComplementSlots();

        for(Slot openSlot : availableComplementSlots) {
            if(openSlot.canAccept(adjacentPhrase.getFeatures())) {
                return true;
            }
        }


       // Check adjunct slots
       List<Slot> adjucntSlotList = getAdjuctSlots();
       for(Slot curAdjunctSlot : availableComplementSlots) {
          if(curAdjunctSlot.canAccept(adjacentPhrase.getFeatures())) {
               return true;
         }
       }



        return false;

     }


    /**
     * Fills one or more of the current slots with phrase
      * @param adjacentPhrase
     * @return List of phrase results from the combination
     */
    /*
    public List<Phrase> fill(Phrase adjacentPhrase) {


        List<Phrase> resultPhrases = new ArrayList<Phrase>();

        // Check complement slots
        List<Slot> availableComplementSlots = getAvailableComplementSlots();

        for(Slot openSlot : availableComplementSlots) {
            if(openSlot.canAccept(adjacentPhrase.getFeatures())) {

                // Create a new phrase filling the open slot
                resultPhrases.add(new Phrase(this, adjacentPhrase, openSlot, true));
            }
        }


        // Check adjunct slots
        List<Slot> adjucntSlotList = getAdjuctSlots();
        for(Slot curAdjunctSlot : availableComplementSlots) {
            if(curAdjunctSlot.canAccept(adjacentPhrase.getFeatures())) {
                resultPhrases.add(new Phrase(this, adjacentPhrase, curAdjunctSlot, false));
            }
        }

        return resultPhrases;
    }

    public static Boolean canCombine(Phrase a, Phrase b) {
        // Check combining b into an a slot
        if(a.canCombine(b)) {
            return true;
        } else if(b.canCombine(a)) {
            return true;
        } else {
            return false;
        }
    }
    */

   /* public List<Slot> getSlots() {
        List<Slot> phraseSlots = new ArrayList<Slot>();
        phraseSlots.addAll(slots.keySet());
        phraseSlots.addAll(adjunctSlots.keySet());
        return phraseSlots;

    }    */


   /* public List<Phrase> combine(Phrase a, Phrase b) {


      //  System.out.println("");
       List<Phrase> resultPhrases = new ArrayList<Phrase>();

        return resultPhrases;
        /*

        for(Slot aSlot : a.getSlots())  {
            try {
                resultPhrases.add(aSlot.fill(b));
            } catch (Exception e) {
               // e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }

        for(Slot bSlot : b.getSlots())  {
            try {
                resultPhrases.add(bSlot.fill(a));
            } catch (Exception e) {
               // e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }

       // System.out.println("Result phrase size: " + resultPhrases.size());
        return resultPhrases;  */


    //    System.out.println("Combining: " + a + " " + b);
        /*List<Phrase> resultPhrases = new ArrayList<Phrase>();
        if(a.canCombine(b)) {
            resultPhrases.addAll(a.fill(b));
        }

        if(b.canCombine(a)) {
            resultPhrases.addAll(b.fill(a));
        }

        return resultPhrases;


    }  */

     public List<Slot> getComplementSlots() {
          return complementSlots;
     }

     public List<Slot> getAvailableComplementSlots() {
          throw new UnsupportedOperationException("Not supported yet.");
     }

    public List<Slot> getSlots() {
        List<Slot> slotList = new ArrayList<Slot>();
        slotList.addAll(getComplementSlots());
        slotList.addAll(getAdjunctSlots());
        return slotList;
    }

     public List<Slot> getAdjunctSlots() {
          return adjunctSlots;
     }

     public List<Slot> getAvailableSlots() {
          throw new UnsupportedOperationException("Not supported yet.");
     }

     public List<Phrase> fillWith(Phrase fillerPhrase) {
          throw new UnsupportedOperationException("Not supported yet.");
     }

     public Feature getPartOfSpeech() {
          return partOfSpeech;
     }

     @Override
     public Phrase clone() {
         List<Slot> compSlots = new ArrayList<Slot>();
         List<Slot> adjSlots = new ArrayList<Slot>();
         for(Slot s : complementSlots)
         {
             compSlots.add(s.clone());
         }
         for(Slot s : adjunctSlots)

         {
             adjSlots.add(s.clone());
         }
         ConcretePhrase temp =new ConcretePhrase(originalString, senseName, features, wordNumber, fixedLeftBoundary, fixedRightBoundary,compSlots,adjSlots, partOfSpeech);
         for(Slot s : temp.getSlots())
         {
             s.setParent(temp);
         }
         temp.parseScore = this.parseScore;
         temp.leftBoundary = leftBoundary;
         temp.rightBoundary = rightBoundary;
         return temp;
     }

     @Override
     public void addScore(int score) {
          this.parseScore += score;
     }

    @Override
    public int getFilledSlots() {
        int count = 0;
        for(Slot s : complementSlots)
        {
            count+= s.getFillCount();
        }
        return count;
    }

    @Override
    public void setLeftBoundary(int leftBoundary) {
        this.leftBoundary = leftBoundary;
    }

    @Override
    public void setRightBoundary(int rightBoundary) {
        this.rightBoundary = rightBoundary;
    }

    @Override
    public int getParseScore() {
        return parseScore;
    }

    @Override
    public List<Phrase> getFilledPhrases() {
        List<Phrase> retVal = new ArrayList<Phrase>();
        for(Slot s : complementSlots)
        {
            if(s.getContents()!=null)
                retVal.add(s.getContents());
        }
        for(Slot s : adjunctSlots)
        {
            retVal.addAll(((AdjunctSlot)s).getContent());
        }
        return retVal;
    }
}
