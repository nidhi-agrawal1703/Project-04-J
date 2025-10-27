package in.co.rays.proj4.bundle;
import java.util.ResourceBundle;

public class TestSystem {

	public static void main(String[] args) {
		ResourceBundle rb=ResourceBundle.getBundle("in.co.rays.proj4.bundle.system");
		System.out.println("driver: "+rb.getString("driver"));
		System.out.println(("url: "+rb.getString("url")));
		System.out.println("username: "+rb.getString("username"));
		System.out.println("password: "+rb.getString("password"));
		System.out.println("initialpoolsize: "+rb.getString("initialpoolsize"));
		System.out.println("acquireincrement: "+rb.getString("acquireincrement"));
		System.out.println("maxpoolsize: "+rb.getString("maxpoolsize"));
	}
}
