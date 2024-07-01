package arakene.likecoupang.ui

import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.util.UUID

@Composable
fun Main() {
    val test by remember {
        mutableStateOf(Uri.parse("http://playertest.longtailvideo.com/adaptive/wowzaid3/playlist.m3u8"))
    }

    /**
     * 로딩이 안끝 난 경우 화면을 꽉채운 controller 화면이 나옴 어떻게 해결하지?
     * lazyRow를 사용하면 안보이고 보이고 하면서 recomposition이 되면서 재생이 처음부터 다시 되는건가?
     * 화면의 정비율을 어느정도 지키고 싶은데 어떻게 하면 될까
     * 터치한 아이템만 재생되고 나머지는 멈추도록하면 어느정도 해소는 될 것 같다
     *
     */

    LazyRow(contentPadding = PaddingValues(horizontal = 20.dp), horizontalArrangement = Arrangement.spacedBy(20.dp)) {
        items(5, key = { UUID.randomUUID().toString() }) {
            TestVideoView(title = "TEST $it", uri = test, modifier = Modifier.width(300.dp))
        }
    }

}