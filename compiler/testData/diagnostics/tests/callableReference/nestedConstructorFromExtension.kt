class A {
    class Nested
}
    
fun A.main() {
    ::Nested : KFunction0<A.Nested>
    A::Nested : KFunction0<A.Nested>
}

fun Int.main() {
    ::<!UNRESOLVED_REFERENCE!>Nested<!> : KFunction0<A.Nested>
    A::Nested : KFunction0<A.Nested>
}
