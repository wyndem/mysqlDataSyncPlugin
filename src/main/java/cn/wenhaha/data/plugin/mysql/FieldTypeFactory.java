package cn.wenhaha.data.plugin.mysql;

import cn.wenhaha.datasource.FieldType;

/**
 * 字段
 * --------
 *
 * @author ：wyndem
 * @Date ：Created in 2022-07-20 22:37
 */
public class FieldTypeFactory {

    public static FieldType getType(String typeName){
        if(typeName.charAt(typeName.length() - 1)==')'){
            typeName = typeName.substring(0,typeName.length()-5);
        }
        typeName=typeName.toUpperCase();
        switch (typeName){
            case "TINYINT": return  FieldType.TinyInt;
            case "INTEGER":
            case "SMALLINT":
            case "MEDIUMINT":
            case "BOOLEAN":
                return  FieldType.Int;
            case "VARCHAR":
            case "TEXT":
                return  FieldType.String;
            case "CHAR":  return  FieldType.Character;
            case "BIT":  return  FieldType.Boolean;
            case "BIGINT":  return  FieldType.BIGINT;
            case "FLOAT":  return  FieldType.Float;
            case "DOUBLE":  return  FieldType.Double;
            case "DECIMAL":  return  FieldType.DECIMAL;

            case "DATETIME":
            case "TIMESTAMP":
                return  FieldType.DateTime;
            case "DATE":  return  FieldType.Date;
            case "TIME":  return  FieldType.Time;
            default: return FieldType.Other;
        }
    }

}
