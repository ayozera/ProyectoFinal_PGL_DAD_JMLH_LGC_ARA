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
                Song("Dyson Sphere Program ", "dysonsphere"),
                Song("Journey", "journey"),
                Song("Minecraft", "minecraft"),
                Song("Outer Wilds", "outerwilds"),
                Song("Potion Craft", "potioncraft"),
                Song("Stardew Valley", "stardewvalley")
            )
        }

        @OptIn(ExperimentalStdlibApi::class)
        fun saveSelection(selection : SelectionMatch, context: Context) {
            val writer: FileOutputStream =
                context.openFileOutput("selections.txt", Context.MODE_PRIVATE)
            writer.write("${selection.gameID}\n".toByteArray())
            writer.write("${selection.gameName}\n".toByteArray())
            writer.write("${selection.gameArt}\n".toByteArray())
            writer.write("${selection.day}\n".toByteArray())
            writer.write("${selection.month}\n".toByteArray())
            writer.write("${selection.year}\n".toByteArray())
            writer.write("${selection.players.size}\n".toByteArray())
            selection.playersId.forEach {
                writer.write("$it\n".toByteArray())
            }
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
            val gameId = reader.readLine()
            val gameName = reader.readLine()
            val gameArt = reader.readLine()
            val day = reader.readLine().toInt()
            val month = reader.readLine().toInt()
            val year = reader.readLine().toInt()
            val numPlayers = reader.readLine().toInt()
            val playersId = ArrayList<String>()
            for (i in 0 until numPlayers) {
                playersId.add(reader.readLine())
            }
            val players = ArrayList<Player>()
            for (i in 0 until numPlayers) {
                val name = reader.readLine()
                val color = Color(android.graphics.Color.parseColor(reader.readLine()))
                val avatar = reader.readLine()
                players.add(Player(name, color, avatar))
            }
            return SelectionMatch(gameId, gameName, gameArt, playersId, players, day, month, year)
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

        fun loadMatches(context : Context) : ArrayList<Match>{
            val file = File(context.filesDir, "matches.txt")
            if (!file.exists()) {
                loadMatchesFirstTime(context)
            }
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
                            matchesList.add(Match(game, gameArt, players, day, month, year, score))
                            players = ArrayList()
                            score = ArrayList()
                        }
                    }
                }
            }
            return matchesList
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

        fun loadMatchesFirstTime(context: Context) {
            val assetManager = context.assets
            val inputStream = assetManager.open("matches.txt")
            val reader = BufferedReader(InputStreamReader(inputStream))
            val writer: FileOutputStream =
                context.openFileOutput("matches.txt", Context.MODE_PRIVATE)
            reader.forEachLine { line ->
                writer.write("$line\n".toByteArray())
            }
            reader.close()
            writer.close()
        }
    }
}