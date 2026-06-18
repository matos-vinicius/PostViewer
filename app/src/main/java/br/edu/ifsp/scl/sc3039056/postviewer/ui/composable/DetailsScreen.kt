package br.edu.ifsp.scl.sc3039056.postviewer.ui.composable

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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.edu.ifsp.scl.sc3039056.postviewer.PostViewModel

/**
 * Tela de detalhes do post. Exibe os comentários da API
 * (GET /posts/{id}/comments), os comentários locais salvos via Room
 * e um formulário para adicionar um novo comentário local.
 */
@Composable
fun DetailsScreen(
    postViewModel: PostViewModel,
    modifier: Modifier = Modifier
) {
    val comments by postViewModel.comments.collectAsState()
    val localComments by postViewModel.localComments.collectAsState()
    val isLoading by postViewModel.isLoadingComments.collectAsState()
    val errorMessage by postViewModel.commentsErrorMessage.collectAsState()

    var authorName by remember { mutableStateOf("") }
    var commentBody by remember { mutableStateOf("") }

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
                onClick = { postViewModel.updateComments() },
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Text(text = "Tentar novamente")
            }
        }
    } else {
        LazyColumn(modifier = modifier.fillMaxWidth()) {
            item {
                Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                    OutlinedTextField(
                        value = authorName,
                        label = { Text("Seu nome") },
                        onValueChange = { authorName = it },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = commentBody,
                        label = { Text("Comentário") },
                        onValueChange = { commentBody = it },
                        modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
                    )
                    Button(
                        onClick = {
                            postViewModel.addLocalComment(authorName, commentBody)
                            authorName = ""
                            commentBody = ""
                        },
                        modifier = Modifier.padding(top = 8.dp)
                    ) {
                        Text("Comentar")
                    }
                }
                HorizontalDivider()
            }

            items(items = localComments, key = { "local-${it.id}" }) { comment ->
                Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                    Text(
                        text = "${comment.authorName} (comentário local)",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                    Text(text = comment.body)
                }
                HorizontalDivider()
            }

            items(items = comments, key = { it.id!! }) { comment ->
                Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                    Text(text = comment.name, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    Text(text = comment.email, fontSize = 12.sp)
                    Text(text = comment.body)
                }
                HorizontalDivider()
            }
        }
    }
}
