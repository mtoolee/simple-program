import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class AutomatonTest {

	@Test
	void testConstructor() {
		Automaton eca = new Automaton(22, new Generation(false, true, false));
		assertEquals(22, eca.getRuleNum());
		assertArrayEquals(new boolean[] { false, true, false }, 
				eca.getGeneration(0).getStates());
		assertEquals(0, eca.getTotalSteps());
		assertEquals("010", eca.toString());
	}

	@Test
	void testEvolve() {
		Automaton eca = new Automaton(22, 
				new Generation("000000010000000", '1'));
		assertEquals(0, eca.evolve(-1));

		assertEquals(1, eca.evolve(1));
		assertEquals(1, eca.getTotalSteps());
		String evolution = 
				"000000010000000" + System.lineSeparator() + 
				"000000111000000";
		assertEquals(evolution, eca.toString());

		assertEquals(2, eca.evolve(2));
		assertEquals(3, eca.getTotalSteps());
		eca.falseSymbol = '_';
		eca.trueSymbol = '%';
		evolution = 
				"_______%_______" + System.lineSeparator() + 
				"______%%%______" + System.lineSeparator() + 
				"_____%___%_____" + System.lineSeparator() + 
				"____%%%_%%%____";
		assertEquals(evolution, eca.toString());

		Generation gen = eca.getGeneration(7);
		assertEquals("OOO.OOO.OOO.OOO", gen.getStates('.', 'O'));
		assertEquals(7, eca.getTotalSteps());
		eca.falseSymbol = '.';
		eca.trueSymbol = 'O';
		evolution = 
				".......O......." + System.lineSeparator() + 
				"......OOO......" + System.lineSeparator() + 
				".....O...O....." + System.lineSeparator() + 
				"....OOO.OOO...." + System.lineSeparator() + 
				"...O.......O..." + System.lineSeparator() + 
				"..OOO.....OOO.." + System.lineSeparator() + 
				".O...O...O...O." + System.lineSeparator() + 
				"OOO.OOO.OOO.OOO";
		assertEquals(evolution, eca.toString());
	}

	@Test
	void testGetCurrentGenerationZero() {
		Automaton eca = new Automaton(22, 
				new Generation("000000010000000", '1'));
		assertEquals("000000010000000", 
				eca.getCurrentGeneration().getStates(eca.falseSymbol, eca.trueSymbol));
	}

	@Test
	void testGetCurrentGenerationFive() {
		Automaton eca = new Automaton(22, 
				new Generation("000000010000000", '1'));
		eca.evolve(5);
		assertEquals("001110000011100", 
				eca.getCurrentGeneration().getStates(eca.falseSymbol, eca.trueSymbol));
	}
}
