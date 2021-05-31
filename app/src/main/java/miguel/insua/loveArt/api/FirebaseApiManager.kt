package miguel.insua.loveArt.api


import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class FirebaseApiManager {

    private val auth = FirebaseAuth.getInstance()

    private val db = FirebaseFirestore.getInstance()

    fun registerUser(email: String, password: String): Boolean {
        var ok: Boolean = false
        auth.createUserWithEmailAndPassword(
            email,
            password
        ).addOnCompleteListener {
            if (it.isSuccessful) {
                val user = auth.currentUser
                if (user != null) {
                    db.collection("users").document(user.uid).set(
                        hashMapOf(
                            "id" to user.uid,
                            "email" to user.email,
                            "password" to password
                        )
                    )
                    ok = true
                }
            }
        }
        return ok
    }


    fun accessWithEmail(email: String, password: String): Boolean {
        var ok: Boolean = false
        auth.signInWithEmailAndPassword(
            email,
            password
        ).addOnCompleteListener {
            if (it.isSuccessful) {
                ok = true
            }
        }
        return ok
    }


}