package com.AndroidOCR.tesseract;

import android.content.res.AssetManager;

import com.googlecode.tesseract.android.TessBaseAPI;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import static com.AndroidOCR.tesseract.MainActivity.context;

/**
 * Created by user on 10/23/2016.
 */

public class TesseractApiImplementation {

    public static String datapath = context.getFilesDir()+ "/tesseract/";

    public static TessBaseAPI InitializeTesseractAPI(){
        TessBaseAPI mTess = new TessBaseAPI();
        String language = "eng";
        checkFile(new File(datapath + "tessdata/"));
        mTess.init(datapath, language);
        return mTess;
    }
    public static void checkFile(File dir) {
        if (!dir.exists()&& dir.mkdirs()){
            copyFiles();
        }
        if(dir.exists()) {
            String datafilepath = datapath+ "/tessdata/eng.traineddata";
            File datafile = new File(datafilepath);

            if (!datafile.exists()) {
                copyFiles();
            }
        }
    }
    public static void copyFiles() {
        try {
            String filepath = datapath + "/tessdata/eng.traineddata";
            AssetManager assetManager = context.getAssets();

            InputStream instream = assetManager.open("tessdata/eng.traineddata");
            OutputStream outstream = new FileOutputStream(filepath);

            byte[] buffer = new byte[1024];
            int read;
            while ((read = instream.read(buffer)) != -1) {
                outstream.write(buffer, 0, read);
            }
            outstream.flush();
            outstream.close();
            instream.close();

            File file = new File(filepath);
            if (!file.exists()) {
                throw new FileNotFoundException();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
