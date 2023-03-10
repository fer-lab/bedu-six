package fo

import fo.utilities.Storage
import java.util.*

class Likes {


    private fun getAll(): Map<String, Like> {

        return Storage.likes.associateBy { it.id }
    }

    fun get(id: String): Like {

        val like = getAll().values.firstOrNull{ it.id == id }

        if (like != null) {
            return like
        }

        return Like(id = "", 0L, 0, 0)
    }


    fun userLike(userId: Int, movieId: Int): Like
    {
        val like = userLikes(userId).values.firstOrNull { it.movie == movieId }

        if (like != null)
        {
            return like
        }

        return Like("", 0L, 0, 0)
    }

    fun userLikes(userId: Int): Map<String, Like>
    {
        return getAll().values.filter { it.user == userId }.associateBy { it.id }
    }


    fun add(movieId: Int, userId: Int)
    {
        val newLike = Like(id = UUID.randomUUID().toString(), date = System.currentTimeMillis(), user=userId, movie=movieId)
        val user = Users().get(userId)
        val movie = Movies().get(movieId)

        if (!user.isValid())
        {
            throw Exception("El usuario no existe")
        }

        if (!movie.isValid())
        {
            throw Exception("La película no existe")
        }

        if (Storage.likes.none { it.user == newLike.user && it.movie == newLike.movie }) {
            Storage.likes.add(newLike)
        }

    }
    fun remove(userId: Int, movieId: Int)
    {
        val like = userLike(userId, movieId)

        if (like.isValid())
        {
            Storage.likes.removeAll{it.id == like.id}
        }

    }

}