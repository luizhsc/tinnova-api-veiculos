package com.api.veiculo.steps

import io.cucumber.junit.CucumberOptions
import org.junit.platform.suite.api.ConfigurationParameter
import org.junit.platform.suite.api.IncludeEngines
import org.junit.platform.suite.api.SelectClasspathResource
import org.junit.platform.suite.api.Suite
import io.cucumber.junit.platform.engine.Constants


@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")
@ConfigurationParameter(key = Constants.GLUE_PROPERTY_NAME, value = "com.api.veiculo.steps,com.api.veiculo.config")
@ConfigurationParameter(key = Constants.PLUGIN_PROPERTY_NAME, value = "pretty, html:target/cucumber-report.html")
@CucumberOptions(
    features = ["src/test/resources/features"],
    plugin = ["pretty"],
    glue = ["com.api.veiculo.steps", "com.api.veiculo.hooks"]
)
class CucumberTestSuite

