import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

/**
 * Reduces the color spectrum in an image to a specified number of Colors.
 * This allows for larger clusters of colors in the image splitter.
 * Example, if there is a cluster of black pixels but some of the pixels are
 * an off-black color, then those pixels are rounded to black so that they
 * can be grouped together as being in the same cluster.
 */
class Quantization {

    /**
     * This class cannot be instantiated.
     */
    private Quantization () {}

    /**
     * Round the colors.
     * @param image         the image who's colors will be rounded
     * @param numOfColors   the number of colors to limit the image to
     */
    static void quantize (BufferedImage image, int numOfColors) {

        if (numOfColors < 1) {
            throw new RuntimeException("numOfColors must be greater than 0.");
        }

        HashMap<Color, Integer> hashMap = countColors(image);
        ArrayList<Color> list = sortByValue(hashMap);

        while (list.size() > numOfColors) {
            list.remove(list.size() - 1);
        }

        roundColors(image, list);

    }

    private static Color convertToColor (int rgb) {

        int r = (rgb >>> 16) & 0xFF;
        int g = (rgb >>> 8) & 0xFF;
        int b = rgb & 0xFF;

        return new Color(r, g, b);
    }

    private static HashMap<Color, Integer> countColors (BufferedImage image) {

        HashMap<Color, Integer> hashMap = new HashMap<>();
        Color color;

        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {

                color = convertToColor(image.getRGB(x, y));

                if (hashMap.containsKey(color)) {
                    hashMap.put(color, hashMap.get(color) + 1);
                } else {
                    hashMap.put(color, 1);
                }

            }
        }

        return hashMap;
    }

    private static ArrayList<Color> sortByValue(Map<Color, Integer> map) {

        ArrayList<Color> result = new ArrayList<>();
        Stream<Map.Entry<Color, Integer>> st = map.entrySet().stream();

        st.sorted( Map.Entry.comparingByValue() )
                .forEachOrdered( e -> result.add(0, e.getKey()) );

        return result;
    }

    private static void roundColors (BufferedImage image, ArrayList<Color> list) {

        Color color;
        Color closestColor;
        double closestDistance;
        double distance;
        Graphics graphics = image.createGraphics();

        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {

                color = convertToColor(image.getRGB(x, y));

                closestColor = list.get(0);
                closestDistance = Double.MAX_VALUE;

                // Find closest color.
                for (Color c : list) {

                    distance = distance(color, c);

                    if (distance < closestDistance) {
                        closestColor = c;
                        closestDistance = distance;
                    }
                }

                // Set new color.
                if (!color.equals(closestColor)) {
                    graphics.setColor(closestColor);
                    graphics.drawRect(x, y, 0, 0);
                }

            }
        }

        graphics.dispose();
    }

    private static double distance (Color c1, Color c2) {

        double redDiff = c2.getRed() - c1.getRed();
        double greenDiff = c2.getGreen() - c2.getGreen();
        double blueDiff = c2.getBlue() - c2.getBlue();

        double redDiffSquared = Math.pow(redDiff, 2);
        double greenDiffSquared = Math.pow(greenDiff, 2);
        double blueDiffSquared = Math.pow(blueDiff, 2);

        return Math.sqrt(redDiffSquared + greenDiffSquared + blueDiffSquared);

    }
}
