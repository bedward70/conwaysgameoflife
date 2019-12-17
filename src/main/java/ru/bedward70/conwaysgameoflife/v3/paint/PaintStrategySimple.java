/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2019 Eduard Balovnev (bedward70)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package ru.bedward70.conwaysgameoflife.v3.paint;

import ru.bedward70.conwaysgameoflife.v3.model.ModelColor;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.isNull;

public class PaintStrategySimple implements PaintStrategy {

    public static final Color backgroundColor = new Color(0x505050);

    private final int imageCellSize = 8;
    private final BufferedImage paintImage = new BufferedImage(imageCellSize, imageCellSize, BufferedImage.TYPE_INT_ARGB);

    private final BufferedImage foodImg;
    private final BufferedImage mineralImg;
    private final BufferedImage ping;
    private final Map<ModelColor, BufferedImage> map = new HashMap<>();

    public PaintStrategySimple() throws IOException {
        this.foodImg = ImageIO.read(new File("images/food_8_4.png"));
        this.mineralImg = ImageIO.read(new File("images/mineral_8_4.png"));
        this.ping = ImageIO.read(new File("images/rabbit_8.png"));

        map.put(ModelColor.WHITE, ImageIO.read(new File("images/rabbit_white_8.png")));
        map.put(ModelColor.BLACK, ImageIO.read(new File("images/rabbit_black_8.png")));
        map.put(ModelColor.PINK, ImageIO.read(new File("images/rabbit_red_8.png")));
        map.put(ModelColor.GREEN, ImageIO.read(new File("images/rabbit_green_8.png")));

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
                big.drawImage(map.get(model.getModel().getColor()), 0, 0, null);
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
