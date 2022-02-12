using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace MainProject
{
    public enum Option
    {
		[Description("Show the current Rule")]		
		PRINT_RULE,

		[Description("Reinitialize utomaton")]
		REINIT_AUTOMATON,

		[Description("Evolve the Auomaton a given number of steps")]
		EVOLVE,

		[Description("Set the true ymbol represention")]
		SET_TRUE_SYMBOL,

		[Description("Set the falsesymbol represention")]
		SET_FALSE_SYMBOL,

		[Description("Show the currnt Generation of the Automaton")]
		PRINT_CURRENT_GENERATION,

		[Description("Show the fullevolution of the Automaton")]
		PRINT_FULL_EVOLUTION,

		[Description("Quit")]
		QUIT						
	}
}
