package edu.harvard.hms.dbmi.avillach.picsure.adapter.hpds;

import java.lang.reflect.Array;
import java.util.*;

import javax.management.openmbean.KeyAlreadyExistsException;

/**
 * Used to store query search criteria within a {@link HpdsQuery} instance.
 * This class only saves keys for search
 *
 * @author  Nick Benik
 * @version %I%, %G%
 * @since   1.0
 */
public class HpdsQueryCriteriaKeys {
    private String helpText;
    protected HashMap<String, HpdsQueryCriteria> entries = new HashMap<>();
    protected HpdsQueryCriteriaKeys() {
        this.helpText = "";
    }
    protected HpdsQueryCriteriaKeys(String helpDesc) {
        this.helpText = helpDesc;
    }

    // ===[ ADD ]========================================
    /**
     * Store the name of a single key to search for.
     * @param key   Identifies a query criterion
     * @since       1.0
     */
    public void add(String key) {
        // add a single key
        List<String> keys = new ArrayList<>();
        keys.add(key);
        this.doAddKeys(keys);
    }


    /**
     * Store the names of several keys to search for.
     * @param keys  Identifies one or more a query criteria
     * @since       1.0
     */
    public void add(List<String> keys) {
        // add multiple keys
        this.doAddKeys(keys);
    }


    // ===[ ADD-Implementation ]========================================
    private void doAddKeys(List<String> keys) {
        Integer l = keys.size();
        for (Integer i = 0; i < l; i++) {
            String newKey = keys.get(i);
            if (this.entries.containsKey(newKey)) {
                throw new KeyAlreadyExistsException(HpdsQueryCriteria.ERROR_MSG_ENTRY_EXISTS + newKey);
            } else {
                HpdsQueryCriteria temp = new HpdsQueryCriteria();
                temp.queryKey = newKey;
                temp.entryType = HpdsQueryCriteria.ENTRY_TYPE_KEY;
                this.entries.put(temp.queryKey, temp);
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
    public void delete(String key) {
        // delete key
        List<String> keys = new ArrayList<>();
        keys.add(key);
        this.doDeleteKeys(keys);
    }


    /**
     * Delete several search criteria by their keys. It does not matter what
     * kind of value the criterion is (existence, value, range, categorical).
     * @param keys  Identifies one or more a query criteria
     * @since       1.0
     */
    public void delete(List<String> keys) {
        this.doDeleteKeys(keys);
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

    // ===[ OTHER FUNCTIONS ]========================================
    /**
     * Function used to clear all criteria from the instantiated object.
     * @since   1.0
     */
    public void clear() {
        this.entries.clear();
    }


    /**
     * Get direct access to the entries in the instantiated object.
     *
     * @return  A cloned copy of the internal HashMap used by the object.
     * @since   1.0
     */
    public HashMap<String, HpdsQueryCriteria> getRawEntries() {
        // this call also allows changes to the internal state since it does a shallow copy via .clone()
        // this function is used by unit tests
        return (HashMap<String, HpdsQueryCriteria>) this.entries.clone();
    }

    public ArrayList<String> getJsonObject() {
        ArrayList<String> ret = (ArrayList<String>) this.entries.keySet();
        return ret;
    }
}