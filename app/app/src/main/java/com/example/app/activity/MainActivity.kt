package com.example.app.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.app.R
import com.example.app.adapter.LoadingStateAdapter
import com.example.app.adapter.StoryAdapter
import com.example.app.data.response.ListStoryItem
import com.example.app.databinding.ActivityMainBinding
import com.example.app.di.Result
import com.example.app.viewmodel.MainViewModel
import com.example.app.viewmodel.ViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        viewModel.getSession().observe(this) {
            if (!it.isLogin) {
                val intent = Intent(this, WelcomeActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
                startActivity(intent)
                finish()
            }
        }

        binding.rvStory.layoutManager = LinearLayoutManager(this)
        val adapter = StoryAdapter()
        binding.rvStory.adapter = adapter

        binding.rvStory.adapter = adapter.withLoadStateFooter(
            footer = LoadingStateAdapter {
                adapter.retry()
            }
        )

        viewModel.getAllStories.observe(this) {
            adapter.submitData(lifecycle, it)
        }

//        viewModel.getAllStories().observe(this){
//            if (it != null) {
//                when(it) {
//                    is Result.Loading -> {
//                        binding.progressBar.visibility = View.VISIBLE
//                    }
//                    is Result.Success -> {
//                        binding.progressBar.visibility = View.GONE
//
//                        val response = it.data
//
//                        binding.rvStory.layoutManager = LinearLayoutManager(this)
//                        val storyAdapter = StoryAdapter(response)
//                        binding.rvStory.adapter = storyAdapter
//
//                        storyAdapter.setOnItemClickCallback(object : StoryAdapter.OnItemClickCallback {
//                            override fun onItemClicked(data: ListStoryItem) {
//                                goToDetail(data)
//                            }
//                        })
//                    }
//                    is Result.Error -> {
//                        binding.progressBar.visibility = View.GONE
//
//                        AlertDialog.Builder(this).apply {
//                            setTitle(it.error)
//                            setPositiveButton("Ok") {dialog, which ->
//                                dialog.dismiss()
//                            }
//                            create()
//                            show()
//                        }
//                    }
//                }
//            }
//        }

        binding.fab.setOnClickListener { startActivity(Intent(this, UploadStoryActivity::class.java)) }
    }

    private fun goToDetail(data: ListStoryItem) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra("data", data)

        val optionsCompat: ActivityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(this)

        startActivity(intent, optionsCompat.toBundle())
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.btn_logout -> {
                viewModel.logout()
                finish()
            }
            R.id.btn_map -> {
                startActivity(Intent(this, MapsActivity::class.java))
            }
        }
        return true
    }
}