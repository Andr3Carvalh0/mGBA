package io.mgba.di.modules

import javax.inject.Singleton

import dagger.Module
import dagger.Provides
import io.mgba.data.remote.interfaces.IRequest
import io.mgba.data.remote.RetrofitClient
import io.mgba.model.io.FilesManager
import io.mgba.model.io.LocalDB
import io.mgba.model.interfaces.IDatabase
import io.mgba.model.interfaces.IFilesManager
import io.mgba.model.interfaces.ILibrary
import io.mgba.model.Library
import io.mgba.utilities.IDeviceManager
import io.mgba.utilities.IResourcesManager
import io.mgba.mgba

@Module
class ModelModule(private val application: mgba, private val path: String) {

    @Provides
    @Singleton
    fun provideLibrary(): ILibrary {
        return Library(application)
    }

    @Provides
    @Singleton
    fun provideDatabase(): IDatabase {
        return LocalDB(application)
    }

    @Provides
    @Singleton
    fun provideFileManager(): IFilesManager {
        return FilesManager(path)
    }

    @Provides
    fun provideDeviceManager(): IDeviceManager {
        return application
    }

    @Provides
    fun provideResourceManager(): IResourcesManager {
        return application
    }


    @Provides
    fun provideRetrofitClient(): IRequest {
        val retrofitClient = RetrofitClient()
        return retrofitClient.getClient(IRequest.BASE_URL).create(IRequest::class.java!!)
    }

}
