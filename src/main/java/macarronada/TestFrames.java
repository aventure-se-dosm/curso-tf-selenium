package macarronada;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

	public void DeveEscreverNaPopupENaTelaPrincipal() {
		/**
		 * Ao clicar no <button id=buttonPopUpEasy> abrimos uma janela (window) que
		 * sempre insere um novo TextArea. Caso não esteja, uma nova janela é aberta e
		 * apenas uma TextArea é exibida.
		 */

		webdriver.findElement(By.id("buttonPopUpEasy")).click();
		webdriver.findElement(By.id("buttonPopUpEasy")).click();
		webdriver.findElement(By.id("buttonPopUpEasy")).click();

		// consultando a nova janela aberta (identificada como 'Popup')
		var popup = webdriver.switchTo().window("Popup");

		// Pegar os textAreas obtidos com os clicks:

		List<WebElement> textareas = popup.findElements(By.tagName("textarea"));
		Assert.assertEquals(3, textareas.size());

		ArrayList<String> message = new ArrayList<>();

		message.addAll(Arrays.asList("Eu", "Te", "Amo!"));


		List<String> allTextAreaTexts = new ArrayList<>();

		 textareas.forEach(p-> p.sendKeys(message.remove(0)));


		textareas.forEach(e -> allTextAreaTexts.add(e.getAttribute("value")));


		Assert.assertEquals(Arrays.asList("Eu Te Amo!".split(" ")).subList(0, 3), allTextAreaTexts.subList(0, 3));

	}
	
	@Test
	public void deveInteragirComJanelaSemIDTest () {
		
		/**
		 * Usa popud do mal.
		 * procurams pela janela aberta sem id com
		 * webdriver.getWindowHandles()
		 */
		
		webdriver.findElement(By.id("buttonPopUpHard")).click();
		
		//procura a popup sem id aberpa
		
		var janelas = webdriver.getWindowHandles().toArray();
	
		
		Assert.assertEquals(janelas[1].toString(), (String)janelas[1]);
		
		var textAreaContent = "Você é linda!";
				
		var textArea = webdriver.switchTo().window(janelas[1].toString()).findElement(By.tagName("textarea"));
		
		textArea.sendKeys(textAreaContent);
		
		Assert.assertEquals(textAreaContent, textArea.getAttribute("value"));
	
		
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
