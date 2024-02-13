package com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.models

import android.content.Context
import androidx.compose.ui.graphics.Color
import java.io.BufferedReader
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStreamReader

class DataUp {
    companion object {
        fun getSongs(): ArrayList<Song> {
            return arrayListOf(
                Song("Chop Suey", "chop_suey"),
                Song("Rise up", "rise_up"),
                Song("Respira el momento", "respira_el_momento"),
                Song("Adentro", "adentro"),
                Song("Jeremias17.5", "jeremias17_5"),
                Song("Maquiavelico", "maquiavelico")
            )
        }

        private fun gameFirstLoader(context: Context) {
            val assetManager = context.assets
            val inputStream = assetManager.open("games.txt")
            val reader = BufferedReader(InputStreamReader(inputStream))
            val writer: FileOutputStream =
                context.openFileOutput("games.txt", Context.MODE_APPEND)
            reader.forEachLine { line ->
                writer.write("$line\n".toByteArray())
            }
            reader.close()
            writer.close()
        }

        fun gameLoader(context: Context): ArrayList<String> {
            val gamesList = ArrayList<String>()
            val file = File(context.filesDir, "games.txt")
            try {
                if (!file.exists()) {
                    gameFirstLoader(context)
                }
                val fileInput = FileInputStream(file)
                val reader = BufferedReader(InputStreamReader(fileInput))

                reader.forEachLine { line ->
                    if (line.isNotBlank()) {
                        gamesList.add(line)
                    }
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
            return gamesList
        }

        private fun playerFirstLoader(context: Context) {
            val assetManager = context.assets
            val inputStream = assetManager.open("players.txt")
            val reader = BufferedReader(InputStreamReader(inputStream))
            val writer: FileOutputStream =
                context.openFileOutput("players.txt", Context.MODE_APPEND)
            reader.forEachLine { line ->
                writer.write("$line\n".toByteArray())
            }
            reader.close()
            writer.close()
        }

        fun playerLoader(context: Context): ArrayList<Player> {
            val playerList = ArrayList<Player>()
            val file = File(context.filesDir, "players.txt")
            try {
                if (!file.exists()) {
                    playerFirstLoader(context)
                }
                val fileInput = FileInputStream(file)
                val reader = BufferedReader(InputStreamReader(fileInput))
                var counter = -1
                var player = ""
                var avatar = ""
                var color = Color.Red

                reader.forEachLine { line ->
                    if (line.isNotBlank()) {
                        counter++
                        when (counter) {
                            0 -> player = line
                            1 -> color = Color(android.graphics.Color.parseColor(line))
                            2 -> {
                                avatar = line
                                counter = -1
                                playerList.add(Player(player, color, avatar))
                            }
                        }
                    }
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
            return playerList
        }
        fun getComments(current: Context): ArrayList<Comment> {
            val lista = arrayListOf(
                Comment("Ana", "Me encanta este juego", "2021-12-12"),
                Comment("Luis", "Es muy entretenido", "2021-12-12"),
                Comment("Juan", "No me gusta", "2021-12-12"),
                Comment("Maria", "Es muy dificil", "2021-12-12"),
                Comment("Pedro", "Es muy facil", "2021-12-12")
            )
            return lista
        }

        fun saveSelection(selection : SelectionMatch, context: Context) {
            val file = File(context.filesDir, "selections.txt")
            val writer: FileOutputStream =
                context.openFileOutput("selections.txt", Context.MODE_APPEND)
            writer.write("${selection.game}\n".toByteArray())
            writer.write("${selection.day}\n".toByteArray())
            writer.write("${selection.month}\n".toByteArray())
            writer.write("${selection.year}\n".toByteArray())
            writer.write("${selection.players.size}\n".toByteArray())
            selection.players.forEach {
                writer.write("${it.name}\n".toByteArray())
                writer.write("${it.color}\n".toByteArray())
                writer.write("${it.avatar}\n".toByteArray())
            }
            writer.close()
        }

        fun loadSelection(context : Context) : SelectionMatch {
            val file = File(context.filesDir, "selections.txt")
            val fileInput = FileInputStream(file)
            val reader = BufferedReader(InputStreamReader(fileInput))
            val game = reader.readLine()
            val day = reader.readLine().toInt()
            val month = reader.readLine().toInt()
            val year = reader.readLine().toInt()
            val players = ArrayList<Player>()
            val numPlayers = reader.readLine().toInt()
            for (i in 0 until numPlayers) {
                val name = reader.readLine()
                val color = Color(android.graphics.Color.parseColor(reader.readLine()))
                val avatar = reader.readLine()
                players.add(Player(name, color, avatar))
            }
            return SelectionMatch(game, players, day, month, year)
        }
    }
}