package edu.harvard.hms.dbmi.avillach.picsure.adapter.hpds;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import java.util.NoSuchElementException;
import java.lang.ArrayStoreException;
import javax.management.openmbean.KeyAlreadyExistsException;
import java.lang.IllegalStateException;


import edu.harvard.hms.dbmi.avillach.picsure.adapter.hpds.HpdsQueryCriteria;

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
    protected void add(String key) {
        // add a single key
        List<String> keys = new ArrayList<>();
        keys.add(key);
        this.doAddKeys(keys);
    }
    protected void add(List<String> keys) {
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
    protected void delete(String key) {
        // delete key
        List<String> keys = new ArrayList<>();
        keys.add(key);
        this.doDeleteKeys(keys);
    }
    protected void delete(List<String> keys) {
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
    public void show() {
        // for jShell
    }
    public void help() {
        // for jShell
    }

    public void clear() {
        this.entries.clear();
    }

    public HashMap<String, HpdsQueryCriteria> getRawEntries() {
        // this call also allows changes to the internal state since it does a shallow copy via .clone()
        // this function is used by unit tests
        return (HashMap<String, HpdsQueryCriteria>) this.entries.clone();
    }
}