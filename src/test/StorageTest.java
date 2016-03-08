package test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import simplyamazing.storage.Storage;

public class StorageTest {
	Storage storage = new Storage();
	
	@Test(expected = Exception.class) 
	public void testIsLocationSetMethod() throws Exception {
		assertEquals(false, storage.isLocationSet());
		assertEquals("Storage location of task data has not been set yet.", storage.getLocation());
	}
	
	@Test(expected = Exception.class) 
	public void testSetLocationMethod() throws Exception {
		assertEquals("Provided storage location is not a valid directory", storage.setLocation("C:\\Users\\Pan\\Documents\\SimplyAmazing"));
		assertEquals("Storage location of task data is set sucessfully.", storage.setLocation("C:\\Users\\Pan\\Documents\\"));
		assertEquals(true, storage.isLocationSet());
	}
	
	@Test(expected = Exception.class) 
	public void testGetLocationMethod() throws Exception {
		assertEquals("C:\\Users\\Pan\\Documents\\", storage.getLocation());
	}
}
