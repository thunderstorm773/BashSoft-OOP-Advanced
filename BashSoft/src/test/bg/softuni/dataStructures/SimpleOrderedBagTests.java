package test.bg.softuni.dataStructures;

import main.bg.softuni.contracts.SimpleOrderedBag;
import main.bg.softuni.dataStructures.SimpleSortedList;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SimpleOrderedBagTests {

    private static final int TEST_ELEMENTS_COUNT = 17;

    private SimpleOrderedBag<String> names;
    private List<String> elements;
    private List<String> sortedNames;
    private List<String> unsortedNames;

    @Before
    public void setUp() {
        this.names = new SimpleSortedList<>(String.class);
        this.elements = new ArrayList<>();
        this.sortedNames = new ArrayList<>();
        this.unsortedNames = new ArrayList<>();

        Collections.addAll(this.unsortedNames, "Rosen", "Georgi", "Balkan");
        Collections.addAll(this.sortedNames, "Balkan", "Georgi", "Rosen");
    }

    @Test
    public void testEmptyCtor() {
        this.names = new SimpleSortedList<>(String.class);
        Assert.assertEquals(16, this.names.capacity());
        Assert.assertEquals(0, this.names.size());
    }

    @Test
    public void testCtorWithInitialCapacity() {
        this.names = new SimpleSortedList<>(String.class, 20);
        Assert.assertEquals(20, this.names.capacity());
        Assert.assertEquals(0, this.names.size());
    }

    @Test
    public void testCtorWithInitialComparator() {
        this.names = new SimpleSortedList<>(String.class, String.CASE_INSENSITIVE_ORDER);
        Assert.assertEquals(16, this.names.capacity());
        Assert.assertEquals(0, this.names.size());
    }

    @Test
    public void testCtorWithAllParams() {
        this.names = new SimpleSortedList<>(
                String.class,
                String.CASE_INSENSITIVE_ORDER,
                30);
        Assert.assertEquals(30, this.names.capacity());
        Assert.assertEquals(0, this.names.size());
    }

    @Test
    public void testAddIncreaseSize() {
        this.names.add("Nasko");
        Assert.assertEquals(1, this.names.size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddNullThrowsException() {
        this.names.add(null);
    }

    @Test
    public void testAddUnsortedDataIsHeldSorted() {
        for (int i = 0; i < this.unsortedNames.size(); i++) {
            this.names.add(this.unsortedNames.get(i));
        }

        int dataIndex = 0;
        for (String name : this.names) {
            if (!name.equals(this.sortedNames.get(dataIndex))) {
                throw new IllegalArgumentException();
            }

            dataIndex++;
        }
    }

    @Test
    public void testAddingMoreThanInitialCapacity() {

        for (int i = 0; i < TEST_ELEMENTS_COUNT; i++) {
            this.elements.add("test");
        }

        this.names.addAll(this.elements);
        Assert.assertEquals(17, this.names.size());
        Assert.assertNotEquals(16, this.names.capacity());
    }

    @Test
    public void testAddingAllFromCollectionIncreasesSize() {
        Collections.addAll(this.elements, "a", "b");
        this.names.addAll(this.elements);
        Assert.assertEquals(2, this.names.size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddingAllFromNullThrowsException() {
        this.names.addAll(null);
    }

    @Test
    public void testAddAllKeepsSorted() {
        this.names.addAll(this.unsortedNames);
        int dataIndex = 0;
        for (String name : this.names) {
            if (!name.equals(this.sortedNames.get(dataIndex))) {
                throw new IllegalArgumentException();
            }

            dataIndex++;
        }
    }

    @Test
    public void testRemoveValidElementDecreasesSize() {
        this.names.add("test");
        this.names.remove("test");
        Assert.assertEquals(0, this.names.size());
    }

    @Test
    public void testRemoveValidElementRemovesSelectedOne() {
        String nameToRemove = "ivan";
        this.names.add("ivan");
        this.names.add("nasko");
        this.names.remove(nameToRemove);

        for (String name : this.names) {
            Assert.assertTrue(!name.equals(nameToRemove));
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRemovingNullThrowsException() {
        this.names.remove(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testJoinWithNull() {
        this.names.addAll(this.unsortedNames);
        this.names.joinWith(null);
    }

    @Test
    public void testJoinWorksFine() {
        this.names.addAll(this.unsortedNames);
        String joinedNames = this.names.joinWith(", ");
        String expectedJoinedNames = "Balkan, Georgi, Rosen";

        Assert.assertEquals(
                "Wrong joined names:",
                expectedJoinedNames,
                joinedNames);
    }

    @Test
    public void testEstimatedTimeForOperations() {
        long startTime = System.nanoTime();

        this.names.add("test");
        this.names.addAll(unsortedNames);
        this.names.add("other test");
        this.names.remove("test");
        this.names.size();
        this.names.capacity();
        this.names.joinWith(", ");

        long estimatedTimeInMillSec = (System.nanoTime() - startTime) / 1_000_000;
        Assert.assertTrue(estimatedTimeInMillSec < 250);
    }
}
