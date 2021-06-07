package miguel.insua.loveArt.modules.lists

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import miguel.insua.loveArt.api.FirebaseMapper
import miguel.insua.loveArt.application.App
import miguel.insua.loveArt.model.Media
import miguel.insua.loveArt.model.User
import miguel.insua.loveArt.model.UserRegister
import miguel.insua.loveArt.model.User_List
import miguel.insua.loveArt.modules.base.BaseViewModel
import miguel.insua.loveArt.modules.home.HomeAdapter

class ListsViewModel(app: Application) : BaseViewModel(app) {

    var userData: UserRegister = UserRegister()

    lateinit var newList: () -> Unit

    private val auth = FirebaseAuth.getInstance()

    val db = FirebaseFirestore.getInstance()

    lateinit var uid: String


    init {
        (app as? App)?.component?.inject(this)
    }


    fun getUserById(id: String) {
        val docRef = db.collection("users").document(id)
        docRef.get()

            .addOnCompleteListener {
                if (it != null) {
                    val result = it.result
                    if (result != null) {
                        val userRegister = result.toObject(UserRegister::class.java)
                        if (userRegister != null) {
                            if (userRegister.email != null) {
                                userData = userRegister
                            }

                        }

                    }

                }
            }
    }

    fun addList(listName: String) {
        var add: Boolean = true
        getUserById(uid)
        for (i in 0 until userData.lists.size) {
            var name: String = userData.lists[i]
            if (name == listName) {
                add = false
            }
        }
        if (add) {
            userData.lists.add(listName)
        }
    }

    fun deleteList(listName: String) {
        getUserById(uid)
        userData.lists.remove(listName)
    }

    fun editListName(oldName: String, newName: String) {
        getUserById(uid)
        userData.lists.add(newName)
        userData.lists.remove(oldName)
    }

    fun refreshUserLists(id: String, adapter: ListAdapter) {
        val docRef = db.collection("users").document(id)
        docRef.get()

            .addOnCompleteListener {
                if (it != null) {
                    val result = it.result
                    if (result != null) {
                        val userRegister = result.toObject(UserRegister::class.java)
                        if (userRegister != null) {
                            if (userRegister.email != null) {
                                userData = userRegister
                            }

                            if (userData.lists != null && userData.lists.size > 0) {
                                adapter.setListData(userData.lists)
                                adapter.notifyDataSetChanged()
                            }

                        }

                    }

                }
            }
    }


    fun refreshData(): LiveData<MutableList<String>> {
        val userLists = MutableLiveData<MutableList<String>>()
        getUserListData().observeForever { lists ->
            userLists.value = lists
        }
        return userLists
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