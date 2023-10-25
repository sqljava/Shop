package uz.ictschool.shop.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.ictschool.shop.R
import uz.ictschool.shop.databinding.ItemCategoryBinding

class CategoryAdapter(
    val context: Context,
    val categories: List<String>,
    val categoryClicked: CategoryClicked
): RecyclerView.Adapter<CategoryAdapter.CategoryHolder>() {

    var currentIndex = 0

    class CategoryHolder(binding: ItemCategoryBinding)
        : RecyclerView.ViewHolder(binding.root){
        val categoryText = binding.categoryName
        val main = binding.categoryMain
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryHolder {
        return CategoryHolder(ItemCategoryBinding
            .inflate(LayoutInflater.from(parent.context),
                parent, false))
    }

    override fun getItemCount(): Int {
        return categories.size + 1
    }

    override fun onBindViewHolder(holder: CategoryHolder, position: Int) {

        //var thisCategory = categories[position]

        if (position == 0){
            holder.categoryText.text = "All"
        }else{
            holder.categoryText.text = categories[position-1].capitalize()
        }

        if (position == currentIndex){
            holder.main.setCardBackgroundColor(context.getColor(R.color.orange))
            holder.categoryText.setTextColor(context.getColor(R.color.white))
        }else{
            holder.main.setCardBackgroundColor(context.getColor(R.color.white))
            holder.categoryText.setTextColor(context.getColor(R.color.orange))
        }

        holder.main.setOnClickListener {
            if (position !=currentIndex){
                notifyItemChanged(currentIndex)
                currentIndex = position
                notifyItemChanged(currentIndex)

                if (currentIndex==0){
                    categoryClicked.onClicked("")
                }else{
                    categoryClicked.onClicked(categories[position-1])
                }
            }

        }

    }

    interface CategoryClicked{
        fun onClicked(category: String)
    }
}