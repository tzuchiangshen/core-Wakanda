package dataBrowser;

import static org.junit.Assert.*;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;


public class TestCasesDataBrowser extends Template {

@Override
public void maximizeWindow() {}
	
	
	public TestCasesDataBrowser(eBrowser browserName, String soluString,
			boolean launchServerOnlyOneTime) {
		super(browserName, soluString, launchServerOnlyOneTime);
	}
	 
	
	
	@Test 
	public void testLeftColumnDataStoreClass_exist() throws InterruptedException {
		
		fDriver.get("http://127.0.0.1:8081/walib/dataBrowser/index.html");
		Thread.sleep(500);

		long end = System.currentTimeMillis() + 5000;
		while (System.currentTimeMillis() < end) {
			Thread.sleep(100);
			try{
		fDriver.findElement(By.xpath("//*[@id='dataClassContent']"));
		assertEquals("Right Column is : ", "true", "true");
			}catch(Exception e){}
        //False
		}
		
		String result = fDriver.findElement(By.xpath("//*[@id='dataClassContentTable']/thead/tr/th")).getText(); 
		
		assertEquals("Solution Name is disabled : ", "Datastore Classes", result);
		
	}
	
	@Test
	public void testDataClassName_exist() throws InterruptedException {
		
		fDriver.get("http://127.0.0.1:8081/walib/dataBrowser/index.html");
		Thread.sleep(500);
		
		String[] DataClassNameArray = {"Compagny","Persons"};
		
		for(int x = 1; x < DataClassNameArray.length ; x++){
	        
			String xpathName = "//*[@id='outline_"+ DataClassNameArray[x] +"']/td/div/div[2]";

			Thread.sleep(500);
			String resultAtt = fDriver.findElement(By.xpath(xpathName)).getAttribute("innerHTML");
			
			assertEquals("DataclassName is : ", DataClassNameArray[x], resultAtt);
			
	    }
		
	}
	
	@Test 
	public void testButtonClose_All_exist() throws InterruptedException {
		
		fDriver.get("http://127.0.0.1:8081/walib/dataBrowser/index.html");
		Thread.sleep(500);
		
		String ButtonClose = fDriver.findElement(By.id("label1")).getText(); 
		
		assertEquals("Button Close is : ", "Close All", ButtonClose);
		
	} 
	
	@Test 
	public void testButtonClose_All_action() throws InterruptedException {
		
		fDriver.get("http://127.0.0.1:8081/walib/dataBrowser/index.html");
		Thread.sleep(500);
		JavascriptExecutor js = (JavascriptExecutor) fDriver;
		
		fDriver.findElement(By.xpath("//*[@id='outline_Persons']")).click();
		Thread.sleep(1000);
		fDriver.findElement(By.id("dataBrowserCloseAll")).click();
		
		Long TabElement = (Long) js.executeScript("return $('ul#menuBarTabView li').length");
		Long TabTrueElement = (long) 1; 
		
		assertEquals("Number of tab is : ", TabTrueElement, TabElement);
		
	} 
	
	@Test 
	public void testButtonExport_exist() throws InterruptedException {
		
		fDriver.get("http://127.0.0.1:8081/walib/dataBrowser/index.html");
		Thread.sleep(500);
		
		String Export = fDriver.findElement(By.id("label2")).getText(); 
		
		assertEquals("Export is : ", "Export", Export);
		
	} 
	
	@Test 
	public void testButtonExport_action() throws InterruptedException {
		
		fDriver.get("http://127.0.0.1:8081/walib/dataBrowser/index.html");
		Thread.sleep(500);
		
		fDriver.findElement(By.id("dataBrowserExportDataClass")).click();
		
		String result = fDriver.findElement(By.id("ui-dialog-title-confirmExportDialog")).getText(); 
		
		assertEquals("Export action is : ","Export dataclass",result);
	
	} 
	
