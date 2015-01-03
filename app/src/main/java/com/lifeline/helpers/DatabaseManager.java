//package com.lifeline.helpers;
//
//import android.content.Context;
//
//import com.couchbase.lite.Database;
//import com.couchbase.lite.Emitter;
//import com.couchbase.lite.Manager;
//import com.couchbase.lite.Mapper;
//import com.couchbase.lite.android.AndroidContext;
//
//import java.util.Map;
//
//public class DatabaseManager {
//    protected static Manager manager;
//    protected Database database;
//    public static final String DATABASE_NAME = "android_data";
//    public static final String ALL_SETTINGS_VIEW = "allSettings";
//
//    public DatabaseManager(Context context) {
//        boolean dbExists = checkForExistingDatabase();
//        try{
//            if (!dbExists) {
//                // create a new database
//                manager = new Manager(new AndroidContext(context), Manager.DEFAULT_OPTIONS);
//                database = manager.getDatabase(DATABASE_NAME);
//            } else  {
//                database = manager.getExistingDatabase(DATABASE_NAME);
//            }
//
//            setViews();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    private boolean checkForExistingDatabase() {
//        try {
//            database = manager.getExistingDatabase(DATABASE_NAME);
//            return database != null;
//        } catch (Exception ex) {
//            return false;
//        }
//    }
//
//    private void setViews() {
//        com.couchbase.lite.View viewSettings = database.getView(ALL_SETTINGS_VIEW);
//        viewSettings.setMap(new Mapper() {
//            @Override
//            public void map(Map<String, Object> document, Emitter emitter) {
//                Object type = document.get("type");
//                if (type != null && type == "setting") {
//                    emitter.emit(document.get("key"), document.get("value"));
//                }
//            }
//        }, "1.0");
//    }
//}
