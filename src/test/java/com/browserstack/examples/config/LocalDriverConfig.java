package com.browserstack.examples.config;

import java.util.List;

import lombok.Data;
import lombok.ToString;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Anirudha Khanna
 */
@Data
@ToString
public class LocalDriverConfig {

    private List<Platform> platforms;

}
