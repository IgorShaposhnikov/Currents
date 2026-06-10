package ru.igorshaposhnikov.currents

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import ru.igorshaposhnikov.currents.ui.screens.DetailScreen
import ru.igorshaposhnikov.currents.ui.screens.NewsListScreen
import ru.igorshaposhnikov.currents.ui.theme.CurrentsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CurrentsTheme {
                val navController = rememberNavController()
                Surface(modifier = Modifier.fillMaxSize()) {
                    NavHost(
                        navController = navController,
                        startDestination = "news_list",
                        modifier = Modifier.fillMaxSize(),
                    ) {
                        composable("news_list") {
                            NewsListScreen(
                                onArticleClick = { article ->
                                    navController.navigate(
                                        "detail/${java.net.URLEncoder.encode(article.url, "UTF-8")}" +
                                        "?title=${java.net.URLEncoder.encode(article.title, "UTF-8")}" +
                                        "&source=${java.net.URLEncoder.encode(article.sourceName, "UTF-8")}" +
                                        "&desc=${java.net.URLEncoder.encode(article.description ?: "", "UTF-8")}"
                                    )
                                },
                            )
                        }
                        composable(
                            route = "detail/{url}?title={title}&source={source}&desc={desc}",
                            arguments = listOf(
                                navArgument("url") { type = NavType.StringType },
                                navArgument("title") { type = NavType.StringType; defaultValue = "" },
                                navArgument("source") { type = NavType.StringType; defaultValue = "" },
                                navArgument("desc") { type = NavType.StringType; defaultValue = "" },
                            ),
                        ) {
                            DetailScreen(
                                onBack = { navController.popBackStack() },
                            )
                        }
                    }
                }
            }
        }
    }
}
