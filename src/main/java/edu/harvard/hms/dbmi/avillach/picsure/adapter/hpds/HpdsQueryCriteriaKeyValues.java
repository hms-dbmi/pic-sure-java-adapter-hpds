package edu.harvard.hms.dbmi.avillach.picsure.adapter.hpds;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import java.util.NoSuchElementException;
import java.lang.ArrayStoreException;
import java.lang.IllegalStateException;

/**
 * Used to store query search criteria within a {@link HpdsQuery} instance.
 * This class saves keys, values, ranges, and catagorical groups.
 *
 * @author  Nick Benik
 * @version %I%, %G%
 * @since   1.0
 */
public final class HpdsQueryCriteriaKeyValues extends HpdsQueryCriteriaKeys {

    public HpdsQueryCriteriaKeyValues() {
        super("");
    }
    public HpdsQueryCriteriaKeyValues(String helpDesc) {
        super(helpDesc);
    }

    // ===[ ADD ]========================================

    /**
     * Store the name of a single key to search for.
     * @param key   Identifies a query criterion
     * @since       1.0
     */
    public void add(String key) { super.add(key); }


    /**
     * Store the names of several keys to search for.
     * @param keys  Identifies one or more a query criteria
     * @since       1.0
     */
    public void add(List<String> keys) { super.add(keys); }


    /**
     * Store one or more categories that a key must have to match search criteria.
     * @param key           Identifies a query criterion
     * @param categories    One or more strings values that a category may take
     * @since               1.0
     */
    public void add(String key, List<String> categories) {
        // add categorical
        List<String> keys = new ArrayList<>();
        keys.add(key);
        this.doAddCategoricals(keys, categories);
    }


    /**
     * Store one or more categories that a list of keys must have to match search criteria.
     * @param keys          Identifies one or more a query criteria
     * @param categories    One or more strings values that a category may take
     * @since               1.0
     */
    public void add(List<String> keys, List<String> categories) {
        // add categorical
        this.doAddCategoricals(keys, categories);
    }


    /**
     * Store a min, max or range that a key's value must validate against.
     * Use null as a min or max to make the range open on one side.
     * @param key   Identifies a query criterion
     * @param min   Value to set/replace or null to leave as is
     * @param max   Value to set/replace or null to leave as is
     * @since       1.0
     */
    public void add(String key, Double min, Double max) {
        // add range
        List<String> keys = new ArrayList<>();
        keys.add(key);
        this.doAddRange(keys, min, max);
    }


    /**
     * Store a min, max or range that a key's value must validate against.
     * Use null as a min or max to make the range open on one side.
     * @param key   Identifies a query criterion
     * @param min   Value to set/replace or null to leave as is
     * @param max   Value to set/replace or null to leave as is
     * @since       1.0
     */
    public void add(String key, int min, int max) {
        this.add(key, Double.valueOf(min), Double.valueOf(max));
    }


    /**
     * Store a min, max or range that multiple keys' values must validate against.
     * Use null as a min or max to make the range open on one side.
     * @param keys  Identifies one or more a query criteria
     * @param min   Value to set/replace or null to leave as is
     * @param max   Value to set/replace or null to leave as is
     * @since       1.0
     */
    public void add(List<String> keys, Double min, Double max) {
        // add range
        this.doAddRange(keys, min, max);
    }


    /**
     * Store a min, max or range that multiple keys' values must validate against.
     * Use null as a min or max to make the range open on one side.
     * @param keys  Identifies one or more a query criteria
     * @param min   Value to set/replace or null to leave as is
     * @param max   Value to set/replace or null to leave as is
     * @since       1.0
     */
    public void add(List<String> keys, int min, int max) {
        // add range
        this.doAddRange(keys, Double.valueOf(min), Double.valueOf(max));
    }

