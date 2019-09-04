package edu.harvard.hms.dbmi.avillach.picsure.adapter.hpds;

import com.google.gson.Gson;
import edu.harvard.dbmi.avillach.domain.QueryRequest;
import edu.harvard.dbmi.avillach.domain.SearchResults;
import edu.harvard.dbmi.avillach.service.ResourceWebClient;

import javax.management.Query;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HpdsDictionary {
    private HpdsResourceConnection resourceConnection;
    protected HpdsDictionary(HpdsResourceConnection rc) {
        this.resourceConnection = rc;
    }

    public void help() {
        // for jShell
    }

    public HpdsDictionaryResults find(String term) {
        // issue request via the internal API object
        HashMap<String,String> queryObj = new HashMap<>();
        queryObj.put("query", term);
        QueryRequest queryRequest = new QueryRequest();
        queryRequest.setQuery(queryObj);
        queryRequest.setResourceUUID(this.resourceConnection.RESOURCE_UUID);
        Map<String, String> credentials = new HashMap<>();
        credentials.put(ResourceWebClient.BEARER_TOKEN_KEY, this.resourceConnection.TOKEN);
        queryRequest.setResourceCredentials(credentials);
        SearchResults results = resourceConnection.refApiObj.search(resourceConnection.RESOURCE_UUID, queryRequest);
        // convert into an internal results object
        results.getResults();
        String resultStr = results.toString();
        Gson gson = new Gson();
        Object resultObj = gson.fromJson(resultStr, Object.class);
        // return instantiated
        return new HpdsDictionaryResults(resultObj);
    }

}
