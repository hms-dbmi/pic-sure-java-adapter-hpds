package edu.harvard.hms.dbmi.avillach.picsure.adapter.hpds;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.lang.ArrayStoreException;
import java.lang.IndexOutOfBoundsException;
import java.util.NoSuchElementException;
import javax.management.openmbean.KeyAlreadyExistsException;

import edu.harvard.hms.dbmi.avillach.picsure.adapter.hpds.HpdsQueryCriteria;

public class HpdsAttrListKeys {
    private String helpText;
    protected HashMap<String, HpdsQueryCriteria> entries;

    public static final String ERROR_MSG_ENTRY_EXISTS = "Can not add new query restriction, key already exists: ";
    public static final String ERROR_MSG_ENTRY_MISSING = "Can not delete/change query restriction, key does not exists: ";
    public static final String ERROR_MSG_ENTRY_WRONG_TYPE = "Can not change query restriction, existing key references the wrong type: ";


    protected HpdsAttrListKeys(String helpDesc) {
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
                throw new KeyAlreadyExistsException(ERROR_MSG_ENTRY_EXISTS + newKey);
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
                throw new NoSuchElementException(ERROR_MSG_ENTRY_MISSING + searchKey);
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




/*
public class HpdsAttrListKeys extends HpdsAttrList{

    protected HpdsAttrListKeys(String helpDesc) {
        super(helpDesc);
    }



    public void add(String key) {
        // add a single key
        super.add(key);
    }
    public void add(List<String> keys) {
        // add multiple keys
        super.add(keys);
    }
    public void delete(String key) {
        // delete a single key
        super.delete(key);
    }
    public void delete(List<String> keys) {
        // delete multiple keys
        super.delete(keys);
    }

    public void show() {
        // for jShell
        // Custom display of entries is performed here
    }
}
*/
