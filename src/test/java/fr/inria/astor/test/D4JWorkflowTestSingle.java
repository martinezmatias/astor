package fr.inria.astor.test;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

import fr.inria.astor.core.entities.ProgramVariant;
import fr.inria.main.CommandSummary;
import fr.inria.main.evolution.AstorMain;

/**
 * Execution of results from
 * https://github.com/Spirals-Team/defects4j-repair/tree/master/results/2015-august
 * 
 * @author Matias Martinez
 *
 */
public class D4JWorkflowTestSingle {

	private static final int TIMEOUTMIN = 60;

	public String bugidParametrized;

	@Test
	public void testMath2() throws Exception {

		CommandSummary cs = new CommandSummary();
		cs.append("-parameters", "maxmemory" + File.pathSeparator + "-Xmx4G");
		runComplete("Math2", "-Dmaven.compiler.source=7 -Dmaven.compiler.target=7", "jGenProg", 90, cs);
	}

	@Test
	public void testMath5() throws Exception {
		CommandSummary cs = new CommandSummary();
		cs.append("-parameters", "maxmemory" + File.pathSeparator + "-Xmx4G");
		runComplete("Math5", "", "jGenProg", 90, cs);
	}

	@Test
	public void testMath8() throws Exception {
		CommandSummary cs = new CommandSummary();
		cs.append("-parameters", "maxmemory" + File.pathSeparator + "-Xmx4G");
		runComplete("Math8", "-Dmaven.compiler.source=7 -Dmaven.compiler.target=7", "jGenProg", 90, cs);
	}

	@Test
	public void testMath28() throws Exception {
		CommandSummary cs = new CommandSummary();
		cs.append("-parameters", "maxmemory" + File.pathSeparator + "-Xmx4G");
		runComplete("Math28", "-Dmaven.compiler.source=7 -Dmaven.compiler.target=7", "jGenProg", 90, cs);

	}

	@Test
	public void testMath40() throws Exception {
		CommandSummary cs = new CommandSummary();
		cs.command.putIfAbsent("-flthreshold", "0.2");
		cs.command.put("-parameters", "logtestexecution:true");
		cs.command.put("-ignoredtestcases",
				"org.apache.commons.math.util.FastMathTest" + File.pathSeparator
						+ "org.apache.commons.math.ode.nonstiff.ClassicalRungeKuttaIntegratorTest" + File.pathSeparator
						+ "org.apache.commons.math.ode.nonstiff.GillIntegratorTest" + File.pathSeparator
						+ "org.apache.commons.math.ode.nonstiff.ThreeEighthesIntegratorTest" + File.pathSeparator
						+ "org.apache.commons.math.ode.nonstiff.HighamHall54IntegratorTest");
		runComplete("Math40", "-Dmaven.compiler.source=7 -Dmaven.compiler.target=7", "jGenProg", 90, cs);
	}

	@Test
	public void testMath49() throws Exception {
		runCompleteJGenProg("Math49", "", 90);
	}

	@Test
	public void testMath50() throws Exception {
		runCompleteJGenProg("Math50", "", 90);
	}

	@Test
	public void testMath53() throws Exception {
		runCompleteJGenProg("Math53", "", 90);
	}

	@Test
	public void testMath70() throws Exception {
		runCompleteJGenProg("Math70", "");
	}

	@Test
	public void testMath71() throws Exception {

		CommandSummary cs = new CommandSummary();
		cs.command.putIfAbsent("-flthreshold", "0");

		runComplete("Math71", "", "jGenProg", 90, cs);
	}

	@Test
	public void testMath73() throws Exception {
		runCompleteJGenProg("Math73", "");
	}

	@Test
	public void testMath78() throws Exception {

		CommandSummary cs = new CommandSummary();
		cs.command.put("-ignoredtestcases", "org.apache.commons.math.util.FastMathTest" + File.pathSeparator
				+ "org.apache.commons.math.random.RandomAdaptorTest");
		runComplete("Math78", "", "jGenProg", 90, cs);

	}

	@Test
	public void testMath80() throws Exception {
		runCompleteJGenProg("Math80", "");
	}

	@Test
	public void testMath81() throws Exception {
		runCompleteJGenProg("Math81", "");
	}

	@Test
	public void testMath82() throws Exception {
		runCompleteJGenProg("Math82", "");
	}

	@Test
	public void testMath84() throws Exception {

		CommandSummary cs = new CommandSummary();
		cs.append("-parameters", "maxmemory" + File.pathSeparator + "-Xmx4G");

		runComplete("Math84", "", "jGenProg", 90, cs);
	}

