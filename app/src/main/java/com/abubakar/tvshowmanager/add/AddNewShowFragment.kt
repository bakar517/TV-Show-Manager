package com.abubakar.tvshowmanager.add

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.abubakar.tvshowmanager.R
import com.abubakar.tvshowmanager.databinding.FragmentAddNewShowBinding
import com.abubakar.tvshowmanager.util.ext.*
import kotlinx.coroutines.InternalCoroutinesApi
import javax.inject.Inject

@OptIn(InternalCoroutinesApi::class)
class AddNewShowFragment @Inject constructor(
    private val factory: ViewModelProvider.Factory
) : Fragment() {

    private val viewModel by lazy {
        ViewModelProvider(this, factory).get(AddNewShowViewModel::class.java)
    }
    private val dateTimeViewModel by lazy {
        ViewModelProvider(requireActivity(), factory).get(DataAndTimePickerViewModel::class.java)
    }

    private lateinit var binding: FragmentAddNewShowBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = viewBinding(
            R.layout.fragment_add_new_show,
            container
        )
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        dateTimeViewModel.state.observe(viewLifecycleOwner) {
            val dateTime = "${it.date} ${it.time}"
            if (dateTime.trim().isNotEmpty()) {
                binding.releaseDataField.editText!!.setText(dateTime)
            }
            viewModel.updateDateTime(it.date, it.time)
        }

        viewModel.state.observe(viewLifecycleOwner) {
            binding.toolbarLayout.toolbar.setNavigationOnClickListener { _ -> it.onTapBackButton() }
            binding.releaseDataField.editText!!.setOnClickListener { _ -> it.onDateTimePick() }
            binding.btnSaveTVShow.setOnClickListener { _ -> it.onTapSave(getMovieForm()) }

            binding.tvShowField.showErrorOrNot(it.showErrorOfTitleEmpty)
            binding.progress.showOrHide(it.state == ViewState.InsertionRequestState.Loading)
            blockUnblockInteraction(it.state == ViewState.InsertionRequestState.Loading)

            when (it.state) {
                is ViewState.InsertionRequestState.Data -> {
                    toast(R.string.save_successfully)
                    dateTimeViewModel.resetState()
                }
                is ViewState.InsertionRequestState.Error -> {
                    toast(R.string.something_went_wrong_message)
                }
                else -> {

                }
            }

        }
    }

    private fun getMovieForm() = MovieForm(
        title = binding.tvShowField.editText?.text.toString().trim(),
        season = binding.seasonsField.editText?.text.toString().trim()
    )

}