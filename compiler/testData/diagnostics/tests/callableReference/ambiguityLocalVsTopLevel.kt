fun bar() = 42

fun main() {
    fun bar() = 239
    ::<!AMBIGUOUS_CALLABLE_REFERENCE!>bar<!>
}
