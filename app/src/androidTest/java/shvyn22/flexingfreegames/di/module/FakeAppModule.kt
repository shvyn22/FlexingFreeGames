package shvyn22.flexingfreegames.di.module

import dagger.Module

@Module(
    includes = [
        FakeDatabaseModule::class,
        FakeNetworkModule::class,
        FakePreferencesModule::class,
        RepositoryModule::class,
        ViewModelModule::class
    ]
)
object FakeAppModule