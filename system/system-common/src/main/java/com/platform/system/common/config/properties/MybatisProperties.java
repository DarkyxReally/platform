package com.platform.system.common.config.properties;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import org.apache.ibatis.session.ExecutorType;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@ConfigurationProperties(prefix = MybatisProperties.MYBATIS_PREFIX)
public class MybatisProperties {

    public static final String MYBATIS_PREFIX = "mybatis";

    /**
     * Config file path.
     */
    private String config;

    /**
     * Location of mybatis mapper files.
     */
    private String mapperLocations;

    /**
     * Package to scan domain objects.
     */
    private String typeAliasesPackage;

    /**
     * Package to scan handlers.
     */
    private String typeHandlersPackage;

    /**
     * Check the config file exists.
     */
    private boolean checkConfigLocation = false;

    /**
     * Execution mode.
     */
    private ExecutorType executorType = ExecutorType.SIMPLE;
}