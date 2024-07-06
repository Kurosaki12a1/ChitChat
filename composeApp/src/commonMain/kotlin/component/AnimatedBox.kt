package component

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/**
 * [AnimatedBox] allows to switch between two layouts with a CrossSlide animation.
 *
 * @param targetState is a key representing your target layout state. Every time you change a key
 * the animation will be triggered. The [content] called with the old key will be faded out while
 * the [content] called with the new key will be faded in.
 * @param modifier Modifier to be applied to the animation container.
 * @param animationSpec the [AnimationSpec] to configure the animation.
 */
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun <T> AnimatedBox(
    currentState: T,
    targetState: T,
    modifier: Modifier = Modifier,
    orderedContent: List<T> = emptyList(),
    content: @Composable (T) -> Unit
) {
    val direction =
        if (orderedContent.indexOf(currentState) < orderedContent.indexOf(targetState)) 1 else -1

    AnimatedContent(
        targetState = targetState,
        transitionSpec = {
            (slideInHorizontally { height -> height * direction } + fadeIn()).togetherWith(
                slideOutHorizontally { height -> -height * direction } + fadeOut())
        },
        content = { target ->
            Box(modifier = modifier) {
                content(target)
            }
        }
    )
}


data class SlideInOutAnimationState<T>(
    val key: T,
    val content: @Composable () -> Unit,
)
