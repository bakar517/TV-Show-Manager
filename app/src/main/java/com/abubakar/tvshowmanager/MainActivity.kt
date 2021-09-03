package com.abubakar.tvshowmanager

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentFactory
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import com.abubakar.tvshowmanager.di.retainedComponent
import com.abubakar.tvshowmanager.navigation.CustomNavigation
import com.abubakar.tvshowmanager.navigation.NavigateUp
import com.abubakar.tvshowmanager.navigation.RealNavigation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    private val fragmentManager get() = supportFragmentManager

    @Inject
    fun fragmentFactory(factory: FragmentFactory) {
        fragmentManager.fragmentFactory = factory
    }

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    @Inject
    lateinit var navigator: RealNavigation


    override fun onCreate(savedInstanceState: Bundle?) {
        retainedComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigator
            .channel
            .receiveAsFlow()
            .onEach(::handleNavigation)
            .launchIn(scope)

    }


    private fun handleNavigation(direction: NavDirections) {
        val controller = findNavController(R.id.nav_host)

        if (direction !is CustomNavigation && controller.hasAction(direction.actionId)) {
            controller.navigate(direction)
            return
        }
        when (direction) {
            is NavigateUp -> {
                controller.navigateUp()
            }
            else -> {
                val handled = controller.popBackStack()
                if (!handled) finish()

            }
        }
    }

    private fun NavController.hasAction(actionId: Int) =
        currentDestination?.getAction(actionId) != null

    override fun onDestroy() {
        scope.cancel()
        super.onDestroy()
    }
}