	@Test 
	public void testTabDataClass_exist() throws InterruptedException {
		
		fDriver.get("http://127.0.0.1:8081/walib/dataBrowser/index.html");
		Thread.sleep(500);
		JavascriptExecutor js = (JavascriptExecutor) fDriver;
		
		String[] DataClassNameArray = {"Compagny","Persons"};
		
		for(int x = 1; x < DataClassNameArray.length ; x++) {
	        
			fDriver.findElement(By.xpath("//*[@id='outline_"+ DataClassNameArray[x] +"']/td/div/div[2]")).click(); ;
			
	    }
		
		Long TabElement = (Long) js.executeScript("return $('ul#menuBarTabView li').length");
		
		Long TabTrueElement = (long) 2;
		
		assertEquals("Number of tab is : ",TabTrueElement, TabElement);
		
	}
	
	
	@Test 
	public void testQuery_exist() throws InterruptedException {
		
		fDriver.get("http://127.0.0.1:8081/walib/dataBrowser/index.html");
		Thread.sleep(500);
		
		String result = fDriver.findElement(By.xpath("//*[@id='databrowser_tabview']/div[3]/div/form/label")).getText();
		
		assertEquals("Query input is : ","Query", result);
		
	}
	
		
	@Test 
	public void testGridForEachDataClass_exist() throws InterruptedException {
		
		fDriver.get("http://127.0.0.1:8081/walib/dataBrowser/index.html");
		Thread.sleep(500);
		
		fDriver.findElement(By.xpath("//*[@id='outline_Persons']")).click();
		Thread.sleep(1000);
		
		String[] AttributeNamePersonsArray = {"ID","firstName", "SecondName", "BirthDate"};
		String[] AttributeNameCompagnyArray = {"ID"};
		
		for(int x = 1; x < AttributeNameCompagnyArray.length ; x++) {
			
			String result = fDriver.findElement(By.xpath("//*[@id='databrowser_tabview']div[3]/div/div[1]/div/div["+x+"]")).getText();
		
			assertEquals("The column in the grid is : ", AttributeNameCompagnyArray[x-1], result);
		
		}
	

		for(int x = 1; x < AttributeNamePersonsArray.length ; x++) {
			
			
			String result = fDriver.findElement(By.xpath("//*[@id='databrowser_tabview']/div[4]/div[2]/div/div[1]/div/div["+x+"]/div[1]")).getText();
			
			assertEquals("The column in the grid is : ", AttributeNamePersonsArray[x-1], result);
			
		}
		
	}
	
	@Test 
	public void testDataLoadedForEachGrid() throws InterruptedException {
		
		fDriver.get("http://127.0.0.1:8081/walib/dataBrowser/index.html");
		Thread.sleep(500);
		
		fDriver.findElement(By.xpath("//*[@id='outline_Persons']")).click();
		Thread.sleep(1000);
		
		fDriver.findElement(By.xpath("//*[@id='databrowser_tabview']/div[4]/div/form/input")).sendKeys("ID < 1");
		
		fDriver.findElement(By.xpath("//*[@id='databrowser_tabview']/div[4]/div/form/button[1]")).click();
		
		String[] EntitiesPersonsArray = {"0", "Marc", "Thomas", "02/09/1988"};
		
		for(int x = 1; x < EntitiesPersonsArray.length ;x++) {
			
			String result = fDriver.findElement(By.xpath("//*[@id='databrowser_tabview']/div[4]/div[2]/div/div[2]/div/div[1]/div/div["+x+"]/div")).getText();

			assertEquals("The data in the grid is : ", EntitiesPersonsArray[x-1], result);
		
		}
		
		
	}
	
	@Test 
	public void testAddNewEntitie_exist() throws InterruptedException {
		
		fDriver.get("http://127.0.0.1:8081/walib/dataBrowser/index.html");
		Thread.sleep(500);
	
		String result = fDriver.findElement(By.xpath("//*[@id='databrowser_tabview']/div[3]/div[2]/div/div[3]/div[1]/ul/li[1]")).getAttribute("title");
		
		assertEquals("Add Button is : ", "Add", result);
		
	}
	
	
	
