package com.orm;

import android.content.Context;
import android.util.Log;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FakeBitmapDisplayer;

import java.util.*;

import static com.orm.dsl.Collection.set;

public class SugarApp extends android.app.Application{

    Database database;
    private static SugarApp sugarContext;

    public void onCreate(){
        super.onCreate();
        SugarApp.sugarContext = this;


        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .cacheInMemory()
                .cacheOnDisc()
                .build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
        .defaultDisplayImageOptions(options)
        .build();
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.init(config);

        this.database = new Database(this);
    }

    public void onTerminate(){

    if (this.database != null) {
      this.database.closeDB();
    }
        super.onTerminate();
    }

    public static SugarApp getSugarContext(){
        return sugarContext;
    }


}
