package vn.codegym.aspect;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class PostsAspect {
    @Before(value = "executeController()")
    public void beforeExecuteController() {
        System.out.println("--beforeExcecuteController--");
    }

    @After(value = "executeController()")
    public void afterExecuteController() {
        System.out.println("afterExecuteController");
    }


    @Pointcut(value = "within(vn.codegym.controller.*)")
    public void executeController() {

    }
}
