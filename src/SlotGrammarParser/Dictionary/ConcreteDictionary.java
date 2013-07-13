package SlotGrammarParser.Dictionary;

import SlotGrammarParser.*;

import SlotGrammarParser.abstractions.Dictionary;
import SlotGrammarParser.abstractions.DictionaryParser;
import SlotGrammarParser.abstractions.Feature;
import SlotGrammarParser.abstractions.LexicalEntry;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class ConcreteDictionary implements Dictionary {

    // A map from a string (given in the sentance) to the according lexical entry
    private final HashMap<String, LexicalEntry> entryLookup;

    private static final HashMap<Feature,List<SlotOption>> featureMapping = createFeatureMapping();
    private static final HashMap<String, String> lemmaForm = createLemmaMapping();
    private final List<LexicalEntry> lexicalEntries;

    public ConcreteDictionary(File f) throws Exception {

        // Parse lexical entries and create the entryLookup map
        lexicalEntries = parseLexicalEntries(f);

        // Create the entry lookup hashmap
        entryLookup = createLexicalEntryLookupMap(lexicalEntries);


    }

    public static HashMap<String, String> createLemmaMapping() {
        HashMap<String, String> retVal = new HashMap<String,String>();

        retVal.put("gave","give");
        retVal.put("checked","check");

        return retVal;
    }

    public HashMap<String, LexicalEntry> lookup(List<String> inputs) {
        HashMap<String, LexicalEntry> retVal = new HashMap<String, LexicalEntry>();
        for(int i=0;i<inputs.size();i++) {
            int k = 0;
            while(!(i+k >= inputs.size()))
            {
                String str = inputs.get(i);
                for(int j=i+1;j+k<inputs.size();j++)
                {
                    str += " " + inputs.get(j);
                }
                try
                {
                    retVal.put(str,lookup(str));
                }
                catch(Exception e)
                {}
                k++;
            }
        }
        return retVal;
    }

    // Creates a mapping of features to a list of options which accept it.
    private static HashMap<Feature,List<SlotOption>> createFeatureMapping()
    {
        HashMap<Feature, List<SlotOption>> result = new HashMap<Feature,List<SlotOption>>();

        // List of features, mapped to slot options they accept
        List<SlotOption> noun = new ArrayList<SlotOption>();
        List<SlotOption> verb = new ArrayList<SlotOption>();
        List<SlotOption> prep = new ArrayList<SlotOption>();
        List<SlotOption> det = new ArrayList<SlotOption>();
        List<SlotOption> np = new ArrayList<SlotOption>();
        List<SlotOption> s = new ArrayList<SlotOption>();
        List<SlotOption> vp = new ArrayList<SlotOption>();
        List<SlotOption> verb_gp = new ArrayList<SlotOption>();
        List<SlotOption> adj = new ArrayList<SlotOption>();
        List<SlotOption> adv = new ArrayList<SlotOption>();

/*        noun.add(SlotOption.n);
        verb.add(SlotOption.v);
        prep.add(SlotOption.p);
        det.add(SlotOption.det);
        np.add(SlotOption.n);
        np.add(SlotOption.det);*/

       /* result.put(Feature.noun, noun);
        result.put(Feature.verb, verb);
        result.put(Feature.prep, prep);
        result.put(Feature.det, det);
        result.put(Feature.np, np);
        result.put(Feature.s, s);
        result.put(Feature.vp, vp);
        result.put(Feature.verb_gp, verb_gp);
        result.put(Feature.adj, adj);
        result.put(Feature.adv, adv);*/

        return result;
    }

    public String getLemmaForm(String string) {
        if(lemmaForm.containsKey(string))
            return lemmaForm.get(string);
        return string;
    }

    public static List<SlotOption> getFeatureMapping(Feature feature) {
        return featureMapping.get(feature);
    }

    // Parses lexical entries from a file
    private List<LexicalEntry> parseLexicalEntries(File f) throws Exception {

        List<LexicalEntry> entries = new ArrayList<LexicalEntry>();
        List<Feature> features = new ArrayList<Feature>();

        try
        {
            entries.addAll(ConcreteDictionaryParser.parseFile(f));
        }
        catch(Exception e)
        {
            throw new Exception("Parser exception.");
        }
        /*LexicalEntry Mary = new LexicalEntry(new IndexWord("Mary"));
        features.add(Feature.noun);
        Mary.addSenseFrame(new ConcreteSenseFrame(PartOfSpeech.PNOUN, features));
        entries.add(Mary);

        features = new ArrayList<Feature>();
        features.add(Feature.verb);
        LexicalEntry gave = new LexicalEntry(new IndexWord("give", "given"));
        gave.addSenseFrame(new ConcreteSenseFrame(PartOfSpeech.VERB, features, new Slot(SlotType.SUBJ, SlotOption.n), new Slot(SlotType.OBJ, SlotOption.n), new Slot(SlotType.IOBJ, SlotOption.n, SlotOption.p)));
        entries.add(gave);

        features = new ArrayList<Feature>();
        features.add(Feature.noun);
        LexicalEntry John = new LexicalEntry(new IndexWord("John"));
        John.addSenseFrame(new ConcreteSenseFrame(PartOfSpeech.PNOUN, features));
        entries.add(John);

        features = new ArrayList<Feature>();
        features.add(Feature.det);
        LexicalEntry a = new LexicalEntry(new IndexWord("a"));
        a.addSenseFrame(new ConcreteSenseFrame(PartOfSpeech.DET,features, new Slot(SlotType.OBJ, SlotOption.n)));
        entries.add(a);

        features = new ArrayList<Feature>();
        features.add(Feature.det);
        LexicalEntry The = new LexicalEntry(new IndexWord("the"));
        The.addSenseFrame(new ConcreteSenseFrame(PartOfSpeech.DET, features));
        entries.add(The);

        features = new ArrayList<Feature>();
        features.add(Feature.noun);
        LexicalEntry book = new LexicalEntry(new IndexWord("book"));
        book.addSenseFrame(new ConcreteSenseFrame(PartOfSpeech.NOUN,features));
        entries.add(book);

        features = new ArrayList<Feature>();
        features.add(Feature.noun);
        LexicalEntry ball = new LexicalEntry(new IndexWord("ball"));
        ball.addSenseFrame(new ConcreteSenseFrame(PartOfSpeech.NOUN,features));
        entries.add(ball);

        features = new ArrayList<Feature>();
        features.add(Feature.prep);
        LexicalEntry to = new LexicalEntry(new IndexWord("to"));
        to.addSenseFrame(new ConcreteSenseFrame(PartOfSpeech.PREP,features, new Slot(SlotType.PPREFV, SlotOption.n) ));
        entries.add(to);

        features = new ArrayList<Feature>();
        features.add(Feature.verb);
        LexicalEntry be = new LexicalEntry(new IndexWord("be","was"));
        be.addSenseFrame(new ConcreteSenseFrame(PartOfSpeech.VERB, features, new Slot(SlotType.SUBJ, SlotOption.n), new Slot(SlotType.VFIN, SlotOption.v)));
        entries.add(be);

        features = new ArrayList<Feature>();
        features.add(Feature.prep);
        LexicalEntry by = new LexicalEntry(new IndexWord("by"));
        by.addSenseFrame(new ConcreteSenseFrame(PartOfSpeech.PREP,features, new Slot(SlotType.PPREFV, SlotOption.n) ));
        entries.add(by);

        features = new ArrayList<Feature>();
        features.add(Feature.noun);
        LexicalEntry db = new LexicalEntry(new IndexWord("data base"));
        db.addSenseFrame(new ConcreteSenseFrame(PartOfSpeech.NOUN,features));
        entries.add(db);

        features = new ArrayList<Feature>();
        features.add(Feature.noun);
        LexicalEntry d = new LexicalEntry(new IndexWord("data"));
        d.addSenseFrame(new ConcreteSenseFrame(PartOfSpeech.NOUN,features));
        entries.add(d);

        features = new ArrayList<Feature>();
        features.add(Feature.noun);
        LexicalEntry b = new LexicalEntry(new IndexWord("base"));
        b.addSenseFrame(new ConcreteSenseFrame(PartOfSpeech.NOUN,features));
        entries.add(b);

        features = new ArrayList<Feature>();
        features.add(Feature.noun);
        LexicalEntry ag = new LexicalEntry(new IndexWord("Attorney General"));
        ag.addSenseFrame(new ConcreteSenseFrame(PartOfSpeech.PNOUN,features));
        entries.add(ag);

        features = new ArrayList<Feature>();
        features.add(Feature.noun);
        LexicalEntry at = new LexicalEntry(new IndexWord("attorney"));
        at.addSenseFrame(new ConcreteSenseFrame(PartOfSpeech.NOUN,features));
        entries.add(at);

        features = new ArrayList<Feature>();
        features.add(Feature.noun);
        LexicalEntry g = new LexicalEntry(new IndexWord("general"));
        g.addSenseFrame(new ConcreteSenseFrame(PartOfSpeech.NOUN,features));
        entries.add(g);

        features = new ArrayList<Feature>();
        features.add(Feature.noun);
        LexicalEntry c = new LexicalEntry(new IndexWord("check"));
        c.addSenseFrame(new ConcreteSenseFrame(PartOfSpeech.NOUN,features));
        features = new ArrayList<Feature>();
        features.add(Feature.verb);
        c.addSenseFrame(new ConcreteSenseFrame(PartOfSpeech.VERB,features));
        entries.add(c);      */

        return entries;
    }

    // word (in any form acceptable by the index word of the
    // LexicalEntry, past, present, future) to its lexical entry.
    private HashMap<String, LexicalEntry> createLexicalEntryLookupMap(List<LexicalEntry> lexicalEntries) {

        HashMap<String, LexicalEntry> entryMap = new HashMap<String, LexicalEntry>();

        for(LexicalEntry le: lexicalEntries) {

            List<String> acceptedPhrases = le.getAcceptedPhrases();

            for(String phrase : acceptedPhrases) {
                entryMap.put(phrase, le);
            }
        }

        return entryMap;
    }



    public LexicalEntry lookup(String str) throws WordNotFoundException, WordHasNoSenseFramesException {
        try
        {
            return lookupHelper(getLemmaForm(str.trim()));
        }
        catch(Exception e)
        {
            try
            {
                return lookupHelper(getLemmaForm(str.toLowerCase().trim()));
            }
            catch(WordNotFoundException w)
            {
                throw new WordNotFoundException(str);
            }
            catch(WordHasNoSenseFramesException w)
            {
                throw new WordHasNoSenseFramesException(str);
            }
        }
    }

    @Override
    public List<LexicalEntry> getLexicalEntryList() {
        return lexicalEntries;
    }
    // Find all relevant information from this word.
  
    public LexicalEntry lookupHelper(String inputWord) throws WordNotFoundException, WordHasNoSenseFramesException {

        LexicalEntry entry = entryLookup.get(inputWord);

        if( entry == null) {
            throw  new WordNotFoundException(inputWord);
        }

        if(entry.getSenseFrames().size()<1) {
            throw new WordHasNoSenseFramesException(inputWord);
        }

        return entry;

        // Since we are using the hashmap, we should no longer need this
        /* for(LexicalEntry entry : lexicalEntries)
     {
         if(entry.getIndexWord().toString().equals(inputWord))
             return entry;
     }   */


        // return entryLookup.get(inputWord);

    }

    

}

