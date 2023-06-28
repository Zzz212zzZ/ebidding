package com.ebidding.bwic.service.config;

import com.ebidding.bwic.common.config.EBiddingConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({EBiddingConfig.class})
public class CommonConfig {
}
