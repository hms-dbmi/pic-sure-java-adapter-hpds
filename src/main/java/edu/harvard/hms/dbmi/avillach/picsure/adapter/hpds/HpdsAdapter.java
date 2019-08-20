package edu.harvard.hms.dbmi.avillach.picsure.adapter.hpds;

import edu.harvard.hms.dbmi.avillach.picsure.client.BasePicSureAdapter;
import edu.harvard.hms.dbmi.avillach.picsure.client.IPicSureConnection;

import java.util.UUID;

public class HpdsAdapter extends BasePicSureAdapter {
    public HpdsAdapter(IPicSureConnection connection) {
        super(connection);
    }

    public void help() {
        // for jShell
    }
    public void version() {
        // for jShell
    }
    public void list() {
        // for jShell
    }
    public HpdsResourceConnection useResource(UUID resource_uuid) {
        return new HpdsResourceConnection(this.refConnectionObj, resource_uuid);
    }
}
