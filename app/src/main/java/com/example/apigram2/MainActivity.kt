package com.example.apigram2

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.apigram2.data.local.PreferenceManager
import com.example.apigram2.data.model.ResponseUser
import com.example.apigram2.databinding.ActivityMainBinding
import com.example.apigram2.detail.DetailActivity
import com.example.apigram2.favorite.FavoriteActivity
import com.example.apigram2.setting.SettingActivity
import com.example.apigram2.utils.Hasil

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val adapter by lazy {
        UserAdapter { user ->
            createDetailActivityIntent(this, user)
        }
    }
    private val viewModel by viewModels<MainViewModel> {
        MainViewModel.Factory(PreferenceManager(this))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        observeTheme()

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.adapter = adapter

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                viewModel.getUser(p0.toString())
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean = false

        })

        binding.btnFav.setOnClickListener {
            createFavoriteActivityIntent(this)
        }
        binding.btnSet.setOnClickListener {
            createSettingActivityIntent(this)
        }
        viewModel.hasilUser.observe(this) { hasilUser ->
            handleHasilUser(hasilUser)
        }
        viewModel.getUser()
    }

    private fun handleHasilUser(hasilUser: Hasil) {
        when (hasilUser) {
            is Hasil.Sukses<*> -> {
                adapter.setData(hasilUser.data as MutableList<ResponseUser.Item>)
            }
            is Hasil.Error -> {
                Toast.makeText(this, hasilUser.exception.message.toString(), Toast.LENGTH_SHORT).show()
            }
            is Hasil.Loading -> {
                binding.progressBar.isVisible = hasilUser.isLoading
            }
        }
    }

    private fun observeTheme() {
        viewModel.getTheme().observe(this) {
            if (it) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }
}

    fun createDetailActivityIntent(context: Context, user: ResponseUser.Item) {
        val intent = Intent(context, DetailActivity::class.java).apply {
            putExtra("item", user)
        }
        context.startActivity(intent)
    }

    fun createFavoriteActivityIntent(context: Context) {
        val intent = Intent(context, FavoriteActivity::class.java)
        context.startActivity(intent)
    }

    fun createSettingActivityIntent(context: Context) {
        val intent = Intent(context, SettingActivity::class.java)
        context.startActivity(intent)
    }
