import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Random;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ConsoleAppTest {

	private static final InputStream SYSTEM_INPUT_STREAM = System.in;
	private static final PrintStream SYSTEM_OUTPUT_STREAM = System.out;

	private static final String OPTIONS = 
			"0: Show the current Rule" 							+ System.lineSeparator() +
			"1: Reinitialize Automaton" 						+ System.lineSeparator() + 
			"2: Evolve the Automaton a given number of steps" 	+ System.lineSeparator() + 
			"3: Set the true symbol represention" 				+ System.lineSeparator() +
			"4: Set the false symbol represention" 				+ System.lineSeparator() +
			"5: Show the current Generation of the Automaton" 	+ System.lineSeparator() +
			"6: Show the full evolution of the Automaton" 		+ System.lineSeparator() +
			"7: Quit" + System.lineSeparator();

	private ByteArrayOutputStream testOutputStream;

	@BeforeEach
	public void redirectOutputStream() {
		// Capture the output of System.out in testOutputStream
		testOutputStream = new ByteArrayOutputStream();
		System.setOut(new PrintStream(testOutputStream));
	}

	@AfterEach
	public void restoreSystemStreams() {
		System.setIn(SYSTEM_INPUT_STREAM);
		System.setOut(SYSTEM_OUTPUT_STREAM);
	}

	private void sendInput(String input) {
		// Convert the input to a ByteArrayInputStream
		ByteArrayInputStream testInputStream = new ByteArrayInputStream(input.getBytes());

		// Reassign System.in to use the test input stream
		System.setIn(testInputStream);
	}

	private String getOutput() {
		return testOutputStream.toString();
	}

	@Test
	void testOptionPrintRuleNoChange() {
		// sendInput MUST BE DONE BEFORE APP IS CONSTRUCTED DUE TO REDIRECTION
		// Send input "0"
		sendInput("0");								// Show the current rule

		// Construct app instance
		ConsoleApp app = new ConsoleApp();

		// Run the app to completion
		app.run();

		// Check for proper output
		String expected = OPTIONS + 
				"Select option > " + 
				"Rule: 22";
		String output = getOutput();
		String actual = output.substring(0, expected.length());

		// Only compare up to the "Rule: 22" portion
		assertEquals(expected, actual);
	}

	@Test
	void testOptionPrintRuleWithChange() {
		// sendInput MUST BE DONE BEFORE APP IS CONSTRUCTED DUE TO REDIRECTION
		sendInput(
				"1" + System.lineSeparator() + 		// Reinitialize Automaton
				"O" + System.lineSeparator() + 		// Set true symbol
				"." + System.lineSeparator() + 		// Set false symbol
				"0" + System.lineSeparator() + 		// Set rule number
				"....." + System.lineSeparator() + 	// Set initial generation
				"0" + System.lineSeparator() + 		// Show current rule
				"7"); 								// Quit

		// Construct app instance
		ConsoleApp app = new ConsoleApp();

		// Run the app to completion
		app.run();

		// Check for proper output
		String expected = OPTIONS + 
				"Select option > " + 
				"Current true symbol: 1" + System.lineSeparator() + 
				"New true symbol > " + 
				"Current false symbol: 0" + System.lineSeparator() + 
				"New false symbol > " + 
				"Enter new rule number > " + 
				"Enter initial generation > " + 
				"Rule number updated to 0" + System.lineSeparator() + 
				"Generation 0:" + System.lineSeparator() + 
				"....." + System.lineSeparator() + 
				System.lineSeparator() + 
				OPTIONS + 
				"Select option > " + 
				"Rule: 0";
		String output = getOutput();
		String actual = output.substring(0, expected.length());

		assertEquals(expected, actual);
	}

	@Test
	void testOptionPrintRuleWithRandomChangeInBounds() {
		// Generate a random rule number for testing
		Random rand = new Random();
		int newRule = rand.nextInt(256);

		// sendInput MUST BE DONE BEFORE APP IS CONSTRUCTED DUE TO REDIRECTION
		sendInput(
				"1" + System.lineSeparator() + 		// Reinitialize Automaton
				"O" + System.lineSeparator() + 		// Set true symbol
				"." + System.lineSeparator() + 		// Set false symbol
				newRule + System.lineSeparator() + 	// Set random rule number
				"....." + System.lineSeparator() + 	// Set initial generation
				"0" + System.lineSeparator() + 		// Show current rule
				"7"); 								// Quit

		// Construct app instance
		ConsoleApp app = new ConsoleApp();

		// Run the app to completion
		app.run();

		// Check for proper output
		String expected = OPTIONS +
				"Select option > " + 
				"Current true symbol: 1" + System.lineSeparator() + 
				"New true symbol > " + 
				"Current false symbol: 0" + System.lineSeparator() + 
				"New false symbol > " + 
				"Enter new rule number > " + 
				"Enter initial generation > " + 
				"Rule number updated to " + newRule + System.lineSeparator() + 
				"Generation 0:" + System.lineSeparator() + 
				"....." + System.lineSeparator() + 
				System.lineSeparator() + 
				OPTIONS + 
				"Select option > " + 
				"Rule: " + newRule;
		String output = getOutput();
		String actual = output.substring(0, expected.length());

		// Only compare up to the "Rule: X" portion
		assertEquals(expected, actual);
	}

	@Test
	void testOptionPrintRuleWithRandomChangeAbove255() {
		// Generate a random rule number for testing
		Random rand = new Random();
		int newRule = 256 + rand.nextInt(256);

		// sendInput MUST BE DONE BEFORE APP IS CONSTRUCTED DUE TO REDIRECTION
		sendInput(
				"1" + System.lineSeparator() + 		// Reinitialize Automaton
				"O" + System.lineSeparator() + 		// Set true symbol
				"." + System.lineSeparator() + 		// Set false symbol
				newRule + System.lineSeparator() + 	// Set random rule number
				"....." + System.lineSeparator() + 	// Set initial generation
				"0" + System.lineSeparator() + 		// Show current rule
				"7"); 								// Quit

		// Construct app instance
		ConsoleApp app = new ConsoleApp();

		// Run the app to completion
		app.run();

		// Check for proper output
		String expected = OPTIONS + 
				"Select option > " + 
				"Current true symbol: 1" + System.lineSeparator() + 
				"New true symbol > " + 
				"Current false symbol: 0" + System.lineSeparator() + 
				"New false symbol > " + 
				"Enter new rule number > " + 
				"Enter initial generation > " + 
				"Rule number updated to 255" + System.lineSeparator() + 
				"Generation 0:" + System.lineSeparator() + 
				"....." + System.lineSeparator() + 
				System.lineSeparator() + 
				OPTIONS + 
				"Select option > " + 
				"Rule: 255";
		String output = getOutput();
		String actual = output.substring(0, expected.length());

		// Only compare up to the "Rule: X" portion
		assertEquals(expected, actual);
	}

	@Test
	void testOptionPrintRuleWithRandomChangeBelow0() {
		// Generate a random rule number for testing
		Random rand = new Random();
		int newRule = -256 + rand.nextInt(256);

		// sendInput MUST BE DONE BEFORE APP IS CONSTRUCTED DUE TO REDIRECTION
		sendInput(
				"1" + System.lineSeparator() + 		// Reinitialize Automaton
				"O" + System.lineSeparator() + 		// Set true symbol
				"." + System.lineSeparator() + 		// Set false symbol
				newRule + System.lineSeparator() + 	// Set random rule number
				"....." + System.lineSeparator() + 	// Set initial generation
				"0" + System.lineSeparator() + 		// Show current rule
				"7");								// Quit

		// Construct app instance
		ConsoleApp app = new ConsoleApp();

		// Run the app to completion
		app.run();

		// Check for proper output
		String expected = OPTIONS + 
				"Select option > " + 
				"Current true symbol: 1" + System.lineSeparator() + 
				"New true symbol > " + 
				"Current false symbol: 0" + System.lineSeparator() + 
				"New false symbol > " +
				"Enter new rule number > " + 
				"Enter initial generation > " + 
				"Rule number updated to 0" + System.lineSeparator() + 
				"Generation 0:" + System.lineSeparator() + 
				"....." + System.lineSeparator() +
				System.lineSeparator() + 
				OPTIONS + 
				"Select option > " + 
				"Rule: 0";
		String output = getOutput();
		String actual = output.substring(0, expected.length());

		// Only compare up to the "Rule: X" portion
		assertEquals(expected, actual);
	}

	@Test
	void testEvolve100() {
		// sendInput MUST BE DONE BEFORE APP IS CONSTRUCTED DUE TO REDIRECTION
		sendInput(
				"2" + System.lineSeparator() + 		// Evolve Automaton
				"100" + System.lineSeparator() + 	// by 100 generations
				"7");								// Quit

		// Construct app instance
		ConsoleApp app = new ConsoleApp();

		// Run the app to completion
		app.run();

		// Check for proper output
		String expected = OPTIONS + 
				"Select option > " + 
				"Enter number of evolutions > " + 
				"Evolved 100 generation(s)" + System.lineSeparator() + 
				System.lineSeparator();
		String output = getOutput();
		String actual = output.substring(0, expected.length());

		assertEquals(expected, actual);
	}

	@Test
	void testEvolveNegative() {
		// sendInput MUST BE DONE BEFORE APP IS CONSTRUCTED DUE TO REDIRECTION
		sendInput(
				"2" + System.lineSeparator() + 		// Evolve Automaton
				"-1" + System.lineSeparator() + 	// by -1 generations
				"7");								// Quit

		// Construct app instance
		ConsoleApp app = new ConsoleApp();

		// Run the app to completion
		app.run();

		// Check for proper output (expect 0 generation(s) since a number less
		// than or equal to zero was specified)
		String expected = OPTIONS + 
				"Select option > " + 
				"Enter number of evolutions > " + 
				"Evolved 0 generation(s)" + System.lineSeparator() + 
				System.lineSeparator();
		String output = getOutput();
		String actual = output.substring(0, expected.length());

		assertEquals(expected, actual);
	}

	@Test
	void testSetTrueSymbol() {
		// sendInput MUST BE DONE BEFORE APP IS CONSTRUCTED DUE TO REDIRECTION
		sendInput(
				"3" + System.lineSeparator() +	// Set the true symbol representation
				"O" + System.lineSeparator() +	// Use the 'O' character
				"3" + System.lineSeparator() +	// Set the true symbol representation
												// (only checking output)
				"X" + System.lineSeparator() +	// Use an arbitrary character
												// since we only care about
												// checking the current symbol
				"7");							// Quit

		// Construct app instance
		ConsoleApp app = new ConsoleApp();

		// Run the app to completion
		app.run();

		// Check for proper output
		String expected = OPTIONS + 
				"Select option > " + 
				"Current true symbol: 1" + System.lineSeparator() + 
				"New true symbol > " + System.lineSeparator() + 
				OPTIONS + 
				"Select option > " +
				"Current true symbol: O";
		String output = getOutput();
		String actual = output.substring(0, expected.length());

		assertEquals(expected, actual);
	}

	@Test
	void testSetFalseSymbol() {
		// sendInput MUST BE DONE BEFORE APP IS CONSTRUCTED DUE TO REDIRECTION
		// 4: Set the false symbol representation
		// O: (use the "O" character)
		// 4: Set the false symbol representation (to see if it changed)
		// X: (arbitrary character)
		// 7: Quit
		// NOTE: Using System.lineSeparator() or spaces to separate inputs does
		// not change the ConsoleApp's output. This is since System.in and
		// System.out represent different I/O streams.
		sendInput("4 . 4 X 7");

		// Construct app instance
		ConsoleApp app = new ConsoleApp();

		// Run the app to completion
		app.run();

		// Check for proper output
		String expected = OPTIONS + 
				"Select option > " + 
				"Current false symbol: 0" + System.lineSeparator() + 
				"New false symbol > " + System.lineSeparator() + 
				OPTIONS + 
				"Select option > " + 
				"Current false symbol: .";
		String output = getOutput();
		String actual = output.substring(0, expected.length());

		assertEquals(expected, actual);
	}

	@Test
	void testPrintCurrentGenerationNoEvolve() {
		// sendInput MUST BE DONE BEFORE APP IS CONSTRUCTED DUE TO REDIRECTION
		// 5: Show the current Generation of the Automaton
		// 7: Quit
		// NOTE: Using System.lineSeparator() or spaces to separate inputs does
		// not change the ConsoleApp's output. This is since System.in and
		// System.out represent different I/O streams.
		sendInput("5 7");

		// Construct app instance
		ConsoleApp app = new ConsoleApp();

		// Run the app to completion
		app.run();

		// Check for proper output
		String expected = OPTIONS + 
				"Select option > " + 
				"Generation 0:" + System.lineSeparator() + 
				"0001000";
		String output = getOutput();
		String actual = output.substring(0, expected.length());

		assertEquals(expected, actual);
	}

	@Test
	void testPrintCurrentGenerationAfterEvolve() {
		// sendInput MUST BE DONE BEFORE APP IS CONSTRUCTED DUE TO REDIRECTION
		// 2: Evolve the Automaton a given number of steps
		// 3: (evolve 3 generations)
		// 5: Show the current Generation of the Automaton
		// 7: Quit
		// NOTE: Using System.lineSeparator() or spaces to separate inputs does
		// not change the ConsoleApp's output. This is since System.in and
		// System.out represent different I/O streams.
		sendInput("2 3 5 7");

		// Construct app instance
		ConsoleApp app = new ConsoleApp();

		// Run the app to completion
		app.run();

		// Check for proper output
		String expected = OPTIONS + 
				"Select option > " + 
				"Enter number of evolutions > " + 
				"Evolved 3 generation(s)" + System.lineSeparator() + 
				System.lineSeparator() + 
				OPTIONS + 
				"Select option > " + 
				"Generation 3:" + System.lineSeparator() + 
				"1110111";
		String output = getOutput();
		String actual = output.substring(0, expected.length());

		assertEquals(expected, actual);
	}

	@Test
	void testPrintFullEvolutionAfterEvolve() {
		// sendInput MUST BE DONE BEFORE APP IS CONSTRUCTED DUE TO REDIRECTION
		// 2: Evolve the Automaton a given number of steps
		// 3: (evolve 3 generations)
		// 6: Show the full evolution of the Automaton
		// 7: Quit
		// NOTE: Using System.lineSeparator() or spaces to separate inputs does
		// not change the ConsoleApp's output. This is since System.in and
		// System.out represent different I/O streams.
		sendInput("2 3 6 7");

		// Construct app instance
		ConsoleApp app = new ConsoleApp();

		// Run the app to completion
		app.run();

		// Check for proper output
		String expected = OPTIONS + 
				"Select option > " + 
				"Enter number of evolutions > " + 
				"Evolved 3 generation(s)" + System.lineSeparator() + 
				System.lineSeparator() + 
				OPTIONS + 
				"Select option > " + 
				"0001000" + System.lineSeparator() + 
				"0011100" + System.lineSeparator() + 
				"0100010" + System.lineSeparator() + 
				"1110111";
		String output = getOutput();
		String actual = output.substring(0, expected.length());

		assertEquals(expected, actual);
	}

	@Test
	void testQuit() {
		// sendInput MUST BE DONE BEFORE APP IS CONSTRUCTED DUE TO REDIRECTION
		// 7: Quit
		sendInput("7");

		// Construct app instance
		ConsoleApp app = new ConsoleApp();

		// Run the app to completion
		app.run();

		// Check for proper output
		String expected = OPTIONS + 
				"Select option > " + 
				"Bye bye!" + System.lineSeparator() + 
				System.lineSeparator();
		String output = getOutput();
		String actual = output;

		assertEquals(expected, actual);
	}

}
