package edu.harvard.hms.dbmi.avillach.picsure.adapter.hpds;

import edu.harvard.hms.dbmi.avillach.picsure.client.BasePicSureAdapter;
import edu.harvard.hms.dbmi.avillach.picsure.client.Connection;
import edu.harvard.hms.dbmi.avillach.picsure.client.IPicSureConnection;
import edu.harvard.hms.dbmi.avillach.picsure.client.IPicSureConnectionAPI;

import java.util.UUID;


/**
 * HpdsAdapter is the main entry point to interact with all PIC-SURE Resources
 * that are powered by a HPDS server on a PIC-SURE network.
 *
 * HpdsAdapter will generate HpdsResourceConnections which are used to access
 * the specified HpdsDictionary and instantiate one or more HpdsQuery objects
 * that are used to run queries against the resource.
 *
 * @author  Nick Benik
 * @version %I%, %G%
 * @since   1.0
 */
public class HpdsAdapter extends BasePicSureAdapter {

    private IPicSureConnectionAPI hpdsApiObj;
    private IPicSureConnection hpdsConnectionObj;

    /**
     * Class function for use in jShell to print help instructions on the screen for this object's use.
     * @since   1.0
     */
    public static void help() {
        // for jShell
    }

    /**
     * Class function for use in jShell to get version of HPDS adapter library.
     * @since   1.0
     */
    public static void version() {
        // for jShell
    }

    /**
     * Static function that instantiates a configured HpdsResourceConnection.
     *
     * @param connection        a configured IPicSureConnection
     * @param resource_uuid     a UUID that points to the PIC-SURE resource that
     *                          the HpdsResourceConnection will operate against
     * @return                  HpdsResourceConnection
     * @see                     edu.harvard.hms.dbmi.avillach.picsure.adapter.hpds.HpdsResourceConnection
     * @see                     edu.harvard.hms.dbmi.avillach.picsure.client.IPicSureConnection
     * @since                   1.0
     */
    public static HpdsResourceConnection useResource(IPicSureConnection connection, UUID resource_uuid) {
        return new HpdsResourceConnection(connection, resource_uuid);
    }
}
