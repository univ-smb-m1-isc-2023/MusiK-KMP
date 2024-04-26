package com.github.enteraname74.remotedatasource

import com.github.enteraname74.domain.datasource.AlbumDataSource
import com.github.enteraname74.domain.datasource.ArtistDataSource
import com.github.enteraname74.domain.datasource.AuthDataSource
import com.github.enteraname74.domain.datasource.LyricsDataSource
import com.github.enteraname74.domain.datasource.MusicFileDataSource
import com.github.enteraname74.domain.datasource.MusicInformationDataSource
import com.github.enteraname74.domain.datasource.PlaylistDataSource
import com.github.enteraname74.domain.datasource.UserDataSource
import com.github.enteraname74.remotedatasource.datasourceimpl.RemoteAuthDataSourceImpl
import com.github.enteraname74.remotedatasource.datasourceimpl.RemoteUserDataSourceImpl
import com.github.enteraname74.remotedatasource.datasourceimpl.RemoteAlbumDataSourceImpl
import com.github.enteraname74.remotedatasource.datasourceimpl.RemoteArtistDataSourceImpl
import com.github.enteraname74.remotedatasource.datasourceimpl.RemoteLyricsDataSourceImpl
import com.github.enteraname74.remotedatasource.datasourceimpl.RemoteMusicFileDataSourceImpl
import com.github.enteraname74.remotedatasource.datasourceimpl.RemoteMusicInformationDataSourceImpl
import com.github.enteraname74.remotedatasource.datasourceimpl.RemotePlaylistDataSourceImpl
import org.koin.core.module.Module
import org.koin.dsl.module

/**
 * Dependencies injection module for the remote data source module.
 */
val remoteDataSourceModule: Module = module {
    single<MusicInformationDataSource> { RemoteMusicInformationDataSourceImpl() }
    single<MusicFileDataSource> { RemoteMusicFileDataSourceImpl() }
    single<UserDataSource> { RemoteUserDataSourceImpl() }
    single<AuthDataSource> { RemoteAuthDataSourceImpl() }
    single<PlaylistDataSource> { RemotePlaylistDataSourceImpl() }
    single<AlbumDataSource> { RemoteAlbumDataSourceImpl() }
    single<ArtistDataSource> { RemoteArtistDataSourceImpl() }
    single<LyricsDataSource> { RemoteLyricsDataSourceImpl() }
}