	@Test
	public void testMath85() throws Exception {
		runCompleteJGenProg("Math85", "");
	}

	@Test
	public void testMath95() throws Exception {
		runCompleteJGenProg("Math95", "");
	}

	@Test
	public void testChart1() throws Exception {
		runCompleteJGenProg("Chart1", "");
	}

	@Test
	public void testChart3() throws Exception {
		runCompleteJGenProg("Chart3", "");
	}

	@Test
	public void testChart5() throws Exception {
		runCompleteJGenProg("Chart5", "");
	}

	@Test
	public void testChart7() throws Exception {
		runCompleteJGenProg("Chart7", "");
	}

	@Test
	public void testChart13() throws Exception {
		runCompleteJGenProg("Chart13", "");
	}

	@Test
	public void testChart15() throws Exception {
		runCompleteJGenProg("Chart15", "");
	}

	@Test
	public void testChart25() throws Exception {
		runCompleteJGenProg("Chart25", "");
	}

	@Test
	public void testTime4() throws Exception {
		runCompleteJGenProg("Time4", "");
	}

	@Test
	public void testTime11() throws Exception {
		runCompleteJGenProg("Time11", "");
	}

	//
	@Test
	public void testMath2JKali() throws Exception {
		CommandSummary cs = new CommandSummary();
		cs.command.putIfAbsent("-flthreshold", "0.0");
		cs.command.putIfAbsent("-javacompliancelevel", "5");
		cs.append("-parameters", "maxmemory" + File.pathSeparator + "-Xmx4G");

		runComplete("Math2", "", "jKali", TIMEOUTMIN, cs);
	}

	@Test
	public void testMath8JKali() throws Exception {
		CommandSummary cs = new CommandSummary();
		cs.command.putIfAbsent("-flthreshold", "0");
		runComplete("Math8", "", "jKali", TIMEOUTMIN, cs);
	}

	@Test
	public void testMath28JKali() throws Exception {
		CommandSummary cs = new CommandSummary();
		cs.command.putIfAbsent("-flthreshold", "0");
		cs.append("-parameters", "maxmemory" + File.pathSeparator + "-Xmx4G");
		runComplete("Math28", "", "jKali", TIMEOUTMIN, cs);
	}

	@Test
	public void testMath32JKali() throws Exception {
		CommandSummary cs = new CommandSummary();
		cs.command.putIfAbsent("-flthreshold", "0");
		runComplete("Math32", "", "jKali", TIMEOUTMIN, cs);
	}

	@Test
	public void testMath40JKali() throws Exception {
		CommandSummary cs = new CommandSummary();
		cs.command.putIfAbsent("-flthreshold", "0");
		cs.command.put("-parameters", "logtestexecution:true");
		cs.command.put("-ignoredtestcases",
				"org.apache.commons.math.util.FastMathTest" + File.pathSeparator
						+ "org.apache.commons.math.ode.nonstiff.ClassicalRungeKuttaIntegratorTest" + File.pathSeparator
						+ "org.apache.commons.math.ode.nonstiff.GillIntegratorTest" + File.pathSeparator
						+ "org.apache.commons.math.ode.nonstiff.ThreeEighthesIntegratorTest" + File.pathSeparator
						+ "org.apache.commons.math.ode.nonstiff.HighamHall54IntegratorTest");
		runComplete("Math40", "", "jKali", TIMEOUTMIN, cs);
	}

	@Test
	public void testMath49JKali() throws Exception {
		runComplete("Math49", "", "jKali", TIMEOUTMIN);
	}

	@Test
	public void testMath50JKali() throws Exception {
		runComplete("Math50", "", "jKali", TIMEOUTMIN);
	}

	@Test
	public void testMath78JKali() throws Exception {
		CommandSummary cs = new CommandSummary();
		cs.command.put("-ignoredtestcases", "org.apache.commons.math.util.FastMathTest" + File.pathSeparator
				+ "org.apache.commons.math.random.RandomAdaptorTest");
		runComplete("Math78", "", "jKali", 90, cs);
	}

	@Test
	public void testMath80JKali() throws Exception {
		runComplete("Math80", "", "jKali", TIMEOUTMIN);
	}

	@Test
	public void testMath81JKali() throws Exception {
		runComplete("Math81", "", "jKali", TIMEOUTMIN);
	}

