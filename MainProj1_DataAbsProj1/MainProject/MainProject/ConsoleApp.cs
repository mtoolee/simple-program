

namespace MainProject
{
    public class ConsoleApp
    {
        private static Generation DEFAULT_GENERATION = new Generation(false, false, false, true, false, false, false);
        private const int DEFAULT_RULE = 22;

        private Automaton? automaton;
		string input;

		public ConsoleApp()
		{
			automaton = new Automaton(DEFAULT_RULE, DEFAULT_GENERATION);
			input = "";
		}

		public void Run()
        {
			Option option; // store user's input Option

			do
			{
				// Show possible options
				printOptions();

				// Get user input
				Console.Write("Select option > ");
				try
				{
					input = Console.ReadLine();

					int inputValue = int.Parse(input);
					option = (Option) inputValue;
				}
				catch (Exception e)
				{
					// Quit on an invalid input (e.g., lack of input)
					option = Option.QUIT;
				}
	
				// Process user input
				processOption(option);

				// Print a blank line between each action
				Console.WriteLine();

			} while (option != Option.QUIT);
		}

		private void processOption(Option option)
		{
			switch (option)
			{
				case Option.PRINT_RULE:
					printRule();
					break;
				case Option.REINIT_AUTOMATON:
					reinitAutomaton();
					break;
				case Option.EVOLVE:
					evolve();
					break;
				case Option.SET_TRUE_SYMBOL:
					setTrueSymbol();
					break;
				case Option.SET_FALSE_SYMBOL:
					setFalseSymbol();
					break;
				case Option.PRINT_CURRENT_GENERATION:
					printCurrentGeneration();
					break;
				case Option.PRINT_FULL_EVOLUTION:
					printFullEvolution();
					break;
				case Option.QUIT:
					printQuitMessage();
					break;
			}
		}

		private static void printOptions()
		{
			
			foreach (Option opt in Enum.GetValues(typeof(Option)))
			{
				Console.WriteLine($"{(int)opt} : {opt.ToString()}");
			}
		}

		/* You will need to implement the methods below. */

		private void printRule()
		{
			// TODO: Print out the current rule in the format specified by
			// the README.
		}

		private void reinitAutomaton()
		{
			// This method's implementation is provided for you.
			setTrueSymbol();
			setFalseSymbol();
			setRuleAndGeneration();
		}

		private void setRuleAndGeneration()
		{
			// TODO: Prompt the user to enter a new rule number and initial
			// generation. Refer to the README for details.
		}

		private void setTrueSymbol()
		{
			// TODO: Prompts the user to enter a new true symbol representation.
		}

		private void setFalseSymbol()
		{
			// TODO: Prompts the user to enter a new false symbol representation.
		}

		private void evolve()
		{
			// TODO: Prompt the user to enter a number of evolutions and evolves
			// the Automaton.
		}

		private void printCurrentGeneration()
		{
			// TODO: Print the current generation of the Automaton using the
			// Automaton's true and false symbols.
		}

		private void printFullEvolution()
		{
			// TODO: Print the full evolution of the automaton.
		}

		private void printQuitMessage()
		{
			// TODO: Print "Bye bye!"
		}


	}
}

