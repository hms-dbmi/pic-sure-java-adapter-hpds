package edu.harvard.hms.dbmi.avillach.picsure.adapter.hpds;

import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class HpdsQueryCriteriaKeysTest {

    @Test
    public void testAddKey() {
        String keyValue = "test key";
        // save query criteria
        HpdsQueryCriteriaKeys testCriteria = new HpdsQueryCriteriaKeys();
        testCriteria.add(keyValue);

        // get query criteria
        HashMap<String, HpdsQueryCriteria> entries = testCriteria.getRawEntries();
        HpdsQueryCriteria queryAttr = entries.get(keyValue);

        // check query criteria's attributes
        assertEquals("Query criteria's key does not match", keyValue, queryAttr.queryKey);
        assertEquals("Query criteria's type is wrong", HpdsQueryCriteria.ENTRY_TYPE_KEY, queryAttr.entryType);
        assertNull("Query criteria's min value should be null", queryAttr.min);
        assertNull("Query criteria's max value should be null", queryAttr.max);
        assertEquals("Query criteria should have no categories populated", 0, queryAttr.categories.size());
    }

    @Test
    public void testMultiAddKey() {
        String keyValueA = "testkeyA";
        String keyValueB = "testkeyB";
        List<String> keyList = Arrays.asList(keyValueA,keyValueB);
        // save query criteria
        HpdsQueryCriteriaKeys testCriteria = new HpdsQueryCriteriaKeys();
        testCriteria.add(keyList);

        // check query criteria's attributes
        HashMap<String, HpdsQueryCriteria> entries = testCriteria.getRawEntries();
        // examine entryA
        HpdsQueryCriteria queryAttr = entries.get(keyValueA);
        assertNotNull(queryAttr);
        assertEquals("Query criteria's key does not match", keyValueA, queryAttr.queryKey);
        assertEquals("Query criteria's type is wrong", HpdsQueryCriteria.ENTRY_TYPE_KEY, queryAttr.entryType);
        assertNull("Query criteria's min value is wrong", queryAttr.min);
        assertNull("Query criteria's max value is wrong", queryAttr.max);
        assertEquals("Query criteria should have 2 test categories populated", 0, queryAttr.categories.size());
        // examine entryB
        queryAttr = entries.get(keyValueB);
        assertNotNull(queryAttr);
        assertEquals("Query criteria's key does not match", keyValueB, queryAttr.queryKey);
        assertEquals("Query criteria's type is wrong", HpdsQueryCriteria.ENTRY_TYPE_KEY, queryAttr.entryType);
        assertNull("Query criteria's min value is wrong", queryAttr.min);
        assertNull("Query criteria's max value is wrong", queryAttr.max);
        assertEquals("Query criteria should have 2 test categories populated", 0, queryAttr.categories.size());
    }

    @Test
    public void testDeleteKey() {
        String keyValue = "testkey";
        // save query criteria
        HpdsQueryCriteriaKeys testCriteria = new HpdsQueryCriteriaKeys();
        testCriteria.add(keyValue);

        // delete
        testCriteria.delete(keyValue);

        // get query criteria
        HashMap<String, HpdsQueryCriteria> entries = testCriteria.getRawEntries();
        HpdsQueryCriteria queryAttr = entries.get(keyValue);
        assertNull(queryAttr);
    }

    @Test
    public void testMultiDeleteKey() {
        String keyValueA = "testkeyA";
        String keyValueB = "testkeyB";
        List<String> keyList = Arrays.asList(keyValueA,keyValueB);
        // save query criteria
        HpdsQueryCriteriaKeys testCriteria = new HpdsQueryCriteriaKeys();
        testCriteria.add(keyList);
        // check query criteria's attributes
        HashMap<String, HpdsQueryCriteria> entries = testCriteria.getRawEntries();
        assertEquals("We are expecting 2 entries for the query criteria", 2, entries.size());
        // delete both keys
        testCriteria.delete(keyList);
        // check query criteria's attributes
        entries = testCriteria.getRawEntries();
        assertEquals("We are expecting 0 entries for the query criteria", 0, entries.size());
    }

    @Test(expected = NoSuchElementException.class)
    public void testDeleteNonExistingKey() {
        // query criteria
        HpdsQueryCriteriaKeys testCriteria = new HpdsQueryCriteriaKeys();
        testCriteria.delete("DoesNotExist");
    }
}
