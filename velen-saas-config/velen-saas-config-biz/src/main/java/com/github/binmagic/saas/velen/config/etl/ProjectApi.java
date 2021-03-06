package com.github.binmagic.saas.velen.config.etl;

import com.github.binmagic.saas.velen.config.entity.KeyRule;
import com.github.binmagic.saas.velen.config.entity.ParserRule;
import com.velen.etl.verification.entity.FieldRuleType;
import com.velen.etl.verification.entity.InputParseType;
import com.velen.etl.verification.entity.VerifyEnum;
import com.velen.etl.verification.tdo.FieldRuleTDO;
import com.velen.etl.verification.tdo.ParseFormatTDO;
import org.springframework.cloud.openfeign.FeignClient;

import java.util.ArrayList;
import java.util.List;

@FeignClient("velen-etl-verification")
public interface ProjectApi extends com.velen.etl.verification.api.ProjectApi {
    class Convert {
        public static List<ParseFormatTDO> toParseFormatTDO(List<ParserRule> parserRulesDTO) {
            List<ParseFormatTDO> parseFormatsTDO=new ArrayList<>();
            for (ParserRule parserRuleDTO : parserRulesDTO) {
                ParseFormatTDO parseFormatTDO = new ParseFormatTDO();
                parseFormatTDO.setInputParseType(InputParseType.parse(parserRuleDTO.getRuleKey()));
                parseFormatTDO.setFormula(parserRuleDTO.getRule());
                parseFormatsTDO.add(parseFormatTDO);
            }
            return parseFormatsTDO;
        }

        public static List<FieldRuleTDO> toFieldRuleTDO(List<KeyRule> keyRulesDTO) {
            List<FieldRuleTDO> fieldRulesTDO=new ArrayList<>();
            for (KeyRule keyRuleDTO:keyRulesDTO) {
                FieldRuleTDO fieldRuleTDO = new FieldRuleTDO();
                fieldRuleTDO.setFieldName(keyRuleDTO.getKey());
                fieldRuleTDO.setKeyRule(keyRuleDTO.getRule());
                fieldRuleTDO.setKeyRuleType(FieldRuleType.parse(keyRuleDTO.getRuleKey()));
                fieldRuleTDO.setValueRule(keyRuleDTO.getValueRule());
                fieldRulesTDO.add(fieldRuleTDO);
            }
            return fieldRulesTDO;
        }
    }
}
