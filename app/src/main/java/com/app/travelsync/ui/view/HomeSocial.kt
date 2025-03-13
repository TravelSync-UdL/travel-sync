package com.app.travelsync.ui.view

/**
 * Crea una publicació nova sobre un viatge. L'usuari pot escriure un text, afegir una imatge
 * i vincular-ho amb un viatge guardat.
 *
 * Rep els parametres: userId, tripId, content, images
 */
fun createPost(){
    /*TODO*/
}

/**
 *Permet editar el text i les imatges d'una publicació existent
 *
 * Rep els parametres: postId, newContent, newImages
 */
fun editPost(){
    /*TODO*/
}

/**
 * Elimina una publicació
 *
 * Rep el paramatre: postId
 */
fun deletePost(){
    /*TODO*/
}

/**
 * Retorna una llista de publicacions dels amics ordenades cronologicament
 */
fun getPosts(){
    /*TODO*/
}


/**
 * L'usuari pot donar m'agrada a una publicacio
 *
 * Rep els parametres: userId, postId
 */
fun likePost(){
    /*TODO*/
}

/**
 * Eliminar un like
 *
 * Rep els parametres: userId, postId
 */
fun unlikePost(){
    /*TODO*/
}

/**
 * Afegir un amica a la llista d'amics
 *
 * Rep els parametres: userId, friendId
 */
fun addFriend(){
    /*TODO*/
}

/**
 * Eliminar un amic de la teva llista
 *
 * Rep els parametres: userId, friendId
 */
fun removeFriend(){
    /*TODO*/
}

/**
 * Retorna la llisat de tots els anics que tens
 *
 * Rep els paramaetres: userId
 */
fun getFriendsList(){
    /*TODO*/
}

/**
 * Poder buscar possibles amics o usauris dins de al APP.
 *
 * Rep els paramaetres: querry
 */
fun searchUsers(){
    /*TODO*/
}

/**
 * Mostra tota la informacio d'un usuari
 *
 * Rep els parametres: userId
 */
fun viewUserProfile(){
    /*TODO*/
}

/**
 * Mostra els viatges que l'usuari comparteix
 *
 * Rep els parametres: userId
 */
fun viewUserTrips(){
    /*TODO*/
}

/**
 * Comparteix un viatge d'un usuari, amb els teus amics.
 *
 */
fun shareTrip(){
    /*TODO*/
}