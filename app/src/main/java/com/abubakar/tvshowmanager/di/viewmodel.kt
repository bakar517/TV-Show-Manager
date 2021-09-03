package com.abubakar.tvshowmanager.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.abubakar.tvshowmanager.add.AddNewShowViewModel
import com.abubakar.tvshowmanager.add.DataAndTimePickerViewModel
import com.abubakar.tvshowmanager.shows.ShowsViewModel
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.multibindings.IntoMap
import javax.inject.Inject
import javax.inject.Provider
import kotlin.reflect.KClass

@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class ViewModelKey(val value: KClass<out ViewModel>)


@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: CustomViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(AddNewShowViewModel::class)
    abstract fun bindAddNewMovieModel(addNewShowViewModel: AddNewShowViewModel): ViewModel


    @Binds
    @IntoMap
    @ViewModelKey(ShowsViewModel::class)
    abstract fun bindMoviesViewModel(ShowsViewModel: ShowsViewModel): ViewModel


    @Binds
    @IntoMap
    @ViewModelKey(DataAndTimePickerViewModel::class)
    abstract fun bindDataAndTimePickerViewModel(dataAndTimePickerViewModel: DataAndTimePickerViewModel): ViewModel



}

class CustomViewModelFactory @Inject constructor(
    private val creators: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val creator = creators[modelClass] ?: creators.entries.firstOrNull {
            modelClass.isAssignableFrom(it.key)
        }?.value
        @Suppress("UNCHECKED_CAST")
        return creator?.get() as? T
            ?: throw IllegalArgumentException("unknown model class $modelClass")
    }
}

