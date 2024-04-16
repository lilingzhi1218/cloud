package com.example.llz.cloud1.transactional;

import com.example.llz.cloud1.jpa.IJpaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("transaction")
public class TransactionalTest {
    @Autowired
    TransactionalTest1 transactionalTest1;

    @Autowired
    IJpaService jpaService;

    @RequestMapping("a")
    @Transactional(rollbackFor = Exception.class)
    public void a(){
        jpaService.update("a", "123");
        transactionalTest1.b();
        c();
    }
    private void c(){
        jpaService.update("c", "125");
        throw new NullPointerException();
    }
    //*七个事务传播特性*//
    /**
     * REQUIRED、REQUIRES_NEW、NESTED的比较：
     *
     * REQUIRED 父事务和子事务为同一个事务，只要一方回滚，另一方也会回滚
     * REQUIRES_NEW 父事务回滚时，子事务不受影响；子事务回滚时，父事务 catch 之后不会回滚
     * NESTED 父事务回滚时，子事务也回滚；子事务回滚时，父事务 catch 之后不会回滚
     */

    /**
     * REQUIRED 当前存在事务则加入，没有则新建事务
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void required(){
        jpaService.update("required", "123");
    }
    /**
     * SUPPORTS 当前存在事务则加入，没有则以非事务的发方式执行
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    public void supports(){
        jpaService.update("supports", "123");
    }
    /**
     * MANDATORY 当前存在事务则加入，没有则抛出异常
     */
    @Transactional(propagation = Propagation.MANDATORY)
    public void mandatory(){
        jpaService.update("mandatory", "123");
    }
    /**
     * REQUIRES_NEW 新建事务，父事务挂起（父事务回滚，不影响子新事务）
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void requires_new(){
        jpaService.update("requires_new", "123");
    }

    /**
     * NOT_SUPPORTED 以非事务方式执行，父事务挂起
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void not_supported(){
        jpaService.update("not_supported", "123");
    }

    /**
     * NESTED 当前存在事务则以嵌套事务执行，没有则新建事务（父事务回滚时，子事务也回滚；子事务回滚时，父事务 catch 之后不会回滚）
     */
    @Transactional(propagation = Propagation.NESTED)
    public void nested(){
        jpaService.update("nested", "123");
    }


    /**
     * never 不使用事务，有则抛出异常
     */
    @Transactional(propagation = Propagation.NEVER)
    public void never(){
        jpaService.update("never", "123");
    }


}
