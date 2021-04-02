package gtmetrix;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import java.util.concurrent.TimeUnit;

public class gtmetrix {
    @Test
    public void run() {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        //hit the url
        driver.get("https://gtmetrix.com/");
        //input value to generate report
        driver.findElement(By.name("url")).sendKeys("https://www.tothenew.com/");
        driver.findElement(By.xpath("//div[2]/button")).click();
        WebDriverWait wait = new WebDriverWait(driver,60);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//main/article/div[1]/div[2]/h1")));
        String actual = "Latest Performance Report for:";
        String report = driver.findElement(By.xpath(("//main/article/div[1]/div[2]/h1"))).getText();
        SoftAssert a = new SoftAssert();
        a.assertEquals(actual, report);
        System.out.println("Report Loaded");
        //Grade
        WebElement grade = driver.findElement(By.xpath("//div[2]/div[1]/div/div[1]/i"));
        String grade_text = grade.getAttribute("class");
        String[] grade_value = grade_text.split("-");
        System.out.println("Grade is " +grade_value[2]);
        //Percentage
        WebElement percentage = driver.findElement(By.xpath("//div[2]/div[1]/div/div[2]/span/span"));
        String percentage_value = percentage.getText();
        System.out.println("Percentage is " +percentage_value);
        //Converting grade to percentage
        System.out.println("Converting Grades to Percentage");
        String grade_is = grade_value[2];
        char ch = grade_is.charAt(0);
        if(ch =='A'){
            System.out.println("Percentage is greater than or equal to 90% ");
        }
        else if (ch == 'B')
        {
            System.out.println("Percentage is between 80-89 %");
        }
        else if(ch=='C'){
            System.out.println("Percentage is between 60-79 % ");
        }
        else if(ch=='D'){
            System.out.println("Percentage is between 40-59 % ");
        }
        else {
            System.out.println("Percentage is less than 40% ");
        }

        //Converting % to grade
        String [] percent = percentage_value.split("%");
        String p = percent[0];
        int i = Integer.parseInt(p);
        System.out.println("Converting % to Grade");
        if(i >= 90 ){
            System.out.println("GRADE : A");
        }
        else if(i >= 80 && i<= 89 ){
            System.out.println("GRADE : B");
        }
        else if(i >= 60 && i<= 79 ){
            System.out.println("GRADE : C");
        }
        else if(i >= 40 && i<= 59 ){
            System.out.println("GRADE : D");
        }
        else if(i <= 39){
            System.out.println("GRADE : E");
        }
        a.assertAll();
        driver.close();


    }
}

