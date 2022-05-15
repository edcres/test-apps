package com.example.testhardwareplus.internalstore

import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.testhardwareplus.BuildConfig
import com.example.testhardwareplus.databinding.FragmentInternalStoreBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File

// more code in the manifest file (file providers, <provider>), and an .xml paths file

// https://www.youtube.com/watch?v=EeLz1DPMsW8&list=PLtO7tIzYX2lUaX0Isy0zbjg0A1Ci--Q3l&index=3&t=602s
// https://medium.com/codex/how-to-use-the-android-activity-result-api-for-selecting-and-taking-images-5dbcc3e6324b

/**
 * take a picture, save it to internal storage, display it from internal storage
 *
 * To take a picture, use activityResultContract() (replaces the deprecated startActivityForResult())
 *      - might need the 2 dependencies:
 *          - implementation "androidx.activity:activity-ktx:1.2.3"
 *          - implementation "androidx.fragment:fragment-ktx:1.3.4"
 */

// The number of stored pics is only updated when 'loadPhotosFromInternalStorageIntoRecyclerView()' is called

private const val TAG = "IntStore__TAG"

class InternalStoreFragment : Fragment() {

    private var binding: FragmentInternalStoreBinding? = null
    private var latestTmpUri: Uri? = null
    private val allPicsUris = mutableListOf<Uri>()
    private val takeImageResult =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { isSuccess ->
            // todo: compress to jpg

            // get the file using the uri, decompress, check if it's null

            if (isSuccess) {
//



                latestTmpUri?.let { uri ->
//                    binding!!.photoImg.setImageURI(uri)
                    loadPhotosFromInternalStorageIntoRecyclerView()
                    Log.d(TAG, "uri = \n$uri")
                }
            } else {
                // happens if the camera starts and the user navigates up before taking a picture, or user click x on the picture
//              val file = requireActivity().cacheDir.listFiles().filter {
//                  it.toUri() == latestTmpUri
//              }[0]
                val file = File(latestTmpUri!!.path!!)
//                Log.d(TAG, "file: \n$file")
//                Log.d(TAG, "exists: ${file.exists()}")
//                Log.d(TAG, "deleted: ${file.delete()}")
//                Log.d(TAG, "file: \n${file.name}")
//                Log.d(TAG, "deleted: ${requireActivity().deleteFile(file.name)}")
                Log.d(TAG, "deleted: ${deleteFile(file.name)}")
                loadPhotosFromInternalStorageIntoRecyclerView()

//                    val bytes = file.readBytes()
//                    val bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
//                    if (bmp == null) {
//                        file.delete()
//                        Log.d(TAG, "bmp is null")
//                    } else {
//                        Log.d(TAG, "bmp: $bmp")
//                    }
                Log.d(TAG, "pic not taken")
            }
        }
//    private val selectImageFromGalleryResult =
//        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
//            uri?.let { binding!!.photoImg.setImageURI(uri) }
//        }

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
        binding?.apply {
            takePhotoBtn.setOnClickListener {
                takeImage()
            }
            deleteBtn.setOnClickListener {
                deleteFileAt(0)
//                Log.d(TAG, "uri to delete: \n$latestTmpUri")
//                val file = File(latestTmpUri!!.path!!)
//                Log.d(TAG, "exists: ${file.exists()}")
//                Log.d(TAG, "deleted: ${file.delete()}")
//                loadPhotosFromInternalStorageIntoRecyclerView()
            }
        }
//        binding!!.photoImg.setImageURI("content://com.example.testhardwareplus.provider/cached_files/tmp_image_file4804381872488905849.jpg".toUri())

        // idk why but when i use 'File.createTempFile' the Uri shows up like the top one, and when i use 'cacheDir.' the Uri show up like the bottom one.
        //  content://com.example.testhardwareplus.provider/cached_files/tmp_image_file4804381872488905849.jpg
        //  file:///data/user/0/com.example.testhardwareplus/cache/tmp_image_file2767257179138799494.jpg
        loadPhotosFromInternalStorageIntoRecyclerView()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    private fun takeImage() {
        lifecycleScope.launchWhenStarted {
            makeTmpFile().let { uri ->
                latestTmpUri = uri
                takeImageResult.launch(uri)
            }
//            loadPhotosFromInternalStorageIntoRecyclerView()
        }
    }

//    private fun selectImageFromGallery() = selectImageFromGalleryResult.launch("image/*")

    private fun makeTmpFile(): Uri {
        val tmpFile = File.createTempFile("tmp_image_file", ".jpg", requireActivity().cacheDir).apply {
            createNewFile()
            deleteOnExit()
        }

        return FileProvider.getUriForFile(requireActivity().applicationContext, "${BuildConfig.APPLICATION_ID}.provider", tmpFile)
    }

