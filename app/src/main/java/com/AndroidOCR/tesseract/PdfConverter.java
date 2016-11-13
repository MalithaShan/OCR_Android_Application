package com.AndroidOCR.tesseract;

import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.CMYKColor;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.AndroidOCR.tesseract.MainActivity.context;

/**
 * Created by user on 10/23/2016.
 */

public   class PdfConverter {
    public static void createPdf(String s) {

        com.itextpdf.text.Document document = new com.itextpdf.text.Document();

        try {
            String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/OCR App";

            File dir = new File(path);
            if(!dir.exists())
                dir.mkdirs();

            Log.d("PDFCreator", "PDF Path: " + path);
            String  fileName=fileNameGenerate();
            File file = new File(dir, fileName);
            FileOutputStream fOut = new FileOutputStream(file);
            PdfWriter.getInstance(document, fOut);

            //open the document
            document.open();

            Paragraph p2 = new Paragraph(s);
            Font paraFont2= new Font(Font.FontFamily.COURIER,14.0f,0, CMYKColor.GREEN);
            p2.setAlignment(Paragraph.ALIGN_CENTER);
            p2.setFont(paraFont2);

            document.add(p2);
            Toast.makeText(context,"OCR save as" + fileName, Toast.LENGTH_SHORT).show();

        } catch (DocumentException de) {
            Log.e("PDFCreator", "DocumentException:" + de);
        } catch (IOException e) {
            Log.e("PDFCreator", "ioException:" + e);
        }
        finally
        {
            document.close();
        }

    }
    public static String fileNameGenerate(){
        String fileName="";
        Date date = new Date();
        String strDateFormat = "hh:mm:ss a";
        DateFormat dateFormat = new SimpleDateFormat(strDateFormat);
        String formattedDate= dateFormat.format(date).replace(":","_");
        fileName="OCRpdf_"+formattedDate+".pdf";

        return fileName;
    }
}
