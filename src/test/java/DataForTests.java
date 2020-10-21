import org.testng.annotations.DataProvider;

public class DataForTests {
		
	@DataProvider(name="GetData")
	public Object[] dataForGet() {
		return new Object [] {1,2,3};						   
	}
	
	@DataProvider(name="PostData")
	public Object[][] dataForPost() {	
		return new Object [][] { 	   
			{"Alex", "Ben Ari",25 ,2}, 
			{"Tom", "Cooper",40 ,1}    
		};							   
	}
	
	@DataProvider(name="PatchData")
	public Object[][] dataForPatch() {
		return new Object [][] { 	   
			{2, "Moshe", 29},
		};							   
	}
	
	@DataProvider(name = "PutData")
	public Object[][] dataForPut() {
		return new Object [][] { 	   
			{3, "Itay", "Cohen", 16, 1}, 
			{5, "Avi", "Cohen" ,42, 2}
		};							   
	}
	
	@DataProvider(name="DeleteData")
	public Object[] dataForDelete() {
		return new Object [] {
			4,5
		};
	}
	
	@DataProvider(name="keyValueData")
	public Object[] dataForKeyValueCheck() {
		return new Object [][] {
			{1,"/users","lastName", "Chen"},
			{2,"/subjects","name", "Developer"}
		};
	}
	
	
}
