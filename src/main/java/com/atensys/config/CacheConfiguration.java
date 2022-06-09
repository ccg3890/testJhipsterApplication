package com.atensys.config;

import java.time.Duration;
import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;
import org.hibernate.cache.jcache.ConfigSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.info.GitProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.context.annotation.*;
import tech.jhipster.config.JHipsterProperties;
import tech.jhipster.config.cache.PrefixedKeyGenerator;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private GitProperties gitProperties;
    private BuildProperties buildProperties;
    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache = jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration =
            Eh107Configuration.fromEhcacheCacheConfiguration(
                CacheConfigurationBuilder
                    .newCacheConfigurationBuilder(Object.class, Object.class, ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                    .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                    .build()
            );
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, com.atensys.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, com.atensys.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, com.atensys.domain.User.class.getName());
            createCache(cm, com.atensys.domain.Authority.class.getName());
            createCache(cm, com.atensys.domain.User.class.getName() + ".authorities");
            createCache(cm, com.atensys.domain.Dictionary.class.getName());
            createCache(cm, com.atensys.domain.Dictionary.class.getName() + ".archetypes");
            createCache(cm, com.atensys.domain.Archetype.class.getName());
            createCache(cm, com.atensys.domain.Archetype.class.getName() + ".dictionaries");
            createCache(cm, com.atensys.domain.Attribute.class.getName());
            createCache(cm, com.atensys.domain.AttributeCompany.class.getName());
            createCache(cm, com.atensys.domain.AttributeOffice.class.getName());
            createCache(cm, com.atensys.domain.AttributeFloor.class.getName());
            createCache(cm, com.atensys.domain.AttributeSeat.class.getName());
            createCache(cm, com.atensys.domain.AttributeRoom.class.getName());
            createCache(cm, com.atensys.domain.Company.class.getName());
            createCache(cm, com.atensys.domain.Company.class.getName() + ".orgs");
            createCache(cm, com.atensys.domain.Company.class.getName() + ".offices");
            createCache(cm, com.atensys.domain.Company.class.getName() + ".attributes");
            createCache(cm, com.atensys.domain.Office.class.getName());
            createCache(cm, com.atensys.domain.Office.class.getName() + ".floors");
            createCache(cm, com.atensys.domain.Office.class.getName() + ".attributes");
            createCache(cm, com.atensys.domain.Floor.class.getName());
            createCache(cm, com.atensys.domain.Floor.class.getName() + ".resources");
            createCache(cm, com.atensys.domain.Floor.class.getName() + ".attributes");
            createCache(cm, com.atensys.domain.UserInfo.class.getName());
            createCache(cm, com.atensys.domain.UserInfo.class.getName() + ".penalties");
            createCache(cm, com.atensys.domain.UserInfo.class.getName() + ".auths");
            createCache(cm, com.atensys.domain.Org.class.getName());
            createCache(cm, com.atensys.domain.Org.class.getName() + ".children");
            createCache(cm, com.atensys.domain.Rank.class.getName());
            createCache(cm, com.atensys.domain.Penalty.class.getName());
            createCache(cm, com.atensys.domain.Auth.class.getName());
            createCache(cm, com.atensys.domain.Auth.class.getName() + ".menus");
            createCache(cm, com.atensys.domain.Auth.class.getName() + ".userInfos");
            createCache(cm, com.atensys.domain.Menu.class.getName());
            createCache(cm, com.atensys.domain.Menu.class.getName() + ".children");
            createCache(cm, com.atensys.domain.Menu.class.getName() + ".auths");
            createCache(cm, com.atensys.domain.Config.class.getName());
            createCache(cm, com.atensys.domain.Config.class.getName() + ".children");
            createCache(cm, com.atensys.domain.Shape.class.getName());
            createCache(cm, com.atensys.domain.ShapeAsset.class.getName());
            createCache(cm, com.atensys.domain.Drawing.class.getName());
            createCache(cm, com.atensys.domain.Drawing.class.getName() + ".drawingItems");
            createCache(cm, com.atensys.domain.DrawingItem.class.getName());
            createCache(cm, com.atensys.domain.Fileinfo.class.getName());
            createCache(cm, com.atensys.domain.Resource.class.getName());
            createCache(cm, com.atensys.domain.Seat.class.getName());
            createCache(cm, com.atensys.domain.Seat.class.getName() + ".attributes");
            createCache(cm, com.atensys.domain.Room.class.getName());
            createCache(cm, com.atensys.domain.Room.class.getName() + ".roomManagers");
            createCache(cm, com.atensys.domain.Room.class.getName() + ".roomUserGroups");
            createCache(cm, com.atensys.domain.Room.class.getName() + ".roomSeats");
            createCache(cm, com.atensys.domain.Room.class.getName() + ".attributes");
            createCache(cm, com.atensys.domain.RoomSeat.class.getName());
            createCache(cm, com.atensys.domain.RoomManager.class.getName());
            createCache(cm, com.atensys.domain.RoomUserGroup.class.getName());
            createCache(cm, com.atensys.domain.Reservation.class.getName());
            createCache(cm, com.atensys.domain.Reservation.class.getName() + ".reservationTargets");
            createCache(cm, com.atensys.domain.Reservation.class.getName() + ".reservedDates");
            createCache(cm, com.atensys.domain.Recurrence.class.getName());
            createCache(cm, com.atensys.domain.ReservedDate.class.getName());
            createCache(cm, com.atensys.domain.ReservationTarget.class.getName());
            createCache(cm, com.atensys.domain.ReservationTarget.class.getName() + ".attendees");
            createCache(cm, com.atensys.domain.ReservationTarget.class.getName() + ".reservedRoomSeats");
            createCache(cm, com.atensys.domain.Attendee.class.getName());
            createCache(cm, com.atensys.domain.ReservedRoomSeat.class.getName());
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache != null) {
            cache.clear();
        } else {
            cm.createCache(cacheName, jcacheConfiguration);
        }
    }

    @Autowired(required = false)
    public void setGitProperties(GitProperties gitProperties) {
        this.gitProperties = gitProperties;
    }

    @Autowired(required = false)
    public void setBuildProperties(BuildProperties buildProperties) {
        this.buildProperties = buildProperties;
    }

    @Bean
    public KeyGenerator keyGenerator() {
        return new PrefixedKeyGenerator(this.gitProperties, this.buildProperties);
    }
}
