package com.cxh.jpaannotation.aspect;



import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.support.spring.PropertyPreFilters;
import com.cxh.jpaannotation.annotation.OperLog;
import com.cxh.jpaannotation.constant.Status;
import com.cxh.jpaannotation.entity.SysOperLog;
import com.cxh.jpaannotation.entity.User;
import com.cxh.jpaannotation.service.ISysOperLogService;
import com.cxh.jpaannotation.util.*;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * 操作日志记录处理
 *
 */
@Aspect
@Component
@Slf4j
public class OperLogAspect
{
    @Autowired
    private ISysOperLogService sysOperLogService;

    /** 排除敏感属性字段 */
    public static final String[] EXCLUDE_PROPERTIES = { "password"};

    /**
     * @description 配置织入点
     * @param
     * @return  void
     **/
    @Pointcut("@annotation(com.cxh.jpaannotation.annotation.OperLog)")
    public void operLogPointCut()
    {
    }

    /**
     * 处理完请求后执行
     *
     * @param joinPoint 切点
     */
    @AfterReturning(pointcut = "operLogPointCut()", returning = "jsonResult")
    public void doAfterReturning(JoinPoint joinPoint, Object jsonResult)
    {
        handleLog(joinPoint, null, jsonResult);
    }

    /**
     * 拦截异常操作
     *
     * @param joinPoint 切点
     * @param e 异常
     */
    @AfterThrowing(value = "operLogPointCut()", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Exception e)
    {
        handleLog(joinPoint, e, null);
    }

    protected void handleLog(final JoinPoint joinPoint, final Exception e, Object jsonResult)
    {
        try
        {
            // 获得注解
            OperLog operLog = getAnnotationLog(joinPoint);
            if (operLog == null)
            {
                return;
            }

            // *========数据库日志=========*//
            SysOperLog sysOperLog = new SysOperLog();
            sysOperLog.setStatus(Status.SUCCESS.ordinal());
            // 返回参数
            sysOperLog.setJsonResult(StringUtils.substring(JSON.marshal(jsonResult), 0, 2000));

            sysOperLog.setOperUrl(ServletUtils.getRequest().getRequestURI());
            // 获取当前的用户
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                    .getRequest();
            // 请求的地址
            String ip = HttpUtils.getRequestAddr(request);
            sysOperLog.setOperIp(ip);
            if(StringUtils.isNotEmpty(ip)){
                // 远程查询操作地点
                sysOperLog.setOperLocation(AddressUtils.getRealAddressByIP(sysOperLog.getOperIp()));
            }
            // 获取当前的用户
            HttpSession session = request.getSession();
            User user = null;
            if (session.getAttribute("user") != null) {
                user = (User) session.getAttribute("user");
            }

            if (user != null)
            {
                sysOperLog.setOperName(user.getName());
            }

            if (e != null)
            {
                sysOperLog.setStatus(Status.ERROR.ordinal());
                sysOperLog.setErrorMsg(StringUtils.substring(e.getMessage(), 0, 2000));
            }
            // 设置方法名称
            String className = joinPoint.getTarget().getClass().getName();
            String methodName = joinPoint.getSignature().getName();
            sysOperLog.setMethod(className + "." + methodName + "()");
            // 设置请求方式
            sysOperLog.setRequestMethod(ServletUtils.getRequest().getMethod());
            // 设置action动作
            sysOperLog.setOperType(operLog.operType().ordinal());
            // 设置标题
            sysOperLog.setTitle(operLog.title());
            // 获取参数的信息，传入到数据库中。
            setRequestValue(sysOperLog);
            // 保存数据库
            sysOperLogService.save(sysOperLog);
        }
        catch (Exception exp)
        {
            // 记录本地异常日志
            log.error("==前置通知异常==");
            log.error("异常信息:{}", exp.getMessage());
            exp.printStackTrace();
        }
    }



    /**
     * 获取请求的参数，放到log中
     *
     * @param operLog 操作日志
     * @throws Exception 异常
     */
    private void setRequestValue(SysOperLog operLog) throws Exception
    {
        Map<String, String[]> map = ServletUtils.getRequest().getParameterMap();
        if (StringUtils.isNotEmpty(map))
        {
            PropertyPreFilters.MySimplePropertyPreFilter excludefilter = new PropertyPreFilters().addFilter();
            excludefilter.addExcludes(EXCLUDE_PROPERTIES);
            String params = JSONObject.toJSONString(map, excludefilter);
            operLog.setOperParam(StringUtils.substring(params, 0, 2000));
        }
    }

    /**
     * 是否存在注解，如果存在就获取
     */
    private OperLog getAnnotationLog(JoinPoint joinPoint) throws Exception
    {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();

        if (method != null)
        {
            return method.getAnnotation(OperLog.class);
        }
        return null;
    }
}
