package com.abubakar.tvshowmanager.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.abubakar.tvshowmanager.R
import com.abubakar.tvshowmanager.databinding.FragmentDataandtimepickerBinding
import com.abubakar.tvshowmanager.util.ext.*
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import javax.inject.Inject

class DateAndTimePickerFragment @Inject constructor(
    private val factory: ViewModelProvider.Factory
) : BottomSheetDialogFragment() {

    private val viewModel by lazy {
        ViewModelProvider(requireActivity(), factory).get(DataAndTimePickerViewModel::class.java)
    }

    private lateinit var binding: FragmentDataandtimepickerBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = viewBinding(R.layout.fragment_dataandtimepicker, container)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.state.observe(viewLifecycleOwner) {

            binding.btnCancel.setOnClickListener { _ -> it.onTapCancel() }

            binding.btnNext.setOnClickListener { _ ->
                it.onTapNext(binding.datePicker.date())
            }

            binding.btnBack.setOnClickListener { _ -> it.onTapBack() }

            binding.btnDone.setOnClickListener { _ ->
                val time = binding.timePicker.time()
                it.onTapDone(time)
            }

            if (it.showDateSelection) {
                binding.timePickerGroup.hide()
                binding.datePickerGroup.show()
            } else {
                binding.timePickerGroup.show()
                binding.datePickerGroup.hide()
            }
        }
    }
}