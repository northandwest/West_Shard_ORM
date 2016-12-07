package com.bucuoa.west.orm.shard.annonation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ShardTable {
	public String policy() default "hash";
	public int nums() default 64;
	public int shards() default 1;
}
