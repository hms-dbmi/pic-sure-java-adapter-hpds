package edu.harvard.hms.dbmi.avillach.picsure.adapter.hpds;

import java.util.ArrayList;

/**
 * Used to represent a query criterion within {@link HpdsQueryCriteriaKeys} and {@link HpdsQueryCriteriaKeyValues}
 * accessed via {@link HpdsQuery#select()}, {@link HpdsQuery#require()}, and {@link HpdsQuery#filter()}.
 *
 * @author  Nick Benik
 * @version %I%, %G%
 * @since   1.0
 */
public class HpdsQueryCriteria {

    public static final byte ENTRY_TYPE_KEY = 0;
    public static final byte ENTRY_TYPE_RANGE = 1;
    public static final byte ENTRY_TYPE_CATEGORICAL = 2;

    public static final String ERROR_MSG_ENTRY_EXISTS = "Can not add new query restriction, key already exists: ";
    public static final String ERROR_MSG_ENTRY_MISSING = "Can not delete/change query restriction, key does not exists: ";
    public static final String ERROR_MSG_ENTRY_WRONG_TYPE = "Can not change query restriction, existing key references the wrong type: ";
    public static final String ERROR_MSG_ENTRY_INVALID_STATE = "Your modification action(s) have put an existing query criteria into an invalid state. Example: a range without min or max value. Key is : ";

    public String queryKey;                 // unique search key used by HPDS to identify data element
    public byte entryType;                  // entry type being one of the following: ENTRY_TYPE_KEY, ENTRY_TYPE_RANGE, ENTRY_TYPE_CATEGORICAL
    public ArrayList<String> categories;    // a list of category values which the query will require the results to satisfy at least one occurrence of.
    public Double min;                      // the minimum value required to satisfy (if not null)
    public Double max;                      // the maximum value required to satisfy (if not null)


    public HpdsQueryCriteria() {
        this.queryKey = null;
        this.entryType = ENTRY_TYPE_KEY;
        this.categories = new ArrayList<>();
        this.min = null;
        this.max = null;
    }
}
