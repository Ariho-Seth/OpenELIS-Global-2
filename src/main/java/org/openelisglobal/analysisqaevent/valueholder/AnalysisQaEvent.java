/**
 * The contents of this file are subject to the Mozilla Public License Version 1.1 (the "License");
 * you may not use this file except in compliance with the License. You may obtain a copy of the
 * License at http://www.mozilla.org/MPL/
 *
 * <p>Software distributed under the License is distributed on an "AS IS" basis, WITHOUT WARRANTY OF
 * ANY KIND, either express or implied. See the License for the specific language governing rights
 * and limitations under the License.
 *
 * <p>The Original Code is OpenELIS code.
 *
 * <p>Copyright (C) The Minnesota Department of Health. All Rights Reserved.
 */
package org.openelisglobal.analysisqaevent.valueholder;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.persistence.Version;
import java.sql.Date;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import org.openelisglobal.analysis.valueholder.Analysis;
import org.openelisglobal.common.util.ConfigurationProperties;
import org.openelisglobal.common.util.ConfigurationProperties.Property;
import org.openelisglobal.common.util.DateUtil;
import org.openelisglobal.common.valueholder.BaseObject;
import org.openelisglobal.common.valueholder.ValueHolder;
import org.openelisglobal.common.valueholder.ValueHolderInterface;
import org.openelisglobal.qaevent.valueholder.QaEvent;

@Getter
@Setter
@DynamicUpdate
@Entity
@Table(name = "ANALYSIS_QAEVENT")
public class AnalysisQaEvent extends BaseObject<String> {

    @Id
    @GeneratedValue(generator = "string-sequence-generator")
    @GenericGenerator(name = "string-sequence-generator", strategy = "org.openelisglobal.hibernate.resources.StringSequenceGenerator", parameters = {
            @org.hibernate.annotations.Parameter(name = "sequence_name", value = "analysis_qaevent_seq") })
    @Column(name = "ID", precision = 10, scale = 0, nullable = false)
    private String id;

    @Transient
    private String qaEventId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "QA_EVENT_ID", nullable = false)
    private ValueHolderInterface qaEvent;

    @Transient
    private String analysisId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ANALYSIS_ID", nullable = false)
    private ValueHolderInterface analysis;

    @Column(name = "CREATED_DATE", length = 7, nullable = false)
    private Date completedDate;

    private String completedDateForDisplay;

    private String analysisQaEventDisplayValue;

    @Version
    @Column(name = "LASTUPDATED")
    private java.sql.Timestamp lastupdated;

    public AnalysisQaEvent() {
        super();
        this.analysis = new ValueHolder();
        this.qaEvent = new ValueHolder();
    }

    // ANALYSIS
    public Analysis getAnalysis() {
        return (Analysis) this.analysis.getValue();
    }

    public void setAnalysis(ValueHolderInterface analysis) {
        this.analysis = analysis;
    }

    public void setAnalysis(Analysis analysis) {
        this.analysis.setValue(analysis);
    }

    protected ValueHolderInterface getAnalysisHolder() {
        return this.analysis;
    }

    protected void setAnalysisHolder(ValueHolderInterface analysis) {
        this.analysis = analysis;
    }

    // QA_EVENT
    public QaEvent getQaEvent() {
        return (QaEvent) this.qaEvent.getValue();
    }

    public void setQaEvent(ValueHolderInterface qaEvent) {
        this.qaEvent = qaEvent;
    }

    public void setQaEvent(QaEvent qaEvent) {
        this.qaEvent.setValue(qaEvent);
    }

    protected ValueHolderInterface getQaEventHolder() {
        return this.qaEvent;
    }

    protected void setQaEventHolder(ValueHolderInterface qaEvent) {
        this.qaEvent = qaEvent;
    }

    public void setCompletedDate(Date completedDate) {
        this.completedDate = completedDate;
        this.completedDateForDisplay = DateUtil.convertSqlDateToStringDate(completedDate);
    }

    public void setCompletedDateForDisplay(String completedDateForDisplay) {
        this.completedDateForDisplay = completedDateForDisplay;
        // also update the java.sql.Date
        String locale = ConfigurationProperties.getInstance().getPropertyValue(Property.DEFAULT_LANG_LOCALE);
        this.completedDate = DateUtil.convertStringDateToSqlDate(completedDateForDisplay, locale);
    }

    public String getAnalysisQaEventDisplayValue() {
        if (analysis != null && qaEvent != null) {
            Analysis analysis = getAnalysis();
            String testDisplayValue = analysis.getTest().getTestDisplayValue();
            QaEvent qaEvent = getQaEvent();
            String qaEventDisplayValue = qaEvent.getQaEventDisplayValue();
            analysisQaEventDisplayValue = testDisplayValue + " | " + qaEventDisplayValue;
        } else {
            analysisQaEventDisplayValue = "NO VALUES AVAILABLE";
        }
        return analysisQaEventDisplayValue;
    }
}
