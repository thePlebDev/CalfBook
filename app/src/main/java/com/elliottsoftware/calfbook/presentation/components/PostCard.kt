package com.elliottsoftware.calfbook.presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.elliottsoftware.calfbook.data.remote.Post


@Composable
fun PostCard(
    post:Post
){
    Card(
        shape = MaterialTheme.shapes.small,
        modifier = Modifier
            .padding(bottom = 6.dp, top = 6.dp)
            .fillMaxWidth(),
        elevation = 8.dp
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp, bottom = 12.dp, start = 8.dp, end = 8.dp)
        ){
            Text(text = post.title, modifier = Modifier
                .fillMaxWidth(0.85f)
                .wrapContentWidth(Alignment.Start))
            Text(
                text = post.id.toString(),
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentWidth(Alignment.End)
                    .align(Alignment.CenterVertically)
            )


        }
    }
}