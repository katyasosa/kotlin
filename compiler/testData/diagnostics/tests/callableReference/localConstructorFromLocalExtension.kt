fun main() {
    class A
    
    fun A.foo() {
        ::A : KFunction0<A>
    }
    
    fun Int.foo() {
        ::A : KFunction0<A>
    }
}
