package org.chobit.calf.service.entity;

import org.chobit.calf.tools.ShortCode;

/**
 * table script
 *
 * @author robin
 */
public class Script extends AbstractEntity {


    private String name;

    private String code = ShortCode.gen();

    private String script;

    private String remark;

    public Script() {
    }

    public Script(String name, String code) {
        this.name = name;
        this.code = code;
    }

    public Script(int id, String name, String code) {
        this(name, code);
        setId(id);
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getScript() {
        return script;
    }

    public void setScript(String script) {
        this.script = script;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
