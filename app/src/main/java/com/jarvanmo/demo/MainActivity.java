package com.jarvanmo.demo;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.jarvanmo.marsboot.tools.image.ImageSelectorCallback;
import com.jarvanmo.marsboot.tools.image.SelectorItemType;
import com.jarvanmo.marsboot.tools.image.SimpleImageSelector;

public class MainActivity extends AppCompatActivity {
    SimpleImageSelector selector;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        selector  = new SimpleImageSelector.Builder(this)
                                            .from(SelectorItemType.ALBUM,SelectorItemType.CAMERA)
                                            .result(new ImageSelectorCallback() {
                                                @Override
                                                public void onImageResult(Uri uri) {
                                                    Log.e("sss",uri.toString());
                                                }
                                            })
                                            .needCrop()
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
        selector.onActivityResult(requestCode,resultCode,data);
    }
}
