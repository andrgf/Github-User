package com.example.githubuser.ui.detail.following

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.R
import com.example.githubuser.databinding.FragmentFollowBinding
import com.example.githubuser.ui.UserAdapter
import com.example.githubuser.ui.detail.DetailUserActivity


class FollowingFragment: Fragment(R.layout.fragment_follow) {

    private var _binding: FragmentFollowBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: UserAdapter
    private val viewModel: FollowingViewModel by activityViewModels()
    private lateinit var username: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        username = arguments?.getString(DetailUserActivity.EXTRA_USERNAME).toString()
        _binding = FragmentFollowBinding.bind(view)

        setUpAdapter()
        setUpAction()

    }

    private fun setUpAction() {
        binding.apply {
            rvFollow.setHasFixedSize(true)
            rvFollow.layoutManager = LinearLayoutManager(activity)
            rvFollow.adapter = adapter
        }

        showLoading(true)

        setUpViewModel()
    }

    private fun setUpViewModel() {
        viewModel.isLoading.observe(viewLifecycleOwner){
            showLoading(it)
        }

        viewModel.setListFollowing(username)
        viewModel.getListFollowing().observe(viewLifecycleOwner){
            if (it != null) {
                adapter.setList(it)
                showLoading(false)
            }
        }
    }

    private fun setUpAdapter() {
        adapter = UserAdapter()
        adapter.notifyDataSetChanged()
    }

    private fun showLoading(state: Boolean) {
        binding.apply {
            if (state) {
                progressBar.visibility = View.VISIBLE
            } else {
                progressBar.visibility = View.GONE
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}