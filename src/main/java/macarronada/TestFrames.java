package macarronada;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Stream;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class TestFrames {
	private final String APP_ROOT = System.getProperty("user.dir");
	private final String PAGE_LOCATION = "file:///" + APP_ROOT + "/src/main/resources/campo_de_testes/componentes.html";

	private static WebDriver webdriver;

	// private List<WebDriver> webdrivers = null;

	@Before
	public void setup() {

		webdriver = new ChromeDriver();

		webdriver.get(PAGE_LOCATION);

	}

	@Test
	public void FrameButtonsWithSameIdTest() {
		/*
		 * Pegar a mensagem do <button> framebutton dentro do <iframe> frame1.
		 * 
		 * Existe OUTRO botão com id=framebitton noutro frame. Por isso, foi necessário
		 * aplicarmos switch para acharmos este elemento.
		 *
		 */
		var button = webdriver.switchTo().frame("frame1").findElement(By.id("framebutton"));
		button.click();

		var leastActiveAlert = webdriver.switchTo().alert();
		var message = leastActiveAlert.getText();

		Assert.assertEquals(message, "Frame OK!");

	}

	@Test
	public void GetNewWindowTextAlreadyWrittenOnSomeOfItsTextAreasTest () {
		/**
		 * Ao clicar no <button id=buttonPopUpEasy> abrimos uma janela
		 * (window) que sempre insere um novo TextArea. Caso não esteja,
		 * uma nova janela é aberta e apenas uma TextArea é exibida.
		 */
		
		
		webdriver.findElement(By.id("buttonPopUpEasy")).click();
		webdriver.findElement(By.id("buttonPopUpEasy")).click();
		webdriver.findElement(By.id("buttonPopUpEasy")).click();
		
		//consultando a nova janela aberta (identificada como 'Popup')
		var popup = webdriver.switchTo().window("Popup");
		 popup = webdriver.switchTo().window("Popup");
		
		//Pegar os textAreas obtidos com os clicks: 
		
		
		var textareas = popup.findElements(By.tagName("textarea"));
		Assert.assertEquals(3, textareas.size());
		
		ArrayList<String> message = new ArrayList<>();
		
		message.addAll(Arrays.asList("Eu", "Te", "Amo!"));
		

		//textareas.forEach(p-> p.sendKeys(message.remove(0)));
		
		for (WebElement textAreaElement: textareas) {
			textAreaElement.sendKeys(message.remove(0));
		}
		
		var message2 = Arrays.asList("Eu", "Te", "Amo!");
		
		
		Assert.assertArrayEquals(message2.toArray() ,textareas.stream().map(t -> t.getText()).toArray<String>());
			
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
