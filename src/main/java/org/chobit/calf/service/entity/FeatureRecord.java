package org.chobit.calf.service.entity;

/**
 * table: feature_record
 *
 * @author robin
 */
public class FeatureRecord extends AbstractEntity {

    private int type = 1;

    private int featureId;

    private int workId;

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

    public int getWorkId() {
        return workId;
    }

    public void setWorkId(int workId) {
        this.workId = workId;
    }
}
