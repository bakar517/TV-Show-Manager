package com.abubakar.tvshowmanager.home

import com.abubakar.baselib.Navigator

fun Navigator.goToInsertion() =
    navigate(HomeFragmentDirections.actionGoToNewInsertion())

fun Navigator.goToMoviesList() =
    navigate(HomeFragmentDirections.actionGoToMoviesList())

