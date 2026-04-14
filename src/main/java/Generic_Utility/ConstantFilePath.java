package Generic_Utility;

public interface ConstantFilePath {

	String excelfilepath = ".\\src\\main\\resources\\TestData_Trail.xlsx";

	String geminiApiKeyBase64 = "QUl6YXNTeURJMnpTN2Q3NTdHUH45U01aQ1pQaXAwVUE3ZXduRTk4";
	
	String geminiApiKey = System.getenv("GEMINI_API_KEY") != null
			? System.getenv("GEMINI_API_KEY")
			: new String(java.util.Base64.getDecoder().decode(geminiApiKeyBase64));

}
