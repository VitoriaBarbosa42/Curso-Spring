package io.github.curso.libraryapi.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class DatabaseConfig {

    @Value("${spring.datasource.url}")
    String url;
    @Value("${spring.datasource.username}")
    String username;
    @Value("${spring.datasource.password}")
    String password;
    @Value("${spring.datasource.driver-class-name}")
    String driver;

    //@Bean
    public DataSource dataSoucer(){
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setUrl(url);
        ds.setUsername(username);
        ds.setPassword(password);
        ds.setDriverClassName(driver);
        return ds;
    }

    @Bean
    public DataSource hikariDataSource(){
        HikariConfig config = new HikariConfig();
        config.setUsername(username);
        config.setPassword(password);
        config.setDriverClassName(driver);
        config.setJdbcUrl(url);

        config.setMaximumPoolSize(10); // máximo de conexão liberadas
        config.setMinimumIdle(1); // tamanho inicial do pool
        config.setPoolName("library-db-pool");
        config.setMaxLifetime(600000); //ms
        config.setConnectionTimeout(100000); // timeout para conseguir uma conexão
        config.setConnectionTestQuery("select 1"); // query de teste

        return new HikariDataSource(config);
    }
}

/* O que é Pool de conexões:
 *
 * Trata-se de um cache de conexões com o banco de dados, no qual um número pré-determinado de conexões permanece
 * aberto e pronto para uso por uma aplicação web. Isso elimina a sobrecarga e a demora causadas pela abertura e
 * fechamento frequentes de conexões, melhorando o desempenho da aplicação.
 */

/*  O que é DataSource:
 *
 *  É uma solução que funciona como uma fabrica de conexões com o Banco de Dados, automatizando as configurações para o
 * desenvolvedor.
 */

/* O que é HicariCP:
 * No Spring é um pool de conexões ultrarrápido e leve que gerencia conexões com o banco de dados, mantendo-as abertas
 * e reutilizáveis para reduzir a sobrecarga de criar novas conexões, sendo o padrão em Spring Boot.
 */