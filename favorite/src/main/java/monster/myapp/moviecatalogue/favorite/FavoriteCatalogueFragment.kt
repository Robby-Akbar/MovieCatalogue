package monster.myapp.moviecatalogue.favorite

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ShareCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import monster.myapp.moviecatalogue.R
import monster.myapp.moviecatalogue.catalogue.CatalogueViewModel
import monster.myapp.moviecatalogue.databinding.FragmentFavoriteBinding
import monster.myapp.moviecatalogue.core.ui.CatalogueAdapter
import monster.myapp.moviecatalogue.core.domain.model.Catalogue
import monster.myapp.moviecatalogue.core.ui.ItemCatalogueCallback
import monster.myapp.moviecatalogue.detail.DetailCatalogueActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoriteCatalogueFragment(private val type: String) : Fragment(), ItemCatalogueCallback {

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding
    private lateinit var listOfId: ArrayList<Int>

    private val catalogueViewModel: CatalogueViewModel by viewModel()
    private lateinit var catalogueAdapter: CatalogueAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        itemTouchHelper.attachToRecyclerView(binding?.recyclerview)

        if (activity != null) {
            catalogueAdapter = CatalogueAdapter(this)
            binding?.let {
                with(it.recyclerview) {
                    layoutManager = LinearLayoutManager(context)
                    setHasFixedSize(true)
                    adapter = catalogueAdapter
                }
            }

            if (type == "movie"){
                catalogueViewModel.getFavoredMovies().observe(viewLifecycleOwner, { movies ->
                    catalogueAdapter.submitData(lifecycle, movies)
                    catalogueAdapter.notifyDataSetChanged()
                })
            } else if (type == "tvshow") {
                catalogueViewModel.getFavoredTvShows().observe(viewLifecycleOwner, { tvshows ->
                    catalogueAdapter.submitData(lifecycle, tvshows)
                    catalogueAdapter.notifyDataSetChanged()
                })
            }

            catalogueAdapter.addLoadStateListener { loadState ->
                if (loadState.source.refresh is LoadState.NotLoading && loadState.append.endOfPaginationReached && catalogueAdapter.itemCount < 1) {
                    binding?.let {
                        it.imgPlaceholder.visibility = View.VISIBLE
                        it.txtFavorite.visibility = View.VISIBLE
                    }
                } else {
                    binding?.let {
                        it.imgPlaceholder.visibility = View.GONE
                        it.txtFavorite.visibility = View.GONE
                    }
                }
            }

            viewLifecycleOwner.lifecycleScope.launch {
                catalogueAdapter.loadStateFlow
                    .collectLatest { listOfId = listOfMovieId(catalogueAdapter.snapshot().items) }
            }
        }
    }

    private fun listOfMovieId(catalogues: List<Catalogue>?): ArrayList<Int> {
        val listId = ArrayList<Int>()
        if (catalogues != null) {
            for (movie in catalogues) {
                listId.add(movie.id)
            }
        }
        return listId
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onShareClick(catalogue: Catalogue) {
        if (activity != null) {
            val mimeType = "text/plain"
            ShareCompat.IntentBuilder(requireActivity())
                .setType(mimeType)
                .setChooserTitle(getString(R.string.text_share))
                .setText(resources.getString(R.string.share_text, catalogue.title))
                .startChooser()
        }
    }

    override fun onItemClick(catalogue: Catalogue) {
        val intent = Intent(requireContext(), DetailCatalogueActivity::class.java)
        intent.putExtra(DetailCatalogueActivity.EXTRA_ID, catalogue.id)
        intent.putExtra(DetailCatalogueActivity.EXTRA_LIST_ID, listOfId)
        intent.putExtra(DetailCatalogueActivity.EXTRA_TYPE, type)
        requireActivity().startActivity(intent)
    }

    private val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.Callback() {
        override fun getMovementFlags(
            recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder
        ): Int = makeMovementFlags(0, ItemTouchHelper.RIGHT)

        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean = true

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            if (view != null) {
                val swipedPosition = viewHolder.bindingAdapterPosition
                val catalogue = catalogueAdapter.getSwipedData(swipedPosition)
                catalogue?.let {
                    if (type == "movie") catalogueViewModel.setFavoriteMovie(it, false)
                    else if (type == "tvshow") catalogueViewModel.setFavoriteTvShow(it, false)
                }
                val snackBar =
                    Snackbar.make(view as View, R.string.message_undo, Snackbar.LENGTH_LONG)
                snackBar.setAction(R.string.message_ok) { _ ->
                    catalogue?.let {
                        if (type == "movie") catalogueViewModel.setFavoriteMovie(it, true)
                        else if (type == "tvshow") catalogueViewModel.setFavoriteTvShow(it, true)
                    }
                }.setActionTextColor(ContextCompat.getColor(requireContext(), R.color.colorAccent))
                    .show()
            }
        }
    })

}