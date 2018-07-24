<#if (packageName)??>
package ${packageName};
</#if>
<#if (table.hasSqlDate)>
import java.sql.Date;
</#if>
<#if (table.hasTimeStamp)>
import java.sql.Timestamp;
</#if>
<#if (table.hasBigDecimal)>
import java.math.BigDecimal;
</#if>
import com.gitee.hengboy.mybatis.enhance.common.annotation.Column;
import com.gitee.hengboy.mybatis.enhance.common.annotation.Id;
import com.gitee.hengboy.mybatis.enhance.common.annotation.Table;
import com.gitee.hengboy.mybatis.enhance.common.enums.KeyGeneratorTypeEnum;
import lombok.Data;

/**
 * <p>本类由Code-Builder自动生成</p>
 * <p>表名: ${table.tableName} - ${table.remark} - 数据实体</p>
 *
 * @author Code-Builder
 * @since 恒宇少年
 * ===============================
 * Created with Code-Builder.
 * User：
 * Date：${.now}
 * 简书：http://www.jianshu.com/u/092df3f77bca
 * 码云：https://gitee.com/hengboy
 * GitHub：https://github.com/hengyuboy
 * ================================
 */
@Data
@Table(name = "${table.tableName}")
public class ${className} {
<#list table.primaryKeys as key>
    /**
     * ${key.columnName} - ${key.remark}
     */
    <#if (key.autoincrement)>
    @Id(generatorType = KeyGeneratorTypeEnum.AUTO)
    <#else>
    @Id(generatorType = KeyGeneratorTypeEnum.UUID)
    </#if>
    @Column(name="${key.columnName}")
    private ${key.javaType} ${key.javaProperty};
</#list>
<#list table.columns as column>
    <#if (!column.primaryKey)>
    /**
     * ${column.columnName} - ${column.remark}
     */
    @Column(name="${column.columnName}"<#if (column.defaultValue)??>,insertable = false</#if>)
    private ${column.javaType} ${column.javaProperty};
    </#if>
</#list>
}