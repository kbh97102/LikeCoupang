package arakene.likecoupang.ui

import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun TestVideoView(
    title: String,
    uri: Uri,
    modifier: Modifier = Modifier
){

    Column(modifier) {
        VideoPlayer(uri = uri)

        Text(text = title)
    }

}