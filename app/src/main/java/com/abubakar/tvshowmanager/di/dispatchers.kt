package com.abubakar.tvshowmanager.di

import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers as KtDispatcher

interface Dispatchers {
    val IO: CoroutineDispatcher
    val Main: CoroutineDispatcher
}

class RealDispatchers @Inject constructor() : Dispatchers {
    override val IO: CoroutineDispatcher = KtDispatcher.IO
    override val Main: CoroutineDispatcher = KtDispatcher.Main
}