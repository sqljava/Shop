package uz.ictschool.shop.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import uz.ictschool.shop.databinding.ItemProductBinding
import uz.ictschool.shop.model.Product

class ProductAdapter(var list: List<Product>):RecyclerView.Adapter<ProductAdapter.ProductHolder>() {
    class ProductHolder(binding: ItemProductBinding):RecyclerView.ViewHolder(binding.root){
        var img = binding.imgProductItem
        var title = binding.titleProductItem
        var price = binding.priceProductItem
        var rating = binding.ratingProductItem
        var brand = binding.brandProductItem

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductHolder {
        return ProductHolder(ItemProductBinding
            .inflate(LayoutInflater.from(parent.context),
                parent,false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ProductHolder, position: Int) {
        val product = list[position]

        holder.title.text = product.title
        holder.price.text = product.price.toString() + " $"
        holder.brand.text = product.brand
        holder.rating.text = product.rating.toString()

        holder.img.load(product.images[0])

    }

    interface ProductClicked{
        fun onClicked(product: Product)
    }
}