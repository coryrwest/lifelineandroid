//package com.lifeline.objects;
//
//import android.content.Context;
//
//import com.couchbase.lite.CouchbaseLiteException;
//import com.couchbase.lite.Document;
//import com.couchbase.lite.Query;
//import com.couchbase.lite.QueryEnumerator;
//import com.couchbase.lite.QueryRow;
//import com.lifeline.helpers.DatabaseManager;
//
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.Map;
//
///**
// * Created by cory on 1/1/2015.
// */
//public class Setting extends DatabaseManager {
//    private Map<String, Object> value;
//
//    public Setting(Context context) {
//        super(context);
//    }
//
//    public void Set(String key, String settingValue) {
//        // create an object to hold document data
//        value = new HashMap<String, Object>();
//
//        // store document data
//        value.put("type", "setting");
//        value.put("key", key);
//        value.put("value", settingValue);
//    }
//
//    public boolean Save(Map<String, Object> object) {
//        Document document = null;
//        // check for object existence
//        boolean exists = false;
//        Query query = database.getView(ALL_SETTINGS_VIEW).createQuery();
//        QueryEnumerator result = null;
//        try {
//            result = query.run();
//            for (Iterator<QueryRow> it = result; it.hasNext(); ) {
//                QueryRow row = it.next();
//                Document doc = row.getDocument();
//                Map<String, Object> setting = doc.getProperties();
//                String key = setting.get("key").toString();
//                exists = key.equals(object.get("key").toString());
//                if (exists) {
//                    document = doc;
//                } else {
//                    document = database.createDocument();
//                }
//            }
//
//            // store the data in the document
//            try {
//                assert document != null;
//                document.putProperties(object);
//                return true;
//            } catch (CouchbaseLiteException e) {
//                return false;
//            }
//
//
//        } catch (CouchbaseLiteException e) {
//            e.printStackTrace();
//        }
//        return false;
//    }
//}
