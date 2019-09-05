package edu.harvard.hms.dbmi.avillach.picsure.adapter.hpds;

import edu.harvard.hms.dbmi.avillach.hpds.data.query.Query;
import edu.harvard.hms.dbmi.avillach.picsure.adapter.hpds.HpdsAdapter;
import edu.harvard.hms.dbmi.avillach.picsure.adapter.hpds.HpdsResourceConnection;
import edu.harvard.hms.dbmi.avillach.picsure.client.Client;
import edu.harvard.hms.dbmi.avillach.picsure.client.Connection;
import edu.harvard.hms.dbmi.avillach.picsure.client.IPicSureConnectionAPI;
import org.junit.Before;
import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

public class HpdsResourceConnectionTest {

    private UUID myResourceUUID;
    private UUID myQueryUUID;
    private URL myEndpoint;
    private String myToken;
    private Connection myConnection;

    public HpdsResourceConnectionTest() {}

    @Before
    public void beforeClass() {
        this.myResourceUUID = UUID.randomUUID();
        this.myQueryUUID = UUID.randomUUID();
        try {
            this.myEndpoint = new URL("http://some.url");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        this.myToken = "TEST_TOKEN";
    }

    @Test
    public void testInstantiate() {

        // not using mocks at the moment

        Connection myConnection = Client.connect(this.myEndpoint, this.myToken);
        assertNotNull("myConnection object was not valid", myConnection);
        assertSame("myConnection.TOKEN is not the same", this.myToken, myConnection.TOKEN);
        assertSame("myConnection.ENDPOINT is not the same", this.myEndpoint, myConnection.ENDPOINT);
        IPicSureConnectionAPI myAPI = myConnection.getApiObject();


        UUID myResourceUUID = UUID.randomUUID();
        HpdsResourceConnection myResource = HpdsAdapter.useResource(myConnection, myResourceUUID);
        assertNotNull("myResource object was not valid", myResource);
        assertSame("myResource.TOKEN is not the same", this.myToken, myResource.getToken());
        assertSame("myResource.ENDPOINT_URL is not the same", this.myEndpoint, myResource.getEndpointUrl());
        assertSame("myResource.protectedApiObj is not the same", myAPI, myResource.getApiObject());
        assertSame("myResource.protectedConnectionObj is not the same", myConnection, myResource.getConnection());

    }
}
