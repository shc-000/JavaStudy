package com.frog.study.generator;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.FileOutConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.TemplateConfig;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author code-generator
 * @date 2020/04/26
 */
public class MysqlGenerator {

    public static final String DATABASE_URL = "jdbc:mysql://127.0.0.1:3306/orion_scm?useUnicode=true"
            + "&serverTimezone=GMT&useSSL=false&characterEncoding=utf8";
    public static final String DATABASE_DRIVER = "com.mysql.jdbc.Driver";
    public static final String DATABASE_USERNAME = "root";
    public static final String DATABASE_PASSWORD = "shc123321";

    public static final String OUT_PUT_PATH = "/src/main/java";
    public static final String TEMPLATES_MAPPER_XML_PATH = "/templates/mapper.xml.ftl";
    public static final String XML_PATH = "/src/main/resources/mapper/";
    public static final String XML_POSTFIX = "Mapper";

    public static final String AUTHOR = "code-generator";
    public static final String PARENT_PACKAGE = "com.frog.study";
    public static final String ENTITY_PACKAGE = "dao.entity";
    public static final String MAPPER_PACKAGE = "dao.mapper";
    //public static final String[] SUPER_ENTITY_COLUMNS = { "id", "create_time", "update_time", "creator", "updator" };
    public static final boolean ONLY_ENTITY = true;

    /**
     * <p>
     * 读取控制台内容
     * </p>
     */
    public static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder help = new StringBuilder();
        help.append("请输入" + tip + "：");
        System.out.println(help.toString());
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotBlank(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }

    /**
     * RUN THIS
     */
    public static void main(String[] args) {
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        gc.setOutputDir(projectPath + OUT_PUT_PATH);
        gc.setAuthor(AUTHOR);
        gc.setOpen(false);
        gc.setServiceName("%sService");
        gc.setBaseResultMap(true);
        gc.setActiveRecord(true);
        gc.setBaseColumnList(true);
        gc.setSwagger2(true);
        gc.setFileOverride(true);
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl(DATABASE_URL);
        dsc.setDriverName(DATABASE_DRIVER);
        dsc.setUsername(DATABASE_USERNAME);
        dsc.setPassword(DATABASE_PASSWORD);
        mpg.setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setModuleName(null);
        pc.setParent(PARENT_PACKAGE);
        pc.setEntity(ENTITY_PACKAGE);
        pc.setMapper(MAPPER_PACKAGE);
        pc.setXml(XML_PATH);

        mpg.setPackageInfo(pc);

        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };
        List<FileOutConfig> focList = new ArrayList<>();
        focList.add(new FileOutConfig(TEMPLATES_MAPPER_XML_PATH) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                return projectPath + XML_PATH + tableInfo.getEntityName() + XML_POSTFIX + StringPool.DOT_XML;
            }
        });
        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);

        TemplateConfig templateConfig = new TemplateConfig().setXml(null);

        //打开说明不需要覆盖或者生成以下类
        if (true) {
            templateConfig.setController(null).setService(null).setServiceImpl(null);//.setMapper(null);
        }

        mpg.setTemplate(templateConfig);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setSuperEntityClass(Model.class);
        //strategy.setSuperEntityColumns(SUPER_ENTITY_COLUMNS);
        strategy.setEntityLombokModel(true);
        strategy.setChainModel(true);
        //		strategy.setInclude(scanner("表名，多个英文逗号分割").split("base_sku,base_spu"));
        //String [] tables = new String[]{"base_base_location","base_category","base_plant","base_sale_area",
        // "base_series","base_sku","base_spu"};
        //        String[] tables = new String[] { "spp_sale_plan" };
        //        String[] tables = new String[] { "sys_user_setting" };
        //        String[] tables = new String[] { "spp_sku_area_month_count" };
        //String[] tables = {"spp_production_month_count","spp_production_plan","spp_production_plan_detail"};
        String[] tables = {"b_order_match_result"};
//        String[] tables = new String[] {};
        strategy.setInclude(tables);
        //		strategy.setControllerMappingHyphenStyle(true);
        strategy.setRestControllerStyle(true);
        //        strategy.setTablePrefix("base_", "spp_");

        // 配置自动填充字段（Entity 会添加相应注解）
        List<TableFill> tableFillList = new ArrayList<>();
        tableFillList.add(new TableFill("creator", FieldFill.INSERT));
        tableFillList.add(new TableFill("updator", FieldFill.INSERT_UPDATE));
        strategy.setTableFillList(tableFillList);

        mpg.setStrategy(strategy);
        // 选择 freemarker 引擎需要指定如下加，注意 pom 依赖必须有！
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.execute();
    }

}

