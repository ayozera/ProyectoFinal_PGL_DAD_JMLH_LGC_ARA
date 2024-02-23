package com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.viewModel

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.models.Match
import com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.models.Player
import com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.repositories.GameDB
import com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.repositories.MatchDB
import com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.repositories.PlayerDB
import com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.repositories.ScoreDB
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.tasks.await
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine
import kotlinx.coroutines.launch
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ProfileViewModel : ViewModel() {

    private var connection = FirebaseFirestore.getInstance()

    private var _matches = MutableStateFlow(ArrayList<Match>())
    val matches = _matches

    @OptIn(DelicateCoroutinesApi::class)
    suspend fun load(userId: String) = withContext(Dispatchers.IO) {
        val matches = ArrayList<Match>()
        val firstScoresList = loadOwnScores(userId)
        val matchesList = loadMatches(firstScoresList)
        val secondScoresList = loadAllScores(matchesList)
        val gamesDB = loadGameNames(matchesList)
        val scoresOneMatch = loadScoresOneMatch(matchesList, secondScoresList)

        var index2 = 0
        scoresOneMatch.forEach { scores ->
            val scores2 = ArrayList<Int>()
            val playersOneMatch = ArrayList<PlayerDB>()

            val jobs2 = scores.map { score ->
                GlobalScope.launch {
                    val player =
                        connection.collection("Player").document(score.player).get().await()
                    val playerDB = player.toObject(PlayerDB::class.java)
                    playerDB?.id = player.id
                    playersOneMatch.add(playerDB!!)
                    scores2.add(score.points)
                }
            }
            runBlocking {
                jobs2.joinAll()
            }

            val players2 = ArrayList<Player>()
            playersOneMatch.forEach { player ->
                val player2 = Player(
                    player.name,
                    Color(android.graphics.Color.parseColor(player.color)),
                    player.avatar
                )
                players2.add(player2)
            }

            val firstHyphen = matchesList[index2].date.indexOf("-")
            val secondHyphen = matchesList[index2].date.indexOf("-", firstHyphen + 1)
            val day = matchesList[index2].date.substring(0, firstHyphen).toInt()
            val month = matchesList[index2].date.substring(firstHyphen + 1, secondHyphen).toInt()
            val year = matchesList[index2].date.substring(secondHyphen + 1).toInt()

            val finalMatch =
                Match(
                    gamesDB[index2].name,
                    gamesDB[index2].image,
                    players2,
                    day,
                    month,
                    year,
                    scores2
                )
            matches.add(finalMatch)
            index2++
        }

        _matches.value = matches
    }

    private fun loadScoresOneMatch(
        matchesList: ArrayList<MatchDB>,
        secondScoresList: ArrayList<ScoreDB>
    ): ArrayList<ArrayList<ScoreDB>> {
        var index = 0
        val scoresOneMatch = ArrayList<ArrayList<ScoreDB>>()
        matchesList.forEach { match ->
            scoresOneMatch.add(ArrayList())
            secondScoresList.forEach { score ->
                if (score.match == match.id) {
                    scoresOneMatch[index].add(score)
                }
            }
            scoresOneMatch[index].sortBy { it.points }
            index++
        }
        return scoresOneMatch
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun loadGameNames(matchesList: ArrayList<MatchDB>): ArrayList<GameDB> {
        val gamesDB = ArrayList<GameDB>()
        val jobs = matchesList.map { match ->
            GlobalScope.launch {
                val game = connection.collection("Game").document(match.game).get().await()
                val gameDB = game.toObject(GameDB::class.java)
                gameDB?.id = game.id
                gamesDB.add(gameDB!!)
            }
        }
        runBlocking {
            jobs.joinAll()
        }
        return gamesDB
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun loadAllScores(matchesList: ArrayList<MatchDB>): ArrayList<ScoreDB> {
        val secondScoresList = ArrayList<ScoreDB>()
        val jobs0 = matchesList.map { match ->
            GlobalScope.launch {
                val scores =
                    connection.collection("Score").whereEqualTo("match", match.id).get().await()
                scores.documents.forEach {
                    val score = it.toObject(ScoreDB::class.java)
                    score?.id = it.id
                    secondScoresList.add(score!!)
                }
            }
        }
        runBlocking {
            jobs0.joinAll()
        }
        return secondScoresList
    }

    @OptIn(DelicateCoroutinesApi::class)
    private suspend fun loadMatches(scoresList: ArrayList<ScoreDB>): ArrayList<MatchDB> =
        suspendCancellableCoroutine { continuation ->
            val matchesList = ArrayList<MatchDB>()
            val jobs = scoresList.map { score ->
                GlobalScope.launch {
                    val document =
                        connection.collection("Match").document(score.match).get().await()
                    val match = document.toObject(MatchDB::class.java)
                    match?.id = document.id
                    matchesList.add(match!!)
                }
            }
            runBlocking {
                jobs.joinAll()
                continuation.resume(matchesList)
            }
        }

    private suspend fun loadOwnScores(id: String): ArrayList<ScoreDB> {
        return suspendCoroutine { continuation ->
            val scoresList = ArrayList<ScoreDB>()
            connection.collection("Score").whereEqualTo("player", id).get()
                .addOnSuccessListener { scores ->
                    scores.documents.forEach {
                        val score = it.toObject(ScoreDB::class.java)
                        score?.id = it.id
                        scoresList.add(score!!)
                    }
                    continuation.resume(scoresList)
                }
        }
    }
}