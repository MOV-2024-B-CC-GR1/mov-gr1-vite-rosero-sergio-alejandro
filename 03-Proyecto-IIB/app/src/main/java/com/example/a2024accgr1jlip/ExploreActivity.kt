package com.example.a2024accgr1jlip

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ExploreActivity : AppCompatActivity() {
    private lateinit var dessertAdapter: DessertAdapter
    private val dessertList = mutableListOf<Dessert>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_explore)

        val recyclerView = findViewById<RecyclerView>(R.id.dessertRecyclerView)
        dessertAdapter = DessertAdapter(dessertList) { dessert ->
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("DESSERT_ID", dessert.id)
            intent.putExtra("DESSERT_NAME", dessert.name)
            intent.putExtra("DESSERT_DESCRIPTION", dessert.description)
            intent.putExtra("DESSERT_PRICE", dessert.price)
            intent.putExtra("DESSERT_IMAGE", dessert.imageResId)
            startActivity(intent)
        }
        recyclerView.adapter = dessertAdapter
        recyclerView.layoutManager = GridLayoutManager(this, 2)

        loadDesserts()
    }

    private fun loadDesserts() {
        // Sample data
        dessertList.add(Dessert(1, "Red Dead Redemption 2", " Es la nueva entrega de la saga de acción ambientada en el salvaje oeste de Rockstar. Red Dead Redemption 2 está disponible en tiendas desde el 26 de octubre de 2018 para PS4 y Xbox One.", 30.50, R.mipmap.pastel1, 4.5f))
        dessertList.add(Dessert(2, "GTA V (Grand Theft Auto V)", " para PS4, PS3, Xbox One, Xbox 360 y PC es la quinta entrega numerada de la saga superventas de Rockstar Games. Se trata de una aventura de acción de mundo abierto con multitud de misiones en la que encarnamos a tres personajes distintos: Trevor, Michael y Franklin. Además, cuenta con GTA Online, el modo multijugador en lína de GTA 5. El juego sale a la venta el 17 de septiembre de 2013 para PS3 y Xbox 360, el 18 de noviembre de 2014 en PS4 y Xbox One y, por último, el 14 de abril de 2015 para PC.", 28.00, R.mipmap.pastel2, 4.2f))
        dessertList.add(Dessert(3, "Cyberpunk 2077 ", "Es el nuevo juego de CD Projekt Red, el estudio polaco responsable de los juegos basados en The Witcher (lo que incluye el galardonado The Witcher III: Wild Hunt), para PS4, Xbox One y PC.", 29.00, R.mipmap.pastel3, 4.2f))
        dessertList.add(Dessert(4, "StarCraft II Heart of the Swarm", "Blizzard Entertainment nos vuelve a sorprender con una increíble historia de reencuentros y venganza con StarCraft II Heart of the Swarm. Sarah Kerrigan y su Enjambre de letales Zerg protagonizan la primera expansión independiente de StarCraft II.", 25.00, R.mipmap.pastel4, 4.2f))
        dessertList.add(Dessert(5, "L.A. Noire", "L.A. Noire puede presumir de tener a los personajes más expresivos del mundo de los videojuegos. No os vamos a decir que con mirarles a la cara ya es posible saber la respuesta... pero ayuda.", 19.00, R.mipmap.pastel5, 4.2f))

        dessertList.add(Dessert(6, "Elden Ring", "Es lo nuevo de FromSoftware y Bandai Namco. Un action RPG de aventura y fantasía en un mundo creado por Hidetaka Miyazaki (creador de la influyente saga DARK SOULS) y George R.R. Martin (autor de la saga de fantasía Canción de Hielo y Fuego).\n" +
                "\n", 38.00, R.mipmap.pastel6, 4.2f))
        dessertList.add(Dessert(7, "StarCraft II Wings of Liberty", "Cada vez que se habla de estrategia, es inevitable que nombres como Age of Empires, Warcraft, Command & Conquer, Warhammer40.000 o Company of Heroes salgan a la palestra. Pero si hablamos de estrategia online sobresale por encima de todos el de StarCraft. Y es que desde 1998, StarCraft ha encandilado a más de diez millones de jugadores en todo el mundo gracias a sus apasionantes partidas a través de Battle.net.", 25.00, R.mipmap.pastel7, 4.2f))
        dessertList.add(Dessert(8, "Persona 5 Royal", "Persona 5 Royal es un JRPG de la saga Persona desarrollado por Atlus y editado por Deep Silver en Europa. Será un título exclusivo de PS4 y a grandes rasgos será una visión expandida de la versión original de Persona 5, lanzada en 2017, que entre otras cosas añadirá un tercer semestre añadido al final de la historia.", 19.00, R.mipmap.pastel5, 4.2f))


        dessertAdapter.notifyDataSetChanged()
    }
}