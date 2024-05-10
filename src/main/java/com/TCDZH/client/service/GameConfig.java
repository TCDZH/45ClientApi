package com.TCDZH.client.service;

import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;


/**
 * Config properties for server URL
 */
@ConfigurationProperties(prefix = "server-game-config")
public record GameConfig (@NotNull String url){}
