package com.walmart.wls.cache;

import java.time.Duration;

/**
 * <p>IBucket interface defines getBucketName API, all the implementation classes
 * should provide the bucket name while utilizing ICacheManager APIs.</p>
 *
 * @author gtulla
 * @version $Id: $Id
 */
public interface IBucket {
	/**
	 * <p>getBucketName.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getBucketName();

	//Duration getDuration();
}
