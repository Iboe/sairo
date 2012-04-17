package de.fhb.sailboat.ufer.prototyp.communication;
import java.io.Serializable;

public class TestWerte implements Serializable{
	private static final long serialVersionUID = -376302004636704066L;
	
	private int test;
	private int anotherTest;
	private String andOneMore;
	public int getTest() {
		return test;
	}
	public void setTest(int test) {
		this.test = test;
	}
	public int getAnotherTest() {
		return anotherTest;
	}
	public void setAnotherTest(int anotherTest) {
		this.anotherTest = anotherTest;
	}
	public String getAndOneMore() {
		return andOneMore;
	}
	public void setAndOneMore(String andOneMore) {
		this.andOneMore = andOneMore;
	}
	@Override
	public String toString() {
		return "Worldmodel [test=" + test + ", anotherTest=" + anotherTest
				+ ", andOneMore=" + andOneMore + "]";
	}
	public TestWerte(int test, int anotherTest, String andOneMore) {
		super();
		this.test = test;
		this.anotherTest = anotherTest;
		this.andOneMore = andOneMore;
	}
	public TestWerte() {
		// TODO Auto-generated constructor stub
	}	
}
