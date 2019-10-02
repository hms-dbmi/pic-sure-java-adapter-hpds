package edu.harvard.hms.dbmi.avillach.picsure.adapter.hpds;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Used to represent a data dictionary entry's information from a
 * HPDS-backed PIC-SURE Resource. The class is instantiated by its
 * parent object, an instance of {@link HpdsDictionaryResults}.
 *
 * @author  Nick Benik
 * @version %I%, %G%
 * @since   1.0
 */
public class HpdsDictionaryRecord {
    public final String key;                    // unique search key used by HPDS to identify data element
    public final String name;                   // human readable name for data element
    public final boolean categorical;           // is the data element a categorical entry?
    public final List<String> categoryValues;   // a listing of all valid categorical values for the entry
    public final Integer observationCount;      // number of records which use this data element
    public final String HpdsType;               // identifying which search class the data element belongs to (Example, "phenotype", "genotype", etc)

    protected HpdsDictionaryRecord(String hpdsTypeName, Map.Entry<String, HashMap> record) {
        this.HpdsType = hpdsTypeName;
        this.key = record.getKey();
        HashMap<String, Object> recordValues = record.getValue();
        this.name = (String) recordValues.get("name");
        this.categorical = (Boolean) recordValues.get("categorical");
        this.observationCount = (Integer) recordValues.get("observationCount");
        this.categoryValues = (List<String>) recordValues.get("categoryValues");
    }
}
