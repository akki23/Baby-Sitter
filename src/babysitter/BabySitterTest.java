
package babysitter;

import junit.framework.TestCase;

public class BabySitterTest extends TestCase {
	public void testCalculateTotalCharge(){
	       BabySitterJob a = new BabySitterJob("9pm","12am","9pm");
	      assertEquals(24,a.calculateTotalCharge());
	   }
}