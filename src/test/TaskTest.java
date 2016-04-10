//@@author A0112659A
package test;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Test;

import simplyamazing.data.Task;

public class TaskTest {
    
	
	
	@Test
	public void testFloatingTaskCreation() {
		Task task = new Task("go swimming");
		Task taskCompare = new Task("go swimming");
		assertEquals("go swimming, , , ,incomplete", task.toString());
		assertEquals(1, task.getTaskType());
		assertEquals(0,task.compareTo(taskCompare));
	}
	
	@Test
	public void testDeadlineCreation() throws Exception {
		Task task = new Task();
		task = new Task("go swimming", "17:30 30 May 2016");
		assertEquals("go swimming, ,17:30 30 May 2016, ,incomplete", task.toString());
	}
	
	@Test 
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
		assertEquals("study for exam", task.getDescription());
	}
	
	@Test
	public void testSetStartTimeMethod() throws Exception {
		Task task = new Task("go home");
		task.setStartTime("15:30 28 Apr 2016");
		Task taskCompare = new Task("go to school");
		taskCompare.setStartTime("15:29 28 Apr 2016");
		assertEquals(1,task.compareTo(taskCompare));
		assertEquals("go home,15:30 28 Apr 2016, , ,incomplete", task.toString());
		assertEquals("15:30 28 Apr 2016",Task.convertDateToString(task.getStartTime(), Task.TIME_FORMAT));
		task.setStartTime("19:00 31 MAy 2016"); 
		Date startTime = task.getStartTime();
		task.setStartTime(startTime);
		assertEquals("19:00 31 May 2016",Task.convertDateToString(task.getStartTime(), Task.TIME_FORMAT));
	}
	
	@Test
	public void testSetEndTimeMethod() throws Exception {
		Task task = new Task("go swimming");
		task.setEndTime("17:30 30 may 2016");
		Task taskCompare = new Task("go shopping");
		taskCompare.setEndTime("17:31 30 MAY 2016");
		assertEquals(-1,task.compareTo(taskCompare));
		assertEquals("go swimming, ,17:30 30 May 2016, ,incomplete", task.toString());
		task.setEndTime("20:30 31 may 2016"); 
		Date endTime = task.getEndTime();
		task.setEndTime(endTime);
		assertEquals("20:30 31 May 2016",Task.convertDateToString(task.getEndTime(), Task.TIME_FORMAT));
        
		
		
	}
	
	@Test
	public void testSetPriorityMethod() throws Exception {
		Task task = new Task("study for exam");
		task.setPriority("hiGH");
		assertEquals("study for exam, , ,high,incomplete", task.toString());
		task.setPriority("meDIUM");
		assertEquals("study for exam, , ,medium,incomplete", task.toString());
		task.setPriority("LOW");
		assertEquals("study for exam, , ,low,incomplete", task.toString());
		task.setPriority(0);
		task.setPriority("NOne");
		assertEquals(0, task.getPriority());
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
