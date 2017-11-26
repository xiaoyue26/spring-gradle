package readinglist;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

import javax.sql.DataSource;

/**
 * Created by xiaoyue26 on 17/11/26.
 */
public class StartupRunner implements CommandLineRunner {
    protected static final Logger logger = LoggerFactory.getLogger(StartupRunner.class);

    @Autowired
    private DataSource ds;
    @Override
    public void run(String... strings) throws Exception {
        logger.info("Datasource: " + ds.toString());


    }
}