	@Test 
	public void testAddNewEntitie_action() throws InterruptedException {
		
		fDriver.get("http://127.0.0.1:8081/walib/dataBrowser/index.html");
		Thread.sleep(500);
		
		fDriver.findElement(By.xpath("//*[@id='outline_Persons']")).click();
		
		fDriver.findElement(By.xpath("//*[@id='databrowser_tabview']/div[4]/div[2]/div/div[2]/div/div[2]")).click();
		
		fDriver.findElement(By.xpath("//*[@id='databrowser_tabview']/div[4]/div[2]/div/div[3]/div[1]/ul/li[1]/div")).click();

		String[] NewEntitiesArray = {"2", "Arnaud", "Tob" , "12/09/1987"};
			int y = 0;
			
		for(int x = 1; x < NewEntitiesArray.length; x++ ) {

			Thread.sleep(3000);
			fDriver.findElement(By.xpath("//*[@id='databrowser_tabview']/div[4]/div[2]/div[2]/div/div[2]/div/div["+x+"]/div[2]/div/input")).sendKeys(NewEntitiesArray[y]);
			
				y++;
		
		}
		
		fDriver.findElement(By.xpath("//*[@id='databrowser_tabview']/div[4]/div[2]/div[2]/div/div[3]/div/div[1]/ul/li[5]")).click();
		Thread.sleep(500);	
		
		fDriver.findElement(By.xpath("//*[@id='databrowser_tabview']/div[4]/div[2]/div[2]/div/div[3]/div/div[1]/ul/li[5]/div")).click();
		Thread.sleep(5000);
		
		fDriver.get("http://127.0.0.1:8081/walib/dataBrowser/index.html");
		Thread.sleep(500);
		
		fDriver.findElement(By.xpath("//*[@id='outline_Persons']")).click();
			int z = 0;
		
		for(int x = 1; x < NewEntitiesArray.length; x++ ) {
			
			
			String result = fDriver.findElement(By.xpath("//*[@id='databrowser_tabview']/div[4]/div[2]/div/div[2]/div/div[3]/div/div["+x+"]/div")).getText();

			assertEquals("The New data in the grid is : ", NewEntitiesArray[z], result);
			
			z++;
		}
		
		
	}
	
	
	
	@Test 
	public void testRemoveEntitie_exist() throws InterruptedException {
		
		fDriver.get("http://127.0.0.1:8081/walib/dataBrowser/index.html");
		Thread.sleep(500);
	
		String result = fDriver.findElement(By.xpath("//*[@id='databrowser_tabview']/div[3]/div[2]/div/div[3]/div[1]/ul/li[2]")).getAttribute("title");
		
		assertEquals("Delete Button is : ", "Delete", result);
		
		
	}
	
	@Test 
	public void testRemoveEntitie_action() throws InterruptedException {
		
		fDriver.get("http://127.0.0.1:8081/walib/dataBrowser/index.html");
		Thread.sleep(500);
	
		fDriver.findElement(By.xpath("//*[@id='outline_Persons']")).click();
		
		fDriver.findElement(By.xpath("//*[@id='databrowser_tabview']/div[4]/div[2]/div/div[2]/div/div[3]")).click();
		Thread.sleep(500);
		
		fDriver.findElement(By.xpath("//*[@id='databrowser_tabview']/div[4]/div[2]/div/div[3]/div[1]/ul/li[2]/div")).click();
		
		String result = fDriver.findElement(By.xpath("//*[@id='databrowser_tabview']/div[4]/div[2]/div/div[3]/div[2]")).getText(); 
		
		assertEquals("Items has been removed : ", "2 item(s)", result);
		
	}
	

	
	@Test 
	public void testAutoFormForEachDataClass_exist() throws InterruptedException {
		
		fDriver.get("http://127.0.0.1:8081/walib/dataBrowser/index.html");
		Thread.sleep(500);
		
		fDriver.findElement(By.xpath("//*[@id='outline_Persons']")).click();

		fDriver.findElement(By.xpath("//*[@id='databrowser_tabview']/div[4]/div[2]/div/div[2]/div/div[1]")).click();
		Thread.sleep(500);
		
		String result = fDriver.findElement(By.xpath("//*[@id='databrowser_tabview']/div[4]/div[2]/div[2]/div/div[1]/div[2]")).getText(); 
		
		assertEquals("AutoForm for the class "+ result +" is : ", "Persons", result);	
		
	}
	
