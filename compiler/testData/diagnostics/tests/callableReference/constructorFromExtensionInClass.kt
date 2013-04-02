class A

class B {
    fun A.ext() {
        ::A : KFunction0<A>
        ::B : KFunction0<B>
    }
    
    fun B.ext() {
        ::A : KFunction0<A>
        ::B : KFunction0<B>
    }
}
