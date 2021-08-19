package com.diets.weightloss.presentation.profile

import android.app.Activity.RESULT_OK
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.Intent.*
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.diets.weightloss.App
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.diets.weightloss.Config
import com.diets.weightloss.R
import com.diets.weightloss.presentation.premium.PremiumHostActivity
import com.diets.weightloss.utils.analytics.Ampl
import com.diets.weightloss.presentation.profile.controllers.BacksAdapter
import com.diets.weightloss.presentation.profile.controllers.IBacks
import com.diets.weightloss.presentation.profile.dialogs.DevelopmentDialog
import com.diets.weightloss.utils.water.WaterCounter
import com.diets.weightloss.presentation.profile.dialogs.LanguageWarningDialog
import com.diets.weightloss.presentation.profile.dialogs.NameDialog
import com.diets.weightloss.presentation.profile.favorites.FavoritesActivity
import com.diets.weightloss.presentation.profile.language.ChoiceLangActivity
import com.diets.weightloss.presentation.profile.measurments.MeasActivity
import com.diets.weightloss.presentation.profile.toasts.DeniedPermToast
import com.diets.weightloss.presentation.profile.toasts.IntroToast
import com.diets.weightloss.utils.PreferenceProvider
import kotlinx.android.synthetic.main.bottom_sheet_backs.*
import kotlinx.android.synthetic.main.fragment_water_tracker.*
import kotlinx.android.synthetic.main.profile_fragment.*
import java.io.File
import java.lang.Exception


class ProfileFragment : Fragment(R.layout.profile_fragment), LanguageWarningDialog.Callbacks {

    private var nameDialog = NameDialog()
    private val MAX_ATEMPT_INTRO = 2
    private val CAMERA_REQUEST = 0
    private val CAMERA_PERMISSION_REQUEST = 1

    private val EMPTY_MEAS = " - "

    private lateinit var bsBehavior: BottomSheetBehavior<LinearLayout>

    private lateinit var uri: Uri

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Ampl.openProfile()
        tvDate.text = "${resources.getString(R.string.together)} ${PreferenceProvider.getFirstTime()}"
        cvParent.setBackgroundResource(R.drawable.shape_profile_card)
        setBack(PreferenceProvider.getBack()!!)
        setAvatar()
        setClickListeners()

        nameDialog.setTargetFragment(this, 0)
        bsBehavior = BottomSheetBehavior.from(llBottomSheet)

        rvBacks.layoutManager = GridLayoutManager(activity, 2)
        rvBacks.adapter = BacksAdapter(resources.getStringArray(R.array.backgrounds_profile), object : IBacks {
            override fun choiceBack(position: Int) {
                PreferenceProvider.setBack(position)
                setBack(position)
                bsBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
            }
        })


