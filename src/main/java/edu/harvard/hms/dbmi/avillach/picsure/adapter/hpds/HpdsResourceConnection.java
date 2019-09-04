package edu.harvard.hms.dbmi.avillach.picsure.adapter.hpds;

import edu.harvard.hms.dbmi.avillach.picsure.client.*;

import java.util.UUID;

public class HpdsResourceConnection extends BasePicSureResourceConnection {

    protected IPicSureConnection refConnectionObj;
    protected IPicSureConnectionAPI refApiObj;

    protected HpdsResourceConnection(HpdsAdapter adapter, UUID resource_uuid) {
        super(adapter, resource_uuid);
        this.refConnectionObj = adapter.refConnectionObj;
        this.refApiObj = adapter.refApiObj;
    }

    @Override
    public void help() {}

    public HpdsDictionary dictionary() { return new HpdsDictionary(this); }
    public HpdsQuery query() {
        return new HpdsQuery(this);
    }


}
