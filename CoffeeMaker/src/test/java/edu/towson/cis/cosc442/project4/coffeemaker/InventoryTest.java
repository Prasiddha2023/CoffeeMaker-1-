package edu.towson.cis.cosc442.project4.coffeemaker;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class InventoryTest {

    private Inventory inventory;

    @BeforeEach
    void setUp() {
        inventory = new Inventory();
    }

    private Recipe makeRecipe(int coffee, int milk, int sugar, int chocolate) {
        Recipe r = new Recipe();
        r.setName("Test");
        r.setAmtCoffee(coffee);
        r.setAmtMilk(milk);
        r.setAmtSugar(sugar);
        r.setAmtChocolate(chocolate);
        return r;
    }

    @Test
    void startsWithFifteenOfEach() {
        // Act
        Inventory fresh = new Inventory();
        // Assert
        assertEquals(15, fresh.getCoffee());
        assertEquals(15, fresh.getMilk());
        assertEquals(15, fresh.getSugar());
        assertEquals(15, fresh.getChocolate());
    }

    @ParameterizedTest(name = "setCoffee({0}) -> {1}")
    @CsvSource({ "-1,0", "0,0", "1,1", "50,50" })
    void setCoffee(int input, int expected) {
        // Act
        inventory.setCoffee(input);
        // Assert
        assertEquals(expected, inventory.getCoffee());
    }

    @ParameterizedTest(name = "setMilk({0}) -> {1}")
    @CsvSource({ "-1,0", "0,0", "1,1", "50,50" })
    void setMilk(int input, int expected) {
        // Act
        inventory.setMilk(input);
        // Assert
        assertEquals(expected, inventory.getMilk());
    }

    @ParameterizedTest(name = "setSugar({0}) -> {1}")
    @CsvSource({ "-1,0", "0,0", "1,1", "50,50" })
    void setSugar(int input, int expected) {
        // Act
        inventory.setSugar(input);
        // Assert
        assertEquals(expected, inventory.getSugar());
    }

    @ParameterizedTest(name = "setChocolate({0}) -> {1}")
    @CsvSource({ "-1,0", "0,0", "1,1", "50,50" })
    void setChocolate(int input, int expected) {
        // Act
        inventory.setChocolate(input);
        // Assert
        assertEquals(expected, inventory.getChocolate());
    }

    @Test
    void setterDoesNotChangeOtherIngredients() {
        // Act
        inventory.setCoffee(3);
        // Assert
        assertEquals(15, inventory.getMilk());
        assertEquals(15, inventory.getSugar());
        assertEquals(15, inventory.getChocolate());
    }

    @Test
    void enoughIngredientsExactAmount() {
        // Arrange
        Recipe exact = makeRecipe(15, 15, 15, 15);
        // Act
        boolean enough = inventory.enoughIngredients(exact);
        // Assert
        assertTrue(enough);
    }

    @Test
    void enoughIngredientsNothingNeeded() {
        // Arrange
        Recipe nothing = makeRecipe(0, 0, 0, 0);
        // Act
        boolean enough = inventory.enoughIngredients(nothing);
        // Assert
        assertTrue(enough);
    }

    @ParameterizedTest(name = "coffee={0}, milk={1}, sugar={2}, chocolate={3}")
    @CsvSource({ "16,0,0,0", "0,16,0,0", "0,0,16,0", "0,0,0,16" })
    void enoughIngredientsOneOver(int coffee, int milk, int sugar, int chocolate) {
        // Arrange
        Recipe tooMuch = makeRecipe(coffee, milk, sugar, chocolate);
        // Act
        boolean enough = inventory.enoughIngredients(tooMuch);
        // Assert
        assertFalse(enough);
    }

    @Test
    void toStringFormat() {
        // Arrange
        inventory.setCoffee(1);
        inventory.setMilk(2);
        inventory.setSugar(3);
        inventory.setChocolate(4);
        // Act
        String text = inventory.toString();
        // Assert
        assertEquals("Coffee: 1\nMilk: 2\nSugar: 3\nChocolate: 4\n", text);
    }
}