	@Test
	public void testMath82JKali() throws Exception {
		runComplete("Math82", "", "jKali", TIMEOUTMIN);
	}

	@Test
	public void testMath84JKali() throws Exception {
		runComplete("Math84", "", "jKali", TIMEOUTMIN);
	}

	@Test
	public void testMath85JKali() throws Exception {
		runComplete("Math85", "", "jKali", TIMEOUTMIN);
	}

	@Test
	public void testMath95JKali() throws Exception {
		CommandSummary cs = new CommandSummary();
		cs.command.putIfAbsent("-flthreshold", "0");
		runComplete("Math95", "", "jKali", TIMEOUTMIN, cs);
	}

	@Test
	public void testTime11JKali() throws Exception {
		CommandSummary cs = new CommandSummary();
		cs.command.putIfAbsent("-flthreshold", "0");
		runComplete("Time11", "", "jKali", TIMEOUTMIN, cs);
	}

	@Test
	public void testChart1JKali() throws Exception {
		runComplete("Chart1", "", "jKali", TIMEOUTMIN);
	}

	@Test
	public void testChart5JKali() throws Exception {
		runComplete("Chart5", "", "jKali", TIMEOUTMIN);
	}

	@Test
	public void testChart13JKali() throws Exception {
		runComplete("Chart13", "", "jKali", TIMEOUTMIN);
	}

	@Test
	public void testChart15JKali() throws Exception {
		runComplete("Chart15", "", "jKali", TIMEOUTMIN);
	}

	@Test
	public void testChart25JKali() throws Exception {
		runComplete("Chart25", "", "jKali", TIMEOUTMIN);
	}

	@Test
	public void testChart26JKali() throws Exception {
		runComplete("Chart26", "", "jKali", TIMEOUTMIN);
	}

	public static void runCompleteJGenProg(String bug_id, String mvn_option) throws Exception {

		runComplete(bug_id, mvn_option, "jGenProg", TIMEOUTMIN);
	}

	public static void runCompleteJGenProg(String bug_id, String mvn_option, int timeout) throws Exception {

		runComplete(bug_id, mvn_option, "jGenProg", timeout);
	}

	public static void runComplete(String bug_id, String mvn_option, String approach, int timeout) throws Exception {
		CommandSummary cs = new CommandSummary();
		runComplete(bug_id, mvn_option, approach, timeout, cs);
	}

	public static void runComplete(String bug_id, String mvn_option, String approach, int timeout, CommandSummary cs)
			throws Exception {
		System.out.println("\n****\nRunning repair attempt for " + bug_id);

		System.out.println("Env var " + System.getenv("J7PATH"));
		File dirResults = new File("./resultsTestCases");
		if (!dirResults.exists()) {
			dirResults.mkdirs();
		}
		System.out.println("Storing results at " + dirResults.getAbsolutePath());

		configureBuggyProject(bug_id, mvn_option);

		String[] faultLocalization = new String[] { "gzoltar", "flacoco" };//

		boolean hasSolution = true;

		Map<String, Long> timePerFL = new HashMap<>();
		Map<String, Boolean> repairedPerFL = new HashMap<>();

		for (String aFL : faultLocalization) {

			System.out.println("Running on FL : " + aFL);

			createCommand(bug_id, approach, timeout,
					("flacoco".equals(aFL) ? "fr.inria.astor.core.faultlocalization.flacoco.FlacocoFaultLocalization"
							: aFL),
					cs);
			cs.command.put("-jvm4testexecution", System.getenv("J7PATH"));
			AstorMain main1 = new AstorMain();

			long init = System.currentTimeMillis();
			try {
				// org.apache.log4j.LogManager.getRootLogger().setLevel(Level.DEBUG);
				main1.execute(cs.flat());
			} catch (Exception e) {
				e.printStackTrace();
			}

			long end = System.currentTimeMillis();

			List<ProgramVariant> variantsSolutions = main1.getEngine().getSolutions();

			System.out.println("Finishing execution for " + bug_id + ": # patches: " + variantsSolutions.size());

			hasSolution = hasSolution && variantsSolutions.size() > 0;

			// Results

			// Copy the result
			File foutput = new File("./output_astor/AstorMain-" + bug_id + File.separator + "astor_output.json");
			// We rename the file and put in a result folder
			File foutputnew = new File(dirResults.getAbsolutePath() + File.separator + File.separator + "astor_output_"
					+ bug_id + "-" + approach + "_" + aFL + ".json");

			Files.copy(foutput.toPath(), foutputnew.toPath(), StandardCopyOption.REPLACE_EXISTING);

			//
			timePerFL.put(aFL, (end - init) / 1000);
			repairedPerFL.put(aFL, variantsSolutions.size() > 0);

			System.out.println("Saving execution of " + aFL + "results at " + foutputnew);

		}
		// Save results

		String fileNameResults = dirResults.getAbsolutePath() + File.separator + "results_" + bug_id + "-" + approach
				+ ".json";
		System.out.println("Saving results at " + fileNameResults);
		FileWriter fw = new FileWriter(fileNameResults);
		fw.write("{\"bugid\" :  \" " + bug_id + "\",  \"approach\": \"  " + approach + " \" , \"flacoco_sol\": \" "
				+ repairedPerFL.get("flacoco") + " \" , \"flacoco_time\" : " + timePerFL.get("flacoco")
				+ ", \"gzoltar_sol\": \" " + repairedPerFL.get("gzoltar") + " \" , \"gzoltar_time\": "
				+ timePerFL.get("gzoltar")

				+ "}");
		fw.close();

		//

		assertTrue(hasSolution);

	}

