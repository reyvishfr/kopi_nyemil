package lat.pam.kopidannyemil

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var tvHalo: TextView
    private lateinit var navOrder: TextView
    private lateinit var etCari: EditText
    private lateinit var tvKosong: TextView
    private lateinit var tabSemua: TextView
    private lateinit var tabMinuman: TextView
    private lateinit var tabMakanan: TextView
    private lateinit var barTotal: LinearLayout
    private lateinit var tvBarJumlah: TextView
    private lateinit var tvBarTotal: TextView
    private lateinit var adapter: ProdukAdapter

    private var kategoriAktif = "Semua"
    private var kataKunci = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvHalo = findViewById(R.id.tvHalo)
        navOrder = findViewById(R.id.navOrder)
        etCari = findViewById(R.id.etCari)
        tvKosong = findViewById(R.id.tvKosong)
        tabSemua = findViewById(R.id.tabSemua)
        tabMinuman = findViewById(R.id.tabMinuman)
        tabMakanan = findViewById(R.id.tabMakanan)
        barTotal = findViewById(R.id.barTotal)
        tvBarJumlah = findViewById(R.id.tvBarJumlah)
        tvBarTotal = findViewById(R.id.tvBarTotal)
        val tvBarLihat = findViewById<TextView>(R.id.tvBarLihat)
        val navHome = findViewById<TextView>(R.id.navHome)
        val navProfile = findViewById<TextView>(R.id.navProfile)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)

        // RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = ProdukAdapter(DummyData.produkList) { updateKeranjang() }
        recyclerView.adapter = adapter


        etCari.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                kataKunci = s.toString().trim()
                terapkanFilter()
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        // pilihan kategori
        tabSemua.setOnClickListener { pilihKategori("Semua") }
        tabMinuman.setOnClickListener { pilihKategori("Minuman") }
        tabMakanan.setOnClickListener { pilihKategori("Makanan") }

        // tab aktif = Home
        navHome.setBackgroundResource(R.drawable.bg_nav_active)
        navHome.setTextColor(Color.WHITE)

        // ke halaman order, lewat tab Order maupun bar total
        navOrder.setOnClickListener { bukaOrder() }
        tvBarLihat.setOnClickListener { bukaOrder() }
        barTotal.setOnClickListener { bukaOrder() }

        navProfile.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        tvHalo.text = "Halo ${DummyData.currentUser?.nama ?: ""},"
        adapter.notifyDataSetChanged()
        updateKeranjang()
    }

    private fun bukaOrder() {
        if (DummyData.totalItem() == 0) {
            Toast.makeText(this, "Pilih menu dulu sebelum order", Toast.LENGTH_SHORT).show()
        } else {
            startActivity(Intent(this, AlamatActivity::class.java))
        }
    }

    // gabungan filter kategori + kata kunci pencarian
    private fun terapkanFilter() {
        val hasil = DummyData.produkList.filter {
            val cocokKategori = kategoriAktif == "Semua" || it.kategori == kategoriAktif
            val cocokKata = it.nama.contains(kataKunci, ignoreCase = true) ||
                    it.deskripsi.contains(kataKunci, ignoreCase = true)
            cocokKategori && cocokKata
        }
        adapter.updateData(hasil)
        tvKosong.visibility = if (hasil.isEmpty()) View.VISIBLE else View.GONE
    }

    private fun pilihKategori(kategori: String) {
        kategoriAktif = kategori
        aturTab(tabSemua, kategori == "Semua")
        aturTab(tabMinuman, kategori == "Minuman")
        aturTab(tabMakanan, kategori == "Makanan")
        terapkanFilter()
    }

    private fun aturTab(tab: TextView, aktif: Boolean) {
        if (aktif) {
            tab.setBackgroundResource(R.drawable.bg_chip_aktif)
            tab.setTextColor(Color.WHITE)
        } else {
            tab.setBackgroundResource(R.drawable.bg_chip_mati)
            tab.setTextColor(ContextCompat.getColor(this, R.color.dark))
        }
    }

    private fun updateKeranjang() {
        val total = DummyData.totalItem()
        navOrder.text = if (total > 0) "Order ($total)" else "Order"

        if (total > 0) {
            barTotal.visibility = View.VISIBLE
            tvBarJumlah.text = "$total item"
            tvBarTotal.text = DummyData.rupiah(DummyData.totalHarga())
        } else {
            barTotal.visibility = View.GONE
        }
    }
}