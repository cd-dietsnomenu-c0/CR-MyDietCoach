package com.wsoteam.mydietcoach.POJOS.interactive

import java.io.Serializable

data class Diet(var title: String, var shortIntroduction: String, var introduction: String,
                var mainImage: String, var benefitsTitle: String,
                var benefits: List<String>, var consTitle: String,
                var cons: List<String>, var menuTitle: String,
                var menuText: String, var days: List<DietDay>, var hintText: String, var resultText: String,
                var review: Review) : Serializable {
}