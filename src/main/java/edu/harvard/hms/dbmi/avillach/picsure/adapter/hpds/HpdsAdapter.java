package edu.harvard.hms.dbmi.avillach.picsure.adapter.hpds;

import edu.harvard.hms.dbmi.avillach.picsure.client.BasePicSureAdapter;
import edu.harvard.hms.dbmi.avillach.picsure.client.Connection;
import edu.harvard.hms.dbmi.avillach.picsure.client.IPicSureConnection;
import edu.harvard.hms.dbmi.avillach.picsure.client.IPicSureConnectionAPI;

import java.util.UUID;

public class HpdsAdapter extends BasePicSureAdapter {
    private IPicSureConnectionAPI hpdsApiObj;
    private IPicSureConnection hpdsConnectionObj;

    public static void help() {
        // for jShell
    }
    public static void version() {
        // for jShell
    }
    public static HpdsResourceConnection useResource(IPicSureConnection connection, UUID resource_uuid) {
        return new HpdsResourceConnection(connection, resource_uuid);
    }
}
