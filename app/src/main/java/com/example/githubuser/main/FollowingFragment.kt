package com.example.githubuser.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.githubuser.FollowingResponse
import com.example.githubuser.FollowingViewModel
import com.example.githubuser.R
import com.example.githubuser.adapter.FollowingAdapter
import com.example.githubuser.databinding.FragmentFollowingBinding

class FollowingFragment() : Fragment() {
    private lateinit var binding: FragmentFollowingBinding
    private lateinit var rvFollowing : RecyclerView
    lateinit var followingListAdapter: FollowingAdapter
    private lateinit var followingViewModel: FollowingViewModel
    private lateinit var username: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentFollowingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        rvFollowing = view.findViewById(R.id.rv_Following)
        rvFollowing.setHasFixedSize(true)

        val args = arguments
        username = args?.getString(MoveActivity.KEY_USER).toString()

        followingViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())
            .get(FollowingViewModel::class.java)

        super.onViewCreated(view, savedInstanceState)
        val linearlayoutManager = LinearLayoutManager(context)
        binding.rvFollowing.layoutManager = linearlayoutManager
        val itemDecoration = DividerItemDecoration(context,linearlayoutManager.orientation)
        binding.rvFollowing.addItemDecoration(itemDecoration)

        followingListAdapter = FollowingAdapter(arrayListOf())
        rvFollowing.apply {
            layoutManager = linearlayoutManager
            adapter = followingListAdapter
        }

        followingViewModel.isLoading.observe(viewLifecycleOwner,{
            showLoading(it)
        })

        followingViewModel.following.observe(viewLifecycleOwner, {
                setFollowing(it)
                showLoading(false)
        })
        followingViewModel.listFollowing(username)
    }

    private fun setFollowing(following: List<FollowingResponse.FollowingResponseItem>) {
        val adapter = FollowingAdapter(following)
        binding.rvFollowing.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}