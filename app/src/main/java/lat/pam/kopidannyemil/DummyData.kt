package lat.pam.kopidannyemil

import java.text.NumberFormat
import java.util.Locale

object DummyData {

    // akun bawaan buat tes login: demo / 123456
    val users = mutableListOf(User("Demo User", "demo", "123456"))
    var currentUser: User? = null

    val produkList = listOf(
        Produk(1, "Palm Sugar Latte", "Espresso, susu, gula aren", 22000, R.drawable.kopi_susu, "Minuman"),
        Produk(2, "Cappuccino", "Espresso, foam susu lembut", 25000, R.drawable.cappuccino, "Minuman"),
        Produk(3, "Matcha Latte", "Matcha Jepang, susu segar", 27000, R.drawable.matcha, "Minuman"),
        // sementara pakai foto yang sudah ada, ganti kalau sudah punya fotonya
        Produk(4, "Ice Chocolate", "Cokelat dingin, susu, es batu", 24000, R.drawable.ice_chocolate
            , "Minuman"),
        Produk(5, "Chocolate Doughnut", "Donat empuk, glaze cokelat", 12000, R.drawable.donat, "Makanan"),
        Produk(6, "Sourdough Loaf", "Roti fermentasi alami", 35000, R.drawable.sourdough, "Makanan"),
        // sementara pakai foto yang sudah ada, ganti kalau sudah punya fotonya
        Produk(7, "Croissant Butter", "Croissant renyah, butter premium", 20000, R.drawable.croissant, "Makanan"),
        Produk(8, "Cookies Chocolate", "Cookies chewy, choco chips", 15000, R.drawable.cookies, "Makanan")
    )

    // id produk -> jumlah
    val cart = mutableMapOf<Int, Int>()

    fun totalItem(): Int = cart.values.sum()

    fun totalHarga(): Int = produkList.sumOf { it.harga * (cart[it.id] ?: 0) }

    fun rupiah(angka: Int): String =
        "Rp " + NumberFormat.getInstance(Locale("id", "ID")).format(angka)
}