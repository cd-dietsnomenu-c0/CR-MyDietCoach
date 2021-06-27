package com.diets.weightloss.presentation.premium

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.diets.weightloss.MainActivity
import com.diets.weightloss.R
import com.diets.weightloss.utils.PreferenceProvider
import kotlinx.android.synthetic.main.premium_host_activity.*

class PremiumHostActivity : AppCompatActivity(R.layout.premium_host_activity) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        PreferenceProvider.isSawPremium = true

        supportFragmentManager.beginTransaction().add(R.id.flFragmentHost, PremiumFragment()).commit()

        ivClose.setOnClickListener {
            openMainActivity()
        }
    }

    private fun openMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    override fun onBackPressed() {
        openMainActivity()
    }
}