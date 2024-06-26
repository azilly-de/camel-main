/* Generated by camel build tools - do NOT edit this file! */
package org.apache.camel.component.leveldb;

import javax.annotation.processing.Generated;
import java.util.Map;

import org.apache.camel.CamelContext;
import org.apache.camel.spi.ExtendedPropertyConfigurerGetter;
import org.apache.camel.spi.PropertyConfigurerGetter;
import org.apache.camel.spi.ConfigurerStrategy;
import org.apache.camel.spi.GeneratedPropertyConfigurer;
import org.apache.camel.util.CaseInsensitiveMap;
import org.apache.camel.component.leveldb.LevelDBAggregationRepository;

/**
 * Generated by camel build tools - do NOT edit this file!
 */
@Generated("org.apache.camel.maven.packaging.GenerateConfigurerMojo")
@SuppressWarnings("unchecked")
public class LevelDBAggregationRepositoryConfigurer extends org.apache.camel.support.component.PropertyConfigurerSupport implements GeneratedPropertyConfigurer, PropertyConfigurerGetter {

    @Override
    public boolean configure(CamelContext camelContext, Object obj, String name, Object value, boolean ignoreCase) {
        org.apache.camel.component.leveldb.LevelDBAggregationRepository target = (org.apache.camel.component.leveldb.LevelDBAggregationRepository) obj;
        switch (ignoreCase ? name.toLowerCase() : name) {
        case "allowserializedheaders":
        case "allowSerializedHeaders": target.setAllowSerializedHeaders(property(camelContext, boolean.class, value)); return true;
        case "deadletteruri":
        case "deadLetterUri": target.setDeadLetterUri(property(camelContext, java.lang.String.class, value)); return true;
        case "maximumredeliveries":
        case "maximumRedeliveries": target.setMaximumRedeliveries(property(camelContext, int.class, value)); return true;
        case "persistentfilename":
        case "persistentFileName": target.setPersistentFileName(property(camelContext, java.lang.String.class, value)); return true;
        case "recoveryinterval":
        case "recoveryInterval": target.setRecoveryInterval(property(camelContext, long.class, value)); return true;
        case "repositoryname":
        case "repositoryName": target.setRepositoryName(property(camelContext, java.lang.String.class, value)); return true;
        case "returnoldexchange":
        case "returnOldExchange": target.setReturnOldExchange(property(camelContext, boolean.class, value)); return true;
        case "serializer": target.setSerializer(property(camelContext, org.apache.camel.component.leveldb.LevelDBSerializer.class, value)); return true;
        case "sync": target.setSync(property(camelContext, boolean.class, value)); return true;
        case "userecovery":
        case "useRecovery": target.setUseRecovery(property(camelContext, boolean.class, value)); return true;
        default: return false;
        }
    }

    @Override
    public Class<?> getOptionType(String name, boolean ignoreCase) {
        switch (ignoreCase ? name.toLowerCase() : name) {
        case "allowserializedheaders":
        case "allowSerializedHeaders": return boolean.class;
        case "deadletteruri":
        case "deadLetterUri": return java.lang.String.class;
        case "maximumredeliveries":
        case "maximumRedeliveries": return int.class;
        case "persistentfilename":
        case "persistentFileName": return java.lang.String.class;
        case "recoveryinterval":
        case "recoveryInterval": return long.class;
        case "repositoryname":
        case "repositoryName": return java.lang.String.class;
        case "returnoldexchange":
        case "returnOldExchange": return boolean.class;
        case "serializer": return org.apache.camel.component.leveldb.LevelDBSerializer.class;
        case "sync": return boolean.class;
        case "userecovery":
        case "useRecovery": return boolean.class;
        default: return null;
        }
    }

    @Override
    public Object getOptionValue(Object obj, String name, boolean ignoreCase) {
        org.apache.camel.component.leveldb.LevelDBAggregationRepository target = (org.apache.camel.component.leveldb.LevelDBAggregationRepository) obj;
        switch (ignoreCase ? name.toLowerCase() : name) {
        case "allowserializedheaders":
        case "allowSerializedHeaders": return target.isAllowSerializedHeaders();
        case "deadletteruri":
        case "deadLetterUri": return target.getDeadLetterUri();
        case "maximumredeliveries":
        case "maximumRedeliveries": return target.getMaximumRedeliveries();
        case "persistentfilename":
        case "persistentFileName": return target.getPersistentFileName();
        case "recoveryinterval":
        case "recoveryInterval": return target.getRecoveryInterval();
        case "repositoryname":
        case "repositoryName": return target.getRepositoryName();
        case "returnoldexchange":
        case "returnOldExchange": return target.isReturnOldExchange();
        case "serializer": return target.getSerializer();
        case "sync": return target.isSync();
        case "userecovery":
        case "useRecovery": return target.isUseRecovery();
        default: return null;
        }
    }
}

