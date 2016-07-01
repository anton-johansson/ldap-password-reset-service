/**
 * Copyright (c) Anton Johansson <antoon.johansson@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.antonjohansson.lprs.controller.spam;

import static com.google.common.cache.CacheBuilder.newBuilder;
import static java.util.concurrent.TimeUnit.MINUTES;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import com.antonjohansson.lprs.controller.configuration.Configuration;
import com.google.common.cache.Cache;
import com.google.inject.Inject;

/**
 * Default implementation of {@link ISpamController}.
 */
class SpamController implements ISpamController
{
    private static final Object EMPTY = new Object();
    private static final int DEFAULT_REQUEST_COUNT = 2;
    private static final int DEFAULT_EXPIRE_TIME = 30;

    private final int requestCount;
    private final int expireTime;
    private Cache<String, Cache<Date, Object>> cache;
    private final TimeUnit timeUnit;

    @Inject
    SpamController(Configuration configuration)
    {
        this(configuration, MINUTES);
    }

    SpamController(Configuration configuration, TimeUnit timeUnit)
    {
        this.timeUnit = timeUnit;
        this.requestCount = configuration.getInteger("spam.request-count", DEFAULT_REQUEST_COUNT);
        this.expireTime = configuration.getInteger("spam.expire-time", DEFAULT_EXPIRE_TIME);
    }

    @Override
    public boolean check(String user)
    {
        if (!isSpamDetectionEnabled())
        {
            return true;
        }

        Cache<Date, Object> cache = getCache().asMap().computeIfAbsent(user, key -> newBuilder()
                .expireAfterWrite(expireTime, timeUnit)
                .build());

        cache.cleanUp();
        if (cache.size() < requestCount)
        {
            cache.asMap().put(new Date(), EMPTY);
            return true;
        }

        return false;
    }

    private Cache<String, Cache<Date, Object>> getCache()
    {
        if (cache == null)
        {
            cache = createCache();
        }
        return cache;
    }

    private synchronized Cache<String, Cache<Date, Object>> createCache()
    {
        return newBuilder()
                .expireAfterAccess(expireTime, timeUnit)
                .build();
    }

    private boolean isSpamDetectionEnabled()
    {
        return requestCount > 0
            && expireTime > 0;
    }
}
