package com.abubakar.tvshowmanager

import com.abubakar.tvshowmanager.di.Dispatchers
import io.mockk.impl.annotations.SpyK
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import kotlinx.coroutines.Dispatchers as KtDispatchers

@OptIn(ExperimentalCoroutinesApi::class)
open class BaseTest {

    val testDispatcher = TestCoroutineDispatcher()

    @SpyK
    var dispatchers = TestDispatchers(testDispatcher)

    @Before
    fun before() {
        KtDispatchers.setMain(testDispatcher)
    }

    @After
    fun after() {
        KtDispatchers.resetMain()
    }
}

@OptIn(ExperimentalCoroutinesApi::class)
class TestDispatchers constructor(dispatcher: TestCoroutineDispatcher) : Dispatchers {
    override val IO: CoroutineDispatcher = dispatcher
    override val Main: CoroutineDispatcher = dispatcher
}