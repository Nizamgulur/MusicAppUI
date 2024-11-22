package com.example.musicappui.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.TextButton
import androidx.compose.material.AlertDialog
import androidx.compose.material.TextField
import androidx.compose.material.MaterialTheme
import androidx.compose.material.primarySurface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties


@Composable
fun AccountDailog(dailogOpen:MutableState<Boolean>){
    if(dailogOpen.value){
        AlertDialog(
            onDismissRequest = {
                dailogOpen.value=false
            },
            confirmButton = {
                TextButton(onClick = {
                    dailogOpen.value =false
                }) {
                    Text("Confirm")
                }

            },
            dismissButton = {
                TextButton(onClick = {
                    dailogOpen.value =false
                }) {
                    Text("Dismiss")
                }

            },
            title = {
                Text(text="Add Account",
                    textAlign = TextAlign.Center
                )
            },
            text = {
                Column(modifier= Modifier
                    .wrapContentHeight()
                    .padding(16.dp),
                    verticalArrangement = Arrangement.Center) {
                    TextField(value = "", onValueChange = {

                    },modifier=Modifier.padding(top=16.dp),label={
                        Text(text = "Email")
                    })

                    TextField(value = "", onValueChange = {

                    },modifier=Modifier.padding(top=8.dp),label={
                        Text(text = "Password")
                    })
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colors.primarySurface)
                .padding(8.dp),
            shape = RoundedCornerShape(5.dp),
            backgroundColor = Color.White,
            properties = DialogProperties(
                dismissOnBackPress = true,
                dismissOnClickOutside = true
            )
        )
    }

}

@Preview(showBackground = true)
@Composable
fun dialogprev(){
    AccountDailog(dailogOpen = remember{
        mutableStateOf(true)
    } )
}