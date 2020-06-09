package com.wsoteam.mydietcoach.profile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.wsoteam.mydietcoach.R
import kotlinx.android.synthetic.main.profile_fragment.*

class ProfileFragment : Fragment(R.layout.profile_fragment) {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cvParent.setBackgroundResource(R.drawable.shape_profile_card)
        Glide.with(activity!!).load("https://i.ibb.co/XFfWh87/back1.jpg").into(ivHeadBack)
        Glide.with(activity!!).load("https://i.ibb.co/w68jTgy/rev9.jpg").into(ivAvatar)
    }
}