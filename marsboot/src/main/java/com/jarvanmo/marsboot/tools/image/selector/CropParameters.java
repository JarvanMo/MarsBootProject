package com.jarvanmo.marsboot.tools.image.selector;

import android.graphics.Bitmap;

/**
 * Created by mo on 17-9-7.
 *
 * @author mo
 */

public class CropParameters {
//
//    Intent intent1 = new Intent("com.android.camera.action.CROP");
//            intent1.setDataAndType(Uri.fromFile(new File(image.path)), "image/*");
//            intent1.putExtra("crop", "true");
//            intent1.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));//
//            intent1.putExtra("aspectX", 1);
//            intent1.putExtra("aspectY", 1);
//            intent1.putExtra("outputFormat", Bitmap.CompressFormat.JPEG);
//            intent1.putExtra("outputX", 720);
//            intent1.putExtra("outputY", 720);
//            intent1.putExtra("scale", true);
//            intent1.putExtra("scaleUpIfNeeded", true);
//            intent1.putExtra("return-data", false);
//    startActivityForResult(intent1, 0x222);

    private int aspectX = 1;
    private int aspectY = 1;
    private Bitmap.CompressFormat outputFormat  = Bitmap.CompressFormat.JPEG;
    private int outputX = 720;
    private int outputY = 720;
    private boolean scale = true;
    private boolean scaleUpIfNeeded= true;

    public int getAspectX() {
        return aspectX;
    }

    public void setAspectX(int aspectX) {
        this.aspectX = aspectX;
    }

    public int getAspectY() {
        return aspectY;
    }

    public void setAspectY(int aspectY) {
        this.aspectY = aspectY;
    }

    public Bitmap.CompressFormat getOutputFormat() {
        return outputFormat;
    }

    public void setOutputFormat(Bitmap.CompressFormat outputFormat) {
        this.outputFormat = outputFormat;
    }

    public int getOutputX() {
        return outputX;
    }

    public void setOutputX(int outputX) {
        this.outputX = outputX;
    }

    public int getOutputY() {
        return outputY;
    }

    public void setOutputY(int outputY) {
        this.outputY = outputY;
    }

    public boolean isScale() {
        return scale;
    }

    public void setScale(boolean scale) {
        this.scale = scale;
    }

    public boolean isScaleUpIfNeeded() {
        return scaleUpIfNeeded;
    }

    public void setScaleUpIfNeeded(boolean scaleUpIfNeeded) {
        this.scaleUpIfNeeded = scaleUpIfNeeded;
    }
}
