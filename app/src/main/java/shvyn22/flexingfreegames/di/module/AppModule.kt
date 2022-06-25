package shvyn22.flexingfreegames.di.module

import dagger.Module

@Module(
    includes = [
        DatabaseModule::class,
        NetworkModule::class,
        PreferencesModule::class,
        RepositoryModule::class,
        ViewModelModule::class
    ]
)
object AppModule