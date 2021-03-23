package com.jiacool.dao;

import com.jiacool.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 符合springDataJpa的dao层接口规范
 *  JpaRepository<操作的实体类型, 实体类中主键属性的类型>
 *      * 封装了基本的CRUD操作
 *  JpaSpecificationExecutor<操作的实体类型>
 *      * 封装了复杂查询 (分页)
 */

public interface CstDao extends JpaRepository<Customer, Long>, JpaSpecificationExecutor<Customer> {

    /**
     * 要求: 更具客户名称查询客户
     *   使用jpql的形式查询
     *   jpql = from Customer where custName = ?
     *   配置jpql语句，使用@Query注解
     * @param custName
     * @return
     */
    @Query(value = "from Customer where custName = ?")
    public Customer findByNameJpql(String custName);


    //要求: 根据客户名称和客户id查询客户
    /*
     对于多个占位符参数
            赋值的时候：默认情况下，占位符的位置需要和方法参数中的位置保持一致
     可以指定占位符参数的位置
            ？索引的方式,指定此占位符的取值来源
    */
    /*@Query(value = "from Customer where custName = ? and custId = ?")
    public Customer findByNameAndId(String custName, Long cust_id);*/

    @Query(value = "from Customer where custName = ?2 and custId = ?1")
    public Customer findByNameAndId(Long cust_id, String custName);


    /*
     要求：根据id更新 客户的名称
     sql：update cst_customer set cust_name = ? where cust_id = ?
     jpql: update Customer set custName = ? where custId =?
     @Query  : 代表的是进行查询
     @Modifying  : 声明当前执行的是一个更新的操作
     */

    @Query("update Customer set custName = ?1 where custId = ?2")
    @Modifying
    public void updateCustomer(String cust_name, Long cust_id);

    /*
     使用sql形式进行查询 要求：查询全部客户
     sql：select * from cst_customer
     nativeQuery：  查询方式
        true： sql查询
        false： jpql查询
     */
    @Query(value = "select * from cst_customer", nativeQuery = true)
    public List<Object[]> findSql();

    //条件查询 要求：根据哭的的名称查询
    @Query(value = "select * from cst_customer where cust_name like ?1", nativeQuery = true)
    public List<Object[]> findByName(String cust_name);


    /*
    方法命名的约定：
        findBy: 查询
             对象中的属性名(首字母大写)：查询的条件  CustName
        在springDataJpa的运行阶段
            会根据方法名称进行解析 findBy  ---  from xxx(实体类)
                               属性名  ---   where custName = ?
        1.findBy + 属性名称(根据属性名称进行查询 = )
        2.findBy + 属性名称 + 查询方式 (Like / isnull)模糊查询
        3.多条件查询：
            findBy + 属性名称 + 查询方式 + 多条件的连接符(and / or) + 属性名称 + 查询方式
     */
    public Customer findByCustName(String cust_name);

    public List<Customer> findByCustNameLike(String cust_name);

    //使用 客户名称模糊匹配 和 客户所属行业精准匹配的查询
    public List<Customer> findByCustNameLikeAndCustIndustry(String cust_name, String cust_industry);


}
