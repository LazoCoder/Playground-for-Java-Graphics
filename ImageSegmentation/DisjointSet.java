import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * The disjoint set data structure, used for grouping pixels together.
 */
class DisjointSet {

    private HashMap<Integer, Integer> hashMap;
    private int sets;

    /**
     * Construct the disjoint set.
     * @param image     the image who's pixels will be disjoint
     */
    DisjointSet (BufferedImage image) {

        hashMap = new HashMap<>();

        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                hashMap.put(new Point(x, y).hashCode(), -1);
            }
        }

        sets = image.getWidth() * image.getHeight();
    }

    private int find (int hashCode) {

        if (hashMap.get(hashCode) < 0) {
            return hashCode;
        }

        hashMap.put(hashCode, find(hashMap.get(hashCode)));

        return hashMap.get(hashCode);
    }

    /**
     * Joins the pixels' sets together.
     * @param p1    the first pixel
     * @param p2    the second pixel
     */
    void union (Point p1, Point p2) {

        int root1 = find(p1.hashCode());
        int root2 = find(p2.hashCode());

        if (root1 == root2) {
            return;
        }

        if (root2 < root1) {
            hashMap.put(root1, root2);
        } else if (root1 < root2) {
            hashMap.put(root2, root1);
        } else {
            hashMap.put(root1, hashMap.get(root1) - 1);
        }

        sets--;
    }

    /**
     * Checks to see if two pixels are in the same set.
     * @param p1    the first pixel
     * @param p2    the second pixel
     * @return      true if the pixels are in the same set
     */
    boolean connected (Point p1, Point p2) {

        boolean containsP1 = hashMap.containsKey(p1.hashCode());
        boolean containsP2 = hashMap.containsKey(p2.hashCode());

        if (!containsP1 || !containsP2) {
            return false;
        }

        return find(p1.hashCode()) == find(p2.hashCode());
    }

    /**
     * Gets all the pixels in the same set as the specified pixel.
     * @param p     the pixel, who's set will be returned
     * @return      the set that contains the specified pixel
     */
    ArrayList<Point> getSet (Point p) {

        ArrayList<Point> list = new ArrayList<>();
        int set = find(p.hashCode());

        for (Map.Entry<Integer, Integer> entry : hashMap.entrySet()) {
            if (entry.getValue() == set || (entry.getKey() == set && entry.getValue() < 0)) {
                list.add(convertToPoint(entry.getKey()));
            }
        }

        return list;
    }

    private Point convertToPoint (int hashCode) {

        int x = hashCode >>> 16;
        int y = hashCode & 0b1111111111111111;

        return new Point(x, y);

    }

    int getSets () {
        return sets;
    }

}
