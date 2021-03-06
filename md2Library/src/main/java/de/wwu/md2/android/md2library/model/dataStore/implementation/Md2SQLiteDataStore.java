package de.wwu.md2.android.md2library.model.dataStore.implementation;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.HashMap;

import de.wwu.md2.android.md2library.model.dataStore.Filter;
import de.wwu.md2.android.md2library.model.dataStore.interfaces.Md2DataStore;
import de.wwu.md2.android.md2library.model.dataStore.interfaces.Md2LocalStore;
import de.wwu.md2.android.md2library.model.dataStore.interfaces.Md2SQLiteHelper;
import de.wwu.md2.android.md2library.model.type.interfaces.Md2Entity;
import de.wwu.md2.android.md2library.model.type.interfaces.Md2Type;

/**
 * Implementation of the local SQLite data store that is used to store values of entities
 * in the SQLite database provided by Android.
 * <p/>
 * Created on 14/07/2015
 *
 * @author Fabian Wrede
 * @version 1.0
 * @since 1.0
 */
public class Md2SQLiteDataStore extends AbstractMd2DataStore implements Md2LocalStore,Md2DataStore {

    /**
     * The Sq lite helper.
     */
    Md2SQLiteHelper sqLiteHelper;

    /**
     * Instantiates a new Md 2 sq lite data store.
     *
     * @param sqLiteHelper the sq lite helper
     */
    public Md2SQLiteDataStore(Md2SQLiteHelper sqLiteHelper) {

        this.sqLiteHelper = sqLiteHelper;
    }

    @Override
    public void query(Filter filter) {
        // TODO: implement query method in SQLite DataStore
        //return null;
    }

    @Override
    public void load(long id, Class dataType) {

        SQLiteDatabase db = sqLiteHelper.open(false);

        String[] columns = sqLiteHelper.getAllColumns(dataType.getClass().getSimpleName());

        String selection = "_id = ?";

        String[] values = {String.valueOf(id)};

        Cursor cursor = db.query(
                dataType.getClass().getSimpleName().toLowerCase(),  // The table to query
                columns,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                values,                            // The values for the WHERE clause
                null,                                     // group the rows
                null,                                     // filter by row groups
                null                                      // The sort order
        );

        cursor.moveToFirst();

        HashMap<String, String> result = new HashMap();

        for (String col : columns
                ) {
            result.put(col, cursor.getString(cursor.getColumnIndexOrThrow(col)));
        }
        System.out.println("Loaded " + result.values().size() + "items");


        cursor.close();
        db.close();
        //return result;
    }

//    @Override
//    public void getInternalId(Md2Entity entity) {
//        SQLiteDatabase db = sqLiteHelper.open(false);
//
//        String[] projection = {"_id"};
//
//        String[] columns = sqLiteHelper.getAllColumns(entity.getTypeName());
//
//        String selection = "";
//        for (int i = 0; i < columns.length; i++) {
//            if (i == 0) {
//                selection += columns[i] + " = ?";
//            } else {
//                selection += " AND " + columns[i] + " = ?";
//            }
//        }
//
//        String[] values = new String[columns.length];
//
//        for (int i = 0; i < values.length; i++) {
//            Md2Type value = entity.get(columns[i]);
//            if (value == null) {
//                values[i] = "";
//            } else {
//                values[i] = value.getString().getPlatformValue();
//            }
//        }
//
//        Cursor cursor = db.query(
//                entity.getTypeName().toLowerCase(),  // The table to query
//                projection,                               // The columns to return
//                selection,                                // The columns for the WHERE clause
//                values,                            // The values for the WHERE clause
//                null,                                     // group the rows
//                null,                                     // filter by row groups
//                null                                      // The sort order
//        );
//        cursor.moveToFirst();
//        long id = -1;
//        if (cursor.getCount() > 0) {
//            id = cursor.getLong(cursor.getColumnIndexOrThrow("_id"));
//        }
//        System.out.println("Retrieved Id: " + id);
//
//        cursor.close();
//        db.close();
//        //return id;
//    }

    @Override
    public void put(Md2Entity md2Entity) {
        // entity exists
        if (md2Entity.getId() > -1) {
            //return put(md2Entity.getId(), md2Entity);
        }

        // Gets the data repository in write mode
        SQLiteDatabase db = sqLiteHelper.open(true);

        String[] columns = sqLiteHelper.getAllColumns(md2Entity.getTypeName());
        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();

        for (String col : columns
                ) {
            Md2Type value = md2Entity.get(col);
            if (value == null) {
                values.put(col, "");
            } else {
                values.put(col, value.getString().getPlatformValue());
            }
        }

        long newRowId;
        newRowId = db.insert(
                md2Entity.getTypeName().toLowerCase(), null,
                values);
        System.out.println("New row id: " + newRowId);

        db.close();
        //return newRowId;
    }

    @Override
    public void put(long id, Md2Entity md2Entity) {
        SQLiteDatabase db = sqLiteHelper.open(true);

        String[] columns = sqLiteHelper.getAllColumns(md2Entity.getTypeName());
        // values
        ContentValues values = new ContentValues();

        for (String col : columns
                ) {
            Md2Type value = md2Entity.get(col);
            if (value == null) {
                values.put(col, "");
            } else {
                values.put(col, value.getString().getPlatformValue());
            }
        }
        values.put("_id", id);

// Which row to update, based on the ID
        String selection = "_id" + " = " + String.valueOf(id);

        int count = db.update(
                md2Entity.getTypeName().toLowerCase(),
                values,
                selection,
                null);
        System.out.println("Updated " + count + "rows");

        db.close();
        //return (count == 1) ? id : -1;
    }

    @Override
    public void remove(long id, Md2Entity md2Entity) {

        SQLiteDatabase db = sqLiteHelper.open(true);

        db.delete(md2Entity.getClass().getSimpleName(), "_id"
                + " = " + id, null);
        System.out.println("Entity deletion");

        db.close();
    }
}