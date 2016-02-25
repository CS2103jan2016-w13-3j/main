import java.util.*;

public class Task {
	public String event;
	public Date startTime;
	public Date endTime;
	public int priority = 0;
	public boolean done = false;
	

	// four constructors
	public Task() {
	        origin = new Point(0, 0);
	    }

	public Task(Point p) {
	        origin = p;
	    }

	public Task(int w, int h) {
	        origin = new Point(0, 0);
	        width = w;
	        height = h;
	    }

	public Task(Point p, int w, int h) {
	        origin = p;
	        width = w;
	        height = h;
	    }

	// a method for moving the rectangle
	public void move(int x, int y) {
		origin.x = x;
		origin.y = y;
	}

	// a method for computing the area of the rectangle
	public int getArea() {
		return width * height;
	}
}
