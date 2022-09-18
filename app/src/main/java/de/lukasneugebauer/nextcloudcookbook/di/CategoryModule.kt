package de.lukasneugebauer.nextcloudcookbook.di

import com.dropbox.android.external.store4.Fetcher
import com.dropbox.android.external.store4.Store
import com.dropbox.android.external.store4.StoreBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import de.lukasneugebauer.nextcloudcookbook.category.data.dto.CategoryDto
import de.lukasneugebauer.nextcloudcookbook.category.data.repository.CategoryRepositoryImpl
import de.lukasneugebauer.nextcloudcookbook.category.domain.repository.CategoryRepository
import de.lukasneugebauer.nextcloudcookbook.core.util.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import javax.inject.Singleton

typealias CategoriesStore = Store<Any, List<CategoryDto>>

@Module
@InstallIn(SingletonComponent::class)
object CategoryModule {

    @ExperimentalCoroutinesApi
    @FlowPreview
    @Provides
    @Singleton
    fun provideCategories(apiProvider: ApiProvider): CategoriesStore {
        val ncCookbookApi = apiProvider.getNcCookbookApi()
            ?: throw NullPointerException("Nextcloud Cookbook API is null.")
        return StoreBuilder
            .from(Fetcher.of { ncCookbookApi.getCategories() })
            .build()
    }

    @Provides
    @Singleton
    fun provideCategoryRepository(
        categoriesStore: CategoriesStore,
        @IoDispatcher ioDispatcher: CoroutineDispatcher
    ): CategoryRepository = CategoryRepositoryImpl(categoriesStore, ioDispatcher)
}
