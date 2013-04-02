class A

fun main() {
    fun foo() {}
    fun bar(<!UNUSED_PARAMETER!>x<!>: Int) {}
    fun baz() = "OK"
    
    class B {
        fun A.ext() {
            ::foo : KFunction0<Unit>
            ::bar : KFunction1<Int, Unit>
            ::baz : KFunction0<String>
        }
    }
}
