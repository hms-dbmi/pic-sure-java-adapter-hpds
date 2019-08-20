package edu.harvard.hms.dbmi.avillach.picsure.adapter.hpds;

import java.util.ArrayList;
import java.util.List;

public class HpdsDictionary {
    private HpdsResourceConnection resourceConnection;
    protected HpdsDictionary(HpdsResourceConnection rc) {
        this.resourceConnection = rc;
    }

    public void help() {
        // for jShell
    }

    public HpdsDictionaryResults find(String term) {
        List<String> ret = new ArrayList<>();
        return new HpdsDictionaryResults(ret);
    }

}
