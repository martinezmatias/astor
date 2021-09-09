import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;

class D4JWorkflowTest {

	@Test
	void testMath70() throws Exception {

		run("Math70", "");
	}

	@Test
	void run(String bug_id, String mvn_option) throws Exception {

		String command = "mkdir -p tempdj4/" + bug_id + ";\n cd tempdj4/" + bug_id
				+ ";\n git init;\n git fetch https://github.com/Spirals-Team/defects4j-repair " + bug_id + ":" + bug_id
				+ ";\n git checkout " + bug_id + ";\n mvn -q test -DskipTests " + mvn_option
				+ ";\n mvn -q dependency:build-classpath -Dmdep.outputFile=cp.txt";
		System.out.println(command);
		// Process p = Runtime.getRuntime().exec(new String[] { "sh", "-c", command });
		Process p = Runtime.getRuntime().exec(new String[] { "bash", "-c", command });

		p.waitFor();
		String output = IOUtils.toString(p.getInputStream());
		String errorOutput = IOUtils.toString(p.getErrorStream());
		System.out.println(output);
		System.err.println(errorOutput);

		System.out.println("End case");

	}

	@Test
	void testJava() throws Exception {

		String command = "java -version;";
		System.out.println(command);
		Process p = Runtime.getRuntime().exec(new String[] { "sh", "-c", command });

		p.waitFor();
		String output = IOUtils.toString(p.getInputStream());
		String errorOutput = IOUtils.toString(p.getErrorStream());
		System.out.println(output);
		System.err.println(errorOutput);

		System.out.println("End case");
	}

}
