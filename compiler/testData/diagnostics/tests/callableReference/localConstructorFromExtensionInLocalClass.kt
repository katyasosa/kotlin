fun main() {
    class A
    
    class B {
        fun Int.foo() {
            ::A : KFunction0<A>
        }
        fun A.foo() {
            ::A : KFunction0<A>
        }
    }
}
