package cc.mrbird;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
/**
 *
 * 功能描述: Junit单元测试
 *
 * @auther: niuhao
 * @date: 2018/12/3 12:08
 */
@RunWith(SpringJUnit4ClassRunner.class)
//@RunWith(SpringRunner.class) // 等价于使用 @RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class ApplicationTest {

    @Test
    public void test() {
        // test something
    }
}
