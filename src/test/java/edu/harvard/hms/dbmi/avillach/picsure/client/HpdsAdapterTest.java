package edu.harvard.hms.dbmi.avillach.picsure.client;

import edu.harvard.dbmi.avillach.domain.QueryRequest;
import edu.harvard.dbmi.avillach.domain.SearchResults;
import edu.harvard.hms.dbmi.avillach.hpds.data.query.Query;
import edu.harvard.hms.dbmi.avillach.picsure.adapter.hpds.*;
import org.apache.commons.codec.Charsets;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import com.google.gson.Gson;


public class HpdsAdapterTest {

    public UUID myResourceUUID = UUID.randomUUID();
    public UUID myQueryUUID = UUID.randomUUID();


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


    @Test
    public void testMockApiInstantiation() {

        OutputStream resultStream = new ByteArrayOutputStream();

        PicSureConnectionAPI mockAPI = mock(PicSureConnectionAPI.class);


/*
        PicSureConnectionAPI mockAPI = mock(PicSureConnectionAPI.class);
        when(mockAPI.info(eq(this.myResourceUUID))).thenReturn(resultStream);
        when(mockAPI.search(eq(this.myResourceUUID), any(Query.class))).thenReturn(resultStream);
        when(mockAPI.syncQuery(eq(this.myResourceUUID), any(Query.class))).thenReturn(resultStream);
//        when(mockAPI.asynchQuery(eq(this.myResourceUUID), any(Query.class))).thenReturn(resultStream.write(this.myQueryUUID.toString().getBytes(Charsets.UTF_8)));
        when(mockAPI.queryStatus(eq(this.myResourceUUID), eq(this.myQueryUUID))).thenReturn(resultStream);
        when(mockAPI.queryResults(eq(this.myResourceUUID), eq(this.myQueryUUID))).thenReturn(resultStream);

        Connection mockConnection = mock(Connection.class);
//        when(mockConnection.getApiObject()).thenReturn((IPicSureConnectionAPI) mockAPI);
        doReturn((IPicSureConnectionAPI) mockAPI).when(mockConnection).getApiObject();

        //client.connect("http://any.url","any_value_as_token");
//        when(mockConnection.getInfo(Matchers.startsWith("foo"))).thenReturn("foo");
        // assertNotNull("Was mockConnection object created", mockConnection);
        // when(mockConnection.getResources()).thenCallRealMethod();

        assertNotNull("mockConnection object was not valid", mockConnection);
        IPicSureConnectionAPI testApi = mockConnection.getApiObject();
        assertSame("The returned API object was not our mockApiObject", mockAPI, testApi);
*/
    }



/*
    @Test
    public void testMockApiSearch() {

        Object searchResults = buildSearchAsthma();

        Gson gson = new Gson();
        String jsonOutput = gson.toJson(searchResults);

        InputStream istream = (InputStream) new ByteArrayInputStream(jsonOutput.getBytes(Charset.forName("UTF-8")));
        SearchResults searchResults1 = new SearchResults();


        URL testApiUrl = null;
        try {
            testApiUrl = new URL("http://any.url");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        IPicSureConnectionAPI testApi = new PicSureConnectionAPI(testApiUrl,"token", true);
        IPicSureConnectionAPI mockAPI = mock(testApi);
        doReturn(searchResults1).when(mockAPI).search(eq(this.myResourceUUID), any(QueryRequest.class));
//        when(mockAPI.search(eq(this.myResourceUUID), any(Object.class))).thenReturn(istream);

        Connection mockConnection = mock(Connection.class);
//        when(mockConnection.getApiObject()).thenReturn((IPicSureConnectionAPI) mockAPI);
        doReturn((IPicSureConnectionAPI) mockAPI).when(mockConnection).getApiObject();

        //client.connect("http://any.url","any_value_as_token");
//        when(mockConnection.getInfo(Matchers.startsWith("foo"))).thenReturn("foo");
        // assertNotNull("Was mockConnection object created", mockConnection);
        // when(mockConnection.getResources()).thenCallRealMethod();

        assertNotNull("mockConnection object was not valid", mockConnection);
        IPicSureConnectionAPI testApi = mockConnection.getApiObject();
        assertSame("The returned API object was not our mockApiObject", mockAPI, testApi);
        // do a search and get back the results stream
        QueryRequest qr = new QueryRequest();
        qr.setQuery("asthma");
        SearchResults resultsStream = mockAPI.search(this.myResourceUUID, qr);

        assertEquals("The API Object did not return the correct value for info service", jsonOutput, resultsStream.toString());

        System.out.println(jsonOutput);


    }
*/
}
