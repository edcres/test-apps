package com.example.testhardwareplus

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.lifecycle.lifecycleScope
import com.example.testhardwareplus.databinding.FragmentInternalStoreBinding
import com.example.testhardwareplus.internalstore.InternalStoragePhoto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import java.lang.Exception
import java.util.*

// https://www.youtube.com/watch?v=EeLz1DPMsW8&list=PLtO7tIzYX2lUaX0Isy0zbjg0A1Ci--Q3l&index=3&t=602s

/**
 * take a picture, save it to internal storage, display it from internal storage
 *
 * To take a picture, use activityResultContract() (replaces the deprecated startActivityForResult())
 *      - might need the 2 dependencies:
 *          - implementation "androidx.activity:activity-ktx:1.2.3"
 *          - implementation "androidx.fragment:fragment-ktx:1.3.4"
 */

// The number of stored pics is only updated when 'loadPhotosFromInternalStorageIntoRecyclerView()' is called

class InternalStoreFragment : Fragment() {

    private var binding: FragmentInternalStoreBinding? = null
    private var selectedPhoto: InternalStoragePhoto? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentBinding = FragmentInternalStoreBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadPhotosFromInternalStorageIntoRecyclerView()

        // TO TAKE A PHOTO //
        val takePhoto = registerForActivityResult(ActivityResultContracts.TakePicturePreview()) {
            // what to do when a photo is taken
            // random file name , it is the bitmap
            val fileName = UUID.randomUUID().toString() + ".jpg" // todo: use this as a property in an entity
            val isSavedSuccessfully = savePhotoToInternalStorage(fileName, it)
            if(isSavedSuccessfully) {
                loadPhotosFromInternalStorageIntoRecyclerView()
                Toast.makeText(requireContext(), "Photo saved successfully", Toast.LENGTH_SHORT)
                    .show()
            } else{
                Toast.makeText(requireContext(), "Failed to save photo", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        binding?.apply {
            deleteBtn.setOnClickListener {
                if(selectedPhoto != null) {
                    val isDeletedSuccessfully = deletePhotoFromInternalStorage(selectedPhoto!!.name)
                    if(isDeletedSuccessfully) {
                        loadPhotosFromInternalStorageIntoRecyclerView()
                        Toast.makeText(requireContext(), "Photo successfully deleted", Toast.LENGTH_SHORT)
                            .show()
                    } else{
                        Toast.makeText(requireContext(), "Failed to delete photo", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
            takePhotoBtn.setOnClickListener {
                takePhoto.launch()
            }
        }
        // TO TAKE A PHOTO //
    }

    // TO QUERY PHOTOS FROM STORAGE //
    private fun loadPhotosFromInternalStorageIntoRecyclerView() {
        // instead of the photos to recyclerView, just do the first photo to an imageView.
        lifecycleScope.launch {
            val photos = loadPhotosFromInternalStorage()
            if(photos.isNotEmpty()) {
                selectedPhoto = photos[0]
                binding!!.photoImg.setImageBitmap(selectedPhoto!!.bmp)
                binding!!.numOfPhotosTxt.text = "${photos.size}"
            } else {
                binding!!.numOfPhotosTxt.text = "0"
            }
        }
    }

    private fun deletePhotoFromInternalStorage(fileName: String): Boolean {
        return try {
            requireActivity().deleteFile(fileName)
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    private suspend fun loadPhotosFromInternalStorage(): List<InternalStoragePhoto> {
        return withContext(Dispatchers.IO) {
            // 'filesDir' refers to the root directory of the internal storage
            val files = requireActivity().filesDir.listFiles()
            files?.filter { it.canRead() && it.isFile && it.name.endsWith(".jpg") }?.map {
                val bytes = it.readBytes()
                val bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
                InternalStoragePhoto(it.name, bmp)
            } ?: listOf()
        }
    }

    private fun savePhotoToInternalStorage(fileName: String, bmp: Bitmap): Boolean {
        return try {
            requireActivity().openFileOutput(fileName, Context.MODE_PRIVATE).use { stream ->
                // 'use' closes the stream after written to, or after an exception is thrown
                if(!bmp.compress(Bitmap.CompressFormat.JPEG, 95, stream)) {
                    throw IOException("Couldn't save bitmap.")
                }
            }
            true
        } catch (e: IOException) {
            e.printStackTrace()
            false
        }
    }
    // TO QUERY PHOTOS FROM STORAGE //
}