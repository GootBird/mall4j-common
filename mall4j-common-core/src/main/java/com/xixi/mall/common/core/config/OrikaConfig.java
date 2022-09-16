package com.xixi.mall.common.core.config;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * MapperFacade 用于dto ->entity的转换
 */
@Configuration
public class OrikaConfig {

	@Bean
	public MapperFactory mapperFactory() {
		return new DefaultMapperFactory.Builder().build();
	}

	@Bean
	public MapperFacade mapperFacade() {
		return mapperFactory().getMapperFacade();
	}

}
