package ru.igorshaposhnikov.currents.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.igorshaposhnikov.currents.data.repository.model.Article
import ru.igorshaposhnikov.currents.ui.components.NewsCard
import ru.igorshaposhnikov.currents.ui.viewmodel.NewsTab
import ru.igorshaposhnikov.currents.ui.viewmodel.NewsViewModel
import ru.igorshaposhnikov.currents.ui.viewmodel.UiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsListScreen(
    modifier: Modifier = Modifier,
    onArticleClick: (Article) -> Unit = {},
    viewModel: NewsViewModel = viewModel(),
) {
    val state by viewModel.uiState.collectAsState()
    val isRefreshing by viewModel.isRefreshing.collectAsState()
    val selectedTab by viewModel.selectedTab.collectAsState()

    Column(modifier = modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            FilterChip(
                selected = selectedTab == NewsTab.ALL,
                onClick = { viewModel.selectTab(NewsTab.ALL) },
                label = { Text("All News") },
            )
            FilterChip(
                selected = selectedTab == NewsTab.BOOKMARKS,
                onClick = { viewModel.selectTab(NewsTab.BOOKMARKS) },
                label = { Text("Saved") },
            )
        }

        Box(modifier = Modifier.fillMaxSize()) {
            when (val currentState = state) {
                is UiState.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center),
                    )
                }

                is UiState.Error -> {
                    Column(
                        modifier = Modifier.align(Alignment.Center),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                    ) {
                        Text(
                            text = currentState.message,
                            style = MaterialTheme.typography.bodyLarge,
                        )
                        Button(
                            onClick = { viewModel.refresh() },
                            modifier = Modifier.padding(top = 16.dp),
                        ) {
                            Text("Retry")
                        }
                    }
                }

                is UiState.Success -> {
                    PullToRefreshBox(
                        isRefreshing = isRefreshing,
                        onRefresh = { viewModel.refresh() },
                        modifier = Modifier.fillMaxSize(),
                    ) {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(16.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp),
                        ) {
                            items(
                                items = currentState.articles,
                                key = { it.url },
                            ) { article ->
                                NewsCard(
                                    article = article,
                                    onClick = { onArticleClick(article) },
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
