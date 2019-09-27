package edu.harvard.hms.dbmi.avillach.picsure.adapter.hpds;

import edu.harvard.dbmi.avillach.domain.QueryRequest;
import edu.harvard.dbmi.avillach.domain.SearchResults;
import edu.harvard.hms.dbmi.avillach.picsure.client.*;
import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class HpdsDictionaryTest {


    @Test
    public void testInstantiation() {

        String myToken = "MY_TOKEN_STRING";
        URL myEndpoint = null;
        try {
            myEndpoint = new URL("http://MY_ENDPOINT_STRING");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        UUID myUUID = UUID.randomUUID();

        PicSureConnectionAPI mockAPI = mock(PicSureConnectionAPI.class);

        HpdsResourceConnection mockResource = mock(HpdsResourceConnection.class);
        when(mockResource.getResourceUUID()).thenReturn(myUUID);
        when(mockResource.getToken()).thenReturn(myToken);
        when(mockResource.getApiObject()).thenReturn(mockAPI);

        HpdsDictionary myDictionary = new HpdsDictionary(mockResource);

        assertNotNull("Was dictionary object created", myDictionary);
    }

    @Test
    public void testVoidFunctions() {
        HpdsResourceConnection mockResource = mock(HpdsResourceConnection.class);
        HpdsDictionary myDictionary = new HpdsDictionary(mockResource);

        myDictionary.help();
    }

    @Test
    public void testFind() {
        String myToken = "MY_TOKEN_STRING";
        String mySearchTerm = "MY_SEARCH_STRING";
        LinkedHashMap<String, LinkedHashMap> myGoodResults = new LinkedHashMap<>();

        URL myEndpoint = null;
        try {
            myEndpoint = new URL("http://MY_ENDPOINT_STRING");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        UUID myUUID = UUID.randomUUID();

        PicSureConnectionAPI mockAPI = mock(PicSureConnectionAPI.class);

        HpdsResourceConnection mockResource = mock(HpdsResourceConnection.class);
        when(mockResource.getResourceUUID()).thenReturn(myUUID);
        when(mockResource.getToken()).thenReturn(myToken);
        when(mockResource.getApiObject()).thenReturn(mockAPI);

        SearchResults mySearchResults = new SearchResults();
        mySearchResults.setResults(myGoodResults);
        when(mockAPI.search(same(myUUID), any(QueryRequest.class))).thenReturn(mySearchResults);


        HpdsDictionary myDictionary = new HpdsDictionary(mockResource);
        assertNotNull("Was dictionary object created", myDictionary);

        HpdsDictionaryResults myResults = myDictionary.find(mySearchTerm);
        assertNotNull("Was dictionary-results object created", myResults);
        assertEquals("Results were returned", myGoodResults, mySearchResults.getResults());


    }
/*

/*    public void testDoSearch() {

        UUID myResourceUUID = UUID.randomUUID();

        Object searchResultObjs = buildSearchAsthma();

        QueryRequest queryRequestResults = new QueryRequest();
        queryRequestResults.setQuery("asthma");

        SearchResults searchResults = new SearchResults();
        searchResults.setResults(searchResultObjs);

        IPicSureConnectionAPI tempMockApi = (IPicSureConnectionAPI) new Object();
        PicSureConnectionAPI mockAPI = mock(tempMockApi.getClass());
        when(mockAPI.search(eq(myResourceUUID), any(QueryRequest.class))).thenReturn(searchResults);

//        Gson gson = new Gson();
//        String jsonOutput = gson.toJson(searchResults);
//        byte[] outputArray = jsonOutput.getBytes(StandardCharsets.UTF_8);


        Connection mockConnection = mock(Connection.class);
        doReturn((IPicSureConnectionAPI) mockAPI).when(mockConnection).getApiObject();
        assertNotNull("mockConnection object was not valid", mockConnection);

        HpdsAdapter adapter = new HpdsAdapter(mockConnection);
        HpdsResourceConnection resource = adapter.useResource(myResourceUUID);
        HpdsDictionary dictionary = resource.dictionary();

        // do a search and get back HpdsDictionaryResults object
        HpdsDictionaryResults results = dictionary.find("asthma");

        // make sure results are correct format
        assertSame("Dictionary search results keys are not as expected", Arrays.asList("hasAsthma", "asthmaAttacksWeekly"), results.keys());

        // make sure results are correct format
        assertSame("Dictionary search results enties are not the same", null, results.keys());


*//*

    }




    private Object buildSearchPhenotype (String name, List<String> categoryValues, Double min, Double max, Integer observationCount) {
        HashMap ret = new HashMap();
        ret.put("name", name);
        ret.put("observationCount", observationCount);

        if (categoryValues == null) {
            ret.put("categorical", false);
        } else {
            ret.put("categorical", true);
            ret.put("categoryValues", (List<String>) categoryValues);
        }

        if (min != null) {
            ret.put("min", min);
        } else {
            if (max != null) ret.put("min", (double) 0);
        }
        if (max != null) {
            ret.put("max", max);
        } else {
            if (min != null) ret.put("max", (double) min * 2);
        }

        return ret;
    }

    private Object buildSearchAsthma () {
        // generate the test results
        HashMap searchResults = new HashMap();
        HashMap searchSec1 = new HashMap();
        searchSec1.put("info", new HashMap());
        HashMap phenotypes = new HashMap();
        phenotypes.put("hasAsthma",buildSearchPhenotype("hasAsthma", Arrays.asList("Yes","No","Maybe"), null, null, 100));
        phenotypes.put("asthmaAttacksWeekly",buildSearchPhenotype("asthmaAttacksWeekly", null, Double.valueOf(0), Double.valueOf(25), 10));
        searchSec1.put("phenotypes", phenotypes);

        searchResults.put("results", searchSec1);
        searchResults.put("searchQuery", "asthma");
        return searchResults;
    }
*/

}
