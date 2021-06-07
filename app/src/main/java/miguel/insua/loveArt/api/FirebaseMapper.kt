package miguel.insua.loveArt.api

import miguel.insua.loveArt.model.Media
import miguel.insua.loveArt.model.UserRegister


object FirebaseMapper {
    fun registerRequestMapper(user: UserRegister): Map<String, Any> {
        val data = HashMap<String, Any>()
        user.run {
            data["uid"] = uid as Any
            data["email"] = email as Any
            data["lists"] = lists as Any
        }
        return data
    }

    fun getMediaRequestMapper(media: Media): Map<String, Any> {
        val data = HashMap<String, Any>()
        media.run {
            data["id"] = id as Any
            data["tittle"] = tittle as Any
            data["original_language"] = og_language as Any
            data["poster_path"] = imageUrl as Any
            data["vote_average"] = rating as Any
            data["vote_count"] = vote_count as Any
            data["release_date"] = releaseDate as Any
        }
        return data
    }

}