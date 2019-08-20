package edu.harvard.hms.dbmi.avillach.picsure.adapter.hpds;

import edu.harvard.hms.dbmi.avillach.hpds.data.query.Query;
import edu.harvard.hms.dbmi.avillach.hpds.data.query.Filter.DoubleFilter;

import edu.harvard.hms.dbmi.avillach.picsure.adapter.hpds.HpdsQueryCriteria;


import java.util.*;

public class HpdsQuery {
    private HpdsResourceConnection resourceConnection;
    private HpdsAttrListKeys listSelect = new HpdsAttrListKeys("");
    private HpdsAttrListKeys listRequire = new HpdsAttrListKeys("");
    private HpdsAttrListKeyValues listFilter = new HpdsAttrListKeyValues("");

    protected HpdsQuery(HpdsResourceConnection rc) {
        this.resourceConnection = rc;
    }

    public void help() {
        // for jShell
    }
    public void show() {
        // for jShell
    }
    public HpdsAttrListKeys select() {
        return this.listSelect;
    }
    public HpdsAttrListKeys require() {
        return this.listRequire;
    }
    public HpdsAttrListKeyValues filter() {
        return this.listFilter;
    }

    public Integer getCount() {
        return 0;
    }
    public List<String> getResults() {
        return new ArrayList<String>();
    }
    public void getRunDetails() {
        // for jShell
    }
    public void getQueryCommand() {
        // for jShell
    }
    private Query buildQuery() {
        Query tempQuery = new Query();

        //  SET THE SELECT FILTERS
        tempQuery.fields.addAll(this.listSelect.getRawEntries().keySet());

        //  SET THE REQUIRE FILTERS
        tempQuery.requiredFields.addAll(this.listRequire.getRawEntries().keySet());

        // Category Filters
        HashMap<String, HpdsQueryCriteria> entries = this.listFilter.getRawEntries();
        for(String key : entries.keySet()) {
            HpdsQueryCriteria entry = (HpdsQueryCriteria) entries.get(key);
            switch (entry.entryType) {
                case HpdsQueryCriteria.ENTRY_TYPE_CATEGORICAL:
                    tempQuery.categoryFilters.put(key, entry.categories.toArray(new String[0]));
                    break;
                case HpdsQueryCriteria.ENTRY_TYPE_RANGE:
                    tempQuery.numericFilters.put(key, new DoubleFilter(entry.min, entry.max));
                    break;
                default:
                    break;
            }
        }

        return tempQuery;
    }
}
