package Generic_Utility;

public interface ConstantFilePath {

	String excelfilepath = ".\\src\\main\\resources\\TestData_Improved.xlsx";

	String geminiApiKeyBase64 = "QUl6YVN5RGgxTmU1S2JwUVlHQ3BtMU5hdm02WGdXemxYRWVlRTBZ";

	String geminiApiKey = System.getenv("GEMINI_API_KEY") != null
			? System.getenv("GEMINI_API_KEY")
			: new String(java.util.Base64.getDecoder().decode(geminiApiKeyBase64));

}
