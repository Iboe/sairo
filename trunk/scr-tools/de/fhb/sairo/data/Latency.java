package de.fhb.sairo.data;

public class Latency {

	private String from;
	private String to;
	private long time;
	
	public Latency(String from, String to, long time) {
		this.from = from;
		this.to = to;
		this.time = time;
	}
	
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("Latency from command: {" + from + "} to command: {" + to + "} = " + time + " ms");
		return sb.toString();
	}
	
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}
		
}
