package com.github.enteraname74.viewmodel

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.github.enteraname74.domain.datasource.AlbumDataSource
import com.github.enteraname74.domain.datasource.ArtistDataSource
import com.github.enteraname74.domain.datasource.MusicFileDataSource
import com.github.enteraname74.domain.datasource.MusicInformationDataSource
import com.github.enteraname74.domain.datasource.PlaylistDataSource
import com.github.enteraname74.domain.model.Album
import com.github.enteraname74.domain.model.Artist
import com.github.enteraname74.domain.model.File
import com.github.enteraname74.event.MainScreenEvent
import com.github.enteraname74.state.MainScreenState
import com.github.enteraname74.strings.appStrings
import com.github.enteraname74.type.FetchingState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

open class HomeScreenModel(
    private val musicInformationDataSource: MusicInformationDataSource,
    private val musicFileDataSource: MusicFileDataSource,
    private val playlistDataSource: PlaylistDataSource,
    private val albumDataSource: AlbumDataSource,
    private val artistDataSource: ArtistDataSource,
) : ScreenModel {
    private val _state = MutableStateFlow(MainScreenState())
    val state = _state.asStateFlow()

    /**
     * Manage events of the main screen.
     */
    fun onEvent(event: MainScreenEvent) {
        screenModelScope.launch {
            when (event) {
                MainScreenEvent.FetchMusics -> fetchAllMusics()
                is MainScreenEvent.UploadMusic -> uploadFile(event.file)
                is MainScreenEvent.FetchPlaylists -> fetchAllPlaylists()
                is MainScreenEvent.FetchAlbums -> fetchAllAlbums()
                is MainScreenEvent.FetchArtists -> fetchAllArtists()
                is MainScreenEvent.CreatePlaylist -> createPlaylist(event.name)
            }
        }
    }

    /**
     * Fetch all music.
     */
    protected open suspend fun fetchAllMusics() {
        _state.update {
            it.copy(
                allMusicsState = FetchingState.Loading(
                    message = appStrings.fetchingAllMusics
                )
            )
        }
        _state.update {
            it.copy(
                allMusicsState = FetchingState.Success(
                    musicInformationDataSource.getAll()
                )
            )
        }
    }

    protected open suspend fun uploadFile(file: File) {
        _state.update {
            it.copy(
                allAlbumsState = FetchingState.Loading(
                    message = appStrings.fetchingAllAlbums
                ),
                allMusicsState = FetchingState.Loading(
                    message = appStrings.fetchingAllMusics
                ),
                allArtistsState = FetchingState.Loading(
                    message = appStrings.fetchingAllArtists
                )
            )
        }
        val fileUploaded = musicFileDataSource.uploadFile(file)
        if (fileUploaded) {
            fetchAllMusics()
            fetchAllAlbums()
            fetchAllArtists()
        }
    }


    suspend fun getAlbum(albumName: String, artistName: String): Album? {
        return albumDataSource.getByNameAndArtist(albumName, artistName)
    }

    suspend fun getArtist(artistName: String): Artist? {
        return artistDataSource.getByName(artistName)
    }

    /**
     * Fetch all playlists.
     */
    protected open suspend fun fetchAllPlaylists() {
        _state.update {
            it.copy(
                allPlaylistsState = FetchingState.Loading(
                    message = appStrings.fetchingAllPlaylists
                )
            )
        }
        _state.update {
            it.copy(
                allPlaylistsState = FetchingState.Success(
                    playlistDataSource.getAll()
                )
            )
        }
    }

    /**
     * Fetch all albums.
     */
    protected open suspend fun fetchAllAlbums() {
        _state.update {
            it.copy(
                allAlbumsState = FetchingState.Loading(
                    message = appStrings.fetchingAllAlbums
                )
            )
        }
        _state.update {
            it.copy(
                allAlbumsState = FetchingState.Success(
                    albumDataSource.getAll()
                )
            )
        }
    }

    /**
     * Fetch all artists.
     */
    protected open suspend fun fetchAllArtists() {
        _state.update {
            it.copy(
                allArtistsState = FetchingState.Loading(
                    message = appStrings.fetchingAllArtists
                )
            )
        }
        _state.update {
            it.copy(
                allArtistsState = FetchingState.Success(
                    artistDataSource.getAll()
                )
            )
        }
    }

    /**
     * Creates a playlist.
     */
    protected open suspend fun createPlaylist(name: String) {
        playlistDataSource.create(name)

        _state.update {
            it.copy(
                allPlaylistsState = FetchingState.Success(
                    playlistDataSource.getAll()
                )
            )
        }
    }

    // Optional
    override fun onDispose() {
        // ...
    }
}