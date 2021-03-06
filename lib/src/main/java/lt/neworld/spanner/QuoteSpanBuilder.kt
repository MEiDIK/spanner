package lt.neworld.spanner

import android.support.annotation.ColorInt
import android.text.style.QuoteSpan

/**
 * @author Andrius Semionovas
 * @since 2017-10-11
 */
internal class QuoteSpanBuilder(@ColorInt private val color: Int?) : SpanBuilder {
    override fun build(): Any {
        return if (color == null) {
            QuoteSpan()
        } else {
            QuoteSpan(color)
        }
    }
}
