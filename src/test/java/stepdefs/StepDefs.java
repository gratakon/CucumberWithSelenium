package stepdefs;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import junit.framework.Assert;
import po.PO_Login;
import po.PO_TransferFunds;

public class StepDefs {
	
	WebDriver driver;
	PO_Login oPO_Login;
	PO_TransferFunds oPO_TransferFunds;
	Scenario scn;
	
	//Open New Account Number variable
	String NEW_ACCOUNT_NUMBER;
	String ORIGINAL_ACC_AMMOUNT;


	//Hooks
	@Before
	public void BeforeMethod(Scenario s) {
		this.scn = s;
	}
	
	@After
	public void AfterMethod(Scenario s) {
		driver.quit();
		scn.write("Driver is closed");
	}
	
	@Given("Login in to Parabank url as {string} username as {string} passowrd as {string}")
	public void login_in_to_parabank(String url, String u, String p) {
		browser_is_invoked();
		navigate_to_URL(url);
		i_enter_username_as(u);
		i_enter_password_as(p);
		i_click_on_submit_button();
	}

	@Given("Browser is  invoked")
	public void browser_is_invoked() {
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		oPO_Login = PageFactory.initElements(driver, PO_Login.class);
		
		scn.write("Browser Invoked and maximized");
	}

	@Given("navigate to URL {string}")
	public void navigate_to_URL(String arg) {
		driver.get(arg);
		scn.write("Browser navigated to url: " + arg);
	}

	@When("I enter username as {string}")
	public void i_enter_username_as(String arg) {
		oPO_Login.EnterUserName(arg);
		scn.write("Username text field set as : " + arg);
	}

	@When("I enter password as {string}")
	public void i_enter_password_as(String arg) {
		oPO_Login.EnterPassword(arg);
		scn.write("password text field set as : " + arg);
	}

	@When("I click on submit button")
	public void i_click_on_submit_button() {
		oPO_Login.ClickSubmitButton();
		scn.write("Submit button clicked");
	}
	
	@When("I click on {string}")
	public void i_click_on(String arg) {
		oPO_TransferFunds = PageFactory.initElements(driver, PO_TransferFunds.class);
	
		scn.write("Clicked on Menu Link: " + arg);
	}


	@Then("Page title should come {string}")
	public void i_page_title_should_come(String string) {
		WebDriverWait wait = new WebDriverWait(driver,20);
		wait.until(ExpectedConditions.titleIs(string));
		
		
		String actual = driver.getTitle();
		String expected  = string;
		Assert.assertEquals(expected, actual);
		scn.write("User successfully logged in. Page Title coming as: " + string);
	}

	//*********************************************
	//*******************Transfer Funds************
	//*********************************************
	@When("I select account {string} in from account field")
	public void i_select_account_in_from_account_field(String arg) {

		WebElement element = driver.findElement(By.id("fromAccountId"));
		Select select  = new Select(element);
		select.selectByVisibleText(arg);
		scn.write("From account field selected with value as : " + arg);
	}

	@When("I select account {string} in to account field")
	public void i_select_account_in_to_account_field(String arg) {
		WebElement element = driver.findElement(By.id("toAccountId"));
		Select select  = new Select(element);
		select.selectByVisibleText(arg);
		scn.write("To account field selected with value as : " + arg);

	}

	@When("I enter amount as {string} in amount field")
	public void i_enter_amount_as_in_amount_field(String string) {
		driver.findElement(By.id("amount")).sendKeys(string);;
		scn.write("Entered amount as :" + string);

	}

	@When("I click on transfer funds button")
	public void i_click_on_transfer_funds_button() {
		driver.findElement(By.xpath("//input[@value='Transfer']")).click();
		scn.write("Tranfer funds button clicked");
	}

	@Then("Transfer is sucessfull message should come")
	public void transfer_is_sucessfull_message_should_come() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@Then("verify amount is transfered from account {string} to account {string}")
	public void verify_amount_is_transfered_from_account_to_account(String string, String string2) {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}
	
	//**************************Open New Account Steps***************
	
