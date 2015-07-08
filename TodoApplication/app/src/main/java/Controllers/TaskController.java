package Controllers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import dataBase.TaskContract;
import dataBase.TaskDBHelper;

/**
 * Created by Zeballos on 7/6/15.
 */
public class TaskController {
    private TaskDBHelper helper;


    public void insertTask(String task, Context context) {

        System.out.println("entro insert");
        helper = new TaskDBHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.clear();
        values.put(TaskContract.Columns.TASK, task);

        db.insertWithOnConflict(TaskContract.TABLE, null, values, SQLiteDatabase.CONFLICT_IGNORE);

    }


    public void deleteTask(String task,Context context) {
        String sql = String.format("DELETE FROM %s WHERE %s = '%s'",
                TaskContract.TABLE,
                TaskContract.Columns.TASK,
                task);


        helper = new TaskDBHelper(context);
        SQLiteDatabase sqlDB = helper.getWritableDatabase();
        sqlDB.execSQL(sql);
    }
    public Cursor updateTask(Context context){
        helper = new TaskDBHelper(context);
        SQLiteDatabase sqlDB = helper.getReadableDatabase();
        Cursor cursor = sqlDB.query(TaskContract.TABLE,
                new String[]{TaskContract.Columns._ID, TaskContract.Columns.TASK},
                null, null, null, null, null);
        return cursor;
    }
}
