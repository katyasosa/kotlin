class A {
    inner class Inner
}
    
fun A.main() {
    ::Inner : KMemberFunction0<A, A.Inner>
    A::Inner : KMemberFunction0<A, A.Inner>
}

fun Int.main() {
    ::<!UNRESOLVED_REFERENCE!>Inner<!> : KMemberFunction0<A, A.Inner>
    A::Inner : KMemberFunction0<A, A.Inner>
}
