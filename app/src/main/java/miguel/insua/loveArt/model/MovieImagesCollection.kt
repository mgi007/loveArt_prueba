package miguel.insua.loveArt.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class MovieImagesCollection(

    @SerializedName("aspect_ratio")
    @Expose
    val aspectRatio: Number?,

    @SerializedName("file_path")
    @Expose
    val filePath: String?,

    @SerializedName("height")
    @Expose
    val height: Int?,

    @SerializedName("iso_639_1")
    @Expose
    val iso_639_1: String?,

    @SerializedName("vote_average")
    @Expose
    val vote_average: Double?,

    @SerializedName("vote_count")
    @Expose
    val vote_count: Int?,

    @SerializedName("width")
    @Expose
    val width: Int?

    ) {}

