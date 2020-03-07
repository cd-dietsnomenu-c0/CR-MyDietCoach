package com.wsoteam.mydietcoach.POJOS.interactive

data class Diet(var title: String, var introduction: String,
                var mainImage: String, var benefitsTitle: String,
                var benefits: List<String>, var consTitle: String,
                var cons: List<String>, var menuTitle: String,
                var menuText: String, var days : List<DietDay>, var hintText : String,
                var resultTitle : String, var resultText : String,
                var reviews : List<Review>) {
}