        bsBehavior.setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
                    ivBSBackground.visibility = View.GONE
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
            }
        })

        if (PreferenceProvider.isHasPremium){
            lavPremium.visibility = View.VISIBLE
        }
    }

    private fun setClickListeners() {
        btnFavorites.setOnClickListener {
            Ampl.openFavorites()
            startActivity(Intent(activity, FavoritesActivity::class.java))
        }

        btnTrophy.setOnClickListener {
            startActivity(PremiumHostActivity.getIntentProfile(requireActivity()))
        }

        tvName.setOnClickListener {
            //nameDialog.show(activity!!.supportFragmentManager, "NameDialog")
            openMeasActivity()
        }

        ivHeadBack.setOnClickListener {
            Ampl.openWallpapers()
            bsBehavior.state = BottomSheetBehavior.STATE_EXPANDED
            ivBSBackground.visibility = View.VISIBLE
            PreferenceProvider.setCountIntro(MAX_ATEMPT_INTRO)
        }

        btnGrade.setOnClickListener {
            if (!Config.FOR_TEST) {
                Ampl.openSettingsGrade()
                var intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse("market://details?id=" + activity!!.packageName)
                startActivity(intent)
            }
        }

        btnReport.setOnClickListener {
            if (!Config.FOR_TEST) {
                Ampl.sendClaim()
                var intent = Intent(Intent(ACTION_SENDTO))
                intent.type = "message/rfc822"
                intent.data = Uri.parse("mailto:")
                intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(getString(R.string.dev_email)));
                intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.report_title));
                intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.report_text));
                try {
                    startActivity(createChooser(intent, getString(R.string.report_wait)))
                } catch (ex: ActivityNotFoundException) {
                    Toast.makeText(this.requireContext(), getString(R.string.report_error), Toast.LENGTH_LONG).show()
                }
            }
        }

        btnPolicy.setOnClickListener {
            if (!Config.FOR_TEST) {
                var intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(activity!!.resources.getString(R.string.url_gdpr))
                startActivity(intent)
            }
        }

        btnShare.setOnClickListener {

            Ampl.openSettingsShare()
            var intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.accompanying_text) + "\n"
                    + "https://play.google.com/store/apps/details?id=${App.getInstance().packageName}")
            startActivity(intent)
        }


        ivCircle.setOnClickListener {
            if (!Config.FOR_TEST) {
                Ampl.clickAvatar()
                if (isCameraForbidden()) {
                    requestPermissions(arrayOf(android.Manifest.permission.CAMERA, android.Manifest.permission.WRITE_EXTERNAL_STORAGE), CAMERA_PERMISSION_REQUEST)
                } else {
                    runCamera()
                }
            }
        }

        flWater.setOnClickListener {
            openMeasActivity()
        }
        flGender.setOnClickListener {
            openMeasActivity()
        }
        flWeight.setOnClickListener {
            openMeasActivity()
        }
    }

    fun isCanClose(): Boolean {
        return if (bsBehavior.state == BottomSheetBehavior.STATE_EXPANDED) {
            bsBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
            false
        } else {
            true
        }
    }

    private fun openMeasActivity() {
        startActivity(Intent(activity!!, MeasActivity::class.java))
    }

    private fun isCameraForbidden(): Boolean = ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
            || ContextCompat.checkSelfPermission(activity!!, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED

    private fun setAvatar() {
        if (PreferenceProvider.getPhoto() != PreferenceProvider.EMPTY_PHOTO) {
            try {
                ivAvatar.setImageURI(Uri.parse(PreferenceProvider.getPhoto()))
            } catch (ex: Exception) {
                setDefaultAvatar()
            }
        } else {
            setDefaultAvatar()
        }
    }

    private fun setDefaultAvatar() {
        Glide.with(this).load(R.drawable.woman).into(ivAvatar)
    }

    private fun createImageFile(): File {
        val name = Config.AVATAR_PATH
        val storage = activity!!.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(name, ".jpg", storage)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        var isGranted = true
        if (requestCode == CAMERA_PERMISSION_REQUEST) {
            grantResults.forEach {
                if (it != PackageManager.PERMISSION_GRANTED) {
                    isGranted = false
                }
            }
        }
        if (isGranted) {
            runCamera()
        } else {
            DeniedPermToast.show(activity!!)
        }
    }

    private fun runCamera() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(activity!!.packageManager)?.also {
                val photoFile: File? = createImageFile()
                photoFile?.also {
                    uri = FileProvider.getUriForFile(
                            activity!!,
                            getString(R.string.authorise_file_provider),
                            it
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri)
                    startActivityForResult(takePictureIntent, CAMERA_REQUEST)
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            ivAvatar.setImageURI(uri)
            PreferenceProvider.setPhoto(uri.toString())
        }
    }

    private fun setBack(number: Int) {
        var url = resources.getStringArray(R.array.backgrounds_profile)[number]
        Glide.with(this).load(url).into(ivHeadBack)
    }


    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden && isResumed) {
            if (PreferenceProvider.getCountIntro()!! < MAX_ATEMPT_INTRO) {
                IntroToast.show(activity!!)
                var count = PreferenceProvider.getCountIntro()!!
                count++
                PreferenceProvider.setCountIntro(count)
            }
        }
    }

    fun bindName() {
        if (PreferenceProvider.getName() == "") {
            tvName.text = resources.getString(R.string.def_name)
        } else {
            tvName.text = PreferenceProvider.getName()
        }
    }

    override fun onResume() {
        super.onResume()
        bindName()
        bindFields()
    }


    private fun bindFields() {
        if (PreferenceProvider.getWeight()!! != PreferenceProvider.EMPTY) {
            tvWeight.text = PreferenceProvider.getWeight()!!.toString()
            tvWater.text = WaterCounter.getWaterDailyRate(PreferenceProvider.getSex()!!, false, false, PreferenceProvider.getWeight()!!, false).toString()
        } else {
            tvWeight.text = EMPTY_MEAS
            tvWater.text = EMPTY_MEAS
        }

        if (PreferenceProvider.getSex() != PreferenceProvider.EMPTY) {
            if (PreferenceProvider.getSex() == PreferenceProvider.SEX_TYPE_MALE) {
                tvGender.text = getString(R.string.male_gender)
            } else {
                tvGender.text = getString(R.string.female_gender)
            }
        } else {
            tvGender.text = EMPTY_MEAS
        }

        if (PreferenceProvider.getName() == "") {
            tvName.text = resources.getString(R.string.def_name)
        } else {
            tvName.text = PreferenceProvider.getName()
        }
    }

    override fun openChangeLangActivity() {
        startActivity(Intent(requireActivity(), ChoiceLangActivity::class.java))
    }


}