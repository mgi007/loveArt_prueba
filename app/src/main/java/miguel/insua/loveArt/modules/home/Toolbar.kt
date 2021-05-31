package miguel.insua.loveArt.modules.home

import android.icu.text.CaseMap
import androidx.appcompat.app.AppCompatActivity
import miguel.insua.loveArt.R

class Toolbar {

    fun show(activities: AppCompatActivity, title: String, upButton: Boolean){
        activities.setSupportActionBar(activities.findViewById(R.id.toolbar))
        activities.supportActionBar?.title = title
        activities.supportActionBar?.setDisplayHomeAsUpEnabled(upButton)
    }
}