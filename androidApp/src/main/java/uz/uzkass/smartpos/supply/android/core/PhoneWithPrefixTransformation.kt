package uz.uzkass.smartpos.supply.android.core

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

class PhoneWithPrefixTransformation(private val prefix: String) : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        return prefixFilter(text, prefix)
    }
}

fun prefixFilter(number: AnnotatedString, prefix: String): TransformedText {
    val input = number.text
    val output = StringBuilder()
    output.append(prefix)
    output.append(" (")
    input.forEachIndexed { index, c ->
        when (index) {
            2 -> {
                output.append(") ")
                output.append(c)
            }
            5 -> {
                output.append("-")
                output.append(c)
            }
            7 -> {
                output.append("-")
                output.append(c)
            }
            else -> {
                output.append(c)
            }
        }
    }

    val resultText = output.toString()

    val numberOffsetTranslator = object : OffsetMapping {
        override fun originalToTransformed(offset: Int): Int {
            if (offset <= 2) return offset + 6
            if (offset <= 5) return offset + 8
            if (offset <= 7) return offset + 9
            if (offset <= 9) return offset + 10
            return 19
        }

        override fun transformedToOriginal(offset: Int): Int {
            if (offset < 6) return 0
            if (offset <= 8) return offset - 6
            if (offset <= 13) return offset - 8
            if (offset <= 16) return offset - 9
            if (offset <= 20) return offset - 10
            return 9
        }
    }

    return TransformedText(AnnotatedString(resultText), numberOffsetTranslator)
}

