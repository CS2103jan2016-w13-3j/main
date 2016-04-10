//@@author A0112659A
package test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import simplyamazing.data.Task;

public class TaskTest {

	@Test
	public void testFloatingTaskCreation() {
		Task task = new Task("go swimming");
		assertEquals("go swimming, , , ,incomplete", task.toString());
	}
	
	@Test(expected = Exception.class) 
	public void testDeadlineCreation() throws Exception {
		Task task = new Task();
		task = new Task("go swimming", "17:30 30 May 2016");
		assertEquals("go swimming, ,17:30 30 May 2016, ,incomplete", task.toString());
	}
	
	@Test(expected = Exception.class) 
	public void testEventCreation() throws Exception {
		Task task = new Task();
		task = new Task("go swimming", "15:30 28 MAY 2016", "17:30 30 May 2016");
		assertEquals("go swimming,15:30 28 May 2016,17:30 30 May 2016, ,incomplete", task.toString());
	}

	@Test
	public void testSetDescriptionMethod() {
		Task task = new Task();
		task.setDescription("study for exam");
		assertEquals("study for exam, , , ,incomplete", task.toString());
	}
	
	@Test(expected = Exception.class)
	public void testSetStartTimeMethod() throws Exception {
		Task task = new Task("go home");
		task.setStartTime("15:30 28 May 2016"); 
		assertEquals("go home,15:30 28 May 2016, , ,incomplete", task.toString());
	}
	
	@Test(expected = Exception.class)
	public void testSetEndTimeMethod() throws Exception {
		Task task = new Task("go swimming");
		task.setEndTime("17:30 30 may 2016"); 
		assertEquals("go swimming, ,17:30 30 May 2016, ,incomplete", task.toString());
	}
	
	@Test(expected = Exception.class)
	public void testSetPriorityMethod() throws Exception {
		Task task = new Task("study for exam");
		task.setPriority("high");
		assertEquals("study for exam, , ,high,incomplete", task.toString());
	}
	
	@Test
	public void testSetDoneMethod() {
		Task task = new Task("go swimming");
		task.setDone(true);
		assertEquals("go swimming, , , ,done", task.toString());
		task.setDone(false);
		assertEquals("go swimming, , , ,incomplete", task.toString());
	}
	
}
