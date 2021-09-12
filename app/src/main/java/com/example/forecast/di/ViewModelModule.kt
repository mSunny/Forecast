package com.example.forecast.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.forecast.domain.*
import com.example.forecast.ui.add_location.AddLocationViewModel
import com.example.forecast.ui.forecast.ForecastViewModel
import com.example.forecast.ui.main.MainViewModel
import com.example.forecast.ui.select_location.SelectLocationViewModel
import dagger.MapKey
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import javax.inject.Provider
import kotlin.reflect.KClass

@Target(AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class ViewModelKey(val value: KClass<out ViewModel>)

@Module
class ViewModelModule {
    @Provides
    fun viewModelFactory(viewModels: MutableMap<Class<out ViewModel>, Provider<ViewModel>>): ViewModelProvider.Factory {
        return ViewModelFactory(viewModels)
    }

    @Provides
    @IntoMap
    @ViewModelKey(SelectLocationViewModel::class)
    fun selectLocationViewModel(requestSavedLocationsInteractor: RequestSavedLocationsInteractor,
                                removeLocationInteractor: RemoveLocationInteractor): ViewModel {
        return SelectLocationViewModel(requestSavedLocationsInteractor, removeLocationInteractor)
    }

    @Provides
    @IntoMap
    @ViewModelKey(AddLocationViewModel::class)
    fun addLocationViewModel(getPossibleLocationsInteractor: GetPossibleLocationsInteractor, addLocationInteractor: AddLocationInteractor): ViewModel {
        return AddLocationViewModel(getPossibleLocationsInteractor, addLocationInteractor)
    }

    @Provides
    @IntoMap
    @ViewModelKey(ForecastViewModel::class)
    fun addForecastViewModel(getForecastInteractor: GetForecastInteractor): ViewModel {
        return ForecastViewModel(getForecastInteractor)
    }

    @Provides
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    fun addMainViewModel(requestSavedLocationsInteractor: RequestSavedLocationsInteractor): ViewModel {
        return MainViewModel(requestSavedLocationsInteractor)
    }
}