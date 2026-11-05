package lat.pam.kopidannyemil

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class DetailActivity : AppCompatActivity() {

    private var jumlah = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        // ambil id menu yang dikirim dari Home
        val id = intent.getIntExtra("id", -1)
        val produk = DummyData.produkList.find { it.id == id }
        if (produk == null) {
            finish()
            return
        }

        val tvBack = findViewById<TextView>(R.id.tvBack)
        val imgDetail = findViewById<ImageView>(R.id.imgDetail)
        val tvKategori = findViewById<TextView>(R.id.tvKategori)
        val tvNamaDetail = findViewById<TextView>(R.id.tvNamaDetail)
        val tvHargaDetail = findViewById<TextView>(R.id.tvHargaDetail)
        val tvDeskripsiDetail = findViewById<TextView>(R.id.tvDeskripsiDetail)
        val tvMinusD = findViewById<TextView>(R.id.tvMinusD)
        val tvQtyD = findViewById<TextView>(R.id.tvQtyD)
        val tvPlusD = findViewById<TextView>(R.id.tvPlusD)
        val btnTambah = findViewById<Button>(R.id.btnTambah)

        imgDetail.setImageResource(produk.gambar)
        tvKategori.text = produk.kategori.uppercase()
        tvNamaDetail.text = produk.nama
        tvHargaDetail.text = DummyData.rupiah(produk.harga)
        tvDeskripsiDetail.text = produk.deskripsi

        // jumlah awal = jumlah yang sudah ada di keranjang (minimal 1)
        jumlah = DummyData.cart[produk.id] ?: 0
        if (jumlah == 0) jumlah = 1

        fun tampilkan() {
            tvQtyD.text = jumlah.toString()
            btnTambah.text = "Tambah ke Order - ${DummyData.rupiah(produk.harga * jumlah)}"
        }
        tampilkan()

        tvPlusD.setOnClickListener {
            jumlah++
            tampilkan()
        }

        tvMinusD.setOnClickListener {
            if (jumlah > 1) {
                jumlah--
                tampilkan()
            }
        }

        btnTambah.setOnClickListener {
            DummyData.cart[produk.id] = jumlah
            Toast.makeText(this, "${produk.nama} masuk ke order", Toast.LENGTH_SHORT).show()
            finish()
        }

        tvBack.setOnClickListener { finish() }
    }
}