fun main() {
    fun foo() {}
    fun bar(<!UNUSED_PARAMETER!>x<!>: Int) {}
    fun baz() = "OK"
    
    class A {
        val x = ::foo : KFunction0<Unit>
        val y = ::bar : KFunction1<Int, Unit>
        val z = ::baz : KFunction0<String>
    }
}
