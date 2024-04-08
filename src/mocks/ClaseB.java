package mocks;

public class ClaseB {
	ClaseA ca;

	public ClaseB(ClaseA ca) {
		super();
		this.ca = ca;
	}
	public int met1() {
		ca.func1();
		ca.func1();
		return ca.func3(5, 6);
	}
	public void met2() {
	ca.func2();
	}
}
