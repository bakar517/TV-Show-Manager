package com.abubakar.tvshowmanager.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.abubakar.baselib.Navigator
import com.abubakar.tvshowmanager.R
import com.abubakar.tvshowmanager.databinding.FragmentHomeBinding
import com.abubakar.tvshowmanager.util.ext.viewBinding
import kotlinx.coroutines.InternalCoroutinesApi
import javax.inject.Inject

@OptIn(InternalCoroutinesApi::class)
class HomeFragment @Inject constructor(
    private val factory: ViewModelProvider.Factory,
    private val navigator: Navigator,
    ) : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = viewBinding(
            R.layout.fragment_home,
            container
        )
        binding.toolbarLayout.toolbar.navigationIcon = null
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnAddNew.setOnClickListener {
            navigator.goToInsertion()
        }

        binding.btnShowAll.setOnClickListener {
            navigator.goToMoviesList()
        }

    }
}