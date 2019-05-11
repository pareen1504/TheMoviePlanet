package com.example.moviedb.ui.screen.moviedetail

import android.os.Bundle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.moviedb.R
import com.example.moviedb.data.model.Movie
import com.example.moviedb.databinding.FragmentMovieDetailBinding
import com.example.moviedb.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_movie_detail.*
import org.koin.androidx.viewmodel.ext.viewModel

class MovieDetailFragment : BaseFragment<FragmentMovieDetailBinding, MovieDetailViewModel>() {

    companion object {
        const val MOVIE = "MOVIE"
        const val TAG = "MovieDetailFragment"

        fun newInstance(movie: Movie) = MovieDetailFragment().apply {
            arguments = Bundle().apply {
                putParcelable(MOVIE, movie)
            }
        }
    }

    override val layoutId: Int = R.layout.fragment_movie_detail

    override val viewModel: MovieDetailViewModel by viewModel()

    val args: MovieDetailFragmentArgs by navArgs()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        button_favorite?.setOnClickListener {
            viewModel.favoriteMovie()
        }
        image_back?.setOnClickListener {
            findNavController().navigateUp()
        }

        viewModel.apply {
            args.movie.let {
                movie.value = it
                checkFavorite(it.id)
            }
        }
    }

}