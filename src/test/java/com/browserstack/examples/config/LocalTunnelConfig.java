package com.browserstack.examples.config;

import lombok.Data;
import lombok.ToString;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Anirudha Khanna
 */
@Data
@ToString
public class LocalTunnelConfig {

    private Boolean enable;

    private Boolean forceLocal;
}
