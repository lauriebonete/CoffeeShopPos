package org.pos.coffee.web;

import org.pos.coffee.bean.BaseEntity;
import org.pos.coffee.service.BaseCrudService;
import org.pos.coffee.utility.StringUtil;

import java.beans.PropertyEditorSupport;

/**
 * Created by Laurie on 11/13/2015.
 */
public class BaseEntityEditor extends PropertyEditorSupport {

    private BaseCrudService<? extends BaseEntity> entityService;

    public BaseEntityEditor(BaseCrudService<? extends BaseEntity> entityService) {
        this.entityService = entityService;
    }

    @Override
    public String getAsText() {
        if (getValue() != null) {
            BaseEntity editor = (BaseEntity) getValue();
            return editor.getId().toString();
        }
        return "";
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        BaseEntity entity = entityService.load(text);
        setValue(entity);
    }
}
