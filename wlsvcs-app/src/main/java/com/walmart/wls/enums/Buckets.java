package com.walmart.wls.enums;

import com.walmart.wls.cache.IBucket;

public enum Buckets implements IBucket {
	ZONE("zone");
	private String bucketName;
	Buckets(String bucket) {
		bucketName = bucket;
	}

	@Override
	public String getBucketName() {
		return bucketName;
	}

}
