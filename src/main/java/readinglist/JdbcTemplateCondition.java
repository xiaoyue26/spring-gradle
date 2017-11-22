package readinglist;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * Created by xiaoyue26 on 17/11/22.
 * 第二章: 条件bean. 只有当matches成立时,才会加载的Bean(MyService)
 */
public class JdbcTemplateCondition implements Condition {

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata annotatedTypeMetadata) {
        //new org.springframework.jdbc.core.JdbcTemplate();
        try {
            context.getClassLoader().loadClass(
                    "org.springframework.jdbc.core.JdbcTemplate");
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    // 使用的代码:
    /*@Conditional(JdbcTemplateCondition.class)
    public MyService myService() {
        //do something
    }*/
}
