/**
 * Contains all the details and parameters of the animation.
 */
class Properties {

    static int       WIDTH                   = 200;
    static int       HEIGHT                  = WIDTH / 16 * 9;
    static int       SCALE                   = 4;

    static double    NUMBER_OF_CATERPILLARS  = 1;
    static boolean   FILL                    = false;

    /**
     * If multiplication is set to true then caterpillars have a chance
     * of spawning another caterpillar as it moves across the screen.
     */
    static boolean   MULTIPLY                = true;
    static int       MULTIPLICATION_ODDS     = 10;

    // Where to spawn the caterpillars.
    static boolean   SPAWN_AT_BOTTOM         = false;
    static boolean   SPAWN_AT_TOP            = false;
    static boolean   SPAWN_AT_LEFT           = false;
    static boolean   SPAWN_AT_RIGHT          = false;
    static boolean   SPAWN_AT_CENTER         = false;
    static boolean   SPAWN_RANDOMLY          = false;
    static boolean   SPAWN_AT_CORNER         = true;

    // The rate that the color will change.
    static float     HUE_INCREMENT           = 1.0f / 360.0f;

    /**
     * Spawns the caterpillars in the centre and expands outwards in all directions.
     */
    static void explosion () {
        dimensions(200, 200/16*9, 4);
        caterpillars(1, true, 10, false);
        spawnLocationTRBL(false, false, false, false);
        spawnLocationCRC(true, false, false);
        HUE_INCREMENT = 2.0f / 360.0f;
    }

    /**
     * Spawns caterpillars in a line on the top and bottom and they expands towards the middle.
     */
    static void crunch () {
        dimensions(200, 200/16*9, 4);
        caterpillars(1, true, 100, false);
        spawnLocationTRBL(false, false, true, true);
        spawnLocationCRC(false, false , false);
    }

    /**
     * Spawns caterpillars random on the screen. The size of each unit of the caterpillar is
     * a pixel.
     */
    public static void smallFireworks () {
        dimensions(500, 500/16*9, 1);
        caterpillars(20, true, 10, true);
        spawnLocationTRBL(false, false, false, false);
        spawnLocationCRC(false, true, false);
    }

    /**
     * Spawns caterpillars random on the screen. The size of each unit of the caterpillar is
     * a 4 pixels.
     */
    static void largeFireworks () {
        dimensions(200, 200/16*9, 4);
        caterpillars(20, true, 10, false);
        spawnLocationTRBL(false, false, false, false);
        spawnLocationCRC(false, true, false);
    }

    /**
     * Sets the dimensions of the window and the scale at which the caterpillars are drawn.
     * @param width     the width of the window
     * @param height    the height of the window
     * @param scale     the scale
     */
    private static void dimensions (int width, int height, int scale) {
        Properties.WIDTH    = width;
        Properties.HEIGHT   = height;
        Properties.SCALE    = scale;
    }

    /**
     * Sets the properties of the caterpillars.
     * @param quantity              the number of caterpillars to spawn
     * @param multiply              if set to true, spawns more caterpillars over time
     * @param multiplicationOdds    the odds of spawning another caterpillar
     * @param fill                  determines the artistic style
     */
    private static void caterpillars (int quantity, boolean multiply, int multiplicationOdds, boolean fill) {
        Properties.NUMBER_OF_CATERPILLARS  = quantity;
        Properties.MULTIPLY                = multiply;
        Properties.MULTIPLICATION_ODDS     = multiplicationOdds;
        Properties.FILL                    = fill;
    }

    /**
     * Helper method for how the caterpillars will spawn.
     * @param left      if set to true, spawn caterpillars on the left
     * @param right     if set to true, spawn caterpillars on the right
     * @param top       if set to true, spawn caterpillars on the top
     * @param bottom    if set to true, spawn caterpillars on the bottom
     */
    private static void spawnLocationTRBL (boolean left, boolean right, boolean top, boolean bottom) {
        Properties.SPAWN_AT_LEFT    = left;
        Properties.SPAWN_AT_RIGHT   = right;
        Properties.SPAWN_AT_TOP     = top;
        Properties.SPAWN_AT_BOTTOM  = bottom;
    }

    /**
     * Helper method for how the caterpillars will spawn.
     * @param center    if set to true, spawn caterpillars in the center
     * @param randomly  if set to true, spawn caterpillars randomly
     * @param corner    if set to true, spawn caterpillars in the bottom left corner
     */
    private static void spawnLocationCRC (boolean center, boolean randomly, boolean corner) {
        Properties.SPAWN_AT_CENTER  = center;
        Properties.SPAWN_RANDOMLY   = randomly;
        Properties.SPAWN_AT_CORNER  = corner;
    }


}