    private fun loadPhotosFromInternalStorageIntoRecyclerView() {
        // instead of the photos to recyclerView, just do the first photo to an imageView.
        lifecycleScope.launch {
            binding?.apply {
                loadPhotosFromInternalStorage().let { photos ->
                    if (photos.isNotEmpty()) {
                        Glide.with(photoImg.context)
                            .load(photos[0].uri)
                            .into(photoImg)
                        numOfPhotosTxt.text = "${photos.size}"
                        photos.forEach {
                            Log.d(TAG, ".\n${it.uri}")
                        }
                    } else {
                        numOfPhotosTxt.text = "0"
                    }
                }
            }
        }
    }

    private suspend fun loadPhotosFromInternalStorage(): List<InternalStoragePhoto> {
        return withContext(Dispatchers.IO) { // 'filesDir' refers to the root directory of the internal storage
            val files = requireActivity().cacheDir.listFiles()
            // they are probably sorted alphabetically
            files?.filter {
                it.canRead() && it.isFile && it.name.endsWith(".jpg")
            }?.map {
                val uri = it.toUri()
//                val bytes = it.readBytes()
//                val bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
                InternalStoragePhoto(it.name, uri)
            } ?: listOf()
        }
    }

    private fun deleteFile(name: String): Boolean {
        val files = requireActivity().cacheDir.listFiles()
        if (files.isNullOrEmpty()) {
            Log.e(TAG, "deleteFile: Error loading files.")
            return false
        } else {
            files.filter {
                it.canRead() && it.isFile && it.name.endsWith(".jpg")
            }.forEach {
                if (it.name == name) {
                    return it.delete()
                }
            }
            return false
        }
    }

