package co.zsmb.choices.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ListItem
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import co.zsmb.choices.data.Record
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Instant

@Composable
fun RecordItem(record: Record) {
    val ts = record.timestamp.toLocalDateTime(TimeZone.currentSystemDefault())
    ListItem(
        headlineContent = {
            val text = record.comment?.takeIf { it.isNotBlank() }
            if (text != null) {
                Text(text)
            } else {
                Text(
                    "no comment",
                    color = LocalContentColor.current.copy(alpha = 0.5f),
                    fontStyle = FontStyle.Italic,
                )
            }
        },
        supportingContent = {
            Text(text = "${ts.date} ${ts.time}")
        },
        leadingContent = {
            Text(text = "#${record.id}")
        },
        trailingContent = {
            val isPositive = record.score
            val bg = if (isPositive) Color(0xFF2E7D32) else Color(0xFFC62828)
            Surface(
                shape = CircleShape,
                color = bg,
            ) {
                Box(
                    modifier = Modifier.size(40.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = if (isPositive) "+1" else "−1",
                        color = Color.White,
                        style = MaterialTheme.typography.labelLarge,
                    )
                }
            }
        },
        modifier = Modifier.fillMaxWidth()
    )
}

@PreviewLightDark
@PreviewFontScale
@Composable
private fun RecordItemPreviewPositiveWithComment() {
    AppTheme {
        RecordItem(
            Record(
                id = 1,
                score = true,
                comment = "I cooked two meals today!",
                timestamp = Instant.fromEpochMilliseconds(1732270000000L)
            )
        )
    }
}

@PreviewLightDark
@PreviewFontScale
@Composable
private fun RecordItemPreviewNegativeWithComment() {
    AppTheme {
        RecordItem(
            Record(
                id = 2,
                score = false,
                comment = "I forgot to do laundry",
                timestamp = Instant.fromEpochMilliseconds(1713270000000L)
            )
        )
    }
}

@PreviewLightDark
@PreviewFontScale
@Composable
private fun RecordItemPreviewPositiveNoComment() {
    AppTheme {
        RecordItem(
            Record(
                id = 3,
                score = true,
                comment = null,
                timestamp = Instant.fromEpochMilliseconds(1743270000000L)
            )
        )
    }
}

@PreviewLightDark
@PreviewFontScale
@Composable
private fun RecordItemPreviewNegativeNoComment() {
    AppTheme {
        RecordItem(
            Record(
                id = 4,
                score = false,
                comment = "",
                timestamp = Instant.fromEpochMilliseconds(1732470000000L)
            )
        )
    }
}
