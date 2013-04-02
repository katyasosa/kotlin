class A {
    fun foo() = 42
}

fun A.foo() {}

fun main() {
    A::<!AMBIGUOUS_CALLABLE_REFERENCE!>foo<!>
}
