class A {
    class Nested
    
    fun main() {
        ::Nested : KFunction0<Nested>
        A::Nested : KFunction0<Nested>
    }
}

class B {
    fun main() {
        ::<!UNRESOLVED_REFERENCE!>Nested<!> : KFunction0<A.Nested>
        A::Nested : KFunction0<A.Nested>
    }
}
