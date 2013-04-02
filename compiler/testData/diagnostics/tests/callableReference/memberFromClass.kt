class A {
    fun foo() {}
    fun bar(<!UNUSED_PARAMETER!>x<!>: Int) {}
    fun baz() = "OK"
    
    fun main() {
        ::foo : KMemberFunction0<A, Unit>
        ::bar : KMemberFunction1<A, Int, Unit>
        ::baz : KMemberFunction0<A, String>
    }
}
