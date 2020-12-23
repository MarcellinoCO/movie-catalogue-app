package co.marcellino.moviecatalogue.injection

import android.content.Context
import co.marcellino.moviecatalogue.data.source.Repository
import co.marcellino.moviecatalogue.data.source.local.LocalDataSource
import co.marcellino.moviecatalogue.data.source.local.room.CatalogueDatabase
import co.marcellino.moviecatalogue.data.source.remote.RemoteDataSource
import co.marcellino.moviecatalogue.utils.AppExecutors

object Injection {

    fun provideRepository(context: Context): Repository {
        val database = CatalogueDatabase.getInstance(context)

        val remoteDataSource = RemoteDataSource.getInstance()
        val localDataSource = LocalDataSource.getInstance(database.catalogueDao())
        val appExecutors = AppExecutors()

        return Repository.getInstance(remoteDataSource, localDataSource, appExecutors)
    }
}