import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class TesteCampo {

	private List<WebDriver> webdrivers = null;
	
	@Before
	public void setup () {
		
		webdrivers = Arrays.asList(
				new FirefoxDriver(),
				new ChromeDriver(), 
				new EdgeDriver()
		);
		
	}
	
	
	private void quitWebDrivers() {
		webdrivers.forEach(wd -> wd.close());
	}
	
	public void finish () {
		
		quitWebDrivers();
	
	}
}
