package wolt.assignment.demo.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import wolt.assignment.demo.R
import wolt.assignment.demo.databinding.LayoutItemRestrorantBinding
import wolt.assignment.demo.models.RestaurantModel
import wolt.assignment.demo.utils.Commons.Companion.loadGlide

class RestaurantAdapter(
    private val context: Context,
    val onFavoriteClick: (position: Int, isFavorite: Boolean) -> Unit
) : RecyclerView.Adapter<RestaurantAdapter.ViewHolder>() {

    private var restaurantList: MutableList<RestaurantModel.Section.Item> = mutableListOf()

    fun submitData(updatedRestaurant: MutableList<RestaurantModel.Section.Item>) {
        val diffCallback = object : DiffUtil.Callback() {
            override fun getOldListSize() = restaurantList.size
            override fun getNewListSize() = updatedRestaurant.size

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return restaurantList[oldItemPosition].venue == updatedRestaurant[newItemPosition].venue
            }

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return restaurantList[oldItemPosition] == updatedRestaurant[newItemPosition]
            }
        }
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        restaurantList.clear()
        restaurantList.addAll(updatedRestaurant)
        diffResult.dispatchUpdatesTo(this)
    }

    inner class ViewHolder(val binding: LayoutItemRestrorantBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = LayoutItemRestrorantBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    @SuppressLint("SetTextI18n", "UseCompatLoadingForDrawables")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.apply {
            val venue = restaurantList[position].venue
            resImage.loadGlide(
                context,
                venue?.brand_image?.url ?: "",
                context.getDrawable(R.drawable.restaurant_placeholder)
            )
            tvTitle.text = venue?.name ?: ""
            tvSubtitle.text = venue?.short_description ?: ""

            val venueId = venue?.id ?: return@apply
            val isFavorite = context.getSharedPreferences("Favorites", Context.MODE_PRIVATE).getBoolean(venueId, false)
            val favoriteIconRes = if (isFavorite) R.drawable.ic_favorite_filled else R.drawable.ic_favorite_unfilled
            favoriteIcon.setImageResource(favoriteIconRes)

            favoriteIcon.setOnClickListener {
                val newFavoriteState = !isFavorite
                onFavoriteClick(position, newFavoriteState)
                favoriteIcon.setImageResource(
                    if (newFavoriteState) R.drawable.ic_favorite_filled else R.drawable.ic_favorite_unfilled
                )
            }
        }
    }

    override fun getItemCount(): Int = restaurantList.size
}



