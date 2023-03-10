package fo

import fo.utilities.Storage

class Movies {

    fun getAll(): Map<Int, Movie> {

        return Storage.movies.associateBy { it.id }
    }

    fun get(id: Int): Movie {
        val movie = getAll().values.firstOrNull{ it.id == id }

        if (movie != null) {
            return movie
        }

        return Movie(id = 0, name = "", genre = "", year = 0, synopsis = "", director = "")
    }

    fun byYear(year: Int): Map<Int, Movie> {
        return getAll().filterValues { it.year == year }

    }

    fun byGenre(genre: String): Map<Int, Movie> {
        return getAll().filterValues { it.genre == genre }
    }

    fun find(query: String): Map<Int, Movie> {
        return getAll().filterValues { it.name.contains(query, ignoreCase = true) }
    }

    fun remove(id: Int) {
        Storage.movies.removeAll{it.id == id}
    }




}