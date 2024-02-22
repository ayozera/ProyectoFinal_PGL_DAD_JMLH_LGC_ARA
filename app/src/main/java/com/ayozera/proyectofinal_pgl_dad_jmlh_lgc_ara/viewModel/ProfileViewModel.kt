package com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.viewModel

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.models.Game
import com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.models.Match
import com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.models.Player
import com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.navigation.Routs
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

class ProfileViewModel : ViewModel() {

    private var connection = FirebaseFirestore.getInstance()

    private var _userName = MutableStateFlow("")
    val userName = _userName

    private var _userAvatar = MutableStateFlow("")
    val userAvatar = _userAvatar

    private var _favouriteGame = MutableStateFlow("")
    val favouriteGame = _favouriteGame

    private var _matches = MutableStateFlow(ArrayList<Match>())
    val matches = _matches


    //Cargamos desde la base de datos las partidas en las que el usuario participa
    suspend fun load(userId: String) {
        val scoresList = loadScores(userId)
        scoresList.forEach {
            println("Score: ${it.points}")
        }
        val matchesList = loadMatches(scoresList)
        matchesList.forEach {
            println("Match1: ${it.game}")
        }
        matchesList.forEach { match ->
            val game = connection.collection("Game").document(match.game).get().result
            val gameDB = game.toObject(GameDB::class.java)
            gameDB?.id = game.id

            val scoresOneMatch = ArrayList<ScoreDB>()
            scoresList.forEach { score ->
                if (score.match == match.id) {
                    scoresOneMatch.add(score)
                }
            }

            scoresOneMatch.sortByDescending { it.points }
            val scores2 = ArrayList<Int>()
            val playersOneMatch = ArrayList<PlayerDB>()
            scoresOneMatch.forEach { score ->
                val player = connection.collection("Player").document(score.player).get().result
                val playerDB = player.toObject(PlayerDB::class.java)
                playerDB?.id = player.id
                playersOneMatch.add(playerDB!!)
                scores2.add(score.points)
            }
            val players2 = ArrayList<Player>()
            playersOneMatch.forEach { player ->
                val player = Player(
                    player.name,
                    Color(android.graphics.Color.parseColor(player.color)),
                    player.avatar
                )
                players2.add(player)
            }

            val day = match.date.substring(0, 2).toInt()
            val month = match.date.substring(3, 5).toInt()
            val year = match.date.substring(6, 10).toInt()

            val finalMatch =
                Match(gameDB!!.name, gameDB!!.image, players2, day, month, year, scores2)
            _matches.value.add(finalMatch)

            println("Match: ${finalMatch.game}")
        }
    }

    private suspend fun loadMatches(scoresList: ArrayList<ScoreDB>): ArrayList<MatchDB> {
        return suspendCoroutine { continuation ->
            val matchesList = ArrayList<MatchDB>()
                scoresList.forEach { score ->
                    connection.collection("Match").document(score.match).get().addOnSuccessListener {
                        val match = it.toObject(MatchDB::class.java)
                        match?.id = it.id
                        matchesList.add(match!!)
                    }
                }
            continuation.resume(matchesList)
        }
    }

    private suspend fun loadScores(id: String): ArrayList<ScoreDB> {
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