	@Test 
	public void testAutoFormForEachEntitie_exist() throws InterruptedException {
		
		fDriver.get("http://127.0.0.1:8081/walib/dataBrowser/index.html");
		Thread.sleep(500);
		
		fDriver.findElement(By.xpath("//*[@id='outline_Persons']")).click();

		fDriver.findElement(By.xpath("//*[@id='databrowser_tabview']/div[4]/div[2]/div/div[2]/div/div[1]")).click();
		Thread.sleep(500);
		
		String[] CurrentEntitiesArrayExist = {"0", "Marc", "Thomas", "02/09/1988"};
		int z = 0;
		
		for(int x =1; x < CurrentEntitiesArrayExist.length; x++ ) {
			
			
			String result = fDriver.findElement(By.xpath("//*[@id='databrowser_tabview']/div[4]/div[2]/div[2]/div/div[2]/div/div["+x+"]/div[2]/div/input")).getAttribute("value");

			assertEquals("The current data for the entites selected is : ", CurrentEntitiesArrayExist[z], result);
			
			z++;
		}
		
		
	}
	

	
	@Test 
	public void testAutoForMoveBetweenEntities_previous_exist() throws InterruptedException {
		
		fDriver.get("http://127.0.0.1:8081/walib/dataBrowser/index.html");
		Thread.sleep(500);
		
		fDriver.findElement(By.xpath("//*[@id='outline_Persons']")).click();

		fDriver.findElement(By.xpath("//*[@id='databrowser_tabview']/div[4]/div[2]/div/div[2]/div/div[1]")).click();
		Thread.sleep(500);
		
		String result = fDriver.findElement(By.xpath("//*[@id='databrowser_tabview']/div[4]/div[2]/div[2]/div/div[3]/div/div[1]/ul/li[1]")).getAttribute("title");
		
		assertEquals("Previous Button is : ", "Previous", result);
		
	}
	

	 
	 
	@Test 
	public void testAutoForMoveBetweenEntities_previous_action() throws InterruptedException {
		
		fDriver.get("http://127.0.0.1:8081/walib/dataBrowser/index.html");
		Thread.sleep(500);
		
		fDriver.findElement(By.xpath("//*[@id='outline_Persons']")).click();

		fDriver.findElement(By.xpath("//*[@id='databrowser_tabview']/div[4]/div[2]/div/div[2]/div/div[2]")).click();
		Thread.sleep(500);
		
		fDriver.findElement(By.xpath("//*[@id='databrowser_tabview']/div[4]/div[2]/div[2]/div/div[3]/div/div[1]/ul/li[1]")).click();
		
		String[] PreviousEntitiesArrayExist = {"0", "Marc", "Thomas", "02/09/1988"};
		int z = 0;
		
		for(int x =1; x < PreviousEntitiesArrayExist.length; x++ ) {
			
			
			String result = fDriver.findElement(By.xpath("//*[@id='databrowser_tabview']/div[4]/div[2]/div[2]/div/div[2]/div/div["+x+"]/div[2]/div/input")).getAttribute("value");

			assertEquals("The Previous data for the entites selected is : ", PreviousEntitiesArrayExist[z], result);
			
			z++;
		}
		
		
		
	}
	
	@Test 
	public void testAutoForMoveBetweenEntities_next_exist() throws InterruptedException {
		
		fDriver.get("http://127.0.0.1:8081/walib/dataBrowser/index.html");
		Thread.sleep(500);
		
		fDriver.findElement(By.xpath("//*[@id='outline_Persons']")).click();

		fDriver.findElement(By.xpath("//*[@id='databrowser_tabview']/div[4]/div[2]/div/div[2]/div/div[1]")).click();
		Thread.sleep(500);
		
		String result = fDriver.findElement(By.xpath("//*[@id='databrowser_tabview']/div[4]/div[2]/div[2]/div/div[3]/div/div[1]/ul/li[2]")).getAttribute("title");
													
		assertEquals("Next Button is : ", "Next", result);
		
	}
	
