package com.wbd.utils;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class BeanUtil
{

    private static final Logger logger = LoggerFactory.getLogger(BeanUtil.class);

    /**
     *
     * @Title: mapToObject
     * @Description: (map转换为bean)
     * @return T    返回类型
     * @param map
     * @param beanClass
     * @return
     * @throws Exception
     */
    public static <T> T mapToObject(Map<String, Object> map, Class<T> beanClass) {
        if (map == null) {
            return null;
        }

        T obj = null;
        try
        {
            obj = beanClass.newInstance();
            BeanUtils.populate(obj, map);
        }
        catch (Exception e)
        {
            logger.error("mapToObject错误", e);
        }

        return obj;
    }

    /**
     *
     * @Title: objectToMap
     * @Description:
     * @return Map<?,?>    返回类型
     * @param obj
     * @return
     */
    public static Map<?, ?> objectToMap(Object obj) {
        if(obj == null) {
            return null;
        }
        return new org.apache.commons.beanutils.BeanMap(obj);
    }
}
