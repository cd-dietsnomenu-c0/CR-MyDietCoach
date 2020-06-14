package com.wsoteam.mydietcoach.common

import com.wsoteam.mydietcoach.App
import com.wsoteam.mydietcoach.common.db.entities.FavoriteEntity

object FavoritesHolder {

    private var favoritesList : MutableList<FavoriteEntity> = App.getInstance().db.dietDAO().getAllFavorites().toMutableList()


    fun addFavorite(index : Int){

    }

    fun removeFavorite(index : Int){

    }

    fun isFavorite(index : Int) : Boolean{
        var isHas = false
        for (i in favoritesList.indices){
            if (favoritesList[i].id == index){
                isHas = true
            }
        }
        return isHas
    }

}