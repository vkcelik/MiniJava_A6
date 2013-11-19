class testmain {
	
	public static void main(String[] args) {
		
		A a;
		
		a = new A();
		
		a.f = "Hello World";
		System.out.println(a.f);
		a.test();
	}
}

class A {
	String f;
	
	void test() {
		System.out.println(f);
		f = "Blaaaah.";
		System.out.println(f);
		return;
	}
}