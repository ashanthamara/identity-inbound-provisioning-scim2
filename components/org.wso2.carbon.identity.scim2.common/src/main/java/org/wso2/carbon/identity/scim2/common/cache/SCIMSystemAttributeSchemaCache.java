/*
 * Copyright (c) 2025, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.wso2.carbon.identity.scim2.common.cache;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.identity.application.common.cache.BaseCache;
import org.wso2.charon3.core.schema.AttributeSchema;

/**
 * This stores system AttributeSchema against tenants.
 */
public class SCIMSystemAttributeSchemaCache 
        extends BaseCache<SCIMSystemAttributeSchemaCacheKey, SCIMSystemAttributeSchemaCacheEntry> {

    private static final String SCIM_SYSTEM_SCHEMA_CACHE = "SCIMSystemAttributeSchemaCache";
    private static final Log log = LogFactory.getLog(SCIMSystemAttributeSchemaCache.class);

    private static volatile SCIMSystemAttributeSchemaCache instance;

    private SCIMSystemAttributeSchemaCache() {

        super(SCIM_SYSTEM_SCHEMA_CACHE);
    }

    public static SCIMSystemAttributeSchemaCache getInstance() {

        if (instance == null) {
            synchronized (SCIMSystemAttributeSchemaCache.class) {
                if (instance == null) {
                    instance = new SCIMSystemAttributeSchemaCache();
                }
            }
        }
        return instance;
    }

    /**
     * Add system attribute schema to cache against tenantId.
     *
     * @param tenantId TenantId.
     * @param systemAttributeSchema SystemAttributeSchema.
     */
    public void addSCIMSystemAttributeSchema(int tenantId, AttributeSchema systemAttributeSchema){

        SCIMSystemAttributeSchemaCacheKey cacheKey = new SCIMSystemAttributeSchemaCacheKey(tenantId);
        SCIMSystemAttributeSchemaCacheEntry cacheEntry = new SCIMSystemAttributeSchemaCacheEntry(systemAttributeSchema);
        super.addToCache(cacheKey, cacheEntry);
        if (log.isDebugEnabled()) {
            log.debug("Successfully added scim system attributes into SCIMSystemSchemaCache for the tenant:"
                    + tenantId);
        }

    }


    /**
     * Get SCIM2 System AttributeSchema by tenantId.
     *
     * @param tenantId TenantId.
     * @return AttributeSchema.
     */
    public AttributeSchema getSCIMSystemAttributeSchemaByTenant(int tenantId) {

        SCIMSystemAttributeSchemaCacheKey cacheKey = new SCIMSystemAttributeSchemaCacheKey(tenantId);
        SCIMSystemAttributeSchemaCacheEntry cacheEntry = super.getValueFromCache(cacheKey);
        if (cacheEntry != null) {
            return cacheEntry.getSCIMSystemAttributeSchema();
        } else {
            if (log.isDebugEnabled()) {
                log.debug("Cache entry is null for tenantId: " + tenantId);
            }
            return null;
        }
    }

    /**
     * Clear SCIM2 System AttributeSchema by tenantId.
     *
     * @param tenantId TenantId.
     */
    public void clearSCIMSystemAttributeSchemaByTenant(int tenantId) {

        if (log.isDebugEnabled()) {
            log.debug("Clearing SCIMSystemAttributeSchemaCache entry by the tenant with id: " + tenantId);
        }
        SCIMSystemAttributeSchemaCacheKey cacheKey = new SCIMSystemAttributeSchemaCacheKey(tenantId);
        super.clearCacheEntry(cacheKey);
    }
}
