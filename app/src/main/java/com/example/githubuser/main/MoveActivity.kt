package com.example.githubuser.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.githubuser.MoveViewModel
import com.example.githubuser.R
import com.example.githubuser.adapter.SectionsPagerAdapter
import com.example.githubuser.databinding.ActivityMoveBinding
import com.example.githubuser.response.DetailResponse
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MoveActivity() : AppCompatActivity() {
    private lateinit var binding: ActivityMoveBinding
    private lateinit var detailViewModel: MoveViewModel

    companion object{
         const val KEY_USER = "login"
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMoveBinding.inflate(layoutInflater)
        setContentView(binding.root)

        detailViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())
            .get(MoveViewModel::class.java)

        val username = intent.getStringExtra(KEY_USER).toString()
        supportActionBar?.title = username

        val bundle = Bundle()
        bundle.putString(KEY_USER,username)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setLogo(R.drawable.ic_back)
        supportActionBar?.setDisplayUseLogoEnabled(true)

        detailViewModel.findUser(username)
        //TabLayout
        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager, bundle)
        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        TabLayoutMediator(tabs, viewPager) {tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        supportActionBar?.elevation = 0f

        detailViewModel.isLoading.observe(this,{
            showLoading(it)
        })

        detailViewModel.detailUser.observe(this, {
                user -> setData(user)
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading){
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }

    }

    private fun setData(detail: DetailResponse){
        with(binding) {
            Glide.with(binding.ivDetailAvatar)
                .load(detail.avatarUrl)
                .circleCrop()
                .into(ivDetailAvatar)
            tvDetailNumberRepos.text = detail.publicRepos.toString()
            tvDetailNumberFollow.text = detail.followers.toString()
            tvDetailNumberFollowing.text = detail.following.toString()
            if (detail.name == null){
                binding.tvName.text = getString(R.string.data_kosong)
            }else{
                binding.tvName.text = detail.name
            }
            if (detail.location == null){
                binding.tvLocation.text   = getString(R.string.data_kosong)
            }else {
                binding.tvLocation.text = detail.location
            }
            if (detail.company == null) {
                binding.tvCompany.text = getString(R.string.data_kosong)
            }else {
                binding.tvCompany.text = detail.company
            }

        }
    }
}