package flipkart;

import java.awt.AWTException;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import io.github.bonigarcia.wdm.WebDriverManager;

public class AddToCart {
	public static void main(String[] args) throws AWTException, InterruptedException {

		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver();
		driver.get("https://www.flipkart.com/");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		// Login Flipkart
		driver.findElement(By.xpath("//input[@class='_2IX_2- VJZDxU']")).sendKeys("8807530080");
		;
		WebElement password = driver.findElement(By.xpath("//input[@class='_2IX_2- _3mctLh VJZDxU']"));
		password.click();
		password.sendKeys("singh1508");
		driver.findElement(By.xpath("//button[@class='_2KpZ6l _2HKlqd _3AWRsL']")).click();

		// Hp laptop
		WebElement searchBox = driver.findElement(By.xpath("//input[@name='q']"));
		searchBox.sendKeys("HP laptop");
		searchBox.sendKeys(Keys.ENTER);
		WebElement product = driver.findElement(By.xpath("(//div[@class='_2kHMtA'])[1]//a[@class='_1fQZEK']"));
		try {
			product.click();
		} catch (StaleElementReferenceException e) {
			product = driver.findElement(By.xpath("(//div[@class='_2kHMtA'])[1]//a"));
			product.click();
		}

		// Handling ChildWindow
		String parentId = driver.getWindowHandle();
		Set<String> allIds = driver.getWindowHandles();
		for (String eachId : allIds) {
			if (!parentId.equals(eachId)) {
				driver.switchTo().window(eachId);
			}
		}

		// Adding hp to cart
		driver.findElement(By.xpath("//button[@class='_2KpZ6l _2U9uOA _3v1-ww']")).click();
		driver.close();

		// adding any mob to cart
		driver.switchTo().window(parentId);
		driver.navigate().refresh();
		WebElement txtsearchBox = driver.findElement(By.xpath("//input[@name='q']"));
		txtsearchBox.clear();
		txtsearchBox.sendKeys("iphone 13");
		txtsearchBox.sendKeys(Keys.ENTER);
		WebElement mob = driver.findElement(By.xpath("(//div[@class='_2kHMtA'])[1]//a"));
		try {
			mob.click();
		} catch (StaleElementReferenceException e) {
			mob = driver.findElement(By.xpath("(//div[@class='_2kHMtA'])[1]//a"));
			mob.click();
		}
		// Handling ChildWindow
		String curentId = driver.getWindowHandle();
		Set<String> allId = driver.getWindowHandles();
		for (String childId : allId) {
			if (!curentId.equals(childId)) {
				driver.switchTo().window(childId);
			}
		}

		// Adding mob to cart
		driver.findElement(By.xpath("//button[@class='_2KpZ6l _2U9uOA _3v1-ww']")).click();

		// remove hp from cart
		WebElement removeBtn = driver.findElement(By.xpath("(//div[text()='Remove'])[2]"));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView(true)", removeBtn);
		removeBtn.click();
		driver.findElement(By.xpath("(//div[text()='Remove'])[1]")).click();

		driver.close();
		driver.switchTo().window(curentId);

		// Logout
		WebElement myacc = driver.findElement(By.xpath("//div[text()='My Account']"));
		Actions act = new Actions(driver);
		act.moveToElement(myacc).perform();
		driver.findElement(By.xpath("(//li[@class='_2NOVgj'])[10]")).click();

	}
}
