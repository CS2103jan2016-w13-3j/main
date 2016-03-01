import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TaskTest {

	@Test
	public void testFloatingTaskCreation() {
		Task task = new Task("go swimming");
		assertEquals("go swimming, , , , ", task.toString());
	}
	
	@Test(expected = Exception.class) 
	public void testDeadlineCreation() throws Exception {
		Task task = new Task("go swimming", "17:30 28 Feb");
		task = new Task("go swimming", "17:30 28 Feb 2016");
		assertEquals("go swimming,,17:30 28 Feb 2016, , ", task.toString());
	}
	
	@Test(expected = Exception.class) 
	public void testEventCreation() throws Exception {
		Task task = new Task("go swimming", "15:30 28 Feb", "17:30 28 Feb");
		task = new Task("go swimming", "15:30 28 Feb 2016", "17:30 28 Feb 2016");
		assertEquals("go swimming,15:30 28 Feb 2016,17:30 28 Feb 2016, , ", task.toString());
	}

	@Test
	public void testSetDescriptionMethod() {
		Task task = new Task("go swimming");
		task.setDescription("study for exam");
		assertEquals("study for exam, , , , ", task.toString());
	}
	
	@Test(expected = Exception.class)
	public void testSetStartTimeMethod() throws Exception {
		Task task = new Task("go swimming");
		task.setStartTime("15:30 28 Feb");
		task.setStartTime("15:30 28 Feb 2016"); 
		assertEquals("go swimming,15:30 28 Feb 2016, , , ", task.toString());
	}
	
	@Test(expected = Exception.class)
	public void testSetEndTimeMethod() throws Exception {
		Task task = new Task("go swimming");
		task.setEndTime("17:30 28 Feb");
		task.setEndTime("17:30 28 Feb 2016"); 
		assertEquals("go swimming,,17:30 28 Feb 2016, , ", task.toString());
	}
	
	@Test(expected = Exception.class)
	public void testSetPriorityMethod() throws Exception {
		Task task = new Task("study for exam");
		task.setPriority("very high");
		task.setPriority("high");
		assertEquals("study for exam, , ,high, ", task.toString());
	}
	
	@Test
	public void testSetDoneMethod() {
		Task task = new Task("go swimming");
		task.setDone(true);
		assertEquals("go swimming, , , ,done", task.toString());
		task.setDone(false);
		assertEquals("go swimming, , , , ", task.toString());
	}
	
}
