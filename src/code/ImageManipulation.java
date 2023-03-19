package code;

import image.APImage;
import image.Pixel;

import java.util.ArrayList;

public class ImageManipulation {

    /** CHALLENGE 0: Display Image
     *  Write a statement that will display the image in a window
     */
    public static void main(String[] args) {
        APImage ap = new APImage("cyberpunk2077.jpg"); ap.draw(); // Challenge 0
        grayScale("cyberpunk2077.jpg"); // Challenge 1
        blackAndWhite("cyberpunk2077.jpg"); // Challenge 2
        edgeDetection("cyberpunk2077.jpg", 20); // Challenge 3
        reflectImage("cyberpunk2077.jpg"); // Challenge 4
        rotateImage("cyberpunk2077.jpg"); // Challenge 5
    }

    /** CHALLENGE ONE: Grayscale
     *
     * INPUT: the complete path file name of the image
     * OUTPUT: a grayscale copy of the image
     *
     * To convert a colour image to grayscale, we need to visit every pixel in the image ...
     * Calculate the average of the red, green, and blue components of the pixel.
     * Set the red, green, and blue components to this average value. */
    public static void grayScale(String pathOfFile) {
        APImage ap = new APImage(pathOfFile);
        for (int i = 0; i < ap.getWidth(); ++i) {
            for (int j = 0; j < ap.getHeight(); ++j) {
                Pixel x = ap.getPixel(i, j);
                int average = getAverageColour(x);
                x.setBlue(average);
                x.setRed(average);
                x.setGreen(average);
                ap.setPixel(i, j, x);
            }
        }
        ap.draw();
    }

    /** A helper method that can be used to assist you in each challenge.
     * This method simply calculates the average of the RGB values of a single pixel.
     * @param pixel
     * @return the average RGB value
     */
    private static int getAverageColour(Pixel pixel) {
        int red = pixel.getRed(), blue = pixel.getBlue(), green = pixel.getGreen();
        return (red + blue + green) / 3;
    }

    /** CHALLENGE TWO: Black and White
     *
     * INPUT: the complete path file name of the image
     * OUTPUT: a black and white copy of the image
     *
     * To convert a colour image to black and white, we need to visit every pixel in the image ...
     * Calculate the average of the red, green, and blue components of the pixel.
     * If the average is less than 128, set the pixel to black
     * If the average is equal to or greater than 128, set the pixel to white */
    public static void blackAndWhite(String pathOfFile) {
        APImage ap = new APImage(pathOfFile);
        for (int i = 0; i < ap.getWidth(); ++i) {
            for (int j = 0; j < ap.getHeight(); ++j) {
                Pixel x = ap.getPixel(i, j);
                int average = getAverageColour(x);
                if (average < 128) {
                    x.setGreen(0);
                    x.setBlue(0);
                    x.setRed(0);
                } else {
                    x.setGreen(255);
                    x.setBlue(255);
                    x.setRed(255);
                }
                ap.setPixel(i, j, x);
            }
        }
        ap.draw();
    }

    /** CHALLENGE Three: Edge Detection
     *
     * INPUT: the complete path file name of the image
     * OUTPUT: an outline of the image. The amount of information will correspond to the threshold.
     *
     * Edge detection is an image processing technique for finding the boundaries of objects within images.
     * It works by detecting discontinuities in brightness. Edge detection is used for image segmentation
     * and data extraction in areas such as image processing, computer vision, and machine vision.
     *
     * There are many different edge detection algorithms. We will use a basic edge detection technique
     * For each pixel, we will calculate ...
     * 1. The average colour value of the current pixel
     * 2. The average colour value of the pixel to the left of the current pixel
     * 3. The average colour value of the pixel below the current pixel
     * If the difference between 1. and 2. OR if the difference between 1. and 3. is greater than some threshold value,
     * we will set the current pixel to black. This is because an absolute difference that is greater than our threshold
     * value should indicate an edge and thus, we colour the pixel black.
     * Otherwise, we will set the current pixel to white
     * NOTE: We want to be able to apply edge detection using various thresholds
     * For example, we could apply edge detection to an image using a threshold of 20 OR we could apply
     * edge detection to an image using a threshold of 35
     *  */
    public static void edgeDetection(String pathToFile, int threshold) {
        APImage ap = new APImage(pathToFile);
        for (int i = ap.getWidth()-1; i >= 0; i--) {
            for (int j = ap.getHeight()-1; j >= 0; j--) {
                if ((i==0||j==0)) {
                    ap.setPixel(i, j, new Pixel(255, 255, 255));
                    continue;
                }
                Pixel curr = ap.getPixel(i, j);
                Pixel left = ap.getPixel(i-1, j);
                Pixel below = ap.getPixel(i, j-1);
                int currAvg = getAverageColour(curr);
                int leftAvg = getAverageColour(left);
                int belowAvg = getAverageColour(below);
                if (Math.abs(currAvg-leftAvg) > threshold || Math.abs(currAvg-belowAvg) > threshold) {
                    curr.setGreen(0);
                    curr.setBlue(0);
                    curr.setRed(0);
                } else {
                    curr.setGreen(255);
                    curr.setBlue(255);
                    curr.setRed(255);
                }
            }
        }
        ap.draw();
    }

    /** CHALLENGE Four: Reflect Image
     *
     * INPUT: the complete path file name of the image
     * OUTPUT: the image reflected about the y-axis
     *
     */
    public static void reflectImage(String pathToFile) {
        APImage ap = new APImage(pathToFile);
        ArrayList<Pixel> al = new ArrayList<>();
        for (int i = 0; i < ap.getWidth(); ++i) {
            for (int j = 0; j < ap.getHeight(); ++j) {
                al.add(ap.getPixel(i, j));
            }
        }
        int als = 0;
        for (int i = ap.getWidth()-1; i >= 0; i--) {
            for (int j = 0; j < ap.getHeight(); j++) {
                ap.setPixel(i, j, al.get(als));
                als++;
            }
        }
        ap.draw();
    }

    /** CHALLENGE Five: Rotate Image
     *
     * INPUT: the complete path file name of the image
     * OUTPUT: the image rotated 90 degrees CLOCKWISE
     *
     *  */
    public static void rotateImage(String pathToFile) {
        APImage ap = new APImage(pathToFile);
        APImage apNew = new APImage(ap.getHeight(), ap.getWidth());
        for (int i = 0; i < ap.getWidth(); ++i) {
            for (int j = 0; j < ap.getHeight(); ++j) {
                Pixel pix = ap.getPixel(i, j);
                apNew.setPixel(ap.getHeight()-j-1, i, pix);
            }
        }
        apNew.draw();
    }

}