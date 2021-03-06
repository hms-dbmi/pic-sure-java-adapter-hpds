package edu.harvard.hms.dbmi.avillach.picsure.adapter.hpds;

import edu.harvard.hms.dbmi.avillach.picsure.client.api.BasePicSureResourceConnection;
import edu.harvard.hms.dbmi.avillach.picsure.client.api.IPicSureConnection;
import edu.harvard.hms.dbmi.avillach.picsure.client.api.IPicSureConnectionAPI;

import java.util.UUID;


/**
 * HpdsResourceConnection is the main configuration object containing
 * configuration information about a HPDS-powered PIC-SURE Resource.
 * <p>
 * This includes connection information used to connect to a PIC-SURE
 * network as well as a preconfigured IPicSureConnectionAPI object that
 * exposes the IPicSureRS operations used to communicate with a PIC-SURE
 * network.  Adapters for each PIC-SURE resource type are required to
 * use the IPicSureConnectionAPI to communicate with the server/network.
 * The IPicSureConnectionAPI object handles all network connectivity and
 * authentication for any adapters that are build up on it.
 * </p>
 * @author  Nick Benik
 * @version %I%, %G%
 * @since   1.0
 * @see IPicSureConnectionAPI
 */
public class HpdsResourceConnection extends BasePicSureResourceConnection {

    private IPicSureConnection refConnectionObj;
    private IPicSureConnectionAPI refApiObj;

    /**
     * Protected constructor to be used by HpdsAdapter object.
     *
     * @param connection        a configured IPicSureConnection
     * @param resource_uuid     a UUID that points to the PIC-SURE resource that
     *                          this HpdsResourceConnection will operate against
     * @see                     edu.harvard.hms.dbmi.avillach.picsure.adapter.hpds.HpdsResourceConnection
     * @see                     edu.harvard.hms.dbmi.avillach.picsure.client.api.IPicSureConnection
     * @since                   1.0
     */
    protected HpdsResourceConnection(IPicSureConnection connection, UUID resource_uuid) {
        super(connection, resource_uuid);
        this.refConnectionObj = connection;
        this.refApiObj = connection.getApiObject();
    }


    public IPicSureConnection getConnection() {
        return this.refConnectionObj;
    }
    public IPicSureConnectionAPI getApiObject() {
        return this.refApiObj;
    }

    public HpdsDictionary dictionary() {
        return new HpdsDictionary(this);
    }
    public HpdsQuery query() {
        return new HpdsQuery(this);
    }
}