    private fun deleteFileAt(position: Int): Boolean {
        val files = requireActivity().cacheDir.listFiles().filter {
            it.canRead() && it.isFile && it.name.endsWith(".jpg")
        }
        loadPhotosFromInternalStorageIntoRecyclerView()
        return files[position].delete()
    }
}
//class InternalStoreFragment : Fragment() {
//
//    private var binding: FragmentInternalStoreBinding? = null
//    private var selectedPhoto: InternalStoragePhoto? = null
//
//
//
//
//    private var receivedUriKey: Uri? = null
//    private lateinit var outputDirectory: File
//    //private var selection: String? = null
//    private var selection: String? = "capture" /* later will get it from clicks*/
//    private val pickerContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
//        // GetContent() on the other hand, prompts the user to pick a piece of content. In our case we want the user to pick an image from his gallery. To specify what mime-type should be picked, we can put in a type filter e.g. “image/*” on launching the contract.
//        if(uri != null) { receivedUriKey = uri}
//    }
//    private val capturePicture = registerForActivityResult(ActivityResultContracts.TakePicture()) {
//        // TakePicture() takes an Uri of a file as a parameter. It then automatically opens the native camera app, takes care of permission handling and all the other stuff we previously had to take care of by ourselves.
//        if(it) {
//            receivedUriKey.let { uri ->
//                if( uri != null) {
//                    receivedUriKey = uri
//                    Log.d("Capture Pic", receivedUriKey.toString())
//                }
//            }
//        }
//    }
//
//
//
//
//
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        val fragmentBinding = FragmentInternalStoreBinding.inflate(inflater, container, false)
//        binding = fragmentBinding
//        return fragmentBinding.root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        loadPhotosFromInternalStorageIntoRecyclerView()
//
//        // TO TAKE A PHOTO //
//        val takePhoto = registerForActivityResult(ActivityResultContracts.TakePicturePreview()) {
//            // what to do when a photo is taken
//            // random file name , it is the bitmap
//            val fileName = UUID.randomUUID().toString() + ".jpg" // todo: use this as a property in an entity
//            val isSavedSuccessfully = savePhotoToInternalStorage(fileName, it)
//            if(isSavedSuccessfully) {
//                loadPhotosFromInternalStorageIntoRecyclerView()
//                Toast.makeText(requireContext(), "Photo saved successfully", Toast.LENGTH_SHORT)
//                    .show()
//            } else{
//                Toast.makeText(requireContext(), "Failed to save photo", Toast.LENGTH_SHORT)
//                    .show()
//            }
//        }
//
//        binding?.apply {
//            deleteBtn.setOnClickListener {
//                if(selectedPhoto != null) {
//                    val isDeletedSuccessfully = deletePhotoFromInternalStorage(selectedPhoto!!.name)
//                    if(isDeletedSuccessfully) {
//                        loadPhotosFromInternalStorageIntoRecyclerView()
//                        Toast.makeText(requireContext(), "Photo successfully deleted", Toast.LENGTH_SHORT)
//                            .show()
//                    } else{
//                        Toast.makeText(requireContext(), "Failed to delete photo", Toast.LENGTH_SHORT)
//                            .show()
//                    }
//                }
//            }
//            takePhotoBtn.setOnClickListener {
////                takePhoto.launch()
//                capturePicture
//            }
//        }
//        // TO TAKE A PHOTO //
//    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//        binding = null
//    }
//
//    override fun onResume() {
//        super.onResume()
//        if( selection == "gallery" && receivedUriKey == null) {
//            pickerContent.launch("image/*")
//        } else if(selection == "capture" && receivedUriKey == null) {
//            Log.d("Capture Resume", receivedUriKey.toString())
//            takeImage()
//        }
//        if(receivedUriKey != null) {
//            Log.d("Capture Resume 2", receivedUriKey.toString())
//            binding!!.photoImg.setImageURI(receivedUriKey)
//        }
//    }
//    private fun takeImage() {
//        lifecycleScope.launchWhenStarted {
//            getTmpFileUri().let { uri ->
//                receivedUriKey = uri
//                capturePicture.launch(uri)
//            }
//        }
//    }
//    private fun getTmpFileUri(): Uri {
//        outputDirectory = getOutputDirectory(requireContext())
//        val tmpFile = File.createTempFile(
//            SimpleDateFormat(FILENAME, Locale.ENGLISH).format(System.currentTimeMillis()), PHOTO_EXTENSION, outputDirectory).apply {
//            createNewFile()
//            deleteOnExit()
//        }
//        return FileProvider.getUriForFile(requireContext(), "${BuildConfig.APPLICATION_ID}.provider", tmpFile)
//    }
//    companion object {
//        private const val FILENAME = "yyyy-MM-dd-HH-mm-ss-SSS"
//        private const val PHOTO_EXTENSION = ".jpeg"
//        private fun getOutputDirectory(context: Context): File {
//            val appContext = context.applicationContext
//            val mediaDir = context.externalMediaDirs.firstOrNull()?.let {
//                File(it, appContext.resources.getString(R.string.app_name)).apply { mkdirs() } }
//            return if (mediaDir != null && mediaDir.exists())
//                mediaDir else appContext.filesDir
//        }
//    }
//
//
//
//
//
//
//
//
//
//
//    // TO QUERY PHOTOS FROM STORAGE //
//    private fun loadPhotosFromInternalStorageIntoRecyclerView() {
//        // instead of the photos to recyclerView, just do the first photo to an imageView.
//        lifecycleScope.launch {
//            binding?.apply {
//                val photos = loadPhotosFromInternalStorage()
//                if (photos.isNotEmpty()) {
//                    selectedPhoto = photos.last()
//
//                    Glide.with(photoImg.context)
//                        .load(selectedPhoto!!.bmp)
//                        .into(photoImg)
//
//                    numOfPhotosTxt.text = "${photos.size}"
//                } else {
//                    numOfPhotosTxt.text = "0"
//                }
//            }
//        }
//    }
//
//    private fun deletePhotoFromInternalStorage(fileName: String): Boolean {
//        return try {
//            requireActivity().deleteFile(fileName)
//        } catch (e: Exception) {
//            e.printStackTrace()
//            false
//        }
//    }
//
//    private suspend fun loadPhotosFromInternalStorage(): List<InternalStoragePhoto> {
//        return withContext(Dispatchers.IO) {
//            // 'filesDir' refers to the root directory of the internal storage
//            val files = requireActivity().filesDir.listFiles()
//            files?.filter { it.canRead() && it.isFile && it.name.endsWith(".jpg") }?.map {
//                val bytes = it.readBytes()
//                val bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
//                InternalStoragePhoto(it.name, bmp)
//            } ?: listOf()
//        }
//    }
//
//    private fun savePhotoToInternalStorage(fileName: String, bmp: Bitmap): Boolean {
//        return try {
//            requireActivity().openFileOutput(fileName, Context.MODE_PRIVATE).use { stream ->
//                // '.use' closes the stream after written to, or after an exception is thrown
//                if(!bmp.compress(Bitmap.CompressFormat.JPEG, 95, stream)) {
//                    throw IOException("Couldn't save bitmap.")
//                }
//            }
//            true
//        } catch (e: IOException) {
//            e.printStackTrace()
//            false
//        }
//    }
//    // TO QUERY PHOTOS FROM STORAGE //
//}