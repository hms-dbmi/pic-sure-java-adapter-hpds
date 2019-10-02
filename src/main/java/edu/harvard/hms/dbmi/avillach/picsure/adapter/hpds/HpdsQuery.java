package edu.harvard.hms.dbmi.avillach.picsure.adapter.hpds;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import edu.harvard.dbmi.avillach.domain.QueryRequest;
import edu.harvard.hms.dbmi.avillach.hpds.data.query.Query;
import edu.harvard.hms.dbmi.avillach.hpds.data.query.Filter.DoubleFilter;
import edu.harvard.hms.dbmi.avillach.hpds.data.query.ResultType;
import edu.harvard.hms.dbmi.avillach.picsure.client.api.IPicSureConnectionAPI;
import org.apache.http.impl.auth.HttpAuthenticator;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;


/**
 * HpdsQuery is used to build a query to search a HPDS-backed PIC-SURE Resource
 * for matching records. The query is build serially through actions against its
 * HpdsQueryCriteriaKeys and HpdsQueryCriteriaKeyValues entries: {@link #select()}, {@link #require()}, and {@link #filter()}.
 * Several methods can be used to send the query to the server and get results.
 * Once a query is run, its criteria change be tweaked and the query can be rerun
 * to get updated results. Multiple HpdsQuery objects can exist with each one
 * having its own selection/search criteria.
 *
 * @author  Nick Benik
 * @version %I%, %G%
 * @since   1.0
 */
public class HpdsQuery {
    private HpdsResourceConnection resourceConnection;
    private HpdsQueryCriteriaKeys listSelect = new HpdsQueryCriteriaKeys("");
    private HpdsQueryCriteriaKeys listRequire = new HpdsQueryCriteriaKeys("");
    private HpdsQueryCriteriaKeyValues listFilter = new HpdsQueryCriteriaKeyValues("");

    /**
     * Constructor function to be called by hiearchial parent object.
     * @param   rc      A configured HpdsResourceConnection for a
     *                  HPDS-backed PIC-SURE Resource.
     * @since   1.0
     */
    protected HpdsQuery(HpdsResourceConnection rc) {
        this.resourceConnection = rc;
    }




    /**
     * Class function to get JSON string representing the query's current search criteria.
     * @since   1.0
     */
    public String getQueryCommand() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        // print the results
        String output = null;
        try {
            output = mapper.writeValueAsString(this.buildQuery());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return output;
    }


    /**
     * Used to access the HpdsQuery object's Select criteria.
     * The Select criteria will only operate on query keys.
     * @return  HpdsQueryCriteriaKeys
     * @since   1.0
     */
    public HpdsQueryCriteriaKeys select() {
        return this.listSelect;
    }


    /**
     * Used to access the HpdsQuery object's Require criteria.
     * The Require criteria will only operate on query keys.
     * @return  HpdsQueryCriteriaKeys
     * @since   1.0
     */
    public HpdsQueryCriteriaKeys require() {
        return this.listRequire;
    }


    /**
     * Used to access the HpdsQuery object's Filter criteria.
     * The Filter criteria takes several kinds of inputs to filter by
     * existence of a query key, a value of a key, a value range of a key,
     * the existence of one or more matching values within a categorical key.
     * @return  HpdsQueryCriteriaKeyValues
     * @see     HpdsQueryCriteriaKeyValues
     * @since   1.0
     */
    public HpdsQueryCriteriaKeyValues filter() {
        return this.listFilter;
    }


