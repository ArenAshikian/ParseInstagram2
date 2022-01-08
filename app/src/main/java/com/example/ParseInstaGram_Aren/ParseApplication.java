package com.example.ParseInstaGram_Aren;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

public class ParseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        ParseObject.registerSubclass(Post.class);
        ParseObject.registerSubclass(Post.class);

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("GZbYyXfxa59Gb61ft2KmrISSFFkAmmaG3s7HhISt")
                .clientKey("uh4IIR40d1AIjvxw0Tzyu6bKd3zeR7oazr3aZjhs")
                .server("https://parseapi.back4app.com")
                .build()
        );
    }
}
