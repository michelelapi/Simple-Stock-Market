package lapi.sss.jpm_test_sss.util;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;

@Aspect
public class xx1 {
	private Logger log = Logger.getLogger(xx1.class);
	
  @Around("execution(* *(..)) && @annotation(Loggable)")
  public Object around(ProceedingJoinPoint point) {
	    Object result = null;
	    
		try {
			long start = System.currentTimeMillis();
		result = point.proceed();
	    log.info(
	      "#%s(%s): %s in %[msec]s"+
	      MethodSignature.class.cast(point.getSignature()).getMethod().getName()+
	      point.getArgs()+
	      result+
	      (System.currentTimeMillis() - start)
	    );
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    return result;
  }
}