package com.finsall.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.finsall.R;

import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public abstract class BaseCameraActivity extends BaseActivity {

    public static final int TAKE_PIC_REQUEST_CODE = 0;
    public static final int CHOOSE_PIC_REQUEST_CODE = 1;
    public static final int MEDIA_TYPE_IMAGE = 2;
    private Uri mMediaUri;
    public ImageView imagePreview;
   private Integer noOfImagesToTake=0;

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 123: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startCapturingImage(imagePreview);
                }
                break;
            }
        }
    }

    void startCapturingImage(ImageView imagePreview) {
        if(imagePreview==null)
            return;
        this.imagePreview = imagePreview;
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA},
                    123);

        } else {

            //show dialog
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Upload or Take a photo");
            builder.setPositiveButton("Upload", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //upload image
                    Intent choosePictureIntent = new Intent(Intent.ACTION_GET_CONTENT);
                    choosePictureIntent.setType("image/*");
                    startActivityForResult(choosePictureIntent, CHOOSE_PIC_REQUEST_CODE);
                    noOfImagesToTake+=1;
                }
            });
            builder.setNegativeButton("Take Photo", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //take photo
                    Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    mMediaUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
                    if (mMediaUri == null) {
                        //display error
                        Toast.makeText(getApplicationContext(), "Sorry there was an error! Try again.", Toast.LENGTH_LONG).show();

                    } else {
                        takePicture.putExtra(MediaStore.EXTRA_OUTPUT, mMediaUri);
                        startActivityForResult(takePicture, TAKE_PIC_REQUEST_CODE);

                        //mSaveChangesBtn.setEnabled(true);
                    }
                    noOfImagesToTake+=1;
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }

    //inner helper method
    private Uri getOutputMediaFileUri(int mediaTypeImage) {
        Log.d("image_", "getOutputMediaFileUri");
        if (isExternalStorageAvailable()) {
            //get the URI
            //get external storage dir
            File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "UPLOADIMAGES");
            //create subdirectore if it does not exist
            if (!mediaStorageDir.exists()) {
                //create dir
                if (!mediaStorageDir.mkdirs()) {

                    return null;
                }
            }
            //create a file name
            //create file
            File mediaFile = null;
            Date now = new Date();
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(now);

            String path = mediaStorageDir.getPath() + File.separator;
            if (mediaTypeImage == MEDIA_TYPE_IMAGE) {
                mediaFile = new File(path + "IMG_" + timestamp + ".jpg");
            }
            //return file uri
            Log.d("UPLOADIMAGE", "FILE: " + Uri.fromFile(mediaFile));

            return Uri.fromFile(mediaFile);
        } else {
            Log.d("image_", "External device not redabl");

            return null;
        }

    }

    //check if external storage is mounted. helper method
    private boolean isExternalStorageAvailable() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("image_", "onActivityResul called");

        if (resultCode == RESULT_OK) {
            if (requestCode == CHOOSE_PIC_REQUEST_CODE) {
                Log.d("image_", "Choose Picture");

                if (data == null) {
                    Toast.makeText(getApplicationContext(), "Image cannot be null!", Toast.LENGTH_LONG).show();
                } else {
                    Log.d("image_", "setting image in imageview");

                    mMediaUri = data.getData();
                    //set previews
                    imagePreview.setImageURI(mMediaUri);

                    //Bundle extras = data.getExtras();

                    //Log.e("URI", mMediaUri.toString());

                    //Bitmap bmp = (Bitmap) extras.get("data");


                }
            } else {
                Log.d("image_", "setting image in imageview from camera or other");
                Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                mediaScanIntent.setData(mMediaUri);
                sendBroadcast(mediaScanIntent);
                //set previews

                imagePreview.setImageURI(mMediaUri);

            }

        } else if (resultCode != RESULT_CANCELED) {
            Toast.makeText(getApplicationContext(), "Cancelled!", Toast.LENGTH_LONG).show();
        }
    }

    public Integer getNoOfImagesToTake() {
        return noOfImagesToTake;
    }
}
