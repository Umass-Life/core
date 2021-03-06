package api.ema;


import domain.models.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.Constraint;

@Entity
public class StressLevel extends BaseEntity {
    public static final String SINGULAR = StressLevel.class.getSimpleName();
    public static final String PLURAL = SINGULAR+ "s";

    @Column(unique = true, nullable = false)
    private Long dateTime;

    @Column(nullable = false)
    private String stressLevel;

    public StressLevel(){}
    public StressLevel(Long dateTime, String stressLevel) {
        this.dateTime = dateTime;
        this.stressLevel = stressLevel;
    }

    public Long getDateTime() {
        return dateTime;
    }

    public void setDateTime(Long dateTime) {
        this.dateTime = dateTime;
    }

    public String getStressLevel() {
        return stressLevel;
    }

    public void setStressLevel(String stressLevel) {
        this.stressLevel = stressLevel;
    }
}
