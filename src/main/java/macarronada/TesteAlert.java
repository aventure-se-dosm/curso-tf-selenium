package macarronada;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class TesteAlert {

	private final String APP_ROOT = System.getProperty("user.dir");
	private final String PAGE_LOCATION = "file:///" + APP_ROOT + "/src/main/resources/campo_de_testes/componentes.html";

	private static WebDriver webdriver;

	// private List<WebDriver> webdrivers = null;

	@Before
	public void setup() {

		/*
		 * webdrivers = Arrays.asList( new FirefoxDriver(), new ChromeDriver(), new
		 * EdgeDriver() );
		 */

		// funciona para testes grandões!
		webdriver = new ChromeDriver();

		// quebra para testes grandões!
		// webdriver = new FirefoxDriver();

		/*
		 * por utilizarmos a mesma url podemos declarar a url da página aqui
		 */

		webdriver.get(PAGE_LOCATION);

	}

	@Test
	public void alertSimpleTypeTest() {
		/**
		 * OBJETIVO: Obter o texto do alert disparado pelo clique do botão "alert" e
		 * copiá-lo para o textfield "nome".
		 */

		WebElement nomeTextArea = webdriver.findElement(By.id("elementosForm:nome"));

		// aqui, atestar que realmente é o único elemento com id 'elementosForm:nome'.
		Assert.assertEquals(nomeTextArea.getAttribute("name"), "elementosForm:nome");

		WebElement alertaButton = webdriver.findElements(By.id("alert")).stream()
				.filter(p -> p.getAttribute("value").equals("Alert"))
				.filter(p -> p.getAttribute("type").equals("button")).findFirst().orElse(null);

		Assert.assertNotNull(alertaButton);
		alertaButton.click();

		// não capturo exceção porque aqui se o clique deu certo não deve haver excessão
		var alertLocator = webdriver.switchTo().alert();

		var message = alertLocator.getText();

		alertLocator.accept();

		webdriver.switchTo().defaultContent();

		nomeTextArea.sendKeys(message);

		Assert.assertEquals("Alert Simples", message);
		Assert.assertEquals("Alert Simples", nomeTextArea.getAttribute("value"));

		nomeTextArea.sendKeys(message);

	}

	@Test
	public void alertOkTest() {

		String message;
		WebElement confirmButton = webdriver.findElements(By.id("alert")).stream()
				.filter(p -> p.getAttribute("value").equals("Alert"))
				.filter(p -> p.getAttribute("type").equals("button")).findFirst().orElse(null);

		Assert.assertNotNull(confirmButton);

		confirmButton.click();

		var confirmLocator = webdriver.switchTo().alert();

		message = confirmLocator.getText();
		confirmLocator.accept();

		Assert.assertEquals(message, "Alert Simples");

		//
	}

	@Test
	public void alertPromptTest() {

		final String NUMBER_FOR_PROMPT = "99";
		Alert promptAlertLocator;

		List<String> messages = new ArrayList<>();

		WebElement promptButton = webdriver.findElements(By.id("prompt")).stream()
				.filter(p -> p.getAttribute("value").equals("Prompt"))
				.filter(p -> p.getAttribute("type").equals("button")).findFirst().orElse(null);

		Assert.assertNotNull(promptButton);

		// enviar 99 e depois concordar
		promptButton.click();
		promptAlertLocator = webdriver.switchTo().alert();
		promptAlertLocator.sendKeys(NUMBER_FOR_PROMPT);
		promptAlertLocator.accept(); // Concordar (accept)
		promptAlertLocator.accept();
		messages.add(promptAlertLocator.getText());
		// message = promptAlertLocator.getText();
		promptAlertLocator.dismiss();
		// Assert.assertEquals(message, ":D");

		// Enviar 99 e depois negar
		promptButton.click();
		promptAlertLocator.sendKeys(NUMBER_FOR_PROMPT);
		promptAlertLocator.accept();
		promptAlertLocator.dismiss(); // Negar
		messages.add(promptAlertLocator.getText());
		// message = promptAlertLocator.getText();
		promptAlertLocator.dismiss();
		// Assert.assertEquals(message, ":(");

		// Era Null? Concordar.
		promptButton.click();
		promptAlertLocator.dismiss();
		promptAlertLocator.accept(); // Concordar
		messages.add(promptAlertLocator.getText());
		// message = promptAlertLocator.getText();
		promptAlertLocator.dismiss();
		// Assert.assertEquals(message, ":D");

		// Era Null? Negar (dismiss)
		promptButton.click();
		promptAlertLocator.dismiss(); // Negar
		promptAlertLocator.dismiss();
		messages.add(promptAlertLocator.getText());
		// message = promptAlertLocator.getText();
		promptAlertLocator.dismiss();
		// Assert.assertEquals(message, ":(");

		Assert.assertArrayEquals(Arrays.asList(":D", ":(", ":D", ":(").toArray(), messages.toArray());

	}

	private void quitWebDrivers() {
//		webdrivers.forEach(wd -> {
//			wd.wait(10000L);
//			wd.quit();
//		});
		// webdriver.wait(10000L);
		if (webdriver != null) {
			// webdriver.close();
			webdriver.quit();
		}
	}

	@After
	public void finish_each() {

		quitWebDrivers();
	}

}
