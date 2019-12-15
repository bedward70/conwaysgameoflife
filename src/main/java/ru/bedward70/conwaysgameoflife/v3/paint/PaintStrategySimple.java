package ru.bedward70.conwaysgameoflife.v3.paint;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static java.util.Objects.isNull;

public class PaintStrategySimple implements PaintStrategy {

    public static final Color backgroundColor = new Color(0x505050);

    private final int imageCellSize = 8;
    private final BufferedImage paintImage = new BufferedImage(imageCellSize, imageCellSize, BufferedImage.TYPE_INT_ARGB);

    private final BufferedImage foodImg;
    private final BufferedImage mineralImg;
    private final BufferedImage ping;

    public PaintStrategySimple() throws IOException {
        this.foodImg = ImageIO.read(new File("images/food_8_4.png"));
        this.mineralImg = ImageIO.read(new File("images/mineral_8_4.png"));
        this.ping = ImageIO.read(new File("images/rabbit_8.png"));
    }

    @Override
    public void paint(Graphics g, int x, int y, int w, int h, PaintModel model) {

        // Mask
        // https://stackoverflow.com/questions/221830/set-bufferedimage-alpha-mask-in-java
        final Graphics big = paintImage.getGraphics();
        big.setColor(backgroundColor);
        big.fillRect(0, 0, imageCellSize, imageCellSize);

        if (!isNull(model)) {
            paintByModel(big, model, this.foodImg, (0x00ff & model.getFood()), 256);
            paintByModel(big, model, this.mineralImg, (0x00ff & model.getMineral()), 256);
            if (!isNull(model.getModel())) {
                big.drawImage(this.ping, 0, 0, null);
            }
        }
        big.dispose();

        g.drawImage(
            paintImage,
            x, y, x + w, y + h,
            0, 0, imageCellSize, imageCellSize,
            null
        );
    }

    private void paintByModel(
        final Graphics g,
        final PaintModel model,
        final BufferedImage unitImg,
        final int value,
        final int maxValue
    ) {
        final int imageCount = unitImg.getWidth() / unitImg.getHeight();
        int currentImage = value * imageCount / maxValue;
        g.drawImage(
            unitImg,
            0, 0, imageCellSize, imageCellSize,
            imageCellSize * currentImage, 0, imageCellSize * currentImage + imageCellSize, imageCellSize,
            null
        );
    }
}
