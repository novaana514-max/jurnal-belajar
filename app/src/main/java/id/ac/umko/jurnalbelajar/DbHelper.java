package id.ac.umko.jurnalbelajar;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/** Riwayat jurnal belajar: data berbentuk daftar & terus bertambah -> database. */
public class DbHelper extends SQLiteOpenHelper {
    public DbHelper(Context c) { super(c, "jurnal.db", null, 1); }

    @Override public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE jurnal (id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "materi TEXT NOT NULL, menit INTEGER NOT NULL, waktu TEXT NOT NULL)");
    }
    @Override public void onUpgrade(SQLiteDatabase db, int o, int n) {
        db.execSQL("DROP TABLE IF EXISTS jurnal");
        onCreate(db);
    }

    public void tambah(String materi, int menit) {
        ContentValues v = new ContentValues();
        v.put("materi", materi);
        v.put("menit", menit);
        v.put("waktu", new SimpleDateFormat("dd MMM HH:mm", new Locale("id")).format(new Date()));
        getWritableDatabase().insert("jurnal", null, v);
    }

    public List<String> semua() {
        List<String> out = new ArrayList<>();
        try (Cursor c = getReadableDatabase().rawQuery(
                "SELECT materi, menit, waktu FROM jurnal ORDER BY id DESC", null)) {
            while (c.moveToNext())
                out.add(c.getString(0) + " — " + c.getInt(1) + " menit (" + c.getString(2) + ")");
        }
        return out;
    }

    public int totalMenit() {
        try (Cursor c = getReadableDatabase().rawQuery("SELECT COALESCE(SUM(menit),0) FROM jurnal", null)) {
            return c.moveToFirst() ? c.getInt(0) : 0;
        }
    }
}
