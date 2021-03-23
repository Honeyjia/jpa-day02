package com.jiacool.test;


import com.jiacool.dao.CstDao;
import com.jiacool.domain.Customer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)//声明spring提供的单元测试的环境
@ContextConfiguration(locations = "classpath:applicationContext.xml") //指定spring容器的配置信息
public class JpqlCstTest {
    @Autowired
    private CstDao cstDao;

    @Test//根据客户名称查询
    public void findJpql(){
        Customer customer = cstDao.findByNameJpql("黑马");
        System.out.println(customer);
    }

    @Test//根据客户名称和客户id查询客户
    public void findByNameAndId(){
        Customer customer = cstDao.findByNameAndId( 3l, "传智2");
        System.out.println(customer);
    }

    /*
       测试jpql的更新操作：
        * springDataJpa中使用jpql完成  更新/删除操作
                需要手动添加事物的支持
                默认执行结束之后，回滚事务
         @Rollback : 设置是否自动回滚  false/true
     */
    @Test
    @Transactional
    @Rollback(value = false)
    public void updateCustomer(){
        cstDao.updateCustomer("黑马程序员", 5l);
    }


    @Test//测试sql查询全部
    public void findSql(){
        List<Object[]> list = cstDao.findSql();
        for (Object[]  customer : list) {
            System.out.println(Arrays.toString(customer));
        }
    }

    @Test//测试sql条件查询
    public void findByName(){
        List<Object[]> list = cstDao.findByName("传智%");
        for (Object[]  customer : list) {
            System.out.println(Arrays.toString(customer));
        }
    }

    @Test//方法命名 条件查询
    public void findByCustName(){
        Customer customer = cstDao.findByCustName("黑马程序员");
        System.out.println(customer);
    }


    @Test//方法命名 模糊查询
    public void findByCustNameLike(){
        List<Customer> list = cstDao.findByCustNameLike("传智%");
        for (Customer customer : list) {
            System.out.println(customer);
        }
    }


    @Test//方法命名 多条件模糊查询
    public void findByCustNameLikeAndCustIndustry(){
        List<Customer> list = cstDao.findByCustNameLikeAndCustIndustry("传智%", "教育");
        for (Customer customer : list) {
            System.out.println(customer);
        }
    }

}