	@Test 
	public void testAutoForMoveBetweenEntities_next_action() throws InterruptedException {
		
		fDriver.get("http://127.0.0.1:8081/walib/dataBrowser/index.html");
		Thread.sleep(500);
		
		fDriver.findElement(By.xpath("//*[@id='outline_Persons']")).click();

		fDriver.findElement(By.xpath("//*[@id='databrowser_tabview']/div[4]/div[2]/div/div[2]/div/div[1]")).click();
		Thread.sleep(500);
		
		fDriver.findElement(By.xpath("//*[@id='databrowser_tabview']/div[4]/div[2]/div[2]/div/div[3]/div/div[1]/ul/li[2]")).click();
		
		String[] PreviousEntitiesArrayExist = {"1", "Thomas", "andrews", "04/05/1985"};
		int z = 0;
		
		for(int x =1; x < PreviousEntitiesArrayExist.length; x++ ) {
			
			
			String result = fDriver.findElement(By.xpath("//*[@id='databrowser_tabview']/div[4]/div[2]/div[2]/div/div[2]/div/div["+x+"]/div[2]/div/input")).getAttribute("value");

			assertEquals("The Next data for the entites selected is : ", PreviousEntitiesArrayExist[z], result);
			
			z++;
		}
		
	}
	
	
	@Test 
	public void testAutoForEntities_add_exist() throws InterruptedException {
		
		fDriver.get("http://127.0.0.1:8081/walib/dataBrowser/index.html");
		Thread.sleep(500);
		
		fDriver.findElement(By.xpath("//*[@id='outline_Persons']")).click();

		fDriver.findElement(By.xpath("//*[@id='databrowser_tabview']/div[4]/div[2]/div/div[2]/div/div[1]")).click();
		Thread.sleep(500);
		
		String result = fDriver.findElement(By.xpath("//*[@id='databrowser_tabview']/div[4]/div[2]/div[2]/div/div[3]/div/div[1]/ul/li[3]")).getAttribute("title");
		
		assertEquals("Add Button is : ", "Add", result);
		
	}
	
	
	
