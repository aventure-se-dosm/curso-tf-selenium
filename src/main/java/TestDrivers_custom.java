
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class TestDrivers_custom {

	// Application Constants

	private final Dimension DIM_2K = new Dimension(1280, 760);

	private final String GOOGLE_BR_URL = "http://www.google.com.br";

	/**
	 * Path dinâmico da raiz do projeto, prefixada pela locação do projeto na
	 * máquina em que for salvo.
	 **/
	private final String DYNAMIC_PROJECT_PATH = System.getProperty("user.dir");

	private List<WebDriver> webdrivers = null;

	private List<String> expectedList = new ArrayList<>();

	private List<String> actualList = new ArrayList<>();

	@Before
	public void setup() {

		webdrivers = Arrays.asList(new FirefoxDriver(), new ChromeDriver(), new EdgeDriver());

		WebDriver wdriver = new ChromeDriver();

		wdriver.manage().window().setSize(DIM_2K);

		System.out.println(DYNAMIC_PROJECT_PATH);
	}

	@Test
	// @Ignore
	public void TestZero() {

		/**
		 * [1] Set a a path for wabpage and a webdriver;
		 * 
		 * [2] Set browser's windows properties a. size; b. position; c. etc....
		 * 
		 * [3] go to a webpage by the set path
		 * 
		 * [4] Make tests as you want for.
		 */

		System.out.println(System.getProperty(DYNAMIC_PROJECT_PATH));
		Assert.fail();
	}

	private String testWebDrivers(WebDriver wdriver) {

		wdriver.manage().window().setSize(DIM_2K);

		wdriver.get(GOOGLE_BR_URL);

		return wdriver.getTitle().toString().toUpperCase();

	}

	// versão lambda
	@Ignore
	@Test
	public void testWebDrivers() {

		/**
		 * Do your code here
		 */

		Assert.fail();
	}

	// versão sem stream
	@Test
	public void testWebDrivers_procedural() {

		// funciona só a partir do Java10

		for (WebDriver w : webdrivers) {
			actualList.add(testWebDrivers(w));
			expectedList.add("GOOGLE");
		}

		// expectedList.set(1, "booble");

		Assert.assertArrayEquals(actualList.toArray(), expectedList.toArray());

	}

//	@Test
//	public void testGoogleDriver() {
//
//		WebDriver wdriver = new ChromeDriver();
//
//		wdriver.manage().window().setSize(DIM_2K);
//
//		wdriver.get(GOOGLE_BR_URL);
//
//		Assert.assertTrue(wdriver.getTitle().equalsIgnoreCase("Google"));
//	}
//
//	@Test
//	public void testFirefoxDriver() {
//
//		WebDriver wdriver = new FirefoxDriver();
//
//		wdriver.manage().window().setSize(DIM_2K);
//
//		wdriver.get(GOOGLE_BR_URL);
//
//		Assert.assertTrue(wdriver.getTitle().equalsIgnoreCase("Google"));
//	}
//
//	@Test
//	public void testEdgeDriver() {
//
//		WebDriver wdriver = new EdgeDriver();
//
//		wdriver.manage().window().setSize(DIM_2K);
//
//		
//		
//		wdriver.get(GOOGLE_BR_URL);
//
//		Assert.assertTrue(wdriver.getTitle().equalsIgnoreCase("Google"));
//	}

	private void quitWebDrivers() {
		webdrivers.forEach(wd -> wd.close());

	}

	public void finish() {

		quitWebDrivers();

	}

}
