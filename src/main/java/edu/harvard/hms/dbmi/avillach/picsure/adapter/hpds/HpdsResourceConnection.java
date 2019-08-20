package edu.harvard.hms.dbmi.avillach.picsure.adapter.hpds;

import edu.harvard.hms.dbmi.avillach.picsure.client.BasePicSureResourceConnection;
import edu.harvard.hms.dbmi.avillach.picsure.client.Connection;
import edu.harvard.hms.dbmi.avillach.picsure.client.IPicSureConnection;

import java.util.UUID;

public class HpdsResourceConnection extends BasePicSureResourceConnection {
    public HpdsResourceConnection(IPicSureConnection connection, UUID resource_uuid) {
        super(connection, resource_uuid);
    }

    @Override
    public void help() {}

    public HpdsDictionary dictionary() {
        return new HpdsDictionary(this);
    }
    public HpdsQuery query() {
        return new HpdsQuery(this);
    }


}
