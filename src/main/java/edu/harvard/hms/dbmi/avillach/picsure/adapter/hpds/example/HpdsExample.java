package edu.harvard.hms.dbmi.avillach.picsure.adapter.hpds.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import edu.harvard.hms.dbmi.avillach.picsure.adapter.hpds.*;
import edu.harvard.hms.dbmi.avillach.picsure.client.Client;
import edu.harvard.hms.dbmi.avillach.picsure.client.Connection;

import java.util.*;
import java.util.stream.Collectors;

/**
 * HpdsExample serves as a command-line example of how to use
 * the PIC-SURE connection and HPDS libraries together in a
 * programatic way.
 *
 * @author  Nick Benik
 * @version %I%, %G%
 * @since   1.0
 */

public class HpdsExample {

    public static void main(String[] args) {

        if (args.length != 3) {
            System.out.println("SYNTAX ERROR! Three arguments are required.");
            System.out.println("USAGE: HpdsExample http://endpoint.url/ AUTH_TOKEN SOME-HPDS-RESOURCE-UUID");
            return;
        }


        HpdsDictionaryRecord record;
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        String output;

        // create a connection to a PIC-SURE network using the PicSure connection library
        Connection connection = Client.connect(args[0], args[1]);

        // obtain a reference to a HPDS resource on the PIC-SURE network via the HpdsAdapter library
        HpdsResourceConnection resource = HpdsAdapter.useResource(connection, UUID.fromString(args[2]));

        // get a reference to the resource's data dictionary component
        HpdsDictionary dictionary = resource.dictionary();


        // create a new query
        HpdsQuery query = resource.query();

        // search the data dictionary for "gender"
        HpdsDictionaryResults dict_results = dictionary.find("gender");
        // print the results
        output = null;
        try {
            output = mapper.writeValueAsString(dict_results.entries());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        System.out.println("Data Dictionary Search Results for GENDER:");
        System.out.println(output + "\n");
        // select the first gender and add it to our query as filter and select criterion
        record = (HpdsDictionaryRecord) dict_results.entries().get(0);
        query.filter().add(record.key, Arrays.asList(record.categoryValues.get(0)));
        query.select().add(record.key);


        // search the data dictionary for "weight"
        dict_results = dictionary.find("weight");
        // print the results
        output = null;
        try {
            output = mapper.writeValueAsString(dict_results.entries());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        System.out.println("Data Dictionary Search Results for WEIGHT:");
        System.out.println(output + "\n");
        // select the first entry for weight and add it to our query as required and select criterion
        record = (HpdsDictionaryRecord) dict_results.entries().get(0);
        query.require().add(record.key);
        query.select().add(record.key);


        // display the query
        System.out.println("Query JSON:");
        query.getQueryCommand();

        Integer queryCountResult = query.getCount();
        System.out.println("Query COUNT:");
        System.out.println(queryCountResult+"\n");

        ArrayList<HashMap<String, String>> queryResult = query.getResults();
        System.out.println("Query RESULT:");
        for (int i=0; i<queryResult.size() && i<10; i++) {
            System.out.println(queryResult.get(i));
        }
    }

}
