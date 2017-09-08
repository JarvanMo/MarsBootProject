package com.jarvanmo.marsboot.tools.image;


import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.util.ArraySet;
import android.view.LayoutInflater;
import android.view.View;

import com.jarvanmo.marsboot.R;
import com.jarvanmo.marsboot.databinding.BottomSheetSimpleImageSelectorBinding;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * Created by mo on 17-9-7.
 *
 * @author mo
 */

public class SimpleImageSelector {


    private static final int requestCodeCamera = 0x100;
    private static final int requestCodeAlbum = requestCodeCamera + 1;
    private static final int requestCodeCrop = requestCodeAlbum + 1;


    private WeakReference<Activity> activityWeakReference;
    private Set<SelectorItemType> itemTypes;
    private ImagePathCallback photoPathCallback;
    private ImagePathCallback croppedImagePathCallback;
    private ImageSelectorCallback selectorCallback;
    private CropParameters cropParameters = new CropParameters();


    private boolean needCrop;


    private File photoTmpFile;
    private BottomSheetDialog bottomSheetDialog;
    private File cropImageTmpFile;

    private List<File> cachedImages = new ArrayList<>();

   private SimpleImageSelector(WeakReference<Activity> activityWeakReference) {
        this.activityWeakReference = activityWeakReference;
    }


   private SimpleImageSelector build() {
        bottomSheetDialog = new BottomSheetDialog(activityWeakReference.get());

        LayoutInflater inflater = LayoutInflater.from(activityWeakReference.get());
        BottomSheetSimpleImageSelectorBinding binding = DataBindingUtil.inflate(inflater, R.layout.bottom_sheet_simple_image_selector, null, false);

        initFromAlbum(binding);

        initFromCamera(binding);

        initFromCancel(binding);

        bottomSheetDialog.setContentView(binding.getRoot());
        bottomSheetDialog.setTitle("请选择图片来源");

        return this;
    }

    private void addViews(BottomSheetSimpleImageSelectorBinding binding){
        for (SelectorItemType itemType : itemTypes) {
            switch (itemType){
                case ALBUM:
//                    setUpAlbum(binding,itemTypes.in);
                    break;
                default:
                    break;
            }
        }
    }

    private void setUpAlbum(BottomSheetSimpleImageSelectorBinding binding,boolean needDivider){

    }