    // ===[ ADD-Implementations ]========================================
    private void doAddCategoricals(List<String> keys, List<String> categories) {
        Integer l1 = keys.size();
        for (Integer i1 = 0; i1 < l1; i1++) {
            // see if the key exists
            String searchKey = keys.get(i1);
            if (this.entries.containsKey(searchKey)) {
                // the entry already exists, add new categories if it is categorical, error if not
                HpdsQueryCriteria temp = this.entries.get(searchKey);
                // see if we have categorical values
                if (temp.entryType == HpdsQueryCriteria.ENTRY_TYPE_CATEGORICAL) {
                    // only handle if it is categorical data type
                    temp.categories.addAll(categories);
                    this.entries.replace(searchKey, temp);
                } else {
                    throw new ArrayStoreException(HpdsQueryCriteria.ERROR_MSG_ENTRY_WRONG_TYPE + searchKey);
                }
            } else {
                // add a new categorical entry
                HpdsQueryCriteria temp = new HpdsQueryCriteria();
                temp.queryKey = searchKey;
                temp.entryType = HpdsQueryCriteria.ENTRY_TYPE_CATEGORICAL;
                temp.categories.addAll(categories);
                if (temp.categories.size() == 0) {
                    throw new IllegalStateException(HpdsQueryCriteria.ERROR_MSG_ENTRY_INVALID_STATE + searchKey);
                }
                this.entries.put(searchKey, temp);
            }
        }
    }
    private void doAddRange(List<String> keys, Double min, Double max) {
        Integer l1 = keys.size();
        for (Integer i1 = 0; i1 < l1; i1++) {
            // see if the key exists
            String searchKey = keys.get(i1);
            if (this.entries.containsKey(searchKey)) {
                // the entry already exists, add/modify range info if it is range type, error if not
                HpdsQueryCriteria temp = this.entries.get(searchKey);
                // see if we have categorical values
                if (temp.entryType == HpdsQueryCriteria.ENTRY_TYPE_RANGE) {
                    // only handle if it is range data type
                    if (min != null) temp.min = min;
                    if (max != null) temp.max = max;
                    // IF YOU PASS null/false FOR MIN OR MAX THEN ITS EXISTING VALUE
                    // WILL NOT BE CHANGED.  TO REMOVE A MIN OR MAX VALUE YOU
                    // MUST ISSUE USE A delete ACTION AND SET THE MIN AND/OR
                    // MAX PARAMETER TO TRUE!!!

                    // only keep the entry if one of the range values (min/max) is set
                    if (temp.min == null && temp.max == null) {
                        throw new IllegalStateException(HpdsQueryCriteria.ERROR_MSG_ENTRY_INVALID_STATE + searchKey);
                    } else {
                        this.entries.replace(searchKey, temp);
                    }
                } else {
                    throw new ArrayStoreException(HpdsQueryCriteria.ERROR_MSG_ENTRY_WRONG_TYPE + searchKey);
                }
            } else {
                // add a new categorical entry
                HpdsQueryCriteria temp = new HpdsQueryCriteria();
                temp.queryKey = searchKey;
                temp.entryType = HpdsQueryCriteria.ENTRY_TYPE_RANGE;
                if (min != null) temp.min = min;
                if (max != null) temp.max = max;
                // only keep the entry if one of the range values (min/max) is set
                if (temp.min != null || temp.max != null) {
                    this.entries.put(searchKey, temp);
                } else {
                    throw new IllegalStateException(HpdsQueryCriteria.ERROR_MSG_ENTRY_INVALID_STATE + searchKey);
                }
            }
        }
    }

    // ===[ DELETE ]========================================
    /**
     * Delete a search criterion by key. It does not matter what kind
     * of value the criterion is (existence, value, range, categorical).
     * @param key   Identifies a query criterion
     * @since       1.0
     */
    public void delete(String key) { super.delete(key);}


    /**
     * Delete several search criteria by their keys. It does not matter what
     * kind of value the criterion is (existence, value, range, categorical).
     * @param keys  Identifies one or more a query criteria
     * @since       1.0
     */
    public void delete(List<String> keys) { super.delete(keys); }


    /**
     * Delete one or more categorical value(s) from a catagorical criterion. It must be a categorical type.
     * @param key           Identifies a query criterion
     * @param categories    One or more category strings to delete
     * @since               1.0
     */
    public void delete(String key, List<String> categories) {
        // delete a key's categories
        List<String> keys = new ArrayList<>();
        keys.add(key);
        this.doDeleteCategoricals(keys, categories);
    }


    /**
     * Delete one or more categorical value(s) from several catagorical criteria. They must be a categorical type.
     * @param keys          Identifies one or more a query criteria
     * @param categories    One or more cagegory strings to delete
     * @since               1.0
     */
    public void delete(List<String> keys, List<String> categories) {
        this.doDeleteCategoricals(keys, categories);
    }


