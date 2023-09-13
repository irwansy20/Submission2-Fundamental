package com.example.githubuser.main

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.githubuser.FollowerResponse
import com.example.githubuser.FollowerViewModel
import com.example.githubuser.FollowingViewModel
import com.example.githubuser.R
import com.example.githubuser.adapter.FollowerAdapter
import com.example.githubuser.databinding.FragmentFollowerBinding
import com.example.githubuser.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowerFragment() : Fragment() {
    private lateinit var binding: FragmentFollowerBinding
    private lateinit var rvFollower : RecyclerView
    lateinit var followListAdapter: FollowerAdapter
    private lateinit var followerViewModel: FollowerViewModel
    private lateinit var username: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentFollowerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        rvFollower = view.findViewById(R.id.rv_Followers)
        rvFollower.setHasFixedSize(true)

        val args = arguments
        username = args?.getString(MoveActivity.KEY_USER).toString()

        followerViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())
            .get(FollowerViewModel::class.java)

        super.onViewCreated(view, savedInstanceState)
        val linearlayoutManager = LinearLayoutManager(context)
        binding.rvFollowers.layoutManager = linearlayoutManager
        val itemDecoration = DividerItemDecoration(context,linearlayoutManager.orientation)
        binding.rvFollowers.addItemDecoration(itemDecoration)

        followListAdapter = FollowerAdapter(arrayListOf())
        rvFollower.apply {
            layoutManager = linearlayoutManager
            adapter = followListAdapter
        }

        followerViewModel.isLoading.observe(viewLifecycleOwner,{
            showLoading(it)
        })

        followerViewModel.follower.observe(viewLifecycleOwner, {
            setFollow(it)
            showLoading(false)
        })
        followerViewModel.listFollow(username)
    }

    private fun setFollow(follow: List<FollowerResponse.FollowerResponseItem>) {
        val adapter = FollowerAdapter(follow)
        binding.rvFollowers.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}