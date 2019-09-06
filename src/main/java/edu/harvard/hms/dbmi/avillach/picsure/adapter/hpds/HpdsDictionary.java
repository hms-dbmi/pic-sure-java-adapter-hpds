package edu.harvard.hms.dbmi.avillach.picsure.adapter.hpds;

import com.google.gson.Gson;
import edu.harvard.dbmi.avillach.domain.QueryRequest;
import edu.harvard.dbmi.avillach.domain.SearchResults;
import edu.harvard.dbmi.avillach.service.ResourceWebClient;

import javax.management.Query;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;

/**
 * HpdsDictionary is used to extract data dictionary information from a
 * HPDS-backed PIC-SURE Resource. The resource it acts upon is dependent on
 * the resource its parent HpdsResourceConnection is configured to act upon.
 *
 * @author  Nick Benik
 * @version %I%, %G%
 * @since   1.0
 */
public class HpdsDictionary {
    private HpdsResourceConnection resourceConnection;

    /**
     * Protected constructor function used by parent's {@link HpdsResourceConnection#dictionary()} function.
     * @param rc    {@link HpdsResourceConnection}
     */
    protected HpdsDictionary(HpdsResourceConnection rc) {
        this.resourceConnection = rc;
    }

    /**
     * Class function for use in jShell to print help instructions on the screen for this object's use.
     * @since   1.0
     */
    public void help() {
        // for jShell
    }

    /**
     *  Searches the data dictionary of the HPDS-backed PIC-SURE Resource
     *  it is configured for by its parent HpdsResourceConnection.
     *
     * @param   term
     * @return  HpdsDictionaryResults containing the matching entries
     * @see     HpdsDictionaryResults
     * @see     HpdsResourceConnection
     */
    public HpdsDictionaryResults find(String term) {
        UUID resourceUUID = this.resourceConnection.getResourceUUID();

        // issue request via the internal API object
        HashMap<String,String> queryObj = new HashMap<>();
        queryObj.put("query", term);
        QueryRequest queryRequest = new QueryRequest();
        queryRequest.setQuery(queryObj);
        queryRequest.setResourceUUID(resourceUUID);
        Map<String, String> credentials = new HashMap<>();
        credentials.put(ResourceWebClient.BEARER_TOKEN_KEY, this.resourceConnection.getToken());
        queryRequest.setResourceCredentials(credentials);
        SearchResults results = resourceConnection.getApiObject().search(resourceUUID, queryRequest);
        // convert into an internal results object
        results.getResults();
        String resultStr = results.toString();
        Gson gson = new Gson();
        Object resultObj = gson.fromJson(resultStr, Object.class);
        // return instantiated
        return new HpdsDictionaryResults(resultObj);
    }

}
