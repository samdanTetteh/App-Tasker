package com.ijikod.apptasker.domain.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.MapKey
import dagger.Module
import javax.inject.Inject
import javax.inject.Provider
import kotlin.reflect.KClass

class ViewModelFactory @Inject constructor(
        private val creators: @JvmSuppressWildcards Map<Class<out ViewModel>, Provider<ViewModel>>
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        var creator: Provider<out ViewModel>? = creators[modelClass]
        creator?.let {
            for ((key, value) in creators) {
                if (modelClass.isAssignableFrom(key)) {
                    creator = value
                    break
                }
            }

            try {
                return it.get() as T
            } catch (e: Exception) {
                throw RuntimeException(e)
            }

        } ?: run {
            throw IllegalArgumentException("Unknown model class: $modelClass")
        }
    }
}

@Module
internal abstract class ViewModelBuilder {
    @Binds
    internal abstract fun bindViewModeFactory(
            factory: ViewModelFactory
    ) : ViewModelProvider.Factory
}

@Target(
        AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER
)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class ViewModelKey(val viewModel: KClass<out ViewModel>)