package edu.harvard.hms.dbmi.avillach.picsure.adapter.hpds;

import edu.harvard.dbmi.avillach.domain.QueryRequest;
import edu.harvard.dbmi.avillach.domain.SearchResults;

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
     *  Searches the data dictionary of the HPDS-backed PIC-SURE Resource
     *  it is configured for by its parent HpdsResourceConnection.
     *
     * @param   term A string to search the ontology for
     * @return  HpdsDictionaryResults containing the matching entries
     * @throws  SecurityException when authentication fails
     * @see     HpdsDictionaryResults
     * @see     HpdsResourceConnection
     */
    public HpdsDictionaryResults find(String term) throws SecurityException {
        UUID resourceUUID = this.resourceConnection.getResourceUUID();

        // issue request via the internal API object
        QueryRequest queryRequest = new QueryRequest();
        queryRequest.setQuery(term);
        SearchResults results = resourceConnection.getApiObject().search(resourceUUID, queryRequest);
        // return instantiated
        return new HpdsDictionaryResults((LinkedHashMap<String, HashMap>) results.getResults());
    }

}
