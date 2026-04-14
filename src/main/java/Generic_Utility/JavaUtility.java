package Generic_Utility;

import java.util.Random;

public class JavaUtility {
	
	
	public int getRandomNumber()
	{
	Random ran = new Random();
    int randomNumber=ran.nextInt(10000);
    return randomNumber;
	}
	
	
}
