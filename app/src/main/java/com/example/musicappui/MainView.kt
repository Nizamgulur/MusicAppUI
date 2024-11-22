package com.example.musicappui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ScaffoldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.CoroutineScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.primarySurface
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.musicappui.ui.theme.AccountDailog
import com.example.musicappui.ui.theme.AccountView
import com.example.musicappui.ui.theme.BrowseScreen
import com.example.musicappui.ui.theme.Home
import com.example.musicappui.ui.theme.Library
import com.example.musicappui.ui.theme.Subscriptionview
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun MainView() {

    val scaffoldState: ScaffoldState = rememberScaffoldState()
    val scope: CoroutineScope = rememberCoroutineScope()
    val controller: NavController = rememberNavController()
    val navBackStackEntry by controller.currentBackStackEntryAsState()
    val viewModel:MainViewModel=viewModel()
    val currentRoute = navBackStackEntry?.destination?.route

    val isSheetFullScreen by remember{ mutableStateOf(false) }

    val modifier = if(isSheetFullScreen) Modifier.fillMaxSize() else Modifier.fillMaxWidth()
    val dialogOpen= remember{
        mutableStateOf(false)
    }
    val currentScreen= remember {
        viewModel.currentScreen.value
    }

    val title = remember{
        mutableStateOf(currentScreen.title)
    }
    val modalsheetstate= rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmValueChange = { it != ModalBottomSheetValue.HalfExpanded}
    )

    val roundedCornerRadius = if(isSheetFullScreen) 0.dp else 12.dp

    val bottomBar: @Composable () -> Unit ={
        if(currentScreen is Screen.DrawerScreen || currentScreen == Screen.BottomScreen.Home){
            BottomNavigation(modifier=Modifier.wrapContentSize()) {
                screensInBottom.forEach{
                    item->
                    val isselected = currentRoute==item.Broute
                    val tint = if(isselected) Color.White else Color.Black
                    BottomNavigationItem(selected = currentRoute==item.Broute,
                        onClick = { controller.navigate(item.Broute)
                                  title.value = item.Btitle
                                  },
                        icon = { Icon(tint=tint,painter = painterResource(id = item.icon),contentDescription = item.Btitle)
                    },
                        label={ Text(text = item.Btitle,color=tint)},
                        selectedContentColor = Color.White,
                        unselectedContentColor = Color.Black
                    )
                }
            }
        }
    }

    ModalBottomSheetLayout(sheetState = modalsheetstate, sheetShape = RoundedCornerShape(topStart = roundedCornerRadius, topEnd = roundedCornerRadius),sheetContent ={
        morebottomsheet(modifier = modifier)
    } ) {
        Scaffold(
            bottomBar = bottomBar,
            topBar = {
                TopAppBar(title = { Text(title.value) },
                    actions = {
                        IconButton(onClick = { scope.launch {
                            if(modalsheetstate.isVisible)
                                modalsheetstate.hide()
                            else
                                modalsheetstate.show()

                        }}) {
                            Icon(imageVector = Icons.Default.MoreVert, contentDescription = "three dots option")
                        }
                    },
                    navigationIcon = {
                        IconButton(onClick = {
                            //Open the Drawer
                            scope.launch {
                                scaffoldState.drawerState.open()
                            }
                        }) {
                            Icon(imageVector = Icons.Default.AccountCircle, contentDescription = "Menu")
                        }
                    }
                )
            }, scaffoldState = scaffoldState,
            drawerContent = {
                LazyColumn(Modifier.padding(16.dp)) {
                    items(screensInDrawer) {
                            item ->
                        DrawerItem(selected = currentRoute == item.Droute, item=item){
                            scope.launch {
                                scaffoldState.drawerState.close()
                            }
                            if (item.Droute=="add_account"){
                                //open Dailog
                                dialogOpen.value =true
                            }
                            else{
                                controller.navigate(item.Droute)
                                title.value = item.Dtitle
                            }
                        }
                    }
                }
            }
        ) {
            Navigation(navController = controller, viewModel = viewModel, pd =it )
            AccountDailog(dailogOpen = dialogOpen)
        }
    }
}

@Composable
fun DrawerItem(
        selected:Boolean,
        item:Screen.DrawerScreen,
        onDrawerItemClicked:()->Unit
    ){
        val background = if(selected) Color.DarkGray else Color.White
        Row(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 16.dp)
                .background(background)
                .clickable {
                    onDrawerItemClicked()
                }
            )  {
            Icon(
                painter = painterResource(id = item.icon),
                contentDescription =item.Dtitle,Modifier.padding(end=8.dp,top=4.dp)
            )
            Text(text = item.Dtitle,
                style = MaterialTheme.typography.h5,
            )
        }
    }

@Composable
fun morebottomsheet(
    modifier: Modifier
){
    Box(
        Modifier
            .fillMaxWidth()
            .height(300.dp)
            .background(MaterialTheme.colors.primarySurface)
    ){
        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.SpaceBetween) {
            Row(Modifier.padding(16.dp)) {
                Icon(modifier=Modifier.padding(end=8.dp),
                    painter= painterResource(id = R.drawable.baseline_settings_24),contentDescription="Setting")
                Text(text = "Settings", fontSize = 20.sp, color = Color.White)
            }
            Row(Modifier.padding(16.dp)) {
                Icon(modifier=Modifier.padding(end=8.dp),
                    painter= painterResource(id = R.drawable.ic_baseline_share_24),contentDescription="Setting")
                Text(
                    text = "Share",
                    fontSize = 20.sp,
                    color = Color.White
                )
            }

            Row(Modifier.padding(16.dp)) {
                Icon(modifier=Modifier.padding(end=8.dp),
                    painter= painterResource(id = R.drawable.ic_help_green),contentDescription="Setting")
                Text(text = "Help", fontSize = 20.sp, color = Color.White)
            }
        }
    }
}

@Composable
fun Navigation(navController: NavController ,viewModel: MainViewModel,pd:PaddingValues){
    NavHost(navController = navController as NavHostController,
        startDestination = Screen.BottomScreen.Home.route,
        modifier=Modifier.padding(pd)){
        composable(Screen.DrawerScreen.Account.route){
            AccountView()
        }
        composable(Screen.DrawerScreen.Subscription.route){
            Subscriptionview()
        }
        composable(Screen.BottomScreen.Library.Broute){
            Library()
        }
        composable(Screen.BottomScreen.Browse.Broute){
            BrowseScreen()
        }
        composable(Screen.BottomScreen.Home.Broute){
            Home()
        }
    }
}