	@Test 
	public void testAutoForEntities_add_action() throws InterruptedException {
		
		fDriver.get("http://127.0.0.1:8081/walib/dataBrowser/index.html");
		Thread.sleep(500);
		
		fDriver.findElement(By.xpath("//*[@id='outline_Persons']")).click();

		fDriver.findElement(By.xpath("//*[@id='databrowser_tabview']/div[4]/div[2]/div/div[2]/div/div[1]")).click();
		Thread.sleep(500);
		
		fDriver.findElement(By.xpath("//*[@id='databrowser_tabview']/div[4]/div[2]/div[2]/div/div[3]/div/div[1]/ul/li[3]")).click();
		
		String[] NewEntitiesAutoFormArray = {"2", "Arnaud", "Tob", "12/09/1987"};
		int y = 0;
		
		for(int x = 1; x < NewEntitiesAutoFormArray.length; x++ ) {
			
			Thread.sleep(1000);
			fDriver.findElement(By.xpath("//*[@id='databrowser_tabview']/div[4]/div[2]/div[2]/div/div[2]/div/div["+x+"]/div[2]/div/input")).sendKeys(NewEntitiesAutoFormArray[y]);
		
			y++;
		}
	
		fDriver.findElement(By.xpath("//*[@id='databrowser_tabview']/div[4]/div[2]/div[2]/div/div[3]/div/div[1]/ul/li[5]")).click();
		Thread.sleep(500);	
		
		fDriver.findElement(By.xpath("//*[@id='databrowser_tabview']/div[4]/div[2]/div[2]/div/div[3]/div/div[1]/ul/li[5]/div")).click();
		Thread.sleep(5000);
		
		fDriver.get("http://127.0.0.1:8081/walib/dataBrowser/index.html");
		Thread.sleep(500);
		
		fDriver.findElement(By.xpath("//*[@id='outline_Persons']")).click();
			int z = 0;
		
		for(int x =1; x < NewEntitiesAutoFormArray.length; x++ ) {
			
			
			String result = fDriver.findElement(By.xpath("//*[@id='databrowser_tabview']/div[4]/div[2]/div/div[2]/div/div[3]/div/div["+x+"]/div")).getText();

			assertEquals("(From the autoForm), The New data in the grid is : ", NewEntitiesAutoFormArray[z], result);
			
			z++;
		}
		
	}
	

	
	@Test 
	public void testAutoForEntities_find_exist() throws InterruptedException {
		
		fDriver.get("http://127.0.0.1:8081/walib/dataBrowser/index.html");
		Thread.sleep(500);
		
		fDriver.findElement(By.xpath("//*[@id='outline_Persons']")).click();

		fDriver.findElement(By.xpath("//*[@id='databrowser_tabview']/div[4]/div[2]/div/div[2]/div/div[1]")).click();
		Thread.sleep(500);
		
		String result = fDriver.findElement(By.xpath("//*[@id='databrowser_tabview']/div[4]/div[2]/div[2]/div/div[3]/div/div[1]/ul/li[4]")).getAttribute("title");
		
		assertEquals("Find Button is : ", "Find", result);
		
	}
	
	
	
	@Test 
	public void testAutoForEntities_find_action() throws InterruptedException {
		
		fDriver.get("http://127.0.0.1:8081/walib/dataBrowser/index.html");
		Thread.sleep(500);
		
		fDriver.findElement(By.xpath("//*[@id='outline_Persons']")).click();

		fDriver.findElement(By.xpath("//*[@id='databrowser_tabview']/div[4]/div[2]/div/div[2]/div/div[1]")).click();
		Thread.sleep(500);
		
		fDriver.findElement(By.xpath("//*[@id='databrowser_tabview']/div[4]/div[2]/div[2]/div/div[3]/div/div[1]/ul/li[4]")).click();
		
		String[] FindEntitiesAutoFormArray = {"1", "Thomas", "andrews", "04/05/1985"};
		int z = 0;
	
		for(int x =1; x < FindEntitiesAutoFormArray.length; x++ ) {
		
			String result = fDriver.findElement(By.xpath("//*[@id='databrowser_tabview']/div[4]/div[2]/div/div[2]/div/div[1]/div/div["+x+"]/div")).getText();

			assertEquals("Find data in the grid is : ", FindEntitiesAutoFormArray[z], result);
		
			z++;
		}
		
	}

	@Test 
	public void testAutoForMoveBetweenEntities_remove_exist() throws InterruptedException {
		
		fDriver.get("http://127.0.0.1:8081/walib/dataBrowser/index.html");
		Thread.sleep(500);
		
		fDriver.findElement(By.xpath("//*[@id='outline_Persons']")).click();

		fDriver.findElement(By.xpath("//*[@id='databrowser_tabview']/div[4]/div[2]/div/div[2]/div/div[1]")).click();
		Thread.sleep(500);
		
		String result = fDriver.findElement(By.xpath("//*[@id='databrowser_tabview']/div[4]/div[2]/div[2]/div/div[3]/div/div[1]/ul/li[6]")).getAttribute("title");
		
		assertEquals("Save Button is : ", "Delete", result);
		
	}
	
