package com.github.enteraname74.strings

/**
 * French implementation of the Application strings.
 */
object FrStrings: ApplicationStrings {
    override val welcome = "Bienvenue sur Musik !"

    /***** Fetching strings ********/
    override val fetchingAllMusics = "Récupération de toutes les musiques..."
    override val noMusicsFound = "Aucune musique n'a été trouvée !"

    /**** Search screen strings ******/
    override val allMusicsPlaceholder = "Rechercher une musique"

    /**** User screen strings ******/
    override val username = "Nom d'utilisateur"
    override val password = "Mot de passe"
    override val connect = "Connexion"
}