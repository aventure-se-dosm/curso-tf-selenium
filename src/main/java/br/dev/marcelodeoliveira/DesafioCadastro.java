package br.dev.marcelodeoliveira;

import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

public class DesafioCadastro {
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
	public void cadastro() {

		String cadastro = "Cadastrado!\n" + "Nome: Marcelo\n" + "Sobrenome: de Oliveira\n" + "Sexo: Masculino\n"
				+ "Comida: Carne Frango Pizza\n" + "Escolaridade: 2graucomp\n" + "Esportes: Natacao Futebol\n"
				+ "Sugestoes: Por meta charset=\"UTF-8\" para evitar problemas de codificacao!";

		cadastro = cadastro.trim();

		webdriver.findElement(By.id("elementosForm:nome")).sendKeys("Marcelo");
		webdriver.findElement(By.id("elementosForm:sobrenome")).sendKeys("de Oliveira");
		webdriver.findElement(By.id("elementosForm:sexo:0")).click();

		List<WebElement> comidas = webdriver.findElements(By.tagName("input"));
		comidas.removeIf(p -> !((p.getAttribute("type").equals("checkbox")
				&& (p.getAttribute("id").contains("elementosForm:comidaFavorita")))));

		comidas.stream().filter(p -> p.getAttribute("value").equals("carne") || p.getAttribute("value").equals("frango")
				|| p.getAttribute("value").equals("pizza")).forEach(p -> p.click());
		;

		// escolaridade
		Select escolaridadeComboSelect = new Select(webdriver.findElement(By.id("elementosForm:escolaridade")));

		escolaridadeComboSelect.selectByValue("2graucomp");

		Assert.assertTrue(escolaridadeComboSelect.getAllSelectedOptions().size() == 1);
		Assert.assertEquals(escolaridadeComboSelect.getFirstSelectedOption().getText(), "2o grau completo");

		// esportes
		Select esportesComboSelect = new Select(webdriver.findElement(By.id("elementosForm:esportes")));

		esportesComboSelect.selectByValue("natacao");
		esportesComboSelect.selectByVisibleText("Futebol");

		Assert.assertTrue(esportesComboSelect.getAllSelectedOptions().size() == 2);

		// sugestões:
		webdriver.findElement(By.id("elementosForm:sugestoes"))
				.sendKeys("Por meta charset=\"UTF-8\" para evitar problemas de codificacao!");

		// finalizar cadastro
		cadastrar();

		System.out.println(webdriver.findElement(By.id("resultado")).getText());
		System.out.println(cadastro);
		Assert.assertEquals(webdriver.findElement(By.id("resultado")).getText(), (cadastro));

	}

	private void cadastrar() {

		webdriver.findElements(By.id("elementosForm:cadastrar")).stream()
				.filter(e -> e.getAttribute("type").equals("button"))
				.filter(e -> e.getAttribute("value").toString().equals("Cadastrar")).findFirst().get().click();
	}

	@Test
//	@Ignore
	public void nomeObrigatorioAusenteTest() {

		cadastrar();

		Assert.assertEquals(("Nome eh obrigatorio"), webdriver.switchTo().alert().getText());

	};

	@Test
	@Ignore
	public void SobrenomeomeObrigatorioAusenteTest() {

		webdriver.findElement(By.id("elementosForm:nome")).sendKeys("Marcelo");

		cadastrar();

		Assert.assertEquals(("Sobrenome eh obrigatorio"), webdriver.switchTo().alert().getText());
	};

	@Test
	public void VegetarianoCoerenteTest() {

		webdriver.findElement(By.id("elementosForm:nome")).sendKeys("Marcelo");
		webdriver.findElement(By.id("elementosForm:sobrenome")).sendKeys("de Oliveira");
		webdriver.findElement(By.id("elementosForm:sexo:0")).click();

		List<WebElement> comidas = webdriver.findElements(By.tagName("input"));
		comidas.removeIf(p -> !((p.getAttribute("type").equals("checkbox")
				&& (p.getAttribute("id").contains("elementosForm:comidaFavorita")))));

		comidas.forEach(p -> p.click());

		cadastrar();

		var alert = webdriver.switchTo().alert();
		var texto = alert.getText();

		Assert.assertEquals(("Tem certeza que voce eh vegetariano?"), texto);
		alert.accept();

		// desseleciona carne
		comidas.stream().filter(p -> p.getAttribute("value").equals("carne")).findFirst().get().click();

		cadastrar();
		Assert.assertEquals(("Tem certeza que voce eh vegetariano?"), alert.getText());
		alert.accept();

		// desseleciona frango, mas seleciona caene
		comidas.stream().filter(p -> p.getAttribute("value").equals("frango")).findFirst().get().click();
		comidas.stream().filter(p -> p.getAttribute("value").equals("carne")).findFirst().get().click();

		cadastrar();
		Assert.assertEquals(("Tem certeza que voce eh vegetariano?"), alert.getText());
		alert.accept();

		// desseleciona pizza
		comidas.stream().filter(p -> p.getAttribute("value").equals("pizza")).findFirst().get().click();

		cadastrar();
		Assert.assertEquals(("Tem certeza que voce eh vegetariano?"), alert.getText());
		alert.accept();

		Assert.assertThrows(NoAlertPresentException.class, () -> alert.accept());
		// webdriver.switchTo().alert().;
	};

	private void quitWebDrivers() {
//	webdrivers.forEach(wd -> {
//		wd.wait(10000L);
//		wd.quit();
//	});
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
