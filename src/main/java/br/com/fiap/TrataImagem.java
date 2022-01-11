package br.com.fiap;

import org.bytedeco.opencv.global.opencv_imgcodecs;
import org.bytedeco.opencv.global.opencv_imgproc;
import org.bytedeco.opencv.opencv_core.*;
import org.bytedeco.opencv.opencv_imgproc.*;
import org.opencv.core.Core;

import static org.bytedeco.opencv.global.opencv_core.*;
import static org.bytedeco.opencv.global.opencv_imgcodecs.*;
import static org.bytedeco.opencv.global.opencv_imgproc.*;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;


import java.io.File;

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
        opencv_imgproc.cvtColor(inputMat, imgGray, COLOR_BGR2GRAY);

        Mat imgThreshold = new Mat();
        opencv_imgproc.adaptiveThreshold(imgGray, imgThreshold, 255, ADAPTIVE_THRESH_MEAN_C, THRESH_BINARY, 51, 13);

        Mat imgGaussianBlur = new Mat();
        opencv_imgproc.GaussianBlur(imgThreshold, imgGaussianBlur, new Size(1, 1), 0);

        Mat imgDilated = new Mat(imgGaussianBlur.size(), CV_8U);
        int dilation_size = 1;
        Mat kernel = opencv_imgproc.getStructuringElement(CV_SHAPE_CROSS, new Size(dilation_size, dilation_size));
        opencv_imgproc.dilate(imgGaussianBlur, imgDilated, kernel, new Point(-1,-1), dilation_size, dilation_size, null);
        //(imgGaussianBlur, imgDilated, kernel, new Point(-1,-1), 1);
        opencv_imgcodecs.imwrite(SRC_PATH + "imagem_tratada.png", imgDilated);

        try {
            result = tesseract.doOCR(new File(SRC_PATH + "imagem_tratada.png"));
        } catch (TesseractException e) {
            e.printStackTrace();

        }

        return result;

    }

    public static String trataImagem() {
    	Mat origin = imread("C:/Users/felip/git/tratamentoImagem/output.png");
        String result = new TrataImagem().extractString(origin);
        System.out.print(result);
        return result;
    }
}