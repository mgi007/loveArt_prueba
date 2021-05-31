package miguel.insua.loveArt.modules.lists

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import miguel.insua.loveArt.application.App
import miguel.insua.loveArt.model.Media
import miguel.insua.loveArt.model.User_List
import miguel.insua.loveArt.modules.base.BaseViewModel
import miguel.insua.loveArt.modules.home.HomeAdapter

class ListsViewModel(app: Application) : BaseViewModel(app) {

    lateinit var newList: () -> Unit

    private val auth = FirebaseAuth.getInstance()

    private val db = FirebaseFirestore.getInstance()

    init {
        (app as? App)?.component?.inject(this)
    }


    fun refreshData(): LiveData<MutableList<String>> {
        val userLists = MutableLiveData<MutableList<String>>()
        getUserListData().observeForever { lists ->
            userLists.value = lists
        }
        return userLists
    }

    fun newList(listName: String): Boolean{
        val user = auth.currentUser
        var ok = false
        if (user != null) {
            db.collection("users").document(user.uid)
                .collection("lists").document(listName)
                .set(
                    hashMapOf(
                        "name" to listName,
                    )
                )
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        ok = true
                    }
                }
        }
        return ok
    }

    fun deleteList(listName: String): Boolean {
        val user = auth.currentUser
        var ok = false
        if (user != null) {
            db.collection("users").document(user.uid)
                .collection("lists").document(listName)
                .delete()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        ok = true
                    }
                }
        }
        return ok
    }

    fun editListName(oldName: String, newName: String) {
        val user = auth.currentUser
        if (user != null) {
             val oldDocument = db.collection("users").document(user.uid)
                .collection("lists").document(oldName)
                .get()
        }
    }

    private fun getUserListData():LiveData<MutableList<String>> {
        val mutableData = MutableLiveData<MutableList<String>>()
        val user = auth.currentUser
        if (user != null) {
            db.collection("users").document(user.uid).collection("lists")
                .get().addOnSuccessListener { result ->
                    val userLists: MutableList<String> = mutableListOf<String>()
                    for (document in result) {
                        val nameList: String? = document.getString("name")
                        userLists.add(nameList.toString())
                    }
                    mutableData.value = userLists
                }
        }
        return mutableData
    }

}