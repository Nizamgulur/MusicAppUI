package com.example.musicappui.ui.theme

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.musicappui.R

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BrowseScreen(){
    val categories = listOf("Hits", "Happy", "Workout", "Running", "TGIF", "Yoga")
    LazyVerticalGrid(
        columns = GridCells.Fixed(2)
    ) {
        items(categories) { item ->
            Card(modifier = Modifier
                .padding(16.dp)
                .size(200.dp),
                onClick = {},
                border = BorderStroke(3.dp, color = Color.DarkGray)
            ){
                Column(verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    Text(text = item)
                    Image(painter = painterResource(id = R.drawable.baseline_article_24), contentDescription = item)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun cardpreview(){
    BrowseScreen()
}
