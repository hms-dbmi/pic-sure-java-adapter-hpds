package edu.harvard.hms.dbmi.avillach.picsure.adapter.hpds;

import edu.harvard.dbmi.avillach.domain.QueryRequest;
import edu.harvard.dbmi.avillach.domain.SearchResults;
import edu.harvard.hms.dbmi.avillach.hpds.data.query.Query;
import edu.harvard.hms.dbmi.avillach.picsure.adapter.hpds.*;
import edu.harvard.hms.dbmi.avillach.picsure.client.*;
import org.apache.commons.codec.Charsets;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.net.MalformedURLException;
import java.net.URL;
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
    public void testConnectionSetup() {
        // test to confirm that the connection, resource uuid, and ApiObject are properly passed
        String myToken = "MY_TOKEN_STRING";
        URL myEndpoint = null;
        try {
            myEndpoint = new URL("http://MY_ENDPOINT_STRING");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        PicSureConnectionAPI mockAPI = mock(PicSureConnectionAPI.class);
        IPicSureConnection mockConnection = mock(IPicSureConnection.class);
        when(mockConnection.getApiObject()).thenReturn(mockAPI);
        when(mockConnection.getTOKEN()).thenReturn(myToken);
        when(mockConnection.getENDPOINT()).thenReturn(myEndpoint);


        UUID resourceId = UUID.randomUUID();
        HpdsResourceConnection resource = HpdsAdapter.useResource(mockConnection, resourceId);

        assertSame("The connection object should be the same", mockConnection, resource.getConnection());
        assertSame("The ApiObject should be the same", mockAPI, resource.getApiObject());
        assertSame("The resource UUID should be the same", resourceId, resource.getResourceUUID());
        assertEquals("The Token should be the same", myToken, resource.getToken());
        assertEquals("The Endpoint should be the same", myEndpoint, resource.getEndpointUrl());

    }

    @Test
    public void testVoidFunctions() {
        HpdsAdapter.help();
        HpdsAdapter.version();
    }
}
