package uz.ictschool.shop.adapters

import android.app.ActionBar.LayoutParams
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.updateLayoutParams
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import coil.load
import uz.ictschool.shop.databinding.ItemImageBinding

class ImageAdapter(val photos: List<String>, var viewPager2: ViewPager2,
    var constraintLayout: ConstraintLayout) : RecyclerView.Adapter<ImageAdapter.ImageHolder>() {

    class ImageHolder(binding: ItemImageBinding): RecyclerView.ViewHolder(binding.root){
        var main = binding.itemImageMain
        var img = binding.itemImage
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageHolder {
        return ImageHolder(ItemImageBinding
            .inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return photos.size
    }

    override fun onBindViewHolder(holder: ImageHolder, position: Int) {
        holder.img.load(photos[position])

        holder.main.setOnClickListener {
            viewPager2.updateLayoutParams {
                height =LayoutParams.MATCH_PARENT
            }
            constraintLayout.setBackgroundColor(Color.BLACK)
        }

    }
}