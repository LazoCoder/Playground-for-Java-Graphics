/**
 * The disjoint set class data structures.
 * This is used to keep track of which walls in the maze are connected and which are not.
 */
class DisjointSet {

    private int[] array;
    private int[] arrayOfSizes;
    private int size;

    /**
     * Constructs the disjoint set class.
     * @param capacity  the amount of items that the set will contain
     */
    DisjointSet (int capacity) {
        array = new int[capacity];

        for (int i = 0; i < array.length; i++) {
            array[i] = i;
        }

        arrayOfSizes = new int[capacity];

        for (int i = 0; i < arrayOfSizes.length; i++) {
            arrayOfSizes[i] = 1;
        }

        size = capacity;
    }

    /**
     * Get the number of disjoint sets.
     * @return  the quantity of sets
     */
    int size () {
        return size;
    }

    private int find (int item) {
        if (array[item] == item) {
            return item;
        } else {
            return array[item] = find(array[item]);
        }
    }

    /**
     * Joins two items' sets together.
     * @param item1     the first item
     * @param item2     the second item
     */
    void union (int item1, int item2) {

        int set1 = find(item1);
        int set2 = find(item2);

        if (isConnected(item1, item2)) {
            return;
        }

        if (arrayOfSizes[set1] > arrayOfSizes[set2]) {
            array[set2] = array[set1];
            arrayOfSizes[set1] = arrayOfSizes[set1] + arrayOfSizes[set2];
        } else {
            array[set1] = array[set2];
            arrayOfSizes[set2] = arrayOfSizes[set1] + arrayOfSizes[set2];
        }

        size--;
    }

    /**
     * Check to see if two items are in the same set.
     * @param item1     the first item
     * @param item2     the second item
     * @return          true if the items are in the same set
     */
    boolean isConnected (int item1, int item2) {
        return find(item1) == find(item2);
    }

    public String toString () {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < array.length; i++) {
            sb.append(i);
            sb.append(": \t");
            sb.append(array[i]);
            sb.append("\tSize: ");
            sb.append(arrayOfSizes[i]);
            sb.append("\n");
        }

        return new String(sb);
    }
}
