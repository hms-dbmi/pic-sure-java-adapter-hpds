package edu.harvard.hms.dbmi.avillach.picsure.adapter.hpds;

import edu.harvard.hms.dbmi.avillach.picsure.client.*;

import java.util.UUID;

public class HpdsResourceConnection extends BasePicSureResourceConnection {

    protected IPicSureConnection refConnectionObj;
    protected IPicSureConnectionAPI refApiObj;

    protected HpdsResourceConnection(IPicSureConnection connection, UUID resource_uuid) {
        super(connection, resource_uuid);
        this.refConnectionObj = connection;
        this.refApiObj = connection.getApiObject();
    }

    @Override
    public void help() {}

    public HpdsDictionary dictionary() { return new HpdsDictionary(this); }
    public HpdsQuery query() {
        return new HpdsQuery(this);
    }


}
