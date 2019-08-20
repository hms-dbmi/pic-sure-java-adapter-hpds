package edu.harvard.hms.dbmi.avillach.picsure.adapter.hpds;

import edu.harvard.hms.dbmi.avillach.picsure.client.Client;
import edu.harvard.hms.dbmi.avillach.picsure.client.Connection;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.assertNotNull;

public class HpdsDictionaryTest {

    @Test
    public void testInstantiation() {
        Client client = new Client();
        Connection mockConnection = client.connect("http://any.url","any_value_as_token");
        assertNotNull("Was mockConnection object created", mockConnection);

        HpdsAdapter adapter = new HpdsAdapter(mockConnection);
        assertNotNull("Was adapter object created", adapter);

        UUID test_uuid = UUID.randomUUID();
        HpdsResourceConnection resourceConnection = adapter.useResource(test_uuid);
        assertNotNull("Was ResourceConnection object created", resourceConnection);

        HpdsDictionary dictionary = resourceConnection.dictionary();
        assertNotNull("Was HpdsDictionary object created", dictionary);
    }
}
