package miguel.insua.loveArt.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.util.*

data class MovieImages(

    @SerializedName("id")
    @Expose
    val id: Int?,

    @SerializedName("backdrops")
    @Expose
    val backdrops: List<MovieImagesCollection>?,

    @SerializedName("posters")
    @Expose
    val posters: List<MovieImagesCollection>?

    ) {}

