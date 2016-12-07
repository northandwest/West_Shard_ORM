package com.bucuoa.west.orm.core.mapping;

public class DefaultMappingFactory extends MappingFactory {

	@Override
	public MysqlMapping createMysql() {
		return new MysqlMapping();
	}

}
