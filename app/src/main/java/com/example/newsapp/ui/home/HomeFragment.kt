package com.example.newsapp.ui.home

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.data.model.NewData
import com.example.newsapp.R
import com.example.newsapp.databinding.FragmentHomeBinding
import com.example.newsapp.helper.isEditTextValid
import com.example.newsapp.ui.adapter.NewsAdapter
import com.patient.base.BaseFragment
import com.patient.data.cashe.PreferencesGateway
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment(R.layout.fragment_home) {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModels()
    private val adapter = NewsAdapter()
    private lateinit var preferenc: PreferencesGateway


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)
        preferenc = activity?.applicationContext?.let { PreferencesGateway(it) }!!
        if (preferenc.load("theme", "").equals("light")) {
            binding.them.setImageResource(R.drawable.moon_svgrepo_com)
        } else {
            binding.them.setImageResource(R.drawable.moon_svg)
        }
        getAllData()
        setUpNewRv()
        search()
        updateTheme()
    }

    fun search() {
        binding.btnSearch.setOnClickListener {
            if (binding.etQuery.isEditTextValid()) {
                viewModel.getNewsHeadLines(
                    com.app.data.remote.Constants.PrefKeys.APP_KEY,
                    binding.etQuery.text.toString(), sortedBy = "publishedAt"
                )
                viewModel.clearData()
            }
        }
    }

    fun updateTheme() {

        binding.them.setOnClickListener {
            if (preferenc.load("theme", "").equals("light")) {
                preferenc.save("theme", "dark")
                binding.them.setImageResource(R.drawable.moon_svgrepo_com)
                AppCompatDelegate.setDefaultNightMode(
                    AppCompatDelegate.MODE_NIGHT_YES
                )

            } else {
                preferenc.save("theme", "light")
                binding.them.setImageResource(R.drawable.moon_svg)
                AppCompatDelegate.setDefaultNightMode(
                    AppCompatDelegate.MODE_NIGHT_NO
                )
            }
        }

    }

    fun getAllData() {
        viewModel.getNewsHeadLines(
            com.app.data.remote.Constants.PrefKeys.APP_KEY,
            "n",
            sortedBy = null
        )
    }

    fun setUpNewRv() {
        binding.recyclerView.adapter = adapter
        binding.recyclerView.setHasFixedSize(false)
        handleSharedFlow(viewModel.newsFlow, onSuccess = {
            it as List<NewData>
            adapter.AddAll(it)
            hideKeyboard()
        })
        adapter.onItemClicked = { data ->
            val bundle = Bundle()
            bundle.putString("image", data.urlToImage)
            bundle.putString("title", data.title)
            bundle.putString("date", data.publishedAt)
            bundle.putString("webView", data.url)
            bundle.putString("Source", data.author)
            bundle.putString("Description", data.description)
            findNavController().navigate(R.id.action_homeFragment_to_detailsFragment, bundle)
        }
    }

}