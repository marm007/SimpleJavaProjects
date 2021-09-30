import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.Random;

import javafx.concurrent.Task;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.canvas.GraphicsContext;

public class DrawerTask extends Task {

    private static Random random = new Random();

    public static double MAX_X = 8;
    public static double MIN_X = -8;

    public static double MAX_Y = 8;
    public static double MIN_Y = -8;

    private static int WIDTH = 800;
    private static int HEIGHT = 540;

    private GraphicsContext gc;
    private int max;
    private int i = 0;
    private int inArea = 0;
    private final BufferedImage bi;

    public DrawerTask(int max, GraphicsContext gc) {
        bi = new BufferedImage(800, 540, BufferedImage.TYPE_INT_RGB);

        this.max = max;
        this.gc = gc;
    }

    public DrawerTask(int max, GraphicsContext gc, int i, int inArea, BufferedImage bi) {
        this.bi = bi;

        this.max = max;
        this.gc = gc;
        this.i = i;
        this.inArea = inArea;
    }

    @Override
    protected Object call() throws Exception {
        while (true) {
            double x = random.nextDouble() * (MAX_X - MIN_X) + MIN_X;
            double y = random.nextDouble() * (MAX_Y - MIN_Y) + MIN_Y;

            if (Equation.calc(x, y)) {
                draw(x, y);
                inArea++;
            }

            i++;

            updateProgress(i, max);

            if (i >= max)
                break;

            if (isCancelled())
                break;
        }
        return null;
    }

    private void draw(double x, double y) {
        int _x = WIDTH - Math.abs((int) ((0 - WIDTH) * (x - MIN_X) / (MAX_X - MIN_X) + 0));
        int _y = HEIGHT - Math.abs((int) ((0 - HEIGHT) * (y - MIN_Y) / (MAX_Y - MIN_Y) + 0));

        bi.setRGB(_x, _y, Color.YELLOW.getRGB());

        if (i % 1000 == 0)
            gc.drawImage(SwingFXUtils.toFXImage(bi, null), 0, 0);
    }

    public GraphicsContext getGc() {
        return gc;
    }

    public int getI() {
        return i;
    }

    public int getInArea() {
        return inArea;
    }

    public BufferedImage getBi() {
        return bi;
    }

    public int getMax() {
        return max;
    }
}
