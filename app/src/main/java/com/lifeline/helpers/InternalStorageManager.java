//package com.lifeline.helpers;
//
//import android.content.Context;
//
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//
///**
// * Created by cory on 1/2/15.
// */
//public class InternalStorageManager {
//    String FILENAME = "lifeline_data";
//    Context Context;
//
//    public InternalStorageManager(Context context) {
//        Context = context;
//    }
//
//    private FileOutputStream OpenFile() {
//        FileOutputStream fos = null;
//        try {
//            fos = Context.openFileOutput(FILENAME, Context.MODE_PRIVATE);
//            return fos;
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//
//}
