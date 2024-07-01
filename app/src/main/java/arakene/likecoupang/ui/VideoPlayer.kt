package arakene.likecoupang.ui

import android.app.Activity
import android.content.Context
import android.content.pm.ActivityInfo
import android.content.pm.ActivityInfo.SCREEN_ORIENTATION_USER_LANDSCAPE
import android.net.Uri
import androidx.annotation.OptIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.common.util.Util
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.hls.HlsMediaSource
import androidx.media3.exoplayer.source.DefaultMediaSourceFactory
import androidx.media3.exoplayer.source.MediaSource
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.AspectRatioFrameLayout.ResizeMode
import androidx.media3.ui.PlayerView

@OptIn(UnstableApi::class)
@Composable
fun VideoPlayer(
    modifier: Modifier = Modifier,
    uri: Uri
){

    val context = LocalContext.current
    val activity = LocalContext.current as Activity

    val dataSourceFactory by rememberUpdatedState(newValue = DefaultHttpDataSource.Factory())

    val hlsMediaSource by rememberUpdatedState(newValue = HlsMediaSource.Factory(dataSourceFactory).createMediaSource(MediaItem.fromUri(uri)))

    val player by rememberUpdatedState(newValue = ExoPlayer.Builder(context).build().apply {
        setMediaSource(hlsMediaSource)
    })

    val playerView = createPlayerView(player = player)


    val enterFullScreen by rememberUpdatedState(newValue = {activity.requestedOrientation = SCREEN_ORIENTATION_USER_LANDSCAPE})
    val exitFullScreen by rememberUpdatedState(newValue = {activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_USER})

    LaunchedEffect(key1 = Unit) {
        activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_USER
    }

    LaunchedEffect(key1 = playerView) {
        playerView.apply {
            resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FIT
            useController = false
            controllerAutoShow = false // 플레이 컨트롤러 자동 보이기
            keepScreenOn = true // 비디오 재생 중 화면 자동으로 꺼지지 않도록 설정
            setShowBuffering(PlayerView.SHOW_BUFFERING_WHEN_PLAYING) // 버퍼링 인디케이터가 반드시 표시되어야하는 경우 해당 함수 설정, 요 옵션은 비디오가 재생중일 때만 나타남
            setFullscreenButtonClickListener { isFullScreen ->
//                if (isFullScreen){
//                    if (activity.requestedOrientation == ActivityInfo.SCREEN_ORIENTATION_USER) {
//                        enterFullScreen()
//                    }
//                }else {
//                    exitFullScreen()
//                }
            }
        }
    }

    LaunchedEffect(key1 = player) {
        player.playWhenReady = true
    }

    AndroidView(modifier = modifier, factory = {playerView})

}

@Composable
private fun createPlayerView(player: Player): PlayerView{

    val context = LocalContext.current
    val playerView = remember{
        PlayerView(context).apply {
            this.player = player
        }
    }

    DisposableEffect(key1 = player) {

        playerView.player = player
        player.prepare()


        onDispose {
            player.release()
        }
    }

    return playerView
}