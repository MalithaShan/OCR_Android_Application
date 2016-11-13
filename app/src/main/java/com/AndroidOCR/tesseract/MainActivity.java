package com.AndroidOCR.tesseract;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.googlecode.tesseract.android.TessBaseAPI;

public class MainActivity extends AppCompatActivity {

    private TessBaseAPI mTess;
    public static Bitmap bitmap;
    String reultText="";
    public static String mCurrentPhotoPath;
    public static Context context;
    public static ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context=getApplicationContext();
        image=(ImageView) findViewById(R.id.imageView);
        mTess=TesseractApiImplementation.InitializeTesseractAPI();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

         if (requestCode == 2 && resultCode == Activity.RESULT_OK) {
            Uri uri = data.getData();
            if (uri != null) {
                ImageHandler.uriOCR(uri);
            }
        }
        else if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
             ImageHandler.setPic();
        }
    }
    public void pickPhotofromGallery(View v) {
        Intent intent=ImageHandler.pickPhoto();
        startActivityForResult(intent, 2);
    }
    public void takePhotofromCam(View view) {
        try {
            Intent takePictureIntent = ImageHandler.dispatchTakePictureIntent();
            startActivityForResult(takePictureIntent, 1);
        }catch (Exception ex){

            Toast.makeText(getApplicationContext(),ex.toString(), Toast.LENGTH_LONG).show();
        }
    }
    public void processImage(View view){
        String OCRresult = null;
        if(bitmap!=null) {
            mTess.setImage(bitmap);
            OCRresult = mTess.getUTF8Text();
            reultText = OCRresult;
            EditText OCRTextView = (EditText) findViewById(R.id.OCRTextView);
            OCRTextView.setText(OCRresult);
            Toast.makeText(getApplicationContext(),"OCR create Sucessfully", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(getApplicationContext(),"Select Image", Toast.LENGTH_SHORT).show();
        }
    }
    public void GeneratePDF(View view) {

        if(reultText !=null && reultText !="" && reultText!="OCR Text will appear here" )
        {
            reultText= ((EditText)findViewById(R.id.OCRTextView)).getText().toString();
            PdfConverter.createPdf(reultText);
        }else {
            Toast.makeText(getApplicationContext(), "Select Image create OCR text", Toast.LENGTH_SHORT).show();
        }

    }

}
