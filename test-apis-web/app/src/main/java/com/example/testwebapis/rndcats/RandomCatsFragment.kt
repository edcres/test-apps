package com.example.testwebapis.rndcats

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.testwebapis.R
import com.example.testwebapis.databinding.FragmentRandomCatsBinding
import com.example.testwebapis.rndcats.network.CatPhoto

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
    private val viewModel: RandomCatsViewModel by lazy {
        ViewModelProvider(this).get(RandomCatsViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentBinding = FragmentRandomCatsBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            lifecycleOwner = viewLifecycleOwner
            imagesRecycler.adapter = CatPhotosAdapter()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun thingsToDo() {
        // todo: bind image (from binding adapters)
        val thisCatPhoto = CatPhoto("4567", "drtfyghb")
        thisCatPhoto.imgSrcUrl.let { imgUrl ->
            // convert the URL string (from the XML) to a Uri object
            val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
            // load the image from the Uri object into the ImageView
            binding?.apply {
                Glide.with(singleCatImg.context)
                    .load(imgUri)
                    .apply(RequestOptions()
                        .placeholder(R.drawable.loading_animation)
                        .error(R.drawable.ic_baseline_broken_image_24))
                    .into(singleCatImg)
            }
        }
    }
}