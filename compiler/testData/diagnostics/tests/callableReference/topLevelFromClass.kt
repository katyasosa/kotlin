fun foo() {}
fun bar(<!UNUSED_PARAMETER!>x<!>: Int) {}
fun baz() = "OK"

class A {
    fun main() {
        ::foo : KFunction0<Unit>
        ::bar : KFunction1<Int, Unit>
        ::baz : KFunction0<String>
    }
}
