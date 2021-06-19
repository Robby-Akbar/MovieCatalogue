package monster.myapp.moviecatalogue.catalogue

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.*
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.app.ShareCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import monster.myapp.moviecatalogue.R
import monster.myapp.moviecatalogue.core.data.Resource
import monster.myapp.moviecatalogue.core.domain.model.Catalogue
import monster.myapp.moviecatalogue.core.ui.ItemCatalogueCallback
import monster.myapp.moviecatalogue.core.ui.CatalogueAdapter
import monster.myapp.moviecatalogue.databinding.FragmentCatalogueBinding
import monster.myapp.moviecatalogue.detail.DetailCatalogueActivity
import monster.myapp.moviecatalogue.main.MainActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class CatalogueFragment : Fragment(), ItemCatalogueCallback {

    private var _binding: FragmentCatalogueBinding? = null
    private val binding get() = _binding
    private lateinit var listOfId: ArrayList<Int>
    private lateinit var type: String

    private val catalogueViewModel: CatalogueViewModel by viewModel()
    private var initial = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCatalogueBinding.inflate(inflater, container, false)
        val view = binding?.root

        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {
            val safeArgs: CatalogueFragmentArgs by navArgs()
            type = safeArgs.type

            val catalogueAdapter = CatalogueAdapter(this)
            binding?.apply {
                with(recyclerview) {
                    layoutManager = LinearLayoutManager(context)
                    setHasFixedSize(true)
                    adapter = catalogueAdapter
                }
                swipeRefreshLayout.setColorSchemeColors(
                    Color.RED, Color.GREEN, Color.BLUE, Color.CYAN
                )
                swipeRefreshLayout.setOnRefreshListener {
                    catalogueViewModel.isRefresh.value = true
                }
            }

            if (type == "movie") {
                catalogueViewModel.movies.observe(viewLifecycleOwner, { movies ->
                    if (movies != null) {
                        binding?.apply {
                            when (movies) {
                                is Resource.Loading -> progressBar.visibility = View.VISIBLE
                                is Resource.Success -> {
                                    progressBar.visibility = View.GONE
                                    swipeRefreshLayout.isRefreshing = false
                                    movies.data?.let {
                                        catalogueAdapter.submitData(lifecycle, it)
                                    }
                                    catalogueAdapter.notifyDataSetChanged()
                                }
                                is Resource.Error -> {
                                    whenError()
                                    Toast.makeText(context, movies.message, Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    }
                })
            } else if (type == "tvshow") {
                catalogueViewModel.tvShows.observe(viewLifecycleOwner, { tvShows ->
                    if (tvShows != null) {
                        binding?.apply {
                            when (tvShows) {
                                is Resource.Loading -> progressBar.visibility = View.VISIBLE
                                is Resource.Success -> {
                                    progressBar.visibility = View.GONE
                                    swipeRefreshLayout.isRefreshing = false
                                    tvShows.data?.let {
                                        catalogueAdapter.submitData(lifecycle, it)
                                    }
                                    catalogueAdapter.notifyDataSetChanged()
                                }
                                is Resource.Error -> {
                                    whenError()
                                    Toast.makeText(context, tvShows.message, Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    }
                })
            }

            catalogueAdapter.addLoadStateListener { loadState ->
                if (loadState.source.refresh is LoadState.NotLoading && loadState.append.endOfPaginationReached && catalogueAdapter.itemCount < 1) {
                    if (initial) {
                        catalogueViewModel.isRefresh.value = true
                    } else {
                        whenNoData()
                    }
                } else {
                    binding?.apply {
                        imgPlaceholder.visibility = View.GONE
                        txtConnection.visibility = View.GONE
                    }
                }
            }

            viewLifecycleOwner.lifecycleScope.launch {
                catalogueAdapter.loadStateFlow
                    .collectLatest { listOfId = listOfCatalogueId(catalogueAdapter.snapshot().items) }
            }
        }
    }

    private fun whenNoData() {
        binding?.apply {
            imgPlaceholder.setImageDrawable(
                ContextCompat.getDrawable(
                    requireActivity(),
                    R.drawable.img_not_found
                )
            )
            imgPlaceholder.visibility = View.VISIBLE
            txtConnection.text = requireActivity().getString(R.string.data_not_found)
            txtConnection.visibility = View.VISIBLE
        }
    }

    private fun whenError() {
        binding?.apply {
            progressBar.visibility = View.GONE
            swipeRefreshLayout.isRefreshing = false
            imgPlaceholder.visibility = View.VISIBLE
            txtConnection.visibility = View.VISIBLE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.option_menu, menu)

        val searchManager =
            requireActivity().getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search).actionView as SearchView

        val searchEditFrame: LinearLayout =
            searchView.findViewById<View>(R.id.search_edit_frame) as LinearLayout

        val params = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )
        params.marginStart = 0
        params.marginEnd = 0
        searchEditFrame.layoutParams = params

        searchView.setSearchableInfo(searchManager.getSearchableInfo(requireActivity().componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                catalogueViewModel.query.value = query
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                initial = false
                catalogueViewModel.query.value = newText
                return true
            }
        })

        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun listOfCatalogueId(catalogues: List<Catalogue>?): ArrayList<Int> {
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

}