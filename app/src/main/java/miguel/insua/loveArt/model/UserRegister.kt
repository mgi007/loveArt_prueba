package miguel.insua.loveArt.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class UserRegister(
    @SerializedName("uid")
    @Expose
    val uid: String = "",
    @SerializedName("email")
    @Expose
    val email: String? = null,
    @SerializedName("lists")
    @Expose
    val lists: ArrayList<String> = arrayListOf<String>()

): Serializable