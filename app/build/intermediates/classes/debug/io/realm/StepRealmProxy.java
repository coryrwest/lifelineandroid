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

public class StepRealmProxy extends Step {

    @Override
    public int getSteps() {
        realm.assertThread();
        return (int) row.getLong(Realm.columnIndices.get("Step").get("steps"));
    }

    @Override
    public void setSteps(int value) {
        realm.assertThread();
        row.setLong(Realm.columnIndices.get("Step").get("steps"), (long) value);
    }

    @Override
    public java.util.Date getDate() {
        realm.assertThread();
        return (java.util.Date) row.getDate(Realm.columnIndices.get("Step").get("date"));
    }

    @Override
    public void setDate(java.util.Date value) {
        realm.assertThread();
        row.setDate(Realm.columnIndices.get("Step").get("date"), (Date) value);
    }

    public static Table initTable(ImplicitTransaction transaction) {
        if(!transaction.hasTable("class_Step")) {
            Table table = transaction.getTable("class_Step");
            table.addColumn(ColumnType.INTEGER, "steps");
            table.addColumn(ColumnType.DATE, "date");
            return table;
        }
        return transaction.getTable("class_Step");
    }

    public static void validateTable(ImplicitTransaction transaction) {
        if(transaction.hasTable("class_Step")) {
            Table table = transaction.getTable("class_Step");
            if(table.getColumnCount() != 2) {
                throw new IllegalStateException("Column count does not match");
            }
            Map<String, ColumnType> columnTypes = new HashMap<String, ColumnType>();
            for(long i = 0; i < 2; i++) {
                columnTypes.put(table.getColumnName(i), table.getColumnType(i));
            }
            if (!columnTypes.containsKey("steps")) {
                throw new IllegalStateException("Missing column 'steps'");
            }
            if (columnTypes.get("steps") != ColumnType.INTEGER) {
                throw new IllegalStateException("Invalid type 'int' for column 'steps'");
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
        return Arrays.asList("steps", "date");
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("Step = [");
        stringBuilder.append("{steps:");
        stringBuilder.append(getSteps());
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
        result = 31 * result + getSteps();
        java.util.Date temp_1 = getDate();
        result = 31 * result + (temp_1 != null ? temp_1.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StepRealmProxy aStep = (StepRealmProxy)o;
        if (getSteps() != aStep.getSteps()) return false;
        if (getDate() != null ? !getDate().equals(aStep.getDate()) : aStep.getDate() != null) return false;
        return true;
    }

}
