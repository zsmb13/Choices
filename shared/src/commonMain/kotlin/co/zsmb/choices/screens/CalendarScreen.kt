package co.zsmb.choices.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import co.zsmb.choices.di.metroViewModel
import co.zsmb.choices.ui.RecordItem
import com.kizitonwose.calendar.compose.HorizontalCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.firstDayOfWeekFromLocale
import kotlinx.coroutines.launch
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.TimeZone
import kotlinx.datetime.YearMonth
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime
import kotlin.math.abs
import kotlin.time.Clock

@Composable
fun CalendarScreen(
    onBack: () -> Unit,
) {
    val viewModel: CalendarViewModel = metroViewModel()
    val dayDataByDate by viewModel.dayDataByDate.collectAsStateWithLifecycle()
    val selectedDate by viewModel.selectedDate.collectAsStateWithLifecycle()
    
    val currentMonth = remember { 
        val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
        YearMonth(now.year, now.month)
    }
    val startMonth = remember { currentMonth.minus(100, DateTimeUnit.MONTH) }
    val endMonth = remember { currentMonth.plus(100, DateTimeUnit.MONTH) }
    val firstDayOfWeek = remember { firstDayOfWeekFromLocale() }

    val state = rememberCalendarState(
        startMonth = startMonth,
        endMonth = endMonth,
        firstVisibleMonth = currentMonth,
        firstDayOfWeek = firstDayOfWeek
    )
    
    val visibleMonth =  state.firstVisibleMonth.yearMonth
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize()
            .padding(16.dp)
    ) {
        TextButton(
            onClick = onBack,
            modifier = Modifier.align(Alignment.Start)
        ) {
            Text("Back")
        }
        
        val monthName = visibleMonth.firstDay.month.name.lowercase().replaceFirstChar { it.uppercase() }
        val year = visibleMonth.firstDay.year
        
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = {
                    coroutineScope.launch {
                        val previousMonth = visibleMonth.minus(1, DateTimeUnit.MONTH)
                        state.animateScrollToMonth(previousMonth)
                    }
                }
            ) {
                Text("◀", style = MaterialTheme.typography.titleLarge)
            }
            
            Text(
                text = "$monthName $year",
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center
            )
            
            IconButton(
                onClick = {
                    coroutineScope.launch {
                        val nextMonth = visibleMonth.plus(1, DateTimeUnit.MONTH)
                        state.animateScrollToMonth(nextMonth)
                    }
                }
            ) {
                Text("▶", style = MaterialTheme.typography.titleLarge)
            }
        }

        HorizontalCalendar(
            state = state,
            dayContent = { day ->
                Day(
                    day = day,
                    dayData = dayDataByDate[day.date],
                    isSelected = day.date == selectedDate,
                    onDayClick = { viewModel.selectDate(day.date) }
                )
            }
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        val selectedDayData = dayDataByDate[selectedDate]
        if (selectedDayData != null && selectedDayData.records.isNotEmpty()) {
            Text(
                text = "Records for ${selectedDate}",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            LazyColumn(
                modifier = Modifier.fillMaxWidth()
            ) {
                items(selectedDayData.records) { record ->
                    RecordItem(record)
                }
            }
        } else {
            Text(
                text = "No records for ${selectedDate}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun Day(
    day: CalendarDay,
    dayData: DayData?,
    isSelected: Boolean,
    onDayClick: () -> Unit
) {
    val backgroundColor = when {
        dayData == null -> MaterialTheme.colorScheme.surface
        else -> {
            val score = dayData.score.toFloat()
            val maxScore = 5f
            val normalizedScore = (score / maxScore).coerceIn(-1f, 1f)
            
            when {
                normalizedScore > 0 -> lerp(Color.Gray, Color.Green, normalizedScore * 0.9f)
                normalizedScore < 0 -> lerp(Color.Gray, Color.Red, abs(normalizedScore) * 0.9f)
                else -> Color.Gray
            }
        }
    }.copy(alpha = 0.7f)
    
    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .padding(4.dp)
            .background(
                color = backgroundColor,
                shape = CircleShape
            )
            .then(
                if (isSelected) {
                    Modifier.border(2.dp, MaterialTheme.colorScheme.primary, CircleShape)
                } else {
                    Modifier
                }
            )
            .clickable { onDayClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = day.date.dayOfMonth.toString(),
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

