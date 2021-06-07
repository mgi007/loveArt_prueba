package miguel.insua.loveArt.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class User(
    @SerializedName("uid")
    @Expose
    val uid: String = "",
    @SerializedName("email")
    @Expose
    val email: String? = null,
    @SerializedName("lists")
    @Expose
    val lists: ArrayList<String> = arrayListOf<String>(),
    @SerializedName("name")
    @Expose
    val name: String? = null,
    @SerializedName("gender")
    @Expose
    val gender: Gender? = null,
    @SerializedName("age")
    @Expose
    val age: Int? = null,
    @SerializedName("location")
    @Expose
    val location: Location? = null

): Serializable