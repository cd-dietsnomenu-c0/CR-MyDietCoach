package com.wsoteam.mydietcoach.profile

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.wsoteam.mydietcoach.R
import com.wsoteam.mydietcoach.common.db.entities.FavoriteEntity
import com.wsoteam.mydietcoach.profile.dialogs.DevelopmentDialog
import com.wsoteam.mydietcoach.profile.dialogs.NameDialog
import com.wsoteam.mydietcoach.profile.favorites.FavoritesActivity
import com.wsoteam.mydietcoach.utils.PrefWorker
import kotlinx.android.synthetic.main.profile_fragment.*

class ProfileFragment : Fragment(R.layout.profile_fragment) {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvDate.text = "${resources.getString(R.string.together)} ${PrefWorker.getFirstTime()}"
        cvParent.setBackgroundResource(R.drawable.shape_profile_card)
        Glide.with(activity!!).load("https://i.ibb.co/XFfWh87/back1.jpg").into(ivHeadBack)
        Glide.with(activity!!).load("https://i.ibb.co/w68jTgy/rev9.jpg").into(ivAvatar)

        btnFavorites.setOnClickListener {
            startActivity(Intent(activity, FavoritesActivity::class.java))
        }

        btnTrophy.setOnClickListener {
            DevelopmentDialog().show(childFragmentManager, "DevelopmentDialog")
        }

        tvName.setOnClickListener {
            NameDialog().show(childFragmentManager, "NameDialog")
        }
    }


}