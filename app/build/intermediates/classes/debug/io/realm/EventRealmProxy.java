package io.realm;


import com.lifeline.objects.*;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.internal.ColumnType;
import io.realm.internal.ImplicitTransaction;
import io.realm.internal.LinkView;
import io.realm.internal.Row;
import io.realm.internal.Table;
import java.util.*;

public class EventRealmProxy extends Event {

    @Override
    public String getEventType() {
        realm.assertThread();
        return (java.lang.String) row.getString(Realm.columnIndices.get("Event").get("eventType"));
    }

    @Override
    public void setEventType(String value) {
        realm.assertThread();
        row.setString(Realm.columnIndices.get("Event").get("eventType"), (String) value);
    }

    @Override
    public java.util.Date getDate() {
        realm.assertThread();
        return (java.util.Date) row.getDate(Realm.columnIndices.get("Event").get("date"));
    }

    @Override
    public void setDate(java.util.Date value) {
        realm.assertThread();
        row.setDate(Realm.columnIndices.get("Event").get("date"), (Date) value);
    }

    public static Table initTable(ImplicitTransaction transaction) {
        if(!transaction.hasTable("class_Event")) {
            Table table = transaction.getTable("class_Event");
            table.addColumn(ColumnType.STRING, "eventType");
            table.addColumn(ColumnType.DATE, "date");
            return table;
        }
        return transaction.getTable("class_Event");
    }

    public static void validateTable(ImplicitTransaction transaction) {
        if(transaction.hasTable("class_Event")) {
            Table table = transaction.getTable("class_Event");
            if(table.getColumnCount() != 2) {
                throw new IllegalStateException("Column count does not match");
            }
            Map<String, ColumnType> columnTypes = new HashMap<String, ColumnType>();
            for(long i = 0; i < 2; i++) {
                columnTypes.put(table.getColumnName(i), table.getColumnType(i));
            }
            if (!columnTypes.containsKey("eventType")) {
                throw new IllegalStateException("Missing column 'eventType'");
            }
            if (columnTypes.get("eventType") != ColumnType.STRING) {
                throw new IllegalStateException("Invalid type 'String' for column 'eventType'");
            }
            if (!columnTypes.containsKey("date")) {
                throw new IllegalStateException("Missing column 'date'");
            }
            if (columnTypes.get("date") != ColumnType.DATE) {
                throw new IllegalStateException("Invalid type 'Date' for column 'date'");
            }
        }
    }

    public static List<String> getFieldNames() {
        return Arrays.asList("eventType", "date");
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("Event = [");
        stringBuilder.append("{eventType:");
        stringBuilder.append(getEventType());
        stringBuilder.append("} ");
        stringBuilder.append("{date:");
        stringBuilder.append(getDate());
        stringBuilder.append("} ");
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    @Override
    public int hashCode() {
        int result = 17;
        String aString_0 = getEventType();
        result = 31 * result + (aString_0 != null ? aString_0.hashCode() : 0);
        java.util.Date temp_1 = getDate();
        result = 31 * result + (temp_1 != null ? temp_1.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventRealmProxy aEvent = (EventRealmProxy)o;
        if (getEventType() != null ? !getEventType().equals(aEvent.getEventType()) : aEvent.getEventType() != null) return false;
        if (getDate() != null ? !getDate().equals(aEvent.getDate()) : aEvent.getDate() != null) return false;
        return true;
    }

}
