package com.abubakar.tvshowmanager.shows

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.abubakar.tvshowmanager.R
import com.abubakar.tvshowmanager.databinding.FragmentShowsBinding
import com.abubakar.tvshowmanager.util.RecyclerViewItemDecoration
import com.abubakar.tvshowmanager.util.ext.showOrHide
import com.abubakar.tvshowmanager.util.ext.viewBinding
import kotlinx.coroutines.InternalCoroutinesApi
import javax.inject.Inject


@OptIn(InternalCoroutinesApi::class)
class ShowsFragment @Inject constructor(
    private val factory: ViewModelProvider.Factory
) : Fragment() {

    private val viewModel by lazy {
        ViewModelProvider(this, factory).get(ShowsViewModel::class.java)
    }

    private val adapter = ShowAdapter()

    private lateinit var binding: FragmentShowsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = viewBinding(
            R.layout.fragment_shows,
            container
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()

        viewModel.state.observe(viewLifecycleOwner) {
            binding.toolbarLayout.toolbar.setNavigationOnClickListener { _ -> it.onTapBackButton() }
            binding.progress.showOrHide(it.state == ViewState.RequestState.Loading)
            binding.lblError.showOrHide(it.state == ViewState.RequestState.Error)
            when (it.state) {
                is ViewState.RequestState.Data -> {
                    adapter.submitList(it.state.list)
                }
                else -> {

                }
            }
        }

    }

    private fun setupRecyclerView() {
        val itemSpace = resources.getDimension(R.dimen.spacing_micro)
        val itemDecoration = RecyclerViewItemDecoration(space = itemSpace.toInt())

        binding.list.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@ShowsFragment.adapter
            overScrollMode = View.OVER_SCROLL_NEVER
            itemAnimator = null
        }
        binding.list.addItemDecoration(itemDecoration)
    }

}