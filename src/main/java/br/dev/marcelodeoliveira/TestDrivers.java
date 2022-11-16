package br.dev.marcelodeoliveira;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.Dimension;

public class TestDrivers {

	private final Dimension DIM_2K = new Dimension(1280, 760);

	private final String GOOGLE_BR_URL = "http://www.google.com.br";

	@Test
	@Ignore
	public void TestZero() {

		Assert.fail();
	}

	@Test
	public void testGoogleDriver() {

		WebDriver wdriver = new ChromeDriver();

		wdriver.manage().window().setSize(DIM_2K);

		wdriver.get(GOOGLE_BR_URL);

		Assert.assertTrue(wdriver.getTitle().equalsIgnoreCase("Google"));
	}

	@Test
	public void testFirefoxDriver() {

		WebDriver wdriver = new FirefoxDriver();

		wdriver.manage().window().setSize(DIM_2K);

		wdriver.get(GOOGLE_BR_URL);

		Assert.assertTrue(wdriver.getTitle().equalsIgnoreCase("Google"));
	}

	@Test
	public void testEdgeDriver() {

		WebDriver wdriver = new EdgeDriver();

		wdriver.manage().window().setSize(DIM_2K);

		wdriver.get(GOOGLE_BR_URL);

		Assert.assertTrue(wdriver.getTitle().equalsIgnoreCase("Google"));
	}

}
