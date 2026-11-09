package id.ac.umko.jurnalbelajar;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;

public class MainActivity extends AppCompatActivity {
    private PrefsManager prefs;
    private DbHelper db;
    private TextView tvSapa, tvTema, tvProgres, tvBukti;
    private EditText etNama, etTarget, etMateri, etMenit;
    private SwitchCompat swTema;
    private ArrayAdapter<String> adapter;

    @Override protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_main);
        prefs = new PrefsManager(this);
        db = new DbHelper(this);

        tvSapa = findViewById(R.id.tvSapa);
        tvTema = findViewById(R.id.tvTema);
        tvProgres = findViewById(R.id.tvProgres);
        tvBukti = findViewById(R.id.tvBukti);
        etNama = findViewById(R.id.etNama);
        etTarget = findViewById(R.id.etTarget);
        etMateri = findViewById(R.id.etMateri);
        etMenit = findViewById(R.id.etMenit);
        swTema = findViewById(R.id.swTema);
        ListView lv = findViewById(R.id.lvJurnal);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        lv.setAdapter(adapter);

        ((Button) findViewById(R.id.btnSimpanPref)).setOnClickListener(v -> {
            String nama = etNama.getText().toString().trim();
            int target = parse(etTarget.getText().toString(), 60);
            prefs.simpanPengaturan(nama, swTema.isChecked(), target); // -> apply()
            Toast.makeText(this, "Pengaturan disimpan", Toast.LENGTH_SHORT).show();
            terapkanTema(swTema.isChecked());
            muatPengaturan();
        });

        ((Button) findViewById(R.id.btnTambah)).setOnClickListener(v -> {
            String materi = etMateri.getText().toString().trim();
            int menit = parse(etMenit.getText().toString(), 0);
            if (materi.isEmpty() || menit <= 0) {
                Toast.makeText(this, "Isi materi & menit", Toast.LENGTH_SHORT).show();
                return;
            }
            db.tambah(materi, menit);          // -> SQLite
            etMateri.setText(""); etMenit.setText("");
            muatRiwayat();
        });
    }

    /** Pengaturan SELALU dibaca ulang dari SharedPreferences saat layar tampil. */
    @Override protected void onResume() {
        super.onResume();
        terapkanTema(prefs.isTemaGelap());
        muatPengaturan();
        muatRiwayat();
    }

    private void muatPengaturan() {
        String nama = prefs.getNama();
        tvSapa.setText("Halo, " + (nama.isEmpty() ? "Mahasiswa" : nama) + "!");
        etNama.setText(nama);
        etTarget.setText(String.valueOf(prefs.getTargetMenit()));
        swTema.setChecked(prefs.isTemaGelap());
        tvTema.setText(prefs.isTemaGelap() ? "Tema gelap aktif" : "Tema terang aktif");
        tvBukti.setText("✓ Dibaca dari SharedPreferences (jurnal_prefs.xml) di onResume()");
    }

    private void muatRiwayat() {
        adapter.clear();
        adapter.addAll(db.semua());
        tvProgres.setText("Hari ini: " + db.totalMenit() + " / " + prefs.getTargetMenit() + " menit");
    }

    private void terapkanTema(boolean gelap) {
        int mode = gelap ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO;
        if (AppCompatDelegate.getDefaultNightMode() != mode)
            AppCompatDelegate.setDefaultNightMode(mode);
    }

    private int parse(String s, int def) {
        try { return Integer.parseInt(s.trim()); } catch (Exception e) { return def; }
    }
}
