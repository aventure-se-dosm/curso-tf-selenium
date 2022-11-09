import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class TesteCampoDeTreinamento {

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

		// webdriver = new ChromeDriver();
		webdriver = new FirefoxDriver();

		/*
		 * por utilizarmos a mesma url podemos declarar a url da página aqui
		 */

		webdriver.get(PAGE_LOCATION);

	}

	@Test
	public void abrePathTest() {
		webdriver.get(PAGE_LOCATION);

		Assert.assertTrue("Campo de TreinAMENTO".equalsIgnoreCase(webdriver.getTitle().toString()));
		// webdriver.close();

	}

	@Test

	// este teste junto com o escreveNome poderiam ser abstraidos para
	// escreveCampo().
	public void testTextArea() {

		// localizar todos os Webelement do foco da página principal com id "nome"
		// queremos apenas um (primeiro): usamos findElement (singular)
		// queremos todas as ocorrências: usamos findElements (plural)
		// .get para pegar algun dos vários encontrados
		WebElement name_by_id_1st = webdriver.findElement(By.id("elementosForm:sobrenome"));

		// escrevemos com o método SendKeys (String s).
		name_by_id_1st.sendKeys("Askon Tundia");

		Assert.assertTrue("Askon Tundia".equals(name_by_id_1st.getAttribute("value")));

		// webdriver.close();

	}

	@Test
	public void testRadioButton_values_correctness() {

		// RadioButtons
		WebElement radioButton_0 = webdriver.findElement(By.id("elementosForm:sexo:0"));
		WebElement radioButton_1 = webdriver.findElement(By.id("elementosForm:sexo:1"));

		By.tagName("label");
		// Labels
		webdriver.findElements(By.tagName("Label")).forEach(p -> System.out.println(p.getText()));

		// achamos o label que rotula o radiobutton id M:
		WebElement masc = webdriver.findElements(By.tagName("Label")).stream()
				.filter(p -> p.getText().equalsIgnoreCase("masculino")).findFirst().get();

		// achamos o label que rotula o radiobutton id F:
		WebElement femi = webdriver.findElements(By.tagName("Label")).stream()
				.filter(p -> p.getText().equalsIgnoreCase("Feminino")).findFirst().get();

		Assert.assertEquals(radioButton_0.getAttribute("value"), "M");
		Assert.assertEquals("elementosForm:sexo:0", radioButton_0.getAttribute("id"));
		Assert.assertEquals("elementosForm:sexo:0", masc.getAttribute("for"));

		Assert.assertTrue("mAsCUlinO".equalsIgnoreCase(masc.getText()));

		Assert.assertEquals(radioButton_1.getAttribute("value"), "F");
		Assert.assertEquals("elementosForm:sexo:1", radioButton_1.getAttribute("id"));
		Assert.assertEquals("elementosForm:sexo:1", femi.getAttribute("for"));
		Assert.assertTrue("FEminIno".equalsIgnoreCase(femi.getText()));

	}

	@Test
	public void testRadioButton_selection() {
		WebElement radioButton_0 = webdriver.findElement(By.id("elementosForm:sexo:0"));
		WebElement radioButton_1 = webdriver.findElement(By.id("elementosForm:sexo:1"));

		radioButton_1.click();
		Assert.assertFalse(radioButton_0.isSelected());
		Assert.assertTrue(radioButton_1.isSelected());

		radioButton_0.click();
		Assert.assertTrue(radioButton_0.isSelected());
		Assert.assertFalse(radioButton_1.isSelected());

	}

	@Test
	public void testCheckBox_singleChoice() {

		WebElement feedOption = webdriver.findElement(By.id("elementosForm:comidaFavorita:0"));

		feedOption.click();
		Assert.assertTrue(feedOption.isSelected());
		feedOption.click();
		Assert.assertFalse(feedOption.isSelected());

	}

	@Test
	public void TestCheckBox_multipleChoice() {
		// Lista com todos os Checkboxs de comida favorita
		List<WebElement> feedOptions = webdriver.findElements(By.name("elementosForm:comidaFavorita"));
		feedOptions.forEach(chkb -> {
			chkb.click();
			if (feedOptions.indexOf(chkb) % 2 == 0)
				chkb.click();
			System.out.println(chkb.getAttribute("value"));
		});

		Assert.assertFalse(feedOptions.get(0).isSelected());
		Assert.assertTrue(feedOptions.get(1).isSelected());
		Assert.assertFalse(feedOptions.get(2).isSelected());
		Assert.assertTrue(feedOptions.get(3).isSelected());

		Assert.assertFalse(feedOptions == null);
		Assert.assertTrue(feedOptions.size() == 4);

	}

	@Test
	public void testCombo_multiselection() {

		// buscar o combo
		Select escolaridadeComboSelect = new Select(webdriver.findElement(By.id("elementosForm:esportes")));

		/*
		 * <option value="1grauincomp">1o grau incompleto</option>
		 *
		 * value: "1grauincomp"; visible: "1o grau incompleto"
		 */

		// seleção por índice: 0 -> natacao -> Natação
		escolaridadeComboSelect.selectByIndex(0);
		
		//por value: futebol -> 1 -> futebol -> Futebol
		escolaridadeComboSelect.selectByValue("futebol");
		
		//por texto apresentado - já selecionado 1 -> futebol -> Futebol
		escolaridadeComboSelect.selectByVisibleText("Futebol");
		
		

		
		//var selectedEscolaridadel = escolaridadeComboSelect.getAllSelectedOptions();

		Assert.assertTrue(escolaridadeComboSelect.getOptions().get(0).isSelected());
		Assert.assertTrue(escolaridadeComboSelect.getOptions().get(1).isSelected());
		Assert.assertFalse(escolaridadeComboSelect.getOptions().get(2).isSelected());
		Assert.assertFalse(escolaridadeComboSelect.getOptions().get(3).isSelected());
		Assert.assertFalse(escolaridadeComboSelect.getOptions().get(4).isSelected());
		
		escolaridadeComboSelect.deselectByIndex(0);
		
		Assert.assertFalse(escolaridadeComboSelect.getOptions().get(0).isSelected());
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
