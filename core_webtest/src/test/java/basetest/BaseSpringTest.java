package basetest;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 *
 * @ProjectNmae：core.test-springbase.
 * @ClassName：BaseSpringTest
 * @Description：spring的JUnit4测试基类<br> 继承该类的测试类可直接使用spring注解注入<br>
 *                                    每一个测试用例结束后自动回滚<br>
 *                                    可在测试方法上使用@Rollback(false)注解取消自动回滚<br>
 *                                    一般用于service及dao层测试
 * @author：chl
 * @crateTime：2013-5-15
 * @editor：
 * @editTime：
 * @editDescription：
 * @version 1.0.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/applicationContext.xml")
public abstract class BaseSpringTest extends
AbstractTransactionalJUnit4SpringContextTests {

}
