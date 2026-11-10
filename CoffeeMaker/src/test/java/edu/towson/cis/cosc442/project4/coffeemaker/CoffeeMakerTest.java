package edu.towson.cis.cosc442.project4.coffeemaker;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class CoffeeMakerTest {

    CoffeeMaker cm;

    @BeforeEach
    void setUp() {
        cm = new CoffeeMaker();
    }

    private Recipe makeRecipe(String name, int price, int coffee, int milk, int sugar, int chocolate) {
        Recipe r = new Recipe();
        r.setName(name);
        r.setPrice(price);
        r.setAmtCoffee(coffee);
        r.setAmtMilk(milk);
        r.setAmtSugar(sugar);
        r.setAmtChocolate(chocolate);
        return r;
    }

    private Recipe named(String name) {
        return makeRecipe(name, 5, 1, 1, 1, 1);
    }

    @Test
    void addRecipeTrue() {
        // Arrange
        Recipe r = named("Latte");
        // Act
        boolean result = cm.addRecipe(r);
        // Assert
        assertTrue(result);
    }

    @Test
    void addRecipeStored() {
        // Arrange
        Recipe r = named("Latte");
        // Act
        cm.addRecipe(r);
        // Assert
        assertSame(r, cm.getRecipeForName("Latte"));
    }

    @Test
    void addRecipeDuplicate() {
        // Arrange
        cm.addRecipe(named("Latte"));
        // Act
        boolean result = cm.addRecipe(named("Latte"));
        // Assert
        assertFalse(result);
    }

    @Test
    void addRecipeFourthTrue() {
        // Arrange
        cm.addRecipe(named("A"));
        cm.addRecipe(named("B"));
        cm.addRecipe(named("C"));
        // Act
        boolean result = cm.addRecipe(named("D"));
        // Assert
        assertTrue(result);
    }

    @Test
    void addRecipeFifthFalse() {
        // Arrange
        cm.addRecipe(named("A"));
        cm.addRecipe(named("B"));
        cm.addRecipe(named("C"));
        cm.addRecipe(named("D"));
        // Act
        boolean result = cm.addRecipe(named("E"));
        // Assert
        assertFalse(result);
    }

    @Test
    void deleteRecipeTrue() {
        // Arrange
        Recipe r = named("Latte");
        cm.addRecipe(r);
        // Act
        boolean result = cm.deleteRecipe(r);
        // Assert
        assertTrue(result);
    }

    @Test
    void deleteRecipeFalse() {
        // Arrange
        Recipe r = named("Latte");
        // Act
        boolean result = cm.deleteRecipe(r);
        // Assert
        assertFalse(result);
    }

    @Test
    void deleteRecipeNull() {
        // Act
        boolean result = cm.deleteRecipe(null);
        // Assert
        assertFalse(result);
    }

    @Test
    void editRecipeSameNameTrue() {
        // Arrange
        Recipe oldR = makeRecipe("Latte", 5, 1, 1, 1, 1);
        Recipe newR = makeRecipe("Latte", 7, 2, 2, 2, 2);
        cm.addRecipe(oldR);
        // Act
        boolean result = cm.editRecipe(oldR, newR);
        // Assert
        assertTrue(result);
    }

    @Test
    void editRecipeSameNameUpdates() {
        // Arrange
        Recipe oldR = makeRecipe("Latte", 5, 1, 1, 1, 1);
        Recipe newR = makeRecipe("Latte", 7, 2, 2, 2, 2);
        cm.addRecipe(oldR);
        // Act
        cm.editRecipe(oldR, newR);
        // Assert
        assertEquals(7, cm.getRecipeForName("Latte").getPrice());
    }

    @Test
    void editRecipeNameChangeFalse() {
        // Arrange
        Recipe oldR = named("Old");
        Recipe newR = named("New");
        cm.addRecipe(oldR);
        // Act
        boolean result = cm.editRecipe(oldR, newR);
        // Assert
        assertFalse(result);
    }

    @Test
    void editRecipeNotInBookFalse() {
        // Arrange
        Recipe oldR = named("Latte");
        Recipe newR = named("Latte");
        // Act
        boolean result = cm.editRecipe(oldR, newR);
        // Assert
        assertFalse(result);
    }

    @Test
    void getRecipeForNameFound() {
        // Arrange
        Recipe r = named("Latte");
        cm.addRecipe(r);
        // Act
        Recipe found = cm.getRecipeForName("Latte");
        // Assert
        assertSame(r, found);
    }

    @Test
    void getRecipeForNameNotFound() {
        // Arrange
        cm.addRecipe(named("Latte"));
        // Act
        Recipe found = cm.getRecipeForName("Mocha");
        // Assert
        assertNotNull(found);
        assertNull(found.getName());
    }

    @Test
    void getRecipesContainsAdded() {
        // Arrange
        Recipe r = named("Latte");
        cm.addRecipe(r);
        // Act
        Recipe[] recipes = cm.getRecipes();
        // Assert
        assertTrue(Arrays.asList(recipes).contains(r));
    }

    @Test
    void addInventoryPositive() {
        // Arrange
        int c = cm.checkInventory().getCoffee();
        int m = cm.checkInventory().getMilk();
        int s = cm.checkInventory().getSugar();
        int ch = cm.checkInventory().getChocolate();
        // Act
        boolean result = cm.addInventory(5, 6, 7, 8);
        // Assert
        assertTrue(result);
        assertEquals(c + 5, cm.checkInventory().getCoffee());
        assertEquals(m + 6, cm.checkInventory().getMilk());
        assertEquals(s + 7, cm.checkInventory().getSugar());
        assertEquals(ch + 8, cm.checkInventory().getChocolate());
    }

    @Test
    void addInventoryZero() {
        // Arrange
        int c = cm.checkInventory().getCoffee();
        // Act
        boolean result = cm.addInventory(0, 0, 0, 0);
        // Assert
        assertTrue(result);
        assertEquals(c, cm.checkInventory().getCoffee());
    }

    @ParameterizedTest
    @CsvSource({ "-1,0,0,0", "0,-1,0,0", "0,0,-1,0", "0,0,0,-1" })
    void addInventoryNegative(int c, int m, int s, int ch) {
        // Arrange
        int coffeeBefore = cm.checkInventory().getCoffee();
        int milkBefore = cm.checkInventory().getMilk();
        int sugarBefore = cm.checkInventory().getSugar();
        int chocBefore = cm.checkInventory().getChocolate();
        // Act
        boolean result = cm.addInventory(c, m, s, ch);
        // Assert
        assertFalse(result);
        assertEquals(coffeeBefore, cm.checkInventory().getCoffee());
        assertEquals(milkBefore, cm.checkInventory().getMilk());
        assertEquals(sugarBefore, cm.checkInventory().getSugar());
        assertEquals(chocBefore, cm.checkInventory().getChocolate());
    }

    @ParameterizedTest
    @CsvSource({ "4,4", "5,0", "6,1", "10,5" })
    void makeCoffeePayment(int paid, int expectedReturn) {
        // Arrange
        Recipe r = makeRecipe("Latte", 5, 1, 1, 1, 1);
        // Act
        int result = cm.makeCoffee(r, paid);
        // Assert
        assertEquals(expectedReturn, result);
    }

    @Test
    void makeCoffeeUsesIngredients() {
        // Arrange
        Recipe r = makeRecipe("Latte", 5, 2, 3, 1, 4);
        int c = cm.checkInventory().getCoffee();
        int m = cm.checkInventory().getMilk();
        int s = cm.checkInventory().getSugar();
        int ch = cm.checkInventory().getChocolate();
        // Act
        cm.makeCoffee(r, 5);
        // Assert
        assertEquals(c - 2, cm.checkInventory().getCoffee());
        assertEquals(m - 3, cm.checkInventory().getMilk());
        assertEquals(s - 1, cm.checkInventory().getSugar());
        assertEquals(ch - 4, cm.checkInventory().getChocolate());
    }

    @Test
    void makeCoffeeUnderpaidInventory() {
        // Arrange
        Recipe r = makeRecipe("Latte", 5, 2, 3, 1, 4);
        int c = cm.checkInventory().getCoffee();
        int m = cm.checkInventory().getMilk();
        // Act
        cm.makeCoffee(r, 4);
        // Assert
        assertEquals(c, cm.checkInventory().getCoffee());
        assertEquals(m, cm.checkInventory().getMilk());
    }

    @Test
    void makeCoffeeNotEnoughReturnsPayment() {
        // Arrange
        Recipe r = makeRecipe("Huge", 5, 1000, 1, 1, 1);
        // Act
        int result = cm.makeCoffee(r, 20);
        // Assert
        assertEquals(20, result);
    }
}