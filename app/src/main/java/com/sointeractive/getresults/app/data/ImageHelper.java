package com.sointeractive.getresults.app.data;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.media.ExifInterface;

import java.io.IOException;

public class ImageHelper {
    public static Bitmap getAvatar(Bitmap imageBitmap, final String picturePath) {
        final int orientation;
        if (picturePath != null) {
            try {
                final ExifInterface exif = new ExifInterface((picturePath));
                orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);
                imageBitmap = rotateBitmap(imageBitmap, orientation);
            } catch (final IOException e) {
                e.printStackTrace();
            }
        }
        final int biggerDimension;
        final int squareSize;
        final int x1;
        final int x2;
        final int y1;
        final int y2;
        final Bitmap squareBitmap;
        if (imageBitmap.getWidth() > imageBitmap.getHeight()) {
            squareSize = imageBitmap.getHeight();
            biggerDimension = imageBitmap.getWidth();
            y1 = 0;
            x1 = (biggerDimension - squareSize) / 2;
            y2 = squareSize;
            x2 = squareSize;
            squareBitmap = Bitmap.createBitmap(imageBitmap, x1, y1, x2, y2);
        } else if (imageBitmap.getHeight() > imageBitmap.getWidth()) {
            squareSize = imageBitmap.getWidth();
            biggerDimension = imageBitmap.getHeight();
            x1 = 0;
            y1 = (biggerDimension - squareSize) / 2;
            x2 = squareSize;
            y2 = squareSize;
            squareBitmap = Bitmap.createBitmap(imageBitmap, x1, y1, x2, y2);
        } else squareBitmap = imageBitmap;
        return getRoundedCornerBitmap(squareBitmap, 1500);
    }

    public static Bitmap getRoundedCornerBitmap(final Bitmap bitmap, final int pixels) {
        final Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap
                .getHeight(), Config.ARGB_8888);
        final Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPx = pixels;

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }

    public static Bitmap rotateBitmap(final Bitmap bitmap, final int orientation) {

        try {
            final Matrix matrix = new Matrix();
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
                final Bitmap bmRotated = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
                bitmap.recycle();
                return bmRotated;
            } catch (final OutOfMemoryError e) {
                e.printStackTrace();
                return null;
            }
        } catch (final Exception e) {
            e.printStackTrace();
            return bitmap;
        }
//        return bitmap;
    }


}
