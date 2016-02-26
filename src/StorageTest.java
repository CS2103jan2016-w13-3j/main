import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class StorageTest {
	Storage storage = new Storage();
	
	@Test
	public void testIsLocationSetMethod() {
		assertEquals(false, storage.isLocationSet());
		assertEquals("Storage location of task data has not been set yet.", storage.getLocation());
	}
	
	@Test
	public void testSetLocationMethod() {
		assertEquals("Provided storage location is not a valid directory", storage.setLocation("C:\\Users\\Pan\\Documents\\SimplyAmazing"));
		assertEquals("Storage location of task data is set sucessfully.", storage.setLocation("C:\\Users\\Pan\\Documents\\"));
		assertEquals(true, storage.isLocationSet());
	}
	
	@Test
	public void testGetLocationMethod() {
		assertEquals("C:\\Users\\Pan\\Documents\\", storage.getLocation());
	}
}
