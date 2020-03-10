package com.wsoteam.mydietcoach.diets.items.article.interactive

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.wsoteam.mydietcoach.POJOS.interactive.Diet
import com.wsoteam.mydietcoach.POJOS.interactive.DietDay
import com.wsoteam.mydietcoach.POJOS.interactive.Eat
import com.wsoteam.mydietcoach.POJOS.interactive.Review
import com.wsoteam.mydietcoach.R
import com.wsoteam.mydietcoach.diets.items.article.interactive.controller.DietAdapter
import kotlinx.android.synthetic.main.diet_act.*
import kotlinx.android.synthetic.main.diet_act.ivCollapsing
import kotlinx.android.synthetic.main.fr_article.*
import java.util.ArrayList

class DietAct : AppCompatActivity(R.layout.diet_act) {

    lateinit var diet: Diet

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        falseLoad()
        Glide.with(this).load("https://image.freepik.com/free-photo/grilled-chicken-legs-with-spices-garlic_82893-5464.jpg").into(ivCollapsing)
        rvDiet.layoutManager = LinearLayoutManager(this)
        rvDiet.adapter = DietAdapter(diet)
    }

    private fun falseLoad() {
        val review = Review("Name",
                "https://img.freepik.com/free-photo/beautiful-young-woman-showing-pointing-white-background_1301-7232.jpg?size=626&ext=jpg",
                "TextTextTextTextTextTextTextTextTextTextTextTextTextTextTextTextTextTextTextTextTextTextTextTextTextTextTextTextTextTextTextTextTextTextTextTextTextTextTextTextText")


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

        val day = DietDay("1 день", "https://s1.stc.all.kpcdn.net/putevoditel/projectid_103889/images/tild3530-6661-4332-a434-613435663634__2.jpg", eatList)
        var days = ArrayList<DietDay>()
        days.add(day)
        days.add(day)
        days.add(day)
        days.add(day)
        days.add(day)
        days.add(day)
        days.add(day)

        var benList = ArrayList<String>()
        benList.add("Оптимальное количество белка в еде – это помогает в процессе похудения, сжигая жировую ткань;")
        benList.add("Чувство насыщения – куриная грудка в сочетании с овощами придает чувство сытости на длительное время;")
        benList.add("В курином мясе намного меньше жиров, чем в других видах мяса;")
        benList.add("Мясо курицы усваивается организмом намного легче, и оно доступнее, в отличие от крольчатины и индейки, которые так же относятся к диетическим сортам;")
        benList.add("Куриная диета обладает разнообразным рационом. Поэтому вы можете сочетать куриное мясо с овощами, кисломолочными продуктами, крупами (кроме пшеничной, манной) и фруктами (кроме банана и винограда).")

        var consList = ArrayList<String>()
        consList.add("Куриная диета не может быть достаточно сбалансированной, так как лишена «полезных» жиров. Придерживаться такого рациона больше 1 недели не рекомендуется.")
        consList.add("Попробовать такую диету могут только полностью здоровые люди. При имеющихся проблемах со здоровьем перед началом диеты нужно проконсультироваться с врачом.")
        consList.add("Куриное мясо может спровоцировать аллергию при имеющейся склонности.")

         diet = Diet("Куриная диета", "В курином мясе содержится множество полезных аминокислот, которые благотворно сказываются на здоровье. Большинство диетологов рекомендуют куриную диету, так как она низкокалорийная, питательная и позволяет достаточно быстро получить желаемый результат.", "https://s1.stc.all.kpcdn.net/putevoditel/projectid_103889/images/tild6139-3064-4537-b930-623362366338__960.jpg",
                "Плюсы диеты", benList, "Минусы диеты", consList, "Меню на неделю для куриной диеты", "Во время куриной диеты нужно пить достаточное количество воды (30 мл на 1 кг веса), также допустимы травяные чаи.", days,
                 "Количество приемов пищи должно быть не менее 5 раз в день.\n" +
                         "\n" +
                         "На время диеты из продуктовой корзины исключаем: сахар, соль, майонез, кетчуп, мед, хлеб, сдобу, колбасы, бананы, виноград, картофель, кукурузу, манку, пшеницу, сладкую газированную воду, пакетированные соки и алкоголь.",
                 "Соблюдая куриную диету можно похудеть от 2 до 6 килограммов за неделю. Конечная цифра на весах будет зависеть от соблюдения всех правил и от продуктов, которые вы будете употреблять. Имейте в виду, что куриная диета рассчитана на интенсивные нагрузки, поэтому вы с легкостью можете подключать и спорт. А это, в свою очередь, поможет еще быстрее сбросить ненужный вес и укрепить здоровье.",
                review)

    }
}