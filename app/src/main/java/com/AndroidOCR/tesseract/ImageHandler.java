package com.AndroidOCR.tesseract;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.AndroidOCR.tesseract.MainActivity.bitmap;
import static com.AndroidOCR.tesseract.MainActivity.context;
import static com.AndroidOCR.tesseract.MainActivity.image;
import static com.AndroidOCR.tesseract.MainActivity.mCurrentPhotoPath;

/**
 * Created by user on 10/23/2016.
 */

public class ImageHandler   {

    public static Intent pickPhoto() {
            Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            return intent;
    }
    public static void uriOCR(Uri uri) {
        if (uri != null) {
            InputStream is = null;
            try {
                is = context.getContentResolver().openInputStream(uri);
                bitmap = BitmapFactory.decodeStream(is);
                image.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } finally {
                if (is != null) {
                    try {
                        is.close();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        }
    }
    public static void setPic() {
        try {


            // Get the dimensions of the View
            int targetW = image.getWidth();
            int targetH = image.getHeight();

            // Get the dimensions of the bitmap
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            bmOptions.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
            int photoW = bmOptions.outWidth;
            int photoH = bmOptions.outHeight;

            // Determine how much to scale down the image
            int scaleFactor = Math.min(photoW / targetW, photoH / targetH);

            // Decode the image file into a Bitmap sized to fill the View
            bmOptions.inJustDecodeBounds = false;
            bmOptions.inSampleSize = scaleFactor << 1;
            bmOptions.inPurgeable = true;

            bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
            image.setImageBitmap(bitmap);
        }catch (Exception ex){
                   Toast.makeText(context,ex.toString(), Toast.LENGTH_SHORT).show();
        }

    }
    public static Intent dispatchTakePictureIntent() {

            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try{
            // Ensure that there's a camera activity to handle the intent
            if (takePictureIntent.resolveActivity(context.getPackageManager()) != null) {
                // Create the File where the photo should go
                File photoFile = null;
                try {
                    photoFile = createImageFile();
                } catch (IOException ex) {
                    // Error occurred while creating the File

                }
                // Continue only if the File was successfully created
                if (photoFile != null) {
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));

                }
            }


        }catch (Exception ex){
            Toast.makeText(context,ex.toString(), Toast.LENGTH_LONG).show();
        }
        return takePictureIntent;
    }
    public static File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        String storageDir = Environment.getExternalStorageDirectory() + "/TessOCR";
        File dir = new File(storageDir);
        if (!dir.exists())
            dir.mkdir();
        File image = new File(storageDir + "/" + imageFileName + ".jpg");
        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

}
