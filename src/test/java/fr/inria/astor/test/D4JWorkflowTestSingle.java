package fr.inria.astor.test;

import org.junit.Test;

public class D4JWorkflowTestSingle {

///Arrays.asList(new Object[][] { { "Math70" }, { "Math81" }, { "Math82" }, { "Math84" }, { "Math85" },
//	{ "Math95" }, { "Math71" }, { "Math73" }, { "Math78" }, { "Math80" }// ,
	// {
	// "Math50"
	// }

	public String bugidParametrized;

	@Test
	public void testMath70() throws Exception {
		D4JWorkflowTest.runComplete("Math70", "");
	}

	@Test
	public void testMath81() throws Exception {
		D4JWorkflowTest.runComplete("Math81", "");
	}

}
