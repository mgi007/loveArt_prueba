package miguel.insua.loveArt.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class MediaRequestFirebase(
    @SerializedName("id")
    @Expose
    var id: Long? = -1,

    @SerializedName("title")
    @Expose
    var tittle: String? = "",

    @SerializedName("original_language")
    @Expose
    var og_language: String? = "",

    @SerializedName("poster_path")
    @Expose
    var imageUrl: String? = "",

    @SerializedName("vote_average")
    @Expose
    var rating: Double? = 0.0,

    @SerializedName("vote_count")
    @Expose
    var vote_count: Long? = 0,

    @SerializedName("release_date")
    @Expose
    var releaseDate: String? = ""

    ) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Long::class.java.classLoader) as? Long,
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Double::class.java.classLoader) as? Double,
        parcel.readValue(Long::class.java.classLoader) as? Long,
        parcel.readString()
    ) {
    }

    override fun describeContents(): Int {
        TODO("Not yet implemented")
    }

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        TODO("Not yet implemented")
    }

    companion object CREATOR : Parcelable.Creator<MediaRequestFirebase> {
        override fun createFromParcel(parcel: Parcel): MediaRequestFirebase {
            return MediaRequestFirebase(parcel)
        }

        override fun newArray(size: Int): Array<MediaRequestFirebase?> {
            return arrayOfNulls(size)
        }
    }
}

