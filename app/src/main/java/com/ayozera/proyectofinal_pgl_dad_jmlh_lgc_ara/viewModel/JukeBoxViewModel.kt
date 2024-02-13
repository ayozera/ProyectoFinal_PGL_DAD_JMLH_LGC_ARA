package com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.viewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.models.DataUp
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class JukeBoxViewModel : ViewModel() {
    private var _mediaPlayer: MutableStateFlow<ExoPlayer?> = MutableStateFlow(null)
    val mediaPlayer = _mediaPlayer.asStateFlow()

    private var _canciones = MutableStateFlow(DataUp.getSongs())
    val canciones = _canciones.asStateFlow()

    private var _index = MutableStateFlow(0)
    val index = _index.asStateFlow()

    private var _durationMinutes = MutableStateFlow( 0)
    val duracionMinutos = _durationMinutes.asStateFlow()

    private var _durationSeconds = MutableStateFlow( 0)
    val duracionSegundos = _durationSeconds.asStateFlow()

    private var _positionMinutes = MutableStateFlow( 0)
    val posicionMinutos = _positionMinutes.asStateFlow()

    private var _positionSeconds = MutableStateFlow( 0)
    val posicionSegundos = _positionSeconds.asStateFlow()


    private var isPlaying = false
    private var bucle = false
    private var random = false
    private var numClics = 0

    private fun playList(context: Context) {
        _mediaPlayer.value!!.addListener(object : Player.Listener {
            override fun onPlaybackStateChanged(playbackState: Int) {
                if (playbackState == Player.STATE_READY) {
                    updateDuration()
                }
                if (playbackState == Player.STATE_ENDED) {
                    if (random) {
                        var temporal = (Math.random() * _canciones.value.size - 1).toInt()
                        if (temporal >= _index.value) {
                            temporal++
                        }
                        _index.value = temporal
                    } else {
                        _index.value++
                        if (_index.value > _canciones.value.lastIndex) {
                            _index.value = 0
                        }
                    }
                    val mediaItem = MediaItem.fromUri(
                        getRout(context,_canciones.value[_index.value].image)
                    )
                    _mediaPlayer.value!!.setMediaItem(mediaItem)
                }
            }
        })
    }

    fun updateDuration() {
        val durationMs = _mediaPlayer.value!!.duration
        _durationMinutes.value = TimeUnit.MILLISECONDS.toMinutes(durationMs).toInt()
        _durationSeconds.value = (TimeUnit.MILLISECONDS.toSeconds(durationMs) % 60).toInt()
    }

    private fun updatePosition() {
        viewModelScope.launch {
            while (true) {
                val positionMs = _mediaPlayer.value!!.currentPosition
                _positionMinutes.value = TimeUnit.MILLISECONDS.toMinutes(positionMs).toInt()
                _positionSeconds.value = (TimeUnit.MILLISECONDS.toSeconds(positionMs) % 60).toInt()

                delay(1000) //muy importante
            }
        }
    }

    fun createPlayer(contexto: Context) {
        _mediaPlayer.value = ExoPlayer.Builder(contexto).build()
        _mediaPlayer.value!!.prepare()
        updateSong(contexto)
    }

    fun clicPlay(context: Context) {
        if (numClics == 0) {
            viewModelScope.launch {
                playList(context)
                updatePosition()
            }
        }
        isPlaying = !isPlaying
        _mediaPlayer.value!!.playWhenReady = isPlaying
        numClics++
        updateDuration()
    }

    fun clicRandom() {
        random = !random
    }

    fun clicBucle() {
        bucle = !bucle
        _mediaPlayer.value!!.repeatMode =
            if (bucle) Player.REPEAT_MODE_ONE else Player.REPEAT_MODE_OFF
    }

    fun clicBefore(context: Context) {
        if (random) {
            var temporal = (Math.random() * _canciones.value.size - 1).toInt()
            if (temporal >= _index.value) {
                temporal++
            }
            _index.value = temporal
        } else {
            _index.value--
            if (_index.value < 0) {
                _index.value = _canciones.value.lastIndex
            }
        }
        updateSong(context)
    }

    fun clicNext(context: Context) {
        if (random) {
            var temporal = (Math.random() * _canciones.value.size - 1).toInt()
            if (temporal >= _index.value) {
                temporal++
            }
            _index.value = temporal
        } else {

            _index.value++
            if (_index.value > _canciones.value.lastIndex) {
                _index.value = 0
            }

        }
        updateSong(context)
    }

    fun moveSlider(posicion : Int) {
        _mediaPlayer.value!!.seekTo((posicion * 1000).toLong())
    }

    private fun updateSong(context: Context){
        if (numClics > 0) {
            _index.value--
        }
        _mediaPlayer.value!!.clearMediaItems()
        val mediaItem = MediaItem.fromUri(getRout(context, _canciones.value[index.value].image))
        _mediaPlayer.value!!.setMediaItem(mediaItem)
    }

    fun getRout(context: Context, nombreCancion: String): String {
        val resID = context.resources.getIdentifier(nombreCancion, "raw", context.packageName)
        return "android.resource://" + context.packageName + "/" + resID
    }

}
