package com.wsoteam.mydietcoach.profile

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.wsoteam.mydietcoach.Config
import com.wsoteam.mydietcoach.R
import com.wsoteam.mydietcoach.profile.controllers.BacksAdapter
import com.wsoteam.mydietcoach.profile.controllers.IBacks
import com.wsoteam.mydietcoach.profile.dialogs.DevelopmentDialog
import com.wsoteam.mydietcoach.profile.dialogs.NameDialog
import com.wsoteam.mydietcoach.profile.favorites.FavoritesActivity
import com.wsoteam.mydietcoach.profile.toasts.DeniedPermToast
import com.wsoteam.mydietcoach.profile.toasts.IntroToast
import com.wsoteam.mydietcoach.utils.PrefWorker
import kotlinx.android.synthetic.main.bottom_sheet_backs.*
import kotlinx.android.synthetic.main.profile_fragment.*
import java.io.File
import java.io.IOException
import java.lang.Exception


class ProfileFragment : Fragment(R.layout.profile_fragment) {

    var nameDialog = NameDialog()
    val MAX_ATEMPT_INTRO = 2
    val CAMERA_REQUEST = 0
    val CAMERA_PERMISSION_REQUEST = 1
    lateinit var bsBehavior : BottomSheetBehavior<LinearLayout>

    lateinit var uri: Uri

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvDate.text = "${resources.getString(R.string.together)} ${PrefWorker.getFirstTime()}"
        cvParent.setBackgroundResource(R.drawable.shape_profile_card)
        setBack(PrefWorker.getBack()!!)
        setAvatar()

        nameDialog.setTargetFragment(this, 0)

        bsBehavior = BottomSheetBehavior.from(llBottomSheet)

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
        ivCircle.setOnClickListener {
            if (isCameraForbidden()) {
                requestPermissions(arrayOf(android.Manifest.permission.CAMERA, android.Manifest.permission.WRITE_EXTERNAL_STORAGE), CAMERA_PERMISSION_REQUEST)
            } else {
                runCamera()
            }
        }
        rvBacks.layoutManager = GridLayoutManager(activity, 2)
        rvBacks.adapter = BacksAdapter(resources.getStringArray(R.array.backgrounds_profile), object : IBacks {
            override fun choiceBack(position: Int) {
                PrefWorker.setBack(position)
                setBack(position)
                bsBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
            }
        })
    }

    private fun isCameraForbidden(): Boolean = activity!!.checkSelfPermission(android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
            || (ContextCompat.checkSelfPermission(activity!!, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) != PackageManager.PERMISSION_GRANTED

    private fun setAvatar() {
        if (PrefWorker.getPhoto() != PrefWorker.EMPTY_PHOTO){
            try {
                ivAvatar.setImageURI(Uri.parse(PrefWorker.getPhoto()))
            }catch (ex : Exception){
                setDefaultAvatar()
            }
        }else{
            setDefaultAvatar()
        }
    }

    private fun setDefaultAvatar(){
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
        if (requestCode == CAMERA_PERMISSION_REQUEST){
            grantResults.forEach {
                if (it != PackageManager.PERMISSION_GRANTED){
                    isGranted = false
                }
            }
        }
        Log.e("LOL", "ret")
        if (isGranted){
            runCamera()
        }else{
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
            PrefWorker.setPhoto(uri.toString())
        }
    }

    private fun setBack(number: Int) {
        var url = resources.getStringArray(R.array.backgrounds_profile)[number]
        Glide.with(this).load(url).into(ivHeadBack)
    }

    fun bindName() {
        if (PrefWorker.getName() == "") {
            tvName.text = resources.getString(R.string.def_name)
        } else {
            tvName.text = PrefWorker.getName()
        }
    }

    override fun onResume() {
        super.onResume()
        bindName()
        if (PrefWorker.getCountIntro()!! < MAX_ATEMPT_INTRO) {
            IntroToast.show(activity!!)
            var count = PrefWorker.getCountIntro()!!
            count++
            PrefWorker.setCountIntro(count)
        }
    }


}