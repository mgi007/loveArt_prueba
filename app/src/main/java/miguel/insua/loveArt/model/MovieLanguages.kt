package miguel.insua.loveArt.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class MovieLanguages(

    @SerializedName("english_name")
    @Expose
    val englishName: String?,

    @SerializedName("iso_639_1")
    @Expose
    val iso_639_1: String?,

    @SerializedName("name")
    @Expose
    val name: String?

    ) : Serializable {}