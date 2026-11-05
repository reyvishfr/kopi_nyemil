package lat.pam.kopidannyemil

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ProdukAdapter(
    private var mList: List<Produk>,
    private val onCartChanged: () -> Unit
) : RecyclerView.Adapter<ProdukAdapter.ViewHolder>() {

    // dipanggil saat kategori diganti
    fun updateData(listBaru: List<Produk>) {
        mList = listBaru
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_produk, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val produk = mList[position]
        val qty = DummyData.cart[produk.id] ?: 0

        holder.imgFoto.setImageResource(produk.gambar)
        holder.tvNama.text = produk.nama
        holder.tvDeskripsi.text = "• " + produk.deskripsi
        holder.tvHarga.text = DummyData.rupiah(produk.harga)
        holder.tvQty.text = qty.toString()

        // klik kartu -> buka halaman detail (Explicit Intent + data)
        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra("id", produk.id)
            context.startActivity(intent)
        }

        holder.tvPlus.setOnClickListener {
            DummyData.cart[produk.id] = (DummyData.cart[produk.id] ?: 0) + 1
            notifyItemChanged(holder.bindingAdapterPosition)
            onCartChanged()
        }

        holder.tvMinus.setOnClickListener {
            val sekarang = DummyData.cart[produk.id] ?: 0
            if (sekarang > 0) {
                DummyData.cart[produk.id] = sekarang - 1
                notifyItemChanged(holder.bindingAdapterPosition)
                onCartChanged()
            }
        }
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgFoto: ImageView = itemView.findViewById(R.id.imgFoto)
        val tvNama: TextView = itemView.findViewById(R.id.tvNama)
        val tvDeskripsi: TextView = itemView.findViewById(R.id.tvDeskripsi)
        val tvHarga: TextView = itemView.findViewById(R.id.tvHarga)
        val tvQty: TextView = itemView.findViewById(R.id.tvQty)
        val tvMinus: TextView = itemView.findViewById(R.id.tvMinus)
        val tvPlus: TextView = itemView.findViewById(R.id.tvPlus)
    }
}