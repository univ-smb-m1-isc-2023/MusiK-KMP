package com.github.enteraname74.strings

/**
 * French implementation of the Application strings.
 */
object FrStrings : ApplicationStrings {
    override val welcome = "Bienvenue sur Musik !"

    /***** Fetching strings ********/
    override val fetchingAllMusics = "Récupération de toutes les musiques..."
    override val noMusicsFound = "Aucune musique n'a été trouvée !"
    override val fetchingAllPlaylists = "Récupération de toutes les listes de lecture..."
    override val noPlaylistsFound = "Aucune liste de lecture n'a été trouvée !"
    override val fetchingAllAlbums = "Récupération de tous les albums..."
    override val noAlbumsFound = "Aucun album n'a été trouvé !"
    override val fetchingAllArtists = "Récupération de tous les artistes..."
    override val noArtistsFound = "Aucun artiste n'a été trouvé !"
    override val fetchingLyrics = "Récupération des paroles..."
    override val noLyricsFound = "Aucune parole trouvée!"

    /**** Search screen strings ******/
    override val allMusicsPlaceholder = "Rechercher une musique"

    /**** User screen strings ******/
    override val username = "Nom d'utilisateur"
    override val password = "Mot de passe"
    override val connect = "Connexion"

    /**** Player screen ****/
    override val lyrics = "Paroles"
    override val close = "Fermer"

    /**** Playlists strings *****/
    override val createPlaylist = "Créer une liste de lecture"
    override val playlistName = "Nom de la liste de lecture"
    override val create = "Créer"
}
