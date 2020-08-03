package com.pelime.ecms.modules.ebank.util;

import java.lang.reflect.Method;
import java.util.Arrays;

public class ReflectionUtils {
    /**
     * 功能描述：设置实体属性
     * 参数 : obj 实体对象  | attrName 属性字段名   | attrValue 属性字段值
     */
    public static void setBeanAttr(Object obj, String attrName, Object attrValue) {
        // 将属性名的首字母变为大写，为执行set/get方法做准备
        String methodName = attrName.substring(0, 1).toUpperCase() + attrName.substring(1);
        Method setMethod = null;
        Method getMethod = null;
        try {
            getMethod = obj.getClass().getMethod("get" + methodName);
            setMethod = obj.getClass().getMethod("set" + methodName, getMethod.getReturnType());
            if (setMethod == null) {
                setMethod = obj.getClass().getSuperclass().getMethod("set" + methodName, getMethod.getReturnType());
            }
            if (setMethod != null){
                if(getMethod.getReturnType().getGenericSuperclass()==java.sql.Date.class.getGenericSuperclass()){
                    setMethod.invoke(obj,attrValue);
                }
                else
                    setMethod.invoke(obj, converseType(attrValue, getMethod.getReturnType()));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 功能描述：数据类型转换
     * 参数        param 属性字段值   | paramClass 属性字段类型
     */
    public static Object converseType(Object param, Class<?> paramClass) {
        if("java.util.List".equals(paramClass.getName())){
            return Arrays.asList(param);
        }
        //java.util.List  java.util.Set
        // 如果是自定义类型 通过返回值类型 实例化此类 给实例赋值 然后将实例set给引用的对象
        if(!isJavaClass(param.getClass())){
            System.out.println(param.getClass().getSimpleName()+"---------ceshi--------------");
            return param;
        }
        // 基本数据类型
        String paramString = param.toString();
        if (String.class.equals(paramClass))
            return paramString;
        if ((Boolean.TYPE.equals(paramClass)) || (Boolean.class.equals(paramClass)))
            return Boolean.valueOf(paramString);
        if ((Byte.TYPE.equals(paramClass)) || (Byte.class.equals(paramClass)))
            return Byte.valueOf(Byte.parseByte(paramString));
        if ((Short.TYPE.equals(paramClass)) || (Short.class.equals(paramClass)))
            return Short.valueOf(paramString);
        if ((Integer.TYPE.equals(paramClass)) || (Integer.class.equals(paramClass)))
            return Integer.valueOf(Integer.parseInt(paramString));
        if ((Long.TYPE.equals(paramClass)) || (Long.class.equals(paramClass)))
            return Long.valueOf(Long.parseLong(paramString));
        if ((Float.TYPE.equals(paramClass)) || (Float.class.equals(paramClass)))
            return Float.valueOf(Float.parseFloat(paramString));
        if ((Double.TYPE.equals(paramClass)) || (Double.class.equals(paramClass))
                || (Number.class.equals(paramClass))) {
            return Double.valueOf(Double.parseDouble(paramString));
        }
        return paramString;
    }

    public static boolean isJavaClass(Class<?> clz) {
        return clz != null && clz.getClassLoader() == null;
    }
}
