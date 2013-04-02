fun foo(x: Int, <!UNUSED_PARAMETER!>y<!>: Any) = x
fun foo(<!UNUSED_PARAMETER!>x<!>: Any, y: Int) = y

fun main() {
    ::<!AMBIGUOUS_CALLABLE_REFERENCE!>foo<!>
    
    ::<!AMBIGUOUS_CALLABLE_REFERENCE!>foo<!> : (Int, Any) -> Unit
}
