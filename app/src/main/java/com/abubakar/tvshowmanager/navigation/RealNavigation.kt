package com.abubakar.tvshowmanager.navigation

import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.navigation.NavDirections
import com.abubakar.baselib.Navigator
import com.abubakar.tvshowmanager.di.Retained
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.UNLIMITED
import javax.inject.Inject

const val NO_ACTION = -1

@Retained
class RealNavigation @Inject constructor() : Navigator {

    val channel = Channel<NavDirections>(UNLIMITED)

    override fun navigate(direction: NavDirections) {
        channel.trySend(direction)
    }

}

sealed class CustomNavigation : NavDirections

object PopBackStack : CustomNavigation() {
    override fun getActionId() = NO_ACTION

    override fun getArguments() = bundleOf()
}

object NavigateUp : CustomNavigation() {
    override fun getActionId() = NO_ACTION

    override fun getArguments() = bundleOf()
}


fun Navigator.popBackStack() = navigate(PopBackStack)
