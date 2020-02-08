package org.chobit.calf.service.entity;

/**
 * @author robin
 */
public class FeatureRecord extends AbstractEntity {

    private int type;

    private int featureId;

    private int recordId;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getFeatureId() {
        return featureId;
    }

    public void setFeatureId(int featureId) {
        this.featureId = featureId;
    }

    public int getRecordId() {
        return recordId;
    }

    public void setRecordId(int recordId) {
        this.recordId = recordId;
    }
}
