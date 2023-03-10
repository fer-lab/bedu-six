package fo

data class Movie(
    val id: Int,
    val name: String,
    val genre: String,
    val year: Int,
    val synopsis: String,
    val director: String
)
{

    fun isValid(): Boolean
    {
        return id > 0
    }
    fun remove()
    {
        Movies().remove(id)
    }


}