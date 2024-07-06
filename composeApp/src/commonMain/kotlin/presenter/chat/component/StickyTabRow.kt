package presenter.chat.component

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.Divider
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScrollableTabRow
import androidx.compose.material.Tab
import androidx.compose.material.TabRowDefaults.DividerOpacity
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import chitchatmultiplatform.composeapp.generated.resources.Res
import chitchatmultiplatform.composeapp.generated.resources.all
import chitchatmultiplatform.composeapp.generated.resources.favorites
import chitchatmultiplatform.composeapp.generated.resources.unread
import org.jetbrains.compose.resources.stringResource

@Composable
fun StickyTabRow(
    selectedTabIndex: Int,
    onTabSelected: (Int) -> Unit
) {
    // List of tab titles from string resources
    val tabs = listOf(
        stringResource(Res.string.all),
        stringResource(Res.string.unread),
        stringResource(Res.string.favorites)
    )

    Box {
        ScrollableTabRow(
            backgroundColor = Color.White, // Background color for the TabRow
            selectedTabIndex = selectedTabIndex,
            edgePadding = 0.dp, // No padding at the edges
            indicator = { tabList ->
                // Get the current tab position
                val currentTabPosition = tabList[selectedTabIndex]

                // Animate the width of the indicator based on the tab width
                val currentTabWidth by animateDpAsState(
                    targetValue = currentTabPosition.width,
                    animationSpec = tween(durationMillis = 250, easing = FastOutSlowInEasing)
                )

                // Animate the offset of the indicator based on the tab position
                val indicatorOffset by animateDpAsState(
                    targetValue = currentTabPosition.left,
                    animationSpec = tween(durationMillis = 250, easing = FastOutSlowInEasing)
                )
                Box(
                    modifier = Modifier
                        .width(currentTabWidth) // Set the width of the indicator
                        .offset(x = indicatorOffset), // Set the offset of the indicator
                    contentAlignment = Alignment.BottomStart
                ) {
                    // Width = x -> offset = (1-x) / 2
                    Divider(
                        modifier = Modifier
                            .width(currentTabWidth * 0.7f) // Indicator width is 70% of the tab width
                            .height(2.dp)
                            .offset(x = currentTabWidth * 0.15f) // Center the indicator within the tab
                            .background(Color.Black)
                    )
                }
            },
            divider = {
                // No need use default divider of tab so just write here
            }
        ) {
            tabs.forEachIndexed { index, title ->
                val isSelected = index == selectedTabIndex
                Tab(
                    modifier = Modifier.wrapContentWidth(),
                    selected = isSelected,
                    onClick = { onTabSelected(index) },
                    text = {
                        Text(
                            text = title,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                            style = MaterialTheme.typography.button
                        )
                    },
                )
            }
        }

        // Bottom divider for the TabRow
        Divider(
            modifier = Modifier.fillMaxWidth()
                .height(1.dp)
                .background(LocalContentColor.current.copy(alpha = DividerOpacity))
                .align(Alignment.BottomStart)
        )
    }
}
