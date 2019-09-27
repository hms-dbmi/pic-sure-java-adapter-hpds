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
    public final String key;
    public final String name;
    public final boolean categorical;
    public final List<String> categoryValues;
    public final Integer observationCount;
    public final String HpdsType;

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
