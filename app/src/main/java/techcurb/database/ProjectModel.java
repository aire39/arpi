package techcurb.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by seanx_000 on 5/24/2016.
 *
 */
public class ProjectModel extends SQLiteOpenHelper {

    private final static String DATABASE_NAME = "ArPi.db";

    public ProjectModel(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE projects " +
                   "(id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT UNIQUE, hardware TEXT, notes TEXT, last_modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS projects");
        onCreate(db);
    }

    public boolean insertProject(String name, String hardware) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("name", name);
        contentValues.put("hardware", hardware);
        db.insert("projects", null, contentValues);
        db.close();

        return true;
    }

    public boolean updateProject(Integer id, String name, String hardware) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("id", id);
        contentValues.put("name", name);
        contentValues.put("hardware", hardware);
        contentValues.put("last_modified", "time('now')");

        db.update("projects", contentValues, "id = ?", new String[] {Integer.toString(id)});
        db.close();

        return true;
    }

    public Integer deleteProject (Integer id) {

        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("projects", "id = ?", new String[] {Integer.toString(id)});
    }

    public HashMap<String, ArrayList<String>> getAllProjects() {
        HashMap<String, ArrayList<String>> array_list = new HashMap<>();


        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM projects", null);
        res.moveToFirst();

        while(!res.isAfterLast()) {
            ArrayList<String> list = new ArrayList<>();
            list.add( res.getString( res.getColumnIndex("id") ));
            list.add( res.getString( res.getColumnIndex("name") ));
            list.add( res.getString( res.getColumnIndex("hardware") ));
            list.add( res.getString( res.getColumnIndex("last_modified") ));
            array_list.put(list.get(0), list);
            res.moveToNext();
        }

        res.close();
        db.close();
        return array_list;
    }
}
