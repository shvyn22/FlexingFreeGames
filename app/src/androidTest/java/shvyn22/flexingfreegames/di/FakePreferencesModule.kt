package shvyn22.flexingfreegames.di

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.test.core.app.ApplicationProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import shvyn22.flexingfreegames.data.preferences.PreferencesManager
import shvyn22.flexingfreegames.data.preferences.PreferencesManagerImpl
import shvyn22.flexingfreegames.util.TEST_DATASTORE_FILENAME
import java.io.File
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [PreferencesModule::class]
)
object FakePreferencesModule {

    @ExperimentalCoroutinesApi
    @Singleton
    @Provides
    fun provideScope(): CoroutineScope =
        CoroutineScope(UnconfinedTestDispatcher() + Job())

    @Singleton
    @Provides
    fun provideDataStore(app: Application, scope: CoroutineScope): DataStore<Preferences> =
        PreferenceDataStoreFactory.create(
            scope = scope,
            produceFile = {
                app.preferencesDataStoreFile(TEST_DATASTORE_FILENAME)
            }
        )

    @Singleton
    @Provides
    fun providePreferencesManager(
        dataStore: DataStore<Preferences>
    ): PreferencesManager = PreferencesManagerImpl(dataStore)
}

fun tearDownPreferencesDependencies(
    scope: CoroutineScope
) {
    File(
        ApplicationProvider
            .getApplicationContext<Context>()
            .filesDir,
        TEST_DATASTORE_FILENAME
    ).deleteRecursively()

    scope.cancel()
}