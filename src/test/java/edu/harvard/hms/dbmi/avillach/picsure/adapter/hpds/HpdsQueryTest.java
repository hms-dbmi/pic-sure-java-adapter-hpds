package edu.harvard.hms.dbmi.avillach.picsure.adapter.hpds;

import com.google.gson.Gson;
import edu.harvard.hms.dbmi.avillach.hpds.data.query.Query;
import edu.harvard.hms.dbmi.avillach.picsure.client.Client;
import edu.harvard.hms.dbmi.avillach.picsure.client.Connection;
import org.junit.Test;

import java.util.Arrays;
import java.util.UUID;

import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

public class HpdsQueryTest {

/*
    @Test
    public void testInstantiation() {
        Connection mockConnection = mock(Connection.class);
        //client.connect("http://any.url","any_value_as_token");
        // when(mockConnection.getInfo(Matchers.startsWith("foo"))).thenReturn("foo");
        // assertNotNull("Was mockConnection object created", mockConnection);
        // when(mockConnection.getResources()).thenCallRealMethod();

        HpdsAdapter adapter = new HpdsAdapter(mockConnection);
        assertNotNull("Was adapter object created", adapter);
        // assertEquals("foo info", adapter.getConncetionToken("foobar"));


        UUID test_uuid = UUID.randomUUID();
        HpdsResourceConnection resourceConnection = adapter.useResource(test_uuid);
        assertNotNull("Was ResourceConnection object created", resourceConnection);

        HpdsQuery query = resourceConnection.query();
        assertNotNull("Was HpdsQuery object created", query);
    }

    @Test
    public void testQueryGeneration() {
        Client client = new Client();
        Connection mockConnection = client.connect("http://any.url","any_value_as_token");
        assertNotNull("Was mockConnection object created", mockConnection);

        HpdsAdapter adapter = new HpdsAdapter(mockConnection);
        assertNotNull("Was adapter object created", adapter);

        UUID test_uuid = UUID.randomUUID();
        HpdsResourceConnection resourceConnection = adapter.useResource(test_uuid);
        assertNotNull("Was ResourceConnection object created", resourceConnection);

        HpdsQuery query = resourceConnection.query();
        assertNotNull("Was HpdsQuery object created", query);

        query.select().add(Arrays.asList("gender","age"));
        query.require().add("EMR_ID");
        query.filter().add("categories", Arrays.asList("cat1","cat2"));
        query.filter().add("age", 18, 85);
        Query queryCommand = query.buildQuery();

        // serialize to json for quick testing
        Gson gson = new Gson();
        String outputJSON = gson.toJson(queryCommand);

        // TEST QUERY OUTPUT
        String expectedJSON = "{\"expectedResultType\":\"DATAFRAME\",\"crossCountFields\":[],\"fields\":[\"gender\",\"age\"],\"requiredFields\":[\"EMR_ID\"],\"numericFilters\":{\"age\":{\"min\":18.0,\"max\":85.0}},\"categoryFilters\":{\"categories\":[\"cat1\",\"cat2\"]}}";
        assertEquals("The query's JSON is not as expected", expectedJSON, outputJSON);

    }

*/

}
