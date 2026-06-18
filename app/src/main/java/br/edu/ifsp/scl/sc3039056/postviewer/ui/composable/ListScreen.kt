package br.edu.ifsp.scl.sc3039056.postviewer.ui.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.edu.ifsp.scl.sc3039056.postviewer.PostViewModel

/**
 * Tela inicial do aplicativo. Exibe a lista de posts carregada da API
 * (GET /posts). Ao tocar em um item, navega para a tela de detalhes.
 */
@Composable
fun ListScreen(
    postViewModel: PostViewModel,
    modifier: Modifier = Modifier,
    onPostClick: () -> Unit
) {
    val posts by postViewModel.posts.collectAsState()
    val isLoading by postViewModel.isLoadingPosts.collectAsState()
    val errorMessage by postViewModel.postsErrorMessage.collectAsState()

    if (isLoading) {
        Column(
            modifier = modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            CircularProgressIndicator()
        }
    } else if (errorMessage != null) {
        Column(
            modifier = modifier.fillMaxSize().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = errorMessage ?: "")
            Button(
                onClick = { postViewModel.updatePosts() },
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Text(text = "Tentar novamente")
            }
        }
    } else {
        LazyColumn(modifier = modifier.fillMaxWidth()) {
            items(items = posts, key = { it.id!! }) { post ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            postViewModel.setSelectedPost(post)
                            onPostClick()
                        }
                        .padding(16.dp)
                ) {
                    Text(text = post.title, fontSize = 18.sp)
                }
                HorizontalDivider()
            }
        }
    }
}
