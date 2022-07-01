package shvyn22.flexingfreegames.di.module

import android.app.Application
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.rxjava3.RxPreferenceDataStoreBuilder
import androidx.datastore.rxjava3.RxDataStore
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.ExperimentalCoroutinesApi
import shvyn22.flexingfreegames.data.preferences.PreferencesManager
import shvyn22.flexingfreegames.data.preferences.PreferencesManagerImpl
import shvyn22.flexingfreegames.util.TEST_DATASTORE_FILENAME
import javax.inject.Singleton

@Module
object FakePreferencesModule {

    @Singleton
    @Provides
    fun provideDataStore(app: Application): RxDataStore<Preferences> =
        RxPreferenceDataStoreBuilder(app, TEST_DATASTORE_FILENAME).build()

    @ExperimentalCoroutinesApi
    @Singleton
    @Provides
    fun providePreferencesManager(
        dataStore: RxDataStore<Preferences>
    ): PreferencesManager = PreferencesManagerImpl(dataStore)
}