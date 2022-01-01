package br.com.fiap;

import static org.opencv.imgcodecs.Imgcodecs.imread;
import static org.opencv.imgproc.Imgproc.ADAPTIVE_THRESH_MEAN_C;
import static org.opencv.imgproc.Imgproc.COLOR_BGR2GRAY;
import static org.opencv.imgproc.Imgproc.THRESH_BINARY;

import java.io.File;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

public class TrataImagem {
    static String SRC_PATH = "C:/OCR/Imagens/";
    static Tesseract tesseract = new Tesseract();

    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        tesseract.setDatapath("C:/Program Files/Tesseract-OCR/tessdata");
        tesseract.setLanguage("por");
    }

    String extractString(Mat inputMat) {
        String result = "";

        Mat imgGray = new Mat();
        Imgproc.cvtColor(inputMat, imgGray, COLOR_BGR2GRAY);

        Mat imgThreshold = new Mat();
        Imgproc.adaptiveThreshold(imgGray, imgThreshold, 255, ADAPTIVE_THRESH_MEAN_C, THRESH_BINARY, 51, 13);

        Mat imgGaussianBlur = new Mat();
        Imgproc.GaussianBlur(imgThreshold, imgGaussianBlur, new Size(1, 1), 0);

        Mat imgDilated = new Mat(imgGaussianBlur.size(), CvType.CV_8U);
        int dilation_size = 1;
        Mat kernel = Imgproc.getStructuringElement(Imgproc.CV_SHAPE_CROSS, new Size(dilation_size, dilation_size));
        Imgproc.dilate(imgGaussianBlur, imgDilated, kernel, new Point(-1,-1), 1);
        Imgcodecs.imwrite(SRC_PATH + "imagem_tratada.png", imgDilated);

        try {
            result = tesseract.doOCR(new File(SRC_PATH + "imagem_tratada.png"));
        } catch (TesseractException e) {
            e.printStackTrace();

        }

        return result;

    }

    public static void trataImagem() {
        Mat origin = imread("C:\\Users\\felip\\git\\tratamento-imagem\\output.png");
        String result = new TrataImagem().extractString(origin);
        System.out.print(result);
    }
}