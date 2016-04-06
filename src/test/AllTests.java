package test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ LogicTest.class, ParserTest.class, StorageTest.class, SystemTest.class, TaskTest.class })
public class AllTests {

}
