class testmain {
	
	public static void main(String[] args) {
		
		A a;
		String s;
	    a = new A();
	    s = "!";
		a.x = 0;
		
		while ( a.x < 10) {
			
			if ( !(a.x < 1) && a.x < 3) {
				System.out.println(s);
			}
			a.x = a.x+1;
		}
	}
	
}

class A {
	int x;
}