	@Test 
	public void testAutoForMoveBetweenEntities_remove_action() throws InterruptedException {
		
		fDriver.get("http://127.0.0.1:8081/walib/dataBrowser/index.html");
		Thread.sleep(500);
	
		fDriver.findElement(By.xpath("//*[@id='outline_Persons']")).click();
		
		fDriver.findElement(By.xpath("//*[@id='databrowser_tabview']/div[4]/div[2]/div/div[2]/div/div[3]")).click();
		Thread.sleep(500);
		
		fDriver.findElement(By.xpath("//*[@id='databrowser_tabview']/div[4]/div[2]/div[2]/div/div[3]/div/div[1]/ul/li[6]")).click();
		
		String result = fDriver.findElement(By.xpath("//*[@id='databrowser_tabview']/div[4]/div[2]/div/div[3]/div[2]")).getText(); 
		
		assertEquals("Items has been removed : ", "2 item(s)", result);
		
	}

	@Test 
	public void testAutoForEntities_save_exist() throws InterruptedException {
		
		fDriver.get("http://127.0.0.1:8081/walib/dataBrowser/index.html");
		Thread.sleep(500);
		
		fDriver.findElement(By.xpath("//*[@id='outline_Persons']")).click();

		fDriver.findElement(By.xpath("//*[@id='databrowser_tabview']/div[4]/div[2]/div/div[2]/div/div[1]")).click();
		Thread.sleep(500);
	
		String result = fDriver.findElement(By.xpath("//*[@id='databrowser_tabview']/div[4]/div[2]/div[2]/div/div[3]/div/div[1]/ul/li[5]")).getAttribute("title");
		
		assertEquals("Save Button is : ", "Save", result);
		
	}
	
	@Test 
	public void testAutoForMoveBetweenEntities_save_action() throws InterruptedException {
		
		fDriver.get("http://127.0.0.1:8081/walib/dataBrowser/index.html");
		Thread.sleep(500);
		
		fDriver.findElement(By.xpath("//*[@id='outline_Persons']")).click();

		fDriver.findElement(By.xpath("//*[@id='databrowser_tabview']/div[4]/div[2]/div/div[2]/div/div[1]")).click();
		Thread.sleep(500);
		
		fDriver.findElement(By.xpath("//*[@id='databrowser_tabview']/div[4]/div[2]/div[2]/div/div[3]/div/div[1]/ul/li[3]")).click();
		
		String[] NewEntitiesSavedAutoFormArray = {"2", "Arnaud", "Tob" , "12/09/1987"};
		int y = 0;
		
		for(int x = 1; x < NewEntitiesSavedAutoFormArray.length; x++ ) {

			fDriver.findElement(By.xpath("//*[@id='databrowser_tabview']/div[4]/div[2]/div[2]/div/div[2]/div/div["+x+"]/div[2]/div/input")).sendKeys(NewEntitiesSavedAutoFormArray[y]);
		
			y++;
		}
	
		fDriver.findElement(By.xpath("//*[@id='databrowser_tabview']/div[4]/div[2]/div[2]/div/div[3]/div/div[1]/ul/li[5]")).click();
		
		fDriver.get("http://127.0.0.1:8081/walib/dataBrowser/index.html");
		Thread.sleep(500);
		
		fDriver.findElement(By.xpath("//*[@id='outline_Persons']")).click();
			int z = 0;
		
		for(int x =1; x < NewEntitiesSavedAutoFormArray.length; x++ ) {
			
			
			String result = fDriver.findElement(By.xpath("//*[@id='databrowser_tabview']/div[4]/div[2]/div/div[2]/div/div[3]/div/div["+x+"]/div")).getText();

			assertEquals("The New data saved in the grid is : ", NewEntitiesSavedAutoFormArray[z], result);
			
			z++;
		}
	
		fDriver.get("http://127.0.0.1:8081/walib/dataBrowser/index.html");
		Thread.sleep(500);
	
		fDriver.findElement(By.xpath("//*[@id='outline_Persons']")).click();
		
		fDriver.findElement(By.xpath("//*[@id='databrowser_tabview']/div[4]/div[2]/div/div[2]/div/div[3]")).click();
		Thread.sleep(500);
		
		fDriver.findElement(By.xpath("//*[@id='databrowser_tabview']/div[4]/div[2]/div[2]/div/div[3]/div/div[1]/ul/li[6]")).click();
		
	}
		
	
}
