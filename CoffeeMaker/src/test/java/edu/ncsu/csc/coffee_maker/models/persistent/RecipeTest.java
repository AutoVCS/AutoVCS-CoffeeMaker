package edu.ncsu.csc.coffee_maker.models.persistent;

import static org.junit.Assert.*;

import java.util.List;

import org.hibernate.Session;
import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc.coffee_maker.util.HibernateUtil;

/**
 * Tests the Recipe object.
 * @author sesmith5
 */
public class RecipeTest {
	
	/** Database session for testing */
	private Session session;
	
	/**
	 * Creates the session and transaction before testing
	 */
	@Before
	public void before() {
		session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		
		Recipe.deleteAll(Recipe.class);
	}


	/**
	 * Tests creating and then retrieving a recipe
	 */
	@Test
	public void testAddRecipe() {		
		Recipe r1 = new Recipe();
		r1.setName("Black Coffee");
		r1.setPrice(1);
		r1.setCoffee(1);
		r1.setMilk(0);
		r1.setSugar(0);
		r1.setChocolate(0);
		r1.save();
		
		Recipe r2 = new Recipe();
		r2.setName("Mocha");
		r2.setPrice(1);
		r2.setCoffee(1);
		r2.setMilk(1);
		r2.setSugar(1);
		r2.setChocolate(1);
		r2.save();
		
		List<Recipe> recipes = Recipe.getAll();
		assertEquals(2, recipes.size());
	}

}
