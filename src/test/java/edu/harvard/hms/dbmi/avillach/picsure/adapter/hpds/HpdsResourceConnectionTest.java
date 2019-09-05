package edu.harvard.hms.dbmi.avillach.picsure.adapter.hpds;

import edu.harvard.hms.dbmi.avillach.hpds.data.query.Query;
import edu.harvard.hms.dbmi.avillach.picsure.adapter.hpds.HpdsAdapter;
import edu.harvard.hms.dbmi.avillach.picsure.adapter.hpds.HpdsResourceConnection;
import edu.harvard.hms.dbmi.avillach.picsure.client.Client;
import edu.harvard.hms.dbmi.avillach.picsure.client.Connection;
import edu.harvard.hms.dbmi.avillach.picsure.client.IPicSureConnectionAPI;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class HpdsResourceConnectionTest {

    private UUID myResourceUUID;
    private UUID myQueryUUID;
    private URL myEndpoint;
    private String myToken;
    private Connection myConnection;

    public HpdsResourceConnectionTest() {
        this.myResourceUUID = UUID.randomUUID();
        this.myQueryUUID = UUID.randomUUID();
        try {
            this.myEndpoint = new URL("http://some.url");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        this.myToken = "TEST_TOKEN";
    }

/*
    @Test
    public void testInstantiate() {

        // do not use mocks since we need to also test the proper
        // use of "protected" and confirm proper encapsulation

        Client myClient = new Client();
        assertNotNull("myClient object was not valid", myClient);

        Connection myConnection = myClient.connect(this.myEndpoint, this.myToken);
        assertNotNull("myConnection object was not valid", myConnection);
        assertSame("myConnection.TOKEN is not the same", this.myToken, myConnection.TOKEN);
        assertSame("myConnection.ENDPOINT is not the same", this.myEndpoint, myConnection.ENDPOINT);
        IPicSureConnectionAPI myAPI = myConnection.getApiObject();

        HpdsAdapter myAdapter = new HpdsAdapter(myConnection);
        assertNotNull("myAdapter object was not valid", myAdapter);
        assertSame("myAdapter.refConnectionObj is not correct", myConnection, myAdapter.refConnectionObj);
        assertSame("myAdapter.refApiObj is not correct", myAPI, myAdapter.refApiObj);
        assertSame("myAdapter.getConnectionToken() is not correct", this.myToken, myAdapter.getConnectionToken());


        HpdsResourceConnection myResource = myAdapter.useResource(this.myResourceUUID);
        assertNotNull("myResource object was not valid", myResource);
        assertSame("myResource.TOKEN is not the same", this.myToken, myResource.TOKEN);
        assertSame("myResource.ENDPOINT_URL is not the same", this.myEndpoint, myResource.ENDPOINT_URL);
        assertSame("myResource.protectedApiObj is not the same", myAPI, myResource.protectedApiObj);
        assertSame("myResource.protectedConnectionObj is not the same", myConnection, myResource.protectedConnectionObj);

    }
*/
}
