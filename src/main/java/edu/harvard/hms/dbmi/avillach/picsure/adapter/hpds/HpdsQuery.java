package edu.harvard.hms.dbmi.avillach.picsure.adapter.hpds;

import edu.harvard.hms.dbmi.avillach.hpds.data.query.Query;
import edu.harvard.hms.dbmi.avillach.hpds.data.query.Filter.DoubleFilter;


import java.util.*;

public class HpdsQuery {
    private HpdsResourceConnection resourceConnection;
    private HpdsQueryCriteriaKeys listSelect = new HpdsQueryCriteriaKeys("");
    private HpdsQueryCriteriaKeys listRequire = new HpdsQueryCriteriaKeys("");
    private HpdsQueryCriteriaKeyValues listFilter = new HpdsQueryCriteriaKeyValues("");

    protected HpdsQuery(HpdsResourceConnection rc) {
        this.resourceConnection = rc;
    }

    public void help() {
        // for jShell
    }
    public void show() {
        // for jShell
    }
    public HpdsQueryCriteriaKeys select() {
        return this.listSelect;
    }
    public HpdsQueryCriteriaKeys require() {
        return this.listRequire;
    }
    public HpdsQueryCriteriaKeyValues filter() {
        return this.listFilter;
    }

    public Integer getCount() {
        return 0;
    }
    public List<String[]> getResults() {
        return new ArrayList<String[]>();
    }
    public void getRunDetails() {
        // for jShell
    }
    public void getQueryCommand() {
        // for jShell
    }
    protected Query buildQuery() {
        HashMap<String, HpdsQueryCriteria> entries;

        Query tempQuery = new Query();

        //  SET THE SELECT FILTERS
        entries = this.listSelect.getRawEntries();
        if (entries.size() > 0) {
            tempQuery.fields = new ArrayList<>();
            tempQuery.fields.addAll(entries.keySet());
        }

        //  SET THE REQUIRE FILTERS
        entries = this.listRequire.getRawEntries();
        if (entries.size() > 0) {
            tempQuery.requiredFields = new ArrayList<>();
            tempQuery.requiredFields.addAll(entries.keySet());
        }

        // Category Filters
        entries = this.listFilter.getRawEntries();
        for(String key : entries.keySet()) {
            HpdsQueryCriteria entry = (HpdsQueryCriteria) entries.get(key);
            switch (entry.entryType) {
                case HpdsQueryCriteria.ENTRY_TYPE_CATEGORICAL:
                    if (tempQuery.categoryFilters == null) tempQuery.categoryFilters = new HashMap<String, String[]>();
                    tempQuery.categoryFilters.put(key, entry.categories.toArray(new String[0]));
                    break;
                case HpdsQueryCriteria.ENTRY_TYPE_RANGE:
                    if (tempQuery.numericFilters == null) tempQuery.numericFilters = new HashMap<String, DoubleFilter>();
                    tempQuery.numericFilters.put(key, new DoubleFilter(entry.min, entry.max));
                    break;
                default:
                    break;
            }
        }

        return tempQuery;
    }
}