	@Given("Fetch Balance of Account {string}")
	public void fetch_balance_of_account(String acc_number) {
		
		WebElement e = driver.findElement(By.xpath("//a[text()='"+acc_number+"']/parent::td/following-sibling::td[1]"));
		ORIGINAL_ACC_AMMOUNT= e.getText();
		scn.write("Original Account number is : " + acc_number + " and ammount before transaction is : " + ORIGINAL_ACC_AMMOUNT );
	}
	
	@When("I select account type as {string}")
	public void i_select_account_type_as(String string) {
		WebElement e = driver.findElement(By.id("type"));
		Select list = new Select(e);
		list.selectByVisibleText(string);
		scn.write("Type selected as : " + string);
	}

	@When("I select the account number as {string}")
	public void i_select_the_account_number_as(String string) {
		WebElement e = driver.findElement(By.id("fromAccountId"));
		Select list = new Select(e);
		list.selectByVisibleText(string);
		scn.write("Account Number selected as : " + string);
	}

	@When("I click on Open New Account Button")
	public void i_click_on_Open_New_Account_Button() {
		driver.findElement(By.xpath("//input[@value='Open New Account']")).click();
		scn.write("Open New Account Button is Clicked");
	}
	
	@Then("Account Opened  message should come")
	public void account_Opened_message_should_come() {

		WebElement e = driver.findElement(By.xpath("//div[@ng-if='showResult' and @class='ng-scope']"));
		String actual_text = e.getText();
		if (actual_text.contains("Account Opened!")) {
			scn.write("AccountOpened Message is coming");
			Assert.assertTrue(true);
		}else {
			scn.write("AccountOpened Message is not coming");
			Assert.assertTrue(false);
		}
		
		if (actual_text.contains("Congratulations, your account is now open.")) {
			scn.write("Congratulations, your account is now open. Message is coming");
			Assert.assertTrue(true);
		}else {
			scn.write("Congratulations, your account is now open. Message is not coming");
			Assert.assertTrue(false);
		}
		
	}

	@Then("New Account Number is created")
	public void new_Account_Number_is_created() {
		WebElement e = driver.findElement(By.id("newAccountId"));
		NEW_ACCOUNT_NUMBER = e.getText();
		scn.write("New Account number created. Account no.: " + NEW_ACCOUNT_NUMBER);
	}

	@Then("New Account is displayed under account Overview table")
	public void new_Account_is_displayed_under_account_Overview_table() {
		//Click on Account Overview Link
		i_click_on("Accounts Overview");
		WebElement e = driver.findElement(By.xpath("//table[@id='accountTable']//a[text()='"+NEW_ACCOUNT_NUMBER+"']"));
		Assert.assertEquals(true, e.isDisplayed());
		scn.write("New Account Number link is displayed");

	}

	@Then("New Account should have ammount as {string}")
	public void new_Account_should_have_ammount_as(String string) {
		String xpath = "//table[@id='accountTable']//a[text()='"+NEW_ACCOUNT_NUMBER+"']/parent::td/following-sibling::td[1]";
		WebElement e = driver.findElement(By.xpath(xpath));
		String actual = e.getText();
		String expected = string;
		Assert.assertEquals(expected, actual);
		scn.write("New Account displays amount as : " + string);
	}

	@Then("New Account's activity table displayes Funds Transfer Received message")
	public void new_Account_s_activity_table_displayes_Funds_Transfer_Received_message() {
		WebElement e = driver.findElement(By.xpath("//table[@id='transactionTable']//a[text()='Funds Transfer Received']"));
		Assert.assertEquals(true, e.isDisplayed());
		scn.write("Transaction table displayed with the message");
	}

	@Then("Original Account ammount should get deducted by {string} from account {string}")
	public void original_Account_ammount_should_get_deducted_by(String exp_amount, String acc) {
		WebElement e = driver.findElement(By.xpath("//a[text()='"+acc+"']/parent::td/following-sibling::td[1]"));
		String deducted_ammount = e.getText().replace("$", "");
		ORIGINAL_ACC_AMMOUNT = ORIGINAL_ACC_AMMOUNT.replace("$", "");
		int temp = Integer.parseInt(ORIGINAL_ACC_AMMOUNT);
		int temp1 = Integer.parseInt(deducted_ammount);
		int result = temp-temp1;
		Assert.assertEquals(exp_amount, result);
	
	}



	
}
