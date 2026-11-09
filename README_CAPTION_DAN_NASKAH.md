# Caption komentar (isi bagian [..] milikmu)

SharedPreferences pada Aplikasi Mobile – Jurnal Belajar Harian
Tugas P10 Pemrograman Mobile · Prodi STI · Universitas Muhammadiyah Kotabumi

Nama  : [Nama lengkap]
NIM   : [NIM]
Kelas : [Kelas]
Dosen : Ryan Aji Wijaya, S.Kom., M.M.S.I

Keterangan:
1 Mahasiswa sering lupa mencatat dan menargetkan waktu belajar; aplikasi Jurnal Belajar mencatat materi + durasi dan mengingat pengaturan pengguna.
2 SharedPreferences: nama panggilan, tema gelap, target belajar (menit). Alasannya: kecil, berbentuk key-value, dibutuhkan sejak layar pertama tampil.
3 Database SQLite: riwayat materi & durasi karena berupa daftar yang terus bertambah dan perlu di-query/dijumlahkan. Tidak disimpan sama sekali: kata sandi/data sensitif (tidak pernah ditulis ke prefs).
4 PrefsManager membungkus getSharedPreferences(MODE_PRIVATE); simpan pakai edit().put...().apply() (asinkron, UI tidak menunggu); nilai dibaca ulang di onResume().
5 Bukti: ubah nama + aktifkan tema gelap -> Simpan -> Force stop -> buka lagi -> "Halo, <nama>!" dan tema gelap tetap ada.

Kode: [link repositori GitHub]

Menurutmu, data apa lagi di aplikasi kalian yang sebaiknya TIDAK disimpan di SharedPreferences?

#android #java #pemrogramanmobile #sharedpreferences #umko

# Naskah video vertikal (~60 detik, 9:16) - rekam layar HP/emulator
0-5s   Judul: "Jurnal Belajar: simpan di mana?" tampilkan app.
5-15s  Masalah: lupa catat & pengaturan hilang tiap buka app.
15-30s Peta data: Prefs = nama, tema, target | SQLite = riwayat | Tidak disimpan = kata sandi.
30-42s Tampilkan PrefsManager.java: edit().putString(...).apply(); jelaskan apply vs commit.
42-50s Tampilkan MainActivity.onResume() membaca prefs.
50-60s BUKTI: isi nama+tema gelap -> Simpan -> Force stop (Setelan > Aplikasi) -> buka lagi, tetap ada. Tutup dengan pertanyaan penonton.
