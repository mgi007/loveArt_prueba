package miguel.insua.loveArt.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class MediaResponse(

    @SerializedName("page")
    @Expose
    val page: Int?,

    @SerializedName("results")
    @Expose
    val results: List<Media>?,

    @SerializedName("total_pages")
    @Expose
    val total_pages: Int?,

    @SerializedName("total_results")
    @Expose
    val total_results: Int?,

    ) {}

