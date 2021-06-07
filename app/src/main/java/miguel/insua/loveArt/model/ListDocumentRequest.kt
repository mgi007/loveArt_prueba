package miguel.insua.loveArt.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class ListDocumentRequest(
    @SerializedName("movies")
    @Expose
    val movies: ArrayList<Int> = arrayListOf<Int>()

): Serializable