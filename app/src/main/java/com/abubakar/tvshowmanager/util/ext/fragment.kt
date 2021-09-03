package com.abubakar.tvshowmanager.util.ext

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

inline fun <reified T : ViewBinding> Fragment.viewBinding(
    @LayoutRes id: Int,
    container: ViewGroup?
): T = DataBindingUtil.inflate(
    LayoutInflater.from(requireContext()),
    id, container, false
)

fun Fragment.toast(@StringRes id: Int) =
    Toast.makeText(requireContext(), id, Toast.LENGTH_SHORT).show()

fun Fragment.blockInteraction() {
    requireActivity().window.setFlags(
        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
    )
}

fun Fragment.unblockInteraction() {
    requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
}


fun Fragment.blockUnblockInteraction(block: Boolean) =
    if (block) blockInteraction() else unblockInteraction()