	public static void configureBuggyProject(String bug_id, String mvn_option)
			throws IOException, InterruptedException {

		// for Chart, we use ant

		if (bug_id.startsWith("Chart") && !new File(bug_id).exists()) {
			// here we use maven to compile
			String command = "mkdir -p tempdj4/" + bug_id + ";\n cd tempdj4/" + bug_id
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
			String command = "mkdir -p tempdj4/" + bug_id + ";\n cd tempdj4/" + bug_id
					+ ";\n git init;\n git fetch https://github.com/Spirals-Team/defects4j-repair " + bug_id + ":"
					+ bug_id + ";\n git checkout " + bug_id + ";\n mvn -q test -DskipTests " + mvn_option
					+ ";\n mvn -q dependency:build-classpath -Dmdep.outputFile=cp.txt";
			System.out.println(command);
			Process p = Runtime.getRuntime().exec(new String[] { "sh", "-c", command });
			p.waitFor();
			String output = IOUtils.toString(p.getInputStream());
			String errorOutput = IOUtils.toString(p.getErrorStream());
			System.out.println(output);
			System.err.println(errorOutput);
		}
	}

	public static CommandSummary createCommand(String bug_id, String approach, int timeout, String faultLocalization,
			CommandSummary cs) throws IOException, FileNotFoundException {
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
			classBin = "/build";// ant_app.getAbsolutePath();
		}
		File ant_test = new File(locationProject + "/build-tests");
		if (ant_test.exists()) {
			// cp.add(ant_test.toURL());
			testBin = "/build-tests";// ant_test.getAbsolutePath();
		}

		System.out.println(cp);
		//
		String depStrings = cp.stream().map(n -> n.toString()).collect(Collectors.joining(File.pathSeparator));

		System.out.println(depStrings);

		cs.command.put("-id", bug_id);
		cs.command.put("-mode", approach);
		cs.command.put("-srcjavafolder", src);
		cs.command.put("-srctestfolder", srcTst);
		cs.command.put("-binjavafolder", classBin);
		cs.command.put("-bintestfolder", testBin);

		cs.command.put("-location", locationProject);
		cs.command.put("-dependencies", depStrings);
		cs.command.putIfAbsent("-maxgen", "10000");
		cs.command.putIfAbsent("-stopfirst", "true");
		cs.command.putIfAbsent("-loglevel", "DEBUG");
		cs.command.putIfAbsent("-maxtime", new Integer(timeout).toString());

		cs.command.put("-faultlocalization", faultLocalization);

		cs.command.putIfAbsent("-javacompliancelevel", "7");

		cs.command.put("-population", "1");
		cs.command.putIfAbsent("-flthreshold", "0.1");
		cs.command.putIfAbsent("-seed", "10");
		cs.command.putIfAbsent("-tmax1", "30000");

		if (cs.command.containsValue("-ignoredtestcases")) {

			// Already passed some test to ignore
			String tctoignore = cs.command.get("-ignoredtestcases");
			cs.command.putIfAbsent("-ignoredtestcases",
					"org.apache.commons.math.util.FastMathTest" + File.pathSeparator + tctoignore);
			//
		} else {

			cs.command.putIfAbsent("-ignoredtestcases", "org.apache.commons.math.util.FastMathTest");
		}
		System.out.println("\nConfiguration " + cs.command.toString());

		cs.append("-parameters", "logtestexecution:true");

		return cs;
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