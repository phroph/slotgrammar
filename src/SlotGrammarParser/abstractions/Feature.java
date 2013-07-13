package SlotGrammarParser.abstractions;/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.*;
import java.util.Dictionary;

/**
 *
 * @author Anthony
 */
public final class Feature {

    static List<Feature> domain = new ArrayList<Feature>(); //Domain
    static java.util.Dictionary<String, Feature> stringMappingFunction = new Hashtable<String, Feature>(); // f: {Keys | Domain}

    Dictionary<Integer, Feature>  subsetMappingFunction = new Hashtable<Integer, Feature>(); // g: (subsetDomain | subsets}
    int domainBound = 0; //subsetDomain is defined to be: all x in Z, 0 < x < domainBound

    //External information
    String stringLiteral;

    public Feature(String name)
    {
        stringLiteral = name;
    }

    //Internal Logical black box.

    private static Enumeration<String> getStringMappingDomain() {
        return stringMappingFunction.keys();
    }
    private static Enumeration<Feature> getStringMappingRange() {
        return Collections.enumeration(domain);
    }
    private static Feature applyToStringMapping(String input) {
        return stringMappingFunction.get(input);  // f(input)
    }

    private Feature applyToSubsetMapping(int i) {
        return subsetMappingFunction.get(i);
    }
    private Enumeration<Feature> getSubsetMappingRange() {
        List<Feature> features = new ArrayList<Feature>();
        for(int i=0;i<domainBound;i++) {
            features.add(apply(i));
        }
        return Collections.enumeration(features);
    }
    private void addMapping(Feature e) {
        try {
            subsetMappingFunction.put(domainBound++, e);
        }
        catch(Exception error) {
            domainBound--;
            subsetMappingFunction.remove(e.toString()); //Just in case, we want to guarantee that our enumeration is consistent.
        }
    }
    private Feature apply(int i) {
        return subsetMappingFunction.get(i);
    }

    private boolean isProperSubset(Feature feature) {
        Enumeration<Feature> enumeration = getSubsets();
        while(enumeration.hasMoreElements())
        {
            Feature temp;
            if((temp = enumeration.nextElement()).equals(feature))
                return true;
            else
            if(temp.isSubset(feature)) return true;
        }
        return false;
    }

    //External interface.
    static public void initialize() {
        domain.clear();
        Enumeration<String> t = stringMappingFunction.keys();
        while(t.hasMoreElements())
        {
            stringMappingFunction.remove(t);
        }
        Feature noun, propn, sg, h, verb, vfin, vpast, vsubj, det, indef, cn, prep, pprefv, motionp;
        noun = new Feature("noun");
        propn = new Feature("propn");
        noun.addMapping(propn);


        sg = new Feature("sg");
        h = new Feature("h");
        verb = new Feature("verb");
        vfin = new Feature("vfin");
        vpast = new Feature("vpast");
        vsubj = new Feature("vsubj");
        det = new Feature("det");
        indef = new Feature("indef");
        cn = new Feature("cn");
        noun.addMapping(cn);
        prep = new Feature("prep");
        pprefv = new Feature("pprefv");
        motionp = new Feature("motionp");
        domain.add(noun);
        domain.add(propn);
        domain.add(sg);
        domain.add(h);
        domain.add(verb);
        domain.add(vfin);
        domain.add(vpast);
        domain.add(vsubj);
        domain.add(det);
        domain.add(indef);
        domain.add(cn);
        domain.add(prep);
        domain.add(pprefv);
        domain.add(motionp);
        stringMappingFunction.put("noun", noun);
        stringMappingFunction.put("propn", propn);
        stringMappingFunction.put("sg", sg);
        stringMappingFunction.put("h", h);
        stringMappingFunction.put("verb", verb);
        stringMappingFunction.put("vfin", vfin);
        stringMappingFunction.put("vpast", vpast);
        stringMappingFunction.put("vsubj", vsubj);
        stringMappingFunction.put("det", det);
        stringMappingFunction.put("indef", indef);
        stringMappingFunction.put("cn", cn);
        stringMappingFunction.put("prep", prep);
        stringMappingFunction.put("pprefv", pprefv);
        stringMappingFunction.put("motionp", motionp);


    }

    public Enumeration<Feature> getSubsets() {
        return getSubsetMappingRange();
    }
    public boolean isSubset(Feature feature) {
        if(this.equals(feature))
            return true;
        else
            return isProperSubset(feature);
    }

    static public Enumeration<Feature> getFeatures() {
        return getStringMappingRange();
    }
    static public Feature getFeatureFromString(String str) {
        return applyToStringMapping(str.toLowerCase());
    }
    public String toString()
    {
        return stringLiteral;
    }
}
