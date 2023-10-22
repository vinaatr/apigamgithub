package com.example.apigram2.detail

import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.ColorRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import coil.load
import com.example.apigram2.MainActivity
import com.example.apigram2.R
import com.example.apigram2.data.local.NoteRoomDb
import com.example.apigram2.data.model.ResponseDetailUser
import com.example.apigram2.data.model.ResponseUser
import com.example.apigram2.databinding.ActivityDetailBinding
import com.example.apigram2.detail.follow.FollowsFragment
import com.example.apigram2.utils.Hasil
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private val viewModel by viewModels<DetailViewModel> {
        DetailViewModel.Factory(NoteRoomDb(this))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val item = intent.getParcelableExtra<ResponseUser.Item>("item")
        val username = item?.login ?: ""

        viewModel.hasilDetailUser.observe(this) {hasilUser ->
            handleHasilUser(hasilUser)
        }
        viewModel.getDetailUser(username)

        val fragments = mutableListOf<Fragment>(
            FollowsFragment.newInstance(FollowsFragment.FOLLOWERS),
            FollowsFragment.newInstance(FollowsFragment.FOLLOWING)
        )
        val titleFragments = mutableListOf(
            getString(R.string.followers), getString(R.string.following),
        )
        val adapter = DetailAdapter(this, fragments)
        binding.viewpager.adapter = adapter

        TabLayoutMediator(binding.tab, binding.viewpager) { tab, posisi ->
            tab.text = titleFragments[posisi]
        }.attach()

        binding.tab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab?.position == 0) {
                    viewModel.getFollowers(username)
                } else {
                    viewModel.getFollowing(username)
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })

        viewModel.getFollowers(username)

        viewModel.hasilSuksesFavorite.observe(this) {
            binding.btnFavorite.changeIconColor(R.color.red)
        }

        viewModel.hasilDeleteFavorite.observe(this) {
            binding.btnFavorite.changeIconColor(R.color.white)
        }

        binding.btnFavorite.setOnClickListener {
            viewModel.setFavorite(item)
        }

        viewModel.findFavorite(item?.id ?: 0) {
            binding.btnFavorite.changeIconColor(R.color.red)
        }

        binding.btnBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun handleHasilUser(hasilUser: Hasil) {
        when (hasilUser) {
            is Hasil.Sukses<*> -> {
                val user = hasilUser.data as ResponseDetailUser
                binding.image.load(user.avatar_url)

                binding.nama.text = user.name
                binding.userLogin.text = "@"+user.login
                binding.bio.text = "bio : "+user.bio.toString()
                binding.tvfollowers.text = user.followers.toString()
                binding.tvfollowing.text = user.following.toString()
            }
            is Hasil.Error -> {
                Toast.makeText(this, hasilUser.exception.message.toString(), Toast.LENGTH_SHORT).show()
            }
            is Hasil.Loading -> {
                binding.progressBar.isVisible = hasilUser.isLoading
            }
        }
    }
}

fun FloatingActionButton.changeIconColor(@ColorRes color: Int) {
    imageTintList = ColorStateList.valueOf(ContextCompat.getColor(this.context, color))
}