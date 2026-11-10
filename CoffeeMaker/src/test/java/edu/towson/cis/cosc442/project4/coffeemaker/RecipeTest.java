package edu.towson.cis.cosc442.project4.coffeemaker;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class RecipeTest {

    private Recipe recipe;

    @BeforeEach
    void setUp() {
        recipe = new Recipe();
    }

    private Recipe named(String name) {
        Recipe r = new Recipe();
        r.setName(name);
        return r;
    }

    @Test
    void newRecipeDefaults() {
        // Act + Assert
        assertNull(recipe.getName());
        assertEquals(0, recipe.getPrice());
        assertEquals(0, recipe.getAmtCoffee());
        assertEquals(0, recipe.getAmtMilk());
        assertEquals(0, recipe.getAmtSugar());
        assertEquals(0, recipe.getAmtChocolate());
    }

    @Test
    void setNameStoresName() {
        // Act
        recipe.setName("Latte");
        // Assert
        assertEquals("Latte", recipe.getName());
    }

    @ParameterizedTest(name = "setPrice({0}) -> {1}")
    @CsvSource({ "-1,0", "0,0", "1,1", "50,50" })
    void setPrice(int input, int expected) {
        // Act
        recipe.setPrice(input);
        // Assert
        assertEquals(expected, recipe.getPrice());
    }

    @ParameterizedTest(name = "setAmtCoffee({0}) -> {1}")
    @CsvSource({ "-1,0", "0,0", "1,1", "50,50" })
    void setAmtCoffee(int input, int expected) {
        // Act
        recipe.setAmtCoffee(input);
        // Assert
        assertEquals(expected, recipe.getAmtCoffee());
    }

    @ParameterizedTest(name = "setAmtMilk({0}) -> {1}")
    @CsvSource({ "-1,0", "0,0", "1,1", "50,50" })
    void setAmtMilk(int input, int expected) {
        // Act
        recipe.setAmtMilk(input);
        // Assert
        assertEquals(expected, recipe.getAmtMilk());
    }

    @ParameterizedTest(name = "setAmtSugar({0}) -> {1}")
    @CsvSource({ "-1,0", "0,0", "1,1", "50,50" })
    void setAmtSugar(int input, int expected) {
        // Act
        recipe.setAmtSugar(input);
        // Assert
        assertEquals(expected, recipe.getAmtSugar());
    }

    @ParameterizedTest(name = "setAmtChocolate({0}) -> {1}")
    @CsvSource({ "-1,0", "0,0", "1,1", "50,50" })
    void setAmtChocolate(int input, int expected) {
        // Act
        recipe.setAmtChocolate(input);
        // Assert
        assertEquals(expected, recipe.getAmtChocolate());
    }

    @Test
    void settersDoNotAffectEachOther() {
        // Act
        recipe.setPrice(1);
        recipe.setAmtCoffee(2);
        recipe.setAmtMilk(3);
        recipe.setAmtSugar(4);
        recipe.setAmtChocolate(5);
        // Assert
        assertEquals(1, recipe.getPrice());
        assertEquals(2, recipe.getAmtCoffee());
        assertEquals(3, recipe.getAmtMilk());
        assertEquals(4, recipe.getAmtSugar());
        assertEquals(5, recipe.getAmtChocolate());
    }

    @Test
    void equalsSameName() {
        // Arrange
        Recipe a = named("Latte");
        Recipe b = named("Latte");
        // Act
        boolean result = a.equals(b);
        // Assert
        assertTrue(result);
    }

    @Test
    void equalsDifferentName() {
        // Arrange
        Recipe a = named("Latte");
        Recipe b = named("Mocha");
        // Act
        boolean result = a.equals(b);
        // Assert
        assertFalse(result);
    }

    @Test
    void equalsThisNameNull() {
        // Arrange
        Recipe a = new Recipe();
        Recipe b = named("Latte");
        // Act
        boolean result = a.equals(b);
        // Assert
        assertFalse(result);
    }

    @Test
    void equalsOtherNameNull() {
        // Arrange
        Recipe a = named("Latte");
        Recipe b = new Recipe();
        // Act
        boolean result = a.equals(b);
        // Assert
        assertFalse(result);
    }

    @Test
    void equalsBothNamesNull() {
        // Arrange
        Recipe a = new Recipe();
        Recipe b = new Recipe();
        // Act
        boolean result = a.equals(b);
        // Assert
        assertFalse(result);
    }

    @Test
    void toStringReturnsName() {
        // Arrange
        Recipe r = named("Latte");
        // Act
        String text = r.toString();
        // Assert
        assertEquals("Latte", text);
    }
}