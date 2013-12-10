class testmain {

	public static void main(String[] args) {
		A a;
		B b;
		a = new A();
		b = new B();
		b = a;
	}
}

class A {

}

class B extends A {

}