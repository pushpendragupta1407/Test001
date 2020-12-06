package com.stackroute.userprofile.aspect;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


/* Annotate this class with @Aspect and @Component */
@Aspect
@Component
public class LoggerAspect {
	/*
	 * Write loggers for each of the methods of User controller, any particular
	 * method will have all the four aspectJ annotation
	 * (@Before, @After, @AfterReturning, @AfterThrowing).
	 */
	private static final Logger logger = LoggerFactory.getLogger(LoggerAspect.class);
	
	@Pointcut("execution (* com.stackroute.userprofile.controller.UserProfileController.*(..))")
	public void Userprofilecalls()
	{
		logger.info(" -----------------------------[ User Profile Service is called ] --------------------------------------------------");
		
	}
//	
//	
//	@Before("Userprofilecalls()")
//	public void before(JoinPoint joinPoint)
//	{
//		logger.info(" -----------------------------[ Before method call ] --------------------------------------------------");
//		logger.debug(" Method Name : ", joinPoint.getSignature().getName());
//		logger.debug(" Argument names : ", Arrays.toString(joinPoint.getArgs()));
//	}
//	
//	@After("Userprofilecalls()")
//	public void after(JoinPoint joinPoint)
//	{
//		logger.info(" -----------------------------[ After the method call ] --------------------------------------------------");
//		
//	}
//	
//	@Around("Userprofilecalls()")
//	public void Around(JoinPoint joinPoint)
//	{
//		logger.info(" -----------------------------[ Before & After the method call ] --------------------------------------------------");
//	
//	}
//	
////	@AfterThrowing(value="Userprofilecalls()",throwing = "error")
////	public void afterthrowing(JoinPoint joinPoint,Throwable error)
////	{
////		logger.debug(" Execption Occured : ", error.fillInStackTrace());
////	}
////	
//	@AfterReturning(value="Userprofilecalls()",returning = "Return")
//	public void afterReturn(JoinPoint joinPoint, Object Return)
//	{
//		logger.debug(" Method Returned : ",Return.toString());
//	}
}

