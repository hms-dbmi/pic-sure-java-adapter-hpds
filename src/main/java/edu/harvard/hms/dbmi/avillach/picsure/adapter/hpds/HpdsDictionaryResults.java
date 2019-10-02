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
    private HashMap<String, HpdsDictionaryRecord> results;


    protected HpdsDictionaryResults(LinkedHashMap<String, HashMap> results) {
        // reformat the passed object structure from native result to HpdsDictionaryResults structure
        HashMap<String, HpdsDictionaryRecord> processedResults = new HashMap();
        for (Map.Entry<String, HashMap> classEntry : results.entrySet()) {
            HashMap<String, HashMap> classRecords = classEntry.getValue();
            for (Map.Entry<String, HashMap> singleRecord : classRecords.entrySet()) {
                HpdsDictionaryRecord transformedRecord = new HpdsDictionaryRecord(classEntry.getKey(), singleRecord);
                processedResults.put(singleRecord.getKey(), transformedRecord);
            }
        }
        this.results = processedResults;
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
        ret.addAll(this.results.values());
        return ret;
    }
}
