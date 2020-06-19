package com.wsoteam.mydietcoach.profile

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.wsoteam.mydietcoach.R
import com.wsoteam.mydietcoach.common.db.entities.FavoriteEntity
import com.wsoteam.mydietcoach.profile.controllers.BacksAdapter
import com.wsoteam.mydietcoach.profile.controllers.IBacks
import com.wsoteam.mydietcoach.profile.dialogs.DevelopmentDialog
import com.wsoteam.mydietcoach.profile.dialogs.NameDialog
import com.wsoteam.mydietcoach.profile.favorites.FavoritesActivity
import com.wsoteam.mydietcoach.profile.toasts.IntroToast
import com.wsoteam.mydietcoach.utils.PrefWorker
import kotlinx.android.synthetic.main.bottom_sheet_backs.*
import kotlinx.android.synthetic.main.profile_fragment.*

class ProfileFragment : Fragment(R.layout.profile_fragment) {

    var nameDialog = NameDialog()
    val MAX_ATEMPT_INTRO = 2

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvDate.text = "${resources.getString(R.string.together)} ${PrefWorker.getFirstTime()}"
        cvParent.setBackgroundResource(R.drawable.shape_profile_card)
        setBack(PrefWorker.getBack()!!)
        Glide.with(activity!!).load("https://i.ibb.co/w68jTgy/rev9.jpg").into(ivAvatar)

        nameDialog.setTargetFragment(this, 0)

        var bsBehavior = BottomSheetBehavior.from(llBottomSheet)

        btnFavorites.setOnClickListener {
            startActivity(Intent(activity, FavoritesActivity::class.java))
        }

        btnTrophy.setOnClickListener {
            DevelopmentDialog().show(activity!!.supportFragmentManager, "DevelopmentDialog")
        }

        tvName.setOnClickListener {
            nameDialog.show(activity!!.supportFragmentManager, "NameDialog")
        }

        ivHeadBack.setOnClickListener {
            bsBehavior.state = BottomSheetBehavior.STATE_EXPANDED
            PrefWorker.setCountIntro(MAX_ATEMPT_INTRO)
        }
        rvBacks.layoutManager = GridLayoutManager(activity, 2)
        rvBacks.adapter = BacksAdapter(resources.getStringArray(R.array.backgrounds_profile), object : IBacks{
            override fun choiceBack(position: Int) {
                PrefWorker.setBack(position)
                setBack(position)
                bsBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
            }
        })
    }



    private fun setBack(number : Int){
        var url = resources.getStringArray(R.array.backgrounds_profile)[number]
        Glide.with(this).load(url).into(ivHeadBack)
    }

     fun bindName() {
        if (PrefWorker.getName() == ""){
            tvName.text = resources.getString(R.string.def_name)
        }else{
            tvName.text = PrefWorker.getName()
        }
    }

    override fun onResume() {
        super.onResume()
        bindName()
        if (PrefWorker.getCountIntro()!! < MAX_ATEMPT_INTRO){
            IntroToast.show(activity!!)
            var count = PrefWorker.getCountIntro()!!
            count++
            PrefWorker.setCountIntro(count)
        }
    }


}