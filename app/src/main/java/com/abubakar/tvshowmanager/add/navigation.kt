package com.abubakar.tvshowmanager.add

import com.abubakar.baselib.Navigator
import com.abubakar.tvshowmanager.navigation.NavigateUp

fun Navigator.goToDateTimePicker() =
    navigate(AddNewShowFragmentDirections.actionGoToDateTimePicker())

fun Navigator.navigateUp() = navigate(NavigateUp)