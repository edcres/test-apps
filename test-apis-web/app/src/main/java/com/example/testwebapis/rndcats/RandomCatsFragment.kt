package com.example.testwebapis.rndcats

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.testwebapis.R
import com.example.testwebapis.databinding.FragmentRandomCatsBinding

/**
 * Simple API use, app displays pictures of cats in a recyclerview
 *
 * loading images on a background thread and caching loaded images
 *
 */

/** Notes:
 *
 * A JSON object is a collection of key-value pairs.
 * Retrofit ultimately creates most of the network layer for you, including critical details such as running the requests on background threads.
 */

class RandomCatsFragment : Fragment() {

    private var binding: FragmentRandomCatsBinding? = null
    private lateinit var viewModel: RandomCatsViewModel
    private lateinit var recyclerAdapter: CatPhotosAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentBinding = FragmentRandomCatsBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        recyclerAdapter = CatPhotosAdapter()
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            lifecycleOwner = viewLifecycleOwner
            imagesRecycler.adapter = recyclerAdapter
        }
        viewModel = ViewModelProvider(this).get(RandomCatsViewModel::class.java)

        viewModel.status.observe(viewLifecycleOwner) { status ->
            binding?.apply {
                when (status) {
                    CatsApiStatus.LOADING -> {
                        statusImg.visibility = View.VISIBLE
                        statusImg.setImageResource(R.drawable.loading_animation)
                    }
                    CatsApiStatus.ERROR -> {
                        statusImg.visibility = View.VISIBLE
                        statusImg.setImageResource(R.drawable.ic_connection_error)
                    }
                    CatsApiStatus.DONE -> {
                        statusImg.visibility = View.GONE
                    }
                }
            }
        }

        viewModel.photos.observe(viewLifecycleOwner) { photos ->
            recyclerAdapter.submitList(photos)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

//    private fun thingsToDo() {
//        val thisCatPhoto = CatPhoto("4567", "drtfyghb")
//        thisCatPhoto.imgSrcUrl.let { imgUrl ->
//            // convert the URL string (from the XML) to a Uri object
//            val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
//            // load the image from the Uri object into the ImageView
//            binding?.apply {
//                Glide.with(singleCatImg.context)
//                    .load(imgUri)
//                    .apply(RequestOptions()
//                        .placeholder(R.drawable.loading_animation)
//                        .error(R.drawable.ic_baseline_broken_image_24))
//                    .into(singleCatImg)
//            }
//        }
//    }
}