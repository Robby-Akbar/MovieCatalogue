package monster.myapp.moviecatalogue.core.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import monster.myapp.moviecatalogue.core.BuildConfig
import monster.myapp.moviecatalogue.core.R
import monster.myapp.moviecatalogue.core.databinding.ItemRowCatalogueBinding
import monster.myapp.moviecatalogue.core.domain.model.Catalogue
import monster.myapp.moviecatalogue.core.utils.AppUtils

/**
 * Created by robby on 06/05/21.
 */
class CatalogueAdapter(private val callback: ItemCatalogueCallback) :
    PagingDataAdapter<Catalogue, CatalogueAdapter.ListViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Catalogue>() {
            override fun areItemsTheSame(oldItem: Catalogue, newItem: Catalogue): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Catalogue, newItem: Catalogue): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val itemsMovieBinding = ItemRowCatalogueBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ListViewHolder(itemsMovieBinding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    fun getSwipedData(swipedPosition: Int): Catalogue? = getItem(swipedPosition)

    inner class ListViewHolder(private val binding: ItemRowCatalogueBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(catalogue: Catalogue) {
            with(binding) {
                tvTitle.text = catalogue.title
                tvTitle.isSelected = true
                tvDescription.text = catalogue.overview
                tvRelease.text = AppUtils.formatDate(catalogue.release_date)
                btnShare.setOnClickListener { callback.onShareClick(catalogue) }
                itemView.setOnClickListener { callback.onItemClick(catalogue) }

                val imgUrl: String =
                    if (catalogue.poster_path == "" || catalogue.poster_path == "null") "https://www.themoviedb.org/assets/2/apple-touch-icon-cfba7699efe7a742de25c28e08c38525f19381d31087c69e89d6bcb8e3c0ddfa.png"
                    else BuildConfig.IMG_URL + catalogue.poster_path

                Glide.with(itemView.context)
                    .load(imgUrl)
                    .apply(
                        RequestOptions.placeholderOf(R.drawable.ic_image_search)
                            .error(R.drawable.ic_broken_image)
                    )
                    .into(imgPoster)
            }
        }
    }
}