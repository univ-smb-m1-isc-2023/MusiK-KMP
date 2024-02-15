package com.github.enteraname74.remotedatasource

import com.github.enteraname74.domain.service.MusicService
import com.github.enteraname74.remotedatasource.serviceimpl.RemoteMusicServiceImpl
import org.koin.core.module.Module
import org.koin.dsl.module

/**
 * Dependencies injection module for the remote data source module.
 */
val remoteDataSourceModule: Module = module {
    single<MusicService> { RemoteMusicServiceImpl() }
}