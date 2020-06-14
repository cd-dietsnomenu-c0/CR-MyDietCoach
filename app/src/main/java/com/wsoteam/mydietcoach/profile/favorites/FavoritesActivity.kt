package com.wsoteam.mydietcoach.profile.favorites

import android.content.Intent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.wsoteam.mydietcoach.App
import com.wsoteam.mydietcoach.Config
import com.wsoteam.mydietcoach.POJOS.interactive.Diet
import com.wsoteam.mydietcoach.R
import com.wsoteam.mydietcoach.ad.AdWorker
import com.wsoteam.mydietcoach.common.GlobalHolder
import com.wsoteam.mydietcoach.diets.list.ItemClick
import com.wsoteam.mydietcoach.diets.list.modern.article.DietAct
import com.wsoteam.mydietcoach.profile.favorites.controllers.FavoritesAdapter
import kotlinx.android.synthetic.main.favorites_activity.*

class FavoritesActivity : AppCompatActivity(R.layout.favorites_activity) {

    lateinit var adapter: FavoritesAdapter
    lateinit var dietList : MutableList<Diet>

    override fun onResume() {
        super.onResume()
        setUIState()
    }



    private fun setUIState() {
        if (App.getInstance().db.dietDAO().getAllFavorites().isEmpty()){
            showEmptyState()
        }else{
            showFilledState()
        }
    }

    private fun showFilledState() {
        lavEmpty.visibility = View.INVISIBLE
        tvEmptyTitle.visibility = View.INVISIBLE
        tvEmptyText.visibility = View.INVISIBLE
        rvFavorites.visibility = View.VISIBLE
        fillList()
    }

    private fun fillList() {
        adapter = FavoritesAdapter(getFavorites(), object : ItemClick{
            override fun click(position: Int) {
                startActivity(Intent(this@FavoritesActivity, DietAct::class.java)
                        .putExtra(Config.NEW_DIET, dietList[position])
                        .putExtra(Config.NEED_SHOW_CONNECT, true))
            }

            override fun newDietsClick() {
            }
        }, arrayListOf())
        rvFavorites.layoutManager = LinearLayoutManager(this)
        rvFavorites.adapter = adapter
    }

    private fun getFavorites(): MutableList<Diet> {
        var list = mutableListOf<Diet>()
        val dbList = App.getInstance().db.dietDAO().getAllFavorites()
        for (i in dbList.indices){
            for (j in GlobalHolder.getGlobal().allDiets.dietList.indices){
                if (GlobalHolder.getGlobal().allDiets.dietList[j].index == dbList[i].id){
                    list.add(GlobalHolder.getGlobal().allDiets.dietList[j])
                }
            }
        }
        dietList = list
        return list
    }

    private fun showEmptyState() {
        lavEmpty.visibility = View.VISIBLE
        tvEmptyTitle.visibility = View.VISIBLE
        tvEmptyText.visibility = View.VISIBLE
        rvFavorites.visibility = View.INVISIBLE
    }


}