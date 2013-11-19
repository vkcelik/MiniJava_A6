class testmain {
	
	public static void main(String[] args) {
		
		A a;
		A b;
		
		a = new A();
		a.f = "Hello World";
		System.out.println(a.f);
		b = a.x;
		b.f = "Hello World";
		System.out.println(b.f);
	}
}

class A {
	A x;
	String f;
}