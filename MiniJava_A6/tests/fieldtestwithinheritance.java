class testmain {
	
	public static void main(String[] args) {
		
		A a;
		B b;
		
		a = new A();
		b = new B();
		
		a.f = "Hello World";
		System.out.println(a.f);
		a.test();
		b.test2();
	}
}

class A {
	String f;
	
	void test() {
		System.out.print(f+" ");
		f = "Blaaaah.";
		System.out.println(f);
		return;
	}
}

class B extends A {
	String g;
	
	void test2() {
		this.g = "asdasdas";
		this.test();
		this.f = "brrrrr";
		this.test();
		System.out.println(g);
		g = "Gaaaah.";
		System.out.println(g);
		return;
	}	
}