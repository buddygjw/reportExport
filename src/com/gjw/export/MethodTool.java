package com.gjw.export;


import java.lang.reflect.Method;

/**
 * Created by Administrator on 14-5-6.
 */
public class MethodTool
{
    public static Object executeMethod(Object object, String methodName)
            throws Exception
    {
        Class clazz = object.getClass();
        Method method = clazz.getDeclaredMethod(methodName, new Class[0]);
        return method.invoke(object, new Object[0]);
    }

    public static void executeMethod(Object object, String methodName, Object param)
            throws Exception
    {
        Class clazz = object.getClass();
        Method method = clazz.getDeclaredMethod(methodName, new Class[] { param.getClass() });
        method.invoke(object, new Object[] { param });
    }

    public static void excuteBoolMethod(Object object, String methodName, boolean param)
            throws Exception
    {
        Class c = object.getClass();
        Method m = c.getDeclaredMethod(methodName, new Class[] { Boolean.TYPE });
        m.invoke(object, new Object[] { Boolean.valueOf(param) });
    }

    public static String returnSetMethodName(String property)
    {
        return "set" + Character.toUpperCase(property.charAt(0)) + property.substring(1);
    }

    public static String returnGetMethodName(String property)
    {
        return "get" + Character.toUpperCase(property.charAt(0)) + property.substring(1);
    }

    public static String returnIsBooleanMethodName(String property)
    {
        return "is" + Character.toUpperCase(property.charAt(0)) + property.substring(1);
    }
}