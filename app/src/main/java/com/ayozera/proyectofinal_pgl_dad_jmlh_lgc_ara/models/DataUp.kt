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
                context.openFileOutput("players.txt", Context.MODE_PRIVATE)
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

        @OptIn(ExperimentalStdlibApi::class)
        fun saveSelection(selection : SelectionMatch, context: Context) {
            val writer: FileOutputStream =
                context.openFileOutput("selections.txt", Context.MODE_PRIVATE)
            writer.write("${selection.game}\n".toByteArray())
            writer.write("${selection.day}\n".toByteArray())
            writer.write("${selection.month}\n".toByteArray())
            writer.write("${selection.year}\n".toByteArray())
            writer.write("${selection.players.size}\n".toByteArray())
            selection.players.forEach {
                writer.write("${it.name}\n".toByteArray())
                writer.write("#${it.color.value.toHexString().substring(0,8)}\n".toByteArray())
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
        fun loadCredentials(context: Context): Pair<String, String> {
            val file = File(context.filesDir, "credentials.txt")
            val fileInput = FileInputStream(file)
            val reader = BufferedReader(InputStreamReader(fileInput))
            val user = reader.readLine()
            val password = reader.readLine()

            return Pair(user, password)
        }

        @OptIn(ExperimentalStdlibApi::class)
        fun saveMatch(match: Match, context: Context) {
            val writer: FileOutputStream =
                context.openFileOutput("matches.txt", Context.MODE_APPEND)
            writer.write("${match.game}\n".toByteArray())
            writer.write("${match.gameArt}\n".toByteArray())
            writer.write("${match.day}\n".toByteArray())
            writer.write("${match.month}\n".toByteArray())
            writer.write("${match.year}\n".toByteArray())
            writer.write("${match.players.size}\n".toByteArray())
            match.players.forEach {
                writer.write("${it.name}\n".toByteArray())
                writer.write("#${it.color.value.toHexString().substring(0,8)}\n".toByteArray())
                writer.write("${it.avatar}\n".toByteArray())
            }
            writer.write("${match.score.size}\n".toByteArray())
            match.score.forEach {
                writer.write("$it\n".toByteArray())
            }
            writer.close()
        }

        fun loadMatches(context : Context) {
            val file = File(context.filesDir, "matches.txt")
            val fileInput = FileInputStream(file)
            val reader = BufferedReader(InputStreamReader(fileInput))
            var counter = -1
            var game = ""
            var gameArt = ""
            var day = 0
            var month = 0
            var year = 0
            val matchesList = ArrayList<Match>()
            var players = ArrayList<Player>()
            var score = ArrayList<Int>()
            reader.forEachLine { line ->
                if (line.isNotBlank()) {
                    counter++
                    when (counter) {
                        0 -> game = line
                        1 -> gameArt = line
                        2 -> day = line.toInt()
                        3 -> month = line.toInt()
                        4 -> year = line.toInt()
                        5 -> {
                            val numPlayers = line.toInt()
                            for (i in 0 until numPlayers) {
                                val name = reader.readLine()
                                val color = Color(android.graphics.Color.parseColor(reader.readLine()))
                                val avatar = reader.readLine()
                                players.add(Player(name, color, avatar))
                            }
                        }
                        6 -> {
                            val numScores = line.toInt()
                            for (i in 0 until numScores) {
                                score.add(reader.readLine().toInt())
                            }
                            counter = -1
                            matchesList.add(Match(game, gameArt.toInt(), players, day, month, year, score))
                            players = ArrayList()
                            score = ArrayList()
                        }
                    }
                }
            }
        }

        fun loadGames(current: Context): ArrayList<String> {
            val gamesList = ArrayList<String>()
            val assetManager = current.assets
            val inputStream = assetManager.open("games.txt")
            val reader = BufferedReader(InputStreamReader(inputStream))
            reader.forEachLine { line ->
                if (line.isNotBlank()) {
                    gamesList.add(line)
                }
            }
            return gamesList
        }

        fun getDescription(current: Context, gameName: String): String {
            val assetManager = current.assets
            val inputStream = assetManager.open("gamesInformation/$gameName.txt")
            val reader = BufferedReader(InputStreamReader(inputStream))
            val description = StringBuilder()
            reader.forEachLine { line ->
                description.append("$line\n")
            }
            return description.toString()
        }
    }
}