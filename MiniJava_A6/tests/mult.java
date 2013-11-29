class testmain {

	public static void main(String[] args) {
		A a;
		int x;
		a = new A();
		x = 6 * a.test();
		System.out.println(x);
	}
}

class A {
	int test() {
		return 7;
	}
}