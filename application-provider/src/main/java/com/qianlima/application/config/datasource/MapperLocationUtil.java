package com.qianlima.application.config.datasource;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MapperLocationUtil {

    public static Resource[] resolveMapperLocations(String[] xmlPaths) {
        List<Resource> resources = new ArrayList<>();
        if (xmlPaths == null || xmlPaths.length == 0){
            return resources.toArray(new Resource[0]);
        }
        ResourcePatternResolver resourceResolver = new PathMatchingResourcePatternResolver();
        List<String> mapperLocations = new ArrayList<>(Arrays.asList(xmlPaths));
        for (String mapperLocation : mapperLocations) {
            try {
                Resource[] mappers = resourceResolver.getResources(mapperLocation);
                resources.addAll(Arrays.asList(mappers));
            } catch (IOException e) {
                // ignore
            }
        }
        return resources.toArray(new Resource[0]);
    }
}
