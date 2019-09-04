package edu.harvard.hms.dbmi.avillach.picsure.adapter.hpds;

import edu.harvard.hms.dbmi.avillach.picsure.client.BasePicSureAdapter;
import edu.harvard.hms.dbmi.avillach.picsure.client.Connection;
import edu.harvard.hms.dbmi.avillach.picsure.client.IPicSureConnection;
import edu.harvard.hms.dbmi.avillach.picsure.client.IPicSureConnectionAPI;

import java.util.UUID;

public class HpdsAdapter extends BasePicSureAdapter {
    private IPicSureConnectionAPI hpdsApiObj;
    private IPicSureConnection hpdsConnectionObj;

    public HpdsAdapter(Connection connection) {
        super(connection);
        this.hpdsConnectionObj = HpdsAdapter.super.refConnectionObj;
        this.hpdsApiObj = HpdsAdapter.super.refApiObj;
    }

    public String getConnectionToken(){
        return this.hpdsConnectionObj.getTOKEN();
    }

    public void help() {
        // for jShell
    }
    public void version() {
        // for jShell
    }
    public HpdsResourceConnection useResource(UUID resource_uuid) {
        return new HpdsResourceConnection(this, resource_uuid);
    }
}
