package com.gmail.hzz041120.spring.io;

import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;

import com.alibaba.intl.sourcing.generalorders.modules.commons.robust.annotations.Robust;

public class ResourcePatternResolverTest {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        MetadataReaderFactory metadataReaderFactory = new CachingMetadataReaderFactory();
        Resource[] resources = resolver.getResources("classpath*:com/gmail/**/*.class");
        for(Resource resource : resources) {
            MetadataReader classmeta = metadataReaderFactory.getMetadataReader(resource);
            String className = classmeta.getClassMetadata().getClassName();
            Class<?> clazz = Class.forName(className);
            Method[] methods = clazz.getDeclaredMethods();
            for(Method m : methods) {
                Robust robust = m.getAnnotation(Robust.class);
                if(robust != null) {
                    Proxy.newProxyInstance(loader, interfaces, h)
                }
            }
        }
    }
}