    /**
     * Delete the min or max value of a range for single criterion.
     * @param key   Identifies a query criterion
     * @param min   To delete? True = Yes
     * @param max   To delete? True = Yes
     * @since       1.0
     */
    public void delete(String key, boolean min, boolean max) {
        // delete the min or max value from a range, move to only keys
        // if both min and max are not set
        List<String> keys = new ArrayList<>();
        keys.add(key);
        this.doDeleteRange(keys, min, max);
    }


    /**
     * Delete the min or max value of a range for many criteria.
     * @param keys  Identifies multiple query criterion
     * @param min   To delete? True = Yes
     * @param max   To delete? True = Yes
     * @since       1.0
     */
    public void delete(List<String> keys, boolean min, boolean max) {
        this.doDeleteRange(keys, min, max);
    }


    // ===[ DELETE-Implementation ]========================================
    private void doDeleteKeys(List<String> keys) {
        Integer l = keys.size();
        for (Integer i = 0; i < l; i++) {
            String searchKey = keys.get(i);
            if (this.entries.containsKey(searchKey)) {
                this.entries.remove(searchKey);
            } else {
                throw new NoSuchElementException(HpdsQueryCriteria.ERROR_MSG_ENTRY_MISSING + searchKey);
            }
        }
    }
    private void doDeleteCategoricals(List<String> keys, List<String> categories) {
        Integer l1 = keys.size();
        for (Integer i1 = 0; i1 < l1; i1++) {
            // see if the key exists
            String searchKey = keys.get(i1);
            if (this.entries.containsKey(searchKey)) {
                HpdsQueryCriteria temp = this.entries.get(searchKey);
                // see if we have categorical values
                if (temp.entryType == HpdsQueryCriteria.ENTRY_TYPE_CATEGORICAL) {
                    // only handle if it is categorical data type
                    temp.categories.removeAll(categories);
                    this.entries.replace(searchKey, temp);
                    if (temp.categories.size() == 0) {
                        throw new IllegalStateException(HpdsQueryCriteria.ERROR_MSG_ENTRY_INVALID_STATE + searchKey);
                    }
                } else {
                    throw new ArrayStoreException(HpdsQueryCriteria.ERROR_MSG_ENTRY_WRONG_TYPE + searchKey);
                }
            } else {
                throw new NoSuchElementException(HpdsQueryCriteria.ERROR_MSG_ENTRY_MISSING + searchKey);
            }
        }
    }
    private void doDeleteRange(List<String> keys, boolean min, boolean max) {
        Integer l1 = keys.size();
        for (Integer i1 = 0; i1 < l1; i1++) {
            // see if the key exists
            String searchKey = keys.get(i1);
            if (this.entries.containsKey(searchKey)) {
                HpdsQueryCriteria temp = this.entries.get(searchKey);
                // see if we have categorical values
                if (temp.entryType == HpdsQueryCriteria.ENTRY_TYPE_RANGE) {
                    // only handle if it is range data type
                    if (min) temp.min = null;
                    if (max) temp.max = null;

                    // throw error if either range values (min/max) is set
                    if (temp.min == null && temp.max == null) {
                        throw new IllegalStateException(HpdsQueryCriteria.ERROR_MSG_ENTRY_INVALID_STATE + searchKey);
                    } else {
                        this.entries.replace(searchKey, temp);
                    }
                } else {
                    throw new ArrayStoreException(HpdsQueryCriteria.ERROR_MSG_ENTRY_WRONG_TYPE + searchKey);
                }
            } else {
                throw new NoSuchElementException(HpdsQueryCriteria.ERROR_MSG_ENTRY_MISSING + searchKey);
            }
        }
    }


    // ===[ OTHER FUNCTIONS ]========================================
    /**
     * Class function for use in jShell to print the query object's current criteria on screen.
     * @since   1.0
     */
    public void show() {
        // for jShell
    }


    /**
     * Class function for use in jShell to print help instructions on the screen for this object's use.
     * @since   1.0
     */
    public void help() {
        // for jShell
    }


    /**
     * Function used to clear all criteria from the instantiated object.
     * @since   1.0
     */
    public void clear() {
        this.entries.clear();
    }


    /**
     * Get direct access to the entries in the instantiated object.
     * @return  a cloned copy of the internal HashMap used by the object.
     * @since   1.0
     */
    public HashMap<String, HpdsQueryCriteria> getRawEntries() {
        // this call also allows changes to the internal state since it does a shallow copy via .clone()
        // this function is used by unit tests
        return (HashMap<String, HpdsQueryCriteria>) this.entries.clone();
    }

}
