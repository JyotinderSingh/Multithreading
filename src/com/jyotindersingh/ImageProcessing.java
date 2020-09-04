package com.jyotindersingh;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.List;

public class ImageProcessing {
    public static final String SOURCE_FILE = "./resources/many-flowers.jpg";
    public static final String DESTINATION_FILE = "./out/many-flowers.jpg";

    public static void main(String[] args) throws IOException {
        BufferedImage originalImage = ImageIO.read(new File(SOURCE_FILE));
        BufferedImage resultImage = new BufferedImage(originalImage.getWidth(), originalImage.getHeight(), BufferedImage.TYPE_INT_RGB);

        long startTime = System.currentTimeMillis();
//        recolorSingleThreaded(originalImage, resultImage);
        int numberOfThreads = 4;
        recolorMultithreaded(originalImage, resultImage, numberOfThreads);
        long endTime = System.currentTimeMillis();

        long duration = endTime - startTime;


        File outputFile = new File(DESTINATION_FILE);
        ImageIO.write(resultImage, "jpg", outputFile);

        System.out.println(String.valueOf(duration));

    }

    public static void recolorMultithreaded(BufferedImage originalImage, BufferedImage resultImage, int numberOfThreads) {
        List<Thread> threads = new ArrayList<>();
        int width = originalImage.getWidth();
        int height = originalImage.getHeight() / numberOfThreads;

        for (int i = 0; i < numberOfThreads; ++i) {
            final int threadMultiplier = i;

            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    int leftCorner = 0;
                    int topCorner = height * threadMultiplier;

                    recolorImage(originalImage, resultImage, leftCorner, topCorner, width, height);
                }
            });
            threads.add(thread);
        }
        for (Thread thread : threads) {
            thread.start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void recolorSingleThreaded(BufferedImage originalImage, BufferedImage resultImage) {
        recolorImage(originalImage, resultImage, 0, 0, originalImage.getWidth(), originalImage.getHeight());
    }

    public static void recolorImage(BufferedImage originalImage, BufferedImage resultImage, int leftCorner, int topCorner, int width, int height) {
        for (int x = leftCorner; x < leftCorner + width && x < originalImage.getWidth(); ++x) {
            for (int y = topCorner; y < topCorner + height && y < originalImage.getHeight(); ++y) {
                recolorPixel(originalImage, resultImage, x, y);
            }
        }
    }

    public static void recolorPixel(BufferedImage originalImage, BufferedImage resultImage, int x, int y) {
        int rgb = originalImage.getRGB(x, y);
        int red = getRed(rgb);
        int green = getGreen(rgb);
        int blue = getBlue(rgb);

        int newRed;
        int newGreen;
        int newBlue;

        if (isShadeOfGray(red, green, blue)) {
            newRed = Math.min(red + 10, 255);
            newGreen = Math.max(green - 80, 0);
            newBlue = Math.max(blue - 20, 0);
        } else {
            newRed = red;
            newGreen = green;
            newBlue = blue;
        }
        int newRGB = createRGBFromColors(newRed, newGreen, newBlue);
        setRGB(resultImage, x, y, newRGB);
    }

    public static void setRGB(BufferedImage image, int x, int y, int rgb) {
        image.getRaster().setDataElements(x, y, image.getColorModel().getDataElements(rgb, null));
    }

    public static boolean isShadeOfGray(int red, int blue, int green) {
        // just check if all the channels are roughly of the same intensity
        return Math.abs(red - green) < 30 && Math.abs(red - blue) < 30 && Math.abs(green - blue) < 30;
    }

    public static int createRGBFromColors(int red, int green, int blue) {
        int rgb = 0;
        rgb |= blue;
        rgb |= green << 8;
        rgb |= red << 16;
        rgb |= 0xFF000000;  // set alpha to FF for opaque pixel

        return rgb;
    }

    public static int getRed(int rgb) {
        // Takes an RGB value and extracts only the red value out of the pixel
        //ARGB, each component is 1 byte, Green is the 3nd byte from the right - so we
        // shift the value 2 bytes to the right
        return (rgb & 0x00FF0000) >> 16;
    }

    public static int getGreen(int rgb) {
        // Takes an RGB value and extracts only the green value out of the pixel
        //ARGB, each component is 1 byte, Green is the 2nd byte from the right - so we
        // shift the value 1 byte to the right
        return (rgb & 0x0000FF00) >> 8;
    }

    public static int getBlue(int rgb) {
        // Takes an RGB value and extracts only the blue value out of the pixel
        // ARGB, each component is 1 byte (8 bits)
        return rgb & 0x000000FF;
    }

}
