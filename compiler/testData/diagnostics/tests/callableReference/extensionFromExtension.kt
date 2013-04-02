class A {
    fun main() {
        ::foo : KExtensionFunction0<A, Unit>
        ::bar : KExtensionFunction1<A, Int, Unit>
        ::baz : KExtensionFunction0<A, String>
    }
}

fun A.foo() {}
fun A.bar(<!UNUSED_PARAMETER!>x<!>: Int) {}
fun A.baz() = "OK"
