class A {
    inner class Inner
}

fun main() {
    ::<!UNRESOLVED_REFERENCE!>Inner<!> : KMemberFunction0<A, A.Inner>
    A::Inner : KMemberFunction0<A, A.Inner>
}
