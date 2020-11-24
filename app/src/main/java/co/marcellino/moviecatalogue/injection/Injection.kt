package co.marcellino.moviecatalogue.injection

import co.marcellino.moviecatalogue.data.source.Repository
import co.marcellino.moviecatalogue.data.source.remote.RemoteDataSource

object Injection {

    fun provideRepository(): Repository {
        val remoteDataSource = RemoteDataSource.getInstance()
        return Repository.getInstance(remoteDataSource)
    }
}