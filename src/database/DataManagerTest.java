package database;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class DataManagerTest
{
	@Test
	void testGetUser()
	{
		assertTrue(DataManager.getUser("estebanQuito") != null);
	}
	
	@Test
	void testGetMessages()
	{
		assertTrue(DataManager.getMessages("estebanQuito").isEmpty() == false);
	}
}