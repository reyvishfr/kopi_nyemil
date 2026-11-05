package lat.pam.kopidannyemil

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class AlamatActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alamat)

        val tvRingkasan = findViewById<TextView>(R.id.tvRingkasan)
        val etNama = findViewById<EditText>(R.id.etNama)
        val etAlamat = findViewById<EditText>(R.id.etAlamat)
        val etPatokan = findViewById<EditText>(R.id.etPatokan)
        val rgBayar = findViewById<RadioGroup>(R.id.rgBayar)
        val btnOrder = findViewById<Button>(R.id.btnOrder)
        val tvKembali = findViewById<TextView>(R.id.tvKembali)

        // ringkasan pesanan
        val sb = StringBuilder()
        for (p in DummyData.produkList) {
            val qty = DummyData.cart[p.id] ?: 0
            if (qty > 0) {
                sb.append("${qty}x ${p.nama} - ${DummyData.rupiah(p.harga * qty)}\n")
            }
        }
        sb.append("\nTotal: ${DummyData.rupiah(DummyData.totalHarga())}")
        tvRingkasan.text = sb.toString()

        etNama.setText(DummyData.currentUser?.nama ?: "")

        btnOrder.setOnClickListener {
            val nama = etNama.text.toString().trim()
            val alamat = etAlamat.text.toString().trim()

            if (nama.isEmpty() || alamat.isEmpty()) {
                Toast.makeText(this, "Nama lengkap dan alamat wajib diisi", Toast.LENGTH_SHORT).show()
            } else {
                // baca metode pembayaran yang dipilih
                val pembayaran = when (rgBayar.checkedRadioButtonId) {
                    R.id.rbTransfer -> "Transfer Bank"
                    R.id.rbEwallet -> "E-Wallet"
                    else -> "COD"
                }

                val intent = Intent(this, KonfirmasiActivity::class.java)
                intent.putExtra("nama", nama)
                intent.putExtra("pembayaran", pembayaran)
                startActivity(intent)
            }
        }

        tvKembali.setOnClickListener { finish() }
    }
}