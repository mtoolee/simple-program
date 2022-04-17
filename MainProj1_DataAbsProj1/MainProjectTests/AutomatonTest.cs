using FluentAssertions;
using MainProject;
using Xunit;

namespace MainProjectTests
{
    public class AutomatonTest
    {
        [Fact]
        public void ShouldConstruct()
        {
            Automaton eca = new Automaton(22, new Generation(false, true, false));
            eca.Should().Be(eca.GetRuleNum());
            eca.getGeneration(0).getStates().Should().BeEquivalentTo(new bool[] { false, true, false });
            eca.GetTotalSteps().Should().Be(0);
            eca.ToString().Should().Be("010");
        }

        [Fact]
        public void ShouldEvolve()
        {
            Automaton eca = new Automaton(22, new Generation("000000010000000", '1'));
            eca.Evolve(-1).Should().Be(0);
            
            eca.Evolve(1).Should().Be(1);
            eca.GetTotalSteps().Be(1);
            
            string evolution =  
            "000000010000000" + Environment.NewLine + 
            "000000111000000";
            eca.ToString().Should().Be(evolution);
            eca.Evolve(2).Should().Be(2);
            eca.GetTotalSteps().Should().Be(3);
            eca.FalseSymbol = '_';
            eca.TrueSymbol = '%';
            evolution = 
				"_______%_______" + Environment.NewLine + 
				"______%%%______" + Environment.NewLine + 
				"_____%___%_____" + Environment.NewLine + 
				"____%%%_%%%____";
            eca.ToString().Should().Be(evolution);
            
            Generation gen = eca.getGeneration(7);
            gen.GetStates('.', 'O').Should().Be("OOO.OOO.OOO.OOO");
            eca.GetTotalSteps().Should().Be(7);
            eca.FalseSymbol = '.';
            eca.TrueSymbol = 'O';
            evolution = 
				".......O......." + Environment.NewLine + 
				"......OOO......" + Environment.NewLine + 
				".....O...O....." + Environment.NewLine + 
				"....OOO.OOO...." + Environment.NewLine + 
				"...O.......O..." + Environment.NewLine + 
				"..OOO.....OOO.." + Environment.NewLine + 
				".O...O...O...O." + Environment.NewLine + 
				"OOO.OOO.OOO.OOO";
            eca.ToString().Should().Be(evolution);
        }

        [Fact]
        public void ShouldGetCurrentZero()
        {
            Automaton eca = new Automaton(22, 
				new Generation("000000010000000", '1'));
            eca.GetCurrentGeneration().GetStates(eca.falseSymbol, eca.trueSymbol).Should().Be("000000010000000");
        }

        [Fact]
        public void ShouldGetCurrentGenerationFive()
        {
            Automaton eca = new Automaton(22, 
				new Generation("000000010000000", '1'));
            eca.Evolve(5);
            eca.GetCurrentGeneration().GetStates(eca.falseSymbol, eca.trueSymbol).Should().Be("001110000011100");
        }

    }
}