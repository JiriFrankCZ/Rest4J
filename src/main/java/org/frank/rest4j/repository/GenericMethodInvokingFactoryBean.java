package org.frank.rest4j.repository;

import org.springframework.beans.factory.config.MethodInvokingFactoryBean;

/**
 * This method invoking bean extends funcionality of base factory with generic return type.
 * Generic return type can be used e.g. for proxies.
 *
 * @author Jiøí Frank
 * @see MethodInvokingFactoryBean
 */
public class GenericMethodInvokingFactoryBean extends MethodInvokingFactoryBean {

    private Class<?> targetType;

    public Class<?> getTargetType() {
        return targetType;
    }

    public void setTargetType(Class<?> targetType) {
        this.targetType = targetType;
    }

    /**
     * If target type is manualy defined, than return this class, otherwise
     * return type according to target type of factory, if it´s been initialized.
     *
     * @return Type for container
     */
    @Override
    public Class<?> getObjectType() {
        if(targetType == null){
            return super.getObjectType();
        }

        return targetType;
    }
}
