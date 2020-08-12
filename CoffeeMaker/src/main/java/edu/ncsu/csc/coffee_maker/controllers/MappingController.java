package edu.ncsu.csc.coffee_maker.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller class for the URL mappings for CoffeeMaker. The controller
 * returns the approprate HTML page in the /src/main/resources/templates folder.
 *
 * @author Kai Presler-Marshall
 * @author Elizabeth Gilbert
 * @author Sarah Heckman
 */
@Controller
public class MappingController {
	
	/**
     * On a GET request to /index, the IndexController will return
     * /src/main/resources/templates/index.html.
     *
     * @param model
     *            underlying UI model
     * @return contents of the page
     */
    @GetMapping ( { "/index", "/" } )
    public String index ( final Model model ) {
        return "index";
    }
    
    /**
     * On a GET request to /recipe, the RecipeController will return
     * /src/main/resources/templates/recipe.html.
     *
     * @param model
     *            underlying UI model
     * @return contents of the page
     */
    @GetMapping ( "/recipe" )
    public String recipeForm ( final Model model ) {
        return "recipe";
    }
    
    /**
     * On a GET request to /deleterecipe, the DeleteRecipeController will return
     * /src/main/resources/templates/deleterecipe.html.
     *
     * @param model
     *            underlying UI model
     * @return contents of the page
     */
    @GetMapping ( "/deleterecipe" )
    public String deleteRecipeForm ( final Model model ) {
        return "deleterecipe";
    }
    
    /**
     * On a GET request to /editrecipe, the EditRecipeController will return
     * /src/main/resources/templates/editrecipe.html.
     *
     * @param model
     *            underlying UI model
     * @return contents of the page
     */
    @GetMapping ( "/editrecipe" )
    public String editRecipeForm ( final Model model ) {
        return "editrecipe";
    }
    
    /**
     * Handles a GET request for inventory. The GET request provides a view to
     * the client that includes the list of the current ingredients in the
     * inventory and a form where the client can enter more ingredients to add
     * to the inventory.
     *
     * @param model
     *            underlying UI model
     * @return contents of the page
     */
    @GetMapping ( "/inventory" )
    public String inventoryForm ( final Model model ) {
        return "inventory";
    }
    
    /**
     * On a GET request to /makecoffee, the MakeCoffeeController will return
     * /src/main/resources/templates/makecoffee.html.
     *
     * @param model
     *            underlying UI model
     * @return contents of the page
     */
    @GetMapping ( "/makecoffee" )
    public String makeCoffeeForm ( final Model model ) {
        return "makecoffee";
    }

}
