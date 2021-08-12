package EjercicioPractico;

import com.github.javafaker.Faker;
import javafx.scene.layout.Priority;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;

public class prueba_mailchimp {
    public WebDriver driver;

    @BeforeMethod
    public void abrirpagina() {
        System.setProperty("webdriver.chrome.driver", "drivers/chromedriver.exe");
        driver = new ChromeDriver();
        driver.get("https://login.mailchimp.com/");
    }

    @Test (priority=5)
    public void validarTituloTest() throws InterruptedException {
        Thread.sleep(2000);
        driver.findElement(By.xpath("//*[@id=\"onetrust-close-btn-container\"]//button[@aria-label='Close']")).click();
        Assert.assertEquals(driver.getTitle(), "Login | Mailchimp");
    }


    @Test (priority=4)
    public void iniciarSesionPageTest() throws InterruptedException {
        Thread.sleep(2000);
        driver.findElement(By.xpath("//*[@id=\"onetrust-close-btn-container\"]//button[@aria-label='Close']")).click();

        WebElement MensajeIniciarSesion = driver.findElement(By.xpath("//*[@id=\"login-form\"]/div/h1"));
        System.out.println(MensajeIniciarSesion.getText());
        Assert.assertEquals(MensajeIniciarSesion.getText(), "Log In");

        WebElement Mensaje = driver.findElement(By.xpath("//*[@id=\"login-form\"]/div/p/span"));
        System.out.println(Mensaje.getText());
        Assert.assertEquals(Mensaje.getText(), "Need a Mailchimp account?");
    }

    @Test (priority=3)
    public void loginErrorTest() throws InterruptedException {

        Thread.sleep(2000);
        driver.findElement(By.xpath("//*[@id=\"onetrust-close-btn-container\"]//button[@aria-label='Close']")).click();

        driver.findElement(By.id("username")).sendKeys("XXXXX@gmail.com");
        driver.findElement(By.xpath("//button[@type='submit' and @value='log in']")).click();


        WebElement errorMensaje = driver.findElement(By.xpath("//*[@id=\"empty-error\"]/div/div/div[2]/p"));
        System.out.println(errorMensaje.getText());
        Assert.assertEquals(errorMensaje.getText(), "Looks like you forgot your password there, XXXXX@gmail.com.");

    }


    @Test (priority=2)
    public void fakeEmailTest() throws InterruptedException {
        Faker faker = new Faker();

        driver.navigate().to("https://login.mailchimp.com/signup/");
        Thread.sleep(2000);
        driver.findElement(By.xpath("//*[@id=\"onetrust-close-btn-container\"]//button[@aria-label='Close']")).click();
        Thread.sleep(2000);
        driver.findElement(By.id("email")).sendKeys(faker.internet().emailAddress());

        System.out.println("URL --> " + driver.getCurrentUrl());
        Assert.assertEquals(driver.getCurrentUrl(), "https://login.mailchimp.com/signup/");

    }

    @DataProvider(name = "Login")
    public Object[][] datosLogin() {
        return new Object[][]{
                {"12349@gmail.com", "holamundo"},
                {"xxxxxa@hotmail.com", "holamundo"},
                {"abcdefg@gmail.com", "holamundo"}

        };
    }

    @Test  (dataProvider = "Login")
    public void dataProviderEmailTest(String unEmail, String unPassword) throws InterruptedException {
        Thread.sleep(2000);
        driver.findElement(By.xpath("//*[@id=\"onetrust-close-btn-container\"]//button[@aria-label='Close']")).click();

        driver.findElement(By.id("username")).sendKeys(unEmail);
        driver.findElement(By.id("password")).sendKeys(unPassword);

        driver.findElement(By.xpath("//button[@type='submit' and @value='log in']")).click();
        //Thread.sleep(2000);
        //WebElement H2UseCustomDomainElement = driver.findElement(By.xpath("//*[@id=\"login-form\"]/fieldset/div[1]/div/div/div[2]/p"));
        //System.out.println(H2UseCustomDomainElement.getText());
        //Assert.assertEquals(H2UseCustomDomainElement.getText(),"Can we help you recover your username?");


    }

    @AfterMethod
    public void closeDriver() {
        driver.close();
    }
}

