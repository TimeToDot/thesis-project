package thesis;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ThesisApplication.class)
public class BasicTest {

    @Test
    public void basicCheck(){

        String s = "asa";
        Assert.assertNotNull(s);
    }
}
