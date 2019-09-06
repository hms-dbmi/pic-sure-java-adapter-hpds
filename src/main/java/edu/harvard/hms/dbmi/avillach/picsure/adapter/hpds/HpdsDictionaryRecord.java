package edu.harvard.hms.dbmi.avillach.picsure.adapter.hpds;

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
    public final long observationCount;

    protected HpdsDictionaryRecord(String key, Object record) {
        this.key = key;
        this.name = ((Map<String, String>) record).get("name");
        this.categorical = (((Map<String, Boolean>) record).get("categorical"));
        this.observationCount = (((Map<String, Long>) record).get("observationCount"));
        this.categoryValues = (((Map<String, List<String>>) record).get("categoryValues"));

    }
}
