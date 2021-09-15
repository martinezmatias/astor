package fr.inria.astor.test;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import fr.inria.astor.core.entities.ProgramVariant;
import fr.inria.main.CommandSummary;
import fr.inria.main.evolution.AstorMain;

@RunWith(Parameterized.class)
public class D4JWorkflowTest {

	@Parameters
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] { { "Math70" }, { "Math81" }, { "Math82" }, { "Math84" }, { "Math85" },
				{ "Math95" }, { "Math71" }, { "Math73" }, { "Math78" }, { "Math80" }// ,
				// {
				// "Math50"
				// }
		});
	}

	public String bugidParametrized;

	public D4JWorkflowTest(String buugid) {
		this.bugidParametrized = buugid;
	}

	@Test
	public void testParam() throws Exception {
		runComplete(bugidParametrized, "");
	}

	public static void runComplete(String bug_id, String mvn_option) throws Exception {
		System.out.println("\n****\nRunning repair attempt for " + bug_id);
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
			classBin = "/target/classes";// maven_app.getAbsolutePath();
		}
		File maven_test = new File(locationProject + "/target/test-classes");
		if (maven_test.exists()) {
			// cp.add(maven_test.toURL());
			testBin = "/target/test-classes";// maven_app.getAbsolutePath();
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
		cs.command.put("-id", bug_id);
		cs.command.put("-mode", "jGenProg");
		cs.command.put("-srcjavafolder", src);
		cs.command.put("-srctestfolder", srcTst);
		cs.command.put("-binjavafolder", classBin);
		cs.command.put("-bintestfolder", testBin);

		cs.command.put("-location", locationProject); // "/Users/matias/develop/code/astor/./examples/math_70");
		cs.command.put("-dependencies", depStrings);
		cs.command.put("-maxgen", "10000");
		cs.command.put("-stopfirst", "true");
		cs.command.put("-loglevel", "DEBUG");
		// cs.command.put("-package", "org.apache.commons");
		cs.command.put("-maxtime", "30");

		cs.command.put("-faultlocalization", "gzoltar");

		cs.command.put("-javacompliancelevel", "7");

		cs.command.put("-population", "1");
		cs.command.put("-flthreshold", "0.1");
		cs.command.put("-seed", "10");

		System.out.println("\nConfiguration " + cs.command.toString());

		AstorMain main1 = new AstorMain();
		main1.execute(cs.flat());
		List<ProgramVariant> variantsSolutions = main1.getEngine().getSolutions();

		System.out.println("Finishing execution for " + bug_id + ": # patches: " + variantsSolutions.size());

		//
		File dirResults = new File("./resultsTestCases");
		if (!dirResults.exists()) {
			dirResults.mkdirs();
		}

		FileWriter fw = new FileWriter(dirResults.getAbsolutePath() + File.separator + "results_" + bug_id + ".json");
		fw.write("{bugid=" + bug_id + ", solutions=" + variantsSolutions.size() + "}");
		fw.close();

		//
		File foutput = new File("./output_astor/AstorMain-" + bug_id + File.separator + "astor_output.json");

		// We rename the file and put in a result folder
		File foutputnew = new File(
				dirResults.getAbsolutePath() + File.separator + File.separator + "astor_output_" + bug_id + ".json");
		Files.copy(foutput.toPath(), foutputnew.toPath(), StandardCopyOption.REPLACE_EXISTING);

		assertTrue(variantsSolutions.size() > 0);

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

}
