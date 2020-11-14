package LS1;

public class edge {
	long u;
	long v;
	
	edge(long u, long v) {
		this.u = u;
		this.v = v;
	}

	public void printEdge() {
		System.out.print("("+this.u+","+this.v+")");
	}
}