    private void initFromAlbum(BottomSheetSimpleImageSelectorBinding binding) {

        if (!itemTypes.contains(SelectorItemType.ALBUM)) {
            binding.fromAlbum.setVisibility(View.GONE);
        }

        binding.fromAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callAlbum();
            }
        });
    }


    private void callAlbum() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        activityWeakReference.get().startActivityForResult(Intent.createChooser(intent, "image selector"), requestCodeAlbum);
        bottomSheetDialog.cancel();
    }

    private void initFromCamera(BottomSheetSimpleImageSelectorBinding binding) {

        if (!itemTypes.contains(SelectorItemType.CAMERA)) {
            binding.fromAlbum.setVisibility(View.GONE);
        }

        binding.fromCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callCamera();
            }
        });

    }

    private void callCamera() {
        if (photoPathCallback != null) {
            photoTmpFile = photoPathCallback.createPhotoFile();
        } else {
            String tmpName = "camera_" + System.currentTimeMillis() + ".jpg";
            File dir = activityWeakReference.get().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            photoTmpFile = new File(dir, tmpName);
        }

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoTmpFile));
        activityWeakReference.get().startActivityForResult(intent, requestCodeCamera);
        bottomSheetDialog.cancel();

    }

    private void initFromCancel(BottomSheetSimpleImageSelectorBinding binding) {

        if (!itemTypes.contains(SelectorItemType.CANCEL)) {
            binding.cancel.setVisibility(View.GONE);
        }

        binding.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetDialog.dismiss();
            }
        });
    }


    public void show() {
        if (!bottomSheetDialog.isShowing()) {
            bottomSheetDialog.show();
        }
    }

    /**
     * clean the images from camera and crop
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public void clean() {
        for (File cachedImage : cachedImages) {
            if (cachedImage.exists()) {
                cachedImage.delete();
            }
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        switch (requestCode) {
            case requestCodeAlbum:
                resultFromAlbum(data);
                break;
            case requestCodeCamera:
                resultFromCamera();
                break;
            case requestCodeCrop:
                resultFromCrop();
                break;
        }

    }

    private void resultFromAlbum(Intent data) {
        if (needCrop) {
            callCrop(data.getData());
        } else if (selectorCallback != null) {
            selectorCallback.onImageResult(data.getData());
        }
    }


    private void resultFromCamera() {
        cachedImages.add(photoTmpFile);

        if (needCrop) {
            callCrop(Uri.fromFile(photoTmpFile));
        } else if (selectorCallback != null) {
            selectorCallback.onImageResult(Uri.fromFile(photoTmpFile));
        }
    }

    private void resultFromCrop() {
        cachedImages.add(cropImageTmpFile);

        if (selectorCallback != null) {
            selectorCallback.onImageResult(Uri.fromFile(cropImageTmpFile));
        }
    }

    private void callCrop(Uri uri) {
        Intent crop = new Intent("com.android.camera.action.CROP");
        crop.setDataAndType(uri, "image/*");
        crop.putExtra("crop", "true");

        crop.putExtra("aspectX", cropParameters.getAspectX());
        crop.putExtra("aspectY", cropParameters.getAspectY());
        crop.putExtra("outputFormat", cropParameters.getOutputFormat());
        crop.putExtra("outputX", cropParameters.getOutputX());
        crop.putExtra("outputY", cropParameters.getOutputY());
        crop.putExtra("scale", true);
        crop.putExtra("scaleUpIfNeeded", true);
        crop.putExtra("return-data", false);

        if (croppedImagePathCallback != null) {
            cropImageTmpFile = croppedImagePathCallback.createPhotoFile();
        } else {
            String tmpName = "crop_" + System.currentTimeMillis() + ".jpg";
            File dir = activityWeakReference.get().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            cropImageTmpFile = new File(dir, tmpName);
        }

        crop.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(cropImageTmpFile));//
        activityWeakReference.get().startActivityForResult(crop, requestCodeCrop);
    }


    public static class Builder {

        private WeakReference<Activity> activityWeakReference;
        private Set<SelectorItemType> itemTypes = null;
        private ImagePathCallback photoPathCallback;
        private ImageSelectorCallback selectorCallback;

        public Builder(Activity activity) {
            activityWeakReference = new WeakReference<>(activity);
        }

        public Builder from(SelectorItemType... types) {
            itemTypes = new ArraySet<>();

            Collections.addAll(itemTypes, types);

            if (itemTypes.isEmpty()) {
                throw new IllegalStateException("selector item can't be empty");
            }

            if (itemTypes.size() == 1 && itemTypes.contains(SelectorItemType.CANCEL)) {
                throw new IllegalStateException("you can't use cancel only!!!");
            }
            return this;
        }


        public Builder photoPath(@NonNull ImagePathCallback callback) {
            photoPathCallback = callback;
            return this;
        }

        public Builder result(@NonNull ImageSelectorCallback selectorCallback) {
            this.selectorCallback = selectorCallback;
            return this;
        }


        public SimpleImageSelector build() {
            SimpleImageSelector simpleImageSelector = new SimpleImageSelector(activityWeakReference);
            simpleImageSelector.needCrop = false;

            if (itemTypes == null) {
                itemTypes = new ArraySet<>();
                itemTypes.add(SelectorItemType.CAMERA);
                itemTypes.add(SelectorItemType.ALBUM);
                itemTypes.add(SelectorItemType.CANCEL);
            }
            simpleImageSelector.itemTypes = itemTypes;

            simpleImageSelector.photoPathCallback = photoPathCallback;

            simpleImageSelector.selectorCallback = selectorCallback;

            return simpleImageSelector.build();
        }


        public NeedCropBuilder needCrop() {
            return new NeedCropBuilder(activityWeakReference, itemTypes, photoPathCallback, selectorCallback);
        }

    }


    public static class NeedCropBuilder {

        private WeakReference<Activity> activityWeakReference;
        private Set<SelectorItemType> itemTypes = new ArraySet<>();
        private ImagePathCallback photoPathCallback;
        private ImagePathCallback croppedImagePathCallback;
        private ImageSelectorCallback selectorCallback;
        private CropParameters parameters = new CropParameters();


        NeedCropBuilder(WeakReference<Activity> activityWeakReference, Set<SelectorItemType> itemTypes, ImagePathCallback photoPathCallback, ImageSelectorCallback selectorCallback) {
            this.activityWeakReference = activityWeakReference;
            this.itemTypes = itemTypes;
            this.photoPathCallback = photoPathCallback;
            this.selectorCallback = selectorCallback;
        }


        public NeedCropBuilder croppedImagePath(@NonNull ImagePathCallback callback) {
            croppedImagePathCallback = callback;
            return this;
        }


        public NeedCropBuilder parameters(@NonNull CropParameters parameters) {
            this.parameters = parameters;
            return this;
        }


        public SimpleImageSelector build() {
            SimpleImageSelector simpleImageSelector = new SimpleImageSelector(activityWeakReference);
            simpleImageSelector.needCrop = true;
            simpleImageSelector.cropParameters = parameters;
            simpleImageSelector.croppedImagePathCallback = croppedImagePathCallback;

            if (itemTypes == null) {
                itemTypes = new ArraySet<>();
                itemTypes.add(SelectorItemType.CAMERA);
                itemTypes.add(SelectorItemType.ALBUM);
                itemTypes.add(SelectorItemType.CANCEL);
            }
            simpleImageSelector.itemTypes = itemTypes;

            simpleImageSelector.photoPathCallback = photoPathCallback;

            simpleImageSelector.selectorCallback = selectorCallback;

            return simpleImageSelector.build();
        }

    }


}
