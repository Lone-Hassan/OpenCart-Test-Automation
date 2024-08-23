package OpenCart.resources;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class Retry implements IRetryAnalyzer{

	int count = 0;
	int maxtry = 2;
	@Override
	public boolean retry(ITestResult result) {
		// TODO Auto-generated method stub
		if (count <maxtry) {
			count++;
			return true;
		}
		return false;
	}

}
