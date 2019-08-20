package edu.harvard.hms.dbmi.avillach.picsure.adapter.hpds;

import javax.management.openmbean.KeyAlreadyExistsException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public final class HpdsAttrListKeyValues extends HpdsAttrListKeys {

    public HpdsAttrListKeyValues(String helpDesc) {
        super(helpDesc);
    }

    // ===[ ADD ]========================================
    public void add(String key) { super.add(key); }
    public void add(List<String> keys) { super.add(keys); }

    protected void add(String key, List<String> categories) {
        // add categorical
        List<String> keys = new ArrayList<>();
        keys.add(key);
        this.doAddCategoricals(keys, categories);
    }
    protected void add(List<String> keys, List<String> categories) {
        // add categorical
        this.doAddCategoricals(keys, categories);
    }
    protected void add(String key, Double min, Double max) {
        // add range
        List<String> keys = new ArrayList<>();
        keys.add(key);
        this.doAddRange(keys, min, max);
    }
    protected void add(List<String> keys, Double min, Double max) {
        // add range
        this.doAddRange(keys, min, max);
    }
    // ===[ ADD-Implementation ]========================================
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
                    throw new ArrayStoreException(ERROR_MSG_ENTRY_WRONG_TYPE + searchKey);
                }
            } else {
                // add a new categorical entry
                HpdsQueryCriteria temp = new HpdsQueryCriteria();
                temp.queryKey = searchKey;
                temp.entryType = HpdsQueryCriteria.ENTRY_TYPE_CATEGORICAL;
                temp.categories.addAll(categories);
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
                    // IF YOU PASS null FOR MIN OR MAX THEN ITS EXISTING VALUE
                    // WILL NOT BE CHANGED.  TO REMOVE A MIN OR MAX VALUE YOU
                    // MUST ISSUE USE A delete ACTION AND SET THE MIN AND/OR
                    // MAX PARAMETER TO TRUE!!!
                    this.entries.replace(searchKey, temp);
                } else {
                    throw new ArrayStoreException(ERROR_MSG_ENTRY_WRONG_TYPE + searchKey);
                }
            } else {
                // add a new categorical entry
                HpdsQueryCriteria temp = new HpdsQueryCriteria();
                temp.queryKey = searchKey;
                temp.entryType = HpdsQueryCriteria.ENTRY_TYPE_RANGE;
                if (min != null) temp.min = min;
                if (max != null) temp.max = max;
                this.entries.put(searchKey, temp);
            }
        }
    }

    // ===[ DELETE ]========================================
    public void delete(String key) { super.delete(key);}
    public void delete(List<String> keys) { super.delete(keys); }
    protected void delete(String key, List<String> categories) {
        // delete a key's categories
        List<String> keys = new ArrayList<>();
        keys.add(key);
        this.doDeleteCategoricals(keys, categories);
    }
    protected void delete(List<String> keys, List<String> categories) {
        this.doDeleteCategoricals(keys, categories);
    }
    protected void delete(String key, boolean min, boolean max) {
        // delete the min or max value from a range, move to only keys
        // if both min and max are not set
        List<String> keys = new ArrayList<>();
        keys.add(key);
        this.doDeleteRange(keys, min, max);
    }
    protected void delete(List<String> keys, boolean min, boolean max) {
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
                throw new NoSuchElementException(ERROR_MSG_ENTRY_MISSING + searchKey);
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
                }
            } else {
                throw new NoSuchElementException(ERROR_MSG_ENTRY_MISSING + searchKey);
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
                    this.entries.replace(searchKey, temp);
                } else {
                    throw new ArrayStoreException(ERROR_MSG_ENTRY_WRONG_TYPE + searchKey);
                }
            } else {
                throw new NoSuchElementException(ERROR_MSG_ENTRY_MISSING + searchKey);
            }
        }
    }

}