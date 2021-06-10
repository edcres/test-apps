package com.example.testmyviewpager2

import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.example.testmyviewpager2.databinding.ActivityViewsSliderBinding

// todo: probably delete this activity with its layout
// this activity shows a viewpager2 without tabs

class ViewsSliderActivity : AppCompatActivity() {

    private var mAdapter: ViewsSliderAdapter? = null
    private lateinit var dots: Array<TextView?>
    private lateinit var layouts: IntArray
    private var binding: ActivityViewsSliderBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewsSliderBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        init()
    }

    private fun init() {
        // layouts of all welcome sliders
        // add few more layouts if you want
        layouts = intArrayOf(
            R.layout.slide_one,
            R.layout.slide_two,
            R.layout.slide_three,
            R.layout.slide_four
        )
        mAdapter = ViewsSliderAdapter()
        binding.viewPager.setAdapter(mAdapter)
        binding.viewPager.registerOnPageChangeCallback(pageChangeCallback)
        binding.btnSkip.setOnClickListener { v -> launchHomeScreen() }
        binding.btnNext.setOnClickListener { v ->
            // checking for last page
            // if last page home screen will be launched
            val current = getItem(+1)
            if (current < layouts.size) {
                // move to next screen
                binding.viewPager.setCurrentItem(current)
            } else {
                launchHomeScreen()
            }
        }
        binding.iconMore.setOnClickListener { view -> showMenu(view) }

        // adding bottom dots
        addBottomDots(0)
    }

    /*
     * Adds bottom dots indicator
     * */
    private fun addBottomDots(currentPage: Int) {
        dots = arrayOfNulls(layouts.size)
        val colorsActive: IntArray = resources.getIntArray(R.array.array_dot_active)
        val colorsInactive: IntArray = resources.getIntArray(R.array.array_dot_inactive)
        binding.layoutDots.removeAllViews()
        for (i in dots.indices) {
            dots[i] = TextView(this)
            // dots[i]?.text = Html.fromHtml("&#8226;")   // 'fromHtml' was deprecated
            dots[i]?.text = HtmlCompat.fromHtml("&#8226;", HtmlCompat.FROM_HTML_MODE_LEGACY)
            dots[i]!!.textSize = 35f
            dots[i]!!.setTextColor(colorsInactive[currentPage])
            binding.layoutDots.addView(dots[i])
        }
        if (dots.isNotEmpty()) dots[currentPage]!!.setTextColor(colorsActive[currentPage])
    }

    private fun getItem(i: Int): Int {
        return binding.viewPager.getCurrentItem() + i
    }

    private fun launchHomeScreen() {
        Toast.makeText(this, R.string.slides_ended, Toast.LENGTH_LONG).show()
        finish()
    }

    private fun showMenu(view: View) {
        val popup = PopupMenu(this, view)
        val inflater: MenuInflater = popup.menuInflater
        inflater.inflate(R.menu.pager_transformers, popup.menu)
        popup.setOnMenuItemClickListener { item ->
            if (item.itemId === R.id.action_orientation) {
                binding.viewPager.setOrientation(ViewPager2.ORIENTATION_VERTICAL)
            } else {
                //binding.viewPager.setPageTransformer(Utils.getTransformer(item.getItemId()))
                binding.viewPager.setCurrentItem(0)
                binding.viewPager.getAdapter().notifyDataSetChanged()
            }
            false
        }
        popup.show()
    }

    /*
     * ViewPager page change listener
     */
    var pageChangeCallback: OnPageChangeCallback = object : OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            addBottomDots(position)

            // changing the next button text 'NEXT' / 'GOT IT'
            if (position == layouts.size - 1) {
                // last page. make button text to GOT IT
                binding.btnNext.setText(getString(R.string.start))
                binding.btnSkip.setVisibility(View.GONE)
            } else {
                // still pages are left
                binding.btnNext.setText(getString(R.string.next))
                binding.btnSkip.setVisibility(View.VISIBLE)
            }
        }
    }

    inner class ViewsSliderAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            val view: View = LayoutInflater.from(parent.context)
                .inflate(viewType, parent, false)
            return SliderViewHolder(view)
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {}
        override fun getItemViewType(position: Int): Int {
            return layouts[position]
        }

        override fun getItemCount(): Int {
            return layouts.size
        }

        inner class SliderViewHolder(view: View?) : RecyclerView.ViewHolder(view) {
            var title: TextView? = null
            var year: TextView? = null
            var genre: TextView? = null
        }
    }
}