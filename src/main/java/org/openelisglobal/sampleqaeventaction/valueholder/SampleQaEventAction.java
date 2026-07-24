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
package org.openelisglobal.sampleqaeventaction.valueholder;

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
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.openelisglobal.action.valueholder.Action;
import org.openelisglobal.common.util.ConfigurationProperties;
import org.openelisglobal.common.util.ConfigurationProperties.Property;
import org.openelisglobal.common.util.DateUtil;
import org.openelisglobal.common.valueholder.BaseObject;
import org.openelisglobal.common.valueholder.ValueHolder;
import org.openelisglobal.common.valueholder.ValueHolderInterface;
import org.openelisglobal.sampleqaevent.valueholder.SampleQaEvent;
import org.openelisglobal.systemuser.valueholder.SystemUser;

/**
 * @author benzd1 bugzilla 2510
 */

@Getter
@Setter
@Entity
@Table(name = "SAMPLE_QAEVENT_ACTION")
public class SampleQaEventAction extends BaseObject<String> {

    @Id
    @GeneratedValue(generator = "string-sequence-generator")
    @GenericGenerator(name = "string-sequence-generator", strategy = "org.openelisglobal.hibernate.resources.StringSequenceGenerator", parameters = {
            @Parameter(name = "sequence_name", value = "sample_qaevent_action_seq") })
    @Column(name = "ID", precision = 10, scale = 0, nullable = false)
    private String id;

    @Transient
    private String sampleQaEventId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SAMPLE_QAEVENT_ID", nullable = false)
    private ValueHolderInterface sampleQaEvent;

    @Transient
    private String actionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ACTION_ID", nullable = false)
    private ValueHolderInterface action;

    @Column(name = "CREATED_DATE", length = 7, nullable = false)
    private Date createdDate;

    private String createdDateForDisplay;

    // bugzilla 2481
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SYS_USER_ID")
    private SystemUser systemUser;

    @Transient
    private String systemUserId;

    @Version
    @Column(name = "LASTUPDATED")
    private java.sql.Timestamp lastupdated;

    public SampleQaEventAction() {
        super();
        this.action = new ValueHolder();
        this.sampleQaEvent = new ValueHolder();
    }

    // Action
    public Action getAction() {
        return (Action) this.action.getValue();
    }

    public void setAction(ValueHolderInterface action) {
        this.action = action;
    }

    public void setAction(Action action) {
        this.action.setValue(action);
    }

    protected ValueHolderInterface getActionHolder() {
        return this.action;
    }

    protected void setActionHolder(ValueHolderInterface action) {
        this.action = action;
    }

    // SAMPLE_QA_EVENT
    public SampleQaEvent getSampleQaEvent() {
        return (SampleQaEvent) this.sampleQaEvent.getValue();
    }

    public void setSampleQaEvent(ValueHolderInterface sampleQaEvent) {
        this.sampleQaEvent = sampleQaEvent;
    }

    public void setSampleQaEvent(SampleQaEvent sampleQaEvent) {
        this.sampleQaEvent.setValue(sampleQaEvent);
    }

    protected ValueHolderInterface getSampleQaEventHolder() {
        return this.sampleQaEvent;
    }

    protected void setSampleQaEventHolder(ValueHolderInterface sampleQaEvent) {
        this.sampleQaEvent = sampleQaEvent;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
        this.createdDateForDisplay = DateUtil.convertSqlDateToStringDate(createdDate);
    }

    public void setCreatedDateForDisplay(String createdDateForDisplay) {
        this.createdDateForDisplay = createdDateForDisplay;
        // also update the java.sql.Date
        String locale = ConfigurationProperties.getInstance().getPropertyValue(Property.DEFAULT_LANG_LOCALE);
        this.createdDate = DateUtil.convertStringDateToSqlDate(createdDateForDisplay, locale);
    }
}
