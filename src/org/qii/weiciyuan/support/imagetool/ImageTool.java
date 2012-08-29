package org.qii.weiciyuan.support.imagetool;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import org.qii.weiciyuan.support.file.FileLocationMethod;
import org.qii.weiciyuan.support.file.FileManager;
import org.qii.weiciyuan.support.http.HttpUtility;

/**
 * User: Jiang Qi
 * Date: 12-8-3
 * Time: 上午9:25
 */
public class ImageTool {


    private static Bitmap decodeBitmapFromSDCard(String path,
                                                 int reqWidth, int reqHeight) {


        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);

        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeFile(path, options);

    }


    public static Bitmap getPictureThumbnailBitmap(String url) {


        String absoluteFilePath = FileManager.getFileAbsolutePathFromUrl(url, FileLocationMethod.picture_thumbnail);

        Bitmap bitmap = BitmapFactory.decodeFile(absoluteFilePath);

        if (bitmap != null) {
            return ImageEdit.getRoundedCornerBitmap(bitmap);
        } else {
            String path = getBitmapFromNetWork(url, absoluteFilePath);
            bitmap = BitmapFactory.decodeFile(path);
            if (bitmap != null)
                return ImageEdit.getRoundedCornerBitmap(bitmap);
        }
        return null;

    }

    public static Bitmap getAvatarBitmap(String url) {


        String absoluteFilePath = FileManager.getFileAbsolutePathFromUrl(url, FileLocationMethod.avatar);

        absoluteFilePath = absoluteFilePath + ".jpg";

        Bitmap bitmap = BitmapFactory.decodeFile(absoluteFilePath);

        if (bitmap == null) {
            String path = getBitmapFromNetWork(url, absoluteFilePath);
            bitmap = BitmapFactory.decodeFile(path);
        }
        if (bitmap != null) {
            bitmap = ImageEdit.getRoundedCornerBitmap(bitmap);
        }
        return bitmap;
    }

    public static Bitmap getNormalBitmap(String url) {


        String absoluteFilePath = FileManager.getFileAbsolutePathFromUrl(url, FileLocationMethod.avatar);

        absoluteFilePath = absoluteFilePath + ".jpg";

        Bitmap bitmap = decodeBitmapFromSDCard(absoluteFilePath, 480, 1600);

        if (bitmap == null) {
            String path = getBitmapFromNetWork(url, absoluteFilePath);
            bitmap = decodeBitmapFromSDCard(path, 480, 1600);
        }
        if (bitmap != null) {
            bitmap = ImageEdit.getRoundedCornerBitmap(bitmap);
        }
        return bitmap;
    }

    private static String getBitmapFromNetWork(String url, String path) {

        HttpUtility.getInstance().executeDownloadTask(url, path);
        return path;

    }

    private static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {

        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            if (height > reqHeight) {
                inSampleSize = Math.round((float) height / (float) reqHeight);
            } else {
                inSampleSize = Math.round((float) width / (float) reqWidth);
            }

        }
        return inSampleSize;
    }
}


