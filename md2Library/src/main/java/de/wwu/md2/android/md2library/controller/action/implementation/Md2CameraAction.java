package de.wwu.md2.android.md2library.controller.action.implementation;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.support.media.ExifInterface; // android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import de.wwu.md2.android.md2library.controller.action.implementation.customCode.Md2AttributeSetTask;
import de.wwu.md2.android.md2library.controller.action.implementation.customCode.Md2TaskQueue;
import de.wwu.md2.android.md2library.exception.Md2WidgetNotCreatedException;
import de.wwu.md2.android.md2library.model.type.implementation.Md2File;
import de.wwu.md2.android.md2library.view.management.implementation.Md2ViewManager;

/**
 * Action that triggers taking a photo.
 * Represents CameraAction element in MD2-DSL.
 * <p/>
 * Created on 03/07/2019
 *
 * @author Christoph Rieger
 * @version 1.0
 * @since 1.0
 */
public class Md2CameraAction extends AbstractMd2Action {

    public static Md2AttributeSetTask activeCallback;

    /**
     * URI of the new image
     */
    protected static Uri photoURI;

    /**
     * The callback action.
     */
    protected Md2AttributeSetTask callback;

    public static final int REQUEST_TAKE_PHOTO = 100;

    /**
     * Instantiates a new Md2 content provider operation action.
     *
     * @param task        the action
     */
    public Md2CameraAction(Md2AttributeSetTask task) {
        super("Md2ContentProviderOperationAction " + task.toString());
        this.callback = task;
    }

    @Override
    public void execute() {
        activeCallback = callback;
        dispatchTakePictureIntent();
    }

    public static void callback(ImageView imageView){
        if(activeCallback != null) {
            // Set image
            Activity activity = Md2ViewManager.getInstance().getActiveView();

            activity.getContentResolver().notifyChange(photoURI, null);

            ContentResolver cr = activity.getContentResolver();
            Bitmap bitmap;
            try {
                bitmap = android.provider.MediaStore.Images.Media
                        .getBitmap(cr, photoURI);

                // Try to identify camera rotation
                int orientation = 0;
                try {
                    InputStream input = activity.getApplicationContext().getContentResolver()
                            .openInputStream(photoURI);

                    ExifInterface exif = new ExifInterface(input);
                    orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                            ExifInterface.ORIENTATION_UNDEFINED);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                // Potential memory improvement
                //imageView.setImageBitmap(resize(bitmap, imageView.getMaxWidth(), imageView.getMaxHeight()));
                imageView.setImageBitmap(rotateBitmap(bitmap, orientation));

                // Set value
                if(activeCallback != null){
                    activeCallback.setValue(new Md2File(bitmap));
                    activeCallback.execute();
                    // Recycle if using resized bitmap above
                    // bitmap.recycle();
                }
            } catch (Md2WidgetNotCreatedException e){
                Md2TaskQueue.getInstance().addPendingTask(activeCallback);
            } catch (Exception e) {
                Log.e("MD2lib", e.toString());
            }
        }
    }

    private void dispatchTakePictureIntent() {
        Activity activity = Md2ViewManager.getInstance().getActiveView();
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(activity.getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                photoURI = FileProvider.getUriForFile(activity,
                        "de.wwu.md2.android.md2library.model.contentProvider.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                activity.startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    private File createImageFile() throws IOException {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            // Create an image file name
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String imageFileName = "JPEG_" + timeStamp + "_";
            File storageDir = Md2ViewManager.getInstance().getActiveView().getApplicationContext().getExternalFilesDir(
                    Environment.DIRECTORY_PICTURES);
            File image = File.createTempFile(
                    imageFileName,  /* prefix */
                    ".jpg",         /* suffix */
                    storageDir      /* directory */
            );

            // Save a file: path for use with ACTION_VIEW intents
            //currentPhotoPath = image.getAbsolutePath();
            return image;
        } else {
            Log.e("MD2", "External storage not available");
        }
        return null;
    }

    private static Bitmap resize(Bitmap image, int maxWidth, int maxHeight) {
        if (maxHeight > 0 && maxWidth > 0) {
            int width = image.getWidth();
            int height = image.getHeight();
            float ratioBitmap = (float) width / (float) height;
            float ratioMax = (float) maxWidth / (float) maxHeight;

            int finalWidth = maxWidth;
            int finalHeight = maxHeight;
            if (ratioMax > ratioBitmap) {
                finalWidth = (int) ((float)maxHeight * ratioBitmap);
            } else {
                finalHeight = (int) ((float)maxWidth / ratioBitmap);
            }
            image = Bitmap.createScaledBitmap(image, finalWidth, finalHeight, true);
            return image;
        } else {
            return image;
        }
    }

    public static Bitmap rotateBitmap(Bitmap bitmap, int orientation) {
        Matrix matrix = new Matrix();
        switch (orientation) {
            case ExifInterface.ORIENTATION_NORMAL:
                return bitmap;
            case ExifInterface.ORIENTATION_FLIP_HORIZONTAL:
                matrix.setScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                matrix.setRotate(180);
                break;
            case ExifInterface.ORIENTATION_FLIP_VERTICAL:
                matrix.setRotate(180);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_TRANSPOSE:
                matrix.setRotate(90);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_90:
                matrix.setRotate(90);
                break;
            case ExifInterface.ORIENTATION_TRANSVERSE:
                matrix.setRotate(-90);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                matrix.setRotate(-90);
                break;
            default:
                return bitmap;
        }
        try {
            Bitmap bmRotated = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            bitmap.recycle();
            return bmRotated;
        }
        catch (OutOfMemoryError e) {
            e.printStackTrace();
            return null;
        }
    }
}
