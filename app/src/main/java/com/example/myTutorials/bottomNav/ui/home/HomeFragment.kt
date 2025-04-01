package com.example.myTutorials.bottomNav.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.myTutorials.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textHome
        homeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        Log.i("HomeFragment", "onCreateView called")
        return root
    }

    override fun onResume() {
        super.onResume()
        Log.i("HomeFragment", "onResume called")
    }

    override fun onPause() {
        super.onPause()
        Log.i("HomeFragment", "onPause called")
    }

    override fun onStop() {
        super.onStop()
        Log.i("HomeFragment", "onStop called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("HomeFragment", "onDestroy called")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        Log.i("HomeFragment", "onDestroyView called")
    }
}