package com.wsoteam.mydietcoach.diets.items.article.interactive

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wsoteam.mydietcoach.POJOS.interactive.Diet
import com.wsoteam.mydietcoach.POJOS.interactive.DietDay
import com.wsoteam.mydietcoach.POJOS.interactive.Eat
import com.wsoteam.mydietcoach.POJOS.interactive.Review
import com.wsoteam.mydietcoach.R
import com.wsoteam.mydietcoach.diets.items.article.interactive.controller.DietDayAdapter
import kotlinx.android.synthetic.main.diet_act.*
import java.util.ArrayList

class DietAct : AppCompatActivity(R.layout.diet_act) {

    lateinit var diet: Diet

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        falseLoad()
        rvDietDays.layoutManager = LinearLayoutManager(this)
        rvDietDays.adapter = DietDayAdapter(diet.days)
    }

    private fun falseLoad() {
        val review = Review("Name",
                "https://img.freepik.com/free-photo/beautiful-young-woman-showing-pointing-white-background_1301-7232.jpg?size=626&ext=jpg",
                "TextTextTextTextTextTextTextTextTextTextTextTextTextTextTextTextTextTextTextTextTextTextTextTextTextTextTextTextTextTextTextTextTextTextTextTextTextTextTextTextText")
        var reviewList = ArrayList<Review>()
        reviewList.add(review)
        reviewList.add(review)

        val eat = Eat( 0, "Отварная грудка с овощным салатом (огурец, помидор, зелень)")
        val eat1 = Eat( 1, "Салат из капусты и огурца")
        val eat2 = Eat( 2, "Тушеная куриная грудка с гречкой")
        val eat3 = Eat( 3, "Яблоко")
        val eat4 = Eat( 4, "Отварная куриная грудка с салатом")

        var eatList = ArrayList<Eat>()
        eatList.add(eat)
        eatList.add(eat1)
        eatList.add(eat4)
        eatList.add(eat2)
        eatList.add(eat3)

        val day = DietDay("1 день", "https://s1.stc.all.kpcdn.net/putevoditel/projectid_103889/images/tild3530-6661-4332-a434-613435663634__2.jpg", eatList, "Совет", "СоветСоветСоветСоветСоветСоветСоветСоветСоветСоветСоветСоветСоветСоветСовет")
        var days = ArrayList<DietDay>()
        days.add(day)
        days.add(day)
        days.add(day)
        days.add(day)
        days.add(day)
        days.add(day)
        days.add(day)

        val benefit = "ПлюсПлюсПлюсПлюс"
        var benList = ArrayList<String>()
        benList.add(benefit)
        benList.add(benefit)
        benList.add(benefit)
        benList.add(benefit)
        benList.add(benefit)

        val cons = "ПлюсПлюсПлюсПлюс"
        var consList = ArrayList<String>()
        consList.add(cons)
        consList.add(cons)
        consList.add(cons)
        consList.add(cons)
        consList.add(cons)

         diet = Diet("Куриная диета", "тексттексттексттексттексттексттексттексттексттексттексттексттексттексттексттексттексттексттекст", "https://s1.stc.all.kpcdn.net/putevoditel/projectid_103889/images/tild6139-3064-4537-b930-623362366338__960.jpg",
                "Плюсы диеты", benList, "Минусы диеты", consList, "Меню на неделю для куриной диеты", "Во время куриной диеты нужно пить достаточное количество воды (30 мл на 1 кг веса), также допустимы травяные чаи.", days,
                "Результаты", "Соблюдая куриную диету можно похудеть от 2 до 6 килограммов за неделю. Конечная цифра на весах будет зависеть от соблюдения всех правил и от продуктов, которые вы будете употреблять. Имейте в виду, что куриная диета рассчитана на интенсивные нагрузки, поэтому вы с легкостью можете подключать и спорт. А это, в свою очередь, поможет еще быстрее сбросить ненужный вес и укрепить здоровье.",
                reviewList)

    }
}