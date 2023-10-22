package com.example.apigram2.detail.follow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.apigram2.UserAdapter
import com.example.apigram2.data.model.ResponseUser
import com.example.apigram2.databinding.FragmentFollowsBinding
import com.example.apigram2.detail.DetailViewModel
import com.example.apigram2.utils.Hasil

class FollowsFragment : Fragment() {

    private var binding: FragmentFollowsBinding? = null
    private val adapter by lazy {
        UserAdapter {

        }
    }
    private val viewModel by activityViewModels<DetailViewModel>()
    var type = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFollowsBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.rvFollows?.apply {
            layoutManager = LinearLayoutManager(requireActivity())
            setHasFixedSize(true)
            adapter = this@FollowsFragment.adapter
        }

        when (type) {
            FOLLOWERS -> {
                viewModel.hasilFollowersUser.observe(viewLifecycleOwner, this::manageResultFollows)
            }
            FOLLOWING -> {
                viewModel.hasilFollowingUser.observe(viewLifecycleOwner, this::manageResultFollows)
            }
        }
    }

    private fun manageResultFollows(state: Hasil) {
        when (state) {
            is Hasil.Sukses<*> -> {
                adapter.setData(state.data as MutableList<ResponseUser.Item>)
            }
            is Hasil.Error -> {
                Toast.makeText(
                    requireActivity(),
                    state.exception.message.toString(),
                    Toast.LENGTH_SHORT
                ).show()
            }
            is Hasil.Loading -> {
                binding?.progressBar?.isVisible = state.isLoading
            }
        }
    }

    companion object {
        const val FOLLOWING = 100
        const val FOLLOWERS = 101

        fun newInstance(type: Int) = FollowsFragment()
            .apply {
                this.type = type
            }
    }
}