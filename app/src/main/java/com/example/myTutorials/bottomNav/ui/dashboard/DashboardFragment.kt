package com.example.myTutorials.bottomNav.ui.dashboard

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.myTutorials.databinding.FragmentDashboardBinding

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textDashboard
        dashboardViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        Log.i("DashboardFragment", "onCreateView called")
        return root
    }

    override fun onResume() {
        super.onResume()
        Log.i("DashboardFragment", "onResume called")
    }

    override fun onPause() {
        super.onPause()
        Log.i("DashboardFragment", "onPause called")
    }

    override fun onStop() {
        super.onStop()
        Log.i("DashboardFragment", "onStop called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("DashboardFragment", "onDestroy called")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        Log.i("DashboardFragment", "onDestroyView called")
    }
}