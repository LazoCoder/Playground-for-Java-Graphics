import java.util.ArrayList;
import java.util.Random;

/**
 * Helper class for setting where to spawn the caterpillars from.
 */
class Spawn {

    /**
     * Spawns caterpillars randomly on the screen.
     * @param image             the image to draw to
     * @param caterpillarList   the list of caterpillars for storage
     */
    static void random (Image image, ArrayList<Caterpillar> caterpillarList) {
        Random r = new Random();

        for (int i = 0; i < Properties.NUMBER_OF_CATERPILLARS; i++) {
            caterpillarList.add(new Caterpillar(
                    image,
                    r.nextInt(Properties.WIDTH),
                    r.nextInt(Properties.HEIGHT),
                    r.nextFloat()
            ));
        }
    }

    /**
     * Spawns caterpillars in the bottom left corner.
     * @param image             the image to draw to
     * @param caterpillarList   the list of caterpillars for storage
     */
    static void inCorner (Image image, ArrayList<Caterpillar> caterpillarList) {
        Random r = new Random();
        caterpillarList.add(new Caterpillar(
                image,
                0,
                Properties.HEIGHT-1,
                r.nextFloat()
        ));
    }

    /**
     * Spawns caterpillars in the center of the screen.
     * @param image             the image to draw to
     * @param caterpillarList   the list of caterpillars for storage
     */
    static void inCenter (Image image, ArrayList<Caterpillar> caterpillarList) {
        Random r = new Random();
        caterpillarList.add(new Caterpillar(
                image,
                Properties.WIDTH / 2,
                Properties.HEIGHT / 2,
                r.nextFloat()
        ));
    }

    /**
     * Spawns caterpillars on the top of the screen.
     * @param image             the image to draw to
     * @param caterpillarList   the list of caterpillars for storage
     */
    static void onTop (Image image, ArrayList<Caterpillar> caterpillarList) {
        float r = new Random().nextFloat();
        for (int i = 0; i < Properties.WIDTH; i++) {
            caterpillarList.add(new Caterpillar(
                    image,
                    i,
                    0,
                    r
            ));
            if (i%20==0) i++;
        }
    }

    /**
     * Spawns caterpillars on the bottom of the screen.
     * @param image             the image to draw to
     * @param caterpillarList   the list of caterpillars for storage
     */
    static void onBottom (Image image, ArrayList<Caterpillar> caterpillarList) {
        float r = new Random().nextFloat();
        for (int i = 0; i < Properties.WIDTH; i++) {
            caterpillarList.add(new Caterpillar(
                    image,
                    i,
                    Properties.HEIGHT-1,
                    r
            ));
            if (i%20==0) i++;
        }
    }

    /**
     * Spawns caterpillars on the left side of the screen.
     * @param image             the image to draw to
     * @param caterpillarList   the list of caterpillars for storage
     */
    static void onLeft (Image image, ArrayList<Caterpillar> caterpillarList) {
        float r = new Random().nextFloat();
        for (int i = 0; i < Properties.HEIGHT; i++) {
            caterpillarList.add(new Caterpillar(
                    image,
                    0,
                    i,
                    r
            ));
            if (i%20==0) i++;
        }
    }

    /**
     * Spawns caterpillars on the right side of the screen.
     * @param image             the image to draw to
     * @param caterpillarList   the list of caterpillars for storage
     */
    static void onRight (Image image, ArrayList<Caterpillar> caterpillarList) {
        float r = new Random().nextFloat();
        for (int i = 0; i < Properties.HEIGHT; i++) {
            caterpillarList.add(new Caterpillar(
                    image,
                    Properties.WIDTH-1,
                    i,
                    r
            ));
            if (i%20==0) i++;
        }
    }


}
