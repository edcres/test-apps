package com.example.testhardwareplus.camera

import android.Manifest
import android.content.ContentUris
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.testhardwareplus.R
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import java.util.concurrent.TimeUnit

typealias LumaListener = (luma: Double) -> Unit

// This code uses the CameraX library

// https://www.youtube.com/watch?v=xZZQ5q5pOp0      I didn't use this
// https://developer.android.com/codelabs/camerax-getting-started#0     Taking photo codelab
// https://github.com/android/storage-samples/tree/main/MediaStore  MediaStore sample project

// todo: should use 'MediaStore' instead of 'mediaDir'
// I'm using 'mediadir' to save the picture and 'MediaStore' to show all the pictures
// only shows the pictures taking with this app.
//  Idk why but it's probably bc I'm missing shared storage permissions

// uses Glide to load the images

// popssible problem, from the time the picture is taken and the time it shows up, it takes a long time

// bug: displaying a photo when it is taken only works the first time. I have to restart
//  the app so it works again (maybe I have to clear Glide from that ImageView widget)

class CameraFragment : Fragment(), GalleryAdapter.OnItemClickListener {

    private lateinit var cameraCaptureBtn: Button
    private lateinit var getImgBtn: Button
    private lateinit var viewFinder: PreviewView
    private lateinit var pictureView: ImageView
    private lateinit var goToCamBtn: Button
    private lateinit var galleyRecycler: RecyclerView

    private val cameraRequest = 1888
    private var imageCapture: ImageCapture? = null
    private lateinit var outputDirectory: File
    private lateinit var cameraExecutor: ExecutorService

    private val imageURIs = mutableListOf<Uri>() // I made this to have a list of image locations
    private lateinit var imagesList: List<MediaStoreImage>

    companion object {
        private const val TAG = "CameraXBasic"
        private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
        private const val REQUEST_CODE_PERMISSIONS = 10
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
    }

