import org.junit.Assert;
import org.junit.Test;

/**
 * Created by chenjingshuai on 17-5-16.
 */
public class TestIT {
    public static void main(String[] args) {
        System.out.println(System.getProperty("java.class.path"));
    }

    @Test
    public void test() {
        System.out.println(System.getProperty("java.class.path"));
        Assert.assertNotNull(null);
    }
}
