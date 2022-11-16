package br.dev.marcelodeoliveira;

import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class DSL {

	private WebDriver driver;

	public DSL(WebDriver driver) {
		this.driver = driver;
	}

	/**
	 * Métodos para otimização semântica, por tipo de elemento e conteúdo
	 */

	/********* Buscas ************/

	List<WebElement> buscaPorAtributo(By atributo) {

		return driver.findElements(atributo);

	}

	WebElement buscaPorAtributo_primeiro(By atributo) {

		try {
			return buscaPorAtributo(atributo).get(0);
		} catch (IndexOutOfBoundsException ioobe) {
			System.out.println(ioobe.getLocalizedMessage());
			return null;
		}

	}

	WebElement buscaPorId(String id) {
		return driver.findElement(By.id(id));
	}

	/********* TextField e TextArea ************/

	public void escreveTexto(String id, String text) {
		buscaPorId(id).sendKeys("text");
	}

	public void escreveTexto(WebElement w, String text) {
		w.sendKeys(text);
	}

	/********* Radio Buttons ************/

	// apenas clica no elemento, independentemente da reversibilidade
	// ou idempotência da opetação.


	public boolean isCheckSelecionado(String id) {

		return buscaPorId(id).isSelected();
	}

	public boolean IsRadioSelecionado(String id) {
		return buscaPorId(id).isSelected();
	}

	/********* Alternadores ************/

	public Alert alternaParaAlert() {
		return driver.switchTo().alert();
	}

	public WebDriver alternaParaFrame(String idOrValue) {
		return driver.switchTo().frame(idOrValue);
	}

	public WebDriver alternaParaFrame(int idOrValue) {
		return driver.switchTo().frame(String.valueOf(idOrValue));
	}

	public void alternaParaWindow(String idOrValue) {
		driver.switchTo().window(idOrValue);
	}

	// ************** Combo (Select) **************
	public void clicaCombo() {

	}

	public WebElement obtemOptionDoCombo(String id) {

		return buscaPorAtributo(By.tagName("option")).stream().filter(p -> p.getAttribute("id").equals(id)).findFirst()
				.get();

	}

}
