import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class RuleTest {

	@Test
	void testConstructor() {
		Rule rule = new Rule(22);
		assertEquals(22, rule.getRuleNum());
		
		rule = new Rule(30);
		assertEquals(30, rule.getRuleNum());
		
		// Check edge cases.
		rule = new Rule(-42);
		assertEquals(0, rule.getRuleNum());
		
		rule = new Rule(2334);
		assertEquals(255, rule.getRuleNum());
	}
	
	@Test
	void testGetNeighborhood() {
		Generation gen = new Generation(false, true, true, false);
		
		boolean[] neighborhood = Rule.getNeighborhood(1, gen);
		assertArrayEquals(new boolean[] {false, true, true}, neighborhood);
		
		neighborhood = Rule.getNeighborhood(2, gen);
		assertArrayEquals(new boolean[] {true, true, false}, neighborhood);
		
		// Check boundary conditions.
		neighborhood = Rule.getNeighborhood(0, gen);
		assertArrayEquals(new boolean[] {false, false, true}, neighborhood);
		
		neighborhood = Rule.getNeighborhood(3, gen);
		assertArrayEquals(new boolean[] {true, false, false}, neighborhood);
	}
	
	@Test
	void testEvolveNeighborhood() {
		Rule rule = new Rule(22);
		assertFalse(rule.evolve(new boolean[] {true, true, true}));
		assertFalse(rule.evolve(new boolean[] {true, true, false}));
		assertFalse(rule.evolve(new boolean[] {true, false, true}));
		assertTrue(rule.evolve(new boolean[] {true, false, false}));
		assertFalse(rule.evolve(new boolean[] {false, true, true}));
		assertTrue(rule.evolve(new boolean[] {false, true, false}));
		assertTrue(rule.evolve(new boolean[] {false, false, true}));
		assertFalse(rule.evolve(new boolean[] {false, false, false}));
		
		rule = new Rule(30);
		assertFalse(rule.evolve(new boolean[] {true, true, true}));
		assertFalse(rule.evolve(new boolean[] {true, true, false}));
		assertFalse(rule.evolve(new boolean[] {true, false, true}));
		assertTrue(rule.evolve(new boolean[] {true, false, false}));
		assertTrue(rule.evolve(new boolean[] {false, true, true}));
		assertTrue(rule.evolve(new boolean[] {false, true, false}));
		assertTrue(rule.evolve(new boolean[] {false, false, true}));
		assertFalse(rule.evolve(new boolean[] {false, false, false}));
	}
	
	@Test
	void testEvolveGeneration() {
		Generation current = new Generation("10001110", '1');
		
		Rule rule = new Rule(22);
		Generation next = rule.evolve(current);
		assertEquals("11010000", next.getStates('0', '1'));
		
		rule = new Rule(30);
		next = rule.evolve(current);
		assertEquals("TTFTTFFF", next.getStates('F', 'T'));
	}
}
