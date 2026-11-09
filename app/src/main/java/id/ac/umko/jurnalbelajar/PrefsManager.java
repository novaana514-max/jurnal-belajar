package id.ac.umko.jurnalbelajar;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Satu-satunya pintu akses ke SharedPreferences.
 * Isi file: shared_prefs/jurnal_prefs.xml (privat, MODE_PRIVATE).
 * Hanya data kecil bertipe key -> value yang disimpan di sini.
 */
public class PrefsManager {
    private static final String FILE = "jurnal_prefs";
    private static final String KEY_NAMA = "nama_panggilan";
    private static final String KEY_TEMA_GELAP = "tema_gelap";
    private static final String KEY_TARGET = "target_menit";

    private final SharedPreferences prefs;

    public PrefsManager(Context context) {
        prefs = context.getApplicationContext()
                .getSharedPreferences(FILE, Context.MODE_PRIVATE);
    }

    public void simpanPengaturan(String nama, boolean temaGelap, int targetMenit) {
        prefs.edit()
                .putString(KEY_NAMA, nama)
                .putBoolean(KEY_TEMA_GELAP, temaGelap)
                .putInt(KEY_TARGET, targetMenit)
                .apply(); // asinkron: UI tidak menunggu tulis disk
    }

    public String getNama()        { return prefs.getString(KEY_NAMA, ""); }
    public boolean isTemaGelap()   { return prefs.getBoolean(KEY_TEMA_GELAP, false); }
    public int getTargetMenit()    { return prefs.getInt(KEY_TARGET, 60); }
}
