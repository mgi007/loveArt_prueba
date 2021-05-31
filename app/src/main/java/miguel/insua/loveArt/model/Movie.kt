package miguel.insua.loveArt.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Movie(

    @SerializedName("id")
    @Expose
    val id: Int?,

    @SerializedName("title")
    @Expose
    val tittle: String?,

    @SerializedName("release_date")
    @Expose
    val releaseDate: String?,

    @SerializedName("overview")
    @Expose
    val overview: String?,

    @SerializedName("genres")
    @Expose
    val genres: List<MovieGenre>?,

    @SerializedName("poster_path")
    @Expose
    val imageUrl: String?,

    @SerializedName("spoken_languages")
    @Expose
    val spokenLanguages: List<MovieLanguages>?,

    @SerializedName("status")
    @Expose
    val status: String?,

    @SerializedName("tagline")
    @Expose
    val tagline: String?,

    @SerializedName("video")
    @Expose
    val video: Boolean?,

    @SerializedName("vote_average")
    @Expose
    val rating: Double?,

    @SerializedName("vote_count")
    @Expose
    val voteCount: Int?

    ) : Serializable