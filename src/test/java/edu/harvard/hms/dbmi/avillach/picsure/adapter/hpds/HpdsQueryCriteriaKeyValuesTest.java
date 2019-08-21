package edu.harvard.hms.dbmi.avillach.picsure.adapter.hpds;

import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class HpdsQueryCriteriaKeyValuesTest {

    @Test
    public void testInstantiation() {
        HpdsQueryCriteriaKeyValues testCriteria = new HpdsQueryCriteriaKeyValues();
        assertNotNull("Was HpdsQueryCriteriaKeyValues object created", testCriteria);
    }


    // === [ Key Tests ] ======================================

    @Test
    public void testAddKey() {
        String keyValue = "test key";
        // save query criteria
        HpdsQueryCriteriaKeyValues testCriteria = new HpdsQueryCriteriaKeyValues();
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
        HpdsQueryCriteriaKeyValues testCriteria = new HpdsQueryCriteriaKeyValues();
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
        HpdsQueryCriteriaKeyValues testCriteria = new HpdsQueryCriteriaKeyValues();
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
        HpdsQueryCriteriaKeyValues testCriteria = new HpdsQueryCriteriaKeyValues();
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
        HpdsQueryCriteriaKeyValues testCriteria = new HpdsQueryCriteriaKeyValues();
        testCriteria.delete("DoesNotExist");
        fail("Test should throw NoSuchElementException");
    }

    @Test(expected = ArrayStoreException.class)
    public void testDeleteKeyViaCategories() {
        String keyValue = "testkey";
        // save query criteria
        HpdsQueryCriteriaKeyValues testCriteria = new HpdsQueryCriteriaKeyValues();
        testCriteria.add(keyValue);
        // delete via category operation
        testCriteria.delete(keyValue, Arrays.asList("noCategory"));
        fail("Test should throw ArrayStoreException");
    }

    @Test(expected = ArrayStoreException.class)
    public void testDeleteKeyViaRange() {
        String keyValue = "testkey";
        // save query criteria
        HpdsQueryCriteriaKeyValues testCriteria = new HpdsQueryCriteriaKeyValues();
        testCriteria.add(keyValue);
        // delete via range operation
        testCriteria.delete(keyValue, true, true);
        fail("Test should throw ArrayStoreException");
    }

    // === [ Range Tests ] ======================================

    @Test(expected = IllegalStateException.class)
    public void testAddBlankRange() {
        String keyValue = "testkey";
        // save query criteria
        HpdsQueryCriteriaKeyValues testCriteria = new HpdsQueryCriteriaKeyValues();
        testCriteria.add(keyValue, null, null);
        fail("Test should throw IllegalStateException");
    }

    @Test
    public void testAddRange() {
        String keyValue = "testkey";
        // save query criteria
        HpdsQueryCriteriaKeyValues testCriteria = new HpdsQueryCriteriaKeyValues();
        testCriteria.add(keyValue, 0, 100);

        // get query criteria
        HashMap<String, HpdsQueryCriteria> entries = testCriteria.getRawEntries();
        HpdsQueryCriteria queryAttr = entries.get(keyValue);

        // check query criteria's attributes
        assertEquals("Query criteria's key does not match", keyValue, queryAttr.queryKey);
        assertEquals("Query criteria's type is wrong", HpdsQueryCriteria.ENTRY_TYPE_RANGE, queryAttr.entryType);
        assertEquals("Query criteria's min value is wrong", Double.valueOf(0), queryAttr.min);
        assertEquals("Query criteria's max value is wrong", Double.valueOf(100), queryAttr.max);
        assertEquals("Query criteria should have no categories populated", 0, queryAttr.categories.size());
    }

    @Test
    public void testMultiAddRange() {
        String keyValue = "testkey-";
        // save query criteria
        HpdsQueryCriteriaKeyValues testCriteria = new HpdsQueryCriteriaKeyValues();
        testCriteria.add(Arrays.asList(keyValue + "B", keyValue + "C"), 1, 99);

        // get query criteria
        HashMap<String, HpdsQueryCriteria> entries = testCriteria.getRawEntries();
        HpdsQueryCriteria queryAttr = entries.get(keyValue+"B");

        // check query criteria's attributes
        assertEquals("Query criteria's key does not match", keyValue+"B", queryAttr.queryKey);
        assertEquals("Query criteria's type is wrong", HpdsQueryCriteria.ENTRY_TYPE_RANGE, queryAttr.entryType);
        assertEquals("Query criteria's min value is wrong", Double.valueOf(1), queryAttr.min);
        assertEquals("Query criteria's max value is wrong", Double.valueOf(99), queryAttr.max);
        assertEquals("Query criteria should have no categories populated", 0, queryAttr.categories.size());
    }

    @Test(expected = IllegalStateException.class)
    public void testDeleteRange() {
        String keyValue = "testkey";
        // save query criteria
        HpdsQueryCriteriaKeyValues testCriteria = new HpdsQueryCriteriaKeyValues();
        testCriteria.add(keyValue, 0, 100);

        // get query criteria
        HashMap<String, HpdsQueryCriteria> entries = testCriteria.getRawEntries();
        HpdsQueryCriteria queryAttr = entries.get(keyValue);

        // check query criteria's attributes
        assertEquals("Query criteria's key does not match", keyValue, queryAttr.queryKey);
        assertEquals("Query criteria's type is wrong", HpdsQueryCriteria.ENTRY_TYPE_RANGE, queryAttr.entryType);
        assertEquals("Query criteria's min value is wrong", Double.valueOf(0), queryAttr.min);
        assertEquals("Query criteria's max value is wrong", Double.valueOf(100), queryAttr.max);
        assertEquals("Query criteria should have no categories populated", 0, queryAttr.categories.size());

        // delete the min value
        testCriteria.delete(keyValue, true, false);

        // refresh the query information
        entries = testCriteria.getRawEntries();
        queryAttr = entries.get(keyValue);

        // test that the range is still correct
        assertNull("Query criteria's min value should be deleted", queryAttr.min);
        assertEquals("Query criteria's max value is wrong", Double.valueOf(100), queryAttr.max);

        // delete the max value, this should also throw an error since the key has no range
        testCriteria.delete(keyValue, false, true);
        // refresh the query information
        fail("Test should throw IllegalStateException");
    }

    @Test
    public void testMultiDeleteRange() {
        String keyValue = "testkey";
        // save query criteria
        HpdsQueryCriteriaKeyValues testCriteria = new HpdsQueryCriteriaKeyValues();
        testCriteria.add(Arrays.asList(keyValue+"A", keyValue+"B"), 0, 100);

        // get query criteria
        HashMap<String, HpdsQueryCriteria> entries = testCriteria.getRawEntries();
        HpdsQueryCriteria queryAttr = entries.get(keyValue+"A");

        // delete the min value
        testCriteria.delete(Arrays.asList(keyValue+"A", keyValue+"B"), true, false);

        // refresh the query information
        entries = testCriteria.getRawEntries();
        HpdsQueryCriteria queryAttrA = entries.get(keyValue+"A");
        HpdsQueryCriteria queryAttrB = entries.get(keyValue+"B");

        // check query criteria's attributes
        assertNull("Query criteria's min value is wrong, it was deleted", queryAttrA.min);
        assertEquals("Query criteria's max value is wrong", Double.valueOf(100), queryAttrA.max);
        assertNull("Query criteria's min value is wrong, it was deleted", queryAttrB.min);
        assertEquals("Query criteria's max value is wrong", Double.valueOf(100), queryAttrB.max);
    }

    @Test
    public void testDeleteRangeByKey() {
        String keyValue = "testkey";
        // save query criteria
        HpdsQueryCriteriaKeyValues testCriteria = new HpdsQueryCriteriaKeyValues();
        testCriteria.add(keyValue, 0, 100);

        // delete
        testCriteria.delete(keyValue);

        // get query criteria
        HashMap<String, HpdsQueryCriteria> entries = testCriteria.getRawEntries();
        HpdsQueryCriteria queryAttr = entries.get(keyValue);
        assertNull(queryAttr);
    }

    @Test(expected = NoSuchElementException.class)
    public void testAlterNonExistingRangeRecord() {
        String keyValue = "testkey";
        HpdsQueryCriteriaKeyValues testCriteria = new HpdsQueryCriteriaKeyValues();

        // delete, should throw error
        testCriteria.delete(keyValue, true, false);
        fail("Test should throw NoSuchElementException");
    }

    @Test(expected = ArrayStoreException.class)
    public void testAlterRangeRecordViaCategory() {
        String keyValue = "testkey";
        List<String> categories = Arrays.asList("cat1","cat2");
        // save query criteria
        HpdsQueryCriteriaKeyValues testCriteria = new HpdsQueryCriteriaKeyValues();
        testCriteria.add(keyValue, 0, 100);

        // delete, should throw error
        testCriteria.delete(keyValue, categories);
        fail("Test should throw ArrayStoreException");
    }

    @Test(expected = IllegalStateException.class)
    public void testDeleteAllRangeValues() {
        String keyValue = "testkey";
        // save query criteria
        HpdsQueryCriteriaKeyValues testCriteria = new HpdsQueryCriteriaKeyValues();
        testCriteria.add(keyValue, 0, 100);

        // delete
        testCriteria.delete(keyValue, true, true);
        fail("Test should throw IllegalStateException");
    }

        // === [ Category Tests ] ======================================

    @Test(expected = IllegalStateException.class)
    public void testAddBlankCategory() {
        String keyValue = "testkey";
        // save query criteria
        HpdsQueryCriteriaKeyValues testCriteria = new HpdsQueryCriteriaKeyValues();
        testCriteria.add(keyValue, Arrays.asList());
        fail("Test should throw IllegalStateException");
    }

    @Test
    public void testAddCategory() {
        String keyValue = "testkey";
        List<String> categories = Arrays.asList("cat1","cat2");
        // save query criteria
        HpdsQueryCriteriaKeyValues testCriteria = new HpdsQueryCriteriaKeyValues();
        testCriteria.add(keyValue, categories);

        // get query criteria
        HashMap<String, HpdsQueryCriteria> entries = testCriteria.getRawEntries();
        HpdsQueryCriteria queryAttr = entries.get(keyValue);
        assertNotNull(queryAttr);
        assertEquals("Query criteria's key does not match", keyValue, queryAttr.queryKey);
        assertEquals("Query criteria's type is wrong", HpdsQueryCriteria.ENTRY_TYPE_CATEGORICAL, queryAttr.entryType);
        assertNull("Query criteria's min value is wrong", queryAttr.min);
        assertNull("Query criteria's max value is wrong", queryAttr.max);
        assertEquals("Query criteria should have 2 test categories populated", 2, queryAttr.categories.size());
    }

    @Test
    public void testMultiAddCategory() {
        String keyValueA = "testkeyA";
        String keyValueB = "testkeyB";
        List<String> categories = Arrays.asList("cat1","cat2");
        // save query criteria
        HpdsQueryCriteriaKeyValues testCriteria = new HpdsQueryCriteriaKeyValues();
        testCriteria.add(Arrays.asList(keyValueA, keyValueB), categories);

        // get query criteria
        HashMap<String, HpdsQueryCriteria> entries = testCriteria.getRawEntries();
        // examine entryA
        HpdsQueryCriteria queryAttr = entries.get(keyValueA);
        assertNotNull(queryAttr);
        assertEquals("Query criteria's key does not match", keyValueA, queryAttr.queryKey);
        assertEquals("Query criteria's type is wrong", HpdsQueryCriteria.ENTRY_TYPE_CATEGORICAL, queryAttr.entryType);
        assertNull("Query criteria's min value is wrong", queryAttr.min);
        assertNull("Query criteria's max value is wrong", queryAttr.max);
        assertEquals("Query criteria should have 2 test categories populated", 2, queryAttr.categories.size());
        // examine entryB
        queryAttr = entries.get(keyValueB);
        assertNotNull(queryAttr);
        assertEquals("Query criteria's key does not match", keyValueB, queryAttr.queryKey);
        assertEquals("Query criteria's type is wrong", HpdsQueryCriteria.ENTRY_TYPE_CATEGORICAL, queryAttr.entryType);
        assertNull("Query criteria's min value is wrong", queryAttr.min);
        assertNull("Query criteria's max value is wrong", queryAttr.max);
        assertEquals("Query criteria should have 2 test categories populated", 2, queryAttr.categories.size());
    }

    @Test
    public void testDeleteCategory() {
        String keyValue = "testkey";
        String testCat = "cat-TEST";
        List<String> categories = Arrays.asList("cat1","cat2", testCat);

        // save query criteria
        HpdsQueryCriteriaKeyValues testCriteria = new HpdsQueryCriteriaKeyValues();
        testCriteria.add(keyValue, categories);

        // get query criteria
        HashMap<String, HpdsQueryCriteria> entries = testCriteria.getRawEntries();
        HpdsQueryCriteria queryAttr = entries.get(keyValue);
        assertNotNull(queryAttr);
        assertEquals("Query criteria should have 3 test categories populated", 3, queryAttr.categories.size());

        // delete one of the categories
        testCriteria.delete(keyValue, Arrays.asList(testCat));

        // get query criteria
        entries = testCriteria.getRawEntries();
        queryAttr = entries.get(keyValue);
        assertEquals("Query criteria should have 2 test categories populated", 2, queryAttr.categories.size());
    }

    @Test(expected = IllegalStateException.class)
    public void testDeleteAllCategoryValues() {
        String keyValue = "testkey";
        List<String> categories = Arrays.asList("cat1","cat2");

        // save query criteria
        HpdsQueryCriteriaKeyValues testCriteria = new HpdsQueryCriteriaKeyValues();
        testCriteria.add(keyValue, categories);

        // get query criteria
        HashMap<String, HpdsQueryCriteria> entries = testCriteria.getRawEntries();
        HpdsQueryCriteria queryAttr = entries.get(keyValue);
        assertNotNull(queryAttr);
        assertEquals("Query criteria should have 2 test categories populated", 2, queryAttr.categories.size());

        // delete all of the categories
        testCriteria.delete(keyValue, categories);
        fail("Test should throw IllegalStateException");
    }


    @Test
    public void testMultiDeleteCategory() {
        String keyValueA = "testkeyA";
        String keyValueB = "testkeyB";
        List<String> keyValues = Arrays.asList(keyValueA, keyValueB);
        List<String> categories = Arrays.asList("cat1","cat2");
        // save query criteria
        HpdsQueryCriteriaKeyValues testCriteria = new HpdsQueryCriteriaKeyValues();
        testCriteria.add(keyValues, categories);

        // delete "cat1" category from both records
        testCriteria.delete(keyValues, Arrays.asList("cat1"));

        // get query criteria
        HashMap<String, HpdsQueryCriteria> entries = testCriteria.getRawEntries();
        // examine entryA
        HpdsQueryCriteria queryAttr = entries.get(keyValueA);
        assertNotNull(queryAttr);
        assertTrue("Query criteria should have 2 test categories populated", queryAttr.categories.contains("cat2"));
        // examine entryB
        queryAttr = entries.get(keyValueB);
        assertNotNull(queryAttr);
        assertTrue("Query criteria should have 2 test categories populated", queryAttr.categories.contains("cat2"));
    }

    @Test
    public void testDeleteCategoryByKey() {
        String keyValue = "testkey";
        // save query criteria
        HpdsQueryCriteriaKeyValues testCriteria = new HpdsQueryCriteriaKeyValues();
        testCriteria.add(keyValue, Arrays.asList("cat1","cat2"));

        // delete
        testCriteria.delete(keyValue);

        // get query criteria
        HashMap<String, HpdsQueryCriteria> entries = testCriteria.getRawEntries();
        HpdsQueryCriteria queryAttr = entries.get(keyValue);
        assertNull(queryAttr);
    }

    @Test
    public void testDeleteNonExistingCategory() {
        String keyValue = "testkey";
        List<String> categories = Arrays.asList("cat1","cat2");
        // save query criteria
        HpdsQueryCriteriaKeyValues testCriteria = new HpdsQueryCriteriaKeyValues();
        testCriteria.add(keyValue, categories);

        // delete, should not throw error
        testCriteria.delete(keyValue, Arrays.asList("no-category"));

        // get query criteria
        HashMap<String, HpdsQueryCriteria> entries = testCriteria.getRawEntries();
        HpdsQueryCriteria queryAttr = entries.get(keyValue);
        assertNotNull(queryAttr);
        assertEquals("Should still have the initial categories intact", (List<String>) queryAttr.categories, (List<String>) categories);
    }

    @Test(expected = NoSuchElementException.class)
    public void testAlterNonExistingCategoryRecord() {
        String keyValue = "testkey";
        HpdsQueryCriteriaKeyValues testCriteria = new HpdsQueryCriteriaKeyValues();

        // delete, should throw error
        testCriteria.delete(keyValue, Arrays.asList("no-category"));
        fail("Test should throw NoSuchElementException");
    }

    @Test(expected = ArrayStoreException.class)
    public void testAlterCategoryRecordViaRange() {
        String keyValue = "testkey";
        List<String> categories = Arrays.asList("cat1","cat2");
        // save query criteria
        HpdsQueryCriteriaKeyValues testCriteria = new HpdsQueryCriteriaKeyValues();
        testCriteria.add(keyValue, categories);

        // delete, should throw error
        testCriteria.delete(keyValue, true, true);
        fail("Test should throw ArrayStoreException");
    }

}
