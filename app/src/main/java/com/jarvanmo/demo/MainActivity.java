package com.jarvanmo.demo;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.jarvanmo.marsboot.tools.image.selector.CropParameters;
import com.jarvanmo.marsboot.tools.image.selector.ImageSelectorCallback;
import com.jarvanmo.marsboot.tools.image.selector.SelectorItemType;
import com.jarvanmo.marsboot.tools.image.selector.SimpleImageSelector;
import com.jarvanmo.marsboot.tools.update.AppUpdater;

import okhttp3.OkHttpClient;
import okhttp3.Request;

public class MainActivity extends AppCompatActivity {
    SimpleImageSelector selector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        selector  = new SimpleImageSelector.Builder(this)
                                            .result(new ImageSelectorCallback() {
                                                @Override
                                                public void onImageResult(Uri uri) {
                                                    Log.e("sss",uri.toString());
                                                }
                                            })
                                            .crop(new CropParameters())
                                            .build();



        findViewById(R.id.hello).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selector.show();
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        selector.onActivityResult(requestCode, resultCode, data);

    }
}
