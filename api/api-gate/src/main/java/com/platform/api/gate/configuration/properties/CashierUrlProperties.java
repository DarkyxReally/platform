package com.platform.api.gate.configuration.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 暴露给商户访问地址配置
 * @version: 1.0
 */
@Component
@ConfigurationProperties(CashierUrlProperties.PREFIX)
public class CashierUrlProperties {

	public static final String PREFIX = "merchant";

	private List<String> cashier = new ArrayList<>();

	public List<String> getCashier() {
		return cashier;
	}

	public void setCashier(List<String> cashier) {
		this.cashier = cashier;
	}
}
