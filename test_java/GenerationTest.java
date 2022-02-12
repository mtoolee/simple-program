import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class GenerationTest {
	
	@Test
	void testBooleanConstructor() {
		Generation gen = new Generation(true);
		assertEquals(1, gen.size());
		assertTrue(gen.getState(0));
		assertArrayEquals(new boolean[] {true}, gen.getStates());
		assertEquals("1", gen.getStates('0', '1'));
		
		gen = new Generation(false, true, false);
		assertEquals(3, gen.size());
		assertFalse(gen.getState(0));
		assertTrue(gen.getState(1));
		assertFalse(gen.getState(2));
		assertArrayEquals(new boolean[] {false, true, false}, gen.getStates());
		assertEquals("FTF", gen.getStates('F', 'T'));
		
		gen = new Generation(true, false, true, true, false, false);
		assertEquals(6, gen.size());
		assertTrue(gen.getState(0));
		assertFalse(gen.getState(1));
		assertTrue(gen.getState(2));
		assertTrue(gen.getState(3));
		assertFalse(gen.getState(4));
		assertFalse(gen.getState(5));
		assertArrayEquals(
				new boolean[] {true, false, true, true, false, false}, 
				gen.getStates());
		assertEquals("O.OO..", gen.getStates('.', 'O'));
		
		// Check edge cases.
		gen = new Generation(null);
		assertEquals(1, gen.size());
		assertFalse(gen.getState(0));
		assertArrayEquals(new boolean[] {false}, gen.getStates());
		assertEquals("0", gen.getStates('0', '1'));

		gen = new Generation();
		assertEquals(1, gen.size());
		assertFalse(gen.getState(0));
		assertArrayEquals(new boolean[] {false}, gen.getStates());
		assertEquals("0", gen.getStates('0', '1'));
	}
	
	@Test
	void testStringConstructor() {
		Generation gen = new Generation("1", '1');
		assertEquals(1, gen.size());
		assertTrue(gen.getState(0));
		assertArrayEquals(new boolean[] {true}, gen.getStates());
		assertEquals("1", gen.getStates('0', '1'));
		
		gen = new Generation("012", '1');
		assertEquals(3, gen.size());
		assertFalse(gen.getState(0));
		assertTrue(gen.getState(1));
		assertFalse(gen.getState(2));
		assertArrayEquals(new boolean[] {false, true, false}, gen.getStates());
		assertEquals("FTF", gen.getStates('F', 'T'));
		
		gen = new Generation("AbAAcd", 'A');
		assertEquals(6, gen.size());
		assertTrue(gen.getState(0));
		assertFalse(gen.getState(1));
		assertTrue(gen.getState(2));
		assertTrue(gen.getState(3));
		assertFalse(gen.getState(4));
		assertFalse(gen.getState(5));
		assertArrayEquals(
				new boolean[] {true, false, true, true, false, false}, 
				gen.getStates());
		assertEquals("O.OO..", gen.getStates('.', 'O'));
		
		// Check edge cases.
		gen = new Generation(null, 'T');
		assertEquals(1, gen.size());
		assertFalse(gen.getState(0));
		assertArrayEquals(new boolean[] {false}, gen.getStates());
		assertEquals("0", gen.getStates('0', '1'));

		gen = new Generation("", '1');
		assertEquals(1, gen.size());
		assertFalse(gen.getState(0));
		assertArrayEquals(new boolean[] {false}, gen.getStates());
		assertEquals("F", gen.getStates('F', 'T'));
	}
	
	@Test
	void testImmutability() {
		boolean[] cellStates = {true};
		Generation gen = new Generation(cellStates);
		assertTrue(gen.getState(0));
		
		cellStates[0] = false;
		assertTrue(gen.getState(0));
		
		cellStates = gen.getStates();
		cellStates[0] = false;
		assertTrue(gen.getState(0));
	}
}
