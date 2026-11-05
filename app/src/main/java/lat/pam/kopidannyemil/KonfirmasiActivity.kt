package lat.pam.kopidannyemil

import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class KonfirmasiActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_konfirmasi)

        val tvHalo = findViewById<TextView>(R.id.tvHalo)
        val tvNoPesanan = findViewById<TextView>(R.id.tvNoPesanan)
        val layoutRincian = findViewById<LinearLayout>(R.id.layoutRincian)
        val tvTotal = findViewById<TextView>(R.id.tvTotal)
        val btnOke = findViewById<Button>(R.id.btnOke)

        val nama = intent.getStringExtra("nama") ?: DummyData.currentUser?.nama ?: ""
        tvHalo.text = "Halo $nama,"

        // nomor pesanan dummy
        tvNoPesanan.text = "KN-" + (1000..9999).random()

        // daftar menu yang dipesan
        for (p in DummyData.produkList) {
            val qty = DummyData.cart[p.id] ?: 0
            if (qty > 0) {
                layoutRincian.addView(
                    buatBaris("${qty}x ${p.nama}", DummyData.rupiah(p.harga * qty))
                )
            }
        }
        tvTotal.text = DummyData.rupiah(DummyData.totalHarga())
        val tvPembayaran = findViewById<TextView>(R.id.tvPembayaran)
        tvPembayaran.text = intent.getStringExtra("pembayaran") ?: "COD"

        btnOke.setOnClickListener {
            DummyData.cart.clear() // pesanan selesai, keranjang dikosongkan
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
        }
    }

    private fun buatBaris(kiri: String, kanan: String): LinearLayout {
        val warna = ContextCompat.getColor(this, R.color.dark)
        val jarak = (6 * resources.displayMetrics.density).toInt()

        val baris = LinearLayout(this)
        baris.orientation = LinearLayout.HORIZONTAL
        baris.setPadding(0, 0, 0, jarak)

        val tvKiri = TextView(this)
        tvKiri.text = kiri
        tvKiri.textSize = 14f
        tvKiri.setTextColor(warna)
        tvKiri.layoutParams = LinearLayout.LayoutParams(
            0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f
        )

        val tvKanan = TextView(this)
        tvKanan.text = kanan
        tvKanan.textSize = 14f
        tvKanan.setTextColor(warna)

        baris.addView(tvKiri)
        baris.addView(tvKanan)
        return baris
    }
}