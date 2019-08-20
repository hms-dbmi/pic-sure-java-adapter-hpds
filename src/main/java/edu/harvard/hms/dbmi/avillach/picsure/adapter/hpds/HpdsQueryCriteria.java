package edu.harvard.hms.dbmi.avillach.picsure.adapter.hpds;

import java.util.ArrayList;

public class HpdsQueryCriteria {

    public static final byte ENTRY_TYPE_KEY = 0;
    public static final byte ENTRY_TYPE_RANGE = 1;
    public static final byte ENTRY_TYPE_CATEGORICAL = 2;

    public String queryKey;
    public byte entryType;
    public ArrayList<String> categories;
    public Double min;
    public Double max;

    public HpdsQueryCriteria() {
        this.queryKey = null;
        this.entryType = ENTRY_TYPE_KEY;
        this.categories = new ArrayList<>();
        this.min = null;
        this.max = null;
    }
}
