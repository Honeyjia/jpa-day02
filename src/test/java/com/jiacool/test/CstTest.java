package com.jiacool.test;


import com.jiacool.dao.CstDao;
import com.jiacool.domain.Customer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)//声明spring提供的单元测试的环境
@ContextConfiguration(locations = "classpath:applicationContext.xml") //指定spring容器的配置信息
public class CstTest {
    @Autowired
    private CstDao cstDao;

    /*
    根据id查询
     */
    @Test
    public void findOne(){
        Customer customer = cstDao.findOne(2l);
        System.out.println(customer);
    }

    /**
     * save：保存或更新
     * 根据传递的对象是否存在主键
     * 如果没有id主键属性：保存
     * 存在id主键属性：根据id查询数据，更新数据
     */
    @Test//保存
    public void save(){
        Customer customer = new Customer();
        customer.setCustAddress("湖南长沙");
        customer.setCustName("黑马");
        customer.setCustIndustry("教育");
        cstDao.save(customer);
    }

    @Test//修改
    public void update(){
        //先根据id查询出要修改的Customer对象，再设置其属性，最后再修改，这样就不会覆盖原来的属性
        Customer c = cstDao.findOne(2l);
        c.setCustIndustry("机器");
        c.setCustName("钓鱼网站");
        cstDao.save(c);
    }


    @Test//删除
    public void delete(){
        cstDao.delete(4l);
    }

    @Test//查询所有
    public void findALL(){
        List<Customer> list = cstDao.findAll();
        for (Customer customer : list) {
            System.out.println(customer);
        }
    }

    @Test//统计客户数量
    public void count(){
        long count = cstDao.count();
        System.out.println(count);
    }

    /**
     * 测试判断id为5的客户是否存在
     * 1.可以查询id为4的客户
     *      如果值为空，代表不存在  如果值不为空，代表存在
     * 2.判断数据库中id为4的客户数量
     *      如果数量为0，代表不存在  如果大于0，代表存在
     */

    @Test
    public void IsExists(){
        boolean exists = cstDao.exists(5l);
        System.out.println(exists);
    }

    /**
     * 根据id从数据库中查询
     *  @Transactional： 保证getOne正常运行
     *  findOne:
     *      em.find()          : 立即查询
     *  getOne:
     *      em.getReference    : 延迟加载
     *      *返回的是一个客户的动态代理对象
     *      *什么时候用，什么时候查询
     */
    @Test
    public void getOne(){
        Customer customer = cstDao.getOne(5l);
        System.out.println(customer);
    }
}
