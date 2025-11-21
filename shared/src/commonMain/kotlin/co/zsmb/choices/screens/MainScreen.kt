package co.zsmb.choices.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import co.zsmb.choices.di.metroViewModel

@Composable
fun MainScreen(
    onShowList: () -> Unit,
) {
    val viewModel: MainViewModel = metroViewModel()
    val score by viewModel.score.collectAsStateWithLifecycle()
    var comment by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.primaryContainer)
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(Modifier.height(24.dp))
        Text(
            text = "$score",
            style = MaterialTheme.typography.displayLarge.copy(
                fontSize = 72.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 24.dp),
            textAlign = TextAlign.Center,
        )

        Spacer(Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Button(
                onClick = {
                    viewModel.addRecord(true, comment)
                    comment = ""
                },
                modifier = Modifier
                    .weight(2f)
                    .height(96.dp),
            ) {
                Text("+", fontSize = 36.sp)
            }

            Spacer(Modifier.width(12.dp))

            Button(
                onClick = {
                    viewModel.addRecord(false, comment)
                    comment = ""
                },
                modifier = Modifier
                    .weight(1f)
                    .height(96.dp),
            ) {
                Text("-", fontSize = 36.sp)
            }
        }

        Spacer(Modifier.height(16.dp))

        TextField(
            value = comment,
            onValueChange = { comment = it },
            modifier = Modifier
                .fillMaxWidth(),
            label = { Text("Comment (optional)") },
            singleLine = true,
            keyboardActions = KeyboardActions(onDone = { /* no-op */ })
        )

        Spacer(modifier = Modifier.weight(1f))

        TextButton(
            onClick = onShowList,
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Show list")
        }
    }
}
