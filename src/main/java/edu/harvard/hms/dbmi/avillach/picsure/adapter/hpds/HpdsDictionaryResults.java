package edu.harvard.hms.dbmi.avillach.picsure.adapter.hpds;

import java.util.*;


/**
 * HpdsDictionary is used to extract data dictionary information from a
 * HPDS-backed PIC-SURE Resource. The resource it acts upon is dependent on
 * the resource its parent HpdsResourceConnection is configured to act upon.
 *
 * @author  Nick Benik
 * @version %I%, %G%
 * @since   1.0
 */
public class HpdsDictionaryResults {
    private Map<String, Object> results;


    protected HpdsDictionaryResults(Object results) {
        // reformat the object structure into a list
        HashMap<String, Object> processedResults = new HashMap<>();
        this.results = processedResults;
    }


    /**
     * Class function for use in jShell to print help instructions on the screen for this object's use.
     * @since   1.0
     */
    public void help() {
        // for jShell
    }


    /**
     * Get a count of how many records are in this result set.
     * @return The number of records
     */
    public int count() {
        return this.results.size();
    }


    /**
     * Get a list of the result set's search keys that are used by {@link HpdsQuery}.
     * @return List of data dictionary keys
     */
    public List<String> keys() {
        List<String> ret = new ArrayList<>();
        ret.addAll(this.results.keySet());
        return ret;
    }


    /**
     * Get a list of details for each record in the result set.
     * @return List of data dictionary records
     */
    public List<Object> entries() {
        List<Object> ret = new ArrayList<>();
        ret.addAll(this.results.entrySet());
        return ret;
    }
}
