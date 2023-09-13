package com.example.githubuser.main

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.githubuser.MainViewModel
import com.example.githubuser.adapter.ListUserAdapter
import com.example.githubuser.R
import com.example.githubuser.databinding.ActivityMainBinding
import com.example.githubuser.response.SearchResponse

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var rvUser: RecyclerView
    private lateinit var mainViewModel: MainViewModel

    lateinit var listAdapter: ListUserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        rvUser = findViewById(R.id.rv_user)
        rvUser.setHasFixedSize(true)

        mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())
            .get(MainViewModel::class.java)


        val layoutManager = LinearLayoutManager(this)
        binding.rvUser.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this,layoutManager.orientation)
        binding.rvUser.addItemDecoration(itemDecoration)

        mainViewModel.message.observe(this,{
            it.getContentIfNotHandled()?.let {
                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
            }
        })

        mainViewModel.isLoading.observe(this,{
            showLoading(it)
        })

        mainViewModel.user.observe(this, {
            if (it != null){
                listAdapter.setData(it)
                showLoading(false)
            }
        })

        setupRecycleView()
    }

    private fun setupRecycleView() {
        listAdapter = ListUserAdapter(arrayListOf())
        rvUser.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = listAdapter
        }
        listAdapter.setOnItemClickCallback(object : ListUserAdapter.OnItemClickCallback{
            override fun onItemClicked(items: SearchResponse.ItemsItem) {
                showSelectedUser(items.login)
            }
        })
    }

    private fun showSelectedUser(username: String) {
        val moveIntent = Intent(this@MainActivity, MoveActivity::class.java)
        moveIntent.putExtra(MoveActivity.KEY_USER,username)
        startActivity(moveIntent)
    }


    private fun showLoading(isLoading: Boolean) {
        if (isLoading){
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }

    }

    //searchview
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_search, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search).actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(querry: String): Boolean {
                mainViewModel.findSearch(querry)
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {

                return false
            }
        })
        return true
    }
}