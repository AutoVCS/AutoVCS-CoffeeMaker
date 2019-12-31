package edu.ncsu.csc.selenium;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import edu.ncsu.csc.coffee_maker.models.persistent.Inventory;
import edu.ncsu.csc.coffee_maker.models.persistent.Recipe;

/**
 * Tests Make Coffee functionality.
 *
 * @author Elizabeth Gilbert (evgilber@ncsu.edu)
 * @author Kai Presler-Marshall (kpresle@ncsu.edu)
 */
public class MakeCoffeeTest extends SeleniumTest {

    /** The URL for CoffeeMaker - change as needed */
    private String             baseUrl;
    private final StringBuffer verificationErrors = new StringBuffer();

    @Override
    @Before
    protected void setUp () throws Exception {
        Inventory.deleteAll( Inventory.class );

        /* Create lots of inventory to use */
        final Inventory ivt = new Inventory( 500, 500, 500, 500 );
        ivt.save();

        super.setUp();
        baseUrl = "http://localhost:8080";
        driver.manage().timeouts().implicitlyWait( 20, TimeUnit.SECONDS );

    }

    /**
     * Helper to create a recipe to make
     *
     * @return the name of the recipe
     * @throws Exception
     *             if there was an issue in submitting the recipe
     */
    private void createRecipe ( final String name, final int price, final int amtCoffee, final int amtMilk,
            final int amtSugar, final int amtChocolate ) throws Exception {

        final Recipe e = Recipe.getByName( name );
        if ( null != e ) {
            e.delete();
        }

        final Recipe recipe = new Recipe();
        recipe.setName( name );
        recipe.setPrice( price );
        recipe.setCoffee( amtCoffee );
        recipe.setMilk( amtMilk );
        recipe.setSugar( amtSugar );
        recipe.setChocolate( amtChocolate );
        recipe.save();

    }

    /**
     * Looks through the list of available recipes and selects the specified
     * recipe
     *
     * @param name
     * @return true if found and selected, false if not
     * @throws InterruptedException
     */
    private boolean selectRecipe ( final String name ) throws InterruptedException {
        final List<WebElement> list = driver.findElements( By.name( "name" ) );
        Thread.sleep( 3000 );

        // Select the recipe
        for ( final WebElement we : list ) {
            if ( name.equals( we.getAttribute( "value" ) ) ) {
                we.click();
                // Wait for thread to perform operation
                while ( !we.isSelected() ) {
                    Thread.sleep( 1000 );
                }

                return true;
            }
        }

        return false;
    }

    /**
     * Create valid coffee
     *
     * @throws Exception
     *
     */
    private void makeCoffee ( final String recipeName, final int price, final int amtCoffee, final int amtMilk,
            final int amtSugar, final int amtChocolate, final int paid, final String expectedMessage )
            throws Exception {
        createRecipe( recipeName, price, amtCoffee, amtMilk, amtSugar, amtChocolate );

        driver.get( baseUrl + "" );
        driver.findElement( By.linkText( "Make Coffee" ) ).click();

        selectRecipe( recipeName );

        try {
            driver.findElement( By.name( "amtPaid" ) ).clear();
            driver.findElement( By.name( "amtPaid" ) ).sendKeys( paid + "" );
        }
        catch ( final Exception e ) {
            System.out.println( driver.getCurrentUrl() );
            System.out.println( driver.getPageSource() );
            Assert.fail();
        }

        // Submit
        System.out.println( recipeName + " " + price + " " + amtCoffee + " " + amtMilk + " " + " " + amtSugar + " "
                + amtChocolate + " " + paid + " " + expectedMessage );
        driver.findElement( By.cssSelector( "input[type=\"submit\"]" ) ).click();
        Thread.sleep( 1000 );

        // Make sure the proper message was displayed.
        assertTextPresent( expectedMessage, driver );
    }

    /**
     * Test for making coffee (valid) Expect to get an appropriate success
     * message.
     *
     * @throws Exception
     */
    @Test
    public void testValidMakeCoffee () throws Exception {
        makeCoffee( "Coffee", 60, 0, 3, 7, 2, 60, "Coffee was made" );
        makeCoffee( "Coffee", 60, 5, 0, 7, 2, 60, "Coffee was made" );
        makeCoffee( "Coffee", 60, 5, 3, 0, 2, 60, "Coffee was made" );
        makeCoffee( "Coffee", 60, 5, 3, 0, 2, 60, "Coffee was made" );
        makeCoffee( "Coffee", 60, 5, 3, 7, 0, 60, "Coffee was made" );
        makeCoffee( "Coffee", 60, 5, 3, 7, 2, 100, "Coffee was made" );
        makeCoffee( "Coffee", 60, 5, 3, 7, 2, 61, "Coffee was made" );
    }

    /**
     * Test for making coffee (invalid) Expect to get an appropriate failure
     * message
     *
     * @throws Exception
     */
    @Test
    public void testInvalidMakeCoffee () throws Exception {
        makeCoffee( "Coffee", 60, 0, 3, 7, 2, 59, "Error while making recipe" );
        makeCoffee( "Coffee", 60, 5, 0, 7, 2, -1, "Error while making recipe" );
    }

    @Override
    @After
    public void tearDown () {
        final String verificationErrorString = verificationErrors.toString();
        if ( !"".equals( verificationErrorString ) ) {
            fail( verificationErrorString );
        }
        /* Remove our inventory so we don't mess up any future test */
        Inventory.deleteAll( Inventory.class );
    }
}
