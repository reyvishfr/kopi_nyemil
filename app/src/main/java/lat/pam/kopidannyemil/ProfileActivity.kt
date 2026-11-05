package lat.pam.kopidannyemil

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val tvHalo = findViewById<TextView>(R.id.tvHalo)
        val etNama = findViewById<EditText>(R.id.etNama)
        val btnSave = findViewById<Button>(R.id.btnSave)
        val tvLogout = findViewById<TextView>(R.id.tvLogout)
        val navHome = findViewById<TextView>(R.id.navHome)
        val navOrder = findViewById<TextView>(R.id.navOrder)
        val navProfile = findViewById<TextView>(R.id.navProfile)

        val user = DummyData.currentUser
        tvHalo.text = "Halo ${user?.nama ?: ""},"
        etNama.setText(user?.nama ?: "")
        val tvInisial = findViewById<TextView>(R.id.tvInisial)
        tvInisial.text = (user?.nama?.firstOrNull() ?: 'K').uppercase()

        // tab aktif = Profile
        navProfile.setBackgroundResource(R.drawable.bg_nav_active)
        navProfile.setTextColor(Color.WHITE)

        btnSave.setOnClickListener {
            val namaBaru = etNama.text.toString().trim()
            if (namaBaru.isEmpty()) {
                Toast.makeText(this, "Nama tidak boleh kosong", Toast.LENGTH_SHORT).show()
            } else {
                DummyData.currentUser?.nama = namaBaru
                Toast.makeText(this, "Profile tersimpan", Toast.LENGTH_SHORT).show()
                finish() // balik ke Home
            }
        }

        tvLogout.setOnClickListener {
            DummyData.currentUser = null
            DummyData.cart.clear()
            val intent = Intent(this, SignUpActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

        navHome.setOnClickListener { finish() }

        navOrder.setOnClickListener {
            if (DummyData.totalItem() == 0) {
                Toast.makeText(this, "Pilih menu dulu sebelum order", Toast.LENGTH_SHORT).show()
            } else {
                startActivity(Intent(this, AlamatActivity::class.java))
            }
        }
    }
}