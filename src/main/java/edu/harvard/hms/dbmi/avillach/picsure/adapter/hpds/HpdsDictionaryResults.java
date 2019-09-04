package edu.harvard.hms.dbmi.avillach.picsure.adapter.hpds;

import java.util.*;

public class HpdsDictionaryResults {
    private Map<String, Object> results;
    protected HpdsDictionaryResults(Object results) {
        // reformat the object structure into a list
        HashMap<String, Object> processedResults = new HashMap<>();
        this.results = processedResults;
    }

    public void help() {
        // for jShell
    }
    public int count() {
        return this.results.size();
    }
    public List<String> keys() {
        List<String> ret = new ArrayList<>();
        ret.addAll(this.results.keySet());
        return ret;
    }

    public List<Object> entries() {
        List<Object> ret = new ArrayList<>();
        ret.addAll(this.results.entrySet());
        return ret;
    }
}
