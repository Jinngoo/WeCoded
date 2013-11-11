package com.jinva.util;

import org.hibernate.dialect.MySQLDialect;

public class CustomerMySQLDialect extends MySQLDialect {

	/**
	 * 重載getTableTypeString()方法
	 */
	public String getTableTypeString() {
		return " ENGINE=InnoDB DEFAULT CHARSET=utf8";
	}
}
