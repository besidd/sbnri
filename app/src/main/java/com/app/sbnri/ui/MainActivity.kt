package com.app.sbnri.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AbsListView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.sbnri.R
import com.app.sbnri.adapters.DataAdapter
import com.app.sbnri.api.ISBNRIApi
import com.app.sbnri.utils.Constants
import com.app.sbnri.utils.ItemDecorator
import com.app.sbnri.utils.SBNRIApplication
import com.app.sbnri.viewmodels.AppViewModel
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

private const val TAG = "MainActivity"

class MainActivity : BaseActivity() {

    @Inject
    lateinit var viewmodel: AppViewModel

    lateinit var dataAdapter: DataAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        SBNRIApplication.appComponent.inject(this)
        setTheme(R.style.AppTheme)

        setContentView(R.layout.activity_main)

        dataAdapter = DataAdapter()

        supportActionBar?.setIcon(R.drawable.sbnri_logo)


        viewmodel.getAllData()

        setupRecyclerView()

        viewmodel.data.observe(this, Observer {
            dataAdapter.differ.submitList(it)
        })

        viewmodel.isLoading.observe(this, Observer {
            pbLoading.apply {
                if (it) {
                    visibility = View.VISIBLE
                } else {
                    visibility = View.GONE
                }
            }
        })

    }

    private fun setupRecyclerView() {
        rv_main.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            addItemDecoration(ItemDecorator(20, 20, 25, 16))
            adapter = dataAdapter
            hasFixedSize()
            addOnScrollListener(rvScrollListener)
        }
    }

    var isScrolling = false
    var isLoading = false

    val rvScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)

            isScrolling = newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL

        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val layoutManager = rv_main.layoutManager as LinearLayoutManager
            val firstItemVisiblePosition = layoutManager.findFirstVisibleItemPosition()
            val visibleCount = layoutManager.childCount
            val totalItems = layoutManager.itemCount

            val isAtLastItem = firstItemVisiblePosition + visibleCount >= totalItems
            val isNotAtBegining = firstItemVisiblePosition > 0
            val isTotalMoreThanVisible = totalItems >= Constants.dataCount

            val shouldPaginate =
                !isLoading && isAtLastItem && isNotAtBegining && isTotalMoreThanVisible && isScrolling

            if (shouldPaginate) {
                viewmodel.getAllData()
                isScrolling = false
            }

        }
    }
}