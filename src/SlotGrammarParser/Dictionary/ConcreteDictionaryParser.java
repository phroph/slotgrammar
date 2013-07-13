package SlotGrammarParser.Dictionary;

import SlotGrammarParser.*;

import SlotGrammarParser.SlotGrammarParser.LexicalEntry;
import SlotGrammarParser.abstractions.Feature;
import SlotGrammarParser.abstractions.Slot;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Phillip
 * Date: 8/15/12
 * Time: 11:45 PM
 * To change this template use File | Settings | File Templates.
 */
public class ConcreteDictionaryParser{

    private static Hashtable<SlotType, List<SlotOption>> defSlots = new Hashtable<SlotType, List<SlotOption>>();

    public static final String SenseFrameDelimeter = "<";
    public static final String Space = " ";
    public static List<LexicalEntry> parseFile(File file) throws FileNotFoundException
    {
        Feature.initialize();
        FileReader fr;
        List<String> lineList = null;
        try
        {
            fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            lineList = new ArrayList<String>();
            String line = null;
            while ((line = br.readLine()) != null) {
                lineList.add(line);
            }
            br.close();
        }
        catch(Exception e)
        {
            throw new FileNotFoundException();
        }

        String[] lines = lineList.toArray(new String[lineList.size()]);
        List<LexicalEntry> entries = new ArrayList<LexicalEntry>();
        LexicalEntry currentEntry = null;
        for(String line : lines)
        {
            if(isIndexWord(line))
            {
                if(currentEntry!=null)
                {
                    entries.add(currentEntry);
                }
                currentEntry = new LexicalEntry();

                currentEntry.setIndexWord(new IndexWord(line));
            }
            else
            {
                String[] pieces = line.trim().split(Space);
                Feature pos = Feature.getFeatureFromString(pieces[1]);
                List<Slot> slots = new ArrayList<Slot>();
                List<Feature> features = new ArrayList<Feature>();
                for(int i=2;i<pieces.length;i++)
                {
                    if(isSlot(pieces[i]))
                    {
                        String slotData = pieces[i];
                        if(pieces[i].charAt(0) == '(')
                        {
                            //slotData = pieces[i].replaceAll("[(]","").split(Space);
                            while(!pieces[i].endsWith(")"))
                            {
                                //  options.add(SlotOption.valueOf(pieces[i+1].replaceAll("[)]","")));
                                i++;
                            }
                        }
                        else
                        {
                            //slotData = pieces[i].split(Space);
                        }
                        SlotType type = SlotType.valueOf(slotData.replaceAll("[(]","").toUpperCase());
                        List<SlotOption> options = getDefaultSlotOptions(type);
                        if(options==null)
                            options = new ArrayList<SlotOption>();
                        //for(int j=1;j<slotData.length;j++)
                        //{
                        //    options.add(SlotOption.valueOf(slotData[i]));
                        //}
                        slots.add(new ComplementSlot(type, convertToArray(SlotOption.class,options)));

                    }
                    else
                    {
                       // features.add(Feature.valueOf(pieces[i].replaceAll("[)]","")));
                    }
                }
                if(pos.toString().toLowerCase().equals("noun"))
                {
                    Slot detSlot = new AdjunctSlot(SlotType.DET, null,new SlotOption(Feature.getFeatureFromString("det")));
                    slots.add(detSlot);
                }
                ConcreteSenseFrame frame = new ConcreteSenseFrame(pos, features, convertToArray(Slot.class,slots));
              //  ConcreteSenseFrame frame = new ConcreteSenseFrame(null, features, convertToArray(Slot.class,slots));
                
                currentEntry.addSenseFrame(frame);
            }
        }
        entries.add(currentEntry);
        return entries;
    }
    public static boolean isSlot(String query)
    {
        if(query.trim().charAt(0) == '(')
            return true;
        try
        {
            SlotType.valueOf(query.toUpperCase());
            return true;
        }
        catch(Exception e)
        {
            return false;
        }
    }
    public static boolean isIndexWord(String line)
    {
        return line.split(SenseFrameDelimeter).length == 1;
    }
    public static <T> T[] convertToArray(Class<T> cls,List<T> list)
    {
        T[] array = (T[]) Array.newInstance(cls, list.size());
        for(int i=0; i<list.size(); i++)
        {
            array[i] = list.get(i);
        }
        return array;
    }
    public static List<SlotOption> getDefaultSlotOptions(SlotType type)
    {
        if(defSlots.size() == 0)
        {
            List<SlotOption> temp = new ArrayList<SlotOption>();
            temp.add(new SlotGrammarParser.SlotOption(Feature.getFeatureFromString("noun")));
            defSlots.put(SlotType.OBJ,temp);
            temp = new ArrayList<SlotOption>();
            temp.add(new SlotGrammarParser.SlotOption(Feature.getFeatureFromString("noun")));
            defSlots.put(SlotType.COMP,temp);
            temp = new ArrayList<SlotOption>();
            temp.add(new SlotGrammarParser.SlotOption(Feature.getFeatureFromString("noun")));
            defSlots.put(SlotType.IOBJ,temp);
            temp = new ArrayList<SlotOption>();
            temp.add(new SlotGrammarParser.SlotOption(Feature.getFeatureFromString("noun")));
            defSlots.put(SlotType.SUBJ,temp);
        }
        return defSlots.get(type);
    }
}