    /**
     * Used get the count of records in the HPDS Resource that match the configured query criteria.
     * This function will fire a request to the server to retreve an answer.
     * @return  Integer or Null
     * @throws  SecurityException when authentication fails
     * @see     #select()
     * @see     #require()
     * @see     #filter()
     * @since   1.0
     */
    public Integer getCount() throws SecurityException {
        // TODO: This should throw an error if server problems are encountered
        Query countQuery = this.buildQuery();
        countQuery.expectedResultType = ResultType.COUNT;
        IPicSureConnectionAPI apiObject = this.resourceConnection.getApiObject();

        QueryRequest request = new QueryRequest();
        request.setResourceUUID(this.resourceConnection.getResourceUUID());
        request.setQuery(countQuery);
        InputStream resultsStream = apiObject.querySync(request);

        Integer returnedCount = null;
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(resultsStream, StandardCharsets.UTF_8)) ) {
            returnedCount = Integer.parseInt(reader.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return returnedCount;
    }


    /**
     * Used get the records data from the HPDS Resource that match the configured query criteria.
     * This function will fire a request to the server to retreve an answer.  The format is a
     * List of Strings with each string being a single line of the returned data.
     * @return  List<Hashmap<String, String>>
     * @throws  SecurityException when authentication fails
     * @see     #select()
     * @see     #require()
     * @see     #filter()
     * @since   1.0
     */
    public ArrayList<HashMap<String, String>> getResults() throws SecurityException {
        // TODO: This should throw an error if server problems are encountered

        ArrayList<HashMap<String,String>> ret = new ArrayList();

        // get results
        InputStream resultsStream = this.getRawResults();

        String[] header = null;
        String[] lineBuffer = null;
        HashMap<String, String> lineObject = null;
        // convert to cleaner output
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(resultsStream, StandardCharsets.UTF_8)) ) {
            header = reader.readLine().split("\\,");

            lineBuffer = reader.readLine().split("\\,");
            while (lineBuffer != null) {
                lineObject = new HashMap<>();
                // copy line elements to the hashmap object for the line
                for(int i=0; i<header.length; i++) {
                    lineObject.put(header[i], lineBuffer[i]);
                }
                // add the current lineObject to our output array
                ret.add(lineObject);
                // try to read the next line
                lineBuffer = reader.readLine().split("\\,");
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ret;
    }


    /**
     * Used get the records data from the HPDS Resource that match the configured query criteria.
     * This function will fire a request to the server to retreve an answer.  The format is an
     * InputStream fed by the HttpClient containing the returned data.
     * @return  InputStream
     * @throws  SecurityException when authentication fails
     * @see     #select()
     * @see     #require()
     * @see     #filter()
     * @since   1.0
     */
    public InputStream getRawResults() throws SecurityException {
        // TODO: This should throw an error if server problems are encountered

        Query dfQuery = this.buildQuery();
        dfQuery.expectedResultType = ResultType.DATAFRAME;
        IPicSureConnectionAPI apiObject = this.resourceConnection.getApiObject();

        QueryRequest request = new QueryRequest();
        request.setResourceUUID(this.resourceConnection.getResourceUUID());
        request.setQuery(dfQuery);
        InputStream resultsStream = apiObject.querySync(request);

        return resultsStream;
    }


    /**
     * Function used to transform internal QueryCriteria into a PIC-SURE Query object.
     * The Filter criteria takes several kinds of inputs to filter by
     * existence of a query key, a value of a key, a value range of a key,
     * the existence of one or more matching values within a categorical key.
     * @return  HpdsQueryCriteriaKeyValues
     * @see     Query
     * @since   1.0
     */
    protected Query buildQuery() {
        HashMap<String, HpdsQueryCriteria> entries;

        Query tempQuery = new Query();

        //  SET THE SELECT FILTERS
        entries = this.listSelect.getRawEntries();
        if (entries.size() > 0) {
            tempQuery.fields = new ArrayList<>();
            tempQuery.fields.addAll(entries.keySet());
        }

        //  SET THE REQUIRE FILTERS
        entries = this.listRequire.getRawEntries();
        if (entries.size() > 0) {
            tempQuery.requiredFields = new ArrayList<>();
            tempQuery.requiredFields.addAll(entries.keySet());
        }

        // Category Filters
        entries = this.listFilter.getRawEntries();
        for(String key : entries.keySet()) {
            HpdsQueryCriteria entry = (HpdsQueryCriteria) entries.get(key);
            switch (entry.entryType) {
                case HpdsQueryCriteria.ENTRY_TYPE_CATEGORICAL:
                    if (tempQuery.categoryFilters == null) tempQuery.categoryFilters = new HashMap<String, String[]>();
                    tempQuery.categoryFilters.put(key, entry.categories.toArray(new String[0]));
                    break;
                case HpdsQueryCriteria.ENTRY_TYPE_RANGE:
                    if (tempQuery.numericFilters == null) tempQuery.numericFilters = new HashMap<String, DoubleFilter>();
                    tempQuery.numericFilters.put(key, new DoubleFilter(entry.min, entry.max));
                    break;
                default:
                    break;
            }
        }
        return tempQuery;
    }
}
