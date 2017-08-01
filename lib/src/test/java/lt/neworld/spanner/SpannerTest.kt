package lt.neworld.spanner

import android.graphics.Typeface
import android.text.TextUtils
import android.text.style.StyleSpan
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

/**
 * @author Andrius Semionovas
 * @since 2017-08-01
 */
@RunWith(RobolectricTestRunner::class)
class SpannerTest {
    @Test
    fun init() {
        var builder = Spanner()
        assertEquals("", builder.toString())

        builder = Spanner("test")
        assertEquals("test", builder.toString())
    }

    @Test
    fun append() {
        val builder = Spanner("test ")
        builder.append("foo")
        assertEquals("test foo", builder.toString())

        builder.append(' ')
        assertEquals("test foo ", builder.toString())

    }

    @Test
    fun append_withSpan() {
        val builder = Spanner("test ")

        val span = StyleSpan(Typeface.NORMAL)
        builder.append("bar", span)
        assertEquals("test bar", builder.toString())
        assertEquals(span, builder.getSpans(0, builder.length - 1, StyleSpan::class.java)[0])

        builder.append(" abc ", 1, 2)
        assertEquals("test bara", builder.toString())
    }

    @Test
    fun insert() {
        val builder = Spanner("ab")

        val span = StyleSpan(Typeface.NORMAL)
        builder.insert(1, " foo ", span)
        assertEquals("a foo b", builder.toString())
        assertEquals(span, builder.getSpans(0, builder.length - 1, StyleSpan::class.java)[0])

        builder.insert(1, " bar")
        assertEquals("a bar foo b", builder.toString())

        builder.insert(1, "abc", 1, 2)
        assertEquals("ab bar foo b", builder.toString())
    }

    @Test
    fun replace() {
        val builder = Spanner("faa {replace}")

        var span = StyleSpan(Typeface.NORMAL)
        builder.replace(1, 3, "oo", span)
        assertEquals("foo {replace}", builder.toString())
        assertEquals(span, builder.getSpans(0, builder.length - 1, StyleSpan::class.java)[0])

        span = StyleSpan(Typeface.NORMAL)
        builder.replace("{replace}", "bar", span)
        assertEquals("foo bar", builder.toString())

        val start = TextUtils.indexOf(builder, "bar")
        assertEquals(span, builder.getSpans(start, builder.length, StyleSpan::class.java)[0])
    }

    @Test
    fun nonExistingReplace() {
        val builder = Spanner("foo bar")

        builder.replace("not exist", "good text", StyleSpan(Typeface.NORMAL))
        assertEquals("foo bar", builder.toString())
        assertEquals(0, builder.getSpans(0, builder.length - 1, StyleSpan::class.java).size)
    }

    @Test
    fun appendNull() {
        val builder = Spanner("foo")

        builder.append(null)
        assertEquals("foo", builder.toString())

        builder.append(null, StyleSpan(Typeface.NORMAL))
        assertEquals("foo", builder.toString())
    }

    @Test
    fun replaceWithNull() {
        val builder = Spanner("bar")

        builder.replace(1, 3, null, StyleSpan(Typeface.NORMAL))
        assertEquals("b", builder.toString())
    }
}