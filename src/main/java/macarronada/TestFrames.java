package macarronada;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
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
	
	@Ignore
	@Test
	public void frameInteractionTest () {
		
		//pegar a mensagem do <button> framebutton
		//dentro do <iframe> frame1
		
		
		
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
