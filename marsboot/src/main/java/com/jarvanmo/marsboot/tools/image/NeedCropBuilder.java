package com.jarvanmo.marsboot.tools.image;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v4.util.ArraySet;

import java.lang.ref.WeakReference;
import java.util.Set;

/**
 * Created by mo on 17-9-7.
 *
 * @author mo
 */

class NeedCropBuilder {

    private WeakReference<Activity> activityWeakReference;
    private Set<SelectorItemType> itemTypes = new ArraySet<>();
    private String photoPath;
    private String croppedImagePath;
    private ImagePathCallback croppedImagePathCallback;
    private CropParameters parameters = new CropParameters();

    NeedCropBuilder(WeakReference<Activity> activityWeakReference, Set<SelectorItemType> itemTypes, String photoPath) {
        this.activityWeakReference = activityWeakReference;
        this.itemTypes = itemTypes;
        this.photoPath = photoPath;
    }


    public NeedCropBuilder croppedImagePath(@NonNull ImagePathCallback callback){
        croppedImagePathCallback = callback;
        return this;
    }


    public NeedCropBuilder parameters(@NonNull CropParameters parameters){
        this.parameters = parameters;
        return this;
    }

}
