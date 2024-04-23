package com.github.enteraname74.remotedatasource

import com.github.enteraname74.domain.datasource.MusicFileDataSource
import com.github.enteraname74.domain.datasource.MusicInformationDataSource
import com.github.enteraname74.domain.datasource.UserDataSource
import com.github.enteraname74.remotedatasource.datasourceimpl.RemoteMusicFileDataSourceImpl
import com.github.enteraname74.remotedatasource.datasourceimpl.RemoteMusicInformationDataSourceImpl
import com.github.enteraname74.remotedatasource.datasourceimpl.RemoteUserDataSourceImpl
import org.koin.core.module.Module
import org.koin.dsl.module

/**
 * Dependencies injection module for the remote data source module.
 */
val remoteDataSourceModule: Module = module {
    single<MusicInformationDataSource> { RemoteMusicInformationDataSourceImpl() }
    single<MusicFileDataSource> { RemoteMusicFileDataSourceImpl() }
    single<UserDataSource> { RemoteUserDataSourceImpl() }
}