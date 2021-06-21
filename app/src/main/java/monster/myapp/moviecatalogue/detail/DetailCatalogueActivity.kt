package monster.myapp.moviecatalogue.detail

import android.graphics.Bitmap
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.MotionEvent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory
import androidx.core.view.GestureDetectorCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.BitmapImageViewTarget
import com.skydoves.androidveil.VeilLayout
import monster.myapp.moviecatalogue.BuildConfig
import monster.myapp.moviecatalogue.R
import monster.myapp.moviecatalogue.core.data.Resource
import monster.myapp.moviecatalogue.core.domain.model.Catalogue
import monster.myapp.moviecatalogue.databinding.ActivityDetailMovieBinding
import monster.myapp.moviecatalogue.core.utils.AppUtils
import monster.myapp.moviecatalogue.core.utils.MyGestureListener
import monster.myapp.moviecatalogue.core.utils.SwipeGestureListener
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailCatalogueActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailMovieBinding
    private lateinit var catalogue: Catalogue

    private val detailCatalogueViewModel: DetailCatalogueViewModel by viewModel()
    private lateinit var veilLayout: VeilLayout
    private var menu: Menu? = null

    private lateinit var detector: GestureDetectorCompat
    private lateinit var listOfId: ArrayList<Int>
    private lateinit var type: String

    private var position = 0
    private var swipedRight = false
    private var swipedLeft = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val extras = intent.extras
        if (extras != null) {
            val id = extras.getInt(EXTRA_ID)
            listOfId = extras.getIntegerArrayList(EXTRA_LIST_ID) as ArrayList<Int>

            type = extras.getString(EXTRA_TYPE).toString()
            detailCatalogueViewModel.setSelectedCatalogue(id)
        }

        veilLayout = binding.veilLayout
        if (type == "movie") {
            detailCatalogueViewModel.movie.observe(this, { movie ->
                if (movie != null) {
                    when (movie) {
                        is Resource.Loading -> veilLayout.veil()
                        is Resource.Success -> if (movie.data != null) {
                            veilLayout.unVeil()
                            movie.data?.let {
                                this.catalogue = it
                            }
                            setObjectData()
                            setSwipeNextPrevState()
                        }
                        is Resource.Error -> {
                            veilLayout.unVeil()
                            Toast.makeText(this, movie.message, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            })
        } else if (type == "tvshow") {
            detailCatalogueViewModel.tvShow.observe(this, { tvshow ->
                if (tvshow != null) {
                    when (tvshow) {
                        is Resource.Loading -> veilLayout.veil()
                        is Resource.Success -> if (tvshow.data != null) {
                            veilLayout.unVeil()
                            tvshow.data?.let {
                                this.catalogue = it
                            }
                            setObjectData()
                            setSwipeNextPrevState()
                        }
                        is Resource.Error -> {
                            veilLayout.unVeil()
                            Toast.makeText(this, tvshow.message, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            })
        }

        detector = GestureDetectorCompat(this, MyGestureListener(object : SwipeGestureListener {
            override fun onSwipeRight() {
                if (swipedRight) detailCatalogueViewModel.setSelectedCatalogue(listOfId[position - 1])
                else Toast.makeText(
                    this@DetailCatalogueActivity, getString(R.string.msg_first), Toast.LENGTH_SHORT
                ).show()
            }

            override fun onLeftSwipe() {
                if (swipedLeft) detailCatalogueViewModel.setSelectedCatalogue(listOfId[position + 1])
                else Toast.makeText(
                    this@DetailCatalogueActivity, getString(R.string.msg_last), Toast.LENGTH_SHORT
                ).show()
            }

            override fun onSwipeTop() {}
            override fun onSwipeBottom() {}
        }))

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        detector.onTouchEvent(ev)
        return super.dispatchTouchEvent(ev)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_detail, menu)
        this.menu = menu
        if (type == "movie") {
            detailCatalogueViewModel.movie.observe(this, { movie ->
                if (movie != null) {
                    when (movie) {
                        is Resource.Loading -> veilLayout.veil()
                        is Resource.Success -> if (movie.data != null) {
                            veilLayout.unVeil()
                            movie.data?.favored?.let {
                                setFavoriteState(it)
                            }
                        }
                        is Resource.Error -> {
                            veilLayout.unVeil()
                            Toast.makeText(this, movie.message, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            })
        } else if (type == "tvshow") {
            detailCatalogueViewModel.tvShow.observe(this, { tvShow ->
                if (tvShow != null) {
                    when (tvShow) {
                        is Resource.Loading -> veilLayout.veil()
                        is Resource.Success -> if (tvShow.data != null) {
                            veilLayout.unVeil()
                            tvShow.data?.favored?.let {
                                setFavoriteState(it)
                            }
                        }
                        is Resource.Error -> {
                            veilLayout.unVeil()
                            Toast.makeText(this, tvShow.message, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            })
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_favorite) {
            if (type == "movie") detailCatalogueViewModel.setFavoredMovie()
            else if (type == "tvshow") detailCatalogueViewModel.setFavoredTvShow()
        } else if (item.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setObjectData() {
        binding.apply {
            val backdropUrl: String =
                if (catalogue.backdrop_path == "" || catalogue.backdrop_path == "null") "https://www.themoviedb.org/assets/2/apple-touch-icon-cfba7699efe7a742de25c28e08c38525f19381d31087c69e89d6bcb8e3c0ddfa.png"
                else BuildConfig.IMG_URL + catalogue.backdrop_path

            Glide.with(this@DetailCatalogueActivity)
                .load(backdropUrl)
                .placeholder(R.drawable.ic_image_search)
                .error(R.drawable.ic_broken_image)
                .into(imgBackdrop)

            val options: RequestOptions = RequestOptions()
                .fitCenter()
                .placeholder(R.drawable.ic_image_search)
                .error(R.drawable.ic_broken_image)
                .priority(Priority.HIGH)

            val posterUrl: String =
                if (catalogue.poster_path == "" || catalogue.poster_path == "null") "https://www.themoviedb.org/assets/2/apple-touch-icon-cfba7699efe7a742de25c28e08c38525f19381d31087c69e89d6bcb8e3c0ddfa.png"
                else BuildConfig.IMG_URL + catalogue.poster_path

            Glide.with(this@DetailCatalogueActivity)
                .asBitmap()
                .apply(options)
                .load(posterUrl)
                .into(object : BitmapImageViewTarget(imgPoster) {
                    override fun setResource(resource: Bitmap?) {
                        val circularBitmapDrawable =
                            RoundedBitmapDrawableFactory.create(
                                this@DetailCatalogueActivity.resources,
                                resource
                            )
                        circularBitmapDrawable.isCircular = true
                        imgPoster.setImageDrawable(circularBitmapDrawable)
                    }
                })

            toolbarLayout.title = catalogue.title
            toolbarLayout.isSelected = true
            tvItemPopularity.text = catalogue.vote_average.toString()
            tvItemRelease.text = AppUtils.formatDate(catalogue.release_date)
            tvItemOverview.text = catalogue.overview
        }
    }

    private fun setFavoriteState(state: Boolean) {
        if (menu == null) return
        val menuItem = menu?.findItem(R.id.action_favorite)
        if (state) {
            menuItem?.icon = ContextCompat.getDrawable(this, R.drawable.ic_favored)
        } else {
            menuItem?.icon = ContextCompat.getDrawable(this, R.drawable.ic_favorite)
        }
    }

    private fun setSwipeNextPrevState() {
        for ((index, id) in listOfId.withIndex()) {
            if (id == catalogue.id) {
                position = index
            }
        }

        when (position) {
            0 -> {
                swipedRight = false
                swipedLeft = listOfId.size - 1 != 0
            }
            listOfId.size - 1 -> {
                swipedRight = true
                swipedLeft = false
            }
            else -> {
                swipedRight = true
                swipedLeft = true
            }
        }
    }

    companion object {
        const val EXTRA_ID = "extra_id"
        const val EXTRA_LIST_ID = "extra_list_id"
        const val EXTRA_TYPE = "extra_type"
    }
}