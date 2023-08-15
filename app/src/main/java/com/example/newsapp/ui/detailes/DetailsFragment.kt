package com.example.newsapp.ui.detailes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import com.example.newsapp.R
import com.example.newsapp.databinding.FragmentDetailsBinding
import com.example.newsapp.helper.loadImage
import com.patient.base.BaseFragment

class DetailsFragment : BaseFragment(R.layout.fragment_details) {
    private lateinit var binding: FragmentDetailsBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding= FragmentDetailsBinding.bind(view)
        DisplayData()
    }

    fun DisplayData(){
        binding.apply {
            arguments?.getString("image")?.let { imageView.loadImage(it) }
            arguments?.getString("title")?.let { tvTitle.text=it }
            arguments?.getString("date")?.let { tvDate.text=it }
            arguments?.getString("Source")?.let { tvSource.text=it }
            arguments?.getString("Description")?.let { tvDesc.text=it }
            webView.settings.javaScriptEnabled=true
            webView.webViewClient = WebViewClient()
            webView.settings.useWideViewPort = true
            webView.settings.domStorageEnabled = true
            arguments?.getString("webView")?.let { webView.loadUrl(it) }
        }
    }

}