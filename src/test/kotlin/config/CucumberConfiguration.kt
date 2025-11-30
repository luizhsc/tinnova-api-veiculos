package config

import com.api.veiculo.VeiculoApplication
import io.cucumber.spring.CucumberContextConfiguration
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@CucumberContextConfiguration
@SpringBootTest(classes = [VeiculoApplication::class])
@ActiveProfiles("test")
class CucumberConfiguration