    // OVERRIDE FUNCTIONS //
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_camera, container, false)

        cameraCaptureBtn = view.findViewById(R.id.camera_capture_btn)
        getImgBtn = view.findViewById(R.id.get_img_btn)
        viewFinder = view.findViewById(R.id.view_finder)
        pictureView = view.findViewById(R.id.picture_view)
        goToCamBtn = view.findViewById(R.id.go_to_cam_btn)
        galleyRecycler = view.findViewById(R.id.galley_recycler)

        imagesList = getImages()
        Log.d(TAG, "onCreateView: listSize = ${imagesList.size}")
        val galleryAdapter = GalleryAdapter(imagesList, this)

        galleyRecycler.adapter = galleryAdapter
        galleyRecycler.layoutManager = GridLayoutManager(context, 3)

        // Request camera permissions
        if (allPermissionsGranted()) {
            startCamera()
        } else {
            ActivityCompat.requestPermissions(
                this.requireActivity(), REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS
            )
        }

        cameraCaptureBtn.setOnClickListener {
            val newImageURI = takePhoto()
            showChosenImageWidgets()

            // it's inefficient to query all the images just to display one.
            // but it's fine, this isn't a real app
//            val newImageURI = imageURIs.last()
//            val newImage = getImages()[0]
            Glide.with(pictureView)
                .load(newImageURI)
                .thumbnail(0.33f)
                .centerCrop()
                .into(pictureView)
        }

        getImgBtn.setOnClickListener {
            // the gallery images are fetched when the activity starts
            //  possible problem: when the user takes a picture, it might not show up in the
            //  gallery until the activity is recreated
            showGalleryWidgets()
        }

        goToCamBtn.setOnClickListener {
            showCamPreviewWidgets()
        }

        outputDirectory = getOutputDirectory()

        cameraExecutor = Executors.newSingleThreadExecutor()

        return view
    }

    // should be replaced with 'registerForActivityResult(ActivityResultContract, ActivityResultCallback)'
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                startCamera()
            } else {
                // If permissions are not granted, present a toast to notify the user that the permissions were not granted
                Toast.makeText(
                    requireContext(),
                    "Permissions not granted by the user.",
                    Toast.LENGTH_SHORT
                ).show()

                // this is to destroy the fragment, the codeLab uses an activity
                parentFragmentManager.beginTransaction().remove(this).commit()
                // 'requireActivity().finish()'
            }
        }
    }

    // for the recyclerView
    override fun onItemClick(position: Int) {
        Log.d(TAG, "onItemClick: position: $position")

        val chosenImageURI = imagesList[position].contentUri
        showChosenImageWidgets()
        Glide.with(pictureView)
            .load(chosenImageURI)
            .thumbnail(0.33f)
            .centerCrop()
            .into(pictureView)
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }
    // OVERRIDE FUNCTIONS //

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            requireContext() /*baseContext*/, it
        ) == PackageManager.PERMISSION_GRANTED
    }

    // FEATURES //
    // preview the camera
    // implement a viewfinder
    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())

        cameraProviderFuture.addListener(Runnable {
            // Used to bind the lifecycle of cameras to the lifecycle owner
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            // Preview
            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(viewFinder.surfaceProvider)
                }

            // Select back camera as a default
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                // Unbind use cases before rebinding, then bind new use cases to camera
                cameraProvider.unbindAll()
                cameraProvider
                    .bindToLifecycle(this, cameraSelector, preview, imageCapture)

            } catch(exc: Exception) {
                Log.e(TAG, "Use case binding failed", exc)
            }

        }, ContextCompat.getMainExecutor(requireContext()))


        // for image capture feature
        imageCapture = ImageCapture.Builder()
            .build()

    }

    // Implement ImageCapture
    // define a configuration object that is used to instantiate the actual use case object
    private fun takePhoto(): Uri? {
        // Get a stable reference of the modifiable image capture use case
//        val imageCapture = imageCapture ?: return
        val imageCapture = imageCapture ?: return null

        // Create time-stamped output file to hold the image
        val photoFile = File(
            outputDirectory,
            SimpleDateFormat(
                FILENAME_FORMAT, Locale.US
            ).format(System.currentTimeMillis()) + ".jpg"
        )

        // Create output options object which contains file + metadata
        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

        // Set up image capture listener, which is triggered after photo has
        // been taken
        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(requireContext()),
            object : ImageCapture.OnImageSavedCallback {

                override fun onError(exc: ImageCaptureException) {
                    Log.e(TAG, "Photo capture failed: ${exc.message}", exc)
                }

                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                    val savedUri = Uri.fromFile(photoFile)
                    val msg = "Photo capture succeeded: $savedUri"
                    imageURIs.add(savedUri) // I made this to have a list of image locations
                    Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
                    Log.d(TAG, msg)
                }
            })

        return Uri.fromFile(photoFile)
    }
    // FEATURES //

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun getOutputDirectory(): File {
        val mediaDir = requireActivity().externalMediaDirs.firstOrNull()?.let {
            File(it, resources.getString(R.string.app_name)).apply { mkdirs() }
        }
        return if (mediaDir != null && mediaDir.exists())
            mediaDir else requireActivity().filesDir
    }

    // code for open gallery picture //
    // display the pictures from the gallery
    private fun getImages(): List<MediaStoreImage> {
        val images = mutableListOf<MediaStoreImage>()

        val projection = arrayOf(
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.DISPLAY_NAME,
            MediaStore.Images.Media.DATE_ADDED
        )
        val selection = "${MediaStore.Images.Media.DATE_ADDED} >= ?"
        val selectionArgs = arrayOf(
            // Release day of the G1. :)
            dateToTimestamp(day = 22, month = 10, year = 2008).toString()
        )
        val sortOrder = "${MediaStore.Images.Media.DATE_ADDED} DESC"

        requireContext().contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            projection,
            selection,
            selectionArgs,
            sortOrder
        )?.use { cursor ->
            val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
            val dateModifiedColumn =
                cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_ADDED)
            val displayNameColumn =
                cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME)

            Log.i(TAG, "Found ${cursor.count} images")
            while (cursor.moveToNext()) {
                // Here we'll use the column indexes that we found above.
                val id = cursor.getLong(idColumn)
                val dateModified =
                    Date(TimeUnit.SECONDS.toMillis(cursor.getLong(dateModifiedColumn)))
                val displayName = cursor.getString(displayNameColumn)

                val contentUri = ContentUris.withAppendedId(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    id
                )

                val image = MediaStoreImage(id, displayName, dateModified, contentUri)
                images += image

                // For debugging, we'll output the image objects we create to logcat.
                Log.v(TAG, "Added image: $image")
            }
        }

        Log.v(TAG, "Found ${images.size} images")
        return images
    }

    // HELPER FUNCTIONS //
    private fun showCamPreviewWidgets() {
        viewFinder.visibility = View.VISIBLE
        cameraCaptureBtn.visibility = View.VISIBLE
        getImgBtn.visibility = View.VISIBLE
        galleyRecycler.visibility = View.GONE
        pictureView.visibility = View.GONE
        goToCamBtn.visibility = View.GONE
    }
    private fun showGalleryWidgets() {
        viewFinder.visibility = View.GONE
        cameraCaptureBtn.visibility = View.GONE
        getImgBtn.visibility = View.GONE
        galleyRecycler.visibility = View.VISIBLE
        pictureView.visibility = View.GONE
        goToCamBtn.visibility = View.GONE
    }
    private fun showChosenImageWidgets() {
        viewFinder.visibility = View.GONE
        cameraCaptureBtn.visibility = View.GONE
        getImgBtn.visibility = View.GONE
        galleyRecycler.visibility = View.GONE
        pictureView.visibility = View.VISIBLE
        goToCamBtn.visibility = View.VISIBLE
    }
    private fun dateToTimestamp(day: Int, month: Int, year: Int): Long =
        SimpleDateFormat("dd.MM.yyyy").let { formatter ->
            TimeUnit.MICROSECONDS.toSeconds(formatter.parse("$day.$month.$year")?.time ?: 0)
        }
    // HELPER FUNCTIONS //
}


// recyclerView to display the gallery
class GalleryAdapter(
    private val imageList: List<MediaStoreImage>,
    private val onItemClickListener: OnItemClickListener
) : RecyclerView.Adapter<GalleryAdapter.ClickListenerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClickListenerViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.gallery_item_layout, parent, false)

        return ClickListenerViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ClickListenerViewHolder, position: Int) {
        val currentImage = imageList[position]
        holder.rootView.tag = currentImage

        // set the image to the imageView
        Glide.with(holder.imageView)
            .load(currentImage.contentUri)
            .thumbnail(0.33f)
            .centerCrop()
            .into(holder.imageView)
    }

    override fun getItemCount(): Int {
        return imageList.size
    }

    inner class ClickListenerViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {

        val rootView = itemView
        val imageView: ImageView = itemView.findViewById(R.id.gallery_image)

        init {
            itemView.setOnClickListener(this)

//            val image = rootView.tag as? MediaStoreImage ?: return@setOnClickListener
//            onClick(image)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                onItemClickListener.onItemClick(position)
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

}
