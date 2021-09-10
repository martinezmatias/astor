package fr.inria.astor.test;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.junit.Ignore;
import org.junit.Test;

import fr.inria.astor.core.entities.ProgramVariant;
import fr.inria.main.CommandSummary;
import fr.inria.main.evolution.AstorMain;

public class D4JWorkflowTest {

	@Test
	public void testMath70() throws Exception {

		runComplete("Math70", "");
	}

	public void runComplete(String bug_id, String mvn_option) throws Exception {

		// for Chart, we use ant
		if (bug_id.startsWith("Chart") && !new File(bug_id).exists()) {
			// here we use maven to compile
			String command = "mkdir " + bug_id + ";\n cd " + bug_id
					+ ";\n git init;\n git fetch https://github.com/Spirals-Team/defects4j-repair " + bug_id + ":"
					+ bug_id + ";\n git checkout " + bug_id + ";\n" + "sed -i -e '/delete dir/ d' ant/build.xml;\n"
					+ "ant -f ant/build.xml compile compile-tests;\n"
					+ "echo -n `pwd`/lib/iText-2.1.4.jar:`pwd`/lib/junit.jar:`pwd`/lib/servlet.jar > cp.txt;\n"

			;
			System.out.println(command);
			Process p = Runtime.getRuntime().exec(new String[] { "sh", "-c", command });
			p.waitFor();
			String output = IOUtils.toString(p.getInputStream());
			String errorOutput = IOUtils.toString(p.getErrorStream());
			System.out.println(output);
			System.err.println(errorOutput);

		} else if (!new File(bug_id).exists()) {

			System.out.println("\nChecking out project: " + bug_id);
			// for the rest we use Maven
			String command = // "export
								// PATH=/Library/Java/JavaVirtualMachines/jdk1.8.0_101.jdk/Contents/Home:$PATH;"
					"mkdir -p tempdj4/" + bug_id + ";\n cd tempdj4/" + bug_id
							+ ";\n git init;\n git fetch https://github.com/Spirals-Team/defects4j-repair " + bug_id
							+ ":" + bug_id + ";\n git checkout " + bug_id + ";\n mvn -q test -DskipTests " + mvn_option
							+ ";\n mvn -q dependency:build-classpath -Dmdep.outputFile=cp.txt";
			System.out.println(command);
			Process p = Runtime.getRuntime().exec(new String[] { "sh", "-c", command });
			p.waitFor();
			String output = IOUtils.toString(p.getInputStream());
			String errorOutput = IOUtils.toString(p.getErrorStream());
			System.out.println(output);
			System.err.println(errorOutput);
		}

		Properties prop = new Properties();
		String locationProject = "./tempdj4/" + bug_id;
		prop.load(new FileInputStream(locationProject + "/defects4j.build.properties"));

		String src = File.separator + prop.get("d4j.dir.src.classes").toString();
		String srcTst = File.separator + prop.get("d4j.dir.src.tests").toString();

		// String failing = bug_id + "/" + prop.get("d4j.tests.trigger");

		System.out.println("Source " + src);

		// getting the classpath from Maven
		List<String> cp = new ArrayList<>();
		for (String entry : FileUtils.readFileToString(new File(locationProject + "/cp.txt"))
				.split(new String(new char[] { File.pathSeparatorChar }))) {
			cp.add(new File(entry).getAbsolutePath());

		}
		String testBin = null, classBin = null;

		File maven_app = new File(locationProject + "/target/classes");
		if (maven_app.exists()) {
			// cp.add(maven_app.toURL());
			classBin = maven_app.getAbsolutePath();
		}
		File maven_test = new File(locationProject + "/target/test-classes");
		if (maven_test.exists()) {
			// cp.add(maven_test.toURL());
			testBin = maven_app.getAbsolutePath();
		}
		File ant_app = new File(locationProject + "/build");
		if (ant_app.exists()) {
			classBin = ant_app.getAbsolutePath();
		}
		File ant_test = new File(locationProject + "/build-tests");
		if (ant_test.exists()) {
			// cp.add(ant_test.toURL());
			testBin = ant_test.getAbsolutePath();
		}
		System.out.println(cp);
		//
		String depStrings = cp.stream().map(n -> n.toString()).collect(Collectors.joining(File.pathSeparator));

		System.out.println(depStrings);

		CommandSummary cs = new CommandSummary();
		cs.command.put("-mode", "jGenProg");
		cs.command.put("-srcjavafolder", src);
		cs.command.put("-srctestfolder", srcTst);
		cs.command.put("-binjavafolder", classBin);
		cs.command.put("-bintestfolder", testBin);
		cs.command.put("-location", locationProject);
		cs.command.put("-dependencies", depStrings);
		cs.command.put("-maxgen", "10000");
		cs.command.put("-stopfirst", "true");

		System.out.println("\nConfiguration " + cs.command.toString());

		AstorMain main1 = new AstorMain();
		main1.execute(cs.flat());
		List<ProgramVariant> variants = main1.getEngine().getVariants();
		assertTrue(variants.size() > 0);

	}

	@Test
	public void testPrint() throws Exception {

		System.out.println("Printing hello");
	}

	public void run(String bug_id, String mvn_option) throws Exception {

		String command = "mkdir -p tempdj4/" + bug_id + ";\n cd tempdj4/" + bug_id
				+ ";\n git init;\n git fetch https://github.com/Spirals-Team/defects4j-repair " + bug_id + ":" + bug_id
				+ ";\n git checkout " + bug_id + ";\n /usr/local/bin/mvn -q test -DskipTests " + mvn_option
				+ ";\n /usr/local/bin/mvn -q dependency:build-classpath -Dmdep.outputFile=cp.txt";
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
	@Ignore
	public void testJava() throws Exception {

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

	@Test
	@Ignore
	public void testMvn() throws Exception {

		System.out.println("Stating test maven:\n");
		String command = "mvn;";
		System.out.println(command);
		Process p = Runtime.getRuntime().exec(new String[] { "sh", "-c", command });

		p.waitFor();
		String output = IOUtils.toString(p.getInputStream());
		String errorOutput = IOUtils.toString(p.getErrorStream());
		System.out.println(output);
		System.err.println(errorOutput);

		System.out.println("\n End case maven");
	}

}
