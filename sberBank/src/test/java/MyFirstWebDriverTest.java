import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.awt.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import static org.junit.Assert.assertTrue;
import static org.openqa.selenium.By.id;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

/**
 * Created by max on 21.09.16.
 */
public class MyFirstWebDriverTest {
    private List<WebElement> webElementList = new ArrayList<WebElement>();
    private WebElement webElement;
    private ChromeOptions options = new ChromeOptions();
    private ChromeDriver driver = new ChromeDriver(options);
    private WebDriverWait wait = new WebDriverWait(driver, 10);

    // Проверка кода страницы
    public  void openIfLinkExists(ChromeDriver driver, String URLName) throws LinkDoesNotExistException {
        try {
            HttpURLConnection.setFollowRedirects(false);
            HttpURLConnection conn = (HttpURLConnection) new URL(URLName).openConnection();
            conn.setRequestMethod("HEAD");
            if(conn.getResponseCode() == HttpURLConnection.HTTP_OK ||  conn.getResponseCode() == HttpURLConnection.HTTP_MOVED_TEMP) {
                driver.get(URLName);
            } else {
                throw new LinkDoesNotExistException();
            }
        } catch (Exception e) {
            throw new LinkDoesNotExistException();
        }
    }

    public void openLink(ChromeDriver driver, String string) throws LinkDoesNotExistException{
        try{
            openIfLinkExists(this.driver, string);
        }
        catch( LinkDoesNotExistException e){
            e.linkMessage();
        }
    }
    // Переход на страницу яндекс маркета и выбор пункта Компьютеры
    public void goToYandexMarketComputers()  throws LinkDoesNotExistException {
        driver.manage().window().maximize();
        openLink(driver , "http://www.yandex.ru");

        webElement = wait.until(visibilityOfElementLocated(By.cssSelector("body > div.container.rows > " +
                "div.row.rows__row.rows__row_main > div > div.container.container__search.container__line > div > " +
                "div.col.col_home-arrow > div > div.home-arrow__tabs > div > a:nth-child(2)")));
        webElement.click();
        wait.until(visibilityOfElementLocated(By.cssSelector("body > div.main > div.topmenu.i-bem.topmenu_js_inited >" +
                "noindex > ul > li:nth-child(2)"))).click();
    }

    // Выбор раздела Ноутбуки
    public void pickNotebooks() {
        wait.until(visibilityOfElementLocated(By.cssSelector("body > div.main > div.layout-grid.layout.layout_type_maya > " +
                "div.layout-grid__col.layout-grid__col_width_2 > div > div:nth-child(1) > div > a:nth-child(2)"))).click();
    }
    // выбор раздела Планшеты
    public void pickTablet() {
        wait.until(visibilityOfElementLocated(By.cssSelector("body > div.main > div.layout-grid.layout.layout_type_maya >" +
                "div.layout-grid__col.layout-grid__col_width_2 > div > div:nth-child(1) > div > a:nth-child(1)"))).click();
    }
    // выбор раздела Расширенный поиск
    public void ultimateSearch() {
        wait.until(visibilityOfElementLocated(By.xpath("/html/body/div[3]/table/tbody/tr/td[2]/table/tbody/tr/td[1]/div/form/table/tbody/tr[6]/td/a"))).click();
    }
    // указываем цену левой границы
    public void setLeftBorderPrice(String priceLeft) {
        webElement = wait.until(visibilityOfElementLocated(By.cssSelector("#glf-pricefrom-var")));
        webElement.sendKeys(priceLeft);
    }
    // указываем цену правой границы
    public void setRightBorderPrice(String priceRight) {
        webElement = wait.until(visibilityOfElementLocated(By.cssSelector("#glf-priceto-var")));
        webElement.sendKeys(priceRight);
    }
    // Нажимаем кнопку применить
    public void acceptConditions() {
        wait.until(visibilityOfElementLocated(By.className("button_action_n-filter-apply"))).click();
    }
    //Нажимаем кнопку Еще
    public void moreCompaniesButton() throws InterruptedException, AWTException {
        wait.until(visibilityOfElementLocated(By.className("button-vendors__others"))).click();
    }
    // Cравниваем количество элементов на странице с переданным числом
    public void checkElementsOnPage(int counts) {
        webElementList = driver.findElements(By.className("snippet-card__header-text"));
        assert webElementList.size() == counts;
    }
    // Вводим первый элемент в поисковую строку
    public void searchFirstElementInSearchLine(String firstElement) throws InterruptedException {
        Thread.sleep(2000);
        webElement = driver.findElement(By.cssSelector("#header-search"));
        webElement.sendKeys(firstElement);
        webElement.submit();
        webElement = wait.until(visibilityOfElementLocated(By.cssSelector("body > div.main > div:nth-child(3) > div.n-product-summary.i-bem.n-product-summary_js_inited > div.n-product-summary__content > div.n-product-summary__headline > div > h1")));
        assert webElement.getText().equals(firstElement);
    }
    // Выполнение первого теста
    @Test
    public void firstScenario() throws InterruptedException, LinkDoesNotExistException {
        openLink(driver , "http://www.yandex.ru");
        goToYandexMarketComputers();
        pickNotebooks();
        ultimateSearch();
        setRightBorderPrice("30000");
        driver.findElementByCssSelector("#glf-1801946-1870091").click();
        driver.findElementByCssSelector("#glf-1801946-1871127").click();
        Thread.sleep(5000);
        acceptConditions();
        checkElementsOnPage(12);
        searchFirstElementInSearchLine(webElementList.get(0).getText());
        driver.quit();
    }
    // Выполнение второго теста
    @Test
    public void secondScenario() throws InterruptedException, AWTException, LinkDoesNotExistException {
        goToYandexMarketComputers();
        pickTablet();
        ultimateSearch();
        moreCompaniesButton();
        setLeftBorderPrice("20000");
        Thread.sleep(4000);
        setRightBorderPrice("25000");
        Thread.sleep(4000);
        driver.findElementByCssSelector("#glf-1801946-3598551").click();
        driver.findElementByCssSelector("#glf-1801946-1871523").click();
        Thread.sleep(2000);
        acceptConditions();
        checkElementsOnPage(12);
        searchFirstElementInSearchLine(webElementList.get(0).getText());
        driver.quit();
    }
}







