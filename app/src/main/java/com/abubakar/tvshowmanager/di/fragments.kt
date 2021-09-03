package com.abubakar.tvshowmanager.di

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.abubakar.tvshowmanager.add.AddNewShowFragment
import com.abubakar.tvshowmanager.add.DateAndTimePickerFragment
import com.abubakar.tvshowmanager.home.HomeFragment
import com.abubakar.tvshowmanager.shows.ShowsFragment
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.multibindings.IntoMap
import javax.inject.Inject
import javax.inject.Provider
import kotlin.reflect.KClass

@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class FragmentKey(val value: KClass<out Fragment>)


@Module
abstract class FragmentModule {

    @Binds
    abstract fun factory(factory: CustomFragmentFactory): FragmentFactory

    @Binds
    @IntoMap
    @FragmentKey(HomeFragment::class)
    abstract fun homeFragment(fragment: HomeFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(AddNewShowFragment::class)
    abstract fun addNewShowFragment(addNewShowFragment: AddNewShowFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(ShowsFragment::class)
    abstract fun showsFragment(showsFragment: ShowsFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(DateAndTimePickerFragment::class)
    abstract fun dateAndTimePickerFragment(dateAndTimePickerFragment: DateAndTimePickerFragment): Fragment

}

class CustomFragmentFactory @Inject constructor(
    private val creators: Map<Class<out Fragment>, @JvmSuppressWildcards Provider<Fragment>>
) : FragmentFactory() {
    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        val modelClass = loadFragmentClass(classLoader, className)
        val provider = creators[modelClass]
            ?: creators.entries.firstOrNull { modelClass.isAssignableFrom(it.key) }?.value
        return provider?.get() ?: super.instantiate(classLoader, className)
    }
}

