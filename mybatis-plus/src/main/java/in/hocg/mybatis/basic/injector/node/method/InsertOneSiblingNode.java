package in.hocg.mybatis.basic.injector.node.method;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.TableInfoHelper;
import com.baomidou.mybatisplus.core.toolkit.sql.SqlScriptUtils;
import org.apache.ibatis.executor.keygen.Jdbc3KeyGenerator;
import org.apache.ibatis.executor.keygen.KeyGenerator;
import org.apache.ibatis.executor.keygen.NoKeyGenerator;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;

/**
 * Created by hocgin on 2018/12/9.
 * email: hocgin@gmail.com
 * <p>
 * ```sql
 * LOCK TABLE nested_category WRITE;
 * <p>
 * SELECT @myRight := rgt FROM nested_category
 * WHERE name = 'TELEVISIONS';
 * <p>
 * UPDATE nested_category SET rgt = rgt + 2 WHERE rgt > @myRight;
 * UPDATE nested_category SET lft = lft + 2 WHERE lft > @myRight;
 * <p>
 * INSERT INTO nested_category(name, lft, rgt) VALUES('GAME CONSOLES', @myRight + 1, @myRight + 2);
 * <p>
 * UNLOCK TABLES;
 * ```
 *
 * @author hocgin
 */
public class InsertOneSiblingNode extends AbstractMethod {
    
    private static StringBuilder SQL = new StringBuilder("<script>")
            .append("SELECT @myRight := rgt FROM :table WHERE :id = #{id};")
            .append("UPDATE :table SET rgt = rgt + 2 WHERE rgt > @myRight;")
            .append("UPDATE :table SET lft = lft + 2 WHERE lft > @myRight;")
            .append("INSERT INTO :table :columnScript VALUES :valuesScript;")
            .append("</script>");
    
    private String getMethodName() {
        return "insertOneSiblingNode";
    }
    
    /**
     * @param mapperClass
     * @param modelClass
     * @param tableInfo
     * @return
     */
    @Override
    public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
        String columnScript = SqlScriptUtils.convertTrim(tableInfo.getAllInsertSqlColumnMaybeIf(),
                LEFT_BRACKET, RIGHT_BRACKET, null, COMMA)
                // fixed: mybatis plus bug
                // 添加前缀不生效。。
                .replaceAll("<if test=\"", "<if test=\"node.")
                .replaceAll(" != null and ", " != null and node.")
                // ---
                .replace("<if test=\"node.lft != null\">lft,</if>", "lft,")
                .replace("<if test=\"node.rgt != null\">rgt,</if>", "rgt,");
        String valuesScript = SqlScriptUtils.convertTrim(tableInfo.getAllInsertSqlPropertyMaybeIf("node."),
                LEFT_BRACKET, RIGHT_BRACKET, null, COMMA)
                // fixed: mybatis plus bug
                // 添加前缀不生效。。
                .replaceAll("<if test=\"", "<if test=\"node.")
                .replaceAll(" != null and ", " != null and node.")
                // ---
                .replace("<if test=\"node.lft != null\">#{node.lft},</if>", "@myRight + 1,")
                .replace("<if test=\"node.rgt != null\">#{node.rgt},</if>", "@myRight + 2,");
        
        String sql = SQL.toString().replaceAll(":table", tableInfo.getTableName())
                .replaceAll(":id", tableInfo.getKeyColumn())
                .replaceAll(":columns", sqlSelectColumns(tableInfo, true))
                .replaceAll(":valuesScript", valuesScript)
                .replaceAll(":columnScript", columnScript);
        
        KeyGenerator keyGenerator = new NoKeyGenerator();
        String keyProperty = null;
        String keyColumn = null;
        
        // 表包含主键处理逻辑,如果不包含主键当普通字段处理
        if (StringUtils.isNotEmpty(tableInfo.getKeyProperty())) {
            if (tableInfo.getIdType() == IdType.AUTO) {
                /** 自增主键 */
                keyGenerator = new Jdbc3KeyGenerator();
                keyProperty = tableInfo.getKeyProperty();
                keyColumn = tableInfo.getKeyColumn();
            } else {
                if (null != tableInfo.getKeySequence()) {
                    keyGenerator = TableInfoHelper.genKeyGenerator(tableInfo, builderAssistant, getMethodName(), languageDriver);
                    keyProperty = tableInfo.getKeyProperty();
                    keyColumn = tableInfo.getKeyColumn();
                }
            }
        }
        SqlSource sqlSource = languageDriver.createSqlSource(configuration, sql, modelClass);
        return this.addInsertMappedStatement(mapperClass, modelClass, getMethodName(), sqlSource, keyGenerator, keyProperty, keyColumn);
    }
}
