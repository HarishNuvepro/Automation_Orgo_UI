package Generic_Utility;

public interface ConstantFilePath {

	String excelfilepath = ".\\src\\main\\resources\\TestData_Trail.xlsx";

	String geminiApiKey = System.getenv("GEMINI_API_KEY") != null
			? System.getenv("GEMINI_API_KEY")
			: "AIzaSyDI6zS2dL757GPn1SMzCZPip0VA7ewnE98";

}
