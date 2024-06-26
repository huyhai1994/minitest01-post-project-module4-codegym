package vn.codegym.controller.aspect;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class RestPostsControllerAspect {
    @Before(value = "executeRestPostsController()")
    public void beforeExecuteController() {
        System.out.println("--beforeExcecuteController--");
    }

    @After(value = "executeRestPostsController()")
    public void afterExecuteController() {
        System.out.println("afterExecuteController");
    }

    @Pointcut(value = "within(vn.codegym.controller.rest.RestPostsController)")
    public void executeRestPostsController() {
    }

}
