package furkan.senol.adapted_fba

import android.util.Patterns
import java.util.regex.Pattern

fun CharSequence?.isValidEmail() =
    !isNullOrEmpty() && !Patterns.EMAIL_ADDRESS.matcher(this).matches()

//Password Setting
fun isValidPasswordNumber(password: String?): Boolean {
    return password?.matches(Pattern.compile(".*[0-9].*").toRegex()) ?: false
}

fun isValidPasswordUpper(password: String?): Boolean {
    return password?.matches(Pattern.compile(".*[A-Z].*").toRegex()) ?: false
}

fun isValidPasswordLower(password: String?): Boolean {
    return password?.matches(Pattern.compile(".*[a-z].*").toRegex()) ?: false
}

fun isValidPasswordSpecialChar(password: String?): Boolean {
    return password?.matches(Pattern.compile(".*[@#\$%^&+.=_].*").toRegex()) ?: false
}

fun isValidPasswordLength(password: String?): Boolean {
    return password?.matches(Pattern.compile(".{6,}\$.*").toRegex()) ?: false
}



