package com.jarvanmo.marsboot.tools.image;


import android.app.Activity;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.util.ArraySet;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.Set;

/**
 * Created by mo on 17-9-7.
 *
 * @author mo
 */

public class SimpleImageSelector {


    private static final int rquestCodeCamera = 0x100;


    public static class Builder {

        private WeakReference<Activity> activityWeakReference;
        private Set<SelectorItemType> itemTypes = new ArraySet<>();
        private ImagePathCallback photoPathCallback;

        public Builder(Activity activity) {
            activityWeakReference = new WeakReference<Activity>(activity);
        }

        public Builder from(SelectorItemType... types) {
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


        public NeedCropBuilder needCrop() {
            String path;
            if (photoPathCallback == null) {
                File dir = activityWeakReference.get().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
                String fileName = System.currentTimeMillis()+".jpg";
                File photo = new File(dir,fileName);
                path = photo.getAbsolutePath();
            }else {
                path = photoPathCallback.createPath();
            }

            return new NeedCropBuilder(activityWeakReference, itemTypes,path);
        }

    }

}
