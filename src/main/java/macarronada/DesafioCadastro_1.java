package macarronada;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

public class DesafioCadastro_1 {
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
	public void cadastro() {
		// WebElement vasc;

		/**
		 * 
		 * OBS: Ao colar o texto da página HTML para comparação caracteres de tabulação
		 * presentes atrapalham o assert; por isso, pré-processamos o texto em nossos
		 * builds.
		 */
		String cadastro = "Cadastrado!\n" + "Nome: Marcelo\n" + "Sobrenome: de Oliveira\n" + "Sexo: Masculino\n"
				+ "Comida: Carne Frango Pizza\n"
				+ "Escolaridade: 2graucomp\r\n"
				+ "Esportes: Natacao Futebol\r\n"
				+ "Sugestoes: Por meta charset=\"UTF-8\" para evitar problemas de codificacao!"
		;

		cadastro = cadastro.trim();

		webdriver.findElement(By.id("elementosForm:nome")).sendKeys("Marcelo");
		webdriver.findElement(By.id("elementosForm:sobrenome")).sendKeys("de Oliveira");
		webdriver.findElement(By.id("elementosForm:sexo:0")).click();

		// comidas favoritas
		List<WebElement> comidas = webdriver.findElements(By.tagName("input"));
		comidas.removeIf(p -> !((p.getAttribute("type").equals("checkbox")
				&& (p.getAttribute("id").contains("elementosForm:comidaFavorita")))));

		comidas.stream().filter(p -> p.getAttribute("value").equals("carne") || p.getAttribute("value").equals("frango")
				|| p.getAttribute("value").equals("pizza")
		).forEach(p -> p.click());
		;
		
		//escolaridade
		Select escolaridadeComboSelect = new Select(webdriver.findElement(By.id("elementosForm:escolaridade")));

		escolaridadeComboSelect.selectByValue("2graucomp");
		
		Assert.assertTrue(escolaridadeComboSelect.getAllSelectedOptions().size() == 1);
		Assert.assertEquals(escolaridadeComboSelect.getFirstSelectedOption().getText(),
				"2o grau completo");

		
		//esportes
		Select esportesComboSelect = new Select(webdriver.findElement(By.id("elementosForm:esportes")));

		esportesComboSelect.selectByValue("natacao");
		esportesComboSelect.selectByVisibleText("Futebol");
		
		Assert.assertTrue(esportesComboSelect.getAllSelectedOptions().size() == 2);
		
//		Assert.assertEquals(esportesComboSelect.getFirstSelectedOption().getText(),
//				"2o grau completo");
		

		//sugestões:
		webdriver.findElement(By.id("elementosForm:sugestoes")).sendKeys("Por meta charset=\"UTF-8\" para evitar problemas de codificacao!");
		
		
		// finalizar cadastro
		var button = webdriver.findElements(By.id("elementosForm:cadastrar")).stream()
				.filter(e -> e.getAttribute("type").equals("button"))
				.filter(e -> e.getAttribute("value").toString().equals("Cadastrar")).findFirst().get();

		button.click();
		System.out.println(webdriver.findElement(By.id("resultado")).getText());
		System.out.println(cadastro);
		Assert.assertTrue(webdriver.findElement(By.id("resultado")).getText().contains(cadastro));

	}

}
