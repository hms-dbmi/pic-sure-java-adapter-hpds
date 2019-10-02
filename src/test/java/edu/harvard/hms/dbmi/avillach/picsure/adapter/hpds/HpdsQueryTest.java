package edu.harvard.hms.dbmi.avillach.picsure.adapter.hpds;

import com.google.gson.Gson;
import edu.harvard.hms.dbmi.avillach.hpds.data.query.Query;
import edu.harvard.hms.dbmi.avillach.picsure.client.PicSureConnectionAPI;
import org.junit.Before;
import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.UUID;

import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

public class HpdsQueryTest {

    private UUID myResourceUUID;
    private UUID myQueryUUID;
    private URL myEndpoint;
    private String myToken;

    @Before
    public void beforeClass() {
        this.myResourceUUID = UUID.randomUUID();
        this.myQueryUUID = UUID.randomUUID();
        try {
            this.myEndpoint = new URL("http://some.url/");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        this.myToken = "TEST_TOKEN";
    }

    @Test
    public void testInstantiation() {
        PicSureConnectionAPI mockAPI = mock(PicSureConnectionAPI.class);

        HpdsResourceConnection mockResource = mock(HpdsResourceConnection.class);
        when(mockResource.getResourceUUID()).thenReturn(this.myResourceUUID);
        when(mockResource.getToken()).thenReturn(this.myToken);
        when(mockResource.getApiObject()).thenReturn(mockAPI);

        HpdsQuery myQuery = new HpdsQuery(mockResource);
        assertNotNull("Was HpdsQuery object created", myQuery);
    }


    @Test
    public void testVoidFunctions() {
        PicSureConnectionAPI mockAPI = mock(PicSureConnectionAPI.class);

        HpdsResourceConnection mockResource = mock(HpdsResourceConnection.class);
        when(mockResource.getResourceUUID()).thenReturn(this.myResourceUUID);
        when(mockResource.getToken()).thenReturn(this.myToken);
        when(mockResource.getApiObject()).thenReturn(mockAPI);

        HpdsQuery myQuery = new HpdsQuery(mockResource);
        assertNotNull("Was HpdsQuery object created", myQuery);
//        myQuery.help();
//        myQuery.show();
//        myQuery.getRunDetails();
        myQuery.getQueryCommand();
    }


    @Test
    public void testQueryParametersObjects() {
        PicSureConnectionAPI mockAPI = mock(PicSureConnectionAPI.class);

        HpdsResourceConnection mockResource = mock(HpdsResourceConnection.class);
        when(mockResource.getResourceUUID()).thenReturn(this.myResourceUUID);
        when(mockResource.getToken()).thenReturn(this.myToken);
        when(mockResource.getApiObject()).thenReturn(mockAPI);

        HpdsQuery myQuery = new HpdsQuery(mockResource);
        assertNotNull("Was HpdsQuery object created", myQuery);

        HpdsQueryCriteriaKeys testKeys = myQuery.require();
        assertNotNull("Query.require object is wrong", testKeys);

        testKeys = myQuery.select();
        assertNotNull("Query.select object is wrong", testKeys);

        HpdsQueryCriteriaKeyValues testKeyValues = myQuery.filter();
        assertNotNull("Query.filter object is wrong", testKeyValues);
    }



    @Test
    public void testQueryGeneration() {
        PicSureConnectionAPI mockAPI = mock(PicSureConnectionAPI.class);

        HpdsResourceConnection mockResource = mock(HpdsResourceConnection.class);
        when(mockResource.getResourceUUID()).thenReturn(this.myResourceUUID);
        when(mockResource.getToken()).thenReturn(this.myToken);
        when(mockResource.getApiObject()).thenReturn(mockAPI);

        HpdsQuery myQuery = new HpdsQuery(mockResource);
        assertNotNull("Was HpdsQuery object created", myQuery);

        myQuery.select().add(Arrays.asList("gender","age"));
        myQuery.require().add("EMR_ID");
        myQuery.filter().add("categories", Arrays.asList("cat1","cat2"));
        myQuery.filter().add("age", 18, 85);
        Query queryCommand = myQuery.buildQuery();

        // serialize to json for quick testing
        Gson gson = new Gson();
        String outputJSON = gson.toJson(queryCommand);

        // TEST QUERY OUTPUT
        String expectedJSON = "{\"expectedResultType\":\"DATAFRAME\",\"crossCountFields\":[],\"fields\":[\"gender\",\"age\"],\"requiredFields\":[\"EMR_ID\"],\"numericFilters\":{\"age\":{\"min\":18.0,\"max\":85.0}},\"categoryFilters\":{\"categories\":[\"cat1\",\"cat2\"]}}";
        assertEquals("The query's JSON is not as expected", expectedJSON, outputJSON);